  /**
   * Mybatis - 通用分页拦截器
   *
   * @author liuzh/abel533/isea533
   * @version 3.3.0 项目地址 : http://git.oschina.net/free/Mybatis_PageHelper
   */
  @SuppressWarnings("rawtypes")
  @Intercepts(@Signature(type = Executor.class, method = "query",
      args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}))
  public class PageHelper implements Interceptor {

    private Logger logger = LoggerFactory.getLogger(PageHelper.class);
    // sql工具类
    private SqlUtil sqlUtil;
    // 属性参数信息
    private Properties properties;
    // 配置对象方式
    private SqlUtilConfig sqlUtilConfig;
    // 自动获取dialect,如果没有setProperties或setSqlUtilConfig，也可以正常进行
    // private boolean autoDialect = true;
    // 运行时自动获取dialect
    private boolean autoRuntimeDialect;
    // 多数据源时，获取jdbcurl后是否关闭数据源
    private boolean closeConn = true;
    // 缓存
    private Map<String, SqlUtil> urlSqlUtilMap = new ConcurrentHashMap<>();
    private ReentrantLock lock = new ReentrantLock();

    private static int PARAMETER_INDEX = 1;
    private static int ROWBOUNDS_INDEX = 2;

    /**
     * 获取任意查询方法的count总数.
     *
     * @param select select
     */
    public static long count(ISelect select) {
      Page<?> page = startPage(1, -1, true);
      select.doSelect();
      return page.getTotal();
    }

    /**
     * 开始分页.
     *
     * @param pageNum 页码
     * @param pageSize 每页显示数量
     */
    public static <E> Page<E> startPage(int pageNum, int pageSize) {
      return startPage(pageNum, pageSize, true);
    }

    /**
     * 开始分页.
     *
     * @param pageNum 页码
     * @param pageSize 每页显示数量
     * @param count 是否进行count查询
     */
    public static <E> Page<E> startPage(int pageNum, int pageSize, boolean count) {
      return startPage(pageNum, pageSize, count, null);
    }

    /**
     * 开始分页.
     *
     * @param pageNum 页码
     * @param pageSize 每页显示数量
     * @param orderBy 排序
     */
    public static <E> Page<E> startPage(int pageNum, int pageSize, String orderBy) {
      Page<E> page = startPage(pageNum, pageSize);
      page.setOrderBy(orderBy);
      return page;
    }


    /**
     * 开始分页.
     *
     * @param pageNum 页码
     * @param pageSize 每页显示数量
     * @param count 是否进行count查询
     * @param reasonable 分页合理化,null时用默认配置
     */
    public static <E> Page<E> startPage(int pageNum, int pageSize, boolean count,
        Boolean reasonable) {
      return startPage(pageNum, pageSize, count, reasonable, null);
    }

    /**
     * 开始分页.
     *
     * @param pageNum 页码
     * @param pageSize 每页显示数量
     * @param count 是否进行count查询
     * @param reasonable 分页合理化,null时用默认配置
     * @param pageSizeZero true且pageSize=0时返回全部结果，false时分页,null时用默认配置
     */
    public static <E> Page<E> startPage(int pageNum, int pageSize, boolean count, Boolean reasonable,
        Boolean pageSizeZero) {
      Page<E> page = new Page<>(pageNum, pageSize, count);
      page.setReasonable(reasonable);
      page.setPageSizeZero(pageSizeZero);
      // 当已经执行过orderBy的时候
      Page<E> oldPage = SqlUtil.getLocalPage();
      if (oldPage != null && oldPage.isOrderByOnly()) {
        page.setOrderBy(oldPage.getOrderBy());
      }
      SqlUtil.setLocalPage(page);
      return page;
    }

    /**
     * 开始分页.
     * 
     * @param params params
     */
    public static <E> Page<E> startPage(Object params) {
      Page<E> page = SqlUtil.getPageFromObject(params);
      // 当已经执行过orderBy的时候
      Page<E> oldPage = SqlUtil.getLocalPage();
      if (oldPage != null && oldPage.isOrderByOnly()) {
        page.setOrderBy(oldPage.getOrderBy());
      }
      SqlUtil.setLocalPage(page);
      return page;
    }

    /**
     * 开始分页.
     *
     * @param offset 页码
     * @param limit 每页显示数量
     */
    public static <E> Page<E> offsetPage(int offset, int limit) {
      return offsetPage(offset, limit, true);
    }

    /**
     * 开始分页.
     *
     * @param offset 页码
     * @param limit 每页显示数量
     * @param count 是否进行count查询
     */
    public static <E> Page<E> offsetPage(int offset, int limit, boolean count) {
      Page<E> page = new Page<>(new int[] {offset, limit}, count);
      // 当已经执行过orderBy的时候
      Page<E> oldPage = SqlUtil.getLocalPage();
      if (oldPage != null && oldPage.isOrderByOnly()) {
        page.setOrderBy(oldPage.getOrderBy());
      }
      SqlUtil.setLocalPage(page);
      return page;
    }

    /**
     * 开始分页.
     *
     * @param offset 页码
     * @param limit 每页显示数量
     * @param orderBy 排序
     */
    public static <E> Page<E> offsetPage(int offset, int limit, String orderBy) {
      Page<E> page = offsetPage(offset, limit);
      page.setOrderBy(orderBy);
      return page;
    }


    /**
     * 排序.
     *
     * @param orderBy orderBy
     */
    public static void orderBy(String orderBy) {
      Page<?> page = SqlUtil.getLocalPage();
      if (page != null) {
        page.setOrderBy(orderBy);
      } else {
        page = new Page();
        page.setOrderBy(orderBy);
        page.setOrderByOnly(true);
        SqlUtil.setLocalPage(page);
      }
    }

    /**
     * 获取orderBy.
     *
     */
    public static String getOrderBy() {
      Page<?> page = SqlUtil.getLocalPage();
      if (page != null) {
        String orderBy = page.getOrderBy();
        if (StringUtils.isBlank(orderBy)) {
          return null;
        } else {
          return orderBy;
        }
      }
      return null;
    }

    /**
     * Mybatis拦截器方法.
     *
     * @param invocation 拦截器入参
     * @return 返回执行结果
     * @throws Throwable 抛出异常
     */
    @SuppressWarnings("unchecked")
    @Override
    public Object intercept(final Invocation invocation) throws Throwable {
      final Object[] queryArgs = invocation.getArgs();
      final Object parameter = queryArgs[PARAMETER_INDEX];
      Pageable pageable = null;
      if (parameter instanceof Pageable) {
        pageable = (Pageable) parameter;
        RowBounds rowBounds = new RowBounds(pageable.getOffset(), pageable.getPageSize());
        queryArgs[ROWBOUNDS_INDEX] = rowBounds;
      } else if (parameter instanceof ParamMap) {
        ParamMap map = (ParamMap) parameter;
        Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
        String param = "model params:";
        while (it.hasNext()) {
          Map.Entry<String, Object> entry = it.next();
          Object o = entry.getValue();
          if (o instanceof Pageable) {
            pageable = (Pageable) o;
            RowBounds rowBounds = new RowBounds(pageable.getOffset(), pageable.getPageSize());
            // it.remove();
            // map.put("rowBounds", rowBounds);
            map.put(entry.getKey(), rowBounds);
            queryArgs[2] = rowBounds;
          } else if (!entry.toString().startsWith("param") && entry.getValue() != null) {
            param = param + entry + ";";
          }
        }
        logger.debug(param);
      }
      return sqlUtil.processPage(invocation);
    }

    /**
     * 初始化sqlUtil.
     *
     * @param invocation invocation
     */
    public synchronized void initSqlUtil(Invocation invocation) {
      if (this.sqlUtil == null) {
        this.sqlUtil = getSqlUtil(invocation);
        if (!autoRuntimeDialect) {
          properties = null;
          sqlUtilConfig = null;
        }
        // autoDialect = false;
      }
    }

    /**
     * 获取url.
     *
     * @param dataSource dataSource
     */
    public String getUrl(DataSource dataSource) {
      Connection conn = null;
      try {
        conn = dataSource.getConnection();
        return conn.getMetaData().getURL();
      } catch (SQLException ex) {
        throw new RuntimeException(ex);
      } finally {
        if (conn != null) {
          try {
            if (closeConn) {
              conn.close();
            }
          } catch (SQLException ex) {
            // ignore
          }
        }
      }
    }

    /**
     * 根据datasource创建对应的sqlUtil.
     *
     * @param invocation invocation
     */
    public SqlUtil getSqlUtil(Invocation invocation) {
      MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
      // 改为对dataSource做缓存
      DataSource dataSource = ms.getConfiguration().getEnvironment().getDataSource();
      String url = getUrl(dataSource);
      if (urlSqlUtilMap.containsKey(url)) {
        return urlSqlUtilMap.get(url);
      }
      try {
        lock.lock();
        if (urlSqlUtilMap.containsKey(url)) {
          return urlSqlUtilMap.get(url);
        }
        if (StringUtils.isBlank(url)) {
          throw new RuntimeException("无法自动获取jdbcUrl，请在分页插件中配置dialect参数!");
        }
        String dialect = Dialect.fromJdbcUrl(url);
        if (dialect == null) {
          throw new RuntimeException("无法自动获取数据库类型，请通过dialect参数指定!");
        }
        SqlUtil sqlUtil = new SqlUtil(dialect);
        if (this.properties != null) {
          sqlUtil.setProperties(properties);
        } else if (this.sqlUtilConfig != null) {
          sqlUtil.setSqlUtilConfig(this.sqlUtilConfig);
        }
        urlSqlUtilMap.put(url, sqlUtil);
        return sqlUtil;
      } finally {
        lock.unlock();
      }
    }

    /**
     * 只拦截Executor.
     *
     * @param target target
     */
    @Override
    public Object plugin(Object target) {
      if (target instanceof Executor) {
        return Plugin.wrap(target, this);
      } else {
        return target;
      }
    }

    private void checkVersion() {
      // MyBatis3.2.0版本校验
      try {
        Class.forName("org.apache.ibatis.scripting.xmltags.SqlNode");// SqlNode是3.2.0之后新增的类
      } catch (ClassNotFoundException ex) {
        throw new RuntimeException("您使用的MyBatis版本太低，MyBatis分页插件PageHelper支持MyBatis3.2.0及以上版本!");
      }
    }

    /**
     * 设置属性值.
     *
     * @param p 属性值
     */
    @Override
    public void setProperties(Properties p) {
      checkVersion();
      // 多数据源时，获取jdbcurl后是否关闭数据源
      String closeConn = p.getProperty("closeConn");
      // 解决#97
      if (StringUtils.isBlank(closeConn)) {
        this.closeConn = Boolean.parseBoolean(closeConn);
      }
      // 初始化SqlUtil的PARAMS
      SqlUtil.setParams(p.getProperty("params"));
      // 数据库方言
      String dialect = p.getProperty("dialect");
      String runtimeDialect = p.getProperty("autoRuntimeDialect");
      if (StringUtils.isNotBlank(runtimeDialect) && runtimeDialect.equalsIgnoreCase("TRUE")) {
        this.autoRuntimeDialect = true;
        // this.autoDialect = false;
        this.properties = p;
      } else if (StringUtils.isBlank(dialect)) {
        // autoDialect = true;
        this.properties = p;
      } else {
        // autoDialect = false;
        sqlUtil = new SqlUtil(dialect);
        sqlUtil.setProperties(p);
      }
    }

    /**
     * 设置属性值.
     *
     * @param config config
     */
    public void setSqlUtilConfig(SqlUtilConfig config) {
      checkVersion();
      // 初始化SqlUtil的PARAMS
      SqlUtil.setParams(config.getParams());
      // 多数据源时，获取jdbcurl后是否关闭数据源
      this.closeConn = config.isCloseConn();
      if (config.isAutoRuntimeDialect()) {
        this.autoRuntimeDialect = true;
        // this.autoDialect = false;
        this.sqlUtilConfig = config;
      } else if (StringUtils.isBlank(config.getDialect())) {
        // autoDialect = true;
        this.sqlUtilConfig = config;
      } else {
        // autoDialect = false;
        sqlUtil = new SqlUtil(config.getDialect());
        sqlUtil.setSqlUtilConfig(config);
      }
    }
  }
——————————————————————————————————————————————————————————————————————————————————————————————————————————————————
——————————————————————————————————————————————————————————————————————————————————————————————————————————————————
/**
 * 日志拦截器.
 * 
 * @author louxl
 * @create 2015年6月29日
 *
 */

// @Component
public class LogInterceptor implements HandlerInterceptor {

  final Logger logger = LogManager.getLogger(LogInterceptor.class);

  @Override
  // 在 Controller 调用之前的拦截
  // request 请求， response 返回
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    // 如果是指纹方式登陆则直接返回 true 
    if (request.getRequestURI().contains("fingerprintLogin")) {
      return true;
    }

    String param = ""; // 新建参数字符串。

    for (String key : request.getParameterMap().keySet()) {
        
      int length = request.getParameterMap().get(key).length;
      if (length == 1) {
        param = param + key + "="
            + filterLongParam(request.getParameterMap().get(key)[0].toString()) + "&";
      } else {
        for (int i = 0; i < length; i++) {
          param = param + key + "[" + i + "]="
              + filterLongParam(request.getParameterMap().get(key)[i].toString()) + "&";
        }
      }
    }

    if (param.endsWith("&")) {
      param = param.substring(0, param.length() - 1);
    }

    logger.debug(request.getRequestURL().toString() + "?" + param);
    return true;
  }

  // 调用的内部方法
  private String filterLongParam(String param) {
    if (StringUtils.isNotBlank(param) && param.length() > 200) {
      param = "[参数太长自动隐藏]";
    }
    return param;
  }

  @Override
  // 拦截器的另一个方法 用在返回内容 在视图渲染的时候进行拦截  ModelAndView 的部分
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) throws Exception {}

  @Override
  // 主要做一些清理的工作
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
      Object handler, Exception ex) throws Exception {}
}

——————————————————————————————————————————————————————————————————————————————————————————————————————————————————
——————————————————————————————————————————————————————————————————————————————————————————————————————————————————
* 不清楚是什么的拦截器

public class ConfigPageInterceptor extends HandlerInterceptorAdapter {

  private String password;

  private final Logger logger = LoggerFactory.getLogger(ConfigPageInterceptor.class);

  public ConfigPageInterceptor() {
    // TODO Auto-generated constructor stub
  }

  public ConfigPageInterceptor(String password) {
    this.password = password;
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    if (!(handler instanceof HandlerMethod)) {
      return true;
    }
    HandlerMethod handlerMethod = null;
    try {
      handlerMethod = (HandlerMethod) handler;
    } catch (ClassCastException ex) {
      ex.printStackTrace();
      send301(response, request.getContextPath() + "/");
    }
    Method method = handlerMethod.getMethod();
    AccessRequired annotation = method.getAnnotation(AccessRequired.class);

    String prisonId = (String) request.getSession().getAttribute("prisonId");
    if (StringUtils.isBlank(prisonId)) {
      send301(response, request.getContextPath() + "/");
      return false;
    }

    if (annotation == null) {
      logger.debug("没有注解，不需要拦截，直接通过");
      return true;
    }

    boolean value = annotation.value();

    if (!value) {
      logger.debug("注解为不拦截，直接通过");
      return true;
    }

    if (annotation.type() == AccessTypeEnum.pageAccess) {
      String jcPassword = (String) request.getSession().getAttribute("jcPassword");
      if (StringUtils.isBlank(jcPassword) || !jcPassword.equals(password)) {
        send301(response, request.getContextPath() + "/");
        return false;
      }
    } else {
      logger.debug("不是页面请求，直接通过");
      return true;
    }

    return super.preHandle(request, response, handler);
  }

  private void send301(HttpServletResponse response, String redirectUrl) {
    response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
    response.setHeader("Location", redirectUrl);
    response.setHeader("Connection", "close");
  }

}
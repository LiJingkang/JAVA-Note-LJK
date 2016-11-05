SSH

配色文件
https://github.com/ChrisKempson/Tomorrow-Themehttps://github.com/kenwheeler/brogrammer-theme/archive/master.zip
———————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
* 初始化的时候调用的注解
    @PostConstruct
    @PreDestroy
        从Java EE 5规范开始，Servlet中增加了两个影响 Servlet生命周期的注解（Annotion）；
        @PostConstruct 和 @PreDestroy。
        这两个注解被用来修饰一个 非静态的void()方法 。
        * 写法有如下两种方式：
            @PostConstruct
            Public void someMethod() {}

            public @PostConstruct void someMethod(){}
    
    被 @PostConstruct修饰的方法 
        会在服务器 加载Servle的时候运行，并且只会被服务器执行一次。

        PostConstruct在构造函数之后执行,init()方法之前执行。
        PreDestroy（）方法在destroy()方法执行执行之后执行


SpringMVC

* 项目结构

	web ---> 拦截器拦截 
	    ---> 获得url，按照注解的url分配controller    
	    ---> controller通过注入得到拦截的数据     
	    ---> 请求Serivce    
	    ---> Serivce再请求业务层-操作数据库(dao.oracal)     
	    ---> dao通过自己的provider获得SEL语句     
	    ---> 使用SEL语句得到数据返回给控制器    
	    ---> 控制器将返回的数据放入ResponseBody中，拼接成一个JSON

* 注解
	@Component
		// 指组件，当组件不好归类的时候，我们可以使用这个注解进行标注
	@Controller
	@Service
	@Repository
		// 应数据访问层Bean
	@Scope
	    用于指定scope 
	    // 用于指定scope作用域的（用在类@Autowired可以对成员变量、方法和构造函数进行标注，来完成自动装配的工作。
	@Autowired
		使用来消除 set ，get方法。
	@Autowire
	@Qualifier("personDaoBean") 
	    存在多个实例配合使用
	@Resource
	    默认按名称装配，当找不到与名称匹配的bean才会按类型装配。
	@RequestMapping
	@RequestBody
	    将Controller的方法返回的对象，通过适当的HttpMessageConverter转化为指定格式后，写入到Response对象的body数据区
	@RequestParam
	@RequestParam(defaultValue = "terminal") // 默认的数据是什么
	@RequestParam(required = true) 
		// 会强制要求添加参数 	// 如果没有提供参数，将会报错拦截
	    把请求参数，传递给请求刚发 // 注入	
	@Override
	    表示重写(当然不写也可以),不过写上有如下好处:
	    可以当注释用,方便阅读
	    编译器可以给你验证@Override下面的方法名是否是你父类中所有的,如果没有则报错
	    ** 例子
			  @Override
			  public String getNumber() {
			    return number;
			  }    
	@Transactional 		// 翻译,相互作用的
	@Transactional("criminalTransactionManager")
	@Transactional(propagation = Propagation.REQUIRED)
	@Transactional(isolation = Isolation.DEFAULT)
	    提供一种控制事务管理的快捷手段,声明这个service所有方法需要事务管理。每一个业务方法开始时都会打开一个事务。 

		@Transactional 注解可以标注在类和方法上，也可以标注在定义的接口和接口方法上。
		如果我们在接口上标注 @Transactional 注解，会留下这样的隐患：
				因为注解不能被继承，所以业务接口中标注的 @Transactional 注解不会被业务实现类继承。
				所以可能会出现不启动事务的情况。所以，Spring 建议我们将 @Transaction 注解在实现类上。
		在方法上的 @Transactional 注解会覆盖掉类上的 @Transactional。 
		// 对数据库进行回滚，保证原子性
	@Validated
	    是@Valid的一次封装@Valid 
	    必须使用@Valid标注我们需要校验的参数user，
	    // 否则Spring不会对它进行校验
	@Valid
		// 使用Vaild标注的属性 会进行效验 
		public boolean saveTodayFocusedEstimate(@Valid Estimate estimate, BindingResult result)
		// 在Estimate中的注解
			@XStreamAlias("Estimate")
			@JsonInclude(Include.NON_NULL)
                // NULL 无法判断诸如  为空的 List<Class> 这种东西
            @JsonInclude(Include.NON_EMPTY)
                // 可以判断
		// 进行判断
			@NotBlank(message = "民警编号不能为空！")
	
	@NotEmpty // 非空
	    用在集合类上面	// 集合类 
	@NotBlank // 非空@NotBlank(message = "dormCode is null")
	    用在String上面
	@NotNull // 非空
	    用在基本类型上

	@XStreamAlias("ComePrisonLocus")
		// 序列化别名
	@XStreamAlias("message")  // 支持转xml 
	@XStreamAlias("Spy") // "" 和类名一样
	    别名注解@XStreamImplicit 隐式集合
	@XStreamImplicit("part") 
	    作用目标: 集合字@XStreamConverter
	    (SingleValueCalendarConverter.class) 注入转换器 
	    作用目标: 对象 
	@XStreamAsAttribute 转换成属性 
	    作用目标: 字段 
	@XStreamOmitField 忽略字段 
	    作用目标: 字段 


	@AccessRequired  
		@AccessRequired(value = false, type = AccessTypeEnum.pageAccess)
		java中，自定义注解拦截器来实现，在需要的拦截的方法上面加上一个注解
                /**
                 * 注解拦截器方法
                 * @return
                 */
                @RequestMapping(value="/urlinter",method=RequestMethod.GET)
                @AccessRequired
                public @ResponseBody String urlInterceptorTest() {
                    return "通过拦截器:user"+request.getAttribute("currUser");
                }

	@ModelAttribut
	    在调用目标处理方法前，会先调用在方法上标注了@ModelAttribut的方法

	@SessionAttributes
	@InitBinder

	@XStreamAlias("BreakRule")
	@JsonInclude(Include.NON_NULL)
	
  	@Min(1)
  	@Max(3)
	  	// 页面单选按钮 默认个人分 监室分：1 ,个人分:2 ,监室和个人分3
  	@NotNull(message = "扣分类型不能为空！")
	  	private Integer deductObject;

	@Schedule
			* 例子
				@Scheduled(fixedDelay=5000)
				public void doSomething() {
				// something that should execute periodically
				}
			* 例子
				@Scheduled(initialDelay=1000, fixedRate=5000)
				public void doSomething() {
				// something that should execute periodically
				}


  	@DateTimeFormat
		  	* 例子
				public PageInfo<ChatRecordDto> search(
					@RequestParam(required = true) String prisonId,
					String policeId, String chatType, String dormCode,
					@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startTime,
					@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime, 
					Pageable pageable)
	@Async
		* 会自动安排子线程啦执行这个任务
		* 和 thread 函数的功能相同，自动进行控制 // 代码重构
		* 例子
	  	private void saveSwipeRecord(String cardId, String dormCode, String prisonId, PrisonPeopleInfo prisoner) {
		  try{
			  swipeRecordService.addSwipeRecord(cardId, prisoner == null ? "" : prisoner.getNumber(),
	          SwipeRecord.SWIPE_TYPE_PRISONER_SIGNIN, dormCode, prisonId);
		  }catch(Exception e){
			  e.printStackTrace();
		  }
	  	}

  	@JsonInclude(Include.NON_NULL)
  		* 添加以后在输出的时候如果键值对为空，则不输出这个属性
———————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
Mybaits

* 注解

		// 全部集中在这个testMapper　
	@SelectProvider
	@SelectProvide(type = DailyDutyDynaSqlProvider.class, method = "findDailyDutysSql")
	    // 用于生成查询用的sql语句，有别于@Select注解
	    // 指定一个Class及其方法，并且通过调用Class上的这个方法来获得sql语句
	    // 以用@Select代替
	@Select
		@Select("select * from mybatis_grade where id=#{id}")

	@Options
		@Options(useCache = true, flushCache = false, timeout = 10000) 
	    // 一些查询的选项开关
	    // useCache = true表示本次查询结果被缓存以提高下次查询速度，
	    // flushCache = false表示下次查询时不刷新缓
	    // timeout = 10000表示查询结果缓存10000秒。
	@Results
	    // 表示sql查询返回的结果集
	    // @Results是以@Result为元素的数组@Result
	    // @Result表示单条属性-字段的映射关系
		@Result(id = true, property = "id", 
				column = "test_id", 	// columns 列，列号
				javaType = String.class, 
				jdbcType = JdbcType.VARCHAR)
		@Result(id = true, property = "id", 
				column = "test_id")
		@Result(column = "DJSJ", 
				property = "date", 
				typeHandler = DateTypeHandler.class),
	    // id = true表示这个test_id字段是个PK，查询时mybatis会给予必要的优化
	    @Result(property = "options", 
	    		javaType = List.class, 
	    		column = "ID",
	    		many = @Many(select = "getOptionsByQuestionId") )})

	@Many
	
	@ResultType
	@ResultType(BreakRuleOriginal.class)
		// resultType 就是返回的类型
		// resultMap 返回的是一个结果集，这个结果集一般是用过resultMap节点来配置的，相应的type一般是一个Model。
		// 而resultType则就是类型，包括了，int,sring,以及类似model这样的Object类型。 

	@Param
	@Param("id")
	    // 全局限定别名 
	   // 定义查询参数在sql语句中的位置不再是顺序下标0,1,2,3....的形式，而是对应名称，该名称就在这里定义。
	@CacheNamespace(size = 512) // 定义在该命名空间内允许使用内置缓存
	
	@ResultMap
	@ResultMap(value = "getByTestText")
	@ResultMap("goodsResultMap")
		// goodsResultMap 由@Results(id="goodsResultMap", value={@Result(colume = "", property = "")})
		// 来定义，相当于写了@Results 这一部分
	    用于从查询结果集RecordSet中取数据然后拼装实体bean。
		// 例子
		@SelectProvider
		@Options
		@Results(value = {
		    @result(id = true,property =)
		     })

  	@InsertProvider(type = LetterDynaSqlProvider.class, method = "insertLetterSql")
  		public void insertLetter(@Param("letter") Letter letter);
	@Delete("Delete  from KSSXJSFDJB where zybh=#{workId}")
		public void deleteLetter(@Param("workId") String workId);

	@NotBlank(message = "姓名不能为空！")
	@Length(max = 8, min = 8, message = "提讯开始时间格式为yyyyMMdd！")

		// @Select("select * from ZYRYSPMXB")
	@ResultMap("foodsMenuResultMap")
		public List<FoodsMenu> getAll();

	@InitBinder
		// 在SpringMVC中，bean中定义了Date，double等类型，如果没有做任何处理的话，日期以及double都无法绑定。
		// 解决的办法就是使用spring mvc提供的 @InitBinder标签
		// 在BaseController中增加方法initBinder，并使用注解 @InitBinder 标注，
		// 那么spring mvc在绑定表单之前，都会先注册这些编辑器，
		// 当然你如果不嫌麻烦，你也可以单独的写在你的每一个controller中。剩下的控制器都继承该类。
		// spring自己提供了大量的实现类，诸如 CustomDateEditor ，CustomBooleanEditor，CustomNumberEditor等
		* 举个例子
			  	@InitBinder
				public void dataBinder(WebDataBinder dataBinder) {
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					dataBinder.registerCustomEditor(Date.class, new CustomDateEditor(df, true));
				}
	@Target
	@Target({ElementType.METHOD})
		@Target 是Java的元注解（指修饰注解的注解）之一。用来指定注解修饰类的哪个成员。
			加大括号表示一个数组，指被修饰的注解能用于多个不同的类成员。
		* 举个栗子：
			@Target(ElementType.FIELD)
			public @interface A{}
				表示注解A只能用来修饰类中的Field
			@Target({ElementType.FIELD, ElementType.METHOD})
			public @interface A{}
				表示注解A能用来修饰类中的Field和Method
	@Retention
	@Retention(RetentionPolicy.RUNTIME)
		* etention(保留)注解说明,这种类型的注解会被保留到那个阶段. 有三个值:
			RetentionPolicy.SOURCE —— 
				这种类型的Annotations只在源代码级别保留,编译时就会被忽略
			RetentionPolicy.CLASS —— 
				这种类型的Annotations编译时被保留,在class文件中存在,但JVM将会忽略
			RetentionPolicy.RUNTIME ——  // 使用比较多
				这种类型的Annotations将被JVM保留,
				所以他们能在运行时被JVM或其他使用反射机制的代码所读取和使用.
		* RetentionPolicy.RUNTIME 的声明:
				@Retention(RetentionPolicy.RUNTIME)
				public @interface Test_Retention {
				   String doTestRetention();
				}
				// @Retention(RetentionPolicy.RUNTIME)注解表明 
				// Test_Retention注解将会由虚拟机保留,以便它可以在运行时通过反射读取.
	@Documented
		* Documented 注解表明这个注解应该被 javadoc工具记录. 
			默认情况下,javadoc是不包括注解的. 
		* 但如果声明注解时指定了 @Documented,则它会被 javadoc 之类的工具处理, 
			所以注解类型信息也会被包括在生成的文档中. 
	@Mapping
———————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
	@Controller
      	例如
            @Controller
            public class SoftCreateController extends SimpleBaseController {}
      	或者
            @Controller("softCreateController")
      	说明
            @Controller 
              // 负责注册一个bean 到spring 上下文中，bean 的ID 默认为类名称开头字母小写
	@Service
      	例如
            @Service
            public class SoftCreateServiceImpl implements ISoftCreateService {}
      	或者
            @Service("softCreateServiceImpl")
      	说明
            @Service 
                // 负责注册一个bean 到spring 上下文中，bean 的ID 默认为类名称开头字母小写
	@Autowired
      	例如
            @Autowired
            private ISoftPMService softPMService;
      	或者
            @Autowired(required=false)
            private ISoftPMService softPMService = new SoftPMServiceImpl();
      	说明
            @Autowired 
                // 根据bean 类型从spring 上线文中进行查找，注册类型必须唯一，否则报异常。
                // 与@Resource 的区别在于，@Resource 允许通过bean 名称或bean 类型两种方式进行查找
            @Autowired(required=false) 表示，如果spring 上下文中没有找到该类型的bean 时， 才会使用new SoftPMServiceImpl();
            @Autowired 标注作用于 Map 类型时，如果 Map 的 key 为 String 类型，
                则 Spring 会将容器中所有类型符合 Map 的 value 对应的类型的 Bean 增加进来，
                用 Bean 的 id 或 name 作为 Map 的 key。
            @Autowired 还有一个作用就是，如果将其标注在 BeanFactory 类型、ApplicationContext 类型、
                ResourceLoader 类型、ApplicationEventPublisher 类型、MessageSource 类型上，
                那么 Spring 会自动注入这些实现类的实例，不需要额外的操作。

	@RequestMapping
      	类
            @Controller 
            @RequestMapping("/bbtForum.do")
            public class BbtForumController {
                         @RequestMapping(params = "method=listBoardTopic")
            public String listBoardTopic(int topicId,User user) {}
            }
 
      	方法
            @RequestMapping("/softpg/downSoftPg.do")
            @RequestMapping(value="/softpg/ajaxLoadSoftId.do",method = POST)
            @RequestMapping(value = "/osu/product/detail.do", params = { "modify=false" }, method =POST)
      	说明
            @RequestMapping 
            @RequestMapping(
                  value = "addPage/{personId},{fullname},{alias},{birthDate},{sex},{designation},{prsNumber},{perid}",
                  method = RequestMethod.GET)
            @RequestMapping(value = "addconPage/{personId}", method = RequestMethod.GET)

              // 可以声明到类或方法上

      	参数绑定说明
            如果我们使用以下的 URL 请求：
                http://localhost/bbtForum.do?method=listBoardTopic&topicId=1&userId=10&userName=tom
            topicId URL 参数将绑定到 topicId 入参上，而 
            userId 和 userName URL 参数将绑定到 user 对象的 userId 和 userName 属性中。
            和 URL 请求中不允许没有 topicId 参数不同，虽然 User 的 userId 属性的类型是基本数据类型，
            但如果 URL 中不存在 userId 参数，Spring 也不会报错，此时 user.userId 值为 0 。
            如果 User 对象拥有一个 dept.deptId 的级联属性，那么它将和 dept.deptId URL 参数绑定。

	@RequestParam
      	参数绑定说明
            @RequestParam("id")
                    http://localhost/bbtForum.do?method=listBoardTopic&id=1&userId=10&userName=tom
                listBoardTopic(@RequestParam("id")int topicId,User user) 中的 
                topicId 绑定到 id 这个 URL 参数， 那么可以通过对入参使用 @RequestParam 注解来达到目的
            @RequestParam(required=false)：参数不是必须的，默认为true
            @RequestParam(value="id",required=false)

      	请求处理方法入参的可选类型
      	Java 基本数据类型和 String
            默认情况下将按名称匹配的方式绑定到 URL 参数上，可以通过 @RequestParam 注解改变默认的绑定规则
            request/response/session
            既可以是 Servlet API 的也可以是 Portlet API 对应的对象，Spring 会将它们绑定到Servlet 和 Portlet 容器的相应对象上
      	org.springframework.web.context.request.WebRequest
            内部包含了 request 对象
      	java.util.Locale
            绑定到 request 对应的 Locale 对象上
      	java.io.InputStream/java.io.Reader
            可以借此访问 request 的内容
      	java.io.OutputStream / java.io.Writer
            可以借此操作 response 的内容
      	任何标注了 @RequestParam 注解的入参
            被标注 @RequestParam 注解的入参将绑定到特定的 request 参数上。
      	java.util.Map / org.springframework.ui.ModelMap
            它绑定 Spring MVC 框架中每个请求所创建的潜在的模型对象，它们可以被 Web 视图对象访问（如 JSP ）
      	命令/ 表单对象（注：一般称绑定使用 HTTP GET 发送的 URL 参数的对象为命令对象，而称绑定使用HTTP POST 发送的 URL 参数的对象为表单对象）
            它们的属性将以名称匹配的规则绑定到 URL 参数上，同时完成类型的转换。
            而类型转换的规则可以通过 
            @InitBinder 注解或通过 HandlerAdapter 的配置进行调 整
      	org.springframework.validation.Errors / org.springframework.validation.BindingResult
            为属性列表中的命令/ 表单对象的校验结果，注意检验结果参数必须紧跟在命令/ 表单对象的后面
      	org.springframework.web.bind.support.SessionStatus
             可以通过该类型 status 对象显式结束表单的处理，这相当于触发 session 清除其中的通过@SessionAttributes 定义的属性
 
      	请求处理方法返回值的可选类型
      	void

	ModelAndView
      	当然还可以是传统的 ModelAndView 。

	ModelAttribute
      	作用域：request
      	例如
            @RequestMapping("/base/userManageCooper/init.do")
            public String handleInit(@ModelAttribute("queryBean") ManagedUser sUser,Model model,){
      	或者
            @ModelAttribute("coopMap")// 将coopMap 返回到页 面
            public Map<Long,CooperatorInfo> coopMapItems(){}
      	说明
            @ModelAttribute 声明在属性上，表示该属性的value 来源于model 里"queryBean" ，
                并被保存到model 里@ModelAttribute 声明在方法上，表示该方法的返回值被保存到model 里

	@Cacheable
	@CacheFlush
      	@Cacheable ：声明一个方法的返回值应该被缓 存
        	例如：
             	@Cacheable(modelId = "testCaching")
      	@CacheFlush ：声明一个方法是清空缓存的触发器
        	例如：
            	@CacheFlush(modelId = "testCaching")
				@Cacheable(value = "globalConfigCache")
      	说明
            	要配合缓存处理器使用，参考： http://hanqunfeng.iteye.com/blog/603719

	@Resource
      	例如
            @Resource
            private DataSource dataSource; // inject the bean named 'dataSource'
      	或者
            @Resource(name="dataSource")
            @Resource(type=DataSource.class)
      	说明
            @Resource 默认按bean 的name 进行查找，如果没有找到会按type 进行查找，此时与@Autowired 类 似
            在没有为 @Resource 注解显式指定 name 属性的前提下，如果将其标注在 
                BeanFactory 类型、ApplicationContext 类型、ResourceLoader 类型、
                ApplicationEventPublisher 类型、MessageSource 类型上，
            那么 Spring 会自动注入这些实现类的实例，不需要额外的操作。
            此时 name 属性不需要指定 ( 或者指定为"")，否则注入失败；

	@PostConstruct
    	在方法上加上注解 @PostConstruct ，这个方法就会在 Bean 初始化之后被Spring 容器执行
    	（注：Bean 初始化包括，实例化 Bean ，并装配 Bean 的属性（依赖注入））。

	@PreDestroy
     	在方法上加上注解@PreDestroy ，这个方法就会在Bean 被销毁前被Spring 容器执行。

	@Repository
		与@Controller 、@Service 类似，都是向spring 上下文中注册bean ，不在赘述。

	@Component
		@Component 是所有受Spring 管理组件的通用形式，Spring 还提供了更加细化的注解形式：  
		@Repository 、@Service 、@Controller ，它们分别对应存储层Bean ，业务层Bean ，和展示层Bean 。
			目前版本（2.5 ）中，这些注解与@Component 的语义是一样的，完全通用， 在Spring 以后的版本中可能会给它们追加更多的语义。 
			所以，我们推荐使用@Repository 、@Service 、@Controller 来替代@Component 。

	@Scope
       	例如
	        @Scope("session")
	        @Repository()
	        public class UserSessionBean implementsSerializable {}
      	说明
			在使用XML 定义Bean 时，可以通过bean 的scope 属性来定义一个Bean 的作用范围，
			同样可以通过@Scope 注解来完成

       	@Scope中可以指定如下值：
			singleton:定义bean的范围为每个spring容器一个实例（默认值）
			prototype:定义bean可以被多次实例化（使用一次就创建一次）
			request:定义bean的范围是http请求（springMVC中有效）
			session:定义bean的范围是http会话（springMVC中有效）
			global-session:定义bean的范围是全局http会话（portlet中有效）
 
	@SessionAttributes
		说明
			Spring 允许我们有选择地指定 ModelMap 中的哪些属性需要转存到 session 中，
			以便下一个请求属对应的 ModelMap 的属性列表中还能访问到这些属性。
			这一功能是通过类定义处标注 @SessionAttributes 注解来实现的。
		@SessionAttributes 只能声明在类上，而不能声明在方法上。 
		例如
			@SessionAttributes("currUser") // 将ModelMap 中属性名为currUser 的属性
			@SessionAttributes({"attr1","attr2"})
			@SessionAttributes(types = User.class)
			@SessionAttributes(types = {User.class,Dept.class})
			@SessionAttributes(types = {User.class,Dept.class},value={"attr1","attr2"})

	@InitBinder
		说明
			如果希望某个属性编辑器仅作用于特定的 Controller ，
			可以在 Controller 中定义一个标注 @InitBinder 注解的方法，
			可以在该方法中向 Controller 了注册若干个属性编辑器
 		例如
			@InitBinder
			public void initBinder(WebDataBinder binder) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				dateFormat.setLenient(false);
				binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
				}
	@Required
    	例如
			@required              
            public  setName(String name){} 
        说明
			@ required 负责检查一个bean在初始化时其声明的 set方法是否被执行， 
			当某个被标注了 @Required 的 Setter 方法没有被调用，则 Spring 在解析的时候会抛出异常，以提醒开发者对相应属性进行设置。 
			@Required 注解只能标注在 Setter 方法之上。
			因为依赖注入的本质是检查 Setter 方法是否被调用了，而不是真的去检查属性是否赋值了以及赋了什么样的值。
			如果将该注解标注在非 setXxxx() 类型的方法则被忽略。
	@Qualifier
		例如
			@Autowired
			@Qualifier("softService")
			private ISoftPMService softPMService; 
		说明
            使用@Autowired 时，如果找到多个同一类型的bean，则会抛异常，
            此时可以使用 @Qualifier("beanName")，明确指定bean的名称进行注入，
            此时与 @Resource指定name属性作用相同。

    @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
        说明
            加在带有孩子节点的实体类上面
——————————————————————————————————————————————————————————————————————
	@RequiresPermissions 
	@RequiresPermissions("ConversationRecord:view")
    	例子
	    	@RequiresPermissions({"file:read", "write:aFile.txt"} )
			voidsomeMethod();
		说明
       		要求subject中必须同时含有 file:read 和 write:aFile.txt 的权限
       		才能执行方法someMethod()。否则抛出异常AuthorizationException。
	@RequestMapping
  	RequestMapping(value = "{queryString}/list", method = RequestMethod.GET)

	@PathVariable
	@PathVariable("queryString")
		例子
			@RequestMapping("/bookings/{booking}")
			public String getBooking(@PathVariable Long booking) {
				} 
		说明
			将 request 里的参数的值绑定到 contorl 里的方法参数里的，区别在于，url不同 
			@PathVariable的 url 是这样的:http://host:port/.../path/参数值 
		例子
			public String psnBaseInfoPage(@PathVariable("personId") String personId,
			@PathVariable("stringData") String stringData, Map<String, Object> map) 
			{
				map.put("personId", personId);
				String backUrl = "/management/dedrug/Discipline/socialRelations/" + stringData + "/backlist";
				map.put("backUrl", backUrl);
				return INFOPAGE;
			}
	@RequestParam
		例子
			@RequestMapping(method = RequestMethod.GET)
		    public String setupForm(@RequestParam("petId") int petId, ModelMap model) {
		        Pet pet = this.clinic.loadPet(petId);
		        model.addAttribute("pet", pet);
		        return "petForm";
			    } 
		说明
			@RequestParam的url是这样的：http://host:port/.../path?参数名=参数值 
———————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
* hibernate 注解
	@Entity

	@Table(name = "jdsryjbxxb")

	@Id
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@GeneratedValue(generator = "system-uuid")

	@Column(name = "JTSF", length = 1)

	@Column(name = "SFHCSJ", length = 14)
	@Temporal(TemporalType.DATE)

———————————————————————————————————————————————————————————————————————————————————————————————————————————————————————

	@Entity
		//继承策略。另一个类继承本类，那么本类里的属性应用到另一个类中
	@Inheritance(strategy = InheritanceType.JOINED ) 
	@Table(name="INFOM_TESTRESULT")
		public class TestResult extends IdEntity{}

	@Entity(name="EntityName")
		必须,name为可选,对应数据库中一的个表

	@Table(name="",catalog="",schema="")
		可选,通常和@Entity配合使用,只能标注在实体的class定义处,表示实体对应的数据库表的信息
		name:
			可选,表示表的名称.默认地,表名和实体名称一致,只有在不一致的情况下才需要指定表名
		catalog:
			可选,表示Catalog名称,默认为Catalog("").
		schema:
			可选,表示Schema名称,默认为Schema("").

	@id
		必须
		@id定义了映射到数据库表的主键的属性,一个实体只能有一个属性被映射为主键.
		置于getXxxx()前.

	@GeneratedValue(strategy=GenerationType,generator="")
		可选
		strategy:
			表示主键生成策略,有AUTO,INDENTITY,SEQUENCE 和 TABLE 4种,
			分别表示让ORM框架自动选择,
				根据数据库的Identity字段生成,
				根据数据库表的Sequence字段生成,
				以有根据一个额外的表生成主键,默认为AUTO
		generator:
			表示主键生成器的名称,这个属性通常和ORM框架相关,
			例如,Hibernate可以指定uuid等主键生成方式.
		示例:
		     @Id
		     @GeneratedValues(strategy=StrategyType.SEQUENCE)
		     public int getPk() {
		       return pk;
		     }

	@Basic(fetch=FetchType,optional=true)
		可选
			@Basic表示一个简单的属性到数据库表的字段的映射,
			对于没有任何标注的getXxxx()方法,默认即为@Basic
		fetch: 
			表示该属性的读取策略,有EAGER和LAZY两种,分别表示主支抓取和延迟加载,默认为EAGER.
		optional:
			表示该属性是否允许为null,默认为true
		示例:
		     @Basic(optional=false)
		     public String getAddress() {
		       return address;
		     }

	@Column
		可选
			@Column描述了数据库表中该字段的详细定义,
			这对于根据JPA注解生成数据库表结构的工具非常有作用.
		name:
			表示数据库表中该字段的名称,默认情形属性名称一致
		nullable:
			表示该字段是否允许为null,默认为true
		unique:
			表示该字段是否是唯一标识,默认为false
		length:
			表示该字段的大小,仅对String类型的字段有效
		insertable:
			表示在ORM框架执行插入操作时,该字段是否应出现INSETRT语句中,默认为true
		updateable:
			表示在ORM框架执行更新操作时,该字段是否应该出现在UPDATE语句中,默认为true.
			对于一经创建就不可以更改的字段,该属性非常有用,如对于birthday字段.
		columnDefinition:
			表示该字段在数据库中的实际类型.通常ORM框架可以根据属性类型自动判断数据库中字段的类型,
			但是对于Date类型仍无法确定数据库中字段类型究竟是DATE,TIME 还是 TIMESTAMP.
			此外,String的默认映射类型为VARCHAR,
			如果要将String类型映射到特定数据库的BLOB或TEXT字段类型,该属性非常有用.
		示例:
		     @Column(name="BIRTH",nullable="false",columnDefinition="DATE")
		     public String getBithday() {
		       return birthday;
		     }

	@Transient
		可选
			@Transient表示该属性并非一个到数据库表的字段的映射,
			ORM框架将忽略该属性.
			如果一个属性并非数据库表的字段映射,就务必将其标示为@Transient,
			否则,ORM框架默认其注解为@Basic
		示例:
		     //根据birth计算出age属性
		     @Transient
		     public int getAge() {
		       return getYear(new Date()) - getYear(birth);
		     }

	@ManyToOne(fetch=FetchType,cascade=CascadeType)
		可选
			@ManyToOne表示一个多对一的映射,该注解标注的属性通常是数据库表的 '外键'
		optional:
			是否允许该字段为null,该属性应该根据数据库表的外键约束来确定,默认为true
		fetch:
			表示抓取策略,默认为FetchType.EAGER
		cascade:
			表示默认的级联操作策略,可以指定为ALL,PERSIST,MERGE,REFRESH和REMOVE中的若干组合,默认为无级联操作
		targetEntity:
			表示该属性关联的实体类型.该属性通常不必指定,ORM框架根据属性类型自动判断targetEntity.
		示例:
			//订单Order和用户User是一个ManyToOne的关系
			//在Order类中定义
			@ManyToOne()
			@JoinColumn(name="USER")
			public User getUser() {
			return user;
			}

	@JoinColumn
		可选
		@JoinColumn和@Column类似,介量描述的不是一个简单字段,而一一个关联字段,
			例如.描述一个@ManyToOne的字段.
		name:
			该字段的名称.由于@JoinColumn描述的是一个关联字段,如ManyToOne,
			则默认的名称由其关联的实体决定.
			例如,实体Order有一个user属性来关联实体User,则Order的user属性为一个外键,
			其默认的名称为实体User的名称+下划线+实体User的主键名称
		示例:
     		见@ManyToOne

	@OneToMany(fetch=FetchType,cascade=CascadeType)
		可选
		@OneToMany描述一个一对多的关联,该属性应该为集体类型,在数据库中并没有实际字段.
		fetch:
			表示抓取策略,默认为FetchType.LAZY,因为关联的多个对象通常不必从数据库预先读取到内存
		cascade:
			表示级联操作策略,对于OneToMany类型的关联非常重要,通常该实体更新或删除时,其关联的实体也应当被更新或删除
		例如:
			实体User和Order是OneToMany的关系,则实体User被删除时,其关联的实体Order也应该被全部删除
		示例:
			@OneTyMany(cascade=ALL)
			public List getOrders() {
			return orders;
			}

	@OneToOne(fetch=FetchType,cascade=CascadeType)
		可选
		@OneToOne描述一个一对一的关联
		fetch:
			表示抓取策略,默认为FetchType.LAZY
		cascade:
			表示级联操作策略
		示例:
			@OneToOne(fetch=FetchType.LAZY)
			public Blog getBlog() {
			return blog;
			}

	@ManyToMany
		可选
		@ManyToMany 描述一个多对多的关联.多对多关联上是两个一对多关联,
		但是在ManyToMany描述中,中间表是由ORM框架自动处理
		targetEntity:
			表示多对多关联的另一个实体类的全名,例如:package.Book.class
		mappedBy:
			表示多对多关联的另一个实体类的对应集合属性名称
		示例:
     		User实体表示用户,Book实体表示书籍,
     		为了描述用户收藏的书籍,可以在User和Book之间建立ManyToMany关联
			 @Entity
			 public class User {
			   private List books;
			   @ManyToMany(targetEntity=package.Book.class)
			   public List getBooks() {
			       return books;
			   }
			   public void setBooks(List books) {
			       this.books=books;
			   }
			 }

		     @Entity
		     public class Book {
		       private List users;
		       @ManyToMany(targetEntity=package.Users.class, mappedBy="books")
		       public List getUsers() {
		           return users;
		       }
		       public void setUsers(List users) {
		           this.users=users;
		       }
		     }

		两个实体间相互关联的属性必须标记为@ManyToMany,并相互指定targetEntity属性,
		需要注意的是,有且只有一个实体的@ManyToMany注解需要指定mappedBy属性,
		指向targetEntity的集合属性名称
		利用ORM工具自动生成的表除了User和Book表外,
		还自动生成了一个User_Book表,用于实现多对多关联

	@MappedSuperclass
		可选
		@MappedSuperclass可以将超类的JPA注解传递给子类,使子类能够继承超类的JPA注解
		示例:
		     @MappedSuperclass
		     public class Employee() {
		       ....
		     }

		     @Entity
		     public class Engineer extends Employee {
		       .....
		     }
		     @Entity
		     public class Manager extends Employee {
		       .....
		     }

	@Embedded
		可选
			@Embedded将几个字段组合成一个类,并作为整个Entity的一个属性.
			例如User包括id,name,city,street,zip属性.
			我们希望city,street,zip属性映射为Address对象.这样,User对象将具有id,name和address这三个属性.
			Address对象必须定义为@Embededable
		示例:
		     @Embeddable
		     public class Address {city,street,zip}
		     @Entity
		     public class User {
		       @Embedded
		       public Address getAddress() {
		           ..........
		       }
		     }

Hibernate验证注解
	@Pattern
		String
		通过正则表达式来验证字符串
		@attern(regex=”[a-z]{6}”)
	@Length
		String
		验证字符串的长度
		@length(min=3,max=20)
	
	@Email
		String
		验证一个Email地址是否有效
		@email
	@Range
		Long
		验证一个整型是否在有效的范围内
		@Range(min=0,max=100)
	@Min
		Long
		验证一个整型必须不小于指定值
		@Min(value=10)
	@Max
		Long
		验证一个整型必须不大于指定值
		@Max(value=20)
	@Size
		集合或数组
		集合或数组的大小是否在指定范围内
		@Size(min=1,max=255)

	以上每个注解都可能性有一个message属性，
	用于在验证失败后向用户返回的消息，还可以三个属性上使用多个注解
———————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
	@PersistenceContext
		private EntityManager em;
		注入的是实体管理器，执行持久化操作的，需要配置文件persistence.xml。
	@PersistenceContext
		则是注入一坨保存实体类状态的数据结构，
		针对实体类的不同状态(四种,managedh或detached等)可以
		做出不同的反应(merge,persist等等)，其实就是把数据从数据库里提出，
		然后在内存里处理的，再返回数据库的法则。
	@Resource
		是注入容器提供的资源对象，比如SessionContext MessageDrivenContext。
		或者你那个name指定的JNDI对象
		@Resource可以理解为资源->数据源->也就是数据连接，
			基本上就是告诉程序数据库在哪里
	@Query		
		@Query("select count(*) from DedrugBasicInfo a where a.entryCass=?")
		public int queryprsId(String prsNumber);
			// 通过 prsNumber 来查询数据库 
	@Log(message = "修改了id={0}的关系人的信息。")
        // 应该是返回的提示信息或者日志
        // 在信息系统 2.0 里面，带 Log 注解的部分都有这个方法。
        LogUitls.putArgs(LogMessageObject.newWrite().setObjects(new Object[] {id}));
        return conversationRecordService.delete(id);
	

	@WebAppConfiguration
		注解
		PrisonerServiceTest.java
		好像可以用来找到配置文件
———————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
* 配置注解
    @Configuration
        @Configuration注解该类，等价 与XML中配置beans；用
    @Bean
    @Bean(name = "criminalSqlSessionFactory")
        @Bean标注方法等价于XML中配置bean。
        // 这样我们就可以使用bean了
        ApplicationContext ctx = new ClassPathXmlApplicationContext("application-config.xml");
        OrderService orderService = (OrderService) ctx.getBean("orderService");  

        JavaConfigApplicationContext ctx = new JavaConfigApplicationContext(ApplicationConfig.class);  
        OrderService orderService = ctx.getBean(OrderService.class);  


    @EnableCaching

    @Primary
        spring 根据类型无法选择到底注入哪一个。这个时候@Primay 可以闪亮登场了。

    @MapperScan(value = "com.newings.dao.oracle", sqlSessionFactoryRef = "criminalSqlSessionFactory")
    @PropertySource("classpath:config.properties")


    @Bean
        Binding triggerBinding(@Qualifier("triggerQueue") Queue triggerQueue,
        @bean 是注解在类上面的 声明这是个bean，不用去xml配置什么<bean id="xxx" cla
        @configuration也是注解在类上面的，声明这是个配置作用的bean，替代xml配置

        * 告诉 spring 这个方法的返回值是一个 bean
            然后在需要的这个 bean 的地方把他注入过去

    @Qualifier     
        // 按照名字进行装配   
    @Qualifier("triggerExchange") TopicExchange triggerExchange)
        * 例如定义一个交通工具类：
            Vehicle，以及它的子类Bus和Sedan。
        * 如果用@Autowired来找Vehicle的话，会有两个匹配的选项Bus和Sedan。为了限定选项，可以象下面这样。
            @Autowired
            @Qualifier("car")
            private Vehicle vehicle;
        * 如果要频繁使用
            @Qualifier("car")并且想让它变得更有意义，我们可以自定义一个@Qualifier。
                @Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE})
                @Retention(RetentionPolicy.RUNTIME)
                @Qualifier
                public @interface Car{
                }

            @Autowired
            @Car
            private Vehicle vehicle;
        * 最后在Sedan类加上注释。
            @Car
            public class Sedan implements Vehicle{
            }


    @NotNull
    @Value("${database.criminal.password}")


    @EnableWebMvc
    @ComponentScan(basePackages = {"com.newings.controller"})
    @Configuration

    @Component 用于将所标注的类加载到 Spring 环境中，需要搭配 component-scan 使用
    @Configuration 是 Spring 3.X 后提供的注解，用于取代 XML 来配置 Spring, 如下：

     @Configuration
     @PropertySource("classpath:/com/acme/app.properties")
     public class AppConfig {
         @Inject Environment env;

         @Bean
         public MyBean myBean() {
             return new MyBean(env.getProperty("bean.name"));
         }
     }

———————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    @Pointcut
        * 定义切点 见 AOP
    @Aspect
        * 

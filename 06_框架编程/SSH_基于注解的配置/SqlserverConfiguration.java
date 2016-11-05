@Configuration
@MapperScan(value = "com.newings.dao.sqlserver", sqlSessionFactoryRef = "scmSqlSessionFactory")
@PropertySource("classpath:config.properties")
public class SqlserverConfiguration {
  @NotNull
  @Value("${database.scm.username}")
  private String username;

  @NotNull
  @Value("${database.scm.password}")
  private String password;

  @NotNull
  @Value("${database.scm.url}")
  private String url;

  @NotNull
  @Value("${database.scm.driverClassName}")
  private String driverClassName;

  @Bean(name = "scmDataSource")
  // @ConfigurationProperties(prefix = "database.scm")
  public DataSource scmDataSource() {
    return DataSourceBuilder.create().driverClassName(driverClassName).username(username)
        .password(password).url(url).build();
  }

  /**
   * sqlserver sessionFactory.
   * 
   * @return sessionFactory
   * @throws Exception 异常
   */
  @Bean(name = "scmSqlSessionFactory")
  public SqlSessionFactory sqlSessionFactory() throws Exception {
    SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
    sessionFactory.setDataSource(scmDataSource());
    Interceptor[] plugins = {sqlserverPageHelper()};
    sessionFactory.setPlugins(plugins);
    return sessionFactory.getObject();
  }

  /**
   * sqlserver分页插件.
   * 
   * @return 分页插件
   */
  @Bean(name = "sqlserverPageHelper")
  @ConfigurationProperties
  public PageHelper sqlserverPageHelper() {
    final PageHelper pageHelper = new PageHelper();
    Properties properties = new Properties();
    properties.setProperty("dialect", "sqlserver");
    properties.setProperty("reasonable", "true");
    properties.setProperty("rowBoundsWithCount", "true");
    pageHelper.setProperties(properties);
    return pageHelper;
  }

  /**
   * sqlserver transactionManager.
   * 
   * @return transactionManager
   */
  @Bean(name = "scmTransactionManager")
  public DataSourceTransactionManager scmTransactionManager() {
    DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
    dataSourceTransactionManager.setDataSource(scmDataSource());
    return dataSourceTransactionManager;
  }
}

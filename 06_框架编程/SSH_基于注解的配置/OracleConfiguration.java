* Oracle 数据库配置

@Configuration
@MapperScan(value = "com.newings.dao.oracle", sqlSessionFactoryRef = "criminalSqlSessionFactory")
@PropertySource("classpath:config.properties")  // 导入配置文件
public class OracleConfiguration {

  @NotNull
  @Value("${database.criminal.username}")
  private String username; // 将配置文件转换为这个类的属性

  @NotNull
  @Value("${database.criminal.password}")
  private String password;

  @NotNull
  @Value("${database.criminal.url}")
  private String url;

  @NotNull
  @Value("${database.criminal.driverClassName}")
  private String driverClassName;

  /**
   * oracl datasource.
   * 
   * @return datasource
   */
  @Bean(name = "criminalDataSource")
  @Primary
  public DataSource criminalDataSource() {
    DataSource dataSource = null;
    try {
      dataSource = DataSourceBuilder.create()  // 建立一个数据库连接 罪犯数据库连接
          .type((Class<? extends DataSource>) Class
              .forName("org.apache.commons.dbcp2.BasicDataSource"))
          .driverClassName(driverClassName).username(username).password(password).url(url).build();
    } catch (ClassNotFoundException ex) {
      ex.printStackTrace();
    }
    return dataSource;
  }

  @Bean(name = "criminalSqlSessionFactory")
  @Primary
  SqlSessionFactory criminalSqlSessionFactory() throws Exception {
    SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
    sessionFactory.setDataSource(criminalDataSource());
    Interceptor[] plugins = {oraclePageHelper()};
    sessionFactory.setPlugins(plugins);
    return sessionFactory.getObject();
  }

  @Bean(name = "oraclePageHelper")
  @ConfigurationProperties
  PageHelper oraclePageHelper() {
    Properties properties = new Properties();
    properties.setProperty("dialect", "oracle");
    properties.setProperty("reasonable", "true");
    properties.setProperty("rowBoundsWithCount", "true");
    PageHelper pageHelper = new PageHelper();
    pageHelper.setProperties(properties);
    return pageHelper;
  }

  /**
   * oracle transactionManager.
   * 
   * @return transactionManager
   */
  @Bean(name = "criminalTransactionManager")
  public DataSourceTransactionManager criminalTransactionManager() {
    DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
    dataSourceTransactionManager.setDataSource(criminalDataSource());
    return dataSourceTransactionManager;
  }
}

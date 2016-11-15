* configuration 里面用到的注解
    @Configuration
    @EnableCaching
    @Bean

    @Configuration
    @MapperScan(value = "com.newings.dao.oracle", sqlSessionFactoryRef = "criminalSqlSessionFactory")
    @PropertySource("classpath:config.properties")

    @NotNull
    @Value("${database.criminal.username}")
    private String username;

    @Bean(name = "criminalDataSource")
    @Primary

    @Bean(name = "oraclePageHelper")
    @ConfigurationProperties

    @Bean
    Binding chatStatusBinding(@Qualifier("chatStatusQueue") Queue chatStatusQueue,
    @Qualifier("chatStatusExchange") TopicExchange chatStatusExchange)

    @Value("${need.message}")
    protected boolean needMessage;

    @Bean(name = "triggerRabbitTemplate")
    public RabbitTemplate triggerRabbitTemplate(
    @Qualifier("defaultConnectionFactory") ConnectionFactory connectionFactory) {
    if (needMessage) 

    @Configuration
    @MapperScan(value = "com.newings.dao.sqlserver", sqlSessionFactoryRef = "scmSqlSessionFactory")
    @PropertySource("classpath:config.properties")

    @EnableWebMvc
    @ComponentScan(basePackages = {"com.newings.controller"})
    @Configuration


* AppConfig
    @Configuration
    @EnableAspectJAutoProxy
    @EnableTransactionManagement
    @ComponentScan(basePackages = {"com.kiiwow.*"})
    @Import({SpringContextConfig.class, C3p0DataSourceDBConfig.class})
    @PropertySource({"classpath:kiiwow.properties"})
        public class AppConfig {

        }

* 注解说明
    @Configuration
        用于表示这个类是一个配置类，用于配置Spring的相关信息
    @EnableAspectJAutoProxy
        启用切面自动代理，用于AOP
    @EnableTransactionManagement
        启用注解事务，即可以使用 @Transactional 注解来控制事务
    @ComponentScan
        组件扫描，在 basePackages 指定的目录下扫描被
        @Controller、@Service、@Component 等注解注册的组件
    @Import
        引入指定的配置类，我们引入了 Spring 容器配置类和数据源事务配置类
    @PropertySource
        加载指定的配置文件，配置文件内容会加载入 Environment 中等待调用
    @EnableWebMvc 
        注解用于启用 SpringMVC，我们让 WebConfig 继承自我们
        已经设计好的 Web 子容器配置类 WebContextConfig，
        这样 WebConfig 就已经拥有了所需要的基本配置。
        由于WebContextConfig本身继承了
        org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter，
        所以我们可以直接重写 void addInterceptors(InterceptorRegistry registry)方法
        来注册自定义的监听器。

* 和配置对应
    
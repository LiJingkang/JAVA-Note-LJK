从零构架一个项目，需要做哪些东西，做那方便的东西
    * 使用maven 构建项目
    * Spring MVC 
        Spring MVC 框架是有一个MVC框架，通过实现 Model-View-Controller 模式来很好地将数据、业务与展现进行分离。
        // 就是Controller 层的那些东西。 主要是配置拦截器 自动分配请求 URL
    * Spring   
        Spring是一个轻量级控制反转(IoC)和面向切面(AOP)的容器框架。     
        这里最基本的概念是BeanFactory
        它提供了依赖注入（DependencyInjection）特征来实现容器对Bean的管理。
        // 主要是帮助你来管理 bean
    * Mybatis
        数据持久层 相关的一些注解。
    * 日志集成 
        lo4j 等
    * 权限相关。登录拦截器
    * 最麻烦的部分是整个框架的配置和整合
        * 无 XML 的，基于注解的配置
        * 

* 补课
    Java 2 核心技术 卷I：基础知识（原书第7版）
    Java 2 核心技术 卷II：高级特性（原书第7版）
    servlet/Jsp
    精通EJB
    Servlet与JSP核心编程（第2版）
    Hibernate Quickly中文版
    Spring框架高级编程

* EJB
    EJB 是 sun 的 JavaEE 服务器端组件模型，
    设计目标与核心应用是部署分布式应用程序。
    简单来说就是把已经编写好的程序（即：类）打包"放在服务器上执行"。

    凭借java跨平台的优势，用 EJB 技术部署的分布式系统可以不限于特定的平台。

    EJB (Enterprise JavaBean)是J2EE(javaEE)的一部分，
    定义了一个用于开发基于组件的企业多重应用程序的标准。
    其特点包括 网络服务支持 和 核心开发工具(SDK)。 

    在J2EE里，Enterprise Java Beans(EJB)称为 Java 企业 Bean，是Java的核心代码，

    分别是 会话Bean（Session Bean），
           实体Bean（Entity Bean）和
           消息驱动Bean（MessageDriven Bean）。    
————————————————————————————————————————————————————————————————————————————————————————————————————————————————
* add sqljdbc4 
	* 添加了数据库的支持模块
		// 在pom.xml里面添加
		<!-- https://mvnrepository.com/artifact/com.hynnet/sqljdbc4-chs -->
		<dependency>
			<groupId>com.hynnet</groupId>
			<artifactId>sqljdbc4-chs</artifactId>
			<version>4.0.2206.100</version>
		</dependency>
* global_config.vm
	* 全局配置文件
* 框架配置到 com.newings.configuration 不再使用xml进行配置

* 所有在spring中注入的bean都建议定义成私有的域变量。并且要配套写上 get 和 set方法。
    @Autowired
    Spring 2.5 引入了 @Autowired 注释，它可以对类成员变量、方法及构造函数进行标注，完成自动装配的工作。 
    通过 @Autowired的使用来消除 set ，get方法。
    // 实际上就是通过这个注解来完成复杂的赋值。 默认的set和get方法而已。
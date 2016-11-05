* http://blog.csdn.net/wangpeng047/article/details/8556800
——————————————————————————————————————————————————————————————————————————————————————————————————————————————————
开篇简介：
* Spring：
        Spring 是一个轻量级的控制反转(IoC)和面向切面(AOP)的容器框架。

    * 两个重要模块：
            Spring 面向切面编程（AOP）和控制反转 （IOC） 容器。

        控制反转模式（也称作依赖性介入）的基本概念是：
                不创建对象，但是描述创建它们的方式。
                在代码中不直接与对象和服务连接，
                但在配置文件中描述哪一个组件需要哪一项服务。

        容器 （在 Spring 框架中是 IOC 容器） 负责将这些联系在一起。
                在典型的 IOC 场景中，容器创建了所有对象，
                并设置必要的属性将它们连接在一起，决定什么时间调用方法。

* MyBatis:
　　　　MyBatis 是支持普通 SQL查询，存储过程 和 高级映射的优秀持久层框架。
        MyBatis 消除了几乎所有的 JDBC 代码 和 参数的手工设置以及结果集的检索。
 
        MyBatis 使用简单的 XML 或 注解用于配置 和 原始映射，
                将接口和 Java 的POJOs（Plain Old Java Objects，普通的 Java对象）映射成数据库中的记录。
——————————————————————————————————————————————————————————————————————————————————————————————————————————————————
1.AOP 术语：
● 感觉 AOP 就是一个 拦截器 和
    ● 所有切点 组成的就是一个切面。 对每一个切点上 插入一段代码或者程序。实现某些功能。
      就是 所谓的 面向切面变成 
        ● 这里首先包括两个工作：
            ● 第一
                    如何通过增强和切点定位到连接点上；
                    "拦截器" 通过拦截器可以拦截 我们需要处理的请求， 也就是找到了 "切面"
            ● 第二
                    如何在增强中编写切面的代码。

                    在 JC 项目中 "最重要" 的是日志相关的内容。
                    那么日志相关的切面编程是怎么完成的呢。


—————————————————— 
● 连接点(Joinpoint)
        一个类 或 一段程序代码拥有一些具有边界性质的 特定点，这些代码中的特定点就称为 "连接点"。
        Spring 中仅"支持方法"的连接点，
        即 仅能在方法调用前、方法调用后、方法抛出异常时以及方法调用前后这些程序"执行点织入增强"。
        连接点是程序中客观存在的事物。
 
● 切点(Pointcut)
        AOP通过切点定位连接点。
        连接点相当于数据库中的记录，而切点相当于"查询条件"。
 
● 增强(Advice)
        增强是织入到目标类"连接点"上的"一段程序代码"。
 
● 目标对象(Target)
        增强逻辑的织入目标类。
 
● 引介(Introduction)
        引介是一种"特殊的增强"，"它为类添加一些属性和方法"。
 
● 织入(Weaving)
        织入是将增强添加到目标类"具体连接点上的过程"。
 
● 代理(Proxy)
        一个类被 AOP 织入增强后，就产出了一个"结果类"，它是"融合了原类"和"增强逻辑"的"代理类"。
        代理类既可以是和原类具有相同接口的类，也可以是原类的子类。
 
● 切面(Aspect)
        切面由 "切点"和"增强（引介）"组成，它既包括了"横切逻辑"的定义，也包括了"连接点"的定义。
        AOP 的"工作重心"在于
            "如何将增强应用于目标对象的连接点上"，
        ● 这里首先包括两个工作：
            ● 第一
                    如何通过增强和切点定位到连接点上；
            ● 第二
                    如何在增强中编写切面的代码。
——————————————————————————————————————————————————————————————————————————————————————————————————————————————————
* 实例
    * 实际上
        设置字符编码格式看作是一个 "切面"，而拦截器就是一个"增强"。
        * 设置字符编码格式相当于是一个 "过滤器" 
——————————————————————————————————————————————————————————————————————————————————————————————————————————————————
* 基于注解的 AOP 方法
    * 先在配置文件里面添加 
        <aop:aspectj-autoProxy/>  
    
    (1) 在 spring 配置文件中加入对注解方法的aop支持。

    (2) 定义切面：
            和创建普通类类似，在类前加上"@Aspect"注解，表明该类是一个切面。

    (3) 在切面中加入切入点：
            切入点 就是 "被拦截对象" "方法的集合"，
            通常切入点 定义在 切面中 "某个" 对切入点 "进行处理的方法" 上。
        使用 "@Pointcut" 注解，语法如下：

    @Pointcut
        @Pointcut("execution(* com.test.service..*.*(..))")  
        public void anyMethod(){//方法名为切入点名  
        切入点处理  
        }  

    语法参数详解：
        a. 第一个"*"：表示被拦截的方法是任意的返回类型。
        b. com.test.service：这里是举一个简单的例子，表示要被拦截的包名，即被拦截的包。
        c.被拦截包名后面的两个".."：表示被拦截包下面的子包也递归进行拦截，即被拦截的子包。
        d. ".."之后的"*"：表示被拦截包及其子包下面的所有类，即被拦截的类。
        e. 最后一个"*"：表示被拦截类中的所有方法，即被拦截的方法。
        f. "(..)"：表示被拦截的方法接收任意的参数，即被拦截的参数。
    注意：
        切入点定义语法可以支持通配符，但是一定要严格遵循语法规则。如：

    @Pointcut("execution(*com.test.service..*.add*(..))")
        表示对 com.test.service 包及其子包下所有的类中以 "add" 开头的方法进行拦截。

(4).在切面中添加通知：
    "@Before"注解：
        声明前置通知。
    "@AfterRutruning"注解：
        声明后置通知。
    "@After"注解：
        声明最终通知。
    "@AfterThrowing"注解：
        声明例外通知。
    "@Around"注解：
        声明环绕通知。

    一个定义通知的例子如下：
        @Before("anyMethod()(切面中声明的切入点名)")  
        public void doAccessCheck(){  
               ……  
        }   
    * 注意：
            环绕通知和其他4种通知的稍有不同，环绕通知的定义方式比较特别，
            环绕通知在整个方法调用前后都会起作用，
            因此必须使用连接点对象告诉连接点在环绕通知处理之后继续其逻辑处理。

    其定义方式如下：
        @Around(切入点名)  
        public Object doBasicProfiling(ProcedingJoinPoint pjp) throws Throwable{  
               ……  
               return pjp.proceed();//该句是告诉连接点继续执行其他的操作  
        }  


7. 基于注解方式的面向切面编程(AOP)开发的一些小技巧：

    (1).获取输入参数：

        如：
            @Before("切入点名 && args(输入参数名)")  
            public void doSomething(String 输入参数名){……}  

    (2).获取返回结果：

        如：
            @AfterReturning(Pointcut="切入点名",returning="返回结果名")  
            public void dosomething(String 结果名){……}  

8. 基于XML方式的面向切面编程(AOP)开发：

    (1).定义切面类，在切面类中添加通知。
    (2).将切面类想普通java类一样在spring配置文件中配置。
    (3).在spring配置文件中添加AOP配置如下：

        <aop:config>  
               <!--配置切面-->  
               <aop:aspect id="切面id" ref="spring配置文件中切面类的id">  
                      <!--配置切入点-->  
                      <aop:pointcut id="切入点id"  
        expression="execution(* com.test.service..*.*(..))"/>  
                      <!--配置通知-->  
                      <aop:before pointcut-ref="切入点id" method="切面类中相应的处理方法"/>  
                      <aop:after ……/>  
                      ……  
        </aop:aspect>  
        </aop:config>  

9. Spring的事务处理(Spring的声明式事务处理)：

    事务简单来说是指数据库中的一条最基本的操作，
    关于事务的详细讲解以后会在数据库相关总结中具体说明。

    Spring的面向切面编程(AOP)一个最重要的应用是事务管理，
        Spring2.5以后版本的事务管理支持基于注解的方式和基于XML文件的方式两种：

    (1).基于注解方式的事务管理：
        a. 在spring配置文件中添加事务管理的命名空间如下：
            xmlns:ts=http://www.springframework.org/schema/tx  
            http://www.springframework.org/schema/tx  
            http://www.springframework.org/schema/tx/spring-tx-2.5.xsd  

        b. 在spring配置文件中配置事务管理器如下：
            <bean id="txManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">  
                   <property name="dataSource" ref="spring中配置的数据源bean的id"/>  
            </bean>  

        c. 在spring配置文件中添加支持注解方式的事务配置项如下：
            <tx:annotation-driventransaction-manager 
                tx:annotation-driventransaction-manager="txManager(spring中配置的事务管理器bean的id)"/>  

        d. 使用基于注解的事务管理：
            在Spring所管理的JavaEE工程中，
            需要使用事务的业务逻辑地方加上"@Transactional"注解。

    (2).基于XML文件方式的事务管理：
        a. 在spring配置文件中配置事务管理器如下：
            <bean id="txManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">  
                   <property name="dataSource" ref="spring中配置的数据源bean的id"/>  
            </bean>  

        b. 在spring配置文件中添加事物管理的切面如下：
            <aop:config>  
                   <!--配置事务切入点-->  
                   <aop:pointcut id="transactionPointcut"  
            Expression="execution(* com.test.service..*.*(..))"/>  
            <!--配置事务通知-->     
            <aop:advisor advice-ref="txAdvice" pointcut-ref="transactionPointcut"/>  
            </aop:config>  

         c. 在spring配置文件中为事务通知添加事物处理特性如下：
            <tx:advice id="txAdvice" transactionManager="txManager">  
                   <tx:attributes>  
                          <!--这里举例将以get开头的查询方法设置为只读，不支持事务-->  
                          <tx:method name="get*" read-only="true" propagation="NOT_SUPPORTED"/>  
                          <!--其他的方法设置为spring默认的事物行为-->  
                          <tx:method name="*"/>  
                   </tx:attributes>  
            </tx:advice>  
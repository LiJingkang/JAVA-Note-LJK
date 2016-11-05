组件装配
    <context:component-sacn>
    元素还会自动注册 AutowireAnnotationBeanPostProcessor 实例，
    该实例可以自动装配具有 
    @Autowired 和
    @Resource
    @Inject 注解的属性

@Autowired自动装配Bean
    
    @Autowired注解自动装配 具有兼容类型 的'单个'Bean属性
        * 可以放在构造器或普通字段（即使是非public）或一切具有参数的方法都可以应用@Authwired注解
        * 默认情况下，所有使用@Autowired注解的属性都需要被设置，
            当Spring'找不到匹配'的Bean'装配属性'时，会抛出异常， 
            若某一属性允许不被设置，'可以设置' @Authwired注解的required属性为 false
        * 默认情况下，当IOC容器里存在多个类型兼容的Bean时
            （@Autowired先是按照类型匹配Bean，如果存在多个类型相同的Bean，
                此时IOC容器会去寻找与属性名相同名字的Bean），
            * 通过类型的自动装配将无法工作，
                此时可以在 @Qualifier注解里 Bean属性的名称。 
                Spring允许对方法的方法的输入参数标注 
                @Qualifier以指定注入"Bean的名称"
    @Authwired注解也可以应用在数组类型的属性上，
        此时Spring将会把所有匹配的Bean进行自动装配
    @Authwired注解也可以应用在集合属性上，
        此时Spring读取该集合的类型信息，然后自动装配所有与之兼容的Bean
    @Authwired注解用在java.util.Map上时，
        若该Map的键值为String，那么Spring将会自动装配与之Map值类型兼容的Bean，
        此时Bean的名称作为键值

@Resource或@Inject自动装配Bean
    Spring还支持@Resource和@Inject注解，这两个注解和@Autowired注解的功能类似
    @Resource注解要求提供一个Bean名称的属性，若该属性为空，
        则自动采用标注处的变量或方法名作为Bean的名称
    @Inject和@Autowired注解一样也是按照类型匹配注入的Bean，
        但没有required属性
    * 建议使用@Autowired注解
——————————————————————————————————————————————————————————————————————————————————————————————————————————————
在 classpath 中扫描组件

* 组件扫描（component scanning）：
    Spring能够从classpath下自动扫描，侦测和实例化具有特定注解的组件

    特定组件包括：
        @Component：
                基本注解，标示了一个受Spring管理的组件 （可以混用，spring还无法识别具体是哪一层）
        @Respository：
                建议标识持久层组件 （可以混用，spring还无法识别具体是哪一层）
        @Service：
                建议标识服务层（业务层）组件 （可以混用，spring还无法识别具体是哪一层）
        @Controller：
                建议标识表现层组件（可以混用，spring还无法识别具体是哪一层）
    
    对于扫描到的组件，Spring有默认的命名策略 ：
        使用非限定类名，第一个字母小写（ UserServiceImpl－> userServiceImpl ）， 
        也可以再注解中通过 value 属性值标识组件的名称（通常可以将UserServiceImpl —> userService,
        可以将Impl拿掉，这是一个习惯）

* 在classpath中扫描组件
    当在组件类中使用了特定的注解之后，还需要在Spring的配置文件中声明 
        <context:component-scan>:
        base-package 属性指定一个需要扫描的基类包，Spring容器将会扫描整个基类包里及其子包中的所有类
        当需要扫描多个包时，可以使用逗号分隔
        如果仅希望扫描特定的类而非基包下的所有类，可使用resource-pattern属性过滤特定的类，
    实例：  
        <context:include-filter>
                子节点表示要包含的目标类
        <context:exclude-filter>
                子节点表示要排除在外的目标类
        <context:component-scan>
                下可以拥有若干个<context:include-filter>和<context:exclude-filter>子节点

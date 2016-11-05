    IoC是什么东东？
    IoC实现的机制是什么样子的？
    模拟Spring IoC的实现
    Spring源码中的IoC

一、IoC是什么东东？
    IoC的e文叫做 Inversion of Control，翻译过来就是控制反转，
    那么IoC究竟翻转了什么呢？思考下在面向对象的世界中，如果

    我们想在对象A中，操作对象B，那么A一定会持有B的引用（对象关系），
    而后才能进行操作。这样的带来的问题是代码不够整洁、代码维护成本高、耦合度大。
    
    Spring IoC带来了这个问题的'解决办法'，
    Spring 将对象之间的'依赖关系'转而用'配置文件来管理'，
    也就是 Spring 的 DI——Dependency Injection（依赖注入），

    同时，任何一个对象'都应该有一个保存他的地方'，也就是IoC容器，
    IoC 容器'保存了'所有的 Bean对象，这个 Bean对象就是 '真实的Object'。
    Spring通过IoC容器，进而操作这些Bean，从而达到对这些对象管理以及一些列额外操作的目的。

    那么接下来回答前文提出的问题：
    Q：IoC是什么？
    A：IoC是控制反转器。

    Q：IoC反转了什么？
    A：IoC'反转了' 对象的'创建及依赖关系'，
        Spring将对象的"创建"以及"对象之间的依赖关系"交给了IoC容器来管理，
        管理的标准，则是'配置文件'。

二、IoC的实现机制是什么样的？
    思考下，Spring既然是通过'配置文件'来'管理'对象的依赖关系，
    而IoC则是将这种关系'进行了实现'。那么，
    是不是说我们要做如下两件事情：

        1. 解析配置文件，获得对象信息
        2. 合理的创建它们
        
三、模拟Spring IoC的实现
    * 第一步，解析配置文件，获得对象信息；
            因为 Spring的配置文件是 *.xml文件，也就是对 XML 文件的解析。
            // 也就是 bean 自动注入 bean
            // 在 JC 中使用主角就已经全部配置好了。
            // <context:component-scan base-package="com.newings" /> XML中就不需要手动配置 bean 了
            <beans>  
                <bean id="u" class="com.hzy.dao.impl.UserDAOImpl"/>  
                  
                <bean id="userService" class="com.hzy.service.UserService">  
                    <property name="userDAO" bean="u"/>  
                </bean>  
            </beans>  

        对XML解析使用了JDOM，jdom的下载地址：http://www.jdom.org/downloads/index.html

    * 第二步  根据解析出来的关系将实例化他们，这里用到的技术为：反射机制
        public class ClassPathXmlApplicationContext implements BeanFactory  
        {  
            private Map<String, Object> beans = new HashMap<String, Object>();  //IoC容器  
              
            public ClassPathXmlApplicationContext() throws Exception  
            {  
                SAXBuilder builder = new SAXBuilder();  
                Document document = builder.build( this.getClass().getClassLoader().getResource( "resource/beans.xml" ) );  
                  
                Element root = document.getRootElement();  
                  
                List<Element> list = root.getChildren( "bean" );              // 获得所有的bean的Element  
                  
                for( int i = 0; i < list.size(); i++ )  
                {  
                    Element element = (Element)list.get( i );  

                    String  id    = element.getAttributeValue( "id" );            
                    String  clazz = element.getAttributeValue( "class" );  
                      
                    System.out.println( id + " : " + clazz );  
                    Object obj = Class.forName( clazz ).newInstance();          // 1th.实例化Bean对象  
                    beans.put( id, obj );  
                      
                    /** 
                     *  <bean id="u" class="com.hzy.dao.impl.UserDAOImpl"/> 
                     *
                     *  <bean id="userService" class="com.hzy.service.UserService"> 
                     *      <property name="userDAO" bean="u"/> 
                     *  </bean> 
                     */  
                    // 2th.注入依赖  
                    // 获得所有property属性  
                    for( Element propertyElement : (List<Element>)element.getChildren( "property" ) )  
                    {  
                        String name       = propertyElement.getAttributeValue( "name" );    // userDAO  
                        String injectBean = propertyElement.getAttributeValue( "bean" );    // u;  
                        Object propertyObj = beans.get( injectBean );  
                          
                        // 3th.拼接userService中 userDAO属性 的 setter方法  
                        // name.substring( 0, 1 ).toUpperCase() 将 u 变成大写  
                        String methodName = "set" + name.substring( 0, 1).toUpperCase() + name.substring( 1 );  
                          
                        System.out.println( "method name = " + methodName );  
                          
                        /** 
                         * getMethod 会返回对象的方法..这个方法来自对象的公开方法或接口     反射机制 
                         * Returns a Method object that reflects the specified  
                         * public member method of the class or interface represented by this Class object.  
                         */  
                        Method m = obj.getClass().getMethod( methodName, propertyObj.getClass().getInterfaces() );  
                        // 4th.注入  
                        m.invoke( obj, propertyObj );  
                    }  
                }  
            }  
              
            @Override  
            public Object getBean( String name )   
            {  
                return beans.get( name );  
            }  
        }  

        完整工程的示例代码在：Spring Ioc模拟源码，运行com.hzy.test中的UserServiceTest.java即可。

四、Spring中IoC的实现（源码）

        Spring 的功能实现 AOP IoC ...
        Spring 的核心组件 
                        Context  Bean 
                            Core 
        
        Spring的核心组件有Core、Context、Bean这三个；
            这三个造就了Spring的根基，决定了上层诸如，IoC、AOP、Transaction....这些特性功能。

4.1 三大核心组件的协同工作
    那么看看这三个组件都负责些什么，把Spring想象成一个大型的舞台剧，那么：
        Context：这台舞台剧的场景、硬件设施
        Core   ：这台舞台剧的 '工作人员'，'负责协调'、'调动资源'
        Bean   ：这台舞台唔剧的 '演员'
        
    那么各个组件的实际的工作就是，
        Bean包装了Object，
                  Object中含有数据；
        Context为Bean提供生存环境，同时维护Bean之间的关系，
                  这个Context对应的'Bean的集合'，这个'关系集合'就称为'IoC容器'；
        Core则是为发现、建立、维护每个Bean之间的关系提供相应的'工具类'。
        
    下面就以 ClassPathXmlApplicationContext 来解释下这些概念。
    查看Api文档，发现 ClassPathXmlApplicationContext 继承了 BeanFactory，
        BeanFactory是 Bean组件的顶级接口，
        BeanFactory提供了对 Bean的定义、创建、解析。
        Bean组件在 org.springframework.beans包下。
    同时Bean Factory的最直接实现类是DefaultListableBeanFactory，
        在这个类中定义了一个集合，用来保存类对象

        Context组件，为了实现资源的调度，实现了ResourceLoader接口，
            并且Context的ApplicationContext接口继承了BeanFactory，
            这样Context通过ApplicationContext可以保证能访问到任何外部资源。

        那么我们实现Spring IoC的思路就很简单了：

    1th  解析XML，获得相应的Bean定义
    2th  实例化Bean
    3th  根据注入方式进行注入 （示例中如property）
    4th  利用反射进行注入

五、小结
    Spring是很优秀的开源框架，很多内容值得借鉴
    下篇会讨论AOP的实现，AOP是对传统OOP的一种补充，AOP与OOP的关注点分别为：

    OOP：
        将需求功能划分为不同的并且相对独立，封装良好的类，
        并让他们有着属于自己的行为，依靠集成和多态来定义彼此的关系
    AOP：
        将通用需求功能从不相关的类中分离出来，
        能够使得很多类共享一个行为

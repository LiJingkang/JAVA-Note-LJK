SpringMVC框架介绍

1) Spring MVC 属于 SpringFrameWork 的后续产品，已经融合在 Spring Web Flow里面。
    Spring 框架提供了构建 Web 应用程序的全功能 MVC 模块。
    使用 Spring 可插入的 MVC 架构，
    可以选择是使用内置的 Spring Web 框架还是 Struts 这样的 Web 框架。
    通过策略接口，Spring 框架是高度可配置的，
    而且包含多种视图技术，

    * 例如 
    JavaServer Pages（JSP）技术、Velocity、Tiles、iText 和 POI。
    Spring MVC 框架并不知道使用的视图，所以不会强迫您只使用 JSP 技术。

    Spring MVC 分离了
        控制器
        模型对象
        分派器
        处理程序对象的角色，
        这种分离让它们更容易进行定制。

2) Spring 的 MVC 框架主要由 
    DispatcherServlet、
    处理器映射、
    处理器(控制器)、
    视图解析器、
    视图组成。

* SpringMVC原理图


* SpringMVC接口解释

    DispatcherServlet 接口：     
        Spring提供的前端控制器，
        所有的请求都有经过它来统一分发。
        在 DispatcherServlet 将请求分发给 Spring Controller 之前，
        需要借助于 Spring 提供的 HandlerMapping 定位到具体的 Controller。

    HandlerMapping 接口：
        够完成客户请求到Controller映射。

    Controller 接口：   
        需要为并发用户处理上述请求，因此实现 Controller 接口时，必须保证线程安全并且可重用。
        Controller 将处理用户请求，这和 Struts Action 扮演的角色是一致的。

        一旦 Controller 处理完用户请求，
        "则返回" ModelAndView 对象给 DispatcherServlet 前端控制器，
        ModelAndView 中包含了 模型（Model）和 视图（View）。

        从宏观角度考虑，
            DispatcherServlet 是整个 Web 应用的控制器；
        从微观考虑，
            Controller 是单个 Http请求处理过程中的控制器，
            "而" ModelAndView 是 Http请求过程中返回的模型（Model）和视图（View）。

    ViewResolver 接口：
        Spring 提供的视图解析器（ViewResolver）在 Web 应用中查找 View 对象，
        从而将相应结果渲染给客户。

* SpringMVC运行原理
    1. 客户端请求提交到 DispatcherServlet
    2. 由 DispatcherServlet 控制器查询一个或多个 HandlerMapping，找到处理请求的 Controller
    3. DispatcherServlet 将请求提交到 Controller
    4. Controller 调用业务逻辑处理后，返回 ModelAndView
    5. DispatcherServlet 查询一个或多个 ViewResoler 视图解析器，找到 ModelAndView 指定的视图
    6. 视图负责将结果显示到客户端

* DispatcherServlet 是整个 Spring MVC 的核心。
    它负责接收 HTTP请求组织协调Spring MVC的各个组成部分。

    * 其主要工作有以下三项：
       1. 截获符合特定格式的URL请求。
       2. 初始化 DispatcherServlet 上下文对应的 
            WebApplicationContext，并将其与业务层、持久化层的 WebApplicationContext 建立关联。
       3. 初始化 Spring MVC的各个组成组件，并装配到 DispatcherServlet 中

——————————————————————————————————————————————————————————————————————————————————————————————————————————————————
* SpringMVC整体框架理解 
* SpringMVC整体框架图
    学习某一种技术的话，要能够从宏观上把握一下这种技术的大致实现原理，然后才开始刨根问底的学习，
    这种技术的细节，这样会快也一些。首先从宏观上把控一些知识点之间的联系，学习起来更有连贯性，
    下面就先从SpringMVC的框架图来整体分析一下。

1.首先是用户发送请求，比如在浏览器中输入
    http://localhost:8080/SpringTest/login

2.用户的请求就发送到了 DispatcherServlet 也就是"前端控制器"，
    这个东西是一个 Servlet 的实现，用来干什么呢？
    用来接收用户的请求，然后响应结果，相当于一个"转发器"，它的配置如下
        <span style="font-family:Comic Sans MS;font-size:18px;">
        <!-- 自此请求已交给Spring Web MVC框架处理，因此我们需要配置Spring的配置文件，
                默认 DispatcherServlet 会加载 WEB-INF/[DispatcherServlet的Servlet名字]-servlet.xml配置文件。  
                本示例为 WEB-INF/ chapter2-servlet.xml。 -->  
        <servlet>  
            <servlet-name>springMVC</servlet-name>  
            <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>  
            <init-param>  
                <param-name>contextConfigLocation</param-name>  
                <param-value>classpath:springMVC-servlet.xml</param-value>  
            </init-param>  
            <load-on-startup>1</load-on-startup>  
        </servlet>  

        <!-- 这里一定要是/根据Servlet规范来的 -->  
        <servlet-mapping>  
            <servlet-name>springMVC</servlet-name>  
            <url-pattern>/</url-pattern>  
        </servlet-mapping></span>  

    如果翻开 DispatcherServlet 源码的话，会发现会默认加载 SpringMVC 的一些配置，源码如下
        <span style="font-family:Comic Sans MS;font-size:18px;">static {  
        // Load default strategy implementations from properties file.  
        // This is currently strictly internal and not meant to be customized  
        // by application developers.  
        try {  
            ClassPathResource resource = new ClassPathResource(DEFAULT_STRATEGIES_PATH, DispatcherServlet.class);  
            defaultStrategies = PropertiesLoaderUtils.loadProperties(resource);  
        }  
        catch (IOException ex) {  
            throw new IllegalStateException("Could not load 'DispatcherServlet.properties': " + ex.getMessage());  
        }  
        }</span> 
    
    并且该 servlet 也会默认加载默认 DispatcherServlet 会
    加载 WEB-INF/[DispatcherServlet的Servlet名字]-servlet.xml 配置文件。
    本示例为WEB-INF/SpringMVC-servlet.xml 改配置文件就用来配置后面的处理映射器、处理适配器等相关信息            

3. 经过 DispatcherServlet 过滤后 URL 后，
    比如上述的URL是 http://localhost:8080/SpringTest/login，
    那么这时候就进入到了 HandleMapping 也就是映射处理器。
    这是用来干什么的呢？

    就是用来根据 URL 来匹配我们的 处理器（或者就做"控制器"），
    比如我们请求的路径最后结尾的字母是 login，那么让那个控制器来为我们服务呢？
    这时候就靠 HandleMapping 来处理了。下面列一下 SpringMVC 中的几种处理映射器。

3.1 BeanNameUrlHandlerMapping：
    用来根据控制器的 name 属性来匹配要处理的映射器，配置如下    
        <span style="font-family:Comic Sans MS;font-size:18px;">  
        <bean id="itemsController1" name="/queryItems_test.action" class="cn.ssm.controller.ItemsController1" />  
        <!-- 处理器映射器 将bean的name作为url进行查找 ，需要在配置Handler时指定beanname（就是url）   
        所有的映射器都实现 HandlerMapping接口。  -->  
        <bean  
            class="org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping" /></span>
    经过这样配置的话，就可以通过/queryItems_test来查找我们的控制器了            

3.2 SimpleUrlHandlerMapping：
    根据URL来映射匹配    

3.3 RequestMappingHandlerMapping：
    3.1 后引入的新的特性，注解适配器，直接通过 Controller 标签即可识别    
        <span style="font-family:Comic Sans MS;font-size:18px;"><!--注解映射器 -->  
        <bean class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping"/></span> 

4. 接着向下走，通过映射处理器就找到了我们需要处理用户请求的控制器也就是 Handler，
    那么此时还需要 HandlerAdapter（处理器适配器）来，
    作用就是要按照特定规则（HandlerAdapter要求的规则）去执行 Handler。常见的适配器如下     
4.1 SimpleControllerHandlerAdapter：
    要求handler实现Controller接口
    SimpleControllerHandlerAdapter 将会调用处理器的 handleRequest 方法进行功能处理，
    该处理方法返回一个 ModelAndView 给 DispatcherServlet；当然了也有对应的注解适配器
4.2 HttpRequestHandlerAdapter：
    要求编写的Handler实现HttpRequestHandler接口
4.3 注解适配器有RequestMappingHandlerAdapter，用来配合注解的开发   


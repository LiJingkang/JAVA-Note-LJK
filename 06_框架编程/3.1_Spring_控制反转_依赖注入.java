* Spring 具体在做什么？
    * 控制反转 依赖注入

——————————————————————————————————————————————————————————————————————————————————————————————————————————————————
* 依赖注入
    * 注入就是，spring的管理机制Bean工厂已经把对象创建好了 你只需要调用就行！ 
    * 一般代码中对象普遍是 new Class把对象实例出来，注入之后呢，spring帮你new好了，你可以直接用了 

    * A类和B类,如果A要用到B
        在A类中加一个setB()就OK了,
        再通过spring的IOC容器实例化好的对象用属性注入就OK了  

    * 解释
        spring通过"控制反转"实现了"依赖注入"。'所谓的注入'，
        我的理解是程序实现就像盖房子，spring 允许你在类（class）里搭个框架，
        在配置文件中注明在什么地方用什么材料，
        在运行时spring按照你的配置真正向这个架子里"注入"了水泥、钢筋等等。 
        
        这在实际运用中很方便，比如你需要向日志服务器提交记录，但是日志服务器的ip地址可能会变更，
        你可以把ip地址写在配置文件里，这样当ip改变时，只需更改配置文件，而不需要修改类再重新编译。

    * 解释
        这么说吧，在一个业务类中，如果要用到 ClassA，是不是要 new 一个 ClassA对象？
        这个就等于是业务类 '主动的去创建一个对象'。引用spring之后，怎么处理呢？

        是这个业务类在需要一个 ClassA对象时，由spring IOC容器去创建这个 ClassA对象，
        然后注入到业务类需要的地方。这里就体现了控制反转的意思。
        也就是创建这个ClassA对象 '由业务类主动的去创建' 改为了 '被动的接受spring' IOC容器的注入。这就是解耦了。
* 一个问题
        Interface A = new ClassA（）；
        a.hello();

    * "如果是依赖注入"。
        这里就不用new 这个 ClassA 对象。而由 Spring动态注入进去。

        Interface A = new ClassA（）；
        a.hello();
        改成
        Interface A; // 用到 A 的时候，自动注入A，也就是自动new 一个A
        a.hello();

        当'需要'ClassA对象 时就'注入'ClassA对象，
          '需要'ClassB对象 时就'注入'ClassB(当然ClassB也要实现相同的接口)。
        所以这个类就不用反复的修改。这里spring体现的是解耦。 

    * 依赖注入，
        可以'不用'在代码中'显式调用new'(框架自动帮你new然后注入)，
        这样程序就不会固定'依赖于ClassA'，使得程序和'ClassA解耦'，
        这样可以在不修改程序的情况下，给程序注入别的如'ClassB'来实现别的功能 // 这一步是如何实现的
    * 
        1、如果你的程序中有100处使用了 new ClassA();你就懂了，某业务类为应对当前时间特殊的处理要求，在本月使用新的实现 ClassB 实现, 本月过后再换回原来的ClassA实现。项目经理安排你来改，你烦不？
        2、使用了 Ioc，由 Spring 容器管理 bean，为继续使用其它组件 Spring 提供基础。

* 解耦
    （1）我觉得你在耦合度的降低上的理解有点偏差，在 spring 中，
            降低耦合度的关键在于将 bean 的'依赖关系抽取出来的环节'，
            而不在于将 bean 的依赖关系放在哪里的环节（配置文件？或注解？）

    （2）spring 之 ioc
            1 将基于'实现类的耦合'变成'基于接口的耦合'，可以避免硬编码所造成的过度程序耦合，
                而接下来需要解决的问题就是，如何确定该'接口的实现类'。
            2 ioc 控制反转，就是将某一接口的具体实现类的控制"从调用类中移除"，
                转交给第三方（也就是spring容器）
    （2）spring之aop
            1 在业务系统里除了要实"现业务功能"之外，还要实现如"权限拦截"、"性能监控"、"事务管理"等非业务功能。
                通常的作法是"非业务的代码"穿插在业务代码中，从而导致了业务组件与非业务组件的耦合。
            2 aop 面向切面编程，就是将这些分散在各个业务逻辑代码中的非业务代码，
                通过"横向切割"的方式抽取到一个'独立的模块'中，从而实现业务组件与非业务组件的解耦。

——————————————————————————————————————————————————————————————————————————————————————————————————————————————————
* HTTP 信息转换器
	当处理方法返回Java对象，这个对象会放在模型中并在试图中渲染使用
	但是如果使用 @ResponseBody ，那表明信息转换机制会发挥作用，并将返回的对象转换诶客户端需要的任何形式。

	* 资源返回的形式要满足请求中Accept头部信息的要求。如果没有Accept头部信息，则假设客户端能够接受任何表达形式。
	* @RequestMapping(value = "/{username}", method = RequestMethod.GET, headers = {"Accept = text/xml, application/json"})
			// 只处理头部信息为text/xml或application/json的请求
* Spring 提供了多个HTTP 信息转换器
	* MappingJacksonHttpMessageConverter
		* 自动添加的，实现JSON信息的互相转换， Jackson JSON Processor
* @RequestBody 也可以添加在传入的数据上
	
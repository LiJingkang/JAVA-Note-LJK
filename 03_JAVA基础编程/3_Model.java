    * bean类：
            是一些实体类，包括 viewbean, databean 等等。
    * action类：
            可作为接收显示层的数据，连接显示层和业务逻辑实现层的控制层。
    * model类：
            MVC 中 model 层就是 Dao层。
            在 java 中无特殊含义就是 模块。
    * util类：
            工具类
    * dao:
            数据库操作类。对数据库进行曾删改查等操作

* JAVA SpringMVC 里的model 和 java里的session有什么区别？
    * 其实model和session都是存放信息的地方，
        不同的地方就是他们的生命周期不同，model是request级别的
        session是会话
        既然 model是request级，直接用request域好了为什么还用model呢。
    * ModelandView是将  
        视图信息和数据封装到一起的，spring来解析 ModelandView 中的信息，包括视图和数据，
        然后将数据 set到request里面，并且根据 model里面的视图信息
        以及spring mvc的配置让 request进行跳转
    * /chatRecord.doSearch
        Model model // 传入一个 Model
        model.addAttribute("polices", polices); // 将对应的内容放入 Model 中
        return model; // 再直接返回
    * main 里面调用 传入一个 Model 
        在main 里面返回的是一个 String 页面。

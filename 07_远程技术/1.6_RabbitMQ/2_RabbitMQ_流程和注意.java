* RabbitMQ 实现原理
    http://www.cnblogs.com/mingaixin/archive/2012/10/26/2741624.html
* RabbitMQ基础概念详细介绍 
    http://blog.csdn.net/whycold/article/details/41119807    
    http://melin.iteye.com/blog/691265


* Channel channel
    doSend 里面发出消息，他会检测 exchange routingKey 是否为空，如果为空则设置为 this.exchange this.routingKey
        而 this.exchange 和 this.routingKey 为 RabbitTemplate 的私有属性。
        * 我们在启动的时候设置 
              @Value("${prisonId}_${mq.exchange.trigger}")
              protected String triggerExchange;

              protected static final String ROUTING_KEY = "#";

            * RabbitmqConfiguration 和 RabbitmqExchangeConfiguration 继承了  

        * 在 @Configuration 里面设置  Spring 启动的时候自动加载了 @Bean， 并且设置了 this.exchange this.routingKey 等。
                我们之后使用的时候都是 Spring 自动注入的，设置好的 RabbitTemplate
                      @Bean(name = "triggerRabbitTemplate")
                      public RabbitTemplate triggerRabbitTemplate(
                          @Qualifier("defaultConnectionFactory") ConnectionFactory connectionFactory) {
                        if (needMessage) {
                          RabbitTemplate template = new RabbitTemplate();
                          template.setConnectionFactory(connectionFactory);
                            // 设置 connectionFactory
                          template.setExchange(triggerExchange);
                            // 设置 triggerExchage
                          template.setMessageConverter(new Jackson2JsonMessageConverter());
                            // 设置 Json 解析
                          return template;
                        }
                        return null;
                      }
        * 设置了一个默认的连接工厂和一个默认的 Exchange
            RabbitmqExchangeConfiguration   
                               
    // 发出的消息
    Cached Rabbit Channel: 
        AMQChannel(
                  amqp://nwt-jc@127.0.0.1:5672//dahua,1), 
                  conn: Proxy@46c9c3 Shared Rabbit 
            Connection: SimpleConnection@1d3793b 
                            [
                                 delegate = amqp://nwt-jc@127.0.0.1:5672//dahua, 
                                localPort = 55732
                            ]

————————————————————————————————————————————————————————————————————————————————————————
    * 
        ---> Exchange ---> Queue 没问题。
    * 
        生产者 ---> 
            mq.username=nwt-jc 
            mq.password=rxJ$7FW0
            mq.host=127.0.0.1
            mq.port=5672
            mq.vhosts=/dahua // 确定虚拟机
            mq.exchange.trigger=trigger // Exchange
        
        服务器 ---> 
            负责管理用户 管理 vhosts/Exchange/Queue 等。
            生产者 和 消费者 实际上都是两个客户端
            一个制造消息 
            一个消费消息    

        消费者 --->
            host = 127.0.0.1 // ip
            port = 5672 // 端口号
            username = nwt-jc // 用户名
            password = rxJ$7FW0 // 用户名密码
            virtualHost = /dahua // 虚拟机 
            exchangeName = 330100111_trigger // Exchange 交换器

            queueName = hik_pi // Exchange 绑定的 Queue
            routingKey =  // Exchange 分配消息 的一个 Key

————————————————————————————————————————————————————————————————————————————————————————
* JC Rabbit 发出通知流程。(框架源代码)
* RabbitMQ 设置
    * triggerExchange = 
        ${prisonId}_${mq.exchange.trigger}
            330100111_trigger  // topic


* triggerRabbitTemplate.convertAndSend(object);
    object == SystemNotice 
        order 
        prisonId
        Map<String, Object> param

* convertAndSend(this.exchange, this.routingKey, object, (CorrelationData) null);
    private volatile String exchange = DEFAULT_EXCHANGE;
    private volatile String routingKey = DEFAULT_ROUTING_KEY;

    private static final String DEFAULT_EXCHANGE = "";
    private static final String DEFAULT_ROUTING_KEY = "";

* send(exchange, routingKey, convertMessageIfNecessary(object), correlationData);
    // convertMessageIfNecessary

* doSend(channel, exchange, routingKey, message, 
            RabbitTemplate.this.returnCallback != null
                && RabbitTemplate.this.mandatoryExpression.getValue(

                            RabbitTemplate.this.evaluationContext, message, Boolean.class),

                        correlationData);
                return null;    

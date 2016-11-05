* MQ 接入。
    * MQ 是什么
        MQ 是一种消息队列机制。
————————————————————————————————————————————————————————————————————————————————————————————————
一、rabbitMQ简介

1.1、rabbitMQ的优点（适用范围）
    1. 基于erlang语言开发具有高可用高并发的优点，适合集群服务器。
    2. 健壮、稳定、易用、跨平台、支持多种语言、文档齐全。
    3. 有消息确认机制和持久化机制，可靠性高。
    4. 开源
*　其他MQ的优势：
    1. Apache ActiveMQ曝光率最高，但是可能会丢消息。
    2. ZeroMQ延迟很低、支持灵活拓扑，但是不支持消息持久化和崩溃恢复。

1.2、几个概念说明
    Producer & Consumer
    Producer 指的是消息生产者，Consumer消息的消费者。

* Queue
    消息队列，提供了FIFO的处理机制，具有缓存消息的能力。
    Rabbitmq中，队列消息可以设置为持久化，临时或者自动删除。

    设置为持久化的队列，Queue 中的消息会在 Server本地硬盘存储一份，防止系统 Crash，数据丢失
    设置为临时队列，Queue 中的数据在系统重启之后就会丢失
    设置为自动删除的队列，当不存在用户连接到 Server，队列中的数据会被自动删除Exchange

* Exchange类似于数据通信网络中的交换机，提供消息路由策略。
    Rabbitmq中，Producer 不是通过信道直接将消息发送给 Queue，
    而是先发送给 Exchange。

    一个 Exchange可以和多个 Queue进行绑定，producer在传递消息的时候，
        会传递一个 ROUTING_KEY，Exchange会根据这个 ROUTING_KEY 按照特定的路由算法，
        将消息路由给指定的queue。
    
    和Queue一样，Exchange也可设置为 持久化，临时或者自动删除。

    Exchange有4种类型：
        direct(默认)，
        fanout, 
        topic, 和
        headers，不同类型的Exchange转发消息的策略有所区别：
    Direct
        直接交换器，工作方式类似于单播，Exchange会将消息发送完全匹配 ROUTING_KEY 的 Queue
    fanout
        广播是式交换器，不管消息的 ROUTING_KEY 设置为什么，
        Exchange 都会将消息转发给所有绑定的 Queue。
    topic
        主题交换器，工作方式类似于组播，
        Exchange 会将消息转发和 ROUTING_KEY 匹配模式相同的所有队列，
        比如，ROUTING_KEY为 user.stock的Message会转发给绑定匹配模式
            为 * .stock,user.stock， * . * 和#.user.stock.#的队列。
            （ * 表是匹配一个任意词组，#表示匹配0个或多个词组）
    headers
        消息体的header匹配（ignore）
    Binding
        所谓绑定就是将一个特定的 Exchange 和一个特定的 Queue 绑定起来。
        Exchange 和Queue的绑定可以是多对多的关系。
    virtual host
        在 Rabbitmq Server上可以创建多个虚拟的 Message broker，
        又叫做virtual hosts (vhosts)。
        每一个vhost本质上是一个 mini-rabbitmq Server，
        分别管理各自的 Exchange，和bindings。vhost相当于物理的server，
        可以为不同app提供边界隔离，使得应用安全的运行在不同的vhost实例上，相互之间不会干扰。

        Producer和consumer连接rabbit server需要指定一个vhost。

1.3、消息队列的使用过程
    1. 客户端连接到消息队列服务器，打开一个 Channel。
    2. 客户端声明一个 Exchange，并设置相关属性。
    3. 客户端声明一个 Queue，并设置相关属性。
    4. 客户端使用 Routing key，在 Exchange和 Queue之间建立好绑定关系。
    5. 客户端投递消息到 Exchange。
    6. Exchange接收到消息后，就根据消息的 key 和已经设置的binding，
        进行消息路由，将消息投递到一个或多个队列里
————————————————————————————————————————————————————————————————————————————————————————————————
* JC - 接入设置
* config.properties 文件中配置
    mq.username=nwt-jc
    mq.password=rxJ$7FW0
    mq.host=127.0.0.1
    mq.port=5672
    mq.vhosts=/dahua
    mq.exchange.trigger=trigger
    need.message=true
    need.task=true
* 获取 properties 里面的内容
    RabbitProperties.java
* 设置 静态变量。其他设置继承的时候使用。  
    RabbitmqConstant.java
        @PropertySource("classpath:config.properties")
        public class RabbitmqConstant {

          @Value("${need.message}")
          protected boolean needMessage;

          @Value("${prisonId}_${mq.exchange.trigger}")
          protected String triggerExchange;

          protected static final String ROUTING_KEY = "#";
        }
* 设置
    RabbitmqConfiguration.java
        @Configuration
        public class RabbitmqConfiguration extends RabbitmqConstant {
          @Autowired
          private RabbitProperties props;
          /**
           * 默认连接工厂.
           * 
           * @return ConnectionFactory
           */
          @Bean(name = "defaultConnectionFactory")
          public ConnectionFactory defaultConnectionFactory() {
            CachingConnectionFactory cf = new CachingConnectionFactory();
            cf.setAddresses(this.props.getAddresses());
            cf.setUsername(this.props.getUsername());
            cf.setPassword(this.props.getPassword());
            cf.setVirtualHost(this.props.getVirtualHost());
            return cf;
          }
          @Bean
          public AmqpAdmin amqpAdmin() {
            return new RabbitAdmin(defaultConnectionFactory());
          }
* Exchange 设置
    RabbitmqExchangeConfiguration.java
        @Configuration
        public class RabbitmqExchangeConfiguration extends RabbitmqConstant {

          @Bean(name = "triggerExchange")
          TopicExchange triggerExchange() {
            if (needMessage) {
              return new TopicExchange(triggerExchange, true, false);
            }
            return null;
          }
        }          
* Template 设置
    RabbitTemplateConfiguration.java
        @Configuration
        @PropertySource("classpath:config.properties")
        public class RabbitTemplateConfiguration {
          @Value("${need.message}")
          private boolean needMessage;

          @Value("${prisonId}_${mq.exchange.trigger}")
          private String triggerExchange;
          /**
           * trigger template.
           * 
           * @param connectionFactory connectionFactory
           * @return trigger template
           */
          @Bean(name = "triggerRabbitTemplate")
          public RabbitTemplate triggerRabbitTemplate(
              @Qualifier("defaultConnectionFactory") ConnectionFactory connectionFactory) {
            if (needMessage) { // 如果判断需要队列
              RabbitTemplate template = new RabbitTemplate();
              template.setConnectionFactory(connectionFactory); // 设置连接工厂
              template.setExchange(triggerExchange); // 设置 Exchange
              template.setMessageConverter(new Jackson2JsonMessageConverter()); // 设置 Json 工具
              return template;
            }
            return null;
          }
        }

* ConnectionFactory 设置     

* 然后 MessageProducer 定义了接口   
* 在 Service 层的 TriggerProducer 中 实现
    @Autowired // 自动加载工具的 RabbitTemplate 
    private RabbitTemplate triggerRabbitTemplate;

    * 之后调用 triggerRabbitTemplate 来发送消息

          @Override
          public void sendMessage(String routingKey, Object object) {
            if (!needMessage) {
              return;
            }
            if (object == null || StringUtils.isBlank(routingKey)) {
              return;
            }
            SystemNotice notice = (SystemNotice) object;
            if (StringUtils.isBlank(notice.getPrisonId())) {
              return;
            }
            triggerRabbitTemplate.convertAndSend(routingKey, object);
            logger.debug("发送trigger消息：" + notice.toString());
          }    
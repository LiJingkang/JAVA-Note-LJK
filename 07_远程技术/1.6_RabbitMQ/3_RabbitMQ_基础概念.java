    你是否遇到过两个（多个）系统间需要通过定时任务来同步某些数据？
    你是否在为异构系统的不同进程间相互调用、通讯的问题而苦恼、挣扎？
        如果是，那么恭喜你，消息服务让你可以很轻松地解决这些问题。
    消息服务擅长于解决 多系统、异构系统间 的 数据交换（消息通知/通讯）问题，
        你也可以把它用于系统间服务的相互调用（RPC）。本文将要介绍的RabbitMQ就是当前最主流的消息中间件之一。

* RabbitMQ简介
    AMQP，即 Advanced Message Queuing Protocol，高级消息队列协议，是应用层协议的一个开放标准，为面向消息的中间件设计。
    消息中间件主要用于组件之间的解耦，消息的发送者无需知道消息使用者的存在，反之亦然。

    AMQP的主要特征是面向消息、队列、路由（包括点对点和发布/订阅）、可靠性、安全。

    RabbitMQ是一个开源的AMQP实现，服务器端用 Erlang 语言编写，支持多种客户端，
        如：Python、Ruby、.NET、Java、JMS、C、PHP、ActionScript、XMPP、STOMP等，支持AJAX。
            用于在分布式系统中存储转发消息，在易用性、扩展性、高可用性等方面表现不俗。
    下面将重点介绍RabbitMQ中的一些基础概念，了解了这些概念，是使用好RabbitMQ的基础。

* ConnectionFactory、Connection、Channel
    ConnectionFactory、
    Connection、
    Channel 都是 RabbitMQ 对外提供的 API 中最基本的对象。

    Connection是RabbitMQ的 socket 链接，它封装了 socket 协议相关部分逻辑。 
    ConnectionFactory为Connection的制造工厂。

    * Channel是我们与RabbitMQ打交道的最重要的一个接口，
        我们大部分的业务操作是在 Channel 这个接口中完成的，
        包括定义 Queue、定义 Exchange、绑定 Queue与 Exchange、发布消息等。
* Queue
    Queue（队列）是RabbitMQ的内部对象，用于存储消息，用下图表示。
    queue

    RabbitMQ中的消息都只能存储在Queue中，生产者（下图中的P）生产消息并 最终投递到Queue中，
        消费者（下图中的C）可以从Queue中获取消息并消费。


    多个消费者可以订阅同一个Queue，这时Queue中的消息会被平均分摊给多个消费者进行处理，而不是每个消费者都收到所有的消息并处理。

* Message Acknowledgment
    在实际应用中，可能会发生消费者收到 Queue中的消息，但没有处理完成就宕机（或出现其他意外）的情况，这种情况下就可能会导致消息丢失。
    为了避免这种情况发生，我们可以要求消费者在消费完消息后发送一个回执给 RabbitMQ，
    RabbitMQ收到消息回执（Message acknowledgment）后才将该消息从 Queue 中移除；

    如果 RabbitMQ没有收到回执并检测到消费者的RabbitMQ连接断开，则RabbitMQ会将该消息发送给其他消费者（如果存在多个消费者）进行处理。

    这里不存在timeout概念，一个消费者处理消息时间再长也不会导致该消息被发送给其他消费者，除非它的 RabbitMQ连接断开。
    
    这里会产生另外一个问题，如果我们的开发人员在处理完业务逻辑后，忘记发送回执给RabbitMQ，
        这将会导致严重的bug——Queue中堆积的消息会越来越多；
    消费者重启后会重复消费这些消息并重复执行业务逻辑…

    另外 pub message 是没有 ack 的。

* Message Durability
    如果我们希望即使在 RabbitMQ 服务重启的情况下，也不会丢失消息，我们可以将 Queue与Message都设置为可持久化的（durable），
    这样可以保证绝大部分情况下我们的 RabbitMQ消息不会丢失。

    但依然解决不了小概率丢失事件的发生（比如RabbitMQ服务器已经接收到生产者的消息，但还没来得及持久化该消息时RabbitMQ服务器就断电了），
    如果我们需要对这种小概率事件也要管理起来，那么我们要用到事务。由于这里仅为 RabbitMQ的简单介绍，所以这里将不讲解 RabbitMQ相关的事务。

* Prefetch Count
    前面我们讲到如果有多个消费者同时订阅同一个Queue中的消息，Queue中的消息会被平摊给多个消费者。这时如果每个消息的处理时间不同，就有可能会导致某些消费者一直在忙，而另外一些消费者很快就处理完手头工作并一直空闲的情况。我们可以通过设置prefetchCount来限制Queue每次发送给每个消费者的消息数，比如我们设置prefetchCount=1，则Queue每次给每个消费者发送一条消息；消费者处理完这条消息后Queue会再给该消费者发送一条消息。

* Exchange
    在上一节我们看到生产者将消息投递到Queue中，实际上这在RabbitMQ中这种事情永远都不会发生。
    实际的情况是，生产者将消息发送到Exchange（交换器，下图中的X），由Exchange将消息路由到一个或多个Queue中（或者丢弃）。

    Exchange是按照什么逻辑将消息路由到Queue的？这个将在Binding一节介绍。
    RabbitMQ 中的 Exchange有四种类型，不同的类型有着不同的路由策略，这将在 Exchange Types一节介绍。

* Routing Key
    生产者在将消息发送给 Exchange的时候，一般会指定一个 routing key，来指定这个消息的'路由规则'，
    而这个 Routing key需要与 Exchange Type及 Binding key联合使用才能最终生效。

    在 Exchange Type与 Binding key固定的情况下（在正常使用时一般这些内容都是固定配置好的），
        我们的生产者就可以在发送消息给 Exchange时，通过指定 Routing Key 来决定消息流向哪里。
    RabbitMQ 为 Routing key设定的长度限制为 255 bytes。

* Binding
    RabbitMQ中通过 Binding将 Exchange与Queue关联起来，这样 RabbitMQ就知道如何正确地将消息路由到指定的 Queue了。

* Binding Key

    在绑定（Binding）Exchange与Queue的同时，一般会指定一个 Binding key；
    消费者将消息发送给 Exchange时，一般会指定一个 Routing key；
    当 Binding key与 Routing key相匹配时，消息将会被路由到对应的 Queue中。这个将在Exchange Types章节会列举实际的例子加以说明。

    在绑定多个 Queue到同一个 Exchange的时候，这些 Binding允许使用相同的 Binding key。
    Binding Key 并不是在所有情况下都生效，它依赖于 Exchange Type，比如
        Fanout 类型的 Exchange 就会无视 Binding Key，而是将消息路由到所有绑定到该 Exchange 的 Queue。

* Exchange Types
    RabbitMQ常用的 Exchange Type有
        fanout、
        direct、
        topic、
        headers 这四种（AMQP规范里还提到两种Exchange Type，分别为system与自定义，这里不予以描述），下面分别进行介绍。
    
    * fanout
        fanout 类型的 Exchange路由规则非常简单，它会把所有发送到该 Exchange 的消息路由到所有与它绑定的 Queue中。
    * direct
        direct 类型的 Exchange路由规则也很简单，它会把消息路由到那些 binding key 与 routing key 完全匹配的 Queue中。
    * Topic
        // 330100111_trigger topic
        // 在 JC 中发出消息的时候只指定了 Exchanges 没有指定 Binding key和 Routing key
        前面讲到 direct类型的 Exchange路由规则是完全匹配 Binding key与 Routing key，但这种严格的匹配方式在很多情况下不能满足实际业务需求。
        Topic 类型的Exchange在匹配规则上进行了扩展，它与 direct类型的 Exchage相似，

        也是将消息路由到 Binding key与 Routing key相匹配的 Queue中，但这里的匹配规则有些不同，它约定：

        Routing Key 为一个句点号 ". " 分隔的字符串（我们将被句点号". "分隔开的每一段独立的字符串称为一个单词），
            如"stock.usd.nyse"、"nyse.vmw"、"quick.orange.rabbit"

        Binding Key 与 Routing Key 一样也是句点号 ". " 分隔的字符串

        Binding Key 中可以存在两种特殊字符"*"与"#"，用于做模糊匹配，
            其中"*"用于匹配一个单词，
                "#"用于匹配多个单词（可以是零个）

        以上图中的配置为例，
            Routing Key = "quick.orange.rabbit" 的消息会同时路由到Q1与Q2，
            Routing Key = "lazy.orange.fox" 的消息会路由到Q1，
            Routing Key = "lazy.brown.fox" 的消息会路由到Q2，
            Routing Key = "lazy.pink.rabbit" 的消息会路由到Q2（只会投递给Q2一次，虽然这个routingKey与Q2的两个bindingKey都匹配）；
            Routing Key = "quick.brown.fox"、
            Routing Key = "orange"、
            Routing Key = "quick.orange.male.rabbit" 的消息将会被丢弃，因为它们没有匹配任何 Binding Key。
    * headers
        headers 类型的 Exchange不依赖于 Routing key与 Binding Key的匹配规则来路由消息，
            而是根据发送的消息内容中的 Headers属性进行匹配。
        在绑定 Queue与 Exchange时指定一组键值对；
        当消息发送到 Exchange时，RabbitMQ会取到该消息的headers（也是一个键值对的形式），
            对比其中的键值对是否完全匹配 Queue与 Exchange绑定时指定的键值对；
        如果完全匹配则消息会路由到该 Queue，否则不会路由到该 Queue。
            该类型的 Exchange没有用到过（不过也应该很有用武之地），所以不做介绍。

* RPC
    MQ本身是基于异步的消息处理，前面的示例中所有的生产者（P）将消息发送到RabbitMQ后不会知道消费者（C）处理
        成功或者失败（甚至连有没有消费者来处理这条消息都不知道）。
    但实际的应用场景中，我们很可能需要一些同步处理，需要同步等待服务端将我的消息处理完成后再进行下一步处理。
        这相当于RPC（Remote Procedure Call，远程过程调用）。在RabbitMQ中也支持RPC。

    RabbitMQ中实现RPC的机制是：
        客户端发送请求（消息）时，在消息的属性（MessageProperties，在 AMQP 协议中定义了14中 properties，这些属性会随着消息一起发送）
        中设置两个值 replyTo（一个 Queue 名称，用于告诉服务器处理完成后将通知我的消息发送到这个 Queue 中）
        和 correlationId（此次请求的标识号，服务器处理完成后需要将此属性返还，客户端将根据这个id了解哪条请求被成功执行了或执行失败）
    
    服务器端收到消息并处理
        服务器端处理完消息后，将生成一条应答消息到 replyTo 指定的 Queue，同时带上 CorrelationId属性
    
    客户端之前已订阅 ReplyTo指定的Queue，从中收到服务器的应答消息后，根据其中的 CorrelationId属性分析哪条请求被执行了，根据执行结果进行后续业务处理

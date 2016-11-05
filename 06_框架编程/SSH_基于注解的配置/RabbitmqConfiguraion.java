

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

  ////////////////////////////////////////////////////////////////////

  @Bean
  SimpleMessageListenerContainer chatStatusContainer(
      @Qualifier("defaultConnectionFactory") ConnectionFactory connectionFactory,
      @Qualifier("chatStatusListenerAdapter") MessageListenerAdapter listenerAdapter) {
    if (needMessage) {
      SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
      container.setConnectionFactory(connectionFactory);
      container.setQueueNames(chatStatusQueue);
      container.setMessageListener(listenerAdapter);
      return container;
    }
    return null;
  }

  @Bean(name = "chatStatusListenerAdapter")
  MessageListenerAdapter chatStatusListenerAdapter(
      @Qualifier("chatStatusReceiver") ChatStatusReceiver receiver) {
    if (needMessage) {
      return new MessageListenerAdapter(receiver, "receiveMessage");
    }
    return null;
  }

  ////////////////////////////////////////////////////////////////////


  @Bean
  SimpleMessageListenerContainer terminalStatusContainer(
      @Qualifier("defaultConnectionFactory") ConnectionFactory connectionFactory,
      @Qualifier("terminalStatusListenerAdapter") MessageListenerAdapter listenerAdapter) {
    if (needMessage) {
      SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
      container.setConnectionFactory(connectionFactory);
      container.setQueueNames(terminalStatusQueue);
      container.setMessageListener(listenerAdapter);
      return container;
    }
    return null;
  }

  @Bean(name = "terminalStatusListenerAdapter")
  MessageListenerAdapter terminalStatusListenerAdapter(
      @Qualifier("terminalStatusReceiver") TerminalStatusReceiver receiver) {
    if (needMessage) {
      return new MessageListenerAdapter(receiver, "receiveMessage");
    }
    return null;
  }

  ////////////////////////////////////////////////////////////////////



  @Bean
  SimpleMessageListenerContainer triggerContainer(
      @Qualifier("defaultConnectionFactory") ConnectionFactory connectionFactory,
      @Qualifier("triggerListenerAdapter") MessageListenerAdapter listenerAdapter) {
    if (needMessage) {
      SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
      container.setConnectionFactory(connectionFactory);
      container.setQueueNames(triggerQueue);
      container.setMessageListener(listenerAdapter);
      return container;
    }
    return null;
  }

  @Bean(name = "triggerListenerAdapter")
  MessageListenerAdapter triggerListenerAdapter(
      @Qualifier("triggerReceiver") TriggerReceiver receiver) {
    if (needMessage) {
      return new MessageListenerAdapter(receiver, "receiveMessage");
    }
    return null;
  }

}

@Configuration
public class RabbitmqBindingConfiguration extends RabbitmqConstant {

  @Bean
  Binding chatStatusBinding(@Qualifier("chatStatusQueue") Queue chatStatusQueue,
      @Qualifier("chatStatusExchange") TopicExchange chatStatusExchange) {
    if (needMessage) {
      return BindingBuilder.bind(chatStatusQueue).to(chatStatusExchange).with(ROUTING_KEY);
    }
    return null;
  }

  @Bean
  Binding terminalStatusBinding(@Qualifier("terminalStatusQueue") Queue terminalStatusQueue,
      @Qualifier("terminalStatusExchange") TopicExchange terminalStatusExchange) {
    if (needMessage) {
      return BindingBuilder.bind(terminalStatusQueue).to(terminalStatusExchange).with(ROUTING_KEY);
    }
    return null;
  }


  @Bean
  Binding triggerBinding(@Qualifier("triggerQueue") Queue triggerQueue,
      @Qualifier("triggerExchange") TopicExchange triggerExchange) {
    if (needMessage) {
      return BindingBuilder.bind(triggerQueue).to(triggerExchange).with(ROUTING_KEY);
    }
    return null;
  }

}

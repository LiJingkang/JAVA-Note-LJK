@Configuration
public class RabbitmqExchangeConfiguration extends RabbitmqConstant {

  @Bean(name = "chatStatusExchange")
  TopicExchange chatStatusExchange() {
    if (needMessage) {
      return new TopicExchange(chatStatusExchange, true, false);
    }
    return null;
  }

  @Bean(name = "terminalStatusExchange")
  TopicExchange terminalStatusExchange() {
    if (needMessage) {
      return new TopicExchange(terminalStatusExchange, true, false);
    }
    return null;
  }


  @Bean(name = "triggerExchange")
  TopicExchange triggerExchange() {
    if (needMessage) {
      return new TopicExchange(triggerExchange, true, false);
    }
    return null;
  }
}

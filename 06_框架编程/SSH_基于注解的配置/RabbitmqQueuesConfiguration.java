* Rabbitmq 队列配置


@Configuration
public class RabbitmqQueuesConfiguration extends RabbitmqConfiguration {

  @Bean(name = "chatStatusQueue")
  Queue chatStatusQueue() {
    if (needMessage) {
      return new Queue(chatStatusQueue, true, false, false);
    }
    return null;
  }

  @Bean(name = "terminalStatusQueue")
  Queue terminalStatusQueue() {
    if (needMessage) {
      return new Queue(terminalStatusQueue, true, false, false);
    }
    return null;
  }

  @Bean(name = "triggerQueue")
  Queue triggerQueue() {
    if (needMessage) {
      return new Queue(triggerQueue, true, false, false);
    }
    return null;
  }
}

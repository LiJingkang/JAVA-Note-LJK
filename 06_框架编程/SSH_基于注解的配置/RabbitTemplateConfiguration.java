@Configuration
public class RabbitTemplateConfiguration {

  @Value("${need.message}")
  private boolean needMessage;

  @Value("${prisonId}_${mq.exchange.terminalStatus}")
  private String terminalStatusExchange;

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
    if (needMessage) {
      RabbitTemplate template = new RabbitTemplate();
      template.setConnectionFactory(connectionFactory);
      template.setExchange(triggerExchange);
      template.setMessageConverter(new JsonMessageConverter());
      return template;
    }
    return null;
  }

  /**
   * terminalStatus template.
   * 
   * @param connectionFactory connectionFactory
   * @return terminalStatus template
   */
  @Bean(name = "terminalStatusRabbitTemplate")
  public RabbitTemplate terminalStatusRabbitTemplate(
      @Qualifier("defaultConnectionFactory") ConnectionFactory connectionFactory) {
    if (needMessage) {
      RabbitTemplate template = new RabbitTemplate();
      template.setConnectionFactory(connectionFactory);
      template.setExchange(terminalStatusExchange);
      template.setMessageConverter(new JsonMessageConverter());
      return template;
    }
    return null;
  }
}

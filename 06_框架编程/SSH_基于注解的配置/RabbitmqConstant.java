package com.newings.configuration;

import org.springframework.beans.factory.annotation.Value;

public class RabbitmqConstant {

  @Value("${need.message}")
  protected boolean needMessage;

  @Value("${prisonId}_${mq.exchange.chatStatus}")
  protected String chatStatusExchange;

  @Value("${prisonId}_${mq.exchange.terminalStatus}")
  protected String terminalStatusExchange;

  @Value("${prisonId}_${mq.exchange.trigger}")
  protected String triggerExchange;

  @Value("${prisonId}_${mq.queue.chatStatus}")
  protected String chatStatusQueue;

  @Value("${prisonId}_${mq.queue.terminalStatus}")
  protected String terminalStatusQueue;

  @Value("${prisonId}_${mq.queue.trigger}")
  protected String triggerQueue;

  protected static final String ROUTING_KEY = "#";
}

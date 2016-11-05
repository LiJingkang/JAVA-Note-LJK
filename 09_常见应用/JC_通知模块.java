* 启动任务
    @Service
    public class TmpMqMessagePushTask extends BaseTask implements Runnable 
    // 这个 Service 实现了 Runnable
    // 实现runable或者继承thread就算是线程类了
    // run()方法在每个线程启动时都会首先执行，启动几个线程就有几个线程去执行这个run()方法。
    // run()方法是Runnabl接口的抽象方法。
    // 实现Runnabl接口就必须实现它的方法，而这个方法就是线程的入口。 
* 实现 run() 方法
    在 run 方法里面进行一些 判断和业务。 然后通过
        public class TriggerProducer implements MessageProducer {}
    
    来发出通知        
* TriggerProducer 通过    
    // MessageProducer 是一个接口。TriggerProducer 实现了这个接口
* 通过 RabbitTemplate 来发出通知
    @Autowired
    private RabbitTemplate triggerRabbitTemplate;    
    // 实现
    triggerRabbitTemplate.convertAndSend(routingKey, object);
    logger.debug("发送trigger消息：" + notice.toString());    
* 知识点
    java实现rabbitmq消息的发送接受。
    http://blog.csdn.net/sdyy321/article/details/9241445     

————————————————————————————————————————————————————————————————————————————————————————————————————————
* SystemNotice.java
    这个文件里面放了一些 通知用到的 静态字符串  
          public static final String ORDER_MORTGAGE = "mortgage";

* 通知模块
    TmpMqMessagePushTask extends BaseTask implements Runnable
    
    * 继承和实现关系    
      @Override
      public void run() {
        String arraignPath = lastSynchronizeTimeFilePath + ARRAIGN_FILE_NAME;
        logger.info("提讯查询...");
        String arraignTimeStr = getLastSynchronizeTime(arraignPath);
        List<MessagePushDto> arraigns = repository.findArraign4MeetingMqPush(prisonId, arraignTimeStr);
        if (CollectionUtils.isNotEmpty(arraigns)) {
          for (MessagePushDto dto : arraigns) {
            sendMessage2Terminal(dto, SystemNotice.ORDER_ARRAIGN);
          }
          updateLastSynchronizeTime(arraignPath, arraigns.get(0).getLastUpdateTimeStr());
        }

        String mortgagePath = lastSynchronizeTimeFilePath + MORTGAGE_FILE_NAME;
        logger.info("提押查询...");
        String mortgageTimeStr = getLastSynchronizeTime(mortgagePath);
        List<MessagePushDto> mortgages =
            repository.findMortgage4MeetingMqPush(prisonId, mortgageTimeStr);
        if (CollectionUtils.isNotEmpty(mortgages)) {
          for (MessagePushDto dto : mortgages) {
            sendMessage2Terminal(dto, SystemNotice.ORDER_MORTGAGE);
          }
          updateLastSynchronizeTime(mortgagePath, mortgages.get(0).getLastUpdateTimeStr());
        }
      }

* 用来发送通知的          
        @Service
        public class TriggerProducer implements MessageProducer {

          // private RabbitTemplate rabbitTemplate;
          @Autowired
          private RabbitTemplate triggerRabbitTemplate;

          private final Logger logger = LoggerFactory.getLogger(TriggerProducer.class);

          @Value("${need.message}")
          private boolean needMessage;

          @Override
          public void sendMessage(Object object) {
            if (!needMessage) {
              return;
            }
            if (object == null) {
              // logger.error("message to send is null");
              return;
            }
            SystemNotice notice = (SystemNotice) object;
            if (StringUtils.isBlank(notice.getPrisonId())) {
              logger.error("prison id to send is null");
              return;
            }
            triggerRabbitTemplate.convertAndSend(object);
            logger.debug("发送trigger消息：" + notice.toString());
          }

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
        }
————————————————————————————————————————————————————————————————————————————————————————————————————————
* 一个例子

* 运行
  public void run() {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.HOUR_OF_DAY, startHour);
    calendar.set(Calendar.MINUTE, startMimute);
    calendar.set(Calendar.SECOND, startSecond);
    Date startTime = calendar.getTime();

    calendar.set(Calendar.HOUR_OF_DAY, endHour);
    calendar.set(Calendar.MINUTE, endMimute);
    calendar.set(Calendar.SECOND, endSecond);
    Date endTime = calendar.getTime();


    List<PoliceInPrisonDto> dtos = criminalDao.findNoSwipeRecordDormCodes(startTime, endTime);
    for (PoliceInPrisonDto dto : dtos) {
      if (StringUtils.isBlank(dto.getActualInDormCode())) {
        // 判断以后发出通知
        sendPoliceInPrisonErrorMessages(null, dto.getShouldInDormCode(),
            SystemNotice.ERROR_TYPE_LESS_THAN_2_TIMES_ONE_DAY);
      }
    }

* 调用的方法
  private void sendPoliceInPrisonErrorMessages(Police police, String dormCode, String errorType) {
    if (police == null) {
      systemNoticeService.sendPoliceInPrisonErrorMessages(null, null, dormCode, errorType);
    } else {
      systemNoticeService.sendPoliceInPrisonErrorMessages(police.getId(), police.getName(),
          dormCode, errorType);
    }
  }

* 发出通知
  public void sendPoliceInPrisonErrorMessages(String policeId, String policeName, String dormCode,
      String errorType) {
    String order = SystemNotice.ORDER_POLICE_IN_PRISON_ERROR;
    SystemNotice systemNotice = new SystemNotice();
    systemNotice.setPrisonId(prisonId);
    systemNotice.setOrder(order);

    Map<String, Object> param = new HashMap<>();
    if (StringUtils.isNotBlank(policeId)) {
      param.put("policeId", policeId);
    }
    if (StringUtils.isNotBlank(policeName)) {
      param.put("policeName", policeName);
    }
    param.put("dormCode", dormCode);
    param.put("time", new Date().getTime());
    param.put("errorType", errorType);
    systemNotice.setParam(param);
    triggerProducer.sendMessage(systemNotice);
    return;
  }

* 发出消息
  @Override
  public void sendMessage(Object object) {
    if (!needMessage) {
      return;
    }
    if (object == null) {
      // logger.error("message to send is null");
      return;
    }
    SystemNotice notice = (SystemNotice) object;
    if (StringUtils.isBlank(notice.getPrisonId())) {
      logger.error("prison id to send is null");
      return;
    }
    triggerRabbitTemplate.convertAndSend(object);
    logger.debug("发送trigger消息：" + notice.toString());
  }  
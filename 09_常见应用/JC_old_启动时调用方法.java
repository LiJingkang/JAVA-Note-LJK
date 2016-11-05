* 老 JC 调用方法
  @PostConstruct
  public void init() {
    NativeSetJNIEnv(); // 非本地方法 native 修饰
    int ret = 657920;
    if (NativeInitialize() == 0) {
      logger.info("Initialize JNI success ...");
      try {
        if (NativeSetMessageCallBack() != 0) {
          return;
        }
        logger.info("SetMessageCallBack success ...");
        ret = NativeConnect(config.getRegServer(), config.getLocalIp(), config.getLocalName(),
            config.getLocalPwd(), config.getAgent(), config.getBaseLogName(), config.getLogLevel(),
            config.getLocalPort(), config.getMinRtpPort(), config.getMaxRtpPort(),
            config.getExpire(), config.getTime4CallerTimeout());
        if (ret != 0) {
          logger.info("Connect server fail ... serverIp:{}, localIp:{}", config.getRegServer(),
              config.getLocalIp());
          return;
        }
        logger.info("Connect server success ... serverIp:{}, localIp:{}", config.getRegServer(),
            config.getLocalIp());
        if (1 == config.getCcsStatus()) {
          int result = NativeSetCcsMessageCallBack(config.getCcsIp(), config.getCcsPort());
          logger.info("SetCcsMessageCallBack ... result:{}", result);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
* 字典的缓存
    * DictUtils // 字典 工具类
        // DictService
        // dictRepository
    * initDictCache // 初始化字典缓存
          @PostConstruct
          public static GuavaCache initDictCache() {
            // 得到一个
            dictCache = (GuavaCache) cacheManager.getCache("dictCache");
            return dictCache;
          }
    * 在获取之前 在 CacheConfiguration cacheManager 里面已经定义了
        * 创建  
            SimpleCacheManager cacheManager = new SimpleCacheManager();
            GuavaCache dicts =
            new GuavaCache("dictCache", CacheBuilder.newBuilder().maximumSize(150000).build());
        * 设置    
            cacheManager.setCaches(Arrays.asList(dicts, terminalStatusCache, chatStatusCache,
                terminalIpCache, globalConfigCache, policeRightCache));
            cacheManager.initializeCaches();
            return cacheManager;
    * 初始化字典信息表
              @PostConstruct
              public void init() {
                if (dictCache == null) {
                  dictCache = initDictCache();
                }
                // 将字典信息放入缓存
                List<Dict> dictList = dictService.getAllDict();
                // 写好的字典Service 层 获取到所有的字典信息
                dictCache.getNativeCache().cleanUp();
                // 清空缓存
                for (Dict dict : dictList) { // 遍历字典
                  String key = dict.getName() + SEPARATE + dict.getCode();
                  // 设置 key
                  dictCache.put(key, dict.getItem());
                  // 将key 放入缓存
                }
                // int cacheSize = (dicts.getNativeCache()).getSize();
                // logger.info(cacheSize + "条字典信息放入缓存，其中" + (dictList.size() - cacheSize) + "条重复");
              }
    * 字典信息取得
              public static String getDictValue(String dictName, String code) { // 字典名  代码号 @return 代号值
                if (dictCache == null) {
                  dictCache = initDictCache();
                } // 如果为空，则新建
                if (StringUtils.isBlank(code)) {
                  return "";
                } // 如果传入的代码号为空 则返回空
                if (!code.contains(",")) { // 判断字符串里面是否包含有 "," 。如果没有则是单数据
                  ValueWrapper valueWrapper = dictCache.get(dictName + SEPARATE + code); // 根据code 和 dictName 获取对应的代号值 拼起来的是 KEY
                  if (null != valueWrapper) {
                    return (String) valueWrapper.get();
                  }
                } else {
                  String[] codes = code.split(",");
                  String result = "";
                  for (int i = 0; i < codes.length; i++) {
                    ValueWrapper valueWrapper = dictCache.get(dictName + SEPARATE + codes[i]);
                    result = result + (valueWrapper == null ? "" : ((String) valueWrapper.get())) + ",";
                  }
                  return result.length() == 0 ? "" : result.substring(0, result.length() - 1);
                }
                return "";
              }
——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
*  终端模块的缓存
    * TerminalIpService // 主方法
    * 准备数据
          @Autowired
          private EquipmentManageService equipmentManageService;

          private GuavaCache terminalIpCache; // 创建一个 缓存

          @Autowired
          private CacheManager cacheManager; // 创建一个 缓存管理器
          public static final String TERMINAL_IP_CACHE = "terminalIpCache"; // 静态名字
    * 初始化ip缓存.
          @PostConstruct
          public GuavaCache initTerminalIpCache() { // 初始化缓存
            // 通过 缓存管理器得到 终端缓存 是一个 GuavaCache
            terminalIpCache = (GuavaCache) cacheManager.getCache(TERMINAL_IP_CACHE);
            return terminalIpCache;
          }            
    * 初始化所有终端的ip.
          @PostConstruct
          public void initTerimialIps() {
            if (terminalIpCache == null) { //如果为空，则设置得到新的终端缓存
              terminalIpCache = initTerminalIpCache();
            }
            logger.debug("terminal ip cache init start");
            // 得到需要的终端 Ip 数据
            List<TerminalIp> terminalIps =
                equipmentManageService.findAllActive(EquipmentTypeEnums.termianl.getName());
            if (CollectionUtils.isNotEmpty(terminalIps)) {
              for (TerminalIp terminalIp : terminalIps) {
                put2cache(terminalIp); // 通过自己写的方法 将数据加入缓存
              }
            }
            logger.debug("terminal ip cache init end");
          }
    * 按照监所编号初始化所有该监所终端的ip.
          public void initTerimialIps(String prisonId) {
            if (terminalIpCache == null) {
              terminalIpCache = initTerminalIpCache();
            }
            logger.debug("terminal ip cache init start");
            List<TerminalIp> terminalIps = equipmentManageService.findAllActiveByPrisonId(prisonId,
                EquipmentTypeEnums.termianl.getName());
            if (CollectionUtils.isNotEmpty(terminalIps)) {
              for (TerminalIp terminalIp : terminalIps) {
                put2cache(terminalIp);
              }
            }
            logger.debug("terminal ip cache init end");
          }
   * 按照ip获取监舍号.
          // 初始化字典以后，对字典的使用
          public String getDormCodeByIp(String prisonId, String ip) {
            String cacheKey = prisonId + "_" + ip; // 拼接字典的 KEY
            String dormCode = getFromCache(cacheKey); // 通过key 得到 缓存里面对应的数据  value
            if (StringUtils.isBlank(dormCode)) { // 如果没有的话
              dormCode = equipmentManageService.findDormCodeByIp(prisonId, ip);
              if (StringUtils.isNotBlank(dormCode)) { // 获取以后放入缓存
                put2cache(cacheKey, dormCode);
              }
            }
            return dormCode;
          }
    * 放入字典
          private void put2cache(TerminalIp terminalIp) {
            // 调用另一个方法
            put2cache(terminalIp.getPrisonId() + "_" + terminalIp.getIp(), terminalIp.getDormCode());
          }

          private void put2cache(String key, String value) {
            if (terminalIpCache == null) {
              terminalIpCache = initTerminalIpCache();
            }
            terminalIpCache.put(key, value);
          }
    * 获取字典
          private String getFromCache(String key) {
            if (terminalIpCache == null) {
              return null;
            }
            return terminalIpCache.get(key, String.class);
          }
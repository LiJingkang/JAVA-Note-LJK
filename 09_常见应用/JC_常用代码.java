* save 接口
      /**
       * 保存用药记录.
       * 
       * @param medicineRecord 用药记录
       */
      public void save(MedicineRecord medicineRecord) {
        if (StringUtils.isBlank(medicineRecord.getId())) {
          medicineRecord.setId(new ObjectId().toHexString());
          medicineRecordRepository.insert(medicineRecord);
        } else {
          medicineRecordRepository.update(medicineRecord);

        }
      }
* findByXXXX 中 使用 search 中的综合查询
* 使用实体里面设定好的常量判断 switch
      public Page<Cash> findCash() {
        int prisonType = Integer.parseInt(prisonId.substring(7, 8));
        switch (prisonType) {
          case Prison.PRISONTYPE_PRISON: // Prison里面设置的 静态常量是 public static final 所以可以直接用.语法调用
            return cashRepository.findPrisonCash(prisonId, startTime, endTime, number, pageable);
          case Prison.PRISONTYPE_DETENTION:
            return cashRepository.findDetentionCash(prisonId, startTime, endTime, number, pageable);
          case Prison.PRISONTYPE_EDUCATION:
            return cashRepository.findEducationCash(prisonId, startTime, endTime, number, pageable);
          default:
            throw new IllegalArgumentException("prisonId type error");
        }
      }
* 返回值可以是 List<?>
        public List<?> showDetail() { // 当在Controller中不确定返回的List里面装的是什么时使用
                // 比如Service 层通过判断有可能有不同的实体
            return businessFlowService.showDetail(nodeId, bfQo);
        }

* 常量工具类 Constants
    * 里面存放的是一些 public static final 类型的常量，用来方便的使用不需要重复设置和定义。
        public static String XXX="XXX";  public static final String XXX="XXX";

* 自定义抛出异常
        if (StringUtils.isBlank(qo.getPrisonId())) {
          throw new IllegalArgumentException("监所号不能为空");
        }

* 自定义错误信息
    * List<ErrorMessage> 
        private List<ErrorMessage> checkBusinessLobby(List<ErrorMessage> message, ComeInfo comeInfo,
          PrisonPeopleInfo alter, String type) {
        if (message != null) {
          return message;
        } else {
          message = new ArrayList<ErrorMessage>(); // 新建ErrorMessage 
        }
        if (type.equals(Constants.BUSINESS_TAKE_BACK)) {
          if (StringUtils.isBlank(comeInfo.getLimit())) {
            addError(message, "limit", "男女不能关在同一监室！"); // 添加错误信息
          }
        }
* RequestMaaping 带参数
    * @RequestMapping(value = "/findCash")

* 返回一个页面
    // 在做返回一个页面的接口的时候，通常都要传入一个 Model model
    // 在 model 里面还有一个简单的例子。
          @RequestMapping("/page/main")
          @AccessRequired(value = false, type = AccessTypeEnum.pageAccess)
          public String main(Model model) { // 传入了一个Model Request级别的。注意它的生命周期
            doSearch(model, prisonId, policeId, chatType, dormCode, deviceType, startTime, endTime,
                pageable); // 调用同文件里面的其他方法。
                            // 这个方法返回了一个model 回来。有什么用呢？
                            // TODO 结合前端在研究一下。
            return "admin/chat_record"; // 返回一个页面。
          }
    * doSearch
            private Model doSearch(Model model, String prisonId, String policeId, String chatType,
              String dormCode, String deviceType, Date startTime, Date endTime, Pageable pageable) {
            Page<ChatRecordDto> chatRecords = chatRecordService.searchDto(prisonId, policeId, chatType,
                dormCode, deviceType, startTime, endTime, pageable);
            model.addAttribute("chatRecords", chatRecords.toPageInfo());

            List<Police> polices = policeService.findSimpleAllByPrisonId(prisonId);
            // 将对应的参数放入 model中返回
            model.addAttribute("polices", polices);
            model.addAttribute("prisonId", prisonId);
            return model;}          

* 比较复杂的返回类型
    * Map<String, List<?>>  
            // Map 是一个字典，里面放着键值对，key 对应的Value是List 
            Map<String, List<?>> failPeople = new HashMap<String, List<?>>();
            failPeople.put("failPrisoners", failPrisoners);
            failPeople.put("failPolices", failPolices);
    * Object 返回类型
        * 不确定返回类型，只知道返回一个实体的时候
            * return error;  ErrorMessage
    * List<byte[]>
        * List<byte[]> findPhotoById(String id);    
* 日志
    * private Logger logger = LoggerFactory.getLogger(BreakRuleService.class);
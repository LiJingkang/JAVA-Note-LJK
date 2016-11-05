* 字典查询
    * 字典 SQL 
        SELECT ITEM1
        FROM DIC_ITEMS
        WHERE dic_name = '行政区划'
        AND code = '642223'

* 

* 信息系统1.0 里面的相关字典相关业务
    * 代码
      public void setSecurityRelation(String securityRelation) {
        this.securityRelation = securityRelation;
        setSecurityRelationValue(DictUtils.getDictValue("家庭关系", securityRelation));
        // 在 编号属性的 set 方法里面设置 对应字典数值的 set 方法，来设置字典对应的属性。
      }

      public void setDangerLevel(String dangerLevel) {
        this.dangerLevel = dangerLevel;
        setDangerLevelValue(DictUtils.getDictValue("人员危险等级", dangerLevel));
      }
        * 全例子
            *     // 危险等级
                  private String dangerLevel;
                  private String dangerLevelValue; 
            *     public String getDangerLevel() {
                    return dangerLevel;
                  }
                  public void setDangerLevel(String dangerLevel) {
                    this.dangerLevel = dangerLevel;
                    setDangerLevelValue(DictUtils.getDictValue("人员危险等级(字典名称)", XXXXX传入参数));
                    // 关键
                  }
                  public String getDangerLevelValue() {
                    return dangerLevelValue;
                  }
                  public void setDangerLevelValue(String dangerLevelValue) {
                    this.dangerLevelValue = dangerLevelValue;
                  }

    * 字典
        * 公司的字典存在
        * DictDynaSqlProvider  // 查询字典的方法
        * "行政区划" ————> 户籍所在地

    * 公司查询 SQL
        SELECT D.DIC_NAME,D.CODE,D.ITEM1
        FROM TABLE_LIST T,FIELD_LIST F,DIC_ITEMS D
        WHERE T.TID=F.TID  AND F.TYPE='B' AND F.REFE_NAME=D.DIC_NAME
        AND d.dic_name LIKE '%人员危险等级%'
    * 查询 SQL
        SELECT dic_name, code, item1
        FROM DIC_ITEMS
        WHERE dic_name like '%行政区划%'
——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
* 通过字典数据 反查询 字典编号。
  // setHouseAddrValue(DictUtils.getDictValue("行政区划", houseAddr));


————————————
* 插入/修改
  * 
——————
* 输出
  * 最终输出的json  
    * 字典中，键值对的key由输出结果List中的成员属性来决定
    * key的内容由查询结果中 注解  @Results({@Result(column="",property="")}) 的反射决定
    * 如果在@Result 中没有反射所对应的内容，则键值对中为null
    * 如果@Result 中有内容，而返回体里面没有内容，这样会映射出错
    * @NotBlank
——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
* 业务规范
  * save
    * @controller 中返回的是一个boolean,成功返回true,失败不处理
      @RequestMapping("/save")
      @ResponseBody
      public boolean save(TourRoute tourRoute) {
        tourRouteService.save(tourRoute);
        return true;
      }
    * @service 
      public void save(TourRoute tourRoute) {
        if (tourRoute == null) {  // 模型类的判断 == null
            // 先对传入的数据进行判断
            // 如果为空则直接返回
          return;
        }
        if (StringUtils.isBlank(tourRoute.getId())) { // 判断是否有id
          insert(tourRoute);  // 如果没有插入
        } else {
          update(tourRoute);  // 如果有，则更新
        }
        return; // 返回值为 void 类型的函数 也需要使用return进行返回
      }
——————
* 比对指纹，错误判断
        /**
         * 在押人员指纹比对.
         * 
         * @param raw 指纹数据
         * @param dormCode 监舍叿
         * @return 匹配信息
         * @throws Exception 匹配失败异常
         */
        public FingerprintMatcher priosnerMatch(byte[] raw, String dormCode) throws Exception {
          synchronized (this) { // 同步调用
            jniPrisonerService.getInstance()
                .loadDb(fingerprintConfig.getDbPath() + FingerprintConfiguration.PRISONER_DB_FILE_NAME);
            try {
              FingerprintMatcher matcher = jniPrisonerService.getInstance().match(raw,
                  fingerprintConfig.getThreshold(), dormCode + PRISONER_USER_TYPE);
              raw = null;
              return matcher;

            } catch (Exception ex) {
              // ex.printStackTrace();
              throw new Exception("在押人员指纹特征比对失败");  // 发出错误
            } finally {
              jniPrisonerService.getInstance().closeDb(); // 强行关闭数据库
            }
          }
        }
——————
* 异步处理
        public FingerprintMatcher match(byte[] fingerprint, float threshold, String userInfo)
            throws Exception { // throws　异常类
          synchronized (this) { // 同步处理
            if (null == fingerprint) { 
              throw new Exception("指纹特征比对失败，raw数据为空"); //　未传入　发出异常
            }
            byte[] raw = null;
            if (RAW_SIZE != fingerprint.length) {
              raw = convertBmp2Raw(fingerprint);
            } else {
              raw = fingerprint;
            }
            byte[] id = new byte[21];
            byte[] code = new byte[1];
            FloatByReference simliarity = new FloatByReference();
            float testThreshold = 0.1f;
            int result = capUtil.FP_Match(raw, testThreshold, id, code, simliarity);　// 比对
            if (result != 0) {
              throw new Exception("testThreshold = 0.1f 指纹特征比对失败:" + result);   // 发出异常
            }

            FingerprintMatcher matcher =
                new FingerprintMatcher(new String(id), code[0], simliarity.getValue()); // 获得匹配结果
            logger.info("指纹匹配结果:" + matcher.toString()); 

            if (threshold > matcher.getSimliarity()) {
              throw new Exception(
                  "testThreshold = " + threshold + "f 指纹特征比对失败:" + matcher.getSimliarity());
            }
            id = null;
            code = null;
            raw = null;
            fingerprint = null; // 全部置空
            return matcher;
          }
        }
—————————————————————————————————————————————————————————————————————————————————————————————————
* 返回错误
————————————
		@RequestMapping(value = "/saveDelay")
		@ResponseBody
		public List<ErrorMessage> saveDelay(@Valid Delay delay, BindingResult result) {
		List<ErrorMessage> error = checkDelay(getErrorMessage(result), delay);
		// 根据传入的result_新建一个错误List
		if (error.size() > 0) {
		  return error;
		}
		windowBusinessService.saveDelay(delay); // 存储
		return null;
		}
————————————
		List<ErrorMessage> message = new ArrayList<ErrorMessage>();

		if (StringUtils.isNoneBlank(breakRule.getNumber())
				&& breakRuleService.getDetailBehaviourList(breakRule).size() == 0) {
			// 关键语句
			addError(message, "receiveDate", "选择的人员类别和具体行为对应的考核类别不一致！");
		}

		public List<?> 
			// 关键语句
————————————————————————————————————————————————————————————————————————————————————————————————
* 返回页面
		@RequestMapping("/page/main")
		@AccessRequired(value = true, type = AccessTypeEnum.pageAccess)
			// 请求的页面，返回的页面
		public String main(Model model, String prisonId, String dormArea, String equipmentType, String ip,
		  Pageable pageable) {

		Page<EquipmentManage> equipments =
		    equipmentManageService.search(prisonId, dormArea, equipmentType, ip, pageable);
			model.addAttribute("equipmentTypeEnum", EquipmentTypeEnums.values()); // 给模型添加键值对
			model.addAttribute("equipments", equipments.toPageInfo()); // 在进入这个页面的时候就会直接传递这个模型
		return "admin/equipment_config";	// 返回去以后前端是如何处理的
			// 转入页面
		}
————————————————————————————————————————————————————————————————————————————————————————————————
* 返回数据
		@RequestMapping("/findPhotoById")
		@ResponseBody
		public List<byte[]> findPhotoById(@RequestParam(required = true) String id) {
			return articleRecordService.findPhotoById(id);
		}
	* 当返回数据是二进制内容，保存的是图片或者其他内容的时候
		  @Select("select photo from t_commodity where com_code = #{code}")
		  @Result(typeHandler = BlobTypeHandler.class)
			// 关键代码 , 进行编码 BlobTypeHandler 
		  public String	findGoodPhotoByCode(@Param("code") String code);
		  	// 返回值 是二进制编码的情况下  String 是base64 编码过的文件
		  	// column = "PHOTO", property = "pohot" 必须在返回体里面有对应的映射关系
		  	// private byte[] photo
	* 修改以后，改为
		* provider 返回 Goods 
			* 在映射过程中，@Result 返回的内容对应到 Goods 类里面去，余下的为空 
		* service 返回 byte[]  返回的是byte[], 在获取到Goods 以后只取出里面的pohto
		* cotroller	返回 byte[]		

* 取子字符串判断操作
		int prisonType = Integer.parseInt(prisonId.substring(7, 8));
			// 关键语句
		switch (prisonType) {
		  case Prison.PRISONTYPE_PRISON:
		    return cashRepository.findPrisonCash(prisonId, startTime, endTime, number, pageable);
		  case Prison.PRISONTYPE_DETENTION:
		    return cashRepository.findDetentionCash(prisonId, startTime, endTime, number, pageable);
		  case Prison.PRISONTYPE_EDUCATION:
		    return cashRepository.findEducationCash(prisonId, startTime, endTime, number, pageable);
		  default:
		    throw new IllegalArgumentException("prisonId type error");
		}
————————————
* 添加巡视点业务逻辑
		public String startTour(@NotBlank(message = "prisonId is null") String prisonId,
			    @NotBlank(message = "policeId is null") String policeId, int shouldSignNumber,
		    String tourRouteId) {
		    String tourRecordId = insertTourRecord(prisonId, policeId, shouldSignNumber, tourRouteId);
		    	// 插入巡视点，返回巡视点id 字符串
		    insertShouldSignNumberSwipe(prisonId, tourRecordId, tourRouteId, policeId);
		    	// 添加需要巡视的巡视点
		    return tourRecordId;
		}

		private String insertTourRecord(String prisonId, String policeId, int shouldSignNumber,
				String tourRouteId) {
			String id = new ObjectId().toHexString();
				// 关键语句		获得 id
			TourRecord tourRecord = new TourRecord();
				// 新建记录点
			tourRecord.setCreateTime(new Date());
			tourRecord.setTourStartTime(new Date());
			return id;
		}
————————————

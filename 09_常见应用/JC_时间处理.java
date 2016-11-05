
* 基本方法
* 时间处理
    * 传入 String 
    * 转化为 Date类型  
        Date startTime = (Date) map.get("startTime");
    * 使用工具类处理格式化日期，进行比较
        WHERE("mxb.RQ BETWEEN '" + DateFormatUtils.format(startTime, "yyyyMMdd") + "' and '"
            + DateFormatUtils.format(endTime, "yyyyMMdd") + "'");
        "mxb.rq between 'yyyyMMdd' and 'yyyyMMdd'"
    * 但是也可以直接比较
        WHERE("c.csrq >= '" + prisonerQo.getOutStartDate() + "'"); // 字符串直接比较
* 获取当前时间
        Date startTime = new Calendar().getTime();
        Date startTime = DateUtils.getMondayOfWeek();
        Date endTime = DateUtils.getSundayOfWeek();
* 时间加减
        Calendar rightNow = Calendar.getInstance();
        Date startTime = rightNow.getTime();
        rightNow.add(Calendar.DATE, 1);
        Date endTime = rightNow.getTime();
————————————————————————————————————————————————————————————————————————————————————————————
————————————————————————————————————————————————————————————————————————————————————————————
* 时间处理
* 传入时间
    * String 转 Date
           SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
          String findDateStr = (String) map.get("FXSJ");
          String recordDateStr = (String) map.get("JLSJ");
          if (StringUtils.isNotEmpty(findDateStr)) {
            try {
              Date findDate = dateFormat.parse(findDateStr);
              breakRule.setFindDate(findDate);
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
          if (StringUtils.isNotEmpty(recordDateStr)) {
            try {
              Date recordDate = dateFormat.parse(recordDateStr);
              breakRule.setRecordDate(recordDate);
            } catch (Exception e) {
              e.printStackTrace();
            }
          }

    * 时间格式化
        * 格式化时间
            2010-05-05 10:20:20
        * 工具类 
            2010-12-20%2012:23:21
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") 
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.TIME_SHOW, Locale.CHINESE);
            Date endTime = simpleDateFormat.parse("20160101010101");
            // 将字符串转化为 Data
        * 直接比较Date类型
                if (startTime != null) {
                    WHERE("CREATE_TIME>= to_date('" + simpleDateFormat.format(startTime) + "','"
                        + Constants.TIME_ORACLE + "')"); }
                // 传入date 类型的startTime 转化格式，然后和数据库的时间进行比较
        * 对传入的Date 类型转化为 String 去和数据库进行比较
            DailyShiftDynaSqlProvider
                WHERE("(DJSJ='" + DateFormatUtils.format(date, "yyyyMMdd") + "' or DJSJ is null)");
                String temp = DateFormatUtils.format(startTime, "yyyyMMdd");
                String startTimeStr = DateFormatUtils.format(startTime, " yyyMMddHHmmss");
                // 使用的格式化参数。学习。有哪些。
                // 比对 Constants.TIME_ORACLE
        * 查询url
            http://localhost:8080/jc/prisoner/search.json?startTime=2010-11-11%2010:23:21
    * 查询时间统一用 
        startTime
        endTime
    * 例子
        endTime != null
        ChatRecordController.search
        java.util.Date
* 输出时间
    * @Result(column = "YYSJ", property = "date", typeHandler = DateTimeTypeHandler.class),
        * typeHandler = DateTimeTypeHandler.class // 处理到秒
        * typeHandler = DateTypeHandler.class // 处理到天
        * typeHandler = DateYearMonthTypeHandler.class // 处理到月
        * import com.newings.mybatis.DateTimeTypeHandler;
    * 添加这个属性以后，会自动将返回的字符串转化为Date类型

*日期比较 
      if (focusedPrisoner.getApplyDate() // String 类型
        .compareTo(systemParamService.getSystemDate() + "999999") > 0) { // 拿到的也是String类型
      addError(message, "applyDate", "申请日期不能大于现在！");
      return message;
    }
————————————————————————————————————————————————————————————————————————————————————————————
————————————————————————————————————————————————————————————————————————————————————————————
* 时间处理
    * 传入两个时间
        if (startTime != null && endTime != null) {
          String startTimeStr = DateFormatUtils.format(startTime, "yyyyMMddHHmmss");
          String endTimeStr = DateFormatUtils.format(endTime, "yyyyMMddHHmmss");
        WHERE("((ls.hjkssj between " + startTimeStr + " and " + endTimeStr + " ) " + " or "
        + "(ls.hjjssj Between " + startTimeStr + " and " + endTimeStr + " ))");
          
        }
    * 只传入开始时间
        if (startTime != null && endTime == null) {
          String startTimeStr = DateFormatUtils.format(startTime, "yyyyMMddHHmmss");
          WHERE("((ls.hjkssj >= " + startTimeStr +  ") or (ls.hjjssj >= " + startTimeStr + "))");
        }
    * 只传入结束时间
        if (startTime == null && endTime != null) {
          String endTimeStr = DateFormatUtils.format(endTime, "yyyyMMddHHmmss");
          WHERE("((ls.hjkssj <= " + endTimeStr +  ") or (ls.hjjssj <= " + endTimeStr + "))");
        }
* 处理返回值
    * 返回的实体类里面
          // 担保人与在押人员关系
          private String securityRelation;
          private String securityRelationValue;
    * get和set方法
          public String getSecurityRelation() {
            return securityRelation;
          }
          public void setSecurityRelation(String securityRelation) {
            this.securityRelation = securityRelation;
            setSecurityRelationValue(DictUtils.getDictValue("家庭关系", securityRelation));
          }
          public String getSecurityRelationValue() {
            return securityRelationValue;
          }
          public void setSecurityRelationValue(String securityRelationValue) {
            this.securityRelationValue = securityRelationValue;
          }
* 调用的字典工具里面存储的内容
          /**
           * 字典信息取得.
           * 
           * @param dictName 字典名
           * @param code 代码号
           * @return 代号值
           */
          public static String getDictValue(String dictName, String code) {
            if (dictCache == null) {
              dictCache = initDictCache();
            }
            if (StringUtils.isBlank(code)) {
              return "";
            }
            if (!code.contains(",")) {
              ValueWrapper valueWrapper = dictCache.get(dictName + SEPARATE + code);
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

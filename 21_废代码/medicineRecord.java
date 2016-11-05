* 对中间用到的名称
    * 简练明了的名字。

* 不要去写一些自己看起来很不舒服的代码 
    * 中间代码过长的 if else 
        if else 可以当作判断使用。
    * 复杂的，非业务逻辑的代码。
        可以提出去作为一个方法来调用
        * 比如异常判断
              private void checkPeriods(String[] periods) {
                // 异常判断
                if (periods == null || periods.length == 0) {
                  throw new IllegalArgumentException("非法时间段参数！");
                }
                for (String period : periods) {
                  if (!StringUtils.equals(period, PERIOD_MORNING) && !StringUtils.equals(period, PERIOD_NOON)
                      && !StringUtils.equals(period, PERIOD_AFTERNOOT)) {
                    throw new IllegalArgumentException("非法时间段参数！");
                  }
                }
              }
    * 多次调用的同一段代码，也提出去作为一个方法来调用
        *   private void doInsert(MedicineRecord medicineRecord, Date dispensationDate, String period) {
            MedicineRecord saveMedicineRecord = new MedicineRecord();
            BeanUtils.copyProperties(medicineRecord, saveMedicineRecord);
            saveMedicineRecord.setId(new ObjectId().toHexString());
            saveMedicineRecord.setDispensationDate(new Date());
            saveMedicineRecord.setPeriod(period); // 设置时间段
            saveMedicineRecord.setDispensationDate(dispensationDate); // 设置发药日期
            medicineRecordRepository.insert(saveMedicineRecord); }
    * 业务处理 
        * 不要去写一些重复的判断代码 来处理业务。 
            * 尤其是 在 for 循环里面 添加  if( XXX == n) 这种循环条件。
            *                             

/**
   * 保存用药记录.
   * 
   * @param medicineRecord 用药记录
   */
  public void save(MedicineRecord medicineRecord) throws Exception {
    // 如果没有传入id 则需要拆分 执行插入
    if (StringUtils.isBlank(medicineRecord.getId())) {
      insert(medicineRecord);  // 代码如果过长，抽出去一个方法
    } else {
      medicineRecordRepository.update(medicineRecord);
    }
  }

  private void insert(MedicineRecord medicineRecord) {
    // 获取发药时间 就是当前时间
    Calendar calendar = Calendar.getInstance();
    Date dispensationDate = calendar.getTime();
    calendar.setTime(dispensationDate);

    // 将传入的字符串切分
    String[] periods = medicineRecord.getPeriod().split(",");
    checkPeriods(periods); // 将判断代码抽出去

    // 获取发药天数
    int takeMedicineDays = medicineRecord.getTakeMedicineDays();

    dispensationDate = calendar.getTime();
    int hour = calendar.get(Calendar.HOUR_OF_DAY); // 获取当前小时 int 类型

    // 从发药天的第二天开始循环
    for (int i = 0; i < takeMedicineDays; i++) {  // 尽量不要再 for 循环里面去写 int 不是从 0 开始的这种代码
      for (int j = 0; j < periods.length; j++) {
        if (i == 0) {
          switch (periods[j]) {
            case "上午": // 如果是 "上午" 执行下面的代码
              if (hour > 9) {
                continue; 
              }
              // 不再去抽 字符串。 直接进行插入
              // 使用好 if else 
              doInsert(medicineRecord, dispensationDate, periods[j]);
              break;
            case "中午": // 由上往下  判断完 "上午" ，判断 "中午"
              if (hour > 13) {
                continue;
              }
              doInsert(medicineRecord, dispensationDate, periods[j]);
              break;
            case "下午": // 执行到这里就一定是 "下午"
              doInsert(medicineRecord, dispensationDate, periods[j]);
              break;
            default:
              break;
          }
        } else {
          doInsert(medicineRecord, dispensationDate, periods[j]);
        }
      }
      calendar.add(Calendar.DATE, 1); // 业务逻辑，可以放在第二层循环里面的放在这里。 尽量不要去写 if 这种
                                        // 很难看的判断
      dispensationDate = calendar.getTime();
    }
  }

  private void doInsert(MedicineRecord medicineRecord, Date dispensationDate, String period) {
    MedicineRecord saveMedicineRecord = new MedicineRecord();
    BeanUtils.copyProperties(medicineRecord, saveMedicineRecord);
    saveMedicineRecord.setId(new ObjectId().toHexString());
    saveMedicineRecord.setDispensationDate(new Date());
    saveMedicineRecord.setPeriod(period); // 设置时间段
    saveMedicineRecord.setDispensationDate(dispensationDate); // 设置发药日期
    medicineRecordRepository.insert(saveMedicineRecord);
  }

  private void checkPeriods(String[] periods) { // 抽出来的方法
    // 异常判断
    if (periods == null || periods.length == 0) {
      throw new IllegalArgumentException("非法时间段参数！"); // 抛出异常
    }
    for (String period : periods) {
      if (!StringUtils.equals(period, PERIOD_MORNING) && !StringUtils.equals(period, PERIOD_NOON)
          && !StringUtils.equals(period, PERIOD_AFTERNOOT)) {
        throw new IllegalArgumentException("非法时间段参数！"); // 抛出异常
      }
    }
  }
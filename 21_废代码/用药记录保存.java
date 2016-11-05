   /**
   * 保存用药记录.
   * 
   * @param medicineRecord 用药记录
   */
  public void save(MedicineRecord medicineRecord) throws Exception {

    if (StringUtils.isBlank(medicineRecord.getId())) {

      Date dispensationDate = medicineRecord.getDispensationDate();
      int takeMedicineDays = medicineRecord.getTakeMedicineDays();

      Calendar calendar = new GregorianCalendar(); 
      calendar.setTime(dispensationDate);

      String[] periods = medicineRecord.getPeriod().split(","); // 将传入的字符串切分

      // 在判断的时候使用工具类  使用 String的 equals 方法的话，会报错。
      for (String period : periods) {
        if (!StringUtils.equals(period, PERIOD_MORNING) && !StringUtils.equals(period, PERIOD_NOON)
            && !StringUtils.equals(period, PERIOD_AFTERNOOT)) {
          throw new IllegalArgumentException("非法时间段参数！");  // 抛出异常
        }
      }

      for (int i = 0; i < takeMedicineDays; i++) {
        for (int j = 0; j < periods.length; j++) {

          // JAVA 里面直接指过去的话 不能赋值。 知识把它的内存地址指过去而已。
          // 类似于潜复制。  而我们需要的是深复制。
          MedicineRecord saveMedicineRecord = new MedicineRecord();  // 新建
          // 使用工具类复制
          BeanUtils.copyProperties(medicineRecord, saveMedicineRecord);
          // 添加id
          saveMedicineRecord.setId(new ObjectId().toHexString());
          saveMedicineRecord.setDispensationDate(new Date());

          saveMedicineRecord.setPeriod(periods[j]); // 设置时间段
          saveMedicineRecord.setDispensationDate(dispensationDate); // 设置发药日期
          medicineRecordRepository.insert(saveMedicineRecord);

          // 跳出第一层循环的时候 发药时间 加一天
          // 这个应该有一个比较好的写法。  想一想
          if (periods.length == (j + 1)) {
            calendar.add(Calendar.DATE, 1);
            dispensationDate = calendar.getTime();
          }
        }
      }
    } else {
      medicineRecordRepository.update(medicineRecord);
    }
  }
}
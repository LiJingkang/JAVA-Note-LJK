实现获取当天，当周，当月，当季，当年的时间段

    由于项目需求，现在要做一个排行榜，需要按照时间（日，周，月，季，年）来从sql中查询数据。

 1、首先要做的就是怎样判断当前的时间段 
     查询资料知：
             项目中关于时间的使用：

1、获取时间  Date date=new Date(); date.getDate();    // System.currentTimeMills();  都是返回long型的数据，

2、以某种格式输出   
    （1）SimpleDateFormat
        sdf=new SimpleDateFormat("yyyy--MM--dd HH:mm:ss " );
        sdf.format(date)将date型转换成 String

    （2）将String转换成Date型
        sdf.parse(str); 将String类型解析成Date类型 

3、对时间进行处理

Calendar 类 可以获取年月日

参看
http://www.apihome.cn/api/java/Calendar.html Calendar的使用文档


    /** 
     * 获取当前日期与周一相差的天数 
     * @return 
     */  
    public static int getMondayPlus(){  
        Calendar day=Calendar.getInstance();  
        int dayOfWeek=day.get(Calendar.DAY_OF_WEEK);  
        if(dayOfWeek==1){ //一周中第一天（周日）  
            return -6;  
        }else{  
            return 2-dayOfWeek;  
        }  
    }  
    /** 
     * 获得当天的起始时间 
     * @return 
     */  
    public static Calendar getStartDate(Calendar today){  
        today.set(Calendar.HOUR_OF_DAY,0);  
        today.set(Calendar.MINUTE, 0);    
        today.set(Calendar.SECOND, 0);    
        today.set(Calendar.MILLISECOND, 0);    
        return today;  
    }  
    /** 
     * 获取当天截止时间 
     * @return 
     */  
    public static Calendar getEndDate(Calendar endToday){  
        endToday.set(Calendar.HOUR_OF_DAY, 23);    
        endToday.set(Calendar.MINUTE, 59);    
        endToday.set(Calendar.SECOND, 59);    
        endToday.set(Calendar.MILLISECOND, 59);    
        return endToday;  
    }  
    /** 
     * 获得当月起始时间 
     * @return 
     */  
    public static Calendar getStartMounth(Calendar today){  
        Calendar calendar = getStartDate(today);     
           calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));   
            return calendar;  
    }  
    /** 
     * 获得当月结束时间 
     * @return 
     */  
    public static Calendar getEndMounth(Calendar endToday){  
        Calendar endMounth=getEndDate(endToday);  
        endMounth.set(Calendar.DAY_OF_MONTH, endMounth.getActualMaximum(endMounth.DAY_OF_MONTH));  
        return endMounth;  
    }  
    /** 
     * 获取当前季度 起始时间 
     * @return 
     */  
    public static Calendar getStartQuarter(Calendar today){  
            int currentMonth = today.get(Calendar.MONTH) + 1;   
         try {   
                if (currentMonth >= 1 && currentMonth <= 3)   
                    today.set(Calendar.MONTH, 0);   
                else if (currentMonth >= 4 && currentMonth <= 6)   
                    today.set(Calendar.MONTH, 3);   
                else if (currentMonth >= 7 && currentMonth <= 9)   
                    today.set(Calendar.MONTH, 4);   
                else if (currentMonth >= 10 && currentMonth <= 12)   
                    today.set(Calendar.MONTH, 9);   
            } catch (Exception e) {   
                e.printStackTrace();   
            }   
        return today;  
    }  
    /** 
     * 获取当季的结束时间 
     */  
    public static Calendar getEndQuarter(Calendar today){  
            int currentMonth = today.get(Calendar.MONTH) + 1;   
            try {   
                if (currentMonth >= 1 && currentMonth <= 3) {   
                    today.set(Calendar.MONTH, 2);   
                    today.set(Calendar.DATE, 31);   
                } else if (currentMonth >= 4 && currentMonth <= 6) {   
                    today.set(Calendar.MONTH, 5);   
                    today.set(Calendar.DATE, 30);   
                } else if (currentMonth >= 7 && currentMonth <= 9) {   
                    today.set(Calendar.MONTH,8);   
                    today.set(Calendar.DATE, 30);   
                } else if (currentMonth >= 10 && currentMonth <= 12) {   
                    today.set(Calendar.MONTH, 11);   
                    today.set(Calendar.DATE, 31);   
                }   
            } catch (Exception e) {   
                e.printStackTrace();   
            }   
            return today;   
    }  
    /** 
     * 获取当年起始时间 
     */  
    public static Calendar getStartYear(Calendar today){  
           try {   
            today.set(Calendar.MONTH, 0);   
            today.set(Calendar.DAY_OF_MONTH, today.getActualMinimum(Calendar.DAY_OF_MONTH));   
           } catch (Exception e) {   
               e.printStackTrace();   
           }   
           return today;   
    }  
    /** 
     * 获取当年结束时间 
     */  
    public static Calendar getEndYear(Calendar today){  
         try {   
                today.set(Calendar.MONTH, 11);   
                today.set(Calendar.DAY_OF_MONTH, today.getMaximum(Calendar.DAY_OF_MONTH));   
          } catch (Exception e) {   
                e.printStackTrace();   
          }   
          return today;   
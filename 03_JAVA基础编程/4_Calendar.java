/**
     * 计算指定日期为当年第几周
     * @param year      指定的年份
     * @param month     指定的月份
     * @param day       指定的日
     * @return          指定日期为当年的第几周
     */
    public static int caculateWeekOfYear(int year,int month,int day){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month - 1);
        c.set(Calendar.DATE, day);
        return c.get(Calendar.WEEK_OF_YEAR);
    }
    
    /**
     * 获取本月第一天是星期几
     * @return
     */
    public static int getWeekOfFirstDay(Calendar c){
        Calendar calendar = c;
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }
    
    /**
     * 获取本月1号是该年的第几周
     * @return
     */
    public static int getMonthStartWeek(Calendar c){
        Calendar calendar = c;
        calendar.set(Calendar.DATE, 1);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }
    
    /**
     * 获取当天是该年的第几周
     */
    public static int getCurrentWeekOfYear(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }
    
    /**
     * 获取上月的总天数
     * @return
     */
    public static int getLastMonthDays(Calendar c){
        Calendar calendar = c;
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
    
    /**
     * 获取指定月份的总天数
     * @return
     */
    public static int getCurrentMonthDays(int month){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, month);
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
    
    /**
     * 获取指定年份有多少周
     * @param year
     * @return
     */
    public static int getTotalWeekOfYear(int year){
        Calendar c = Calendar.getInstance();
        return c.getActualMaximum(Calendar.WEEK_OF_YEAR);
    }
    
    /**
     * 判断指定月份是否是当前月
     * @param month
     * @return
     */
    public static boolean isCurrentMonth(int month){
        Calendar c = Calendar.getInstance();
        return (c.get(Calendar.MONTH) == month)?true:false;
    }
    
    /**
     * 计算指定的月份共有多少天
     */
    public static int getTotalDaysOfMonth(int year, int month){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DATE, 1);
        /**
         * 计算这个月有多少天
         */
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
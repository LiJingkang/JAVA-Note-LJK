* Date 转字符串
	  DateFormatUtils.format(comeDateStartTime, "yyyyMMdd")
	  String timeStampStr = DateFormatUtils.format(talks.get(0).getTimeStamp(), "yyyyMMddHHmmss");
	  
* 获取当前时间
      Calendar calendar = new GregorianCalendar(); 
      Date dispensationDate = calendar.getTime(); // 不放入时间就是获取的当前时间
      calendar.setTime(dispensationDate); // 将时间放入以后 再获取就是 放入的时间

      String defaultTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

* Calendar 相关处理
      int hour = calendar.get(Calendar.HOUR_OF_DAY); // 获取当前小时 int 类型

    * 例子
		Calendar calendar = Calendar.getInstance() // 初始化
		calendar.set(Calendar.HOUR_OF_DAY, getStartHour()); // 设置 当天开始的小时
		calendar.set(Calendar.MINUTE, getStartMimute()); // 设置 当天开始的分钟	
		calendar.set(Calendar.SECOND, getStartSecond()); // 设置 当天开始的秒
		Date startTime = calendar.getTime(); // 得到当天开始的时间

		calendar.set(Calendar.HOUR_OF_DAY, getEndHour());
		calendar.set(Calendar.MINUTE, getEndMimute());
		calendar.set(Calendar.SECOND, getEndSecond());
		Date endTime = calendar.getTime(); // 得到当天结束的时间
		// 使用这两个时间来判断一天内要判断的业务。
	
	* 例子
		Calendar calendar = Calendar.getInstance(); // 初始化
		calendar.add(Calendar.DAY_OF_WEEK, -1); // 天 
		calendar.set(Calendar.HOUR_OF_DAY, 0); // 小时
		calendar.set(Calendar.MINUTE, 0); // 分
		calendar.set(Calendar.SECOND, 0); // 秒
		Date startTime = calendar.getTime();

		calendar.set(Calendar.HOUR_OF_DAY, 23); // 小时
		calendar.set(Calendar.MINUTE, 59); // 分钟
		calendar.set(Calendar.SECOND, 59); // 秒
		Date endTime = calendar.getTime();
	* 		
——————————————————————————————————————————————————————————————————————————————————————————
* Java 日期时间
	* Date()
* Date 方法
	boolean after(Date date)
		若当调用此方法的Date对象在指定日期之后返回true,否则返回false。
	boolean before(Date date)
		若当调用此方法的Date对象在指定日期之前返回true,否则返回false。
	Object clone( )
		返回此对象的副本。
	int compareTo(Date date)
		比较当调用此方法的Date对象和指定日期。两者相等时候返回0。调用对象在指定日期之前则返回负数。调用对象在指定日期之后则返回正数。
	int compareTo(Object obj)
		若obj是Date类型则操作等同于compareTo(Date) 。否则它抛出ClassCastException。
	boolean equals(Object date)
		当调用此方法的Date对象和指定日期相等时候返回true,否则返回false。
	long getTime( )
		返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。
	int hashCode( )
		返回此对象的哈希码值。
	void setTime(long time)
		用自1970年1月1日00:00:00 GMT以后time毫秒数设置时间和日期。
	String toString( )
		转换Date对象为String表示形式，并返回该字符串。
* 获取当前日期时间
	Calendar calendar = Calendar.getInstance();
	Date nowDate = calendar.getTime(); // 不给 clendar 放入时间，那么得到的就是当前时间
	Date date = new Date(); // 好像也可以得到当前时间


	public class DateDemo {
	   public static void main(String args[]) {
	       // 初始化 Date 对象
	       Date date = new Date();
	       // 使用 toString() 函数显示日期时间
	       System.out.println(date.toString()); }}
* 日期比较
    * 使用getTime( ) 
		方法获取两个日期（自1970年1月1日经历的毫秒数值），然后比较这两个值。
    * 使用方法before()，after()和equals()。
		例如，一个月的12号比18号早，则new Date(99, 2, 12).before(new Date (99, 2, 18))返回true。
    * 使用compareTo()方法，
		它是由Comparable接口定义的，Date类实现了这个接口。
* 使用SimpleDateFormat格式化日期
	* SimpleDateFormat允许你选择任何用户自定义日期时间格式来运行。
	* 例子
		public class DateDemo {
		   public static void main(String args[]) {
		      Date dNow = new Date( );
		      SimpleDateFormat ft = 
		      new SimpleDateFormat ("E yyyy.MM.dd 'at' hh:mm:ss a zzz");

		      System.out.println("Current Date: " + ft.format(dNow)); }}
* 简单的DateFormat格式化编码

* 使用printf格式化日期
     // 使用toString()显示日期和时间
     String str = String.format("Current Date/Time : %tc", date );
* 解析字符串为时间

* 时间加一天
		import java.util.Date ;

		date = new date();//取时间 
		Calendar calendar = new GregorianCalendar(); 
		calendar.setTime(date); 
		calendar.add(Calendar.DATE,1);  // 把日期往后增加一天.整数往后推,负数往前移动 
									// 静态变量 直接使用 变量名调用， 不需要使用 实例化的 方法名来调用
		date=calendar.getTime();   // 这个时间就是日期往后推一天的结果 
——————————————————————————————————————————————————————————————————————————————————————————
* Java 休眠(sleep)
* 如果 获取时间戳为空 则返回当前时间
		static String getLastSynchronizeTime(String path) {
	    String lastSynchronizeTime = null;
	    String defaultTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
	    try {
	      BufferedReader reader = new BufferedReader(new FileReader(getFile(path)));
	      lastSynchronizeTime = reader.readLine();
	      reader.close();
	      if (StringUtils.isBlank(lastSynchronizeTime)) {
	        updateLastSynchronizeTime(path, defaultTime);
	        return defaultTime;
	      } else {
	        return lastSynchronizeTime;
	      }
	    } catch (FileNotFoundException ex1) {
	      ex1.printStackTrace();
	    } catch (IOException ex2) {
	      ex2.printStackTrace();
	    }

	    return defaultTime;
	  }
——————————————————————————————————————————————————————————————————————————————————————————
* 时间比较
	/** 获取两个时间的时间查 如1天2小时30分钟 */
	public static String getDatePoor(Date endDate, Date nowDate) {
	 
	    long nd = 1000 * 24 * 60 * 60;
	    long nh = 1000 * 60 * 60;
	    long nm = 1000 * 60;
	    // long ns = 1000;
	    // 获得两个时间的毫秒时间差异
	    long diff = endDate.getTime() - nowDate.getTime();
	    // 计算差多少天
	    long day = diff / nd;
	    // 计算差多少小时
	    long hour = diff % nd / nh;
	    // 计算差多少分钟
	    long min = diff % nd % nh / nm;
	    // 计算差多少秒//输出结果
	    // long sec = diff % nd % nh % nm / ns;
	    return day + "天" + hour + "小时" + min + "分钟";

	    // 如果要直接获取分钟差，可以 long min = diff / nm
	}
* 相差多少分钟
	public static void main(String[] args){
		Calendar dateOne=Calendar.getInstance(),dateTwo=Calendar.getInstance();
		dateOne.setTime(new Date());	//设置为当前系统时间 
		dateTwo.set(2015,0,25);			//设置为2015年1月15日
		long timeOne=dateOne.getTimeInMillis();
		long timeTwo=dateTwo.getTimeInMillis();
		long minute=(timeOne-timeTwo)/(1000*60);//转化minute
		System.out.println("相隔"+minute+"分钟");
		}	
	

* Java 异常处理


* 层次结构

			Throwable

	Error 					Exception

					IOException		RuntimeException // 这两个是 异常类的主要子类

** Exception类的层次
		Exception类 是 Throwable类 的子类
		Throwable 还有一个子类 Error 。
		Error 用来指示运行时环境发生的错误。
	异常类有两个主要的子类：
		IOException类  
		RuntimeException类

** Java 内置异常类
	ArithmeticException 	
		当出现异常的运算条件时，抛出此异常。例如，一个整数"除以零"时，抛出此类的一个实例。
	ArrayIndexOutOfBoundsException 	
		用非法索引访问数组时抛出的异常。如果索引为负或大于等于数组大小，则该索引为非法索引。
	ArrayStoreException 	
		试图将错误类型的对象存储到一个对象数组时抛出的异常。
	ClassCastException 	
		当试图将对象强制转换为不是实例的子类时，抛出该异常。
	IllegalArgumentException 	
		抛出的异常表明向方法传递了一个不合法或不正确的参数。"异常处理"
	IllegalMonitorStateException 	
		抛出的异常表明某一线程已经试图等待对象的监视器，或者试图通知其他正在等待对象的监视器而本身没有指定监视器的线程。
	IllegalStateException 	
		在非法或不适当的时间调用方法时产生的信号。换句话说，即 Java 环境或 Java 应用程序没有处于请求操作所要求的适当状态下。
	IllegalThreadStateException 	
		线程没有处于请求操作所要求的适当状态时抛出的异常。
	IndexOutOfBoundsException 	
		指示某排序索引（例如对数组、字符串或向量的排序）超出范围时抛出。
	NegativeArraySizeException 	
		如果应用程序试图创建大小为负的数组，则抛出该异常。
	NullPointerException 	
		当应用程序试图在需要对象的地方使用 null 时，抛出该异常
	NumberFormatException 	
		当应用程序试图将字符串转换成一种数值类型，但该字符串不能转换为适当格式时，抛出该异常。
	SecurityException 	
		由安全管理器抛出的异常，指示存在安全侵犯。
	StringIndexOutOfBoundsException 	
		此异常由 String 方法抛出，指示索引或者为负，或者超出字符串的大小。
	UnsupportedOperationException 	
		当不支持请求的操作时，抛出该异常。
	ClassNotFoundException 	
		应用程序试图加载类时，找不到相应的类，抛出该异常。
	CloneNotSupportedException 	
		当调用 Object 类中的 clone 方法克隆对象，但该对象的类无法实现 Cloneable 接口时，抛出该异常。
	IllegalAccessException 	
		拒绝访问一个类的时候，抛出该异常。
	InstantiationException 	
		当试图使用 Class 类中的 newInstance 方法创建一个类的实例，而指定的类对象因为是一个接口或是一个抽象类而无法实例化时，抛出该异常。
	InterruptedException 	
		一个线程被另一个线程中断，抛出该异常。
	NoSuchFieldException 	
		请求的变量不存在
	NoSuchMethodException 	
		请求的方法不存在

** 异常方法 
	下面的列表是Throwable 类的主要方法:
		public String getMessage()
			返回关于发生的异常的详细信息。这个消息在Throwable 类的构造函数中初始化了。
		public Throwable getCause()
			返回一个Throwable 对象代表异常原因。
		public String toString()
			使用getMessage()的结果返回类的串级名字。
		public void printStackTrace()
			打印toString()结果和栈层次到System.err，即错误输出流。
		public StackTraceElement [] getStackTrace()
			返回一个包含堆栈层次的数组。下标为0的元素代表栈顶，最后一个元素代表方法调用堆栈的栈底。
		public Throwable fillInStackTrace()
			用当前的调用栈层次填充Throwable 对象栈层次，添加到栈层次任何先前信息中。
** 捕获异常
	try
	{ // 程序代码
	} catch(ExceptionName e1)
	{ //Catch 块 // 捕获
	}
** 多重捕获块
** throws/throw关键字：
		// 如果一个方法没有捕获一个检查性异常，那么该方法必须使用throws 关键字来声明。throws关键字放在方法签名的尾部。  
		public class className
		{ public void deposit(double amount) throws RemoteException	
												// throws 一个Exception 或者继承自 Exception的类
		   { // Method implementation
		      throw new RemoteException();  // throw 抛出一个异常 或者抛出一个 自定义异常
		   } //Remainder of class definition
		}

		public class className
		{ public void withdraw(double amount) throws RemoteException, InsufficientFundsException
		   {  // Method implementation 
		   } //Remainder of class definition
		}
** finally关键字 
		在finally代码块中，可以运行清理类型等收尾善后性质的语句。
		finally代码块出现在catch代码块最后
** 声明自定义异常
	    所有异常都必须是 Throwable 的子类。
	    如果希望写一个检查性异常类，则需要继承 Exception 类。
	    如果你想写一个运行时异常类，那么需要继承 RuntimeException 类。
	    class MyException extends Exception{ }
    * 实例
	    * 
			public class InsufficientFundsException extends Exception // 继承Exception 
			{ private double amount;
			   public InsufficientFundsException(double amount) // 传入 amount 数量
			   { this.amount = amount; } 
			   public double getAmount()
			   { return amount; } }
		*
			public class CheckingAccount
			{
			   private double balance;
			   private int number;
			   public CheckingAccount(int number)
			   {this.number = number;}
			   public void deposit(double amount) // 存款
			   {balance += amount;}
			   public void withdraw(double amount) throws
			                              InsufficientFundsException 
			                              // 如果一个方法没有捕获一个检查性异常，
			                              // 那么该方法必须使用throws 关键字来声明。
			                              // throws关键字放在方法签名的尾部。  
			   {if(amount <= balance)
			      { balance -= amount;}
			      else { double needs = amount - balance;
			         throw new InsufficientFundsException(needs); // 抛出异常
			     }}
			   public double getBalance()
			   {return balance;}
			   public int getNumber()
			   {return number;}}
		*
			public class BankDemo
			{
			   public static void main(String [] args)
			   { CheckingAccount c = new CheckingAccount(101);
			      System.out.println("Depositing $500...");
			      c.deposit(500.00);
			      try
			      {  System.out.println("\nWithdrawing $100...");
			         c.withdraw(100.00); // 调用withdraw 方法
			         System.out.println("\nWithdrawing $600...");
			         c.withdraw(600.00); // 处理
			      }catch(InsufficientFundsException e) // 捕获到throw出来的异常
			      	// Catch语句包含要捕获异常类型的声明。
			      	// 当保护代码块中发生一个异常时，try后面的catch块就会被检查。
					// 如果发生的异常包含在catch块中，异常会被传递到该catch块，这和传递一个参数到方法是一样。 
			      {	System.out.println("Sorry, but you are short $"
			        e.printStackTrace(); // 调用继承的 Exception 内方法
			      }}}

** 通用异常
    JVM(Java虚拟机)异常：由JVM抛出的异常或错误。
    	例如：NullPointerException类，
    			ArrayIndexOutOfBoundsException类，
    			ClassCastException类。
    程序级异常：由程序或者API程序抛出的异常。
    	例如: IllegalArgumentException类，
    			IllegalStateException类。
——————————————————————————————————————————————————————————————————————————————————————————————————————
* 自定义异常
——————————————
	* 例1
		if (prisonerAccount == null) {
	      throw new UserDoesNotExistException(ExceptionMessageEnum.USER_DOES_NOT_EXIST.getName()
	          + " number = " + number + " cardId = " + cardId);
	      		// UserDoesNotExistTxeption 是继承 RuntimeException 类的 public 方法 传入一个message
	      		// ExceptionMessageEnum 是一个Enum类，Enum类中定义了几个Enum常亮，调用getName 方法，返回了一个定义好的字符串
	      		// 再添加几个传入的数据
	      		// 给异常类返回一个emessage 字符串。 抛出异常
	    }
    * 例2
	    if (prisonerAccount == null || prisonerAccount.getBalance() == null
	        || prisonerAccount.getBalance() <= 0) {
	      throw new InsufficientFundsException(ExceptionMessageEnum.INSUFFICIENT_FUNDS.getName());
	    }
	    // 自定义异常
	    public class InsufficientFundsException extends RuntimeException {
			  private static final long serialVersionUID = -5218852211299117913L;
			  public InsufficientFundsException(String message) {
				    super(message);
			  }}
	* 例3
		// 方法要继承 throws Exception 才可以
		  for (String period : periods) {
	        if (!StringUtils.equals(period, PERIOD_MORNING) && !StringUtils.equals(period, PERIOD_NOON)
	            && !StringUtils.equals(period, PERIOD_AFTERNOOT)) {
	          throw new IllegalArgumentException("非法时间段参数！"); // 使用　throw　来抛出异常
	        }
	      }			  
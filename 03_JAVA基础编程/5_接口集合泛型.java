** 集合接口
	Collection 接口
		允许你使用一组对象，是Collection层次结构的根接口。
	List 接口
		继承于Collection和一个 List实例存储一个有序集合的元素。
	Set
		继承于 Collection，是一个不包含重复元素的集合。
	SortedSet
		继承于Set保存有序的集合。
	Map
		将唯一的键映射到值。
	Map.Entry
		描述在一个Map中的一个元素（键/值对）。是一个Map的内部类。
	SortedMap
		继承于Map，使Key保持在升序排列。
	Enumeration
		这是一个传统的接口和定义的方法，通过它可以枚举（一次获得一个）对象集合中的元素。
		这个传统接口已被迭代器取代。
** 集合类
	AbstractCollection 
		实现了大部分的集合接口。
	AbstractList 
		继承于AbstractCollection 并且实现了大部分List接口。
	AbstractSequentialList 
		继承于 AbstractList ，提供了对数据元素的链式访问而不是随机访问。
	LinkedList
		继承于 AbstractSequentialList，实现了一个链表。
	ArrayList
		通过继承AbstractList，实现动态数组。
	AbstractSet 
		继承于AbstractCollection 并且实现了大部分Set接口。
	HashSet
		继承了AbstractSet，并且使用一个哈希表。
	LinkedHashSet
		具有可预知迭代顺序的 Set 接口的哈希表和链接列表实现。
	TreeSet
		继承于AbstractSet，使用元素的自然顺序对元素进行排序.
	AbstractMap 
		实现了大部分的Map接口。
	HashMap
		继承了HashMap，并且使用一个哈希表。
	TreeMap
		继承了AbstractMap，并且使用一颗树。
	WeakHashMap
		继承AbstractMap类，使用弱密钥的哈希表。
	LinkedHashMap
		继承于HashMap，使用元素的自然顺序对元素进行排序.
	IdentityHashMap
		继承AbstractMap类，比较文档时使用引用相等。

	Vector
		Vector类实现了一个动态数组。和ArrayList和相似，但是两者是不同的。
	Stack
		栈是Vector的一个子类，它实现了一个标准的后进先出的栈。
	Dictionary
		Dictionary 类是一个抽象类，用来存储键/值对，作用和Map类相似。
	Hashtable
		Hashtable是原始的java.util的一部分， 是一个Dictionary具体的实现 。
	Properties
		Properties 继承于 Hashtable.表示一个持久的属性集.属性列表中每个键及其对应值都是一个字符串。
	BitSet
		一个Bitset类创建一种特殊类型的数组来保存位值。BitSet中数组大小会随需要增加。

**  泛型方法
		你可以写一个泛型方法，该方法在调用时可以接收"不同类型"的参数。
		根据传递给泛型方法的"参数类型"，编译器适当地处理每一个方法调用。
	* 下面是定义泛型方法的规则：
    	所有泛型方法声明都有一个类型参数声明部分（由尖括号分隔），
    	该类型参数声明部分在"方法返回类型之前"（在下面例子中的<E>）。
    * 注意类型参数只能代表引用型类型，不能是原始类型（像int,double,char的等）。

** Java 序列化
	*     	
** javadoc 标签
	@author 	
		标识一个类的作者 	@author description
	@deprecated 	
		指名一个过期的类或成员 	@deprecated description
	{@docRoot} 	
		指明当前文档根目录的路径 	Directory Path
	@exception 	
		标志一个类抛出的异常 	@exception exception-name explanation
	{@inheritDoc} 	
		从直接父类继承的注释 	Inherits a comment from the immediate surperclass.
	{@link} 	
		插入一个到另一个主题的链接 	{@link name text}
	{@linkplain} 	
		插入一个到另一个主题的链接，但是该链接显示纯文本字体 	Inserts an in-line link to another topic.
	@param 	
		说明一个方法的参数 	@param parameter-name explanation
	@return 	
		说明返回值类型 	@return explanation
	@see 	
		指定一个到另一个主题的链接 	@see anchor
	@serial 	
		说明一个序列化属性 	@serial description
	@serialData 	
		说明通过writeObject( ) 和 writeExternal( )方法写的数据 	@serialData description
	@serialField 	
		说明一个ObjectStreamField组件 	@serialField name type description
	@since 	
		标记当引入一个特定的变化时 	@since release
	@throws 	
		和 @exception标签一样. 	The @throws tag has the same meaning as the @exception tag.
	{@value} 	
		显示常量的值，该常量必须是static属性。 	Displays the value of a constant, which must be a static field.
	@version 	
		指定类的版本 	@version info
————————————————————————————————————————————————————————————————————————————————————————————————————————————————		
** Java 重写(Override)与重载(Overload)
* 重写(Override)
	* 
* 方法的重写规则

* Super关键字的使用

————————————————————————————————————————————————————————————————————————————————————————————————————————————————		
** Java 抽象类
	* public abstract class Employee
* 继承抽象类
	* public class Salary extends Employee
* 抽象方法
	* 继承抽象方法的子类必须重写该方法。
		否则，该子类也必须声明为抽象类。
		最终，必须有子类实现该抽象方法，
		否则，从最初的父类到最终的子类都不能用来实例化对象。


————————————————————————————————————————————————————————————————————————————————————————————————————————————————		
** Java 封装
	* 这些方法被称为getter和setter方法。
		因此，任何要访问类中私有成员变量的类都要通过这些getter和setter方法。
————————————————————————————————————————————————————————————————————————————————————————————————————————————————		
** 接口
	接口（英文：Interface），在JAVA编程语言中是一个抽象类型，是抽象方法的集合，接口通常以interface来声明。
	一个类通过继承接口的方式，从而来继承接口的抽象方法。

	接口并不是类，编写接口的方式和类很相似，但是它们属于不同的概念。
	类'描述对象的属性和方法'。接口则包含类要'实现的方法'。
	除非实现接口的类是抽象类，否则该类要定义接口中的所有方法。
* 接口的声明
	接口的声明语法格式如下：
		interface 接口名称 [extends 其他的类名] {
		        // 声明变量
		        // 抽象方法
		}
	* 接口有以下特性：
	    接口是隐式抽象的，当声明一个接口的时候，不必使用abstract关键字。
	    接口中每一个方法也是隐式抽象的，声明时同样不需要abstract关键子。
	    接口中的方法都是公有的。
* 接口的实现
		当类实现接口的时候，类要实现接口中所有的方法。
		否则，类必须声明为抽象的类。
		类使用 Implements 关键字实现接口。在类声明中，Implements关键字放在class声明后面。
	* 实现一个接口的语法，可以使用这个公式：
		... implements 接口名称[, 其他接口, 其他接口..., ...] ...
		public class MammalInt implements Animal
* 接口的继承
* 接口的多重继承
* 标记接口

————————————————————————————————————————————————————————————————————————————————————————————————————————————————		
————————————————————————————————————————————————————————————————————————————————————————————————————————————————		
* Java Applet基础
	*  applet是一种Java程序。它一般运行在支持Java的Web浏览器内。因为它有完整的Java API支持,所以applet是一个全功能的Java应用程序。
			如下所示是独立的Java应用程序和applet程序之间重要的不同：
	    * Java中applet类继承了 java.applet.Applet类
	    * Applet类没有定义main()，所以一个 Applet程序不会调用main()方法，
	    * Applets被设计为嵌入在一个HTML页面。
	    * 当用户浏览包含Applet的HTML页面，Applet的代码就被下载到用户的机器上。
	    * 要查看一个applet需要JVM。 JVM可以是Web浏览器的一个插件，或一个独立的运行时环境。
	    * 用户机器上的JVM创建一个applet类的实例，并调用Applet生命周期过程中的各种方法。
	    * Applets有Web浏览器强制执行的严格的安全规则，applet的安全机制被称为沙箱安全。
	    * applet需要的其他类可以用Java归档（JAR）文件的形式下载下来。
	* "Hello, World" Applet: 
			public class HelloWorldApplet extends Applet
			{ public void paint (Graphics g)
			   { g.drawString ("Hello World", 25, 50); } }
————————————————————————————————————————————————————————————————————————————————————————————————————————————————		
————————————————————————————————————————————————————————————————————————————————————————————————————————————————		
Thread 方法
	public void start()
		使该线程开始执行；Java 虚拟机调用该线程的 run 方法。
	public void run()
		如果该线程是使用独立的 Runnable 运行对象构造的，则调用该 Runnable 对象的 run 方法；
		否则，该方法不执行任何操作并返回。

	public final void setName(String name)
		改变线程名称，使之与参数 name 相同。
	public final void setPriority(int priority)
		更改线程的优先级。
	public final void setDaemon(boolean on)
		将该线程标记为守护线程或用户线程。
	public final void join(long millisec)
		等待该线程终止的时间最长为 millis 毫秒。
	public void interrupt()
		中断线程。
	public final boolean isAlive()
		测试线程是否处于活动状态。
————————————————————————————————————————————————————————————————————————————————————————————————————————————————
* 多线程
	* 就绪状态:
* 线程的优先级
	* Java线程的优先级是一个整数，其取值范围是1 （Thread.MIN_PRIORITY ） - 10 （Thread.MAX_PRIORITY ）。
		默认情况下，每一个线程都会分配一个优先级NORM_PRIORITY（5）。 
* 创建一个线程
** 通过实现Runnable接口来创建线程
	* Java提供了两种创建线程方法：
	    通过实现Runable接口；
	    通过继承Thread类本身。
	* 实例
			// 创建一个新的线程
			class NewThread implements Runnable {
			   Thread t;
			   NewThread() {
			      t = new Thread(this, "Demo Thread"); // 创建第二个新线程
			      System.out.println("Child thread: " + t);
			      t.start(); // 开始线程
			   }
			  
			   public void run() { // 第二个线程入口
			      try {
			         for(int i = 5; i > 0; i--) {
			            System.out.println("Child Thread: " + i);
			            Thread.sleep(50); // 暂停线程
			         }
			     } catch (InterruptedException e) {
			         System.out.println("Child interrupted.");
			     }
			     System.out.println("Exiting child thread.");
			   }
			}
			 
			public class ThreadDemo {
			   public static void main(String args[]) {
			      new NewThread(); // 创建一个新线程
			      try {
			         for(int i = 5; i > 0; i--) {
			           System.out.println("Main Thread: " + i);
			           Thread.sleep(100);
			         }
			      } catch (InterruptedException e) {
			         System.out.println("Main thread interrupted.");
			      }
			      System.out.println("Main thread exiting.");
			   }
			}

** 通过继承Thread来创建线程
			// 通过继承 Thread 创建线程
			class NewThread extends Thread {
			   NewThread() {
			      // 创建第二个新线程
			      super("Demo Thread");
			      System.out.println("Child thread: " + this);
			      start(); // 开始线程
			   }
			 
			   // 第二个线程入口
			   public void run() {
			      try {
			         for(int i = 5; i > 0; i--) {
			            System.out.println("Child Thread: " + i);
			                            // 让线程休眠一会
			            Thread.sleep(50);
			         }
			      } catch (InterruptedException e) {
			         System.out.println("Child interrupted.");
			      }
			      System.out.println("Exiting child thread.");
			   }
			}
			 
			public class ExtendThread {
			   public static void main(String args[]) {
			      new NewThread(); // 创建一个新线程
			      try {
			         for(int i = 5; i > 0; i--) {
			            System.out.println("Main Thread: " + i);
			            Thread.sleep(100);
			         }
			      } catch (InterruptedException e) {
			         System.out.println("Main thread interrupted.");
			      }
			      System.out.println("Main thread exiting.");
			   }
			}



























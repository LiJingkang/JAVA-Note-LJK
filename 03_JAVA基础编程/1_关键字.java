* JavaBean	
	1、所有属性为 private
	2、提供默认构造方法
	3、提供 getter 和 setter
	4、实现 serializable 接口 

	* @Autowried
		// 来自动装配bean 
	* JavaBean 
		// 对属性进行处理
		也是一个 java类，知识一个有get set 方法的类而已。
	* 可以在 jsp 中调用  <jsp:getProperty/>


	abstract 	抽象方法，抽象类的修饰符
	assert 	断言条件是否满足
	boolean 	布尔数据类型
	break 	跳出循环或者label代码段
	byte 	8-bit 有符号数据类型
	case 	switch语句的一个条件
	catch 	和try搭配扑捉异常信息
	char 	16-bit Unicode字符数据类型
	class 	定义类
	const 	未使用
	continue 	不执行循环体剩余部分
	default 	switch语句中的默认分支
	do 	循环语句，循环体至少会执行一次
	double 	64-bit双精度浮点数
	else 	if条件不成立时执行的分支
	enum 	枚举类型
	extends 	表示一个类是另一个类的子类
	final 	表示一个值在初始化之后就不能再改变了
	表示方法不能被重写，或者一个类不能有子类
	finally 	为了完成执行的代码而设计的，主要是为了程序的健壮性和完整性，无论有没有异常发生都执行代码。
	float 	32-bit单精度浮点数
	for 	for循环语句
	goto 	未使用
	if 	条件语句
	implements 	表示一个类实现了接口
	import 	导入类
	instanceof 	测试一个对象是否是某个类的实例
	int 	32位整型数
	interface 	接口，一种抽象的类型，仅有方法和常量的定义
	long 	64位整型数
	native 	表示方法用非java代码实现
	new 	分配新的类实例
	package 	一系列相关类组成一个包
	private 	表示私有字段，或者方法等，只能从类内部访问
	protected 	表示字段只能通过类或者其子类访问
	子类或者在同一个包内的其他类
	public 	表示共有属性或者方法
	return 	方法返回值
	short 	16位数字
	static 	表示在类级别定义，所有实例共享的
	strictfp 	浮点数比较使用严格的规则
	super 	表示基类
	switch 	选择语句
	synchronized 	表示同一时间只能由一个线程访问的代码块
	this 	表示调用当前实例
	或者调用另一个构造函数
	throw 	抛出异常
	throws 	定义方法可能抛出的异常
	transient 	修饰不要序列化的字段
	try 	表示代码块要做异常处理或者和finally配合表示是否抛出异常都执行finally中的代码
	void 	标记方法不返回任何值
	volatile 	标记字段可能会被多个线程同时访问，而不做同步
	while 	while循环

protected	
Static


Number 方法
	xxxValue()
		将number对象转换为xxx数据类型的值并返回。
	compareTo()
		将number对象与参数比较。
	equals()
		判断number对象是否与参数相等。
	valueOf()
		返回一个 Number 对象指定的内置数据类型
	toString()
		以字符串形式返回值。
	parseInt()
		将字符串解析为int类型。
	abs()
		返回参数的绝对值。
	ceil()
		对整形变量向左取整，返回类型为double型。
	floor()
		对整型变量向右取整。返回类型为double类型。
	rint()
		返回与参数最接近的整数。返回类型为double。
	round()
		返回一个最接近的int、long型值。
	min()
		返回两个参数中的最小值。
	max()
		返回两个参数中的最大值。
	exp()
		返回自然数底数e的参数次方。
	log()
		返回参数的自然数底数的对数值。
	pow()
		返回第一个参数的第二个参数次方。
	sqrt()
		求参数的算术平方根。
	sin()
		求指定double类型参数的正弦值。
	cos()
		求指定double类型参数的余弦值。
	tan()
		求指定double类型参数的正切值。
	asin()
		求指定double类型参数的反正弦值。
	acos()
		求指定double类型参数的反余弦值。
	atan()
		求指定double类型参数的反正切值。
	atan2()
		将笛卡尔坐标转换为极坐标，并返回极坐标的角度值。
	toDegrees()
		将参数转化为角度。
	toRadians()
		将角度转换为弧度。
	random()
		返回一个随机数。

Character 方法
	isLetter()
		是否是一个字母
	isDigit()
		是否是一个数字字符
	isWhitespace()
		是否一个空格
	isUpperCase()
		是否是大写字母
	isLowerCase()
		是否是小写字母
	toUpperCase()
		指定字母的大写形式
	toLowerCase()
		指定字母的小写形式
	toString()
		返回字符的字符串形式，字符串的长度仅为1

String 方法
	char charAt(int index)
		返回指定索引处的 char 值。
	int compareTo(Object o)
		把这个字符串和另一个对象比较。
	int compareTo(String anotherString)
		按字典顺序比较两个字符串。
	int compareToIgnoreCase(String str)
		按字典顺序比较两个字符串，不考虑大小写。
	String concat(String str)
		将指定字符串连接到此字符串的结尾。
	boolean contentEquals(StringBuffer sb)
		当且仅当字符串与指定的StringButter有相同顺序的字符时候返回真。
	static String copyValueOf(char[] data)
		返回指定数组中表示该字符序列的 String。
	static String copyValueOf(char[] data, int offset, int count)
		返回指定数组中表示该字符序列的 String。
	boolean endsWith(String suffix)
		测试此字符串是否以指定的后缀结束。
	boolean equals(Object anObject)
		将此字符串与指定的对象比较。
	boolean equalsIgnoreCase(String anotherString)
		将此 String 与另一个 String 比较，不考虑大小写。
	byte[] getBytes()
		使用平台的默认字符集将此 String 编码为 byte 序列，并将结果存储到一个新的 byte 数组中。
	byte[] getBytes(String charsetName)
		使用指定的字符集将此 String 编码为 byte 序列，并将结果存储到一个新的 byte 数组中。
	void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin)
		将字符从此字符串复制到目标字符数组。
	int hashCode()
		返回此字符串的哈希码。
	int indexOf(int ch)
		返回指定字符在此字符串中第一次出现处的索引。
	int indexOf(int ch, int fromIndex)
		返回在此字符串中第一次出现指定字符处的索引，从指定的索引开始搜索。
	int indexOf(String str)
		返回指定子字符串在此字符串中第一次出现处的索引。
	int indexOf(String str, int fromIndex)
		返回指定子字符串在此字符串中第一次出现处的索引，从指定的索引开始。
	String intern()
		返回字符串对象的规范化表示形式。
	int lastIndexOf(int ch)
		返回指定字符在此字符串中最后一次出现处的索引。
	int lastIndexOf(int ch, int fromIndex)
		返回指定字符在此字符串中最后一次出现处的索引，从指定的索引处开始进行反向搜索。
	int lastIndexOf(String str)
		返回指定子字符串在此字符串中最右边出现处的索引。
	int lastIndexOf(String str, int fromIndex)
		返回指定子字符串在此字符串中最后一次出现处的索引，从指定的索引开始反向搜索。
	int length()
		返回此字符串的长度。
	boolean matches(String regex)
		告知此字符串是否匹配给定的正则表达式。
	boolean regionMatches(boolean ignoreCase, int toffset, String other, int ooffset, int len)
		测试两个字符串区域是否相等。
	boolean regionMatches(int toffset, String other, int ooffset, int len)
		测试两个字符串区域是否相等。
	String replace(char oldChar, char newChar)
		返回一个新的字符串，它是通过用 newChar 替换此字符串中出现的所有 oldChar 得到的。
	String replaceAll(String regex, String replacement
		使用给定的 replacement 替换此字符串所有匹配给定的正则表达式的子字符串。
	String replaceFirst(String regex, String replacement)
		使用给定的 replacement 替换此字符串匹配给定的正则表达式的第一个子字符串。
	String[] split(String regex)
		根据给定正则表达式的匹配拆分此字符串。
	String[] split(String regex, int limit)
		根据匹配给定的正则表达式来拆分此字符串。
	boolean startsWith(String prefix)
		测试此字符串是否以指定的前缀开始。
	boolean startsWith(String prefix, int toffset)
		测试此字符串从指定索引开始的子字符串是否以指定前缀开始。
	CharSequence subSequence(int beginIndex, int endIndex)
		返回一个新的字符序列，它是此序列的一个子序列。
	String substring(int beginIndex)
		返回一个新的字符串，它是此字符串的一个子字符串。
	String substring(int beginIndex, int endIndex)
		返回一个新字符串，它是此字符串的一个子字符串。
	char[] toCharArray()
		将此字符串转换为一个新的字符数组。
	String toLowerCase()
		使用默认语言环境的规则将此 String 中的所有字符都转换为小写。
	String toLowerCase(Locale locale)
		使用给定 Locale 的规则将此 String 中的所有字符都转换为小写。
	String toString()
		返回此对象本身（它已经是一个字符串！）。
	String toUpperCase()
		使用默认语言环境的规则将此 String 中的所有字符都转换为大写。
	String toUpperCase(Locale locale)
		使用给定 Locale 的规则将此 String 中的所有字符都转换为大写。
	String trim()
		返回字符串的副本，忽略前导空白和尾部空白。
	static String valueOf(primitive data type x)
		返回给定data type类型x参数的字符串表示形式。
————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
* native
	今日在hibernate源代码中遇到了native关键词，甚是陌生，就查了点资料，
	对native是什么东西有了那么一点了解，并做一小记。

	native 关键字说明其修饰的方法是一个原生态方法，方法对应的实现不是在当前文件，
		而是在用其他语言（如C和C++）实现的文件中。

	Java语言本身不能对操作系统底层进行访问和操作，
	但是可以通过 JNI 接口调用 其他语言 来 实现对底层的访问。

* JNI
	JNI 是 Java 本机接口（Java Native Interface），是一个本机编程接口，
		它是Java软件开发工具箱（Java Software Development Kit，SDK）的一部分。
		JNI允许Java代码使用以其他语言编写的代码和代码库。

	Invocation API（JNI的一部分）可以用来将Java虚拟机（JVM）嵌入到本机应用程序中，
		从而允许程序员从本机代码内部调用Java代码。

不过，对Java外部的调用通常不能移植到其他平台，在 applet 中还可能引发安全异常。
	实现本地代码将使您的 Java应用程序无法通过 100%纯Java测试。

但是，如果必须执行本地调用，则要考虑几个准则：

	1. 将您的所有本地方法都封装到一个类中，这个类调用单个的DLL。
		对每一种目标操作系统平台，都可以用特定于适当平台的版本的DLL。
		这样可以将本地代码的影响减少到最小，并有助于将以后所需要的移植问题考虑在内。

	2.本地方法尽量简单。
		尽量使您的本地方法对第三方（包括 Microsoft）运行时DLL的依赖减少到最小。
		使您的本地方法尽量独立，以将加载您的DLL和应用程序所需的开销减少到最小。
		如果需要运行时 DLL，必须随应用程序一起提供。
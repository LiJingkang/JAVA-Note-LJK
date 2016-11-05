* 学习PrintWriter类 
java.io包

1> 首先先知道它的八种构造方法，但怎么记住这八种呢？我们都知道PrintWriter是一种过滤流，也叫处理流。
    也就是能对字节流和字符流进行处理，所以它会有：
    * PrintWriter(OutputStream out)  
                根据现有的 OutputStream 创建不带自动行刷新的新 PrintWriter。
    * PrintWriter(Writer out)  
                创建不带自动行刷新的新 PrintWriter。
        
* 这两种构造方法。由于 PrintWriter 能够实现自动刷新所以
* 又衍生出另两种：
    * PrintWriter(OutputStream out, boolean autoFlush)  
                通过现有的 OutputStream 创建新的 PrintWriter。
    * PrintWriter(Writer out, boolean autoFlush)  
                创建新 PrintWriter
        * true 代表能自动刷新。
                注意这四种均不能指定编码集，
                但 PrintStream 中对 OutprintStream 操作时是可以的。
                PrintWriter 能够直接对文件操作，所以还有这两种构造方法：
    * PrintWriter(File file) 
                使用指定文件创建不具有自动行刷新的新 PrintWriter。
    * PrintWriter(String fileName)  
                创建具有指定文件名称且不带自动行刷新的新 PrintWriter。

* 注意该构造方法无法指定能否自动刷新，但可以指定字符集所以
* 又衍生出两种：
    PrintWriter(File file, String csn)  
                创建具有指定文件和字符集且不带自动刷行新的新 PrintWriter。
    PrintWriter(String fileName, String csn)  
                创建具有指定文件名称和字符集且不带自动行刷新的新 PrintWriter。

2> 熟记这八种构造方法，你可以随心构造你需要的PrintWriter对象了（假如为pw）。
    那它实现了什么接口呢？
    1.Closeable接口， 
                所以它有pw.close()方法来实现对PrintWriter的关闭。
    2.Flushable接口，
                所以它有pw.flush()方法来实现人为的刷新。
    3.Appendable接口，
                所以它有pw.append(char c)方法来向此输出流中追加指定字符，等价于print().

3> 下面就是它的方法。
    * 返回类型为PrintWriter的方法
        append(char c)
        format(String regex,Object args)
                以指定格式的字符串和参数写入PrintWriter，我个人认为等同于printf
        printf()
    * 返回类型为void的方法
        println(Object obj)
                打印obj，可以是基本数据类型或对象，并换行
        print(Object obj)
                同上，但不换行
        write(int i) 
                写入单个字符i
        write(char[] buf)  
                写入字符数组。
        write(char[] buf, int off, int len)  
                写入字符数组的某一部分。
        write(String s) 
                写入字符串 
        write(String s, int off, int len)
                写入字符串的某一部分

    * 个人认为：
        write() 方法与 print()方法基本一致 但是write(char[] buf, int off, int len)print()不行。
        但笔者推荐使用Print系列方法，这正是PrintWriter类的价值所在。
        返回类型为boolean类型的方法
        checkError() 刷新流并检查其错误状态

4> 举例：
    public class PrintWriterDemo {
    public static void main(String[] args) {
        PrintWriter pw = null;
        String name = "张松伟";
        int age = 22;
        float score = 32.5f;
        char sex = '男';
    try{
        pw = new PrintWriter(new FileWriter(new File("e:\\file.txt")),true);
        pw.printf("姓名：%s;年龄：%d;性别：%c;分数：%5.2f;", name,age,sex,score);
        pw.println();
        pw.println("多多指教");
        pw.write(name.toCharArray());
        }
    catch(IOException e)
        {
        e.printStackTrace();
        }
    finally
        {
        pw.close();
        }
    }
    }

    上面这个例子中呢，完全可以把
        pw = new PrintWriter(new FileWriter(new File("e:\\file.txt")),true);
        中的new  FileWrite去掉 毫无影响且可以指定字符集。
    总结：
        PrintWriter 是一个非常实用的输出流，下一篇我将介绍一个非常实用的输入流Scanner
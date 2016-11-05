junit4 Assert静态方法介绍   .

分类： 
 
junit4（4）   


junit中的assert方法全部放在Assert类中，总结一下junit类中assert方法的分类。

1.assertTrue/False([String message,]boolean condition);
     用来查看变量是是否为false或true，如果assertFalse()
     查看的变量的值是false则测试成功，如果是true则失败，assertTrue()与之相反；

2.fail([String message,]);
     直接用来抛出错误。

3.assertEquals([String message,]Object expected,Object actual);
     判断是否相等，可以指定输出错误信息。
     第一个参数是期望值，第二个参数是实际的值。
     这个方法对各个变量有多种实现
    // 相等 通过
    // 不相等 不通过

4.assertNotNull/Null([String message,]Object obj);
     判读一个对象是否非空(非空)。
    // 不为空  通过
    // 为空 不通过

5.assertSame/NotSame([String message,]Object expected,Object actual);
     判断两个对象是否指向同一个对象。看内存地址。

7.failNotSame/failNotEquals(String message, Object expected, Object actual)
     当不指向同一个内存地址或者不相等的时候，输出错误信息。
     注意信息是必须的，而且这个输出是格式化过的。


JUnit中的assert方法全部放在Assert类中，现在总结一下经常用到的junit类中assert方法。 
1.assertTrue/False([String message],boolean condition) 
    判断一个条件是true还是false。 
2.fail([String message,]); 
    失败，可以有消息，也可以没有消息。 
3.assertEquals([String message],Object expected,Object actual); 
    判断是否相等，可以指定输出错误信息。 
4.assertNotNull/Null([String message],Object obj); 
    判读一个对象是否非空(非空)。 
5.assertSame/NotSame([String message],Object expected,Object actual); 
    判断两个对象是否指向同一个对象。看内存地址。 

另外还有其他的几个Annotation： 
@Before： 
    使用了该元数据的方法在每个测试方法执行之前都要执行一次。 
    
@After： 
    使用了该元数据的方法在每个测试方法执行之后要执行一次。 

    注意：@Before和@After标示的方法只能各有一个。这个相当于取代了JUnit以前版本中的setUp和tearDown方法。 

@Test(expected=*.class) 
    在JUnit4.0之前，对错误的测试，我们只能通过fail来产生一个错误，并在try块里面assertTrue（true）来测试。现在，通过@Test元数据中的expected属性。expected属性的值是一个异常的类型 

@Test(timeout=xxx): 
    该元数据传入了一个时间（毫秒）给测试方法， 
    如果测试方法在制定的时间之内没有运行完，则测试也失败。 

@ignore： 
    该元数据标记的测试方法在测试中会被忽略。当测试的方法还没有实现，
    或者测试的方法已经过时，或者在某种条件下才能测试该方法
    （比如需要一个数据库联接，而在本地测试的时候，数据库并没有连接），
    那么使用该标签来标示这个方法。
    同时，你可以为该标签传递一个String的参数，来表明为什么会忽略这个测试方法。
    比如：@lgnore(“该方法还没有实现”)，在执行的时候，仅会报告该方法没有实现，
    而不会运行测试方法.
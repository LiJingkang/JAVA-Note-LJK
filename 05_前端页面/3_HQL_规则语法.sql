* hibernate -- HQL语句总结

1. 查询整个映射对象所有字段
    * 直接from查询出来的是一个映射对象，即：查询整个映射对象所有字段   
        String hql = "from Users";   
        Query query = session.createQuery(hql);   
            
        List<Users> users = query.list();   
        for(Users user : users){   
            System.out.println(user.getName() + " : " + user.getPasswd() + " : " + user.getId());   
        }   
   
        输出结果为：   
        name1 : password1 : 1 
        name2 : password2 : 2 
        name3 : password3 : 3 

2.查询字段　　
    * 查询其中几个字段   
        String hql = " select name,passwd from Users";   
        Query query = session.createQuery(hql);   

    * 默认查询出来的list里存放的是一个Object数组   
        List<Object[]> list = query.list();   
        for(Object[] object : list){   
            String name = (String)object[0];   
            String passwd = (String)object[1];   
            System.out.println(name + " : " + passwd);   
        }   

        输出结果为：   
        name1 : password1   
        name2 : password2   
        name3 : password3  

3.修改默认查询结果
        (query.list())不以Object[]数组形式返回，以List形式返回
    * 查询其中几个字段,添加new list(),注意list里的l是小写的。也不需要导入包，
        这样通过query.list()出来的list里存放的
        不再是默认的Object数组了，而是List集合了  
      
        String hql = " select new list(name,passwd) from Users";   
        Query query = session.createQuery(hql);   

    * 默认查询出来的list里存放的是一个Object数组，但是在这里list里存放的不再是默认的Object数组了，而是List集合了   
        List<List> list = query.list();   
        for(List user : list){   
            String name = (String)user.get(0);   
            String passwd = (String)user.get(1);   
                
            System.out.println(name + " : " + passwd);   
        }   

        输出结果为：  
        name1 : password1  
        name2 : password2  
        name3 : password3  

4.修改默认查询结果
        (query.list())不以Object[]数组形式返回，以Map形式返回
    * 查询其中几个字段,添加new map(),注意map里的m是小写的。也不需要导入包，
        这样通过query.list()出来的list里存放的不再是默认的Object数组了，而是map集合了   

        String hql = " select new map(name,passwd) from Users";   
        Query query = session.createQuery(hql);   

    * 默认查询出来的list里存放的是一个Object数组，但是在这里list里存放的不再是默认的Object数组了，而是Map集合了   

        List<Map> list = query.list();   
        for(Map user : list){   

    * 一条记录里所有的字段值都是map里的一个元素,
        key是字符串 0,1,2,3....，value是字段值   

    * 如果将hql改为：
        String hql = " select new map(name as username,passwd as password) from Users"
            那么key将不是字符串0,1,2...了，而是"username","password"了   

            String name = (String)user.get("0");

    * get("0");是get(key),注意:0,1,2...是字符串，而不是整形   

            String passwd = (String)user.get("1");               
            System.out.println(name + " : " + passwd);   
        }   

        输出结果为：  
        name1 : password1  
        name2 : password2  
        name3 : password3  

5.修改默认查询结果
    (query.list())不以Object[]数组形式返回，以自定义类型返回

6.条件查询
    * 条件查询，参数索引值从0开始，索引位置。
        通过setString,setParameter设置参数   

        String hql = "from Users where name=? and passwd=?";   
        Query query = session.createQuery(hql);   
    * 第1种方式   
        query.setString(0, "name1");   
        query.setString(1, "password1");   
    * 第2种方式   
        query.setParameter(0, "name1",Hibernate.STRING);   
        query.setParameter(1, "password1",Hibernate.STRING);   

        List<Users> list = query.list();   
        for(Users users : list){   
            System.out.println(users.getId());   
        }   
 
    * 条件查询，自定义索引名(参数名)
        :username,:password.通过setString,setParameter设置参数   

        String hql = "from Users where name=:username and passwd=:password";   
        Query query = session.createQuery(hql);   

    * 第1种方式   
        query.setString("username", "name1");   
        query.setString("password", "password1");   

    * 第2种方式,第3个参数确定类型   
        query.setParameter("username", "name1",Hibernate.STRING);   
        query.setParameter("password", "password1",Hibernate.STRING);   

        List<Users> list = query.list();   
        for(Users users : list){   
            System.out.println(users.getId());   
        }  

    * 条件查询,通过setProperties设置参数   
        String hql = "from Users where name=:username and passwd=:password";   
        Query query = session.createQuery(hql);   
    * MyUser类的2个属性必须和:username和:password对应   
        MyUser myUser = new MyUser("name1","password1");   
        query.setProperties(myUser);   
        List<Users> list = query.list();   
        for(Users users : list){   
            System.out.println(users.getId());   
        }  

7.update 数据
    * 执行SQL语句(为了执行某些复杂的SQL语句）　
        String sql="update Table set field = 'test'"
        Session session = HibernateSessionFactory.getSession();
        session.createSQLQuery(sql).executeUpdate();
        ts.commit();

   * 执行HQL语句　　　
        String hql="update Table set field = 'test'"
        Session session = HiberanteSessionFactory.getSession();
        Transaction ts = session.beginTransaction();
        Query query = session.createQuery(hql);
        query.executeUpdate();
        ts.commit();
————————————————————————————————————————————————————————————————————————————————————————
    都像SELECT，FROM和WHERE等关键字不区分大小写，
    但如'表名和列名的属性是区分在HQL敏感'

* FROM 语句
    使用FROM子句，如果要加载一个完整的持久化对象到内存中。下面是一个使用FROM子句的简单的语法：

        String hql = "FROM Employee";
        Query query = session.createQuery(hql);
        List results = query.list();

    如果需要完全限定在HQL一个类名，只需指定如下的包和类名：

        String hql = "FROM com.hibernatebook.criteria.Employee";
        Query query = session.createQuery(hql);
        List results = query.list();

*AS 语句

    AS子句可以用来别名分配给类中的HQL查询，特别是当有很长的查询。

        String hql = "FROM Employee AS E";
        Query query = session.createQuery(hql);
        List results = query.list();

    AS关键字是可选的，也可以直接在之后的类名指定别名，如下所示：

        String hql = "FROM Employee E";
        Query query = session.createQuery(hql);
        List results = query.list();

* SELECT 子句

    SELECT子句提供了更多的控制权比from子句的结果集。
    如果想获得对象而不是整个对象的几个属性，使用SELECT子句。
    下面是一个使用SELECT语句来获取Employee对象只是FIRST_NAME字段的简单的语法：

        String hql = "SELECT E.firstName FROM Employee E";
        Query query = session.createQuery(hql);
        List results = query.list();

    值得注意的是在这里，Employee.firstName是Employee对象的一个属性，而不是EMPLOYEE表的一个字段。

* WHERE 子句

        String hql = "FROM Employee E WHERE E.id = 10";
        Query query = session.createQuery(hql);
        List results = query.list();

* ORDER BY 子句

        String hql = "FROM Employee E WHERE E.id > 10 ORDER BY E.salary DESC";
        Query query = session.createQuery(hql);
        List results = query.list();

    如果想通过一个以上的属性进行排序

        String hql = "FROM Employee E WHERE E.id > 10 " +
                     "ORDER BY E.firstName DESC, E.salary DESC ";
        Query query = session.createQuery(hql);
        List results = query.list();

* GROUP BY 子句

    该子句允许从Hibernate的它基于属性的值的数据库和组提取信息，
    并且通常使用结果包括总值。下面是一个使用GROUP BY子句的语法很简单：

        String hql = "SELECT SUM(E.salary), E.firtName FROM Employee E " +
                     "GROUP BY E.firstName";
        Query query = session.createQuery(hql);
        List results = query.list();

* 使用命名参数

    Hibernate命名在其HQL查询参数支持。
    这使得编写接受来自用户的输入容易，不必对SQL注入攻击防御HQL查询。

        String hql = "FROM Employee E WHERE E.id = :employee_id";
        Query query = session.createQuery(hql);
        query.setParameter("employee_id",10);
        List results = query.list();

* UPDATE 子句

    批量更新是新的HQL与Hibernate3，以及不同的删除工作，在Hibernate 3和Hibernate2一样。
    Query接口现在包含一个名为executeUpdate()方法用于执行HQL UPDATE或DELETE语句。

    在UPDATE子句可以用于更新一个或多个对象中的一个或多个属性。下面是一个使用UPDATE子句的简单的语法：

        String hql = "UPDATE Employee set salary = :salary "  + 
                     "WHERE id = :employee_id";
        Query query = session.createQuery(hql);
        query.setParameter("salary", 1000);
        query.setParameter("employee_id", 10);
        int result = query.executeUpdate();
        System.out.println("Rows affected: " + result);

* DELETE 子句

    DELETE子句可以用来删除一个或多个对象。下面是一个使用DELETE子句的简单的语法：

        String hql = "DELETE FROM Employee "  + 
                     "WHERE id = :employee_id";
        Query query = session.createQuery(hql);
        query.setParameter("employee_id", 10);
        int result = query.executeUpdate();
        System.out.println("Rows affected: " + result);

* INSERT 子句

    HQL支持INSERT INTO子句中只记录在那里可以插入从一个对象到另一个对象。

        String hql = "INSERT INTO Employee(firstName, lastName, salary)"  + 
                     "SELECT firstName, lastName, salary FROM old_employee";
        Query query = session.createQuery(hql);
        int result = query.executeUpdate();
        System.out.println("Rows affected: " + result);

* 聚合方法

    HQL支持多种聚合方法，类似于SQL。
    他们工作在HQL同样的方式在SQL和下面的可用功能列表：

    DISTINCT关键字只计算在该行设定的唯一值。下面的查询将只返回唯一的计数：

    String hql = "SELECT count(distinct E.firstName) FROM Employee E";
    Query query = session.createQuery(hql);
    List results = query.list();

* 使用查询分页

    有用于分页查询接口的两个方法。

方法与说明
    Query setFirstResult(int startPosition)
        This method takes an integer that represents the first row in your result set, starting with row 0.
    Query setMaxResults(int maxResult)
        This method tells Hibernate to retrieve a fixed number maxResults of objects.

采用上述两种方法一起，可以在网站或Swing应用程序构建一个分页组件。下面是例子，可以扩展来获取10行：

String hql = "FROM Employee";
Query query = session.createQuery(hql);
query.setFirstResult(1);
query.setMaxResults(10);
List results = query.list();
一、Hibernate的三种查询方式（掌握） 

* Hibernate中提供了三种查询方式：
    1）Session的查询：
        按主键查询查询，方法为 get 或 load
    2）Query的查询：
        使用 HQL 语句或 SQL 语句完成查询
    3）Criteria的查询：
        通过方法和类中属性的关系，来设置查询条件，完成查询。

* Session 中 get 和 load 方法的区别？
    1） 如果没有查询到数据，get 会返回 null，而 load 则直接提示错误。
    2） 使用load查询时，可能会出现以下错误，因为load方式使用的是懒汉式加载方法。
        执行load方法时，不立刻查询数据库。当用到查询出的对象的属性时，才加载数据。

    解决这个异常的方法：

    1）  不关连接；2）不用load，就使用get就可以了
    1.1、Query查询（重点）

    1.1.1HQL语句不只支持查询功能，还支持修改以及删除功能

    [java] view plain copy
    在CODE上查看代码片派生到我的代码片

        public void doRemove(Integer id) throws Exception {  
                // 注意,使用Hibernate删除时,必须先查询对象,再删除.  
                // HibernateSessionFactory.getSession().delete(findById(id));  
                String hql = "DELETE FROM News AS n WHERE n.id = ?" ;  
                Query query = HibernateSessionFactory.getSession().createQuery(hql);  
                query.setInteger(0, id);  
                query.executeUpdate();  
            }  

    注：使用HQL的删除可以不需要先查询，直接删除，支持一次删除多条数据
    开发中的选择：1）当删除一条数据时，直接使用session.delete()就可以，因为简单，hibernate不在乎那点查询的性能；

    2）批量删除时，使用Hql形式，这是可以提高性能的方法，因为它中间省去了查询的步骤。


    1.1.2HQL语句修改功能

    [java] view plain copy
    在CODE上查看代码片派生到我的代码片

        public void doUpdate(News vo) throws Exception {  
                // HibernateSessionFactory.getSession().update(vo);  
                String hql = "UPDATE News AS n SET n.title = ?,n.content = ? WHERE n.id = ?" ;  
                Query query = HibernateSessionFactory.getSession().createQuery(hql);  
                query.setString(0, vo.getTitle());  
                // ....其他参数一样设置  
                query.executeUpdate();  
            }  

    开发中的选择：1）如果是直接的修改功能，肯定选择session.update()方法；

    2）如果是只修改某一字段，使用HQL方式。
    注：HQL语句不支持添加，但是Query支持添加。


    1.1.3针对HQL的查询功能，也支持写SELECT，可以通过编写SELECT，来只查询对象中某一个或某几个属性。

    但是对于多种字段不同类型的查询返回的，Hibernate中只能是数组。

    例如：

    [java] view plain copy
    在CODE上查看代码片派生到我的代码片

        public List testHQL() throws Exception {  
                String hql = "SELECT n.id,n.title FROM News AS n";  
                Query query = HibernateSessionFactory.getSession().createQuery(hql);  
                return query.list();  
            }  

    经过测试，发现当只查询一个字段时，直接返回该类型的List集合。

    但查询两个以上的字段时，返回的是List<Object[]>，每一条查询出的数据，使用Object[]来表示，这就很不方便。
    [java] view plain copy
    在CODE上查看代码片派生到我的代码片

        public void testHQL() throws Exception {  
                List all = ServiceFactory.getINewsServiceInstance().testHQL();  
                Object[] value1 = (Object[]) all.get(0);  
                System.out.println(value1[1]);  
            }  

    这样使用起来很麻烦，因此在Hibernate3.2以上的版本中，提供了一个自动转换类，可以将查询出的Object[]，自动转换为pojo 对象。
    [java] view plain copy
    在CODE上查看代码片派生到我的代码片

        public List testHQL() throws Exception {  
                String hql = "SELECT n.id AS id,n.title AS title FROM News AS n";  
                Query query = HibernateSessionFactory.getSession().createQuery(hql);  
                <span style="color:#cc0000;">query  
                        .setResultTransformer(new AliasToBeanResultTransformer(  
                                News.class));</span>  
                return query.list();  
            }  

    注：一般开发中不会使用这种方法，只有当表中的字段过多，但查询只需要其中的几个字段时，才会用到这种方法。


    1.1.4Hibernate还可以将语句写到配置文件中

    [java] view plain copy
    在CODE上查看代码片派生到我的代码片

        <query name="findAll">  
                FROM News AS n WHERE n.title LIKE ?  
            </query>  

    通过程序读取配置文件，取得这段HQL，并生成Query对象，完成查询。
    [java] view plain copy
    在CODE上查看代码片派生到我的代码片

        Query query = HibernateSessionFactory.getSession().getNamedQuery(  
                        "findAll");  
                query.setString(0, "%测试%");  
                return query.list();  

    这种方式在Mybatis中普遍使用，但是在Hibernate中一班很少这样做。
    1.2、Criteria查询（了解）
    Criteria也是Hibernate提供的一个查询对象，支持按对象的方式来完成查询。例如：

    查询全部功能：

    [java] view plain copy
    在CODE上查看代码片派生到我的代码片

        public List<News> testCriteria() throws Exception {  
                // 根据传入的pojo类型,查询该类型对应的全部数据  
                Criteria c = HibernateSessionFactory.getSession().createCriteria(  
                        News.class);          
                return c.list();  
            }  

    如果想加入查询条件，需要使用Restrictions的各种方法来完成条件的拼写。
    [java] view plain copy
    在CODE上查看代码片派生到我的代码片

        public List<News> testCriteria() throws Exception {  
                // 根据传入的pojo类型,查询该类型对应的全部数据  
                Criteria c = HibernateSessionFactory.getSession().createCriteria(  
                        News.class);  
                // 1、WHERE id = 26  
                // c.add(Restrictions.eq("id", 26));  
                // 2、WHERE id > 26  
                // c.add(Restrictions.gt("id", 26));  
                // 3、WHERE id < 26  
                // c.add(Restrictions.lt("id", 26));  
                // 4、WHERE id >= 26  
                // c.add(Restrictions.ge("id", 26));  
                // 5、WHERE id <= 26  
                // c.add(Restrictions.le("id", 26));  
                // 6、WHERE id <> 26  
                // c.add(Restrictions.ne("id", 26));  
                // 7、WHERE title LIKE '%测试%'  
                // c.add(Restrictions.like("title", "%测试%"));  
                // 8、WHERE id between 23 and 27  
                // c.add(Restrictions.between("id", 23, 27));  
                // 9、WHERE id IN (23,25,27)  
                // List<Integer> allIds = new ArrayList<Integer>();  
                // allIds.add(23);  
                // allIds.add(25);  
                // allIds.add(27);  
                // c.add(Restrictions.in("id", allIds));  
                // 10、复杂条件，需要使用and或or来连接各个条件  
                // WHERE id = 23 OR (id <> 26 AND title LIKE '%测试%')  
                c  
                        .add(Restrictions.or(Restrictions.eq("id", 23), Restrictions  
                                .and(Restrictions.ne("id", 26), Restrictions.like(  
                                        "title", "%测试%"))));  
                return c.list();  
            }  

    如果想加入ORDER BY排序条件，需要使用Order对象。
    [java] view plain copy
    在CODE上查看代码片派生到我的代码片

        c.addOrder(Order.desc("id"));  

    如果想加入统计函数和分组函数，则需要用到Projection这个类
    [java] view plain copy
    在CODE上查看代码片派生到我的代码片

        <span style="white-space:pre">  </span>ProjectionList pro = Projections.projectionList();  
                // 加入统计函数  
                pro.add(Projections.rowCount());  
                // 还可以加入分组条件  
                pro.add(Projections.groupProperty("title"));  
                c.setProjection(pro);  

二、Hibernate中Session操作的三种状态
* Session操作过程中的pojo对象存在三种状态：
    1） 瞬时态：
            该对象在数据库中没有对应的数据。（刚new出来的数据）
    2） 持久态：
            数据库中存在该对象对应的数据，同时操作该对象的Session也存在。
    3） 游离态：
            数据库中包含该对象对应的数据，但操作此对象的Session已经不存在或被关闭了。
* 三种状态之间的转换：
    瞬时 -->持久：save()，saveOrUpdate()
    持久 -->瞬时：delete()
    持久 -->游离：close()
    游离 --> 持久：update()，saveOrUpdate()

* 针对持久态对象，Hibernate还存在以下两个特点：
    1） 持久态对象，在同一 Session 中只存在同一个。
        a) 如果连接不关闭，多次查询同一条数据，只返回同一个对象，也就是只查询一次数据库。
        b) 此功能也被称为一级缓存，但实际开发中实用性很低。
    2） 修改持久态对象的属性，可以自动同步到数据库对应的数据中。
        a) 当修改了一个持久态对象的属性，而且提交了事务，
            则数据库自动调用更新操作，也一起修改。
        b) (用处) 当登陆后，要求将当前系统时间，作为最后登陆时间保存到数据库中时，可以使用。
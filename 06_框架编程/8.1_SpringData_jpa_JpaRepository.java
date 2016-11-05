AppleFramework 在数据访问控制层采用了 Spring Data 作为这一层的解决方案

1.Spring Data 所解决的问题 
    Spring Data :
        提供了一整套数据访问层(DAO)的解决方案，致力于减少数据访问层(DAO)的开发量。
        它使用一个叫作Repository的接口类为基础，它被定义为访问底层数据模型的超级接口。
        而对于某种具体的数据访问操作，则在其子接口中定义。 
            public interface Repository<T, ID extends Serializable> { 
            } 
        所有继承这个接口的interface都被spring所管理，
        此接口作为标识接口，功能就是用来控制domain模型的。 

    Spring Data 可以让我们只定义接口，只要遵循spring data的规范，就无需写实现类。 

2.什么是 Repository？ 
  2.1 Repository（资源库）：
        通过用来访问领域对象的一个类似集合的接口，在领域与数据映射层之间进行协调。
        这个叫法就类似于我们通常所说的DAO，在这里，我们就按照这一习惯把数据访问层叫 Repository 
      Spring Data给我们提供几个Repository，基础的Repository提供了最基本的数据访问功能，其几个子接口则扩展了一些功能。
     
      它们的继承关系如下： 
      
      Repository： 
          仅仅是一个标识，表明任何继承它的均为仓库接口类，方便Spring自动扫描识别 
      CrudRepository： 
          继承Repository，实现了一组CRUD相关的方法 
      PagingAndSortingRepository： 
          继承CrudRepository，实现了一组分页排序相关的方法 
      JpaRepository： 
          继承PagingAndSortingRepository，实现一组JPA规范相关的方法 
      JpaSpecificationExecutor： 
          比较特殊，不属于Repository体系，实现一组JPA Criteria查询相关的方法 

      我们自己定义的XxxxRepository需要继承JpaRepository，
          这样我们的XxxxRepository接口就具备了通用的数据访问控制层的能力。 

  2.2 JpaRepository 所提供的基本功能 

  2.2.1 CrudRepository<T, ID extends Serializable>： 
      这个接口提供了最基本的对实体类的添删改查操作 
              T save(T entity);
                  //保存单个实体 
              Iterable<T> save(Iterable<? extends T> entities);
                  //保存集合 
              T findOne(ID id);
                  //根据id查找实体 
              boolean exists(ID id);
                  //根据id判断实体是否存在 
              Iterable<T> findAll();
                  //查询所有实体,不用或慎用! 
              long count();
                  //查询实体数量 
              void delete(ID id);
                  //根据Id删除实体 
              void delete(T entity);
                  //删除一个实体 
              void delete(Iterable<? extends T> entities);
                  //删除一个实体的集合 
              void deleteAll();
                  //删除所有实体,不用或慎用! 

  2.2.2 PagingAndSortingRepository<T, ID extends Serializable> 
          这个接口提供了分页与排序功能  
          Iterable<T> findAll(Sort sort);
              //排序 
          Page<T> findAll(Pageable pageable);
              //分页查询（含排序功能） 
  2.2.3 JpaRepository<T, ID extends Serializable> 
          这个接口提供了JPA的相关功能 
          List<T> findAll();
              //查找所有实体 
          List<T> findAll(Sort sort);
              //排序 查找所有实体 
          List<T> save(Iterable<? extends T> entities);
              //保存集合 
          void flush();
              //执行缓存与数据库同步 
          T saveAndFlush(T entity); 
              //强制执行持久化 
          void deleteInBatch(Iterable<T> entities);
              //删除一个实体集合 

3.Spring data 查询 
  3.1 简单条件查询:查询某一个实体类或者集合 
      按照Spring data 定义的规则，查询方法以 find|read|get 开头 
      涉及条件查询时，条件的属性用条件关键字连接，要注意的是：条件属性以首字母大写其余字母小写为规定。 
      例如：
          定义一个Entity实体类 
          class User { 
            private String firstname; 
            private String lastname; 
          } 
      使用And条件连接时，应这样写： 
          findByLastnameAndFirstname(String lastname,String firstname); 
          // 条件的属性名称与个数要与参数的位置与个数一一对应 

  3.2 使用JPA NamedQueries （标准规范实现） 
      这种查询是标准的JPA规范所定义的，直接声明在Entity实体类上，
        调用时采用在接口中定义与命名查询对应的method，由Spring Data根据方法名自动完成命名查询的寻找。 
  3.2.1 在Entity实体类上使用@NamedQuery注解直接声明命名查询。 
          @Entity 
          @NamedQuery(name = "User.findByEmailAddress", 
            query = "select u from User u where u.emailAddress = ?1") 
          public class User { 

          } 
        注：定义多个时使用下面的注解 

          @NamedQueries(value = { 
          @NamedQuery(name = User.QUERY_FIND_BY_LOGIN, 
                      query = "select u from User u where u." + User.PROP_LOGIN 
                              + " = :username"), 
          @NamedQuery(name = "getUsernamePasswordToken", 
                      query = "select new com.aceona.weibo.vo.TokenBO(u.username,u.password) from User u where u." + User.PROP_LOGIN 
                            + " = :username")}) 
  3.2.2 在interface
          public interface UserRepository extends JpaRepository<User, Long> { 

            List<User> findByLastname(String lastname); 

            User findByEmailAddress(String emailAddress); 
          } 
  3.3 使用@Query自定义查询（Spring Data提供的） 
      这种查询可以声明在Repository方法中，
      摆脱像命名查询那样的约束，将查询直接在相应的接口方法中声明，结构更为清晰，
      这是Spring data的特有实现。 
      例如： 
          public interface UserRepository extends JpaRepository<User, Long> { 

            @Query("select u from User u where u.emailAddress = ?1") 
            User findByEmailAddress(String emailAddress); 
          } 
  3.4 @Query与 @Modifying 执行更新操作 
      这两个annotation一起声明，可定义个性化更新操作，例如只涉及某些字段更新时最为常用，
      示例如下： 
          @Modifying 
          @Query("update User u set u.firstname = ?1 where u.lastname = ?2") 
          int setFixedFirstnameFor(String firstname, String lastname); 

3.5 索引参数与命名参数 
  3.5.1 索引参数如下所示，索引值从1开始，查询中 ”?X” 个数需要与方法定义的参数个数相一致，
        并且顺序也要一致 
          @Modifying 
          @Query("update User u set u.firstname = ?1 where u.lastname = ?2") 
          int setFixedFirstnameFor(String firstname, String lastname); 

  3.5.2 命名参数（推荐使用这种方式） 
        可以定义好参数名，赋值时采用@Param("参数名")，而不用管顺序。
        如下所示： 
          public interface UserRepository extends JpaRepository<User, Long> { 

            @Query("select u from User u where u.firstname = :firstname or u.lastname = :lastname") 
            User findByLastnameOrFirstname(@Param("lastname") String lastname, 
                                           @Param("firstname") String firstname); 
          } 

4. Transactionality（事务） 
  4.1 操作单个对象的事务 
      Spring Data提供了默认的事务处理方式，即所有的查询均声明为只读事务，
      对于持久化，更新与删除对象声明为有事务。 
      参见org.springframework.data.jpa.repository.support.SimpleJpaRepository<T, ID> 
      @org.springframework.stereotype.Repository 
      @Transactional(readOnly = true) 
      public class SimpleJpaRepository<T, ID extends Serializable> implements JpaRepository<T, ID>, 
                      JpaSpecificationExecutor<T> { 
              …… 
              @Transactional 
              public void delete(ID id) { 

                      delete(findOne(id)); 
              } 
              …… 
              } 
    对于自定义的方法，如需改变spring data提供的事务默认方式，
    可以在方法上注解@Transactional声明 

4.2 涉及多个Repository的事务处理 
  进行多个Repository操作时，也应该使它们在同一个事务中处理，按照分层架构的思想，
  这部分属于业务逻辑层，因此，需要在Service层实现对多个Repository的调用，并在相应的方法上声明事务。 
      例如： 
              @Service(“userManagement”) 
              class UserManagementImpl implements UserManagement { 

                private final UserRepository userRepository; 
                private final RoleRepository roleRepository; 

                @Autowired 
                public UserManagementImpl(UserRepository userRepository, 
                  RoleRepository roleRepository) { 
                  this.userRepository = userRepository; 
                  this.roleRepository = roleRepository; 
                } 

                @Transactional 
                public void addRoleToAllUsers(String roleName) { 

                  Role role = roleRepository.findByName(roleName); 

                  for (User user : userRepository.readAll()) { 
                    user.addRole(role); 
                    userRepository.save(user); 
                  } 
              } 

5.关于DAO层的规范 
  5.1 对于不需要写实现类的情况：
          定义XxxxRepository 接口并继承JpaRepository接口，
          如果Spring data所提供的默认接口方法不够用，
          可以使用@Query在其中定义个性化的接口方法。 
  5.2 对于需要写实现类的情况：
          定义XxxxDao 接口并继承com.aceona.appleframework.persistent.data.GenericDao 
          书写XxxxDaoImpl实现类并继承com.aceona.appleframework.persistent.data.GenericJpaDao，
          同时实现XxxxDao接口中的方法 
        
          在Service层调用XxxxRepository接口与XxxxDao接口完成相应的业务逻辑 
———————————————————————————————————————————————————————————————————————————————————————————————————————————
1.核心接口：
    public interface Repository<T, ID extends Serializable> {
        }   
    这个接口只是一个空的接口，目的是为了统一所有Repository的类型，其接口类型使用了泛型，
    泛型参数中T代表实体类型，ID则是实体中id的类型。

2.再来看一下Repository的直接子接口CrudRepository中的方法：
    public interface CrudRepository<T, ID extends Serializable> extends Repository<T, ID> {
        <S extends T> S save(S entity);
        <S extends T> Iterable<S> save(Iterable<S> entities);
        T findOne(ID id);
        boolean exists(ID id);
        Iterable<T> findAll();
        Iterable<T> findAll(Iterable<ID> ids);
        long count();
        void delete(ID id);
        void delete(T entity);
        void delete(Iterable<? extends T> entities);
        void deleteAll();
    ｝
    此接口中的方法大多是我们在访问数据库中常用的一些方法，如果我们要写自己的DAO类的时候，
    只需定义个接口来集成它便可使用了。

3.再来看看Spring Data未我们提供分页和排序的Repository的接口PagingAndSortingRepository：
    public interface PagingAndSortingRepository<T, ID extends Serializable> extends CrudRepository<T, ID> {
        Iterable<T> findAll(Sort sort);
        Page<T> findAll(Pageable pageable);
    }
    这些Repository都是spring-data-commons提供给我们的核心接口，
    spring-data-commons是Spring Data的核心包。
    这个接口中为我们提供了数据的分页方法，以及排序方法。

4.JPA实现：
    针对 spring-data-jpa 又提供了一系列 repository 接口，
    其中有 JpaRepository 和 JpaSpecificationExecutor，这2个接口又有什么区别呢，
    我们分别来看看这2个接口的源码。
4.1 JpaRepository.class
    public interface JpaRepository<T, ID extends Serializable> extends PagingAndSortingRepository<T, ID> {
        List<T> findAll();
        List<T> findAll(Sort sort);
        <S extends T> List<S> save(Iterable<S> entities);
        void flush();
        T saveAndFlush(T entity);
        void deleteInBatch(Iterable<T> entities);
        void deleteAllInBatch();

    这个类继承自PagingAndSortingRepository，看其中的方法，
    可以看出里面的方法都是一些简单的操作，并未涉及到复杂的逻辑。
    当你在处理一些简单的数据逻辑时，便可继承此接口，

    * 例子
        本文JPA供应者选择的是Hibernate EntityManager，当然读者们也可以选择其他的JPA供应者，
        比如EclipseLink、OpenJPA，反正JPA是个标准，在无须修改的情况下便可移植。

        * 先定义一用户实体类 User.class：
            @Entity
            @Table( name = "spring_data_user" )
            @PrimaryKeyJoinColumn( name = "id" )
            public class User extends IdGenerator{

                private static final long serialVersionUID = 1L;
                
                private String name;
                private String username;
                private String password;
                private String sex;
                private Date birth;
                private String address;
                private String zip;
                    
                    //省略getter和setter
            }
            Id生成策略是采用的表生成策略

        * 持久层 IUserDao.class：
            @Repository("userDao")
            public interface IUserDao extends JpaRepository<User, Long>｛｝

        * spring的配置文件中加上以下代码
            <beans xmlns="http://www.springframework.org/schema/beans"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
                xmlns:jpa="http://www.springframework.org/schema/data/jpa"
                xsi:schemaLocation="http://www.springframework.org/schema/beans
                http://www.springframework.org/schema/data/jpa
                http://www.springframework.org/schema/data/jpa/spring-jpa.xsd">
            <jpa:repositories base-package="org.tea.springdata.**.dao" />
            </beans>
            加上这段后Spring就会将指定包中 @Repository的类注册为bean，
            将 bean 托管给 Spring。
            Spring data已经帮我们写好一个实现类了，
            而简单的操作我们只须这样继承JpaRepository就可以做CRUD操作了。
            由于我用的Cglib来动态代理，所以就不定义接口了，直接

        * 定义类UserService.class：
            @Service("userService")
            public class UserService {                
                @Autowired
                private IUserDao dao;           
                public void save(User user) {
                    dao.save(user);
                }
                public void delete(Long id) {
                    dao.delete(id);
                }
                public void update(User user) {
                    dao.saveAndFlush(user);
                }
                public List<User> findAll() {
                    return dao.findAll();
                }
            }

        * 单元测试。
            public class UserServiceTest {
                private static ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
                private static UserService userService = (UserService) context.getBean("userService");
                public void saveUser() {
                    StopWatch sw = new StopWatch(getClass().getSimpleName());
                    sw.start("Add a user information.");
                    User u = new User();
                    u.setName("John");
                    u.setSex("Man");
                    u.setUsername("JohnZhang");
                    u.setPassword("123456");
                    u.setBirth(new Date());
                    userService.save(u);
                    sw.stop();
                    System.err.println(sw.prettyPrint());
                }

                 public static void main(String[] args) {
                    UserServiceTest test = new UserServiceTest();
                    test.saveUser();
                }
            }

4.2 提供的接口。
    * JpaSpecificationExecutor.class
        public interface JpaSpecificationExecutor<T> {
            T findOne(Specification<T> spec);
            List<T> findAll(Specification<T> spec);
            Page<T> findAll(Specification<T> spec, Pageable pageable);
            List<T> findAll(Specification<T> spec, Sort sort);
            long count(Specification<T> spec);
        }
        在这个接口里面出现次数最多的类就是Specification.class，
        而这个类主要也就是围绕Specification来打造的，
        Specification.class 是 Spring Data JPA提供的一个查询规范，
        而你只需围绕这个规范来设置你的查询条件便可，
        我们来看一下Specification.class这个接口中有些什么东西。

    * Specification.class
        public interface Specification<T> {
            Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb);
        }
        只有一个方法toPredicate，而其中的参数大家并不陌生，都是JPA规范中的，
        ROOT查询中的条件表达式、CriteriaQuery条件查询设计器、CriteriaBuilder条件查询构造器，
        而我们在使用复杂对象查询时，实现该方法用JPA去构造对象查询便可。

    * 例子：
            @Repository("userDao")
            public interface IUserDao extends JpaSpecificationExecutor<User>{
                // 一个继承了 JpaSpecificationExecutor 的 Dao
            ｝
            仍然只是一个空接口，这次继承的是JpaSpecificationExecutor了。
        * 测试用例：
            查询用户表中name包含Sam的记录，并分页按照birth排倒序
            public class UserDaoTest {
                private static ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
                private static IUserDao userDao = (IUserDao) context.getBean("userDao");
                    // 得到userDao
                public void findBySpecAndPaginate() {
                    Page<User> page = userDao.findAll(new Specification<User>() {
                        // userDao继承的findAll。 传入 一个 Specificatrion<User> 
                        // 测试类里面直接传入一个Specification
                        // 在写接口的时候，传入一个查询条件，我们要将他变成specification 
                        @Override
                        public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                            root = query.from(User.class);
                            Path<String> nameExp = root.get("name");
                            return cb.like(nameExp, "%Sam%");
                        }

                    }, new PageRequest(1, 5, new Sort(Direction.DESC, new String[] { "birth" })));

                    StringBuilder stout = new StringBuilder(" 以下是姓名包含Sam人员信息 : ").append("\n");
                    stout.append("| 序号 | username | password | name | sex | birth |").append("\n");
                    int sortIndex = 1;
                    for (User u : page.getContent()) {
                        stout.append(" | ").append(sortIndex);
                        stout.append(" | ").append(u.getUsername());
                        stout.append(" | ").append(u.getPassword());
                        stout.append(" | ").append(u.getName());
                        stout.append(" | ").append(u.getSex());
                        stout.append(" | ").append(u.getBirth());
                        stout.append(" | \n");
                        sortIndex++;
                    }
                    System.err.println(stout);
                }

                public static void main(String[] args) {
                    UserDaoTest test = new UserDaoTest();
                    test.findBySpecAndPaginate();
                }
            }

            当然，这只是一个测试，很简单的一个条件查询方法。
            你也可以设计复杂的查询来得到自己所需的结果，我这只是写一个很简单的方法来带大家入门。
            写了两篇文章了，还没有讲Spring Data JPA为什么只需定义接口就可以使用，
            其实这也不难发现，查看源码，可以找到针对JpaRepository和JpaSpecificationExecutor有一个实现类，
            SimpleJpaRepository.class，这个类实现了刚才所提的两个接口。
            而Spring在给我们注入实现类的时候，就正是这个SimpleJpaRepository.class，
            具体的实现方式我就不在这意义赘述了，大家如果有兴趣可以去查看它的源码，和传统的JPA实现是一样的。

            通过这篇文章我们学习到了，当要使用复杂的条件查询时，
            我们可以选择使用此接口来完善我们的需求，这篇文章就讲到这里，
            在下一篇文章中我主要是讲Spring Data JPA为我们提供的注解查询。
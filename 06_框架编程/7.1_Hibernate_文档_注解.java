* hibernate
	// 信息系统2.0里面好像只使用在实体类里面
* Hibernate概述,Hibernate是什么？
* Hibernate架构
	* Hibernate使用各种现有的 Java API，如JDBC，Java事务API（JTA）和Java命名和目录接口（JNDI）。 
	  JDBC提供了常见的关系数据库功能的抽象的一个基本水平，使具有JDBC驱动程序，Hibernate的支持几乎任何数据库。 
	  JNDI和JTA允许Hibernate与J2EE应用服务器进行集成。

* 以下部分列出了每个参与Hibernate应用程序体系结构的类的对象的简要说明。

	* Configuration 对象:
		Configuration 对象是你在任何 Hibernate 应用程序中创建并通常在应用程序初始化创建一次，第一个 Hibernate 的对象。
		它代表了 Hibernate 所需的配置或属性文件。 

	* Configuration 对象提供了两个按键组成部分：
	    * 数据库连接：
	    		这是通过Hibernate支持的一个或多个配置文件来处理。
	    		这些文件是：
	    					hibernate.properties 和 
	    					hibernate.cfg.xml。
	    * 类映射设置
	    		此组件创建Java类和数据库表之间的连接

	* SessionFactory 对象:
		Configuration 对象用于创建一个 SessionFactory 对象，它反过来可以配置 Hibernate 的使用提供的配置文件的应用程序，并允许一个 Session 对象被实例化。
		通过 SessionFactory 是线程安全的对象和使用的应用程序的所有线程。

		通过 SessionFactory 是重量级的对象，因此通常它被应用程序时创建的启动和保持以备后用。
		将使用一个单独的配置文件需要每个数据库都有一个 SessionFactory 对象。所以，如果正在使用多个数据库，那么就需要创建多个 SessionFactory 的对象。
	
	* Session 对象:
		Session 对象用于获取与数据库的物理连接。 

		Session 对象是重量轻，设计了一个互动是需要与数据库每次被实例化。持久化对象被保存，并通过一个 Session 对象中检索。

		会话中的对象不应该保持开放很长一段时间，因为他们通常不被线程安全的，应该被创建并根据需要销毁他们。
	
	* Transaction 对象:
		事务代表一个工作单元与数据库和大部分 RDBMS 支持事务功能。在 Hibernate 事务是由一个基本的事务管理器和事务（从JDBC或JTA）来处理。

		这是一个可选的对象和 Hibernate 应用程序可以选择不使用这个接口，而不是在他们自己的应用程序代码管理事务。
	* Query 对象:
		查询对象使用 SQL 或 Hibernate 查询语言（HQL）字符串从数据库中检索数据并创建对象。
		一个查询实例是用来绑定查询参数，限制查询返回的结果数量，并最终执行查询。

	* Criteria 对象:
		Criteria 对象用于创建和执行面向对象的条件查询来检索对象。

* Hibernate 环境配置
* Hibernate 配置
* Hibernate Sessions
* Hibernate 持久化类
* Hibernate 映射文件
* Hibernate 映射类型
* Hibernate 实例
* Hibernate O/R 映射
* Hibernate 注解

	@Entity 注解:
		在 EJB3 规范说明都包含在 javax.persistence 包，所以我们导入这个包作为第一步。
		其次，我们使用了 @Entity 注解来这标志着这个类作为一个实体 bean Employee 类，因此它必须有一个无参数的构造函数，总算是有保护的范围可见。

	@Table 注解:
		@Table注释 允许指定的表将被用于保存该实体在数据库中的详细信息。

		@Table注释提供了四个属性，允许覆盖表的名称，它的目录，它的架构，并执行对列的唯一约束在表中。
		现在，我们使用的是刚刚是 EMPLOYEE 表的名称。

	@Id 和 @GeneratedValue 注解:
		每个实体 bean 将有一个主键，注释在类的 @Id 注解。
			主键可以是单个字段或根据表结构的多个字段的组合。

		默认情况下，@Id 注解会自动确定要使用的最合适的主键生成策略，但

		可以通过应用 @GeneratedValue注释，它接受两个参数，strategy 和 generator，不打算在这里讨论，只使用默认的默认键生成策略。
		让 Hibernate 确定要使用的 generator 类型使不同数据库之间代码的可移植性。

	@Column 注解:
		@Column 批注用于指定的列到一个字段或属性将被映射的细节。

		可以使用列注释以下最常用的属性：
			name 属性允许将显式指定列的名称。
	    	length 属性允许用于映射一个 value 尤其是对一个字符串值的列的大小。
	    	nullable 属性允许该列被标记为NOT NULL生成架构时。
	    	unique 属性允许被标记为只包含唯一值的列。

* Hibernate查询语言
* Hibernate查询条件
* Hibernate原生SQL
* Hibernate缓存
* Hibernate批量处理
* Hibernate拦截器

* distinct
    去重 用来处理返回的数据，这样就不会返回相同的字段。去重
    select distinct name from A

    select distinct name, id from A

    select distinct xing, ming from B

    --会提示错误，因为distinct必须放在开头
    select id, distinct name from A;   

    --表中name去重后的数目， SQL Server支持，而Access不支持
    select count(distinct name) from A;   

    --count是不能统计多个字段的，下面的SQL在SQL Server和Access中都无法运行。
    select count(distinct name, id) from A;

    --若想使用，请使用嵌套查询，如下：
    select count(*) from (select distinct xing, name from B) AS M;

    SELECT distinct jsh,jsbh,rybh
    FROM kssryjbxxb


SQL 语句例子
* select
    SELECT
    FROM
    WHERE
        AND  ANY (  SELECT
                FROM
                WHERE )

* insert
    INSERT
        INTO
        VALUES ( '330211111',jdbcType = VARCHAR,
                TO_CHAR( SYSDATE, 'yyyymmddhh24miss' )
            )
* update
        UPDATE      Table1
        SET         Table1.Value= Table2.NewValue
        FROM        Table1,Table2
        WHERE       Table1.Value=Table2.OldValue
* 表复制语句
  insert 
        into Table2 ( field1,field2,...
            ) 
        select value1,value2,... 
        from Table1
  
  SELECT vale1, value2 
        into Table2 
        from Table1        

        INSERT
    INTO
        YYCRB(
            ZYBH,
            RYBH,
            JSBH,
            YYR,
            YYLX,
            YYLXCode,
            YYNR,
            YYNRCode,
            YYSJ,
            SJC,
            STATE  // 列名
        ) SELECT
            ryxx.JSBH || '160728d13002',
            ryxx.RYBH,
            ryxx.JSBH,
            ry.XM,
            '看病',
            'doctor',
            '',
            'test',
            '20160728132633',
            '20160728132633',
            '0'
        FROM
            KSSRYJBXXB ryxx
        LEFT OUTER JOIN RYJBXXB ry ON
            ryxx.JBXXBH = ry.JBXXBH
        WHERE
            (
                ryxx.RYBH = '330211111201405290006'
            )
———————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
SQL 语句

as
        as 一般用在两个地方，一个是query的时候，用来重新指定返回的column 名字
            如：一个table 有个column叫 id， 
        我们的query是select id from table1.
        但是如果你不想叫id了，就可以重新命名，如叫 systemID 就可以这样写
        select id as systemId from table1;

        还有一个用法就是在create table 或 procedure 的时候，as 是个关键字。
        例如
        create table test as select * from table1
        这时候就会create 一个table test，他是完全copy table table1里的全部数据。

        * 别名例子
            select
                tm.RYBH,
                tm.MEMBER_ID id,
                vr.MEMBER_NAME name,
                vr.BALANCE balance,
                vr.DEPT_Name dormCode,
                vr.BYKYYE monthLimit,
                vr.KYYE availableBalance
            from
                V_RYXJYY3 vr join T_MEMBERS tm on
                tm.member_id = vr.member_id

case
        * 判断语句
        * 返回的是 then 的内容
        Case具有两种格式。简单Case函数和Case搜索函数。 
        --简单Case函数CASE sex
             WHEN '1' THEN '男' -- 当 1 的时候 返回 男 跳出
             WHEN '2' THEN '女' -- 当 2 的时候 返回 女 跳出
             ELSE '其他' end -- 当 其他 的时候 返回 NULL
        --Case搜索函数
             CASE WHEN sex = '1' THEN '男'
             WHEN sex = '2' THEN '女'
             ELSE '其他' END    

             select (case when a.score<60 then '不及格' else '及格' end ) as 成绩
             from tablename a //小于60不及格，否则及格

             select case a.sex when 'a' then '男' else '女' end from tablename a
             如果a.sex=a 则为男，否则为女
OR 
AND
        AND 和 OR 运算符
        AND 和 OR 可在 WHERE 子语句中把两个或多个条件结合起来。
        如果第一个条件和第二个条件都成立，则 AND 运算符显示一条记录。
        如果第一个条件和第二个条件中只要有一个成立，则 OR 运算符显示一条记录。

        使用 AND 来显示所有姓为 "Carter" 并且名为 "Thomas" 的人：
                SELECT * FROM Persons WHERE FirstName='Thomas' AND LastName='Carter'
        使用 OR 来显示所有姓为 "Carter" 或者名为 "Thomas" 的人：        
                SELECT * FROM Persons WHERE firstname='Thomas' OR lastname='Carter'
|| 
        字符串连接

LIKE
        LIKE 操作符LIKE 操作符用于在 WHERE 子句中搜索列中的指定模式。

        * 例子
            SELECT column_name(s)
            FROM table_name
            WHERE column_name LIKE pattern"Persons" 表中选取居住在以 "N" 开始的城市里的人：
            SELECT * FROM Persons
            WHERE City LIKE 'N%'
                            '%N%'
            WHERE("d.ZJHM LIKE '%" + prisonerQo.getIdentityId() + "%'");
HAVING
        对由sum或其它集合函数运算结果的输出进行限制。
        比如，我们可能只希望看到Store_Information数据表中销售总额超过1500美圆的商店的信息，
            这时我们就需要使用HAVING从句
        * 例子    
            SELECT store_name, SUM(sales)
            FROM Store_Information
            GROUP BY store_name
            HAVING SUM(sales) > 1500
ORDER BY
DESC
        ORDER BY 语句用于根据指定的列对结果集进行排序。
        ORDER BY 语句默认按照升序对记录进行排序。
        如果您希望按照降序对记录进行排序，可以使用 DESC 关键字。
        * 例子
            SELECT Company, OrderNumber FROM Orders ORDER BY Company DESC, OrderNumber ASC
            ORDER_BY("ksry.jsbh desc");
Join

LEFT OUTER JOIN
        left outer join 是左外连接，left outer join 也可以写成left join，因为 join 默认是 outer 属性的。

Inner Join
        Inner Join 逻辑运算符返回满足第一个（顶端）输入与第二个（底端）输入联接的每一行。
            这个和用 select 查询多表是一样的效果，所以很少用到;
outer join
        会返回每个满足第一个（顶端）输入与第二个（底端）输入的联接的行。它还返回任何在第二个输入中没有匹配行的第一个输入中的行。
        关键就是后面那句，返回的多一些。
        所以通常意义上的 left join 就是 left outer join 
LEFT JOIN 
        关键字会从左表 (Persons) 那里返回所有的行，即使在右表 (Orders) 中没有匹配的行。

JOIN
        如果表中有至少一个匹配，则返回行
LEFT JOIN
        即使右表中没有匹配，也从左表返回所有的行
RIGHT JOIN
        即使左表中没有匹配，也从右表返回所有的行
FULL JOIN
        只要其中一个表中存在匹配，就返回行
IN
        IN 操作符允许我们在 WHERE 子句中规定多个值。
        * 例子
            SELECT column_name(s)
            FROM table_name
            WHERE column_name IN (value1,value2,...)
        * 例子
            select TCK.KIND_NO,TC.COM_CODE, TC.COM_NAME, TC.PRICE, TC.UNIT , TC.IS_XG
            from T_COMMODITY_KIND TCK, T_COMMODITY TC 
            where TCK.KIND_NO = TC.KIND_NO 
            and TC.COM_CODE in 
                (
                    select goods_code
                    from DORM_ORDER
                    where order_id = #{orderId}
                    )
ANY
        * 比较
            SELECT * FROM Customers
            WHERE CustomerID = ANY (SELECT CustomerID FROM Customers WHERE MemberCategory = 'A')
             
            SELECT * FROM Customers 
            WHERE CustomerID IN (SELECT CustomerID FROM Customers WHERE MemberCategory = 'A')

        * = ANY 与 in 等价.
            any和some是同义词，他们之间没有逻辑区别
            in 的输入可以是自面值列表货返回单列的子查询，
            any/some 和all仅支持用子查询作为输入  // 当用于搜索子表的时候都可以使用
            如果你要用自面值列表作为这些谓词的输入，必须把该列值转化为子查询
count()
        * count() 是个聚合函数 作用是求表的所有记录数
        * 表名
            select * from 表名          
                这个是查询表的所有记录
            select count(*) from 表名   
                这个是查询表的所有 "记录数"
end  as

from    
        * from ryjbxxb ryxx, kssryjbxxb jbxxb, kssrybdxxb bdxxb
            可以并列几张表，但是不连接
alter
        * alter table test 
          add new_column varchar(100)
            在test表中添加一个叫做new_column的列类型是varchar(100)
        * ALTER TABLE table_name
          ADD column_name datatype            
———————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
* 创建数据库
Create DB
        * 例子
            CREATE DATABASE database_name  // 创建一个数据库
            CREATE DATABASE my_db
CREATE TABL
        * 例子
            CREATE TABLE 表名称
            (
            列名称1 数据类型,
            列名称2 数据类型,
            列名称3 数据类型,
            ....
            )
        * 存储类型
            CREATE TABLE Persons
            (
            Id_P int,
            LastName varchar(255),
            FirstName varchar(255),
            Address varchar(255),
            City varchar(255)
            )
Constraints
        * 约束用于限制加入表的数据的类型。
            * NOT NULL
            * UNIQUE
            * PRIMARY KEY
            * FOREIGN KEY
            * CHECK
            * DEFAULT
CREATE INDEX
        * 创建索引
        * 例子
                CREATE INDEX index_name
                ON table_name (column_name)  // 注释："column_name" 规定需要索引的列。
CREATE UNIQUE INDEX
        * 在表上创建一个唯一的索引。唯一的索引意味着两个行不能拥有相同的索引值。
        * 例子
                CREATE UNIQUE INDEX func_based_index 
                ON the_table
                         (CASE 
                            WHEN unique_value IS NOT NULL AND unique_group_id IS NOT NULL
                            THEN UNIQUE_VALUE || ',' || UNIQUE_GROUP_ID
                            END)


———————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
insert into
        * INSERT INTO 表名称 VALUES (值1, 值2,....)
        * 例子

———————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
delete
        DELETE FROM 表名称 WHERE 列名称 = 值

        * 删除某行
            DELETE FROM Person WHERE LastName = 'Wilson' 
        * 删除所有行
            DELETE FROM table_name
            DELETE * FROM table_name
———————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
Update
        UPDATE 表名称 SET 列名称 = 新值 WHERE 列名称 = 某值  

        * 更新某一行中的一个列
            我们为 lastname 是 "Wilson" 的人添加 firstname：
            UPDATE Person SET FirstName = 'Fred' WHERE LastName = 'Wilson' 
———————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
jdbcType
        inset 时候选择的存入类型
        * 类型对应见
            jdbcType与javaType的对应关系表
———————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
———————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
* SQL 函数
* functions
* Aggregate 函数
    * Aggregate 函数的操作面向一系列的值，并返回一个单一的值。
        AVG(column)     返回某列的平均值
        COUNT(column)   返回某列的行数（不包括 NULL 值）
        COUNT(*)        返回被选行数
        FIRST(column)   返回在指定的域中第一个记录的值
        LAST(column)    返回在指定的域中最后一个记录的值
        MAX(column)     返回某列的最高值
        MIN(column)     返回某列的最低值
        SUM(column)     返回某列的总和

        AVG(column)     返回某列的平均值     
        COUNT(column)   返回某列的行数（不包括NULL值）
        COUNT(*)        返回被选行数
        COUNT(DISTINCT column)  返回相异结果的数目
        FIRST(column)   返回在指定的域中第一个记录的值（SQLServer2000 不支持）
        LAST(column)    返回在指定的域中最后一个记录的值（SQLServer2000 不支持）
        MAX(column)     返回某列的最高值
        MIN(column)     返回某列的最低值 
        SUM(column)     返回某列的总和
* Scalar 函数
    * Scalar 函数的操作面向某个单一的值，并返回基于输入值的一个单一的值。
        UCASE(c)    将某个域转换为大写
        LCASE(c)    将某个域转换为小写
        MID(c,start[,end])  从某个文本域提取字符
        LEN(c)  返回某个文本域的长度
        INSTR(c,char)   返回在某个文本域中指定字符的数值位置
        LEFT(c,number_of_char)  返回某个被请求的文本域的左侧部分
        RIGHT(c,number_of_char)     返回某个被请求的文本域的右侧部分
        ROUND(c,decimals)   对某个数值域进行指定小数位数的四舍五入
        MOD(x,y)    返回除法操作的余数
        NOW()   返回当前的系统日期
        FORMAT(c,format)    改变某个域的显示方式
        DATEDIFF(d,date1,date2)     用于执行日期计算
———————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
* 函数例子
AVG()
        * SELECT AVG(column_name) FROM table_name
        * SELECT Customer FROM Orders
          WHERE OrderPrice>(SELECT AVG(OrderPrice) FROM Orders)
COUNT()
        * SELECT COUNT(column_name) FROM table_name
        * SELECT COUNT(*) FROM table_name
        * SELECT COUNT(DISTINCT column_name) FROM table_name
        * SELECT COUNT(DISTINCT Customer) AS NumberOfCustomers FROM Orders
FIRST() 
        * SELECT FIRST(column_name) FROM table_name
        * SELECT FIRST(OrderPrice) AS FirstOrderPrice FROM Orders
LAST()
        * SELECT LAST(column_name) FROM table_name
        * SELECT LAST(OrderPrice) AS LastOrderPrice FROM Orders
MAX() 
        * SELECT MAX(column_name) FROM table_name
        * SELECT MAX(OrderPrice) AS LargestOrderPrice FROM Orders

MIN()
        * SELECT MIN(column_name) FROM table_name
        * SELECT MIN(OrderPrice) AS SmallestOrderPrice FROM Orders
SUM()
        * SELECT SUM(column_name) FROM table_name
        * SELECT SUM(OrderPrice) AS OrderTotal FROM Orders
GROUP BY
        * 合计函数 (比如 SUM) 常常需要添加 GROUP BY 语句。
        * SELECT Customer,OrderDate,SUM(OrderPrice) FROM Orders
          GROUP BY Customer,OrderDate
HAVING
        * 在 SQL 中增加 HAVING 子句原因是，WHERE 关键字无法与合计函数一起使用。
        * SELECT column_name, aggregate_function(column_name)
            FROM table_name
            WHERE column_name operator value
            GROUP BY column_name
            HAVING aggregate_function(column_name) operator value
        * SELECT Customer,SUM(OrderPrice) FROM Orders
            GROUP BY Customer
            HAVING SUM(OrderPrice)<2000
UCASE()
        * SELECT UCASE(column_name) FROM table_name
LCASE()
        * SELECT LCASE(column_name) FROM table_name
MID()
        * SELECT MID(column_name,start[,length]) FROM table_name
        * SELECT MID(City,1,3) as SmallCity FROM Persons --　提取1 到 3 的字符
LEN() 
        * LEN 函数返回文本字段中值的长度。
        * SELECT LEN(column_name) FROM table_name
        * SELECT LEN(City) as LengthOfCity FROM Persons
ROUND() 
        * 函数用于把数值字段舍入为指定的小数位数
        * SELECT ROUND(column_name,decimals) FROM table_name
        * SELECT ProductName, ROUND(UnitPrice,0) as UnitPrice FROM Products
NOW() 
        * NOW 函数返回当前的日期和时间。
        * 如果您在使用 Sql Server 数据库，请使用 getdate() 函数来获得当前的日期时间。
        * SELECT NOW() FROM table_name
        * SELECT ProductName, UnitPrice, Now() as PerDate FROM Products
FORMAT() 
        * FORMAT 函数用于对字段的显示进行格式化。
        * SELECT FORMAT(column_name,format) FROM table_name
        * SELECT ProductName, UnitPrice, FORMAT(Now(),'YYYY-MM-DD') as PerDate
            FROM Products
———————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
Oracle 函数
dual 
        * Oracle提供的最小的工作表，只有一行一列，具有某些特殊功用。
        * select计算常量表达式、伪列等值
            oracle内部处理使它只返回一行数据，而使用其它表时可能返回多个数据行。
        * 查看当前用户
            select user from dual;
            select count(*) from dual;
        * 用做计算器
            select 7*9*10-10 from dual；
        * 调用系统函数
            * 获得当前系统时间
                select to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') from dual;
            * 获得主机名
                select sys_context('userenv','terminal') from dual;
            * 获得当前locale
                select sys_context('userenv','language') from dual;
            * 获得一个随机数
                select DBMS_RANDOM.random from dual;
        * 查看序列值
            * 创建序列aaa 以1开始，每次加1
                create sequence aaa increment by 1 start with 1;
            * 获得序列aaa 的下一个序列值
                select aaa.nextval from dual;
            * 获得序列aaa 的当前序列值
                select aaa.currval from dual;
trunc()
        * TRUNC(number,num_digits)
        * 函数 TRUNC 和函数 INT 类似，都返回整数。
            函数 TRUNC 直接去除数字的小数部分，而函数 INT 则是向下舍入到最接近的整数。
        * TRUNC（89.985，2）=89.98  -- 截取保留2位小数
        * TRUNC（89.985）=89  -- 不写，默认截取为整数
        * TRUNC（89.985，-1）=80   -- 负数，省略小数点前面的内容
        * 例子
                * TRUNC( MONTHS_BETWEEN ( to_date(b.GYQX,'yyyymmdd'), SYSDATE ) /12  
                    -- MONTHS_BETWEEN返回一个int数表示月份数目 /12 返回一个浮点数  
                    ) -- 不填写第二个参数，默认截取为整数
TRUNC(for dates)
        * TRUNC（date,[fmt]） // fmt 日期格式 
            * TRUNC(TO_DATE('24-Nov-1999 08:00 pm','dd-mon-yyyy hh:mi am'))
            * select trunc(sysdate) from dual     --2013-01-06 今天的日期为2013-01-06
            * select trunc(sysdate, 'mm') from dual   --2013-01-01 返回当月第一天.
            * select trunc(sysdate,'yy') from dual    --2013-01-01 返回当年第一天
            * select trunc(sysdate,'dd') from dual    --2013-01-06 返回当前年月日
            * select trunc(sysdate,'yyyy') from dual  --2013-01-01 返回当年第一天
            * select trunc(sysdate,'d') from dual     --2013-01-06 (星期天)返回当前星期的第一天
            * select trunc(sysdate, 'hh') from dual   --2013-01-06 17:00:00 当前时间为17:35
            * select trunc(sysdate, 'mi') from dual   --2013-01-06 17:35:00 TRUNC()函数没有秒的精确
        * 例子
            trunc(
                    sysdate
                    -
                    to_date(c.rsrq,'yyyymmddhh24miss')
                )
to_char(timestamp,text) 
        * to_char，函数功能，就是将数值型或者日期型转化为字符型。
        * to_char(timestamp 'now','HH12:MI:SS') // 给一个日期形，
MONTHS_BETWEEN()
        * MONTHS_BETWEEN (x, y)用于计算x和y之间有几个月。
            如果x在日历中比y早，那么MONTHS_BETWEEN()就返回一个负数。
to_date()
        * 转化为 date 格式，将传入的字符串格式，按照日期格式转化为 date 格式
        * 当省略HH、MI和SS对应的输入参数时，Oracle使用0作为DEFAULT值。
            如果输入的日期数据忽略时间部分，Oracle会将时、分、秒部分都置为0，也就是说会取整到日。
        * 同样，忽略了DD参数，Oracle会采用1作为日的默认值，也就是说会取整到月。
        * 但是，不要被这种“惯性”所迷惑，如果忽略MM参数，Oracle并不会取整到年，取整到当前月。
        * to_date('2007-06-12 10:00:00', 'yyyy-mm-dd hh24:mi:ss')
mod()
        * 求余函数，对两个数求余
        * 例子
                * MOD( TRUNC ( MONTHS_BETWEEN(to_date(b.GYQX,'yyyymmdd'),SYSDATE) )
                       -- 获得两个日期之间的月份，一个数字 截取为整数
                    ,12 ) -- 求余函数，对两个数求余
ADD_MONTHS(d,n)
        * 时间点d再加上n个月
        * 例子：
            * select sysdate, add_months(sysdate,2) aa from dual;
* 综合例子
        to_char(
                ( ADD_MONTHS( to_date(b.GYQX,'yyyymmdd'), -- 转化为 date 类型
                                TRUNC( MONTHS_BETWEEN
                                                (SYSDATE,to_date(b.GYQX,'yyyymmdd') -- 取出数据 转为字符串 
                                                ) -- 返回两个月份之间的差距   
                                     ) -- 截取为整数
                                ) -- 给取出来的日期 添加几个月
                 )
                - 
                TRUNC(SYSDATE)
                )
———————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
流程
        数据库返回的内容要通过@Result来获得别名 // 列名 ---> 别名
        别名和返回的类中的 名字相同 // 别名 ---> JSON字典中的键值对
            //　一般不会特别的处理数据
        返回的类　PrisonPeopleInfo　// 里面的数据要和数据库返回的内容进行配对

查询 
        一般使用String注入要查询的内容，然后换成数据库的列名进行查询
        如果可传入的数据过多，使用一个类来封装数据。提供GET SET方法。
        所传入的内容要是数据库中保存的数据
——————————————————
查询数据库里面
        列名和内容对应的关系表 -- 可以用搜索来构造对应表
        SELECT * FROM field_list WHERE tid=(SELECT tid FROM TABLE_list WHERE phy_name='dxxxb')


* 孙志军

除非是加字段，否则不要去动它

* 给自己写的每一个方法添上注释

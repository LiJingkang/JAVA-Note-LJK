* 注释格式
    /**
     * controller基类.
     * @author ZhangXin
     * @create 2014年8月21日 上午10:28:16
     */
——————
* 工具类
  * CheckUtil
      // 身份证和姓名关系校验
  * Constants  // 常数
      // 人员标记
      // 日期格式/人员标记/照片
  * Converters
      // 卡号，天数校验
      * 卡号补零
        Converters.supplement0(cardId)
      * 获得时间戳
        Constants.DATE_TIME
  * DateUtils
      // 日期工具
      * 日期工具使用
        new SimpleDateFormat(Constants.TIME_FORMAT).format(new Date())) < 0
  * DictUtils
      // 字典信息取得
  * PinyinUtil
      // 拼音工具类
  * SystemUtil
      // 是否是64位的jdk
  * ObjectId
      new ObjectId().toHexString()

* List Map 赋值
    // 定义为接口的map不能直接new。需要一个实现它接口的实体类来new。然后才可以对他进行调用。
    // 如果看到api要求传入的的参数是一个定义为接口的类，就是说只要满足这个接口定义的实体类参数都可以传入
    Map<Integer, String> map = new HashMap<Integer, String>() {{ 
            put( 1 , "name" ); 
             put( 2 , "sex" ); 
    }}; 

    List<String> list = new ArrayList<String>() {{ 
            add( "first" ); 
            add( "second" ); 
            add( "third" );  
    }}; 
——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
* JAVA语法

  * 输出错误
    throw new IllegalArgumentException("监狱号不能为空");
      	// throws关键字通常被应用在声明方法时，用来指定可能抛出的异常。
      	// 多个异常可以使用逗号隔开。当在主函数中调用该方法时，如果发生异常，就会将异常抛给指定异常对象。
  * 字典工具使用  
      setSexValue(DictUtils.getDictValue("性别", sex));
      setAccentValue(DictUtils.getDictValue("口音特点", accent));
  * 全局静态变量
      public static final int PRISONTYPE_PRISON = 1;  // 变量名用大写    
  * List 和 Map 使用
      Map<String, Object> result = new HashMap<String, Object>();
      List<ErrorMessage> message = new ArrayList<ErrorMessage>();
      List<Order> orders = new ArrayList<Order>();
      * 组合赋值
          private Model doSearch(Model model, String prisonId) {
              Page<ChatRecordDto> chatRecords = chatRecordService.searchDto(prisonId, pageable);
              //　获得 Page Mybatis - 分页对象
              model.addAttribute("chatRecords", chatRecords.toPageInfo());
              //  给模型添加属性  
              List<Police> polices = policeService.findSimpleAllByPrisonId(prisonId);
              //  新建  List
              model.addAttribute("polices", polices); //  给 model 添加属性
              model.addAttribute("endTime", endTime);
              return model;
          }
      * 存储键值对
          map.put("staingData", staingData); 
              // 将staingData 存入 关键字为staingData的字典里面去
          
  * Model
      类型(模型类)
        ** 例子
            @RequestMapping("/page/main")
            @AccessRequired(value = true, type = AccessTypeEnum.pageAccess)
            private String mainPage(Model model, String prisonId,
                @RequestParam(defaultValue = "terminal") String terminalType) {
              List<TerminalModule> terminalModule =
                  terminalModuleService.findByPrisonId(prisonId, terminalType);
              model.addAttribute("terminalType", terminalType);
                // 添加的时候 添加键值对
              model.addAttribute("terminalModules", terminalModule);
              return "admin/terminal_module_config";
            }
  * HashMap
      HashMap 和 HashSet 是 Java Collection Framework 的两个重要成员，
      其中 HashMap 是 Map 接口的常用实现类，
      HashSet 是 Set 接口的常用实现类。
      虽然 HashMap 和 HashSet 实现的接口规范不同，
      但它们底层的 Hash 存储机制完全一样，甚至 HashSet 本身就采用 HashMap 来实现的。
      // 系统 key-value 当成一个整体进行处理，系统总是根据 Hash 算法来计算 key-value 的存储位置，
      // 这样可以保证能快速存、取 Map 的 key-value 对 
  * 错误
      // 错误List
      List<ErrorMessage> message = new ArrayList<EooroMessage>();
      // 错误
      ErrorMessage error = new ErrorMessage();
      error.setField("inState");
      error.setMessage(prisoner.getName() + prisoner.getInStateValue());
  * BOOL类型的成员变量get set 方法
      public boolean isTongyiSearch() {
        return tongyiSearch;
      }
      public void setTongyiSearch(boolean tongyiSearch) {
        this.tongyiSearch = tongyiSearch;
      }
  * for 循环
      for (String orderId : orderIds) {}
  * for (String : List<String>)
        Java增强for循环
        Java5引入了一种主要用于数组的增强型for循环。
          Java增强for循环语法格式如下:
              for(声明语句 : 表达式)
              {
                 //代码句子
              }
      * 声明语句：声明新的局部变量，该变量的类型必须和数组元素的类型匹配。
              其作用域限定在循环语句块，其值与此时数组元素的值相等。 // 实际上就是遍历这个数组
      * 表达式：表达式是要访问的数组名，或者是返回值为数组的方法。
  * throws
      是用来声明一个方法可能抛出的所有异常信息
  * throw
      则是指抛出的一个具体的异常类型
      // 一个方法（类）的声明处通过throws声明方法（类）可能抛出的异常信息，
      // 而在方法（类）内部通过throw声明一个具体的异常信息。
      // throws通常不用显示的捕获异常，可由系统自动将所有捕获的异常信息抛给上级方法；
      // throw则需要用户自己捕获相关的异常，而后在对其进行相关包装，最后在将包装后的异常信息抛出
      ** 例子
          public Test() throws RepletException {
            try {
                System.out.println("Test this Project!")
                }
            catch (Exception e) {
                throw new Exception(e.toString());
                }
            }
  * enum 类型
      * enum Car { lamborghini,tata,audi,fiat,honda }
      * switch(c) { case lamborghini:
                      System.out.println("你选择了 lamborghini!"); }
  * final
      * final类不能被继承，没有子类，final类中的方法默认是final的。
      * final方法不能被子类的方法覆盖，但可以被继承。
      * final成员变量表示常量，只能被赋值一次，赋值后值不再改变。
      * final不能用于修饰构造方法。
      * 注意：父类的private成员方法是不能被子类方法覆盖的，因此private类型的方法默认是final类型的。
——————
* Comparable
  * 
———————————————————————————————————————————————————————————————————————————————————————————————————
* 系统类
    BindingResult result
    // getErrorMessage(result)
* 异常
    System.out.println(e)   这个方法打印出异常，并且输出在哪里出现的异常
    e.printStackTrace()     方法不同。后者也是打印出异常，但是它还将显示出更深的调用信息。
——————
* 模型类
  // 模型类中定义的静态方法，可以在外部直接进行调用
      // public static final String STATUS_RECEIVED = "received";
  Order.STATUS_DELETED
    @XStreamAlias("Order")
        public class Order {
——————
* Enum 类型
  // ExceptionMessageEnum
  ** 例子
      public enum ExceptionMessageEnum { // 定义枚举类
        INSUFFICIENT_FUNDS("INSUFFICIENT_FUNDS 余额不足"), 
        INSUFFICIENT_QUOTA("INSUFFICIENT_QUOTA 额度不足"), 
        USER_DOES_NOT_EXIST("USER_DOES_NOT_EXIST 用户不存在"), 
        EQUIPMENT_NO_AUTHORIZED("EQUIPMENT_NO_AUTHORIZED 设备未授权"), 
        USERNAME_PASSWORD_DO_NOT_MATCH("USERNAME_PASSWORD_DO_NOT_MATCH 用户名密码不匹配"), 
        CREATE_TASK_FAIL("CREATE_TASK_FAIL 创建任务失败");
        String name; // 设置变量
        ExceptionMessageEnum(String name) { // 设置一个和Enum类同名的方法，来设置变量的值
          this.name = name;
        }
        public String getName() { // get方法
          return name;
        }
        public void setName(String name) { // set方法
          this.name = name;
        }
      }
* Enum（枚举）构造函数及方法的使用     
  ** enum Car {
         lamborghini(900),tata(2),audi(50),fiat(15),honda(12); // 定义枚举类型 
         private int price; // 定义私有变量
         Car(int p) { // 设置一个和 Enum 同名的方法，来设置私有变量的值。 
              price = p; }
         int getPrice() { // 返回 自己定义的私有变量
              return price; } }
      * 使用
——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
** SQL 拼接
* 拼接SQL语句
  * #{prisonerName}  // 这种写法中 prisonerName 必须由外面的@Param(prisonerName)注解
  * OR() // 拼接规则
  * 使用 Map<String, Object> map,的时候需要手动拼接SQL字符串
          WHERE("(ry.xm like '%#{prisonerName}%' or ry.bm like '%#{prisonerName}%')");
* #{}
  * 可以在""内对类参数进行取值  #{dto.goods.code}  
  * 例子
      VALUES("GOODS_CODE", "#{dto.goods.code,jdbcType=VARCHAR}");
* 拼接数组
  * SELECT * FROM blog WHERE id=ANY(#{blogIds}::int[]) // 再测
    @Select("SELECT * FROM TOUR_POINT 
            WHERE PRISON_ID CARD_ID=ANY // ANY 使用 后面必须接子查询 // 会默认遍历这个数组
                (
                  SELECT TOUR_POINT_CARD_ID 
                  FROM TOUR_ROUTE_POINT 
                  WHERE TOUR_ROUTE_ID=#{tourRouteId})") // #{tourRouteId} 查询结果是一个额数组
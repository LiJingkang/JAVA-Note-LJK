* Controller 
    @Controller
    @RequestMapping

        @Autowired

        @InitBinder
            // bean中定义了Date，double等类型，如果没有做任何处理的话，日期以及double都无法绑定。

        @RequiresPermissions("JC_JAIL_DOCTOR_DEDRUGDRUGBASICIN:view")
            // 要求subject中必须同时含有 file:read 和 write:aFile.txt 的权限
            // 才能执行方法someMethod()。否则抛出异常AuthorizationException。
        @RequestMapping(value = "list", method = RequestMethod.GET)
            // Get 请求，传送一个 list 过来

        @RequiresPermissions("JC_JAIL_DOCTOR_DEDRUGDRUGBASICIN:view")
        @RequestMapping(value = "queryByPaging", method = RequestMethod.POST)
        @ResponseBody
——————————————————————————————————————————————————————————————————————————————————————————————————————
* Service  // 外面的部分是 一个接口 
            // 接口的实现在 impl 里面
    Service 层里面只有 Controller 层里面调用的方法名 
    * Hibarnate 会在 impl 里面提供基础的增删查该方法。只需要在这里写方法名就可以实现。
        不需要在 impl 里面去写对应的方法
    * 对于自己要实现的一些操作和功能 在这里写方法的接口，在impl 里面实现

* Service 里面的 impl
    @Service("dedrugDrugBasicInfoService")
    @Transactional
            // 提供一种控制事务管理的快捷手段,声明这个service所有方法需要事务管理。
            // 每一个业务方法开始时都会打开一个事务。 

    @Autowired

    @Override
        // 来实现接口的方法

——————————————————————————————————————————————————————————————————————————————————————————————————————
* DAO
    一般是没有代码！
    * 会自动生成 delete() 
                 save() 
                 findById() 
      这些东西
    * 自动生成
        List<PrsRoomNumber> findByPrsIdAndStatus(String prsId, String status);
        // 会自动生成查询代码
    * 根据文件名来生成查询代码
        // 见另一个文档

    * 自定义代码
            @Query("FROM DailyShift shift WHERE shift.prsId = ?1 AND shift.dormCode = ?2  "
                + "and shift.shiftDate >= ?3 and shift.shiftDate <= ?4 order by createTime")
            List<DailyShift> queryByWeek(String prisonId, String dormCode, Date startTime, Date endTime);



    ReportSummaryDAO
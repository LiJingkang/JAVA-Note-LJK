* 代码规则(强迫症规则)
	* 综合查询用 "search"
	* 变量名无论如何 首字母一定小写
	* 返回的实体类尽量 写一个
	* 修改了输出字段以后，要修改映射
	* "search" 是综合查询使用的
	* 如果只有一个查询条件，那么我们使用 "findXXXXXXByXXXXX"
	* 不论查询条件如何，都添加 "prisonId" 这个字段进行区分，并且在返回值里
	* 在查询人员通过 "number" 进行查询的时候，返回值要包含 "number" 去确认时候是所查询的人员
	* 不论查询条件如何，都要将查询条件进行返回，给查询人员进行确认。
	* 起别名的时候，如果使用 "a" "b" "c" 的话使用大写来区分。
	* 时间，一般我们都是用 "xxStartTime" , "xxEndDate"
	* 别名尽量和别人统一
	* "number" 只代表人员编号
    
* 代码规范 
	* List<GoodsQuantityDto> dtos = new ArrayList<>(); // 没有添加 ArrayList 的类型
			List<GoodsQuantityDto> dtos = new ArrayList<GoodsQuantityDto>(); 
	* String identityId = meetFamilyQo.getIdentityID(); // ID 大写
	* 使用危险代码之前先进行非空判断
        if (chatRecord.getChatRequestTime() == null) {
          chatRecord.setChatRequestTime(new Date());
        }
        if (chatRecord.getChatStartTime() == null) {
          chatRecord.setChatStartTime(new Date());
        }
    * final PageHelper pageHelper = new PageHelper();
    * @Select("select distinct id from TOUR_SWIPE_RECORD "
      	+ "where TOUR_RECORD_ID=#{tourRecordId} and TOUR_POINT_ID=#{tourPointId}")
    	// 将手动拼写的 SQL 语句  select 和 where 部分分开
    * SELECT(
        "jbxxb.RYBH,jbxxb.FH,jbxxb.JSH,jbxxb.SSJD,jbxxb.GYQX,jbxxb.BADW,jbxxb.BADWLX,jbxxb.XQ,"
            + "jbxxb.CLJG,jbxxb.WXDJ, bdxxb.AJLB as ajlb,bdxxb.RSRQ,bdxxb.RSYY, ryxxb.XM,ryxxb.BM,ryxxb.XB,"
            + "ryxxb.CSRQ,ryxxb.HYZK,ryxxb.MZ,ryxxb.GJ,ryxxb.WHCD,ryxxb.HJSZD,ryxxb.HJDXZ,ryxxb.ZY,ryxxb.GZDW");
    	// 如果每张表查询的内容都较多，最好把每张表查询的内容分开 用加好
    	// 别名小写 列名大写 
    * 新建的相关变量和中间量， 跟着使用他的代码块
    * final String url = "/evaluation/summary";
    	// 静态变量记得加 final
    * 父子节点的例子
    	// 见 JsonTest
    	// 父节点用  fathers  子节点用 children
    *   /**
	   * 按照人员编号查找信件信息. // 类名的注释要准确有效
	   * */
    * @RequestMapping(value = "/findLetterByNumber")	
    	// 类似于在 Letter 里面的 search findByXXX, 具体的方法名前面不再重复添加 Letter 
    * 统一每一张表的 别名
    	* bdxxb  jbxxb  ryxxb 不同的表，所查询的不同的内容，用 + 分开
    * List<GoodsQuantityDto> validatedDtos = new ArrayList<GoodsQuantityDto>();
	  List<GoodsQuantityDto> validatedDtos = new ArrayList<>();  	


* 注释规范
	* @throws Exception 异常
	*   /**
	   * 按照监所编号查找启用的设备.   // 在第一行注释之后有句点
	   * 
	   * @param map 查询参数
	   * @return sql
	   */
	*   /**
	   * 获取任意查询方法的count总数.
	   *
	   * @param select select  // 不要因为相同 就删除了 注解后面的部分  含义会有不同
	   */
	*    /**
	   * .  // 无内容 也在标题这里加一个 .
	   * 
	   * @param resultMaps resultMaps
	   */

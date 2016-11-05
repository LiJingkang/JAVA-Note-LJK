* 不认识的写法
    *  @Result(property = "details",javaType = List.class, column = "ZYBH", many = @Many(select = "getDetailById"))})

————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
* Like 使用
    * oracle 的简直是瞎写，那样写根本就是错的 。CONCAT('%',CONCAT(#{name},'%'）)  
    * oracle 成功的例子  
        @Select("SELECT DIC_NAME, CODE, ITEM1 FROM DIC_ITEMS WHERE DIC_NAME= #{dicName} AND ITEM1 LIKE CONCAT('%',CONCAT(#{item},'%'))")
            <where>  
                <if test="bookName!=null">  
                    bookName like CONCAT('%','${bookName}','%' )  
                </if>  
                <if test="author!=null">  
                    and author like CONCAT('%','${author}','%' )  
                </if>  
* in 查询
    * 好像没有找到太好的 SQL 构造方法，那么只能由我们来拼 SQL 语句了
    * 一个例子
        * 传入
            String[] workId = (String[]) map.get("workId");
        * 拼接字符串
            String workIds = "";
            for (int i = 0; i < workId.length; i++) {
              workIds = workIds + "'" + workId[i] + "',";
            }
            workIds = workIds.substring(0, workIds.length() - 1);
        * 加入查询条件
            WHERE("zybh in (" + workIds + ")");
    * 另一个例子
        * 传入
            String[] answers = (String[]) map.get("answers");
        * 拼接
            // 太复杂，再看
            buildMultipleInsert(String prisonId, String number, String typeId, Date createTime,
    * 一个 ArrayList 的拼接例子
        * 传入
            List<String> dormCodes = (List<String>) map.get("dormCodes");
        * 拼接字符串
            if (dormCodes != null && dormCodes.size() > 0) {
              StringBuilder codes = new StringBuilder();
              codes.append("ryxx.JSH in (");
              for (String code : dormCodes) {
                codes.append("'").append(code).append("',");
              }
              codes.deleteCharAt(codes.length() - 1).append(")");
              WHERE(codes.toString());
            }    
    * 自己写的另一个户籍查询的例子
        * 通过字典获得户籍代码 List 
            // 通过传入的houseAddr 获得对应的 户籍代码
            List<Dict> dicts = dictService.getCodeByItem("行政区划", houseAddr);
            List<String> houseAddrCodes = new ArrayList<String>();
            for (Dict dict : dicts) {
              houseAddrCodes.add(dict.getCode());
            }
        * 传入参数
            @SuppressWarnings("unchecked")
            List<String> houseAddrCodes = (List<String>) map.get("houseAddrCodes");
        * 拼接字符串
            // 通过传入的户籍代码 List 来拼接 in 查询的字符串
            if (houseAddrCodes != null && houseAddrCodes.size() > 0) {
              StringBuilder codes = new StringBuilder();
              codes.append("ryxxb.HJSZD in (");
              for (String code : houseAddrCodes) {
                codes.append("'").append(code).append("',");
              }
              codes.deleteCharAt(codes.length() - 1).append(")");
              WHERE(codes.toString());
            }    
* DAO 层传参数 的写法
    * 原来
            public String searchSql(Map<String, Object> map) {
                String policeId = (String) map.get("policeId");
                String type = (String) map.get("type");
                String dormCode = (String) map.get("dormCode");
                String deviceType = (String) map.get("deviceType");
                Date startTime = (Date) map.get("startTime");
                Date endTime = (Date) map.get("endTime");
    * 现在
            public String searchSql(@Param("policeId") String policeId, @Param("type") String type,
                @Param("dormCode") String dormCode, @Param("deviceType") String deviceType,
                @Param("startTime") Date startTime, @Param("endTime") Date endTime) {        
————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
* insert
    * Date 类型 传入 数据库 String 类型
        SET("SQRQ='" + DateFormatUtils.format(outsideMedicineRecord.getApplicationDate(),
        Constants.DATE1_FORMAT) + "'");

* boolen 类型 传入 数据库 "1" "0"
        if (outsideMedicineRecord.getIsInHospital()) {
          SET("SFZY = '1'");
        } else {
          SET("SFZY = '0'");
        }

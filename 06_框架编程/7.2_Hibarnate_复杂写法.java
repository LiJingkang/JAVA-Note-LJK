* Hibernate 
    * 返回对应数据库一张表的实体类的里面的某一部分。
        * HQL 写法  
        @Query("SELECT NEW ReportSummary(r.rCode, r.rpName, r.docStyle) FROM ReportSummary r " +
                "WHERE exists(select 1 from ReportModule m where m.rCode = r.rCode and m.module.sn = ?1) " +
                "and (not exists(select 1 from ReportOrganization o where o.rCode = r.rCode) " +
                "or exists(select 1 from ReportOrganization o where o.rCode = r.rCode and o.organization.jsbh = ?2)) order by r.rCode")
        List<ReportSummary> findByModuleSn(String module_sn, String prsId);
    
    * 在实体类里面写一个构造方法
          public ReportSummary() {}
          public ReportSummary(String rCode, String rpName, String docStyle) {
            this.rCode = rCode;
            this.rpName = rpName;
            this.docStyle = docStyle;
          }
* 页面跳转
      @RequestMapping(value = "addconPage/{personId}", method = RequestMethod.GET)
      public String addConPageForMessage(@PathVariable("personId") String personId,
          Map<String, Object> map) {
        map.put("personId", personId);
            // Map 字典用来放返回给 页面地址 的时候传送的内容 也不知道怎么传的
        return ADDCONVERPAGE;
      }
* in 查询
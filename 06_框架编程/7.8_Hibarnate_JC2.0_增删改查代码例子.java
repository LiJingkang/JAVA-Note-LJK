* 跳转页面
        /**
        * 跳转到查看人员详情页面
        *
        * @return
        */
        @RequiresPermissions("JC_DEDRUG_DISCIPLINE_SOCIALRELATIONS:view")
        // 权限控制
        @RequestMapping(value = "{personId}/{stringData}/psnBaseInfoPage", method = RequestMethod.GET)
        // 设置进入的url 和参数绑定。 方法为get 方法，放在url里面
        public String psnBaseInfoPage(@PathVariable("personId") String personId,
                                    // 设置映射关系  @PathVariable
          @PathVariable("stringData") String stringData, Map<String, Object> map) {
        map.put("personId", personId);
          // 设置返回的personId
        String backUrl = "/management/dedrug/Discipline/socialRelations/" + stringData + "/backlist";
          // 获得返回的url
        map.put("backUrl", backUrl);   // put 设置map的内容
        return INFOPAGE;   // 设置好的常量
        }
——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
* 综合查询
——————————————
    * controller 
        /**
         * 分页查询socialRelations列表
         * 
         * @return
         */
        @Controller
        @RequestMapping("/management/dedrug/Discipline/socialRelations")
        public class SocialRelationsController {

          @RequiresPermissions("JC_DEDRUG_DISCIPLINE_SOCIALRELATIONS:view") 
          @RequestMapping(value = "queryByPaging", method = RequestMethod.POST)
            // 设置请求的url  使用 post 请求 请求参数放在请求体里面
          @ResponseBody
          public DTData<SocialRelations> queryByPaging(ServletRequest request, DTPager pager) 
            // 自定义的DTData   实体类SocialRelations  实体类和数据库的表有映射关系
            // 自定义的DTPager分页参数   request 请求体   pager 分页标记
          {
            Specification<SocialRelations> specification =
            // Specification 大概是一个List  存放的是SocialRelations实体类
            // 获得一个specification 在查询的时候要传入进行复杂查询 继承了
                DynamicSpecifications.bySearchFilter(request, SocialRelations.class);
                // 通过公共的DynamicSpecifications的bySearchFilter。搜索拦截器方法
                // 找到specification JpaSpecificationExecutor
            return socialRelationsService.queryByPaging(specification, pager);
                // 通过服务层的 queryByPaging 来查询。返回一组数据
          }
        }
——————————————
     * service
        public interface SocialRelationsService {
          Response addSocialRelations(SocialRelations socialRelations);
            // 添加 传入的是一个实体类
          Response updateSocialRelations(SocialRelations socialRelations);
            // 更新 传入的也是一个实体类
          DTData<SocialRelations> queryByPaging(Specification<SocialRelations> specification,
                DTPager pager);
            // 传入 Specification pager 来查询
——————————————
    * serviceImpl/SocialRelationsServiceImpl  
        // 添加了Impl 后缀名。和service 进行关联
        @Service("socialRelationsService")
        @Transactional
        public class SocialRelationsServiceImpl implements SocialRelationsService {
        // Impl 直接继承 对应的Service 来实现它里面的方法
        @Override
        public DTData<SocialRelations> queryByPaging(Specification<SocialRelations> specification,
            DTPager pager) 
          // 传入 specification pager 
        {
          Page<SocialRelations> socialRelationsPage =
            // 返回 含有表单的SocialRelations 
              socialRelationsDAO.findAll(specification, tempAuxiliaryUtils.buildPageRequest(pager));
                // 调用对应DAO层，查找到全部数据，返回一个分页对象
                // 传入 specification 进行查询  通过工具类获得pager
          return new DTData<SocialRelations>(socialRelationsPage, pager);
            // 通过 DTData 的方法，返回对应的分页数据 
        }
——————————————
  * DAO
        public interface SocialRelationsDAO
          extends JpaRepository<SocialRelations, String>, JpaSpecificationExecutor<SocialRelations> {
            // 为什么什么都没有
            // 应该是使用JpaRepository 里面的方法进行查询，这样就不需要自己去写sql语句   
        }
——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
* 单条件查询
——————————————
    * controller 
        @RequiresPermissions("JC_DEDRUG_DISCIPLINE_SOCIALRELATIONS:view")
        @RequestMapping(value = "{id}/view", method = RequestMethod.GET)
        @ResponseBody
        public Response queryById(@PathVariable("id") String id) {
            return socialRelationsService.queryById(id);
        }
    * service
        Response queryById(String id);
    * serviceImpl
        @Override
        public SocialRelations findById(String id) {
            return socialRelationsDAO.findOne(id);
            // findOne 是框架提供的一个查询方法。在DAO里面不需要再写代码
        }
——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
* 更新表
    * controller 
        @Log(message = "修改了id={0}的关系人的信息。")  
        @RequiresPermissions("JC_DEDRUG_DISCIPLINE_SOCIALRELATIONS:edit")
        @RequestMapping(value = "{id}/update", method = RequestMethod.POST)
        @ResponseBody
        public Response update(SocialRelations socialRelations) {
        DedrugBasicInfo detentionPsnBasicInfo =
            detentionPsnBasicInfoService.findById(socialRelations.getDetentionPsnBasicInfo().getId());
            // basicInfo 基本信息表 通过传入的socialRelations获得id 查找到基本信息表
        socialRelations.setDetentionPsnBasicInfo(detentionPsnBasicInfo);
            // 将获取的 basicInfo 写入 socialRelations
            LogUitls
                .putArgs(LogMessageObject.newWrite().setObjects(new Object[] {socialRelations.getId()}));
                // 工具类  将LogMessageObject放入LOG_ARGUMENTS。 描述
        return socialRelationsService.updateSocialRelations(socialRelations);
            // 更新表 然后返回 一个 Response 
        }
    * service 
        Response updateSocialRelations(SocialRelations socialRelations);
    * serviceimpl
        @Override
        public Response updateSocialRelations(SocialRelations socialRelations) {
            Assert.notNull(socialRelations);
            // 不为空
            Assert.notNull(socialRelations.getId());
            // id不为空
            socialRelationsDAO.save(socialRelations);
            // 保存
            // 传入一个和表关联的实体类，调用jpa提供的save方法，可以在不写sql语句的条件下。完成和数据库的关联
        return new SuccessResponse();
        }
——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————





  

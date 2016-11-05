* List在查询中使用
  MeetingDynaSQL

——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
* 分页拦截器
  * 使用import com.github.pagehelper.Page; // 使用这个分页
  * 查询时使用方法中的属性
    * page = 1
      // 不使用springFramework
——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
* find
——————  
* 例子1
  * controller
      @RequestMapping(value = "/findLeavePrison")
      @ResponseBody
      public List<PrisonPeopleInfo> findLeavePrison(LeavePrisonerQo qo) {
    	  if (StringUtils.isBlank(qo.getPrisonId())) {
    		  throw new IllegalArgumentException("监狱号不能为空");
    	  }
    	  return leavePrisonService.findLeavePrisoner(qo);
      }
——————
  * service
      public List<PrisonPeopleInfo> findLeavePrisoner(LeavePrisonerQo leavePrisonerQo) {
  	  return leavePrisonRepository.findLeavePrisoner(leavePrisonerQo);
      }
——————
  * prisoner
      @Results({@Result(column = "ZYBH", property = "workId"),
          @Result(column = "RSYY", property = "comeReason"),
          @Result(column = "RSRQ", property = "comeDate"), 
          @Result(column = "ZXF", property = "felon"),
          @Result(column = "RYBH", property = "number")})
      @SelectProvider(type = LeavePrisonDynaSqlProvider.class, method = "findLeavePrisonerSql")
      public List<PrisonPeopleInfo> findLeavePrisoner(@Param("lpQo") LeavePrisonerQo leavePrisonerQo);
——————
  * DynaSqlProvider
      public String findLeavePrisonerSql(Map<String, Object> map) {
          LeavePrisonerQo qo = (LeavePrisonerQo) map.get("lpQo");
          String prisonId = qo.getPrisonId();

          return new SQL() {
            {
              SELECT("A.Zybh,A.RSYY,A.RSRQ,A.ZXF,B.JSH,B.FH,B.Gyqx,B.Ssrq,B.Ssjd,B.CLJG,B.PJZM,B.SNZT,"
                  + "C.XM,C.Xb,C.Csrq,C.BMPY,C.PYZT,c.SFHCBZ,B.RYBH");
              FROM("kssrybdxxb A");
              LEFT_OUTER_JOIN("kssryjbxxb B on A.Rybh = B.Rybh");
              LEFT_OUTER_JOIN("ryjbxxb C on B.Jbxxbh = C.Jbxxbh");
              WHERE("A.Bdrybj = '0'");
              WHERE("A.CSLCZT is null");
              WHERE("B.rybj = '0'");
              WHERE("B.jsbh ='" + prisonId + "'");
              if (StringUtils.isNoneBlank(qo.getName())) {
                WHERE("(c.xm like '%" + qo.getName() + "%' or c.bm like '%" + qo.getName() + "%')");
              }
              if (StringUtils.isNoneBlank(qo.getComeEndDate())) {
                WHERE("a.rsrq <= '" + qo.getComeEndDate() + "595959'");
              }
              if (StringUtils.isNoneBlank(qo.getResult())) {
                WHERE("b.cljg='" + qo.getResult() + "'");
              }
              if (qo.isTodayLeave()) {
                WHERE("b.GYQX = ( Select to_char(SysDate,'yyyyMMdd') from dual) ");
                WHERE("a.RYBH in (Select L.RYBH from KSSLSXXB L left join KSSRYJBXXB K on L.RYBH=K.RYBH"
                    + " where K.RYBJ='0' and K.JSBH='" + prisonId + "')");
              }
            }
          }.toString();
        }
——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
* 注意 
	拼接 OR 语句的时候要添加括号
——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
* 例子2
  * controller
      @RequestMapping("/compositiveQuery")
      @Controller
      public class CompositiveQueryController extends BaseController {

        @Autowired
        private CompositiveQueryService compositiveQueryService;

        @RequestMapping(value = "/findPrisoner")
        @ResponseBody
        public Page<PrisonPeopleInfo> findPrisoner(PrisonerQo qo, Pageable pageable) {
          if (StringUtils.isBlank(qo.getPrisonId())) {
            throw new IllegalArgumentException("监所号不能为空");
          }
          return compositiveQueryService.findPrisoner(qo, pageable);
        }
      }
——————
  * service 
      @Service
      @Validated
      public class CompositiveQueryService {

        @Autowired
        private CompositiveQueryRepository compositiveQueryRepository;

        public Page<PrisonPeopleInfo> findPrisoner(PrisonerQo pqDto, Pageable pageable) {
          return compositiveQueryRepository.findPrisoner(pqDto, pageable);
        }
      }
——————
  * provider
      @Transactional("criminalTransactionManager")
      public interface CompositiveQueryRepository {

        @SelectProvider(type = CompositiveQueryDynaSqlProvider.class, method = "findPrisonerSql")
        @Results(id = "prisonPeopleInfoMap",
            value = {@Result(column = "RYBH", property = "number"),
                @Result(column = "FH", property = "unifoNo"),
                @Result(column = "JSH", property = "dormCode"),
                @Result(column = "SFHCBZ", property = "identityMark"),
                @Result(column = "GJMJ", property = "disciplinePolice"),
                @Result(column = "numOfDays", property = "numOfDays"),
                @Result(column = "leftSentence", property = "leftSentence")})
        public Page<PrisonPeopleInfo> findPrisoner(@Param("prisonerQo") PrisonerQo prisonerQo,
            Pageable pageable);
      }
——————
  * DynaSqlProvider // BaseDynaSqlProvider
      public class CompositiveQueryDynaSqlProvider extends BaseDynaSqlProvider {
        public String findPrisonerSql(Map<String, Object> map) {
          PrisonerQo prisonerQo = (PrisonerQo) map.get("prisonerQo");
          String mark = prisonerQo.getMark();
          String prisonId = prisonerQo.getPrisonId();

          return new SQL() {
            {
              SELECT("b.RYBH,b.FH,b.JSH,b.SSJD,b.DAH,b.BADW,b.BADWLX,b.XQ,b.xqksrq,b.XQJZRQ,b.CLJG,"
                  + "case when c.csrq is not null "
                  + "then trunc(to_date(c.csrq,'yyyymmddhh24miss')-to_date(c.rsrq,'yyyymmddhh24miss')) "
                  + "else trunc(sysdate-to_date(c.rsrq,'yyyymmddhh24miss')) end  as numOfDays,"
                  + "case when b.XQJZRQ is null then ''"
                  + "else (TRUNC(MONTHS_BETWEEN(to_date(b.GYQX,'yyyymmdd'),SYSDATE)/12)|| '年' ||"
                  + " (MOD(TRUNC(MONTHS_BETWEEN(to_date(b.GYQX,'yyyymmdd'),SYSDATE)),12))|| '月' ||"
                  + "to_char((ADD_MONTHS(to_date(b.GYQX,'yyyymmdd'),"
                  + "TRUNC(MONTHS_BETWEEN(SYSDATE,to_date(b.GYQX,'yyyymmdd')))))"
                  + "-TRUNC(SYSDATE))|| '天') end as leftSentence ");
              FROM("KSSRYJBXXB b");
              LEFT_OUTER_JOIN("KSSRYBDXXB c on b.rybh=c.rybh");
              LEFT_OUTER_JOIN("RYJBXXB d on b.JBXXBH=d.JBXXBH");
              if (StringUtils.isNoneBlank(prisonId)) {
                LEFT_OUTER_JOIN("jshb e on e.jsbh='" + prisonId + "' and b.jsh=e.dm");
                WHERE("b.JSBH='" + prisonId + "'");
              } else {
                LEFT_OUTER_JOIN("jshb e on b.jsh=e.dm");
              }
              if (StringUtils.isNoneBlank(mark) && mark.equals(PrisonerQo.MARK_IN)) {
                WHERE("((c.BDRYBJ='0' and b.RYBJ='0') or (c.BDRYBJ='A' and b.RYBJ='A'))");
              } else if (StringUtils.isNoneBlank(mark) && mark.equals(PrisonerQo.MARK_HISTORY)) {
                WHERE("((c.BDRYBJ='1') or (c.BDRYBJ='A' and b.RYBJ='A'))");
              } else if (StringUtils.isNoneBlank(mark) && mark.equals(PrisonerQo.MARK_ALL)) {
                WHERE("((c.BDRYBJ=0 or c.BDRYBJ=1 ) and (b.RYBJ=0 or b.RYBJ=1))");
              }

              if (StringUtils.isNoneBlank(prisonerQo.getName())) {
                WHERE("(d.xm LIKE '%" + prisonerQo.getName() + "%' OR d.bm LIKE '%" + prisonerQo.getName()
                    + "%')");
              }

              if (StringUtils.isNoneBlank(prisonerQo.getComeReason())) {
                WHERE("c.rsyy = '" + prisonerQo.getComeReason() + "'");
              }

              if (StringUtils.isNoneBlank(prisonerQo.getOutReason())) {
                WHERE("c.csyy = '" + prisonerQo.getOutReason() + "'");
              }

              if (StringUtils.isNoneBlank(prisonerQo.getOutEndDate())) {
                WHERE("c.csrq <= '" + prisonerQo.getOutEndDate() + "235959'");
              }

              if (StringUtils.isNoneBlank(prisonerQo.getBirthEndDate())) {
                WHERE("d.csrq <= '" + prisonerQo.getBirthEndDate() + "235959'");
              }

              if (prisonerQo.isCheck()) {
                WHERE("(sfhcbz IS NULL OR sfhcbz = '0')");
              }

              if (StringUtils.isNoneBlank(mark) && mark.equals(PrisonerQo.MARK_IN)) {
                ORDER_BY("c.rsrq");
              }
            }
          }.toString();
        }
——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
** insert
* 例子1
    @RequestMapping(value = "/saveInterimMeetFamilyApply")
    @ResponseBody
    public List<ErrorMessage> saveInterimMeetFamilyApply(@Valid InterimMeetFamily interimMeetFamily,
        BindingResult result, String prisonId, String loginName) { //　如果使用 class 来封装插入的数据
      // 返回值是一个ErrorMessage 用来返回错误信息
      // Valid 验证 InterimMeetFamily 内部的@inNotBlank @Length

      // 验证 error 
      List<ErrorMessage> error = checkInterimMeetFamilyApply(getErrorMessage(result),
          interimMeetFamily, prisonId, loginName);
      if (error.size() > 0) {
        return error;
      }
      // 创建 prisoner
      PrisonPeopleShortInfo prisoner =
          businessFlowService.getPrisonPeopleShortInfo(interimMeetFamily.getNumber());
      interimMeetFamily.setDormCode(prisoner.getDormCode());
      interimMeetFamilyService.saveInterimMeetFamilyApply(interimMeetFamily);
      return null; // 插入成功
    }
——————
    public void saveInterimMeetFamilyApply(InterimMeetFamily interimMeetFamily) {
      interimMeetFamilyRepository.insertInterimMeetFamilyApply(interimMeetFamily);
      // 传入 class 调用Repository接口来添加数据
    }
——————
    @InsertProvider(type = InterimMeetFamilyDynaSqlProvider.class,
        method = "insertInterimMeetFamilyApplySql")
    public void insertInterimMeetFamilyApply(
        @Param("interimMeetFamily") InterimMeetFamily interimMeetFamily);
——————
    public String insertInterimMeetFamilyApplySql(Map<String, Object> map) {
        InterimMeetFamily interimMeetFamily = (InterimMeetFamily) map.get("interimMeetFamily");
        String workId = new ObjectId().toNumberString(interimMeetFamily.getNumber());
        return new SQL() {
          {
            INSERT_INTO("JDLSTFTXB");
            VALUES("ZYBH", "'" + workId + "'");
            VALUES("RYBH", "#{interimMeetFamily.number,jdbcType=VARCHAR}");　// #{} ,{} 要包裹整个反射的数据别名　
            VALUES("SQMJ", "#{interimMeetFamily.applyPolice,jdbcType=VARCHAR}");
            VALUES("ZT", "'0'");
            VALUES("DYRZJHM", "#{interimMeetFamily.firstIdentityId,jdbcType=VARCHAR}");
            VALUES("SJC", Constants.DATE_TIME);
          }
        }.toString();
      }
——————
* 例子2
    @RequestMapping("/add")
    @ResponseBody
    public String add(Task task) {
      try {
        taskService.insert(task);
      } catch (Exception ex) {
        ex.printStackTrace();
        //
        // java抛出异常的方法有很多，其中最常用的两个：
        // System.out.println(e)，这个方法打印出异常，并且输出在哪里出现的异常
        // e.printStackTrace()方法不同。后者也是打印出异常，但是它还将显示出更深的调用信息。
        return "fail";
      }
      return "sucess";
    }
——————
    public void insert(Task task) throws Exception {
      task.setId(new ObjectId().toHexString());
      task.setCreateTime(new Date());

      String taskId = scheduleService.addTask(task);
      if (StringUtils.isBlank(taskId)) {
        throw new Exception(ExceptionMessageEnum.CREATE_TASK_FAIL.getName());
      }
      task.setTaskId(taskId);
      taskRepository.insert(task);
    }
——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
** update
——————
  * controller
      @RequestMapping(value = "/updateArraign")
      @ResponseBody
      public List<ErrorMessage> updateArraign(@Valid Arraign arraign, BindingResult result) {
        if (StringUtils.isBlank(arraign.getWorkId())) {
          throw new IllegalArgumentException("作业编号不能为空");
        }
        List<ErrorMessage> error = checkArraign(getErrorMessage(result), arraign);
        if (error.size() > 0) {
          return error;
        }
        arraignService.updateArraign(arraign);
        return null;
      }
——————
  * service
      public void updateArraign(Arraign arraign) {
        arraignRepository.updateArraign(arraign);
      }
——————
  * provider 
      @UpdateProvider(type = ArraignDynaSqlProvider.class, method = "updateArraignSql")
      public void updateArraign(@Param("arraign") Arraign arraign);
——————
  * DynaSqlProvider // BaseDynaSqlProvider
      public String updateArraignSql() {
        return new SQL() {
          {
            UPDATE("KSSTXXXB");
            SET("QTZDQK = #{arraign.otherImport,jdbcType=VARCHAR}");
            WHERE("ZYBH=#{arraign.workId}");
          }
        }.toString();
      }
————————————
      public String updateLetterSql() {
          return new SQL() {
            {
              UPDATE("KSSXJSFDJB");
              SET("SFSJ=#{letter.date,jdbcType=VARCHAR}");
            }
          }.toString();
        }
——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
——————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
** delete
    @RequestMapping(value = "/deleteMeetFamily")
    @ResponseBody
    public void deleteMeetFamily(@RequestParam(required = true) String workId) {
      meetFamilyService.deleteMeetFamily(workId);
    }
——————
    public void deleteMeetFamily(@NotBlank(message = "workId is null") String workId) {
      meetFamilyRepository.deleteMeetFamily(workId);
    }
——————
    @Delete("Delete  from KSSJZHJB where zybh=#{workId}")
    public void deleteMeetFamily(@Param("workId") String workId);
——————
* 拼接delete SQL
  * 例子1
    return new SQL() {
      {
        DELETE_FROM("KSSZPB");
        for (int i = 0; i < workId.length; i++) {
          WHERE("ZYBH ='" + workId[i] + "'");
          if (i != workId.length - 1) {
            OR();
          }
        }
      }
    }.toString();
——————
  * 例子2
        return new SQL() {
      {
        DELETE_FROM("TOUR_POINT tp");
        WHERE("tp.prison_id = #{prisonId}");
        if (StringUtils.isNotBlank(pointCode)) {
          WHERE("tp.point_code = #{pointCode}");
        }
        if (StringUtils.isNotBlank(cardId)) {
          WHERE("tp.card_id = #{cardId}");
        }
        if (StringUtils.isNotBlank(pointName)) {
          WHERE("tp.point_name = #{pointName}");
        }
        if (StringUtils.isNotBlank(id)) {
          WHERE("tp.id = #{id}");
        }     
      }
    }.toString();

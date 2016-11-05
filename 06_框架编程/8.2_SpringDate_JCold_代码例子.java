添加时间戳

  // 时间戳 SJC
  private Date timeStamp;

  // 查询代码
      if (startTime != null) {
      String startTimeStr = new SimpleDateFormat("yyyyMMddHHmmss").format(startTime);
      sb.append("and SJC >= '").append(startTimeStr).append("' ");
    }
    if (endTime != null) {
      String endTimeStr = new SimpleDateFormat("yyyyMMddHHmmss").format(endTime);
      sb.append("and SJC <= '").append(endTimeStr).append("' ");
    }

  // 返回代码
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

      String timeStampStr = (String) map.get("SJC");
      try {
        Date time = dateFormat.parse(timeStampStr);
        breakRule.setTimeStamp(time);
      } catch (Exception e) {
        e.printStackTrace();
      }
————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
http://localhost:8080/jc/policeShift/findToday?prisonId=330211111
        * 成功

    /policeShift/findToday?prisonId=330211111

————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    @Controller
    @RequestMapping("/policeShift")
    public class PoliceShiftController {

      @Autowired
      private PoliceShiftService policeShiftService;

      @RequestMapping("/findToday")
      @ResponseBody
      public PoliceShift findToday(@RequestParam(required = true) String prisonId) {
        return policeShiftService.findToday(prisonId);
      }
    }
————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    @Service
    public class PoliceShiftService {

      @Autowired
      private PoliceShiftRepository repository;

      public PoliceShift findToday(String prisonId) {
          if (StringUtils.isBlank(prisonId)) {
          return null;
        }
        // SELECT * FROM zbapb WHERE SUBSTR(zbkssj,1,8)='20110520'
        return repository.findByDate(prisonId, new Date());
      }
    }
————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    public interface PoliceShiftRepositoryCustom {
      public PoliceShift findByDate(String prisonId, Date date);
    }
————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    public interface PoliceShiftRepository extends PoliceShiftRepositoryCustom {
    }
————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
    import org.slf4j.Logger;
    @Repository
    public class PoliceShiftRepositoryImpl implements PoliceShiftRepository {

      final Logger logger = LoggerFactory.getLogger(PoliceShiftRepositoryImpl.class);

      @Autowired
      @Qualifier("criminalJdbcTemplate")
      private JdbcTemplate template;

      @Override
      public PoliceShift findByDate(String prisonId, Date date) {
        String dateStr = new SimpleDateFormat("yyyyMMdd").format(date);
        String sql = "SELECT  DBLD,ZBMJ FROM zbapb WHERE JSBH='" + prisonId
            + "' and SUBSTR(zbkssj,1,8)='" + dateStr + "'";

        Map<String, Object> map = Collections.emptyMap();
        try {
          map = template.queryForMap(sql);
        } catch (EmptyResultDataAccessException e) {
          e.printStackTrace();
          return null;
        }
        if (!CollectionUtils.isEmpty(map)) {
          List<String> shiftPoliceNames = new ArrayList<>();

          PoliceShift policeShift = new PoliceShift();
          policeShift.setLeaderPoliceName((String) map.get("DBLD"));

          
          String shiftPoliceName = (String) map.get("ZBMJ");


          if (StringUtils.isNotBlank(shiftPoliceName)) {
            shiftPoliceNames.addAll(Arrays.asList(shiftPoliceName.split(",")));
            policeShift.setShiftPoliceNames(shiftPoliceNames);
          }
          return policeShift;
        }

        return null;
      }

    }



    long count = template.count(query, Meeting.class);

    query.with(pageable);
    List<Meeting> list = template.find(query, Meeting.class);
    return new PageImpl<Meeting>(list, pageable, count);
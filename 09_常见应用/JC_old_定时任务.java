* 定时任务 时间设置
    // 每天11点之前
    String taskId = addTask("01 11 * * *", policeNotInPrisonTask);    
    // 11点至17点没有民警进监则报警
    String taskId = addTask("01 17 * * *", policeNotInPrisonTask);
    // 值班领导一天内未进一个监室报警
    String taskId = addTask("01 00 * * *", shiftLeaderPoliceNotInPrisonTask);
    // 警区领导进监。每天0点1分运行 
    String taskId = addTask("01 00 * * *", leaderPoliceNotInPrisonTask);
    // 谈话时间少于十分钟。每分钟运行
    String taskId = addTask("* * * * *", policeTalkTimeDeficientTask);
    // 主管民警月谈话： 每个月20号0点1分运行， 统计上个月21号0点至本月20号0点的数据
    String taskId = addTask("01 00 20 * *", managePoliceNotTalkTask);

* 在 Spring 启动的时候，自动加载所有 Bean 的时候
    会调用 @PostConstruct 注解的方法
    这样就完成了 Task 任务的启动
    通过业务判断，设置定时任务。由系统自动来调用
    而每一个任务，都 通过自己的业务判断。来发出对应的通知就可以了。

————————————————————————————————————————————————————————————————————————————————————————————
    @Service
    public class ScheduleService {
      // 日志  
      private final Logger logger = LogManager.getLogger(ScheduleService.class);
      // new Scheduler
      private final Scheduler scheduler = new Scheduler();
      // 获取配置文件  是否需要 task
      @Value("${need.task}")
      private boolean needTask;
      // 根据配置文件 获取 prisonId
      @Value("${prisonId}")
      private String prisonId;

      // 启动
      private boolean start() {
        if (!needTask) {
          return false;
        }
        // 打印日志
        logger.info("ScheduleService start ...");
        // 启动 scheduler
        scheduler.start();
        return true;
      }

      // 看不懂
      public it.sauronsoftware.cron4j.Task getById(String id) {
        return scheduler.getTask(id);
      }

      // 添加 Task
      private String addTask(String cronExpress, Runnable task) throws InvalidPatternException {
        if (!needTask) {
          return null;
        }
        return scheduler.schedule(cronExpress, task);
      }


      public void delTask(String taskId) {
        scheduler.deschedule(taskId);
      }

      /**
       * 初始化所有任务.
       */
      // 启动的时候会执行一次。 
      @PostConstruct
      public void init() {
        if (!needTask) {
          return;
        }

        this.start();
        // 这些就是我们设置好的 task 任务
        startPoliceNotInPrisonTaskMorning();
        startPoliceNotInPrisonTaskEvening();
        startShiftLeaderPoliceNotInPrisonTask();
        startLeaderPoliceNotInPrisonTask();
        startPoliceTalkTimeDeficientTask();
        startManagePoliceNotTalkTask();
      }

      /**
       * 每天11点之前没有民警进监则报警
       */
      private void startPoliceNotInPrisonTaskMorning() {
        policeNotInPrisonTask.setPrisonId(prisonId);
        policeNotInPrisonTask.setStartHour(0);
        policeNotInPrisonTask.setStartMimute(0);
        policeNotInPrisonTask.setStartSecond(0);
        policeNotInPrisonTask.setEndHour(11);
        policeNotInPrisonTask.setEndMimute(0);
        policeNotInPrisonTask.setEndSecond(0);
        // 添加任务 返回一个 taskId 用来判断
        String taskId = addTask("01 11 * * *", policeNotInPrisonTask);
        if (StringUtils.isBlank(taskId)) {
          logger.error("启动 PoliceNotInPrisonTaskMorning 任务失败！");
        }
      }

      /**
       * 11点至17点没有民警进监则报警
       */
      private void startPoliceNotInPrisonTaskEvening() {
        policeNotInPrisonTask.setPrisonId(prisonId);
        policeNotInPrisonTask.setStartHour(11);
        policeNotInPrisonTask.setStartMimute(0);
        policeNotInPrisonTask.setStartSecond(0);
        policeNotInPrisonTask.setEndHour(17);
        policeNotInPrisonTask.setEndMimute(0);
        policeNotInPrisonTask.setEndSecond(0);
        String taskId = addTask("01 17 * * *", policeNotInPrisonTask);
        if (StringUtils.isBlank(taskId)) {
          logger.error("启动 PoliceNotInPrisonTaskEvening 任务失败！");
        }
      }

      /**
       * 值班领导一天内未进一个监室报警
       */
      private void startShiftLeaderPoliceNotInPrisonTask() {
        shiftLeaderPoliceNotInPrisonTask.setPrisonId(prisonId);
        String taskId = addTask("01 00 * * *", shiftLeaderPoliceNotInPrisonTask);
        if (StringUtils.isBlank(taskId)) {
          logger.error("启动 ShiftLeaderPoliceNotInPrisonTask 任务失败！");
        }
      }

      /**
       * 警区领导进监。每天0点1分运行
       */
      private void startLeaderPoliceNotInPrisonTask() {
        leaderPoliceNotInPrisonTask.setPrisonId(prisonId);
        String taskId = addTask("01 00 * * *", leaderPoliceNotInPrisonTask);
        if (StringUtils.isBlank(taskId)) {
          logger.error("启动 LeaderPoliceNotInPrisonTask 任务失败！");
        }
      }

      /**
       * 谈话时间少于十分钟。每分钟运行
       */
      private void startPoliceTalkTimeDeficientTask() {
        policeTalkTimeDeficientTask.setPrisonId(prisonId);
        String taskId = addTask("* * * * *", policeTalkTimeDeficientTask);
        if (StringUtils.isBlank(taskId)) {
          logger.error("启动 PoliceTalkTimeDeficientTask 任务失败！");
        }
      }

      /**
       * 主管民警月谈话： 每个月20号0点1分运行， 统计上个月21号0点至本月20号0点的数据
       */
      private void startManagePoliceNotTalkTask() {
        managePoliceNotTalkTask.setPrisonId(prisonId);
        String taskId = addTask("01 00 20 * *", managePoliceNotTalkTask);
        if (StringUtils.isBlank(taskId)) {
          logger.error("启动 ManagePoliceNotTalkTask 任务失败！");
        }
      }
    }

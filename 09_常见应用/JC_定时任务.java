* JC-new 定时任务
    @Service
    public class ScheduleService {
      public it.sauronsoftware.cron4j.Task getById(String id) {
        return scheduler.getTask(id);
      }

      private String addTask(String cronExpress, Runnable task) throws InvalidPatternException {
        if (!needTask) {
          return null;
        }
        return scheduler.schedule(cronExpress, task);
      }

      /**
       * 新增任务.
       * 
       * @param task 任务信息
       * @return 任务id
       * @throws Exception 新增任务异常
       */
      public String addTask(Task task) throws Exception {
        if (!needTask) {
          throw new Exception("config文件配置不需要任务，任务启动失败，并且不会被插入到数据库中！");
        }

        // 启动任务
        String order = task.getOrderName();
        switch (order) {
          case Task.ORDER_ROLLCALL:
            rollcallTask.setPrisonId(task.getPrisonId());
            return this.addTask(task.getCronExpress(), rollcallTask);
          case Task.ORDER_SHIFT_START:
            shiftStartTask.setPrisonId(task.getPrisonId());
            return this.addTask(task.getCronExpress(), shiftStartTask);
          case Task.ORDER_PLAY_MUSIC:
            playMusicTask.setPrisonId(task.getPrisonId());
            playMusicTask.setMusicAddress(task.getMusicFtpAddress());
            return this.addTask(task.getCronExpress(), playMusicTask);
          case Task.ORDER_UDP_MESSAGE_PUSH:
            tmpUdpMessagePushTask.setPrisonId(task.getPrisonId());
            return this.addTask(task.getCronExpress(), tmpUdpMessagePushTask);
          case Task.ORDER_MQ_MESSAGE_PUSH:
            tmpMqMessagePushTask.setPrisonId(task.getPrisonId());
            return this.addTask(task.getCronExpress(), tmpMqMessagePushTask);
          case Task.ORDER_FINGERPRINT_ADD:
            fingerprintUpdateTask.setPrisonId(task.getPrisonId());
            return this.addTask(task.getCronExpress(), fingerprintUpdateTask);
          case Task.ORDER_FINGERPRINT_DELETE:
            fingerprintDeleteTask.setPrisonId(task.getPrisonId());
            return this.addTask(task.getCronExpress(), fingerprintDeleteTask);
          default:
            break;
        }
        return "";
      }
      public void delTask(String taskId) {
        scheduler.deschedule(taskId);
      }

      /**
       * 初始化所有任务.
       */
      @PostConstruct
      public void init() {
        if (!needTask) {
          return;
        }
        this.start();
        List<Task> tasks = taskService.findByPrisonId(prisonId);
        for (Task task : tasks) {
          try {
            String taskId = this.addTask(task);
            logger.debug("init task : taskId = " + taskId);
            taskService.updateTaskId(task.getId(), taskId);
          } catch (Exception ex) {
            ex.printStackTrace();
          }
        }
      }
    }
————————————————————————————————————————————————————————————————————————————————————————————————————————————
* 定时任务设置
    // 注入以后 放入任务
    @Autowired
    private TmpMqMessagePushTask tmpMqMessagePushTask;        
* 设置定时任务
    /**
     * 临时任务。轮询数据库，提押提讯人员推送给mq.
     * 
     * @author lorien
     *
     */
    public class TmpMqMessagePushTask extends BaseTask implements Runnable {
     @Override
      public void run() {
        String arraignPath = lastSynchronizeTimeFilePath + ARRAIGN_FILE_NAME;
        logger.info("提讯查询...");
        String arraignTimeStr = getLastSynchronizeTime(arraignPath);
        List<MessagePushDto> arraigns = repository.findArraign4MeetingMqPush(prisonId, arraignTimeStr);
        if (CollectionUtils.isNotEmpty(arraigns)) {
          for (MessagePushDto dto : arraigns) {
            sendMessage2Terminal(dto, SystemNotice.ORDER_ARRAIGN);
          }
          updateLastSynchronizeTime(arraignPath, arraigns.get(0).getLastUpdateTimeStr());
        }

        String mortgagePath = lastSynchronizeTimeFilePath + MORTGAGE_FILE_NAME;
        logger.info("提押查询...");
        String mortgageTimeStr = getLastSynchronizeTime(mortgagePath);
        List<MessagePushDto> mortgages =
            repository.findMortgage4MeetingMqPush(prisonId, mortgageTimeStr);
        if (CollectionUtils.isNotEmpty(mortgages)) {
          for (MessagePushDto dto : mortgages) {
            sendMessage2Terminal(dto, SystemNotice.ORDER_MORTGAGE);
          }
          updateLastSynchronizeTime(mortgagePath, mortgages.get(0).getLastUpdateTimeStr());
        }
      }
      }         
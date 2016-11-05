* 新建文件夹
    // 根据路径判断，如果存在这个路径就返回这个 文件。
    // 如果不存在这个路径 则创建这个文件。
    File file = new File(path);
      if (!file.exists()) {
        file.createNewFile();
      }
      return file;
    }    
    
* 新建文件路径
    // 根据路径判断，如果这个文件不存在。则创建这个文件路径。
    private void creatNewFilePath() {
      File file = new File(lastSynchronizeTimeFilePath);
      if (!file.exists()) {
        file.mkdir();
      }
    }  
    
* 文件操作 配置
    // properties 文件中配置
    talk.lastSynchronizeTime.path = D:/dba/

    // 通过 @Value 来取出配置的数据。
    @Value("${talk.lastSynchronizeTime.path}")
    private String lastSynchronizeTimeFilePath;
    
    #task param
    tmp.messagepush.lastSynchronizeTime.path = D:/dba/
————————————————————————————————————————————————————————————————————————————————————————————————————
* 获取文件
      private static File getFile(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
          file.createNewFile();
        }
        return file;
      }    
* 
      static String getLastSynchronizeTime(String path) {
        String lastSynchronizeTime = null;
        String defaultTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        try {
          // 根据获取的文件，创建 BufferedReader  
          BufferedReader reader = new BufferedReader(new FileReader(getFile(path)));
          // 通过 readLine() 读取 String
          lastSynchronizeTime = reader.readLine();
          // 关闭 reader
          reader.close();
          // 业务处理
          if (StringUtils.isBlank(lastSynchronizeTime)) {
            // 如果为空 则获取当前时间写入文件
            updateLastSynchronizeTime(path, defaultTime);
            return defaultTime;
          } else {
            return lastSynchronizeTime;
          }
        } catch (FileNotFoundException ex1) {
          ex1.printStackTrace();
        } catch (IOException ex2) {
          ex2.printStackTrace();
        }

* 写入文件
      static void updateLastSynchronizeTime(String path, String timestamp) {
        try {
          // 根据文件创建 BufferedWriter
          BufferedWriter writer = new BufferedWriter(new FileWriter(getFile(path)));
          // 写入文件
          writer.write(timestamp);
          // 关闭 BufferedWriter
          writer.close();
        } catch (FileNotFoundException ex1) {
          ex1.printStackTrace();
        } catch (IOException ex2) {
          ex2.printStackTrace();
        }
      }
        return defaultTime;
      }
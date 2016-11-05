* 配置里面的设置内容
    #upload
    picUploadPath=D:/dba/
    picAccessUrl=/pic

    #network
    localArea.ipStart=192.168. // 好像是路径
————————————————————————————————————————————————————————————————————————————————————————————————
  @Controller
  @RequestMapping("/file")
  public class FileController {

    @Value("${picUploadPath}") // 取得上传路径
    private String picUploadPath;

    @Value("${picAccessUrl}") // 获取上传 url 
    private String picAccessUrl;

    @Autowired
    private GlobalConfigService globalConfigService; // 全局设置

    @Value("${localArea.ipStart}")
    private String localAreaIpStart; // 本地 IP 开始的头 

    private final Logger logger = LoggerFactory.getLogger(FileController.class);
    // TODO 看不懂这段代码。。

    /**
     * ckeditor上传图片.
     * 
     * @param multipartRequest 图片
     * @param response 返回信息
     * @param path 图片上传路径
     * @throws IllegalStateException 非法状态
     * @throws IOException io异常
     */
    @ResponseBody
    // 上传url 使用 POST 方法传入
    @RequestMapping(value = "/upload/CKEditorPic", method = RequestMethod.POST)
    // Spring 中 MultipartHttpServletRequest实现文件上传
    public void ckEditorPicUpload(MultipartHttpServletRequest multipartRequest,
        HttpServletResponse response, String path) throws IllegalStateException, IOException {

      // 获取上传的文件
      // 获取请求体里面的内容
      MultipartFile multiFile = multipartRequest.getFile("upload");

      // PrintWriter 是一个非常实用的输出流 初始化
      PrintWriter out = response.getWriter();
      String callback = multipartRequest.getParameter("CKEditorFuncNum"); // 返回的内容
      out.println("<script type=\"text/javascript\">");

      // 如果为空的话 发出提示信息
      String expandedName = getExpandedName(multiFile.getContentType());
      if (StringUtils.isBlank(expandedName)) { 
        out.println("window.parent.CKEDITOR.tools.callFunction(" + callback + ",'',"
            + "'image type must be .jpg/.gif/.bmp/.png');");
        out.println("</script>");
        return;
      }

      // 生成文件名
      String fileName = new ObjectId().toHexString() + expandedName; 
      InputStream is = null; // 输入流
      OutputStream os = null; // 输出流

      String dirPath = picUploadPath + "/" + path; // 根据根目录路径，获得要保存的路径
                                                   // path 是传入的路径
      // 保存
      try {
        is = multiFile.getInputStream(); // 输入流
        byte[] bs = new byte[1024]; // 
        int len;
        File dir = new File(dirPath);
        if (!dir.exists()) {
          dir.mkdirs();
        }
        os = new FileOutputStream(new File(dirPath + "/" + fileName)); // 输出流
        while ((len = is.read(bs)) != -1) {
          os.write(bs, 0, len);
        }
      } catch (FileNotFoundException ex1) {
        logger.error("file not found!");
        ex1.printStackTrace();
        out.println(
            "window.parent.CKEDITOR.tools.callFunction(" + callback + ",''," + "'update error');");
      } catch (IOException ex2) {
        logger.error("io exception!");
        ex2.printStackTrace();
        out.println(
            "window.parent.CKEDITOR.tools.callFunction(" + callback + ",''," + "'update error');");
      } finally {
        if (os != null) {
          os.close();
        }
        if (is != null) {
          is.close();
        }
      }

      String ip = multipartRequest.getRemoteAddr();
      String prisonId = (String) multipartRequest.getSession().getAttribute("prisonId");
      GlobalConfig globalConfig =
          globalConfigService.findByPrisonId(prisonId, ip.startsWith(localAreaIpStart));

      out.println("window.parent.CKEDITOR.tools.callFunction(" + callback + ",'" + "http://"
          + globalConfig.getNginxAddress() + ":" + globalConfig.getNginxPort()
          + multipartRequest.getContextPath() + "/" + picAccessUrl + "/" + path + "/" + fileName
          + "','')");

      out.println("</script>");
      return;
    }

    /**
     * 文件上传.
     * 
     * @param request 请求
     * @param file 文件
     * @param path 上传路径
     * @param fileExpandedName 文件后缀
     * @return 图片访问路径
     * @throws IOException io异常
     */

    @ResponseBody
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(HttpServletRequest request, byte[] file, String path,
        String fileExpandedName) throws IOException {
      // String ip = request.getRemoteAddr();
      // String nginxAddress = ip.startsWith(localAreaIpStart) ? nginxLocalAddress : nginxPubAddress;
      logger.debug("收到上传文件请求，文件长度：" + file.length);

      String fileName = new ObjectId().toHexString() + "." + fileExpandedName;
      OutputStream os = null;

      Calendar calendar = Calendar.getInstance();
      path += "/" + calendar.get(Calendar.YEAR) + "/" + calendar.get(Calendar.MONTH) + "/";

      String dirPath = picUploadPath + "/" + path;
      String filePath = dirPath + "/" + fileName;
      String fileAccessUrl =
          request.getContextPath() + "/" + picAccessUrl + "/" + path + "/" + fileName;
      try {
        File dir = new File(dirPath);
        if (!dir.exists()) {
          dir.mkdirs();
        }
        byte[] decodeFile = java.util.Base64.getDecoder().decode(file);
        os = new FileOutputStream(new File(filePath));
        os.write(decodeFile);
        logger.debug("写入文件成功");
      } catch (FileNotFoundException ex1) {
        logger.error("file not found!");
        ex1.printStackTrace();
      } catch (IOException ex2) {
        logger.error("io exception!");
        ex2.printStackTrace();
      } finally {
        if (os != null) {
          os.close();
        }
      }
      return fileAccessUrl;
    }

    /**
     * 检查文件类型.
     * 
     * @param uploadContentType 文件类型
     * @return 后缀
     */
    public String getExpandedName(String uploadContentType) {

      switch (uploadContentType) {
        case "image/pjpeg":
          return ".jpg";
        case "image/jpeg":
          return ".jpg";
        case "image/png":
          return ".png";
        case "image/x-png":
          return ".png";
        case "image/gif":
          return ".gif";
        case "image/bmp":
          return ".bmp";
        default:
          return "";
      }
    }

  }
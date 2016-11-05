* 
——————————————————————————————————————————————————————————————————————————————————————————————————————————
* switch 循环
* 学习 这种处理思想。
      // 处理字符串
      for (String string : periods) {
        switch (string) {
          case "上午":
            if (hour < 10) {
              list.add(string);
            }
            break;
          case "中午":
            if (hour < 14) {
              list.add(string);
            }
            break;
          case "下午":
            list.add(string);
            break;
          default:
            break;
        }
      }
——————————————————————————————————————————————————————————————————————————————————————————————————————————
* 删除字符串
    * 如果在字符串处理中，删除字符串比较困难。
        可以思考可不可以 新建一个字符串来添加。
* 字符串数组 转 字符串 List 
    * 因为字符串数组是固定的 不好处理业务。
        最好可以将他转换为 List 数组。
        // TODO 尝试写一个工具类。
——————————————————————————————————————————————————————————————————————————————————————————————————————————
* 截取字符串
    String result = answers.substring(0, answers.length() - 1);
    codes.deleteCharAt(codes.length() - 1).append(")"); 
——————————————————————————————————————————————————————————————————————————————————————————————————————————
* 拼接字符串
    // 通过传入的户籍代码 List 来拼接 in 查询的字符串
    if (houseAddrCodes != null && houseAddrCodes.size() > 0) {

      StringBuilder codes = new StringBuilder();
      codes.append("ryxxb.HJSZD in (");
      for (String code : houseAddrCodes) {
        codes.append("'").append(code).append("',");
      }
      codes.deleteCharAt(codes.length() - 1).append(")");
      WHERE(codes.toString()); // toString 转换
    }

    // 拼接字符串
    StringBuilder stringBuilder = new StringBuilder();
    for (String drug : drugs) {
      stringBuilder.append(drug).append(",");
    }
    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
——————————————————————————————————————————————————————————————————————————————————————————————————————————
* 判断字符串是否含有某个子字符串
    !code.contains(",")
    // 判断字符串里面是否包含有 "," 。如果没有则是单数据

——————————————————————————————————————————————————————————————————————————————————————————————————————————
* String 常用方法
    * boolean endsWith(String suffix)    
        // 判断某个字符串是否是以suffix结束的。
        // 与之对应的还有startsWith, 在 String.java 中，endsWith 也是调用 startsWith

    * 取子字符串
        int prisonType = Integer.parseInt(prisonId.substring(7, 8));

    * 分割字符串
        JavaScript split() 方法
        // split() 方法用于把一个字符串分割成字符串数组。
        stringObject.split(separator,howmany)
        String[] answersArr = answers.split(",");
        String[] periods = medicineRecord.getPeriod().split(","); // 将传入的字符串切分        

——————————————————————————————————————————————————————————————————————————————————————————————————————————
* 字符串处理
 	 * 比较
   	 	if (prisoner.getInState() != null && !prisoner.getInState().equals("7")) 
            equals()比较的是对象的内容（区分字母的大小写格式），
            但是如果使用 "==" 比较两个对象时，
            比较的是两个对象的内存地址，所以不相等。
            即使它们内容相等，但是不同对象的内存地址也是不相同的。
  	* 取子字符串
    	int prisonType = Integer.parseInt(prisonId.substring(7, 8));

* 基本数据类型(系统方法)
    	&& (Integer.valueOf(tmp.getParam1()) < 21 || tmp.getParam1().equals("24"))
    	policeGroup != null

* 字符串定义
		// 定义和区别
		String... args
		String[]
* 传入两个变量
	// 一个name，一个values数组  可变参数
	param(String name, String... values)
	// 调用方式不同
	callMe1(new String[] {"a", "b", "c"});  
	callMe2("a", "b", "c");  
——————————————————————————————————————————————————————————————————————————————————————————————————————————
* 字符串转Map 和 List
    int status = result.getResponse().getStatus();
        // 确定返回的判断码
    if (status == 200) {
        // 如果是200成功
      String json = result.getResponse().getContentAsString();
        // 将整个字符串当作json 返回值
      ObjectMapper objectMapper = new ObjectMapper();
        // 创建一个 Jackson ObjectMapper 类 来处理JSON数据
      JavaType javaType =
          // 保存类型
          objectMapper.getTypeFactory().constructParametricType(ArrayList.class, Meeting.class);
          // 确定JavaType
      List<Meeting> meetings = (ArrayList<Meeting>) objectMapper.readValue(json, javaType);
            // 将json 数据转换为List<Meeting>
      
      if (meetings != null) {
        for (Meeting meeting : meetings) {
          System.out.println(meeting.toString());
        }
      }

    } else {
      System.out.println(result.getResponse().getContentAsString());
      fail("response status is not 200");
    }
——————————————————————————————————————————————————————————————————————————————————————————————————————————
* jackson 复杂对象集合 的几种简单转换
        /**
         * jackson 复杂 对象集合 的几种简单转换
         * @author lenovo
         *
         * @param <T>
         */
        public class Main<T>
        {
            static ObjectMapper mapper = new ObjectMapper();

            public static void main(String[] args) throws JsonParseException,
                    JsonMappingException, IOException
            {

                String josn = "{\"UserID\":1,\"LoginName\":\"唐工\",\"Truename\":\"超级\",\"Nickname\":null,\"LoginPwd\":\"E10ADC3949BA59ABBE56E057F20F883E\",\"QQ\":\"\",\"Phone\":\"\",\"Email\":null,\"Remark\":\"\",\"Account_Non_Locked\":0,\"Telelephone\":null,\"IsDelete\":0}";
                User u = mapper.readValue(josn, User.class);
                // User u=new Main<User>().jsonStreamConverObject(josn, User.class);
                System.out.println("转对象:" + u);

                // 转集合
                String josn2 = "[{\"UserID\":1,\"LoginName\":\"唐工\",\"Truename\":\"超级\",\"Nickname\":null,\"LoginPwd\":\"E10ADC3949BA59ABBE56E057F20F883E\",\"QQ\":\"\",\"Phone\":\"\",\"Email\":null,\"Remark\":\"\",\"Account_Non_Locked\":0,\"Telelephone\":null,\"IsDelete\":0}]";
                JavaType javaType = mapper.getTypeFactory().constructParametricType(
                        List.class, User.class);
                List<User> me = mapper.readValue(josn2, javaType);
                System.out.println("转集合me:" + me);

                // 对象里有 集合 转换
                String josn3 = "{\"UserID\":1,\"LoginName\":\"唐工\",\"Truename\":\"超级\",\"Nickname\":null,\"LoginPwd\":\"E10ADC3949BA59ABBE56E057F20F883E\",\"QQ\":\"\",\"Phone\":\"\",\"Email\":null,\"Remark\":\"\",\"Account_Non_Locked\":0,\"Telelephone\":null,\"IsDelete\":0,\"RoleList\":[{\"Roleid\":0,\"Name\":\"超级管理员\",\"Show_Name\":\"超级管理员\",\"Remark\":null,\"Type\":1}]}";

                User u3 = mapper.readValue(josn3, User.class); // 简单方式
                // User u3=new Main<User>().jsonConverObject(josn3, User.class); 流方式
                System.out.println("转对象里有集合u3:" + u3);

                // 集合 对象 集合 转换
                String josn4 = "[{\"UserID\":1,\"LoginName\":\"唐工\",\"Truename\":\"超级\",\"Nickname\":null,\"LoginPwd\":\"E10ADC3949BA59ABBE56E057F20F883E\",\"QQ\":\"\",\"Phone\":\"\",\"Email\":null,\"Remark\":\"\",\"Account_Non_Locked\":0,\"Telelephone\":null,\"IsDelete\":0,\"RoleList\":[{\"Roleid\":0,\"Name\":\"超级管理员\",\"Show_Name\":\"超级管理员\",\"Remark\":null,\"Type\":1}]},{\"UserID\":2,\"LoginName\":\"唐工\",\"Truename\":\"超级\",\"Nickname\":null,\"LoginPwd\":\"E10ADC3949BA59ABBE56E057F20F883E\",\"QQ\":\"\",\"Phone\":\"\",\"Email\":null,\"Remark\":\"\",\"Account_Non_Locked\":0,\"Telelephone\":null,\"IsDelete\":0,\"RoleList\":[{\"Roleid\":0,\"Name\":\"超级管理员\",\"Show_Name\":\"超级管理员\",\"Remark\":null,\"Type\":1}]}]";
                JavaType javaType4 = mapper.getTypeFactory().constructParametricType(
                        List.class, User.class);
                List<User> list = mapper.readValue(josn4, javaType4);
                System.out.println("集合里是对象 对象里有集合转换:" + list);

            }

            /***
             * 转对象
             * @param josn
             * @param clz
             * @return
             */
            public T jsonStreamConverObject(String josn, Class<T> clz)
            {

                T t = null;
                // ObjectMapper jacksonMapper = new ObjectMapper();
                InputStreamReader in = new InputStreamReader(new ByteArrayInputStream(
                        josn.getBytes()));
                BufferedReader streamReader = new BufferedReader(in);
                StringBuilder buff = new StringBuilder();
                String inputStr;
                try
                {
                    while ((inputStr = streamReader.readLine()) != null)
                        buff.append(inputStr);
                    // ObjectMapper mapper = new ObjectMapper();
                    t = mapper.readValue(buff.toString(), clz);

                } catch (IOException e)
                {

                    e.printStackTrace();
                }

                return t;
            }

            /***
             * 转对象
             * @param josn
             * @param clz
             * @return
             */
            public T jsonConverObject(String josn, Class<T> clz)
            {

                T t = null;
                try
                {
                    t = mapper.readValue(josn, clz);
                } catch (JsonParseException e)
                {
                    e.printStackTrace();
                } catch (JsonMappingException e)
                {
                    e.printStackTrace();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }

                return t;
            }

            /**
             * 转集合
             * @param josn
             * @param clz
             * @return
             */
            public List<T> jsonConverList(String josn, Class<T> clz)
            {

                List<T> me = null;
                try
                {
                    // jacksonMapper
                    // .disable(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);
                    // jacksonMapper.enableDefaultTyping();
                    // jacksonMapper.setVisibility(JsonMethod.FIELD,JsonAutoDetect.Visibility.ANY);
                    // jacksonMapper.configure(SerializationConfig.Feature.INDENT_OUTPUT,
                    // false);//格式化
                    // jacksonMapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
                    // jacksonMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS,
                    // false);

                    JavaType javaType = mapper.getTypeFactory()
                            .constructParametricType(List.class, clz);// clz.selGenType().getClass()

                    me = mapper.readValue(josn, javaType);
                } catch (JsonParseException e)
                {
                    e.printStackTrace();
                } catch (JsonMappingException e)
                {
                    e.printStackTrace();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }

                return me;
            }
        }


    * output:
    * 转对象:
            // 一个User对象
            User [UserID=1, LoginName=唐工, Truename=超级, Nickname=null, 
                    LoginPwd=E10ADC3949BA59ABBE56E057F20F883E, QQ=, Phone=, 
                    Email=null, Remark=, Account_Non_Locked=0, Telelephone=null, 
                    Indate=null, IsDelete=0, RoleList=null
                    ]
                    // 里面是User 的属性
    * 转集合me:
            [User 
                [UserID=1, LoginName=唐工, Truename=超级, Nickname=null, 
                    LoginPwd=E10ADC3949BA59ABBE56E057F20F883E, QQ=, Phone=, 
                    Email=null, Remark=, Account_Non_Locked=0, Telelephone=null, 
                    Indate=null, IsDelete=0, RoleList=null
                ]
                [
                ]
            ]
    * 转对象里有集合u3:
            // 一个User对象
            User [UserID=1, LoginName=唐工, Truename=超级, Nickname=null, 
                    LoginPwd=E10ADC3949BA59ABBE56E057F20F883E, QQ=, Phone=, 
                    Email=null, Remark=, Account_Non_Locked=0, Telelephone=null, 
                    Indate=null, IsDelete=0, 
                    // 里面都是 User的属性 
                    RoleList=[Role 
                                [Roleid=0, Name=超级管理员, 
                                    Show_Name=超级管理员, Remark=null, Type=1
                                ]
                             ]
                    // RoleList 是User的一个属性
                    // 里面保存的是一个集合。
                ]
    * 集合里是对象 对象里有集合转换:
            [User 
                [UserID=1, LoginName=唐工, Truename=超级, Nickname=null, 
                    LoginPwd=E10ADC3949BA59ABBE56E057F20F883E, QQ=, Phone=, Email=null, 
                    Remark=, Account_Non_Locked=0, Telelephone=null, Indate=null, IsDelete=0, 
                    RoleList=[Role [Roleid=0, Name=超级管理员, Show_Name=超级管理员, 
                                    Remark=null, Type=1
                                    ]
                              ]
                ], 
            User [UserID=2, LoginName=唐工, Truename=超级, Nickname=null, 
                    LoginPwd=E10ADC3949BA59ABBE56E057F20F883E, QQ=, Phone=, 
                    Email=null, Remark=, Account_Non_Locked=0, Telelephone=null, 
                    Indate=null, IsDelete=0, 
                    RoleList=[Role [Roleid=0, Name=超级管理员, Show_Name=超级管理员, 
                                    Remark=null, Type=1
                                    ]
                              ]
                 ]
            ]
————————————————————————————————————————————————————————————————————————————————————————————


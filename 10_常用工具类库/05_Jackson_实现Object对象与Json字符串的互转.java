 Jackson实现Object对象与Json字符串的互转
——————————————————————————————————————————————————————————————————————————————————————————————————————————
一、准备

如果你需要使用jackson，你必须得导入相应的架包，有如下三个包

jackson-annotations；jackson-core；jackson-databind

Maven引入依赖代码

[java] view plain copy
print?在CODE上查看代码片派生到我的代码片

    <span style="font-size:18px;">            <dependency>  
                    <groupId>com.fasterxml.jackson.core</groupId>  
                    <artifactId>jackson-databind</artifactId>  
                    <version>2.0.6</version>  
                </dependency>  
                <dependency>  
                    <groupId>com.fasterxml.jackson.module</groupId>  
                    <artifactId>jackson-module-jaxb-annotations</artifactId>  
                    <version>2.0.6</version>  
                </dependency>  
                <dependency>  
                    <groupId>com.fasterxml.jackson.core</groupId>  
                    <artifactId>jackson-core</artifactId>  
                    <version>2.3.0</version>  
                </dependency>  
                <dependency>  
                    <groupId>com.fasterxml.jackson.core</groupId>  
                    <artifactId>jackson-annotations</artifactId>  
                    <version>2.3.0</version>  
                </dependency></span>  

——————————————————————————————————————————————————————————————————————————————————————————————————————————
二、不带日期的对象实体与json互转

1.定义实体UserBean.java,DeptBean.java
    
    package com.jackson.bean;  
      
    import java.util.List;  
      
    public class DeptBean {  
        private int deptId;  
        private String deptName;  
        private List<UserBean> userBeanList;        
          
        public int getDeptId() {  
            return deptId;  
        }  
        public void setDeptId(int deptId) {  
            this.deptId = deptId;  
        }  
        public String getDeptName() {  
            return deptName;  
        }  
        public void setDeptName(String deptName) {  
            this.deptName = deptName;  
        }  
        public List<UserBean> getUserBeanList() {  
            return userBeanList;  
        }  
        public void setUserBeanList(List<UserBean> userBeanList) {  
            this.userBeanList = userBeanList;  
        }  
          
          
          
        @Override  
        public String toString() {  
            String userBeanListString = "";  
            for (UserBean userBean : userBeanList) {  
                userBeanListString += userBean.toString() + "\n";  
            }  
              
            return "DeptBean [deptId=" + deptId + ", deptName=" + deptName  
                    + ", \nuserBeanListString=" + userBeanListString + "]";  
        }  
        public DeptBean(int deptId, String deptName, List<UserBean> userBeanList) {  
            super();  
            this.deptId = deptId;  
            this.deptName = deptName;  
            this.userBeanList = userBeanList;  
        }  
        public DeptBean() {  
            super();  
        }  
          
          
    }  

    package com.jackson.bean;  
      
    public class UserBean {  
        private int userId;  
        private String userName;  
        private String password;  
        private String email;  
        public int getUserId() {  
            return userId;  
        }  
        public void setUserId(int userId) {  
            this.userId = userId;  
        }  
        public String getUserName() {  
            return userName;  
        }  
        public void setUserName(String userName) {  
            this.userName = userName;  
        }  
        public String getPassword() {  
            return password;  
        }  
        public void setPassword(String password) {  
            this.password = password;  
        }  
        public String getEmail() {  
            return email;  
        }  
        public void setEmail(String email) {  
            this.email = email;  
        }  
        @Override  
        public String toString() {  
            return "UserBean [userId=" + userId + ", userName=" + userName  
                    + ", password=" + password + ", email=" + email + "]";  
        }  
        public UserBean(int userId, String userName, String password, String email) {  
            super();  
            this.userId = userId;  
            this.userName = userName;  
            this.password = password;  
            this.email = email;  
        }  
        public UserBean() {  
            super();  
        }  
          
          
    }  

注意：在实体中必须存在无参的构造方法，否则转换时会有如下异常;

com.fasterxml.jackson.databind.JsonMappingException: No suitable constructor found 
for type [simple type, class com.jackson.bean.UserBean]: 
    can not instantiate from JSON object (need to add/enable type information?)


2.jackson数据转换工具类

    package com.jackson.utils;  
      
    import com.fasterxml.jackson.core.type.TypeReference;  
    import com.fasterxml.jackson.databind.ObjectMapper;  
      
    /** 
     * The class JacksonUtil 
     * 
     * json字符与对像转换 
     *  
     * @version: $Revision$ $Date$ $LastChangedBy$ 
     * 
     */  
    public final class JacksonUtil {  
      
        public static ObjectMapper objectMapper;  
      
        /** 
         * 使用泛型方法，把json字符串转换为相应的JavaBean对象。 
         * (1)转换为普通JavaBean：readValue(json,Student.class) 
         * (2)转换为List,如List<Student>,将第二个参数传递为Student 
         * [].class.然后使用Arrays.asList();方法把得到的数组转换为特定类型的List 
         *  
         * @param jsonStr 
         * @param valueType 
         * @return 
         */  
        public static <T> T readValue(String jsonStr, Class<T> valueType) {  
            if (objectMapper == null) {  
                objectMapper = new ObjectMapper();  
            }  
      
            try {  
                return objectMapper.readValue(jsonStr, valueType);  
                // 转换为 JAVA 对象
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
      
            return null;  
        }  
          
        /** 
         * json数组转List 
         * @param jsonStr 
         * @param valueTypeRef 
         * @return 
         */  
        public static <T> T readValue(String jsonStr, TypeReference<T> valueTypeRef){  
            if (objectMapper == null) {  
                objectMapper = new ObjectMapper();  
            }  
      
            try {  
                return objectMapper.readValue(jsonStr, valueTypeRef);  
                // 
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
            return null;  
        }  
      
        /** 
         * 把JavaBean转换为json字符串 
         *  
         * @param object 
         * @return 
         */  
        public static String toJSon(Object object) {  
            if (objectMapper == null) {  
                objectMapper = new ObjectMapper();  
            }  
      
            try {  
                return objectMapper.writeValueAsString(object);  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
            return null;  
        }   
    }
——————————————————————————————————————————————————————————————————————————————————————————————————————————
3.jackson数据转换具体实现

    package com.jackson.main;  
      
    import java.util.ArrayList;  
    import java.util.List;  
      
    import com.fasterxml.jackson.core.type.TypeReference;  
    import com.jackson.bean.DeptBean;  
    import com.jackson.bean.UserBean;  
    import com.jackson.utils.JacksonUtil;  
      
    /** 
     * 实例实现利用jackson实现实体对象与json字符串的互相转换 
     * @author liangming.deng 
     * 
     */  
    public class JacksonDemo {  
        public static void main(String[] args){  
              
            UserBean userBean1 = new UserBean(1, "liubei", "123", "liubei@163.com");  
            UserBean userBean2 = new UserBean(2, "guanyu", "123", "guanyu@163.com");  
            UserBean userBean3 = new UserBean(3, "zhangfei", "123", "zhangfei@163.com");  
                // 定义三个JAVA 对象
              
              
            List<UserBean> userBeans = new ArrayList<>();  
            userBeans.add(userBean1);  
            userBeans.add(userBean2);  
            userBeans.add(userBean3);  
                // 创建一个包含有JAVA 对象的 List泛型
              
            DeptBean deptBean = new DeptBean(1, "sanguo", userBeans);  
            //对象转json  
            String userBeanToJson = JacksonUtil.toJSon(userBean1);  
            String deptBeanToJson = JacksonUtil.toJSon(deptBean);  
              
            System.out.println("deptBean to json:" + deptBeanToJson);  
            System.out.println("userBean to json:" + userBeanToJson);  
              
            //json转字符串  
            UserBean jsonToUserBean = JacksonUtil.readValue(userBeanToJson, UserBean.class);  
            DeptBean jsonToDeptBean = JacksonUtil.readValue(deptBeanToJson, DeptBean.class);  
              
            System.out.println("json to DeptBean" + jsonToDeptBean.toString());  
            System.out.println("json to UserBean" + jsonToUserBean.toString());  
              
            //List 转json字符串  
            String listToJson = JacksonUtil.toJSon(userBeans);  
            System.out.println("list to json:" + listToJson);  
              
            //数组json转 List  
            List<UserBean> jsonToUserBeans = JacksonUtil.readValue(listToJson, new TypeReference<List<UserBean>>() {  
            });  
            String userBeanString = "";  
            for (UserBean userBean : jsonToUserBeans) {  
                userBeanString += userBean.toString() + "\n";  
            }  
            System.out.println("json to userBeans:" + userBeanString);  
        }     
    }  

输出结果：

    deptBean to json:{"deptId":1,"deptName":"sanguo","userBeanList":[{"userId":1,"userName":"liubei","password":"123","email":"liubei@163.com"},{"userId":2,"userName":"guanyu","password":"123","email":"guanyu@163.com"},{"userId":3,"userName":"zhangfei","password":"123","email":"zhangfei@163.com"}]}  
    userBean to json:{"userId":1,"userName":"liubei","password":"123","email":"liubei@163.com"}  
    json to DeptBeanDeptBean [deptId=1, deptName=sanguo,   
    userBeanListString=UserBean [userId=1, userName=liubei, password=123, email=liubei@163.com]  
    UserBean [userId=2, userName=guanyu, password=123, email=guanyu@163.com]  
    UserBean [userId=3, userName=zhangfei, password=123, email=zhangfei@163.com]  
    ]  
    json to UserBeanUserBean [userId=1, userName=liubei, password=123, email=liubei@163.com]  
    list to json:[{"userId":1,"userName":"liubei","password":"123","email":"liubei@163.com"},{"userId":2,"userName":"guanyu","password":"123","email":"guanyu@163.com"},{"userId":3,"userName":"zhangfei","password":"123","email":"zhangfei@163.com"}]  
    json to userBeans:UserBean [userId=1, userName=liubei, password=123, email=liubei@163.com]  
    UserBean [userId=2, userName=guanyu, password=123, email=guanyu@163.com]  
    UserBean [userId=3, userName=zhangfei, password=123, email=zhangfei@163.com]  
——————————————————————————————————————————————————————————————————————————————————————————————————————————
——————————————————————————————————————————————————————————————————————————————————————————————————————————
三、带日期的实体对象与json转换

jackson实现带日期的实体对象与json转换有两种方法

1).将实体对象中的日期对象定义为String型，在使用的时候再将String型转换为Date型使用，其他就无需修改。

2).当实体对象中的日期对象定义为Date型，就需要通过集成JsonSerializer<Date>对象完成日期的转换，本段将重点讲解
1.在UserBean.java和DeptBean.java中分别添加Date createDate

        private Date createDate;      
            public Date getCreateDate() {  
            return createDate;  
        }  
      
        public void setCreateDate(Date createDate) {  
            this.createDate = createDate;  
        }

2.实现集成于JsonSerializer<Date>对象的工具类

    package com.jackson.utils;  
      
    import java.io.IOException;  
    import java.text.SimpleDateFormat;  
    import java.util.Date;  
      
    import com.fasterxml.jackson.core.JsonGenerator;  
    import com.fasterxml.jackson.core.JsonProcessingException;  
    import com.fasterxml.jackson.databind.JsonSerializer;  
    import com.fasterxml.jackson.databind.SerializerProvider;  
      
    /** 
     * jackson日期转换工具类 
     *  
     * @author liangming.deng 
     * 
     */  
    public class JsonDateFormatFull extends JsonSerializer<Date> {  
      
        /** 
         * Jackson支持日期字符串格式 
         * "yyyy-MM-dd'T'HH:mm:ss.SSSZ" "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" 
         * "EEE, dd MMM yyyy HH:mm:ss zzz" "yyyy-MM-dd" 
         */  
        @Override  
        public void serialize(Date value, JsonGenerator jgen,  
                SerializerProvider provider) throws  
      
        IOException, JsonProcessingException {  
            SimpleDateFormat formatter = new SimpleDateFormat(  
                    "yyyy-MM-dd'T'HH:mm:ss");  
            String formattedDate = formatter.format(value);  
            jgen.writeString(formattedDate);  
      
        }  
      
    }  
    


3.在UserBean.java和DeptBean.java中Date变量加入如下注解

        @JsonSerialize(using = JsonDateFormateFull.class)  
        private Date createDate;

4.jackson日期互转具体实现

    package com.jackson.main;  
      
    import java.util.Date;  
    import java.util.ArrayList;  
    import java.util.Calendar;  
    import java.util.List;  
      
    import com.fasterxml.jackson.core.type.TypeReference;  
    import com.jackson.bean.DeptBean;  
    import com.jackson.bean.UserBean;  
    import com.jackson.utils.JacksonUtil;  
      
    /** 
     *利用jackson实现Json与实体对象的互转 
     * @author liangming.deng 
     * 
     */  
    public class JacksonDemo {  
        public static void main(String[] args){  
              
            UserBean userBean1 = new UserBean(1, "liubei", "123", "liubei@163.com");  
            userBean1.setCreateDate(new Date(Calendar.getInstance().getTimeInMillis()));  
            UserBean userBean2 = new UserBean(2, "guanyu", "123", "guanyu@163.com");  
            userBean2.setCreateDate(new Date(Calendar.getInstance().getTimeInMillis()));  
            UserBean userBean3 = new UserBean(3, "zhangfei", "123", "zhangfei@163.com");  
            userBean3.setCreateDate(new Date(Calendar.getInstance().getTimeInMillis()));  
              
              
            List<UserBean> userBeans = new ArrayList<>();  
            userBeans.add(userBean1);  
            userBeans.add(userBean2);  
            userBeans.add(userBean3);  
              
            DeptBean deptBean = new DeptBean(1, "sanguo", userBeans);  
            deptBean.setCreateDate(new Date(Calendar.getInstance().getTimeInMillis()));  
            //实体对象 转json  
            String userBeanToJson = JacksonUtil.toJSon(userBean1);  
            String deptBeanToJson = JacksonUtil.toJSon(deptBean);  
              
            System.out.println("deptBean to json:" + deptBeanToJson);  
            System.out.println("userBean to json:" + userBeanToJson);  
              
            //json 转实体对象  
            UserBean jsonToUserBean = JacksonUtil.readValue(userBeanToJson, UserBean.class);  
            DeptBean jsonToDeptBean = JacksonUtil.readValue(deptBeanToJson, DeptBean.class);  
              
            System.out.println("json to DeptBean" + jsonToDeptBean.toString());  
            System.out.println("json to UserBean" + jsonToUserBean.toString());  
              
            //List 转json  
            String listToJson = JacksonUtil.toJSon(userBeans);  
            System.out.println("list to json:" + listToJson);  
              
            //json 转 List  
            List<UserBean> jsonToUserBeans = JacksonUtil.readValue(listToJson, new TypeReference<List<UserBean>>() {  
            });  
            String userBeanString = "";  
            for (UserBean userBean : jsonToUserBeans) {  
                userBeanString += userBean.toString() + "\n";  
            }  
            System.out.println("json to userBeans:" + userBeanString);  
        }     
    }  
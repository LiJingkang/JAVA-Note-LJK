* 查询通过没有返回空值不会报错
* 如果碰到代码中的错误拦截，则测试类会报错

* Assert.fail("response is null");
* status == HttpStatus.SC_OK
* System.out.println(result.getResponse().getContentAsString());


原来junit unrooted tests initializationError和找不到或无法加载主类其实错误的本质是一样的！就是无法创建类
————————————————————————————————————————————————————————————————————————————————————
*  JUnit 自带的断言和判断
    private static final String ACCEPT_JSON = "application/json;charset=utf-8";
    private static final String ACCEPT_JSON_UTF8 = MediaType.APPLICATION_JSON_UTF8.toString();
    // MediaType中定义好了 ACCEPT_JSON 并不需要我们自己去手动写

    // 测试正常返回数据
    RequestBuilder requestBuilder = get(url).params(params).accept(ACCEPT_JSON);
    MvcResult result = mockMvc.perform(requestBuilder).andDo(print()) // 执行请求
        .andExpect(handler().handlerType(PrisonerController.class)) // 验证分配的响应控制器对不对
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)) // 验证响应contentType 是不是
        .andExpect(status().isOk()) // 验证返回的特征码
        .andReturn(); // 返回Result 可以自己添加自定义断言

    // 测试 正常查询返回数据
    RequestBuilder requestBuilder = get(url).params(params).accept(ACCEPT_JSON);
    MvcResult result = mockMvc.perform(requestBuilder).andDo(print()) // 执行请求
        .andExpect(handler().handlerType(PrisonerController.class)) // 验证分配的响应控制器对不对
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)) // 验证响应contentType 是不是
        .andExpect(status().isOk()) // 验证返回的特征码
        .andReturn(); // 返回Result 可以自己添加自定义断言    
————————————————————————————————————————————————————————————————————————————————————
* 引入的包
    package com.newings.controller;

    import org.junit.Before;
    import org.junit.Test;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.test.web.servlet.MockMvc;
    import org.springframework.test.web.servlet.MvcResult;
    import org.springframework.test.web.servlet.RequestBuilder;
    import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
    import org.springframework.test.web.servlet.setup.MockMvcBuilders;
    import org.springframework.web.context.WebApplicationContext;

    // 需要引入的包
    import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;  
    import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;  
    import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;  
    import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;  

————————————————————————————————————————————————————————————————————————————————————
* 必传参数设置 并且打印

    // 测试必传参数
    params.remove("XXXXX");
    MvcResult errorResultNoXXXXX = mockMvc.perform(get(url).params(params).accept(ACCEPT_JSON))
        .andExpect(status().isBadRequest()).andReturn();
    System.out.println(errorResultNoXXXXX.getResponse().getErrorMessage());
    params.set("XXXXX", "XXXXX");

————————————————————————————————————————————————————————————————————————————————————
* 必传参数设置
    // 测试 正常查询返回数据    
    // 在返回值里面添加对结果的测试

    // 测试必传参数为空的情况
    // 会不会返回400错误，如果会则测试通过，如果不会则测试报错
    params.remove("cardId");
    mockMvc.perform(get(url).params(params).accept(ACCEPT_JSON)).andExpect(status().isBadRequest());
    params.set("cardId", "000000000924B870");

    params.remove("prisonId");
    mockMvc.perform(get(url).params(params).accept(ACCEPT_JSON)).andExpect(status().isBadRequest());

    // 检测 500 错误
    mockMvc.perform(get(url).params(params).accept(ACCEPT_JSON)).andExpect(status().isInternalServerError());

————————————————————————————————————————————————————————————————————————————————————
* ControllerTest 代码

        private static final String ACCEPT_JSON = "application/json;charset=utf-8";

        @Autowired
            private WebApplicationContext wac;
            private MockMvc mockMvc;

        @Before
        public void setUp() {
            mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        }
————————————————————
* 单条件查询通用例子
        @Test
        public void test() throws Exception {
            String url = "/compositiveQuery/findChangeDetainByNumber";
            String K = "number";
            String V = "330211111201510120013";

            RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url).param(K, V).accept(ACCEPT_JSON);
            MvcResult result = mockMvc.perform(requestBuilder).andReturn();
            System.out.println(result.getResponse().getContentAsString());
        }    
        // 要不要写一个工具类来统一调用
————————————————————
* 多条件查询例子
        /**
        * @author LJK
        */
        @Test
        public void XXXXXX() throws Exception {
            String url = "XXXXXX";

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.set("XXXXXX", "XXXXXX");            
            params.set("XXXXXX", "XXXXXX");
            params.set("XXXXXX", "XXXXXX");

            RequestBuilder requestBuilder =
                MockMvcRequestBuilders.get(url).params(params).accept(ACCEPT_JSON);
            MvcResult result = mockMvc.perform(requestBuilder).andReturn();
            System.out.println(result.getResponse().getContentAsString());
        }
————————————————————
* json转List泛型
      @Test
      public void testFindPrisoner() throws Exception {
        String url = "/meeting/search";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        // params.set("dormCodes", "0204");
        // params.set("dateFrom", "2016/01/01 01:01:01");
        // params.set("isDisease", "1");

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url)
            .param(params).accept(ACCEPT_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        int status = result.getResponse().getStatus();
        if (status == HttpStatus.SC_OK) {
          String json = result.getResponse().getContentAsString();
          ObjectMapper objectMapper = new ObjectMapper();

          JavaType javaType =
              objectMapper.getTypeFactory().constructParametricType(ArrayList.class, XXX.class);
          @SuppressWarnings("unchecked")    
          List<XXX> XXX = (ArrayList<XXX>) objectMapper.readValue(json, javaType);

          if (XXX != null) {
            for (XXX XXX : XXX) {
              System.out.println(XXX.toString());
            }
          }
          if (XXX.isEmpty()) {
            System.out.println("response is null");
            fail("response is null");
          }
        } else {
          System.out.println("response status is not 200");
          fail("response status is not 200");
        }
      }
————————————————————
* json转对象
        @Test
        public void testXXX throws Exception {
        String url = "XXX";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set("XXX", "XXX");

        RequestBuilder requestBuilder =
            MockMvcRequestBuilders.get(url).params(params).accept(ACCEPT_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        int status = result.getResponse().getStatus();
        if (status == HttpStatus.SC_OK) {
          String json = result.getResponse().getContentAsString();
          ObjectMapper objectMapper = new ObjectMapper();

          XXX XXX = objectMapper.readValue(json, XX.class);
          if (XXX != null) {
            System.out.println(XXX.toString());
          }
          if (XXX == null) {
            System.out.println("response is null");
            fail("response is null");
          }
        } else {
          System.out.println("response status is not 200");
          fail("response status is not 200");
        }
          }
————————————————————————————————————————————————————————————————————————————————————
* boole 类型的判断
        int status = result.getResponse().getStatus();
        if (status == HttpStatus.SC_OK) {
          String booleStr = result.getResponse().getContentAsString();

          if (booleStr.equals("true")) {
            System.out.println("success");
          } else {
            fail("response is faile");
          }

        } else {
          System.out.println("response status is not 200");
          fail("response status is not 200");
        }
————————————————————————————————————————————————————————————————————————————————————
*  以前的例子
      public class CompositiveQueryControllerTest extends BaseControllerTest {

        private static final String ACCEPT_JSON = "application/json;charset=utf-8";

        // 表示bean自动加载，而不用像之前的两个类要添加一个set的方法
        @Autowired
        private WebApplicationContext wac;
          // 默认格式

        private MockMvc mockMvc;

          // 默认格式
        @Before
        public void setUp() {
          mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        }
          // 默认格式

        @Test
        public void testSave() {
          fail("Not yet implemented");
        }
          // 测试例子

        @Test
        public void testFindPrisoner() throws Exception {
            // 测试FindPrisoner
          MvcResult result =  // 获得测试结果
          mockMvc.perform( // 执行
            MockMvcRequestBuilders.get("/compositiveQuery/findPrisoner") 
                // 执行 get 方法
              .param("number", "330100111201208070111")
                // 传入 number 参数
              .accept(ACCEPT_JSON))
                // 接受返回值类型， application/json;charset=utf-8
              .andReturn();
                // 并且返回
          System.out.println(result.getResponse().getContentAsString());
                // 打印结果里面的得到的返回值的内容 
        }
      }
————————————————————————————————————————————————————————————————————————————————————
* 继承的 ControllerTest 的测试基类

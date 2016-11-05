
* 测试时 返回内容的 相关结构
* Request
    // 框架把结构分开了了 

    MockHttpServletRequest:
          HTTP Method = GET   // 请求方式
          Request URI = /prisoner/findByCardId  // URL
                        // 参数
           Parameters = {cardId=[00000000DED97A20], dormCode=[0204], prisonId=[330211111], isSwipe=[1]} 
                        // Headers
              Headers = {Accept=[application/json;charset=utf-8]}

    Handler:
                        // 拦截以后分配的 控制器
                 Type = com.newings.controller.PrisonerController
                        // 分配处理的方法
               Method = public com.newings.entity.model.PrisonPeopleInfo com.newings.controller.PrisonerController.findMediumByCardId(java.lang.String,java.lang.String,java.lang.String,boolean)

    Async:
        Async started = false
         Async result = null

    Resolved Exception:
                 Type = null

    ModelAndView:
            View name = null
                 View = null
                Model = null

    FlashMap:
           Attributes = null


// 返回的内容
* Response

    MockHttpServletResponse:
                    // 代码
           Status = 200
    Error message = null
                    // 格式要求
          Headers = {Content-Type=[application/json;charset=utf-8]}
                    // 格式
     Content type = application/json;charset=utf-8
                    // Body 里面放的是返回的 json
             Body = {"number":"330211111201607190003","name":"吴长龙","sex":"1","sexValue":"男性","identityId":"330182111111111111","ethnicGroup":"01","ethnicGroupValue":"汉族","dormCode":"0110","prisonId":"330211111","cardId":"00000000DED97A20","unifoNo":"5020","manageType":"0","manageTypeValue":"非重点"}
    Forwarded URL = null
   Redirected URL = null
                    // Cookies
          Cookies = []
HttpServletResponse对象(一)

　　Web服务器收到客户端的http请求，会针对每一次请求，
    分别创建一个用于  "代表请求" 的 request 对象、
                    和"代表响应" 的 response 对象。
    request 和 response 对象即然代表请求和响应，那我们
    要获取客户机提交过来的数据，只需要找request对象就行了。
    要向客户机输出数据，只需要找response对象就行了。
* 创建用户 
    mq.username=nwt-jc
    mq.password=rxJ$7FW0
    // 先设置为用户 再设置为管理员
* 设置为管理员
* 再设置权限
* 就可以登陆 http://localhost:15672 了

* http://jingyan.baidu.com/article/e4d08ffd9ec61c0fd2f60d1f.html
* Rabbit MQ 是建立在强大的 Erlang OTP平台上，因此安装RabbitMQ之前要先安装Erlang。
    erlang：http://www.erlang.org/download.html
    rabbitmq：http://www.rabbitmq.com/download.html

注意：
    1.现在先别装最新的 3.6.3 ，本人在安装完最新的版本，queue 队列有问题，降到了 3.6.2 就解决了。
    2.默认安装的 Rabbit MQ 监听端口是：5672

    安装完以后erlang需要手动设置ERLANG_HOME 的系统变量。

    输入：set ERLANG_HOME=C:\Program Files\erl8.0

    如下图所示：
    图解RabbitMQ安装与配置
2

    激活Rabbit MQ s Management Plugin
    使用Rabbit MQ 管理插件，可以更好的可视化方式查看Rabbit MQ 服务器实例的状态，你可以在命令行中使用下面的命令激活。
    输入：
        rabbitmq-plugins.bat  enable  rabbitmq_management   
    同时，我们也使用 rabbitmqctl 控制台命令
        （位于 rabbitmq_server-3.6.3\sbin>）来创建用户，密码，绑定权限等。
3
    创建管理用户
    输入：
        rabbitmqctl.bat add_user zhangweizhong weizhong1988
4
    设置管理员
    输入：
        rabbitmqctl.bat set_user_tags zhangweizhong administrator
5
    设置权限
    输入：
        rabbitmqctl.bat set_permissions -p / zhangweizhong ".*" ".*" ".*"
6

    其他命令
    查询用户： 
        rabbitmqctl.bat list_users
    查询vhosts： 
        rabbitmqctl.bat list_vhosts
    启动RabbitMQ服务: 
        net stop RabbitMQ && net start RabbitMQ
    
    以上这些，账号、vhost、权限、作用域等基本就设置完了。
    
    END

* Rabbit MQ管理后台
1
    使用浏览器打开
        http://localhost:15672 
    访问Rabbit Mq的管理控制台，使用刚才创建的账号登陆系统即可。

    Rabbit MQ 管理后台，可以更好的可视化方式查看RabbitMQ服务器实例的状态，如下图所示：
    END

* 创建 Vhosts
1
    创建 Vhosts,  在 Admin 页面，点击右侧 Virtual Hosts ，如下图所示：
    图解 RabbitMQ 安装与配置
2
    将刚创建的 OrderQueue 分配给相关用户。
3
    其他创建 exchange ，queue 大家自己在后台创建吧，这里不再赘述。    

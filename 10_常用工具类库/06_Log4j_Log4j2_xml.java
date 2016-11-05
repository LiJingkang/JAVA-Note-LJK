问题描述：

    有时在项目中导入log4j的jar包，并配置log4j.xml或者是log4j2.xml后，
    在代码中引入log4j的API，并不能成功的将log写入控制台或者文件中。
    原因有很多很多种，下面将介绍经过我测试log写入成功的事例。

    在开始前，需要谨记log4j的几种级别： 
        "trace < debug < info < warn < error < fatal"，  
    级别之间是包含的关系，意思是如果你设置日志级别是trace，
    则大于等于这个级别的日志都会输出。

* 成功例子1 
    log4j.xml：

1. 导入log4j2的jar包，主需要导入log4j2的core包即可。
    如： log4j-core.2.1.jar
        <dependency>
              <groupId>org.apache.logging.log4j</groupId>
              <artifactId>log4j-core</artifactId>
              <version>2.1</version>
        </dependency>

2.在classpath中配置log4j.xml，注意不是log4j2.xml,其内容如下

        <?xml version="1.0" encoding="UTF-8"?>
        <!DOCTYPE log4j:configuration SYSTEM "http://logging.apache.org/log4j/1.2/apidocs/org/apache/log4j/xml/doc-files/log4j.dtd">
        <log4j:configuration xmlns:log4j="http://jakarta.apache.org/log4j/" debug="false">
        <!-- Set INFO to WARN for warning-level logging, or DEBUG for debug-level logging. -->
          <!-- Output to terminal by default. -->
          <appender name="terminal" class="org.apache.log4j.ConsoleAppender">
            <param name="Threshold" value="INFO" />
            <param name="Target" value="System.out" />
            <layout class="org.apache.log4j.PatternLayout">
        <!-- param name="ConversionPattern" value="%d{ABSOLUTE} [%t] %-5p [%c{1}] %m%n"/ -->
            <param name="ConversionPattern" value="%-d [%t] %-5p %c:%L - %m%n" />
            </layout>
          </appender>
          <!-- Optional: asynchronous file output (set <appender-ref ref="asynch-file"/> in root below). -->
          <appender name="asynch-file" class="org.apache.log4j.AsyncAppender">
            <param name="locationInfo" value="false" />
            <appender-ref ref="out-file" />
          </appender>
          <!-- Optional: asynchronous file location (set <appender-ref ref="asynch-file"/> in root below). -->
          <appender name="out-file" class="org.apache.log4j.RollingFileAppender">
            <param name="Threshold" value="INFO" />
            <!-- <param name="file" value="${app.logfile}"/> -->
            <param name="file" value="log/automation.log" />
            <param name="MaxFileSize" value="50MB" />
            <param name="MaxBackupIndex" value="50" />
            <layout class="org.apache.log4j.PatternLayout">
          <param name="ConversionPattern" value="%-d [%t] %-5p %c %X{processID}-%X{queryIndex} -  %m  %n" />
            </layout>
          </appender>
          <logger name="com.wsheng.uiautomation.test">
            <level value="INFO" />
          </logger>
          <logger name="com.wsheng.uiautomation.sitestatus">
            <level value="INFO" />
          </logger>
          <root>
            <level value="INFO" />
            <appender-ref ref="terminal" />
            <appender-ref ref="out-file" />
          </root>
        </log4j:configuration>

3. 在项目中写log的代码：

        import org.apache.log4j.Logger;

        protected static Logger logger = Logger.getLogger(BaseTestPlan.class);

    * 需要注意的是此处导入的是 org.apache.log4j.Logger;

    * 如果使用下面的代码， 则不能写入log

        import org.apache.logging.log4j.LogManager;
        import org.apache.logging.log4j.Logger;

        protected static Logger logger = LogManager.getLogger(BaseTestPlan.class);

* 即便是将上面的log4j.xml改为log4j2.xml也不能写入log。

    * 这是因为log4j2.xml和log4j.xml在配置上有区别。

* 成功例子2: 
    log4j2.xml

1. 和log4j.xml一样，也导入log4j2的jar包，主需要导入log4j2的core包即可。
        如： log4j-core.2.1.jar

        <dependency>
             <groupId>org.apache.logging.log4j</groupId>
             <artifactId>log4j-core</artifactId>
             <version>2.1</version>
        </dependency>

2.在classpath中配置log4j2.xml，注意不是log4j.xml,其内容如下

        <?xml version="1.0" encoding="UTF-8"?>
        <Configuration status="WARN">

          <properties>
            <property name="logPath">log</property>
          </properties>
          
          <Appenders>
            <Console name="Console" target="SYSTEM_OUT">
              <PatternLayout pattern="%d{YYYY-MM-dd HH:mm:ss} [%t] %-5p %c{1}:%L - %msg%n" />
              <!-- <PatternLayout pattern="%d{HH:mm:ss} [%t] %-5level %logger{36} - %msg%n" /> -->
              <!-- <PatternLayout pattern="%-d [%t] %-5p %c{1}:%L - %m%n" /> -->
            </Console>
            
          <!-- <File name="LogFile" filename="${sys:catalina.home}/logs/automation.log">
          <PatternLayout pattern="%d{YYYY-MM-dd HH:mm:ss} [%t] %-5p %c{1}:%L - %msg%n" />
            </File> -->
            
            <RollingFile name="RollingFile" filename="${logPath}/automation.log"
              filepattern="${logPath}/%d{YYYYMMddHHmmss}-automation.log">
          <PatternLayout pattern="%d{YYYY-MM-dd HH:mm:ss} [%t] %-5p %c{1}:%L - %msg%n" />
              <Policies>
                <SizeBasedTriggeringPolicy size="100 MB"/>
              </Policies>   
               <DefaultRolloverStrategy max="20"/>
            </RollingFile>
            
          </Appenders>
          <Loggers>
            <Root level="info">
              <AppenderRef ref="Console" />
              <!-- <AppenderRef ref="LogFile" /> -->
              <AppenderRef ref="RollingFile" />
            </Root>
          </Loggers>
        </Configuration>

3. 在项目中写log的代码：

    import org.apache.logging.log4j.LogManager;
    import org.apache.logging.log4j.Logger;

    protected static Logger logger = LogManager.getLogger(BaseTestPlan.class);

* 如果使用 org.apache.log4j.Logger,得到的结果是能写入控制台，
    但不能写入对应的文件。

    import org.apache.log4j.Logger;

    protected static Logger logger = Logger.getLogger(BaseTestPlan.class);


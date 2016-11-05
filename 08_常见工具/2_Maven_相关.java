    * eclipse右键没maven菜单
        * eclipse右键没有maven菜单项目
                右键-Configure-Convert to Maven Project
    * 

——————————————————————————————————————————————————————————————————————————————
* Maven POM文件、多模块以及依赖关系 

一、POM.XML
    1、ProjectObject Model：
                项目对象模型
    2、基本项：
        project：
                pom.xml的顶级元素。
        groupId：
                指出创建这个工程的组织或团队的唯一标识。
        plugins：
                插件。
        artifactId：
                基本名称。
        packaging：
                类型（如JAR、WAR、EAR等等），默认是JAR，
                所有带有子模块的项目的packaging都为pom。
        version：
                版本号。
        modelVersion：
                指出POM使用哪个版本的对象模型。

二、多模块
    1、如何创建
        参考：
            http://www.cnblogs.com/quanyongan/archive/2013/05/28/3103243.html
    2、为什么要分多模块
        软件公司通常的一种做法就是将多个项目构建到主要产品中。
        维护依赖关系链和一次性地构建整个产品足以成为一个挑战，
        但是如果使用Maven的话，事情将变得简单。

        如果您创建了一个指向其它子模块的 pom.xml父文件，
            Maven将为您处理整个构建过程。
            它将分析每个子模块的pom.xml文件，
            并且按照这些子模块的相互依赖顺序来构建项目。

            如果每个项目明确地指明它们的依赖项，
            那么子模块在父文件中的放置顺序是不造成任何影响的。

        但是考虑到其他的开发者，最好保证子模块在 pom.xml 父文件中 的放置顺序和您期望的子项目被构建的顺序一样。

        参考：http://juvenshun.iteye.com/blog/305865

    3、依赖另一个项目的子模块
        A 项目下有 2 个子模块 A1,A2；
        B 项目下有 3 个子模块 B1，B2，B3；
    
        A1 依赖 B1 和 A2 ； A2依赖B2,B3；

        * 则，需要先运行B项目，成功之后再运行A项目。

三、依赖关系
    1、简介
        参看：
            管理依赖
    2、scope参数
        指定依赖项在何种阶段是所需的。

* 如果添加以来的 module 坐标
    直接在当前 module 中的 pom.xml 文件中添加你需要依赖的 module 的坐标。
    这种方式简单，用的也比较多。
        例如：
            <dependencies>
            <dependency>
            <groupId>org.test.autodeploy</groupId>   // com.newings.jc
            <artifactId>org.test.autodeploy</artifactId> // jc-common  依赖的项目
            <version>0.0.1-SNAPSHOT</version>  // 版本号  // 
            </dependency>
            </dependencies>
* pom jar 

——————————————————————————————————————————————————————————————————————————————
* 对于使用maven的骨架创建工程，想必大家都已经熟悉了，这里是一些常用的工程类型，如想看到更多的骨架可以使用mvn的交互式Interactive generate Goal创建指令：mvn archetype:generate


// Creating a simple java application
mvn archetype:create -DgroupId=[your project's group id] -DartifactId=[your project's artifact id]

// Creating a webapp
mvn archetype:create -DgroupId=[your project's group id] -DartifactId=[your project's artifact id] -DarchetypeArtifactId=maven-archetype-webapp

// Creating a site
mvn archetype:create -DgroupId=[your project's group id] -DartifactId=[your project's artifact id] -DarchetypeArtifactId=maven-archetype-site-simple

// Creating a mojo
mvn archetype:create -DgroupId=[your project's group id] -DartifactId=[your project's artifact id] -DarchetypeArtifactId=maven-archetype-mojo

// Creating a portlet
mvn archetype:create -DgroupId=[your project's group id] -DartifactId=[your project's artifact id] -DarchetypeArtifactId=maven-archetype-portlet

 

现在想介绍的是多模块的工程的构建。

 

典型的多模块划分，即按MVC的分层方式来构建多个模块，如工程包括web,business,core3个模块。好我们先看看主工程的pom中应添加些什么，请注意红色文字部分


1.主工程的pom文件中内容：

 

    <modelVersion>4.0.0</modelVersion>
    <groupId>com.your-company.xxxx</groupId>
    <artifactId>xxxx</artifactId>
    <packaging>pom</packaging>
    <version>1.0</version>
    <name>xxxx Project</name>
    <url>http://maven.apache.org</url>

 

    <!-- 工程所包含的模块 -->

    <modules>
        <module>xxxx-core</module>
        <module>xxxx-business</module>
        <module>xxxx-web</module>
    </modules>

 

2.Web模块的pom文件：

 

    <!-- 父级的pom文件位置 -->

    <parent>
        <groupId>com.your-company.xxxx</groupId>
        <artifactId>xxxx</artifactId>
        <version>1.0</version>
        <relativePath>../pom.xml</relativePath>
    </parent>

 

    <groupId>com.your-company.xxxx</groupId>
    <artifactId>xxxx-web</artifactId>
    <packaging>war</packaging>
    <version>1.0</version>
    <name>xxxx-web/name>
    <url>http://maven.apache.org</url>

 

    <dependencies>
        <!-- Application Dependencies -->
        <!-- Web层所依赖的上两层模块 -->

        <dependency>
            <groupId>com.your-company.xxxx</groupId>
            <artifactId>xxxx-core</artifactId>
            <version>${version}</version>
        </dependency>
        <dependency>
            <groupId>com.your-company.xxxx</groupId>
            <artifactId>xxxx-business</artifactId>
            <version>${version}</version>
        </dependency>

        ...

    <dependencies>


3.完成后，mvn eclipse:eclipse后的文件目录为：

 

xxxx

├─xxxx-core

│  ├─pom.xml
│  ├─.settings
│  ├─src
│  │  ├─main
│  │  │  ├─java
│  │  │  └─resources
│  │  └─test
│  │      ├─java
│  │      └─resources
│  └─target
│      ├─classes
│      └─test-classes

├─xxxx-business
│  ├─pom.xml
│  ├─.settings
│  ├─src
│  │  ├─main
│  │  │  ├─java
│  │  │  └─resources
│  │  └─test
│  │      ├─java
│  │      └─resources
│  └─target
│      ├─classes
│      └─test-classes

├─xxxx-web

│  ├─pom.xml
│  ├─.settings
│  ├─src
│  │  ├─main
│  │  │  ├─java
│  │  │  └─resources
│  │  └─test
│  │      ├─java
│  │      └─resources
│  └─target
│      ├─classes
│      └─test-classes

|--pox.xml  

对于使用maven的骨架创建工程，想必大家都已经熟悉了，这里是一些常用的工程类型，如想看到更多的骨架可以使用mvn的交互式Interactive generate Goal创建指令：mvn archetype:generate


// Creating a simple java application
mvn archetype:create -DgroupId=[your project's group id] -DartifactId=[your project's artifact id]

// Creating a webapp
mvn archetype:create -DgroupId=[your project's group id] -DartifactId=[your project's artifact id] -DarchetypeArtifactId=maven-archetype-webapp

// Creating a site
mvn archetype:create -DgroupId=[your project's group id] -DartifactId=[your project's artifact id] -DarchetypeArtifactId=maven-archetype-site-simple

// Creating a mojo
mvn archetype:create -DgroupId=[your project's group id] -DartifactId=[your project's artifact id] -DarchetypeArtifactId=maven-archetype-mojo

// Creating a portlet
mvn archetype:create -DgroupId=[your project's group id] -DartifactId=[your project's artifact id] -DarchetypeArtifactId=maven-archetype-portlet

 

现在想介绍的是多模块的工程的构建。

 

典型的多模块划分，即按MVC的分层方式来构建多个模块，如工程包括web,business,core3个模块。好我们先看看主工程的pom中应添加些什么，请注意红色文字部分


1.主工程的pom文件中内容：

 

    <modelVersion>4.0.0</modelVersion>
    <groupId>com.your-company.xxxx</groupId>
    <artifactId>xxxx</artifactId>
    <packaging>pom</packaging>
    <version>1.0</version>
    <name>xxxx Project</name>
    <url>http://maven.apache.org</url>

 

    <!-- 工程所包含的模块 -->

    <modules>
        <module>xxxx-core</module>
        <module>xxxx-business</module>
        <module>xxxx-web</module>
    </modules>

 

2.Web模块的pom文件：

 

    <!-- 父级的pom文件位置 -->

    <parent>
        <groupId>com.your-company.xxxx</groupId>
        <artifactId>xxxx</artifactId>
        <version>1.0</version>
        <relativePath>../pom.xml</relativePath>
    </parent>

 

    <groupId>com.your-company.xxxx</groupId>
    <artifactId>xxxx-web</artifactId>
    <packaging>war</packaging>
    <version>1.0</version>
    <name>xxxx-web/name>
    <url>http://maven.apache.org</url>

 

    <dependencies>
        <!-- Application Dependencies -->
        <!-- Web层所依赖的上两层模块 -->

        <dependency>
            <groupId>com.your-company.xxxx</groupId>
            <artifactId>xxxx-core</artifactId>
            <version>${version}</version>
        </dependency>
        <dependency>
            <groupId>com.your-company.xxxx</groupId>
            <artifactId>xxxx-business</artifactId>
            <version>${version}</version>
        </dependency>

        ...

    <dependencies>


3.完成后，mvn eclipse:eclipse后的文件目录为：

 

xxxx

├─xxxx-core

│  ├─pom.xml
│  ├─.settings
│  ├─src
│  │  ├─main
│  │  │  ├─java
│  │  │  └─resources
│  │  └─test
│  │      ├─java
│  │      └─resources
│  └─target
│      ├─classes
│      └─test-classes

├─xxxx-business
│  ├─pom.xml
│  ├─.settings
│  ├─src
│  │  ├─main
│  │  │  ├─java
│  │  │  └─resources
│  │  └─test
│  │      ├─java
│  │      └─resources
│  └─target
│      ├─classes
│      └─test-classes

├─xxxx-web

│  ├─pom.xml
│  ├─.settings
│  ├─src
│  │  ├─main
│  │  │  ├─java
│  │  │  └─resources
│  │  └─test
│  │      ├─java
│  │      └─resources
│  └─target
│      ├─classes
│      └─test-classes

|--pox.xml 
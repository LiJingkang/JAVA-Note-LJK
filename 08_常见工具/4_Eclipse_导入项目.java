* 导入新的项目
* 进入新的 workspace
* File -> Import -> General -> Existing Projects into Workspace
	// 在这之前配置好 JDK 
* 等到自动下载依赖包
* Debug as -> Jetter  // Tomcat 再说
* Debug Configurations 
	* Context  // jc
	* WebApp dir	// src/main/webapp
* 项目的编码格式
	Windows -> Preferences -> General -> Wordspace -> Text file encoding -> utf-8
* 文件的编码格式
	// 有的时候我们导入项目文件,并没有导入到我们的workspace,只是一个引用,
		// 这个时候如果有编码的问题,我们可以修改文件的编码查看.
	在Eclipse项目文件上右键,选择Properties,在Resource选择修改编码格式
* .properties 编码
	eclipse的.properties文件中文显示问题
* Fonts
	Windows -> Preferences -> General -> Appearance -> Colors and Fonts
			-> Java -> Java Editor Text Font -> 16
	* 修改 XML 字体
		Xml文件字体大小的调整： 
		window / preferences / General / appearance / colors and fonts / Basic /  "Text font "
			// 然后点击Edit，可以设置字体的大小。
* 控制台字体
	 window --> preferences ---> general ---> Appearance ---> Colors and Fonts
		Basic ---> Text Font
* 配色
	http://eclipse-color-theme.github.com/update  // 插件下载地址
	Color Theme -> Color Theme -> Pastel/Wombat  Oblivion
* 控制台字体
	Colors and Fonts -> Debug -> Console font -> 13
* 自动格式化 包
	http://xieyanhua.iteye.com/blog/1447616
	java -> code style -> formatter -> import 
* 自动格式化  保存
	java -> editor -> Save Actions -> Format Source Code
* 安装jetty
	使用市场
* 修改滚动条
	Jeeeyul s Eclipse Themes 插件  // 不好用，失败
	https://github.com/jeeeyul/eclipse-themes
* 代码提示
	Perferences -> Java -> Editor -> Content Assist 



* 使用IDEA
	* 各种配置
	* 配置JDK
	* 配置maven
	* 配置maven 本地库

————————————————————————————————————————————————————————————————————————————————————————————————————————————
* 导入没有 .classpath 的 eclipse 
	* 自己写.classpath he .project 来导入项目
		* .classpath 一般不需要去特意修改
		* .project 修改项目名称。
		* 导入项目以后相关的依赖和包，以及需要的依赖和关系。
	* IDEA 在 file 里面 可以导出为 eclipse 项目，添加 .classpath  .project 
		* 总项目里面的 pom 文件 里面的 modules 是什么 意思

	* eclipse 中忽略jsp, xml文件中的报错信息 
		// 运行是好的，但是就是报错。可以忽略掉这些信息。
		// 但是将会不再提示
		// 必须使用的时候，给每个项目单独的配置
		Windows-Preferences 输入 validation, 然后选中 validation, disable all 即可. 
	* 

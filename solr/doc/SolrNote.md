# Solr笔记

## 1.1 全文检索技术简介
在一些大型门户网站、电子商务网站等都需要站内搜索功能，使用传统的数据库查询方式实现搜索无法满足一些高级的搜索需求，比如：搜索速度要快、搜索结果按相关度排序、搜索内容格式不固定等，这里就需要使用全文检索技术实现搜索功能。
- 单独使用Lucene实现站内搜索需要开发的工作量较大，主要表现在：索引维护、索引性能优化、搜索性能优化等，因此不建议采用。
- 通过第三方搜索引擎提供的接口实现站内搜索，这样和第三方引擎系统依赖紧密，不方便扩展，不建议采用。
- 基于Solr实现站内搜索扩展性较好，并且可以减少程序员的工作量。因为Solr提供了较为完备的搜索引擎解决方案，因此，在门户、论坛等系统中常用此方案。

## 1.2 Solr简介
- Solr是Apache下的一个顶级开源项目，采用Java开发，它是基于Lucene的全文搜索服务器。Solr提供了比Lucene更为丰富的查询语言，同时实现了可配置、可扩展，并对索引、搜索性能进行了优化。
- Solr可以独立运行，运行在Jetty、Tomcat等Servlet容器中，Solr索引的实现方法很简单，用POST方法向Solr服务器发送一个描述Field及其内容的XML文档，Solr根据XML文档添加、删除、更新索引。Solr搜索只需要发送HTTP GET请求，然后对Solr返回XML、json等格式的查询结果进行解析，组织页面布局。Solr不提供构建UI的功能，Solr提供了一个管理界面，通过管理界面可以查询Solr的配置和运行情况。

## 1.3 Solr示意图
![solr-min](http://www.wailian.work/images/2018/11/16/solr-min.png)

## 1.4 Solr与Lucene区别
- Lucene是一个开放源代码的全文检索引擎工具包，它不是一个完整的全文检索引擎。Lucene提供了完整的查询引擎和索引引擎，目的是为软件开发人员提供一个简单易用的工具包，以便在目标系统中实现全文检索的功能，或者以Lucene为基础构建全文检索引擎。
- Solr的目标是打造一款企业级的搜索引擎系统，它是一个搜索引擎服务，可以独立运行，通过Solr可以非常快速的构建企业的搜索引擎，通过Solr也可以高效地完成站内搜索功能。

## 1.5 Solr下载与安装
- Solr下载地址：[http://archive.apache.org/dist/lucene/solr/](http://archive.apache.org/dist/lucene/solr/)
- 安装Solr与Tomcat集成：Linux环境CentOS6.4、Tomcat7.0、Solr4.10.3
- 安装步骤：
    - 解压Solr：`tar -zxvf solr-4.10.3.tgz.tar`
    - 进入目录：`cd solr-4.10.3/example/webapps/`
    - 拷贝其Fwar文件到Tomcat的webapps中：`cp solr.war /usr/local/apache-tomcat-7.0.29/webapps/`
    - 解压Solr：`mkdir solr`&&`unzip solr.war -d solr`&&`rm -rf solr.war`
    - 修改解压好的solr文件夹，修改其文件：`vim solr/WEB-NF/web.xml`，查找到`nv-entry`内容，解开注释文本。并修改solr/home的地址：`/usr/local/solr-4.10.3/example/solr`。保存并退出即可。
    - 拷贝相关jar包到Tomcat下：`cd /usr/local/solr-4.10.3/example/ib/ext`&&`cp * /usr/local/apache-tomcat-7.0.29/ib/`
    - 启动Tomcat即可：`/usr/local/apache-tomcat-7.0.29/bin/startup.sh`
    - 查看日志：`tail -500 /usr/local/apache-tomcat-7.0.29/logs/catalina.out`
    - 通过浏览器访问：`http:/地址:8080/solr`，看到Solr主页即可。


## References
- Solr全文搜索
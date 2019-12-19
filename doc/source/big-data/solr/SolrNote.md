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
![solr-min](https://www.wailian.work/images/2018/11/16/solr-min.png)

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
    - 解压Solr：`mkdir solr && unzip solr.war -d solr && rm -rf solr.war`
    - 修改解压好的solr文件夹，修改其文件：`vim solr/WEB-NF/web.xml`，查找到`nv-entry`内容，解开注释文本。并修改`solr/home`的地址：`/usr/local/solr-4.10.3/example/solr`。保存并退出即可。
    - 拷贝相关jar包到Tomcat下：`cd /usr/local/solr-4.10.3/example/ib/ext && cp * /usr/local/apache-tomcat-7.0.29/ib/`
    - 启动Tomcat即可：`/usr/local/apache-tomcat-7.0.29/bin/startup.sh`
    - 查看日志：`tail -500 /usr/local/apache-tomcat-7.0.29/logs/catalina.out`
    - 通过浏览器访问：[http://localhost:8080/solr](http://localhost:8080/solr)，看到Solr主页即可。

## 1.6 简单操作Solr服务器页面
打开collection1进行添加数据测试

![solr-documents-min](https://www.wailian.work/images/2018/11/20/solr-documents-min.png)

![solr-query-min](https://www.wailian.work/images/2018/11/20/solr-query-min.png)

## 2.1 Java操作Solr服务器HelloWorld
使用Java可以对Solr服务器进行操作。新建一个Solr的maven项目，对其进行演示。

项目：springMVC + MyBatis + Spring + Solr + MySQL只需要在项目中加入：`solr-solrj-4.10.3.jar`即可。

HelloWorld程序：实现对Solr服务器的信息添加、查询、修改、删除等操作。

通过查看Solr服务器下`schema.xml`文件可知道Solr里面存储的数据类型，路径为：`vim /usr/local/solr-4.10.3/example/solr/collection1/conf/schema.xml`

## 2.2 全文检索基础
1. 信息源->本地（进行加工和处理）->建立索引库（信息集合，一组文件的集合）
1. 搜索时从本地的（索引库）信息集合中搜索
1. 文本在建立索引和搜索时，都会先进行分词（使用分词器）
1. 索引的结构：
    - 索引表（存放具体的词汇，哪些词汇在哪些文档里存储，索引里存储的就是分词器分词之后的结果）
    - 存放数据（文档信息集合）
1. 用户搜索时：首先经过分词器进行分词，然后去索引表里查找对应的词汇（利用倒排序索引），再找到对应的文档集合
1. 索引库位置（Directory）
1. 信息集合里的每一条数据都是一个document（存储所有信息，它是一个Filed属性的集合）
1. store是否进行存储（可以不存储，也可以存储）
1. index是否进行存储（可以不索引，也可以索引。索引分为：分词后索引，或者直接索引）

## 2.3 全文检索基础（IK Analyzer）
- 无论是Solr还是Lucene，对中文分词都不太好，所以，一般索引中文需要使用IK中文分词器。
- 下载：`IK Analyzer 2012FF_hf1.zip`
- 进行解压：`tar IK Analyzer 2012FF_hf1.zip`
- 安装：把`IKAnalyzer2012FF_u1.jar`拷贝到Tomcat的Solr应用服务下：`cd /usr/local/software && cp IKAnalyzer2012FF_u1.jar /usr/local/apache-tomcat-7.0.29/webapps/solr/WEB-INF/lib/`
- 创建`classes`文件夹：`mkdir /usr/local/apache-tomcat-7.0.29/webapps/solr/WEB-INF/classes`
- 把`IKAnalyzer.cfg.xml`和`stopword.dic`拷贝到新创建的`classes`目录下即可。
- 修改Solr core的`schema`文件，默认是：`vim /usr/local/solr-4.10.3/example/solr/collection1/conf/schema.xml`
- 添加如下配置：
	```
	<fieldType name="text_ik" class="solr.TextField">
	<!--索引时候的分词器-->
	<analyzer type="index" isMaxWordLength="false" class="org.wltea.analyzer.lucene.IKAnalyzer"/>
	<!--查询时候的分词器-->
	<analyzer type="query" isMaxWordLength="true" class="org.wltea.analyzer.lucene.IKAnalyzer"/>
	</fieldType>
	```
- 接下来，启动Solr：`/usr/local/apache-tomcat-7.0.29/bin/startup.sh`
- 进入页面在分词器选择ik中文分词器，进行输入：`互联网应用架构`。查看分词结果如图所示：

![solr-analysis-min](https://www.wailian.work/images/2018/11/20/solr-analysis-min.png)

如果想自定义一些词库，让IK分词器可以识别，那么就需要自定义扩展词库了。操作步骤：
1. 修改`/usr/local/apache-tomcat-7.0.29/webapps/solr/WEB-INF/classes/IKAnalyzer.cfg.xml`目录下的`IKAnalyzer.cfg.xml`配置文件，添加如下配置：`<entry key="ext_dict">ext.dic;</entry>`
1. 新建`ext.dic`文件，在里面添加内容：`互联网应用`（注意：`ext.dic`的编码必须是`Encode in UTF-8 without BOM`，否则自定义的词库不会被识别）

![solr-analysis-ext-min](https://www.wailian.work/images/2018/11/20/solr-analysis-ext-min.png)

## 2.4 Solr基础
- 因为Solr包装并扩展了Lucene，所以它们使用很多相同的术语。更重要的是，Solr创建的索引与Lucene搜索引擎库完全兼容。通过对Solr进行适当的配置，某些情况下可能需要进行编码，Solr可以阅读和使用构建到其它Lucene应用程序中的索引。
- 在Solr和Lucene中，使用一个或多个Document来构建索引。Document包括一个或多个Field。Field包括名称、内容以及告诉Solr如何处理内容的元数据。例如，Field可以包含字符串、数字、布尔值或者日期，也可以包含你想添加的任何类型，只需用在Solr的配置文件中进行相应的配置即可。Field可以使用大量的选项来描述，这些选项告诉Solr在索引和搜索期间如何处理内容。

属性名称 | 描述
---|------
`indexed` | indexed Field可以进行搜索和排序。还可以在indexed Field上运行Solr分析过程，此过程可修改内容以改进或更改结果。
`stored` | stored Field内容保存在索引中。这对于检索和醒目显示内容很有用，但对于实际搜索则不是必需的。例如，很多应用程序存储指向内容位置的指针，而不是存储实际的文件内容。

## 2.5 Solr索引操作
- 在Solr中，通过向部署在servlet容器中的Solr Web应用程序发送HTTP请求来启动索引和搜索。Solr接受请求，确定要使用的适当`SolrRequestHandler`，然后处理请求。通过HTTP以同样的方式返回响应。默认配置返回Solr的标准XML响应。你也可以配置Solr的备用响应格式，如json、csv格式的文本。
- 索引就是接受输入元数据（数据格式在`schema.xml`中进行配置）并将它们传递给Solr，从而在HTTP Post XML消息中进行索引的过程。你可以向Solr索引servlet传递四个不同的索引请求。
    - `add/update`允许您向Solr添加文档或更新文档。直到提交后才能搜索到这些添加和更新。
    - `commit`告诉Solr，应该使上次提交以来所做的所有更改都可以搜索到。
    - `optimize`重构Lucene的文件以改进搜索性能。索引完成后执行一下优化通常比较好。如果更新比较频繁，则应该在使用率较低的时候安排优化。一个索引无需优化也可以正常地运行。优化是一个耗时较多的过程。
    - `delete`可以通过`id`或查询来指定。按`id`删除将删除具有指定`id`的文档；按查询删除将删除查询返回的所有文档。
    - Lucene中操作索引也有这几个步骤，但是没有更新。Lucene更新是先删除，然后添加索引。因为更新索引在一定情况下，效率没有先删除后添加的效率好

## 2.6 Solr搜索
添加文档后，就可以搜索这些文档了。Solr接受HTTP GET和HTTP POST查询消息。收到的查询由相应的`SolrRequestHandler`进行处理。

参数 | 描述 | 示例
---|------|------
`q` | 可以通过追加一个分号和已索引且未进行断词的字段（下面会进行解释）的名称来包含排序信息。默认的排序是`score desc`，指按记分降序排序。 | `q=myField:Java AND otherField:developerworks;date desc`此查询按索指定的两个字段，并根据一个日期字段对结果进行排序。
`start` | 将初始偏移里指走到结果生中。可用于对结果进行分页。默认值为`0`。 | `start=15`返回从第15个结果开始的结果。
`rows` | 返回文档的最大数目。默认值为`10`。 | `rows=25`，返回25个结果集
`fq` | 提供一个可选的筛选器查询。查询结果被限制为仅搜索筛选筹查询返回的结果。筛选过的查询由Solr进行缓存。它们对提高复杂查询的速度非常有用。 | 任何可以用`q`参数传递的有效查询，排序信息除外。
`hl` | 当`hl=true`时，在查询响应中醒目显示片段。默认为`false`。 | `hl=true`
`n` | 作为逗号分隔的列表指定文档结果中应返回的Field集。默认为`*`，指所有的字段。`score`指还应返回记分。 | `*`，`score`
`sort` | 排序，对查询结果进行排序 | `sort=date asc,price desc`

## 2.7 Solr模式
之前看过`schema.xml`这个配置，这个配置可以在你下载Solr包的安装解压目录的`apache-solr-3.4.0\example\solr\conf`中找到，它就是Solr模式关联的文件。打开这个配置文件，你会发现有详细的注释。模式组织主要分为三个重要配置：
- `types`部分是一些常见的可重用定义，定义了Solr（和Lucene）如何处理Field。也就是添加到索引中的xml文件属性中的类型，如`int`、`text`、`date`等
- `fileds`是你添加到索引文件中出现的属性名称，而声明类型就需要用到上面的`types`。
- `uniqueKey`唯一键，这里配置的是上面出现的`fileds`，一般是`id`、`url`等不重复的。在更新、删除时可以用到。
- `defaultSearchField`默认搜索属性，如`q=solr`就是默认的搜索那个字段
- `solrQueryParser`查询转换模式，是并且还是或者（`and/or`）

- 在`schema.xml`中，下面的就是一个type，配置`field`时可以用这个type。
    ```
    <fieldType name="text_ws" class="solr.TextField" positionIncrementGap="100">
      <analyzer>
        <tokenizer class="solr.WhitespaceTokenizerFactory"/>
      </analyzer>
    </fieldType>
    ```
- 上面的`fieldType`的配置中有个analyzer，它是分词器。主要把我们的数据进行分割成一个个的词语。词干提取、停止词删除以及相似的操作都被应用于标记，然后才进行索引和搜索，导致使用相同类型的标记。
- 在`schema.xml`中，Solr定义一些`field`内容，应用程序在添加`filed`时，如果出现`ERROR:unknown field 'xxxx'`，就表示设置的这个`field`在`schema.xml`中不存在，如果一定要使用这个`field`，请在`schema.xml`中进行`filed`元素的配置。
	```
	<field name="sku" type="text_en_splitting_tight" indexed="true" stored="true" omitNorms="true"/>
	<field name="name" type="text_general" indexed="true" stored="true"/>
	<field name="manu" type="text_general" indexed="true" stored="true" omitNorms="true"/>
	<field name="cat" type="string" indexed="true" stored="true" multiValued="true"/>
	<field name="features" type="text_general" indexed="true" stored="true" multiValued="true"/>
	<field name="includes" type="text_general" indexed="true" stored="true" termVectors="true" termPositions="true" termOffsets="true" />
	```

## 2.8 Solr添加信息对象
- 我们知道如果想要使数据加入到Solr服务器中，在`schema.xml`必须要存在与其对应的Filed标签声明，如果我们要添加一个对象（比如用户对象）每次去一个一个的`setFiled`，这样比较麻烦，Solr允许使JavaBean的方式，将一个对象直接保存到Solr服务器中。
- 首先，在`schema.xml`中定义自己的`User`对象：
	```
	<field name="user.name” type="text general" indexed="true" stored="true" />
	<field name="user.sex" type="text general" indexed="true" stored="true" />
	<field name="user.age” type="text general" indexed="false" stored="true" />
	<field name="user.like" type="text general" indexed="true" stored="true" multivalued="true" />
	```
- 然后，在Java中建立实体对象，并且一定要在属性或`set`方法上添加`@Field(nameValue)`注解。（`org.apache.solr.client.solj.beans.Field`）
- 最后，在junit里面进行测试。

## 2.9 Solr查询详细使用
Solr可以对查询数据进行一系列的操作。
- 查询分页`start`（起始位置）、`rows`（数据条数）、`sort`（排序）
- 查询操作拼接：可使用`AND`、`OR`、`NOT`进行拼接联合查询
- 查询操作区间：`price:[5 TO 10]`表示包含，`price:{5 TO 10}`为不包含5和10的数据
- 查询操作过滤器：`addFilterQuery`提高查询效率
- 查询操作开启高亮设置：`setHighlight*`等方法，可以高亮显示结果信息。
- 查询操作分片处理：可以进行统计分析单词出现次数。

## 3.1 Solr管理员命令
在生产环境时，需要管理员维护Solr服务器的数据信息。那么两种主要的手段：
1. 直接使用curl命令进行操作Solr数据，如：
    - 删除：`curl http://localhost:8080/solr/update --data-binary "<delete><query>name:abc</query></delete>" -H 'Content-type:text/xml;charset=utf-8'`
    - 提交：`curl http://localhost:8080/solr/update --data-binary "<commit/>" -H 'Content-type:text/xml;charset=utf-8'`
1. 进入：`/usr/locallsolr-4.10.3/example/exampledocs`下执行`java -jar post.jar`命令进行维护数据操作：
    - 删除：`java -Ddata=args -jar postjar "<delete><id>42</d></delete>"`
    - 帮助：`java jar post.jar -help`

## 3.2 Solr集群搭建
Solr性能不用说，一定是非常好的。那么，针对于互联网大数据方向的搜索，海量数据进行索引，Solr一般使用集群环境，利用ZooKeeper去协调，参考相关文档对Solr进行集群搭建：
- 参考SolrCloud高可用集群搭建手册。
- [Solr安装-Tomcat+SolrCloud构建稳健Solr集群](http://blog.csdn.net/xyls12345/article/details/27504965)
- [SolrCloud 分布式集群部署步骤](http://segmentfault.com/a/1190000000595712)

## 4.x 案例实战 
- [Spring Boot整合Solr](SolrSpringBoot.md)

## References
- 尚学堂互联网架构师课程
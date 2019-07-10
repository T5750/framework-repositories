# Solr网摘笔记

## Spring Boot整合Solr
1. `build.gradle`引入jar包
	```
	compile group: 'org.springframework.data', name: 'spring-data-solr', version: '2.1.3.RELEASE'
	```
1. `application.yml`配置
	```
	spring:
	  data:
		solr:
		  host: http://127.0.0.1:8080/solr
	```
1. `SolrApplication`，`SolrController`

### 删除操作
Solr Admin删除时，需要使用XML的方式操作，且必须加上`commit`
- 根据`id`进行删除
	```
	<delete>
	　　<id>1</id>
	</delete>
	<commit />
	```
- 删除所有索引
	```
	<delete>
	　　<query>*:*</query>
	</delete>
	<commit />
	```

## Results
- [http://localhost:8081/solr/solr/add](http://localhost:8081/solr/solr/add)
- [http://localhost:8081/solr/solr/getById?id=1](http://localhost:8081/solr/solr/getById?id=1)
- [http://localhost:8081/solr/solr/addMobile](http://localhost:8081/solr/solr/addMobile)
- [http://localhost:8081/solr/solr/search](http://localhost:8081/solr/solr/search)
- [http://localhost:8081/solr/solr/delete?id=1](http://localhost:8081/solr/solr/delete?id=1)
- [http://localhost:8081/solr/solr/deleteAll](http://localhost:8081/solr/solr/deleteAll)

## References
- [springboot 整合 solr](https://www.cnblogs.com/elvinle/p/8149256.html)
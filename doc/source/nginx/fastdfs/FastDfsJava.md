# 使用Spring Boot集成FastDFS

## 使用maven从源码安装
- `git clone https://github.com/happyfish100/fastdfs-client-java.git`
- `mvn clean install`

## 配置&集成
- `compile group: 'org.csource', name: 'fastdfs-client-java', version: '1.27-SNAPSHOT'`
- 配置文件：`fdfs_client.conf`
	```
	connect_timeout = 60
	network_timeout = 60
	charset = UTF-8
	http.tracker_http_port = 8000
	http.anti_steal_token = no
	http.secret_key = 123456
	
	tracker_server = 192.168.100.110:22122
	```
- 封装FastDFS上传工具类：`FastDFSFile`
- `FastDFSApplication`

## References
- [使用Spring Boot集成FastDFS](http://ityouknow.com/springboot/2018/01/16/spring-boot-fastdfs.html)
- [FastDFS java client SDK](https://github.com/happyfish100/fastdfs-client-java)
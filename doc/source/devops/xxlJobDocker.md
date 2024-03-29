# XXL-JOB Docker

XXL-JOB是一个分布式任务调度平台，其核心设计目标是开发迅速、学习简单、轻量级、易扩展。现已开放源代码并接入多家公司线上产品线，开箱即用。

## Docker
```
/**
* 如需自定义 mysql 等配置，可通过 "-e PARAMS" 指定，参数格式 PARAMS="--key=value  --key2=value2" ；
* 配置项参考文件：/xxl-job/xxl-job-admin/src/main/resources/application.properties
* 如需自定义 JVM内存参数 等配置，可通过 "-e JAVA_OPTS" 指定，参数格式 JAVA_OPTS="-Xmx512m" ；
*/
docker run -e PARAMS="--spring.datasource.url=jdbc:mysql://127.0.0.1:3306/xxl_job?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&serverTimezone=Asia/Shanghai" -p 8080:8080 -v /tmp:/data/applogs --name xxl-job-admin  -d xuxueli/xxl-job-admin:{指定版本}
```

## Docker Compose
- `tables_xxl_job.sql`
- `xxl-job.yml`
- [http://localhost:8080/](http://localhost:8080/)
- User: admin, 123456

## Architecture
![](https://github.com/xuxueli/xxl-job/blob/master/doc/images/img_Qohm.png?raw=true)

## Screenshots
![](https://github.com/xuxueli/xxl-job/blob/master/doc/images/img_Hr2T.png?raw=true)

![](https://github.com/xuxueli/xxl-job/blob/master/doc/images/img_o8HQ.png?raw=true)

![](https://github.com/xuxueli/xxl-job/blob/master/doc/images/img_6yC0.png?raw=true)

## References
- [xxl-job GitHub](https://github.com/xuxueli/xxl-job/)
- [分布式任务调度平台XXL-JOB](https://www.xuxueli.com/xxl-job/#%E3%80%8A%E5%88%86%E5%B8%83%E5%BC%8F%E4%BB%BB%E5%8A%A1%E8%B0%83%E5%BA%A6%E5%B9%B3%E5%8F%B0XXL-JOB%E3%80%8B)
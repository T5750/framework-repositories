# Neutrino-Proxy Docker

一个基于Netty的开源内网穿透神器

## Docker
### 部署服务端
使用默认h2数据库一键部署
```sh
docker run -it -p 9000-9200:9000-9200/tcp -p 8888:8888 \
    -d --restart=always --name neutrino-proxy \
    -v /root/neutrino-proxy-server/config:/root/neutrino-proxy/config \
    -v /root/neutrino-proxy-server/data:/root/neutrino-proxy/data \
    -v /root/neutrino-proxy-server/logs:/root/neutrino-proxy/logs \
    aoshiguchen/neutrino-proxy-server:latest
docker run -d --name neutrino-proxy -p 9000-9200:9000-9200/tcp -p 8888:8888 aoshiguchen/neutrino-proxy-server
```
- [http://localhost:8888/](http://localhost:8888/)
- User: admin / 123456

指定自己的mysql数据库
- 在服务器上创建目录：/root/neutrino-proxy/config
- 在该目录下创建`app.yml`文本文件，并配置如下内容：

```
neutrino:
  data:
    db:
      type: mysql
      # 自己的数据库实例，创建一个空的名为'neutrino-proxy'的数据库即可，首次启动服务端会自动初始化
      url: jdbc:mysql://xxxx:3306/neutrino-proxy?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&useAffectedRows=true&useSSL=false
      driver-class: com.mysql.jdbc.Driver
      # 数据库帐号
      username: xxx
      # 数据库密码
      password: xxx
```

### 部署客户端
```sh
docker run -it -d --restart=always --name npclient -e SERVER_IP=xxxx -e LICENSE_KEY=xxxx \
aoshiguchen/neutrino-proxy-client:latest
```

## Runtime Environment
- [Java 21](https://github.com/openjdk/jdk)
- [Vue.js](https://github.com/vuejs/vue)

## Screenshots
![](https://gitee.com/dromara/neutrino-proxy/raw/master/assets/run-example/home.png)

![](https://gitee.com/dromara/neutrino-proxy/raw/master/assets/run-example/port-mapping1.png)

![](https://gitee.com/dromara/neutrino-proxy/raw/master/assets/design/neutrino-proxy-process.jpg)

## References
- [Neutrino-Proxy](https://neutrino-proxy.dromara.org/neutrino-proxy/)
- [Neutrino-Proxy GitHub](https://github.com/dromara/neutrino-proxy)
- [Neutrino-Proxy Docker](https://neutrino-proxy.dromara.org/neutrino-proxy/pages/5b9bef/)
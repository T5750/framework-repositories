# Canal Docker

## Docker
```
docker run --name canal-server -d -p 2222:2222 -p 11110:11110 -p 11111:11111 -p 11112:11112 canal/canal-server:v1.1.5
```

## Canal Admin
```
docker run --name canal-admin -d -p 8089:8089 canal/canal-admin:v1.1.5
```
- http://127.0.0.1:8089/
- User: admin / 123456
- Server 管理 -> 新建Server
    + admin 端口: `11110`
- Instance 管理 -> 新建 Instance
    + Instance 名称: `example`
    + 载入模板

![](https://img-blog.csdnimg.cn/20191104101735947.png)

## 工作原理
### MySQL主备复制原理
![](http://dl.iteye.com/upload/attachment/0080/3086/468c1a14-e7ad-3290-9d3d-44ac501a7227.jpg)

- MySQL master 将数据变更写入二进制日志( binary log, 其中记录叫做二进制日志事件binary log events，可以通过 show binlog events 进行查看)
- MySQL slave 将 master 的 binary log events 拷贝到它的中继日志(relay log)
- MySQL slave 重放 relay log 中事件，将数据变更反映它自己的数据

### canal 工作原理
- canal 模拟 MySQL slave 的交互协议，伪装自己为 MySQL slave ，向 MySQL master 发送dump 协议
- MySQL master 收到 dump 请求，开始推送 binary log 给 slave (即 canal )
- canal 解析 binary log 对象(原始为 byte 流)

## 多语言
canal 特别设计了 client-server 模式，交互协议使用 protobuf 3.0 , client 端可采用不同语言实现不同的消费逻辑
- canal java 客户端: [https://github.com/alibaba/canal/wiki/ClientExample](https://github.com/alibaba/canal/wiki/ClientExample)
- canal c# 客户端: [https://github.com/dotnetcore/CanalSharp](https://github.com/dotnetcore/CanalSharp)
- canal go客户端: [https://github.com/CanalClient/canal-go](https://github.com/CanalClient/canal-go)
- canal php客户端: [https://github.com/xingwenge/canal-php](https://github.com/xingwenge/canal-php)
- canal Python客户端：[https://github.com/haozi3156666/canal-python](https://github.com/haozi3156666/canal-python)
- canal Rust客户端：[https://github.com/laohanlinux/canal-rs](https://github.com/laohanlinux/canal-rs)

## Screenshots
![](https://camo.githubusercontent.com/626b23a038986a83fac0765fc2d15229b031b9770b84a1d367879b7ff20c4fd9/687474703a2f2f646c322e69746579652e636f6d2f75706c6f61642f6174746163686d656e742f303133322f323331382f61306566393430662d663739382d333233332d393831342d6661316337616539313236662e706e67)

## References
- [Docker QuickStart](https://github.com/alibaba/canal/wiki/Docker-QuickStart)
- [Canal Admin Docker](https://github.com/alibaba/canal/wiki/Canal-Admin-Docker)
- [Canal Admin 创建Instance的bug问题](https://github.com/alibaba/canal/issues/2847)
# frp Docker

简单、高效的内网穿透工具

## 原理
frp 主要由 **客户端(frpc)** 和 **服务端(frps)** 组成，服务端通常部署在具有公网 IP 的机器上，客户端通常部署在需要穿透的内网服务所在的机器上。

## Docker
start frps
```sh
docker run --restart=always --network host -d -v /etc/frp/frps.ini:/etc/frp/frps.ini --name frps snowdreamtech/frps
```
start frpc
```sh
docker run --restart=always --network host -d -v /etc/frp/frpc.ini:/etc/frp/frpc.ini --name frpc snowdreamtech/frpc
```

## 通过 SSH 访问内网机器
`vi frps.ini`
```
[common]
bind_port = 7000
```
`vi frpc.ini`
```
[common]
server_addr = x.x.x.x
server_port = 7000

[ssh]
type = tcp
local_ip = 127.0.0.1
local_port = 22
remote_port = 6000
```
```sh
ssh -oPort=6000 sysadmin@x.x.x.x
```

## Dashboard
### 服务端 Dashboard
`vi frps.ini`
```
[common]
dashboard_port = 7500
# dashboard 用户名密码，可选，默认为空
dashboard_user = admin
dashboard_pwd = admin
```
[http://localhost:7500/](http://localhost:7500/)

### 客户端 Dashboard
`vi frpc.ini`
```
[common]
admin_addr = 127.0.0.1
admin_port = 7400
admin_user = admin
admin_pwd = admin
```
[http://localhost:7400/](http://localhost:7400/)

## Runtime Environment
- [Go](https://golang.org/)

## Architecture
![](https://github.com/fatedier/frp/blob/dev/doc/pic/architecture.png)

## Screenshots
![](https://github.com/fatedier/frp/blob/dev/doc/pic/dashboard.png)

## References
- [frp](https://gofrp.org/)
- [frp GitHub](https://github.com/fatedier/frp)
- [snowdreamtech/frp Docker](https://github.com/snowdreamtech/frp)
- [frp 通过 SSH 访问内网机器](https://gofrp.org/docs/examples/ssh/)
- [frp Web 界面](https://gofrp.org/docs/features/common/ui/)
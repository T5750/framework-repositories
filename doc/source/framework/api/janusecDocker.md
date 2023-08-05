# JANUSEC Docker

JANUSEC应用网关，提供安全的接入，包括反向代理、K8S Ingress Controller、ACME自动化HTTPS证书、WAF (Web Application Firewall)、CC防御、OAuth2身份认证、负载均衡等

## Docker
```sh
docker run -d --privileged=true --restart=always -p 80:80 -p 443:443 -p 9080:9080 -p 9443:9443 registry.cn-shenzhen.aliyuncs.com/janusec/janusec:1.3.1 /sbin/init
docker run -d --name janusec --privileged=true --restart=always -p 9080:9080 registry.cn-shenzhen.aliyuncs.com/janusec/janusec:1.3.1 /sbin/init
```
- [http://localhost:9080/janusec-admin/](http://localhost:9080/janusec-admin/)
- User: admin / J@nusec123

## Runtime Environment
- [Go](https://golang.org/)

## Screenshots
![](https://doc.janusec.com/images/gateway1.png)

![](https://doc.janusec.com/images/screenshot-cert.png)

![](https://doc.janusec.com/images/application2.png)

## References
- [JANUSEC](https://doc.janusec.com/)
- [JANUSEC GitHub](https://github.com/Janusec/janusec)
- [JANUSEC Docker](https://doc.janusec.com/cn/appendix-docker/)
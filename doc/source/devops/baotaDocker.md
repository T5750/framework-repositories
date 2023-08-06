# Baota Docker

安全高效的服务器运维面板

## Demo
[查看演示](https://demo.bt.cn/login)

## Centos
```sh
yum install -y wget && wget -O install.sh https://download.bt.cn/install/install_6.0.sh && sh install.sh ed8484bec
```

## Ubuntu
```sh
wget -O install.sh https://download.bt.cn/install/install-ubuntu_6.0.sh && sudo bash install.sh ed8484bec
```

## Docker
```sh
docker run -itd --net=host --name ubuntu18 ubuntu:18.04
docker exec -it ubuntu18 bash
apt-get update && apt-get install -y wget && apt-get install -y vim
wget -O install.sh https://download.bt.cn/install/install-ubuntu_6.0.sh && bash install.sh ed8484bec
bt
8 # 改面板端口
8888
```

### 最快化部署
```sh
docker run -d --net=host --restart=always --name baota cyberbolt/baota:latest -port 8888 -username cyberbolt -password abc123456
```
[http://localhost:8888/](http://localhost:8888/)

### 生产环境部署
```sh
docker run -itd --net=host --name baota-test cyberbolt/baota -port 26756 -username cyberbolt -password abc123456
docker cp baota-test:/www /www
docker stop baota-test && docker rm baota-test
docker run -d -v /www:/www --net=host --restart=always --name baota cyberbolt/baota -port 8888 -username cyberbolt -password abc123456
```

## Screenshots
![](https://www.bt.cn/static/new/images/linux/home.png)

## References
- [宝塔](https://www.bt.cn/new/index.html)
- [Cyberbolt/baota GitHub](https://github.com/Cyberbolt/baota)
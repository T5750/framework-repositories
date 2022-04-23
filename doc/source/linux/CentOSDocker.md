# CentOS Docker

## Docker
```
docker pull centos:7
docker pull centos:7.9.2009
docker pull centos:8
docker pull centos:8.4.2105
docker run -d --name centos7 --privileged=true -p 10022:22 -p 10080:80 centos:7 /usr/sbin/init
```

## Runtime Environment
- [CentOS 7](https://www.centos.org/download/)
- [CentOS 8](https://www.centos.org/download/)

## References
- [CentOS Docker](https://hub.docker.com/_/centos)
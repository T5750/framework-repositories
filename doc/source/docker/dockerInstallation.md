# Docker Installation

## Install on CentOS

### Prerequisites
To install Docker Engine, you need a maintained version of CentOS 7

### Installation methods

#### Install using the repository
```
yum install -y yum-utils
yum-config-manager --add-repo https://mirrors.tuna.tsinghua.edu.cn/docker-ce/linux/centos/docker-ce.repo
```
Install Docker Engine
1. `yum install docker-ce docker-ce-cli containerd.io`
2. `yum list docker-ce --showduplicates | sort -r`
3. `systemctl start docker`
4. `docker run hello-world`

```
systemctl stop docker
```

#### Install from a package
```
#curl https://download.docker.com/linux/centos/docker-ce.repo -o /etc/yum.repos.d/docker-ce.repo
curl https://mirrors.tuna.tsinghua.edu.cn/docker-ce/linux/centos/docker-ce.repo -o /etc/yum.repos.d/docker-ce.repo
yum install https://mirrors.tuna.tsinghua.edu.cn/docker-ce/linux/centos/7/x86_64/stable/Packages/containerd.io-1.2.13-3.2.el7.x86_64.rpm
```

### Uninstall Docker Engine
1. `yum remove docker-ce docker-ce-cli containerd.io`
2. `rm -rf /var/lib/docker`

## References
- [CentOS Docker 安装](https://www.runoob.com/docker/centos-docker-install.html)
- [Install Docker Engine on CentOS](https://docs.docker.com/engine/install/centos/)

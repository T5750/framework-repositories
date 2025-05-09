# Docker Installation

## Install on CentOS

### Prerequisites
To install Docker Engine, you need a maintained version of CentOS 7

### Installation methods

#### Install using the repository
```sh
sudo yum install -y yum-utils
#yum-config-manager --add-repo https://mirrors.tuna.tsinghua.edu.cn/docker-ce/linux/centos/docker-ce.repo
sudo yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
```
Install Docker Engine
1. `sudo yum install -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin`
    - Or: `sudo yum install -y docker-ce docker-ce-cli containerd.io`
    - Or: `sudo yum install -y --allowerasing docker-ce docker-ce-cli containerd.io`
2. `sudo systemctl start docker`
3. `docker run hello-world`

```sh
sudo systemctl stop docker
# Specific version
sudo yum list docker-ce --showduplicates | sort -r
sudo yum install docker-ce-<VERSION_STRING> docker-ce-cli-<VERSION_STRING> containerd.io docker-buildx-plugin docker-compose-plugin
```

#### Install from a package
```sh
#curl https://download.docker.com/linux/centos/docker-ce.repo -o /etc/yum.repos.d/docker-ce.repo
curl https://mirrors.tuna.tsinghua.edu.cn/docker-ce/linux/centos/docker-ce.repo -o /etc/yum.repos.d/docker-ce.repo
yum install https://mirrors.tuna.tsinghua.edu.cn/docker-ce/linux/centos/7/x86_64/stable/Packages/containerd.io-1.2.13-3.2.el7.x86_64.rpm
```

### Uninstall Docker Engine
1. `yum remove docker-ce docker-ce-cli containerd.io`
2. `rm -rf /var/lib/docker`

## Tips
CentOS 8 Errors: 
- Problem 1: problem with installed package podman-1.4.2-5.module_el8.1.0+237+63e26edc.x86_64
- Problem 2: problem with installed package buildah-1.9.0-5.module_el8.1.0+237+63e26edc.x86_64

```sh
rpm -q podman
sudo dnf remove podman
rpm -q buildah
sudo dnf remove buildah
```
```sh
sudo systemctl enable docker
sudo mkdir /etc/docker
sudo vi /etc/docker/daemon.json
{
  "registry-mirrors": ["https://docker.mirrors.ustc.edu.cn/"],
  "log-driver": "json-file",
  "log-opts": {"max-size":"500m", "max-file":"3"},
  "dns": ["8.8.8.8","114.114.114.114"]
}
sudo systemctl daemon-reload
sudo systemctl restart docker
```

### Util
```sh
#停止所有容器
docker stop $(docker ps -aq)
```

### Docker Registry
- docker.io: Docker Hub 官方镜像仓库
- gcr.io、k8s.gcr.io: 谷歌镜像仓库
- quay.io: Red Hat 镜像仓库
- ghcr.io: GitHub 镜像仓库

### 修改默认存储路径
```sh
docker info
#默认目录：Docker Root Dir: /var/lib/docker
systemctl stop docker.service
mkdir -p /home/docker/
#使用 rsync 替代 cp 解决断点续传问题
rsync -avz /var/lib/docker/* /home/docker/
vi /etc/docker/daemon.json
```
```
{
"data-root": "/home/docker"
}
```
```sh
systemctl start docker
docker info
df -h
rm -rf /var/lib/docker
```

## References
- [Install Docker Engine on CentOS](https://docs.docker.com/engine/install/centos/)
- [CentOS Docker 安装](https://www.runoob.com/docker/centos-docker-install.html)
- [Docker限制容器日志大小](https://www.cnblogs.com/angel-devil/p/12558908.html)
- [修改Docker默认存储路径，解决系统盘占用90%+问题(修改docker root dir)](https://cloud.tencent.com/developer/article/2452495)
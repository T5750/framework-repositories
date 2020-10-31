# Docker One Hour

Docker是一个改进的容器技术。具体的“改进”体现在，Docker为容器引入了镜像，使得容器可以从预先定义好的模版（images）创建出来，并且这个模版还是分层的

## 特点
- 轻量，体现在内存占用小，高密度
- 快速，毫秒启动
- 隔离，沙盒技术更像虚拟机

## 常见命令

### 容器操作
- `docker create`: 创建一个容器但是不启动它
- `docker run`: 创建并启动一个容器
- `docker stop`: 停止容器运行，发送信号SIGTERM
- `docker start`: 启动一个停止状态的容器
- `docker restart`: 重启一个容器
- `docker rm`: 删除一个容器
- `docker kill`: 发送信号给容器，默认SIGKILL
- `docker kill $(docker ps -q)`: 杀掉所有正在运行的容器
- `docker attach`: 连接(进入)到一个正在运行的容器
- `docker wait`: 阻塞到一个容器，直到容器停止运行
- `docker ps`: 显示状态为运行（Up）的容器
- `docker ps -a`: 显示所有容器，包括运行中（Up）的和退出的(Exited)
- `docker inspect`: 深入容器内部获取容器所有信息
- `docker logs`: 查看容器的日志(stdout/stderr)
- `docker events`: 得到docker服务器的实时的事件
- `docker port`: 显示容器的端口映射
- `docker top`: 显示容器的进程信息
- `docker diff`: 显示容器文件系统的前后变化
- `docker cp`: 从容器里向外拷贝文件或目录
- `docker export`: 将容器整个文件系统导出为一个tar包，不带layers、tag等信息
- `docker exec`: 在容器里执行一个命令，可以执行bash进入交互式
- `docker exec container_id env`: 获取环境变量

获取Container IP地址（Container状态必须是Up）
```
docker inspect id | grep IPAddress | cut -d '"' -f 4
```
获取端口映射
```
docker inspect -f '{{range $p, $conf := .NetworkSettings.Ports}} {{$p}} -> {{(index $conf 0).HostPort}} {{end}}' id
```

### 镜像操作
- `docker images`: 显示本地所有的镜像列表
- `docker import`: 从一个tar包创建一个镜像，往往和export结合使用
- `docker build`: 使用Dockerfile创建镜像（推荐）
- `docker commit`: 从容器创建镜像
- `docker rmi`: 删除一个镜像
- `docker load`: 从一个tar包创建一个镜像，和save配合使用
- `docker save`: 将一个镜像保存为一个tar包，带layers和tag信息
- `docker history`: 显示生成一个镜像的历史命令
- `docker tag`: 为镜像起一个别名
- `docker login`: 登录到一个registry
- `docker search`: 从registry仓库搜索镜像
- `docker pull`: 从仓库下载镜像到本地
- `docker push`: 将一个镜像push到registry仓库中

### 删除操作
删除老的(一周前创建)容器
```
docker ps -a | grep 'weeks ago' | awk '{print $1}' | xargs docker rm
```
删除已经停止的容器
```
docker rm `docker ps -a -q`
```
删除所有镜像，小心
```
docker rmi $(docker images -q)
```

## Dockerfile
正是有了Dockerfile，docker的自动化和可移植性才成为可能
- `FROM`: 从一个基础镜像构建新的镜像
- `MAINTAINER`: 维护者信息
- `ENV`: 设置环境变量
- `RUN`: 非交互式运行shell命令
- `ADD`: 将外部文件拷贝到镜像里，src可以为url
- `WORKDIR /path/to/workdir`: 设置工作目录
- `USER`: 设置用户ID
- `VULUME <#dir>`: 设置volume
- `EXPOSE`: 暴露哪些端口
- `ENTRYPOINT [‘executable’, ‘param1’,’param2’]`: 执行命令
- `CMD [“param1”,”param2”]`

## References
- [一小时Docker教程](https://blog.csphere.cn/archives/22)
# FastDFS笔记

## 1.1 FastDFS简介
- FastDFS是一个开源的轻量级分布式文件系统，它对文件进行管理，功能包括：文件存储、文件同步、文件访问（文件上传、文件下载）等，解决了大容量存储和负载均衡的问题。特别适合以文件为载体的在线服务，如相册网站、视频网站等
- FastDFS是为互联网应用量身定做的一套分布式文件存储系统，非常适合用来存储用户图片、视频、文档等文件。对于互联网应用，和其它分布式文件系统相比，优势非常明显
- 官方学习网站：[http://www.csource.org/](http://www.csource.org/)
- 软件包下载地址：[https://sourceforge.net/projects/fastdfs/files/](https://sourceforge.net/projects/fastdfs/files/)
- 源码包下载地址：[https://github.com/happyfish100/fastdfs](https://github.com/happyfish100/fastdfs)

## 1.2 单节点安装
单个节点安装需要tracker、storage，以及使用Http访问，需要集成nginx模块

## 1.3 FastDFS安装
1. 安装之前首先Linux上要有gcc包：`yum install make cmake gcc gcc-c++`
1. 安装`libfastcommon`
1. 安装FastDFS
1. 配置跟踪器（tracker）
1. 配置存储器（storage）
1. 环境搭建完成，可以进行测试环境是否搭建成功
1. 跟踪器和存储器安装nginx，之前已经安装完毕了，可以对nginx进行整合，实现使用浏览器下载文件

启动和关闭服务顺序：跟踪器、存储器、nginx

## 2.1 Java操作FastDFS
- 使用Java操作FastDFS非常简单，可以下载官方的源码包，自行进行打包，然后应用到项目中去
- 下载：`fastdfs_client_v1.24.jar`
- 封装通用的上传下载工具类，进行上传、下载、删除等操作测试

## 3.1 FastDFS系统结构图
![FastDfsCluster-min](https://s0.wailian.download/2019/02/03/FastDfsCluster-min.jpg)

## 3.2 上传文件交互过程
![FastDFS-file-upload-min](https://s0.wailian.download/2019/02/03/FastDFS-file-upload-min.jpg)

1. client询问tracker上传到的storage，不需要附加参数；
2. tracker返回一台可用的storage；
3. client直接和storage通讯完成文件上传。

## 3.3 下载文件交互过程
![FastDFS-file-download-min](https://s0.wailian.download/2019/02/03/FastDFS-file-download-min.jpg)

1. client询问tracker下载文件的storage，参数为文件标识（卷名和文件名）；
2. tracker返回一台可用的storage；
3. client直接和storage通讯完成文件下载。

## 3.4 FastDFS集群环境
![FastDfsClusterBuild-min](https://s0.wailian.download/2019/02/03/FastDfsClusterBuild-min.jpg)

## 3.5 FastDFS集群+nginx负载均衡
![FastDfsCluster-min-min](https://s0.wailian.download/2019/03/12/FastDfsCluster-min-min.png)

## References
- [FastDFS GitHub](https://github.com/happyfish100/fastdfs)
- [分布式文件系统（FastDFS）](http://bbs.chinaunix.net/forum-240-1.html)
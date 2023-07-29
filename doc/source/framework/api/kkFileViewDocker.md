# kkFileView Docker

kkFileView为文件文档在线预览解决方案，该项目使用流行的spring boot搭建，易上手和部署，基本支持主流办公文档的在线预览，如doc,docx,xls,xlsx,ppt,pptx,pdf,txt,zip,rar,图片,视频,音频等

## Demo
[在线体验](https://file.kkview.cn/)

## Docker
```sh
# 网络环境方便访问docker中央仓库
docker pull keking/kkfileview:4.1.0
# 网络环境不方便访问docker中央仓库
wget https://kkview.cn/resource/kkFileView-4.1.0-docker.tar
docker load -i kkFileView-4.1.0-docker.tar
```
```sh
docker run -it -p 8012:8012 keking/kkfileview:4.1.0
docker run -d --name kkfileview -p 8012:8012 keking/kkfileview
```
[http://localhost:8012/](http://localhost:8012/)

## Runtime Environment
- [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

## Screenshots
![](https://kkview.cn/img/preview/preview-doc-image.png)

![](https://kkview.cn/img/preview/preview-ppt-image.png)

![](https://kkview.cn/img/preview/preview-xls.png)

![](https://kkview.cn/img/preview/preview-dcm.png)

![](https://kkview.cn/img/preview/preview-drawio.png)

## References
- [kkFileView](https://kkview.cn/zh-cn/index.html)
- [kkFileView GitHub](https://github.com/kekingcn/kkFileView)
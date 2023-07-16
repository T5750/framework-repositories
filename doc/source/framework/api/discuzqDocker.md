# Discuz! Q Docker

Discuz! Q 拥有完全开源、提供丰富接口、前后端分离、轻量化、数据独立可控、敏捷上云、快速变现七大能力。通过这些能力，能够帮助创业者更高效的上线内容产品，让信息能高效准确的分享与传播，流量变现能更加简单快捷。在某种意义上，我们希望能够重塑移动社区的用户体验，提供一种更可靠的信息连接与知识传播的方式。

## Demo
[Discuz! Q 官方演示站](https://discuz.chat/)

## Docker
```sh
docker run -d --name discuz -p 8080:80 ccr.ccs.tencentyun.com/discuzq/dzq:latest
```
- [http://localhost:8080/install](http://localhost:8080/install)
    + 站点名称, MySQL Host, 数据库, 用户名, 密码, 管理员 用户名, 密码
- [http://localhost:8080/admin](http://localhost:8080/admin)
- [http://localhost:8080/](http://localhost:8080/)

## Runtime Environment
- [PHP](https://www.php.net/downloads)
- [MySQL 5.x](http://www.mysql.com/)

## Screenshots
![](https://main.qcloudimg.com/raw/8e3ac52669180620b946750787b50c95.png)

![](https://main.qcloudimg.com/raw/e829271278cebc3892cdf194b3ff07d8.png)

![](https://main.qcloudimg.com/raw/ba86ab072b9c8f6a4143d7222b8b1a09.png)

## References
- [Discuz! Q](https://discuz.com/)
- [Discuz! Q Docker](https://discuz.com/docs/%E5%AE%89%E8%A3%85/Linux%20%E4%B8%BB%E6%9C%BA.html#%E5%9F%BA%E4%BA%8E-docker-%E5%AE%B9%E5%99%A8-%E6%8E%A8%E8%8D%90)
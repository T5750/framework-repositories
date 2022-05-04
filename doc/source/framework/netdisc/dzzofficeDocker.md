# DzzOffice Docker

DzzOffice是一套开源办公套件，适用于企业、团队搭建自己的类似“Google企业应用套件”、“微软Office365”的企业协同办公平台

## Demo
[http://demo.dzzoffice.com](http://demo.dzzoffice.com)

## Docker
```
docker run -d --name dzzoffice -v $PWD/dzzoffice:/var/www/html/data -p 8080:80 imdevops/dzzoffice
docker exec -it dzzoffice bash
chown -R www-data:www-data /var/www/html/data
```

[http://localhost:8080/](http://localhost:8080/)

## Office Online

解决方案 | 在线预览 | 在线编辑 | 本地依赖 | 内网使用 | 私有化部署 | 免费使用
---|---|---|---|---|---|---
onlyoffice | √ | √ | 无 | √ | √ | √ 最大20连接数
collabora | √ | √ | 无 | √ | √ | √ 最大20连接数
MS Office Online | √ | √ | 无 | √ | √ | 预览免费，编辑需购买
google Doc | √ | √ | 无 | × | × | √
永中Office | √ | × | 无 | × | × | √
pageOffice | √ | √ | Office软件 | √ | √ | ×

## Runtime Environment
- [PHP](https://www.php.net/downloads)
- [MySQL 5.x](http://www.mysql.com/)

## Screenshots
![](http://dzzoffice.com/images/DZZ-Office-2_03.png)

## References
- [DzzOffice](http://dzzoffice.com/index.html)
- [zyx0814/dzzoffice GitHub](https://github.com/zyx0814/dzzoffice)
- [imdevops/dzzoffice Docker](https://hub.docker.com/r/imdevops/dzzoffice)
- [在线office方案综述](https://www.dzzoffice.com/corpus/list?cid=3#fid_79)
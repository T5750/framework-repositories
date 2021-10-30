# ShowDoc Docker

ShowDoc是一个非常适合IT团队的在线API文档、技术文档工具。通过showdoc，你可以方便地使用markdown语法来书写出美观的API文档、数据字典文档、技术文档、在线excel文档等等。如果不想编辑markdown文档，你还可以利用showdoc的自动化能力，从程序注释中自动生成API文档，或者从搭配的RunApi客户端（类似postman的api调试工具）中一边调试接口、一边自动生成文档。通过分配项目成员和团队成员，你可以很方便地进行项目文档的权限管理和团队协作，也可以分享文档出去给朋友查看。

## Demo
- [API文档](http://www.showdoc.com.cn/2)
- [数据字典](http://www.showdoc.com.cn/1)
- [说明文档](http://www.showdoc.com.cn/3)

## Docker
```
mkdir -p ./showdoc_data/html
chmod -R 777 ./showdoc_data
docker run -d --name showdoc --user=root -p 8999:80 \
-v /showdoc_data/html:/var/www/html/ showdoc
```

## Docker Compose
`showdoc.yml`

- [http://localhost:8999/](http://localhost:8999/)
- User: showdoc / 123456

## Backup
```
zip -r showdoc_bak.zip ./showdoc_data/html
```

## References
- [ShowDoc](https://hub.docker.com/r/star7th/showdoc)
- [Docker方式安装](https://www.showdoc.com.cn/help/65610)
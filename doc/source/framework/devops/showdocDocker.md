# ShowDoc Docker

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
# Kafka Manager

## Configuration
`conf/application.properties`
```
kafka-manager.zkhosts="192.168.100.163:2181,192.168.100.164:2181,192.168.100.165:2181"
```

## Starting the service
```
bin/kafka-manager &
```

[http://192.168.100.163:9000/](http://192.168.100.163:9000/)

### Windows
```
bin\kafka-manager.bat
```

[http://localhost:9000/](http://localhost:9000/)

## References
- [Kafka Manager 1.3.3.22 GitHub](https://github.com/yahoo/kafka-manager)
- [kafka manager 安装包下载](https://blog.wolfogre.com/posts/kafka-manager-download/)
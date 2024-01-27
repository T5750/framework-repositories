# webSpoon Docker

webSpoon is a web-based graphical designer for Pentaho Data Integration with the same look & feel as Spoon. Kettle transformation/job files can be designed and executed in your favorite web browser. This is one of the community activities and not supported by Pentaho.

## Docker
```sh
docker run --name webspoon -d -p 8080:8080 hiromuhota/webspoon
```
[http://localhost:8080/](http://localhost:8080/)

## 汉化
```sh
docker exec -it webspoon bash
cd /usr/local/tomcat/bin
echo CATALINA_OPTS=\"-Dorg.apache.tomcat.util.buf.UDecoder.ALLOW_ENCODED_SLASH=true\" >> setenv.sh
echo JAVA_OPTS=\"-Duser.language=zh -Duser.region=CN -Dfile.encoding=UTF-8\" >> setenv.sh
docker restart webspoon
```

## Runtime Environment
- [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

## References
- [webSpoon GitHub](https://github.com/HiromuHota/pentaho-kettle)
- [kettle web 版本 (webspoon) 中文部署](https://blog.csdn.net/qq_37349379/article/details/127082840)
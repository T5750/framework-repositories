# Tomcat Docker

Apache Tomcat is an open source implementation of the Java Servlet and JavaServer Pages technologies

## Docker
```
docker run -it --rm -p 8888:8080 tomcat:9.0
docker run -d --rm --name tomcat -p 8080:8080 tomcat:8.5.77-jre8
```
[http://localhost:8080/](http://localhost:8080/)

## References
- [Tomcat Docker](https://hub.docker.com/_/tomcat)
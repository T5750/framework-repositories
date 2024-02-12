# Camel Karavan Docker

Apache Camel Karavan is a Low-Code Data Integration Platform

## Docker
```sh
docker run -it -p 8080:8080 -v $(pwd):/deployments/integrations ghcr.io/apache/camel-karavan:latest
docker run -it --rm --name karavan -p 8080:8080 -e KARAVAN_GIT_INSTALL_GITEA=true -e KARAVAN_IMAGE_REGISTRY_INSTALL=true ghcr.io/apache/camel-karavan
```
[http://localhost:8080/](http://localhost:8080/)

## Runtime Environment
- [Java 11](https://openjdk.java.net/projects/jdk/11/)
- [Quarkus](https://quarkus.io/)
- [TypeScript](https://www.typescriptlang.org/)

## Screenshots
![](https://gitee.com/mirrors_apache/camel-karavan/raw/main/images/karavan-clouds-large.png)

![](https://gitee.com/mirrors_apache/camel-karavan/raw/3.18.4/images/karavan-about.png)

![](https://gitee.com/mirrors_apache/camel-karavan/raw/3.18.4/images/karavan-ipaas-1.png)

![](https://gitee.com/mirrors_apache/camel-karavan/raw/3.18.4/images/karavan-vscode.png)

## References
- [Camel Karavan GitHub](https://github.com/apache/camel-karavan)
- [Camel Karavan Examples](https://github.com/apache/camel-karavan/tree/main/karavan-demo)
- [Camel Karavan Gitee](https://gitee.com/mirrors_apache/camel-karavan)
# OpenJDK Docker

"Vanilla" builds of OpenJDK (an open-source implementation of the Java Platform, Standard Edition)

## Docker
### Start a Java instance in your app
`vi Dockerfile`
```
FROM openjdk:11
COPY . /usr/src/myapp
WORKDIR /usr/src/myapp
RUN javac Main.java
CMD ["java", "Main"]
```
```sh
$ docker build -t my-java-app .
$ docker run -it --rm --name my-running-app my-java-app
```

### Compile your app inside the Docker container
```sh
$ docker run --rm -v "$PWD":/usr/src/myapp -w /usr/src/myapp openjdk:11 javac Main.java
```

### Simple Tags
```sh
docker pull openjdk:8
docker pull openjdk:8-slim
docker pull openjdk:8-jre-slim
```

## Amazon Corretto
Corretto is a no-cost, production-ready distribution of the Open Java Development Kit (OpenJDK).

```sh
docker pull amazoncorretto:8
docker pull amazoncorretto:11
docker run --rm amazoncorretto:8 java -version
```
`vi Dockerfile`
```
FROM amazoncorretto:8
# RUN echo $' \
RUN echo ' \
public class Hello { \
public static void main(String[] args) { \
System.out.println("Welcome to Amazon Corretto!"); \
} \
}' > Hello.java
RUN javac Hello.java
CMD ["java", "Hello"]
```
```sh
docker build -t hello-app .
docker run --rm hello-app
docker run --rm -it hello-app sh
```

### How is Corretto different from OpenJDK?
Corretto is a distribution of Open JDK with patches included by Amazon that are not yet integrated in the corresponding OpenJDK update projects. We focus on patches that improve performance or stability in OpenJDK, chosen based on Amazon's observations running large services.

### What is included in Corretto's long-term support?
Amazon will provide security updates for Corretto 8 until at least June 2023. Updates are planned to be released quarterly. Corretto 11, corresponding to OpenJDK 11, will be available during the first half of 2019. Amazon will support Corretto 11 with quarterly updates until at least August 2024.

## Eclipse Temurin
Eclipse Temurin 是由基于 OpenJDK 的开源 Java SE 产生的构建版本

```sh
docker pull eclipse-temurin:17-jre
```

## References
- [OpenJDK Docker](https://hub.docker.com/_/openjdk)
- [Amazon Corretto Docker](https://hub.docker.com/_/amazoncorretto)
- [Amazon Corretto Docker GitHub](https://github.com/corretto/corretto-docker)
- [在 Docker 映像上使用 Amazon Corretto 8 入门](https://docs.aws.amazon.com/zh_cn/corretto/latest/corretto-8-ug/docker-install.html)
- [Eclipse Temurin™ Latest Releases](https://adoptium.net/zh-CN/temurin/releases/)
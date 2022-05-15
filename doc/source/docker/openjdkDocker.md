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
```
$ docker build -t my-java-app .
$ docker run -it --rm --name my-running-app my-java-app
```

### Compile your app inside the Docker container
```
$ docker run --rm -v "$PWD":/usr/src/myapp -w /usr/src/myapp openjdk:11 javac Main.java
```

## References
- [OpenJDK Docker](https://hub.docker.com/_/openjdk)
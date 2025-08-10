# Hawtio Docker

A modular web console for managing your Java stuff

## Running from CLI (JBang)
### JBang
[Download JBang](https://www.jbang.dev/download/)

GitBash/cygwin/mingwin/WSL:
```sh
curl -Ls https://sh.jbang.dev | bash -s - app setup
```

### Start up Hawtio
```sh
jbang app install hawtio@hawtio/hawtio
hawtio --help
```

### Connecting directly to a remote JVM from CLI
```sh
hawtio --connection=myconn=http://localhost:8778/jolokia/
hawtio --connection=myconn
hawtio --connection=conn1 --connection=conn2 --connection=conn3
hawtio --port 8000 --connection=myconn=http://localhost:8090/sba/actuator/jolokia
```

## Docker
```sh
docker run -d --name hawtio -p 8080:8080 znio/hawtio
```

## Runtime Environment
- [Java 17](https://github.com/openjdk/jdk)

## Screenshots
![](https://hawt.io/static/camel-route-fbe71bd43f2a9c13599fa3209e482560.png)

![](https://hawt.io/static/jmx-d47e91820ac31a3d039d4c1e9b73e3d5.png)

![](https://hawt.io/static/spring-boot-913cfe98f6b0d8caf321ae452335075d.png)

## References
- [Hawtio](https://hawt.io/)
- [Hawtio GitHub](https://github.com/hawtio/hawtio)
- [Hawtio Get Started](https://hawt.io/docs/get-started.html)
- [zionio/docker-hawtio GitHub](https://github.com/zionio/docker-hawtio)
- [Jolokia Manual](https://jolokia.org/reference/html/manual/index.html)
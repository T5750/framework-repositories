# Jetty Docker

Jetty provides a Web server and javax.servlet container.

## Docker
```sh
docker run -d -p 8080:8080 jetty
```
[http://localhost:8080/](http://localhost:8080/)
```sh
docker run -d -p 80:8080 -p 443:8443 jetty
```

## Environment
```
JETTY_HOME    =  /usr/local/jetty
JETTY_BASE    =  /var/lib/jetty
TMPDIR        =  /tmp/jetty
```

## Configuration
```sh
docker run -d jetty --list-config
docker run -d jetty --module=jmx jetty.threadPool.maxThreads=500
```
```
FROM jetty
RUN java -jar "$JETTY_HOME/start.jar" --add-to-startd=jmx,stats
```

### JVM Configuration
```sh
docker run -e JAVA_OPTIONS="-Xmx1g" -d jetty
```

## Read-only container
```sh
docker run -d --read-only -v /tmp/jetty -v /run/jetty jetty
```

## HTTP/2 Support
```sh
FROM jetty
RUN java -jar $JETTY_HOME/start.jar --add-to-startd=http2 --approve-all-licenses
```

## Security
```sh
docker run -d -u jetty jetty
```

## References
- [Eclipse Jetty](https://www.eclipse.org/jetty/)
- [Jetty Docker](https://hub.docker.com/_/jetty)
- [Jetty GitHub](https://github.com/eclipse/jetty.project)
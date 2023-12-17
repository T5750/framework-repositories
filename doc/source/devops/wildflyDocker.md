# WildFly Docker

A powerful, modular, & lightweight application server that helps you build amazing applications.

## Docker
To boot in standalone mode
```sh
docker run -it quay.io/wildfly/wildfly:28.0.0.Final-jdk11
```

To boot in standalone mode with admin console available remotely
```sh
docker run -p 8080:8080 -p 9990:9990 -it quay.io/wildfly/wildfly /opt/jboss/wildfly/bin/standalone.sh -b 0.0.0.0 -bmanagement 0.0.0.0
docker run -d --name=wildfly -p 8080:8080 -p 9990:9990 quay.io/wildfly/wildfly:28.0.0.Final-jdk11 /opt/jboss/wildfly/bin/standalone.sh -b 0.0.0.0 -bmanagement 0.0.0.0
docker exec -it wildfly bash
sh wildfly/bin/add-user.sh
```
- [http://localhost:8080/](http://localhost:8080/)
- Administration Console: [http://localhost:9990/](http://localhost:9990/)

To boot in domain mode
```sh
docker run -it quay.io/wildfly/wildfly /opt/jboss/wildfly/bin/domain.sh -b 0.0.0.0 -bmanagement 0.0.0.0
```

## Extending the image
To be able to create a management user to access the administration console create a Dockerfile with the following content
```
FROM quay.io/wildfly/wildfly
RUN /opt/jboss/wildfly/bin/add-user.sh admin Admin#70365 --silent
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
```

Then you can build the image:
```sh
docker build --tag=jboss/wildfly-admin .
```
Or for jdk11:
```sh
docker build --build-arg jdk=11 --tag=jboss/wildfly-admin .
```

Run it:
```sh
docker run -it jboss/wildfly-admin
```

## Screenshots
![](https://www.wildfly.org/assets/img/homepage/homepage-screenshot.png)

## References
- [WildFly](https://www.wildfly.org/)
- [WildFly GitHub](https://github.com/wildfly/wildfly)
- [WildFly Docker](https://quay.io/repository/wildfly/wildfly)
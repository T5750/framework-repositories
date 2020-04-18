# Spring Boot Admin in Java 7

## Getting started
### build.gradle
```
apply plugin: 'java'
sourceCompatibility = 1.7
targetCompatibility = 1.7
apply plugin: 'war'

dependencies {
    compile group: 'de.codecentric', name: 'spring-boot-admin-starter-server', version: '1.5.7'
    compile group: 'de.codecentric', name: 'spring-boot-admin-server-ui-login', version: '1.5.7'
    compile('org.springframework.boot:spring-boot-starter-security')
    compile("org.springframework.boot:spring-boot-starter-web:1.5.3.RELEASE") {
        exclude group: 'org.springframework.boot', module: 'spring-boot-starter-tomcat'
    }
    compile group: 'org.apache.tomcat.embed', name: 'tomcat-embed-core', version: '7.0.77'
    compile group: 'org.apache.tomcat.embed', name: 'tomcat-embed-el', version: '7.0.77'
    compile group: 'org.apache.tomcat.embed', name: 'tomcat-embed-websocket', version: '7.0.77'
    compile group: 'org.apache.tomcat', name: 'tomcat-juli', version: '7.0.77'
}
```

### SpringBootAdmin
```
SpringBootAdmin extends SpringBootServletInitializer
```

## Runtime Environment
- [Java 7](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html)
- [Spring Framework 4.3.8.RELEASE](https://spring.io/projects/spring-framework)
- [Spring Boot 1.5.3.RELEASE](https://spring.io/projects/spring-boot)
- [spring-boot-admin 1.5.x](https://github.com/codecentric/spring-boot-admin/tree/1.5.x)
- [Tomcat 7](http://tomcat.apache.org/)
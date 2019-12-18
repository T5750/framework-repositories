# Monitoring and management over JMX

## 1 Customizing MBean names
`application.properties`:
```
endpoints.jmx.domain=myapp
endpoints.jmx.unique-names=true
```

## 2 Disabling JMX endpoints
```
endpoints.jmx.enabled=false
```

## 3 Using Jolokia for JMX over HTTP
```
<dependency>
    <groupId>org.jolokia</groupId>
    <artifactId>jolokia-core</artifactId>
</dependency>
```

### 3.1 Customizing Jolokia
```
jolokia.config.debug=true
```

### 3.2 Disabling Jolokia
```
endpoints.jolokia.enabled=false
```

## Tips
jconsole
- [Tomcat ThreadPool http-nio-8071 maxThreads](http://localhost:8071/jolokia/read/Tomcat:type=ThreadPool,name=%22http-nio-8071%22/maxThreads)

## References
- [Monitoring and management over JMX](https://docs.spring.io/spring-boot/docs/1.5.3.RELEASE/reference/htmlsingle/#production-ready-jmx)
- [Jolokia init parameters](https://jolokia.org/reference/html/agents.html#agent-war-init-params)
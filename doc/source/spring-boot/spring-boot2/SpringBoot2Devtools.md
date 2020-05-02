# Spring Boot 2 Devtools

## Static Resource Caching
`application.properties`
```
#spring.freemarker.cache = true //set true in production environment
spring.freemarker.cache = false //set false in development environment; It is false by default.
//Other such properties
spring.thymeleaf.cache = false
spring.mustache.cache = false
spring.groovy.template.cache = false
```
>**Auto-refresh vs Auto-restart** – Auto-refresh (or auto-load) refer to UI reload at browser to see static content changes. Auto-restart is referred to reloading the server side code and configurations followed by server restart.

## Automatic UI refresh
By default, live reload is enabled. If you wish to disable this feature for some reason, then set `spring.devtools.livereload.enabled` property to `false`.

`application.properties`
```
spring.devtools.livereload.enabled  = false #Set false to disable live reload
```

### Excluding Resources from auto-reload
By default, Auto-reload works on these paths:
1. `/META-INF/maven`
1. `/META-INF/resources`
1. `/resources`
1. `/static`
1. `/public`
1. `/templates`

If you want to disable auto-reload in browser for files in few of these paths, then use `spring.devtools.restart.exclude` property. e.g.
```
spring.devtools.restart.exclude=static/**,public/**
```

### Watching/Excluding Additional Paths
There may be few files not in classpath, but you still may want to watch those addtional files/paths to reload the application. To do so, use the `spring.devtools.restart.additional-paths` property.
```
spring.devtools.restart.additional-paths=script/**
```
Similarily, If you want to keep those defaults and add additional exclusions, use the `spring.devtools.restart.additional-exclude` property instead.
```
spring.devtools.restart.additional-exclude=styles/**
```

## Automatic server restart
### Enable/disable logging of auto-configuration changes
```
spring.devtools.restart.log-condition-evaluation-delta = false
```

### Disabling Restart
```
spring.devtools.restart.enabled = false
```

### Using a Trigger File
Use `spring.devtools.restart.trigger-file` property to mention the trigger file for your application. It can be any external or internal file.
```
spring.devtools.restart.trigger-file = d:/workspace/restart-trigger.txt
```

## Global settings file
To create global file, go to your system’s user’s home directory and create a file named `.spring-boot-devtools.properties`. (Please note that file name start with a dot). Not use this global property file to configure globally available options.
`.spring-boot-devtools.properties`
```
spring.devtools.restart.trigger-file = d:/workspace/restart-trigger.txt
```

## References
- [Spring Boot Devtools Tutorial](https://howtodoinjava.com/spring-boot2/developer-tools-module-tutorial/)
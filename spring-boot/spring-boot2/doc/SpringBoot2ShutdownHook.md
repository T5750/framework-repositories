# Spring Boot 2 ShutdownHook

## 1.Shell
`springboot2.sh`
```
./springboot2.sh start
./springboot2.sh stop
./springboot2.sh restart
./springboot2.sh status
./springboot2.sh stop -f
```

## 2.BootJar
`org.springframework.boot.gradle.tasks.bundling.BootJar` Since: 2.0.0

To create a ‘fully executable’ jar with Maven, use the following plugin configuration:
```
<plugin>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-maven-plugin</artifactId>
	<configuration>
		<executable>true</executable>
	</configuration>
</plugin>
```
The following example shows the equivalent Gradle configuration:
```
bootJar {
	launchScript()
}
```
```
$ chmod u+x spring-boot2-1.0.jar
$ sudo ln -s /var/springboot2/spring-boot2-1.0-boot.jar /etc/init.d/boot2
$ service boot2 start
$ service boot2 stop
$ service boot2 status
```

## References
- [优雅的启动、停止、重启你的SpringBoot项目](https://www.cnblogs.com/linkstar/p/9815097.html)
- [Installing Spring Boot Applications](https://docs.spring.io/spring-boot/docs/2.1.1.RELEASE/reference/htmlsingle/#deployment-install)
- [Class BootJar](https://docs.spring.io/spring-boot/docs/2.1.1.RELEASE/gradle-plugin/api/org/springframework/boot/gradle/tasks/bundling/BootJar.html)
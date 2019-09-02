# Spring Boot 2 Logging

## 1. Default Zero Configuration Logging
Spring boot active enabled logging is determined by [spring-boot-starter-logging](https://github.com/spring-projects/spring-boot/blob/master/spring-boot-project/spring-boot-starters/spring-boot-starter-logging/pom.xml) artifact and it’s auto-configuration which enables anyone of supported logging providers ([Java Util Logging](https://docs.oracle.com/javase/8/docs/api//java/util/logging/package-summary.html), [Log4J2](https://logging.apache.org/log4j/2.x/), and [Logback](https://logback.qos.ch/)) based on configuration provided.

## 2. Logback Logging
`logback.xml`
```
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <property name="LOG_LOCATION" value="c:/temp" />
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} - %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>
    <appender name="FILE" class="ch.qos.logback.core.FileAppender">
        <File>{LOG_LOCATION}/mylog.log</File>
        <encoder>
             <pattern>%d{yyyy-MM-dd HH:mm:ss} - %logger{36} - %msg%n</pattern>
        </encoder>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>${LOG_LOCATION}/archived/mylog-%d{yyyy-MM-dd}.%i.log
            </fileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy
                class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>10MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
        </rollingPolicy>
    </appender>
    <root level="INFO">
        <appender-ref ref="CONSOLE"/>
        <appender-ref ref="FILE"/>
    </root>
    <!-- Application logs at trace level -->
    <logger name="com.howtodoinjava" level="trace" additivity="false">
        <appender-ref ref="RollingFile" />
        <appender-ref ref="Console" />
    </logger>
</configuration>
```

## 3. Log4j2 Logging
### Step 1: Exclude logback and include log4j2
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-logging</artifactId>
        </exclusion>
    </exclusions>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-log4j2</artifactId>
</dependency>
```

### Step 2: Add log4j2 configuration file
`log4j2.xml` or (`log4j2.properties`)
```
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="WARN" monitorInterval="30">
	<Properties>
		<Property name="LOG_PATTERN">%d{yyyy-MM-dd'T'HH:mm:ss.SSSZ} %p %m%n</Property>
		<Property name="APP_LOG_ROOT">spring-boot/spring-boot2/logs</Property>
	</Properties>
	<Appenders>
		<Console name="Console" target="SYSTEM_OUT" follow="true">
			<PatternLayout pattern="${LOG_PATTERN}"/>
		</Console>
		<RollingFile name="appLog"
					 fileName="${APP_LOG_ROOT}/boot2application.log"
					 filePattern="${APP_LOG_ROOT}/boot2application-%d{yyyy-MM-dd}-%i.log">
			<PatternLayout pattern="${LOG_PATTERN}"/>
			<Policies>
				<SizeBasedTriggeringPolicy size="19500KB"/>
			</Policies>
			<DefaultRolloverStrategy max="1"/>
		</RollingFile>
	</Appenders>
	<Loggers>
		<Logger name="t5750.springboot2" additivity="false">
			<AppenderRef ref="appLog"/>
			<AppenderRef ref="Console"/>
		</Logger>
		<Root level="info">
			<AppenderRef ref="Console"/>
		</Root>
	</Loggers>
</Configuration>
```

### Step 3: With or without Slf4j
By default, if you are using SLF4J logger classes i.e. `org.slf4j.Logger` and `org.slf4j.LoggerFactory`, nothing needs to be changed in application code and all log statement will continue printing in target appenders.

If you are targeting to use log4j2 specific classes only, use `org.apache.logging.log4j.Logger` and `org.apache.logging.log4j.LogManager`.

I will recommend to use SLF4J logger classes.

SLF4J logger classes
```
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class Application {
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        LOGGER.info("Simple log statement with inputs {}, {} and {}", 1,2,3);
    }
}
```

LOG4J2 logger classes
```
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpringBootApplication
public class Application {
    private static Logger LOGGER = LogManager.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        LOGGER.info("Simple log statement with inputs 1, 2 and 3");
    }
}
```

## Spring Boot log4j2
`log4j2.xml`
```
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="WARN">
	<Properties>
		<Property name="PID">????</Property>
		<Property name="LOG_EXCEPTION_CONVERSION_WORD">%xwEx</Property>
		<Property name="LOG_LEVEL_PATTERN">%5p</Property>
		<Property name="LOG_DATEFORMAT_PATTERN">yyyy-MM-dd HH:mm:ss.SSS</Property>
		<Property name="CONSOLE_LOG_PATTERN">%clr{%d{${LOG_DATEFORMAT_PATTERN}}}{faint} %clr{${LOG_LEVEL_PATTERN}} %clr{${sys:PID}}{magenta} %clr{---}{faint} %clr{[%15.15t]}{faint} %clr{%-40.40c{1.}}{cyan} %clr{:}{faint} %m%n${sys:LOG_EXCEPTION_CONVERSION_WORD}</Property>
		<Property name="FILE_LOG_PATTERN">%d{${LOG_DATEFORMAT_PATTERN}} ${LOG_LEVEL_PATTERN} ${sys:PID} --- [%t] %-40.40c{1.} : %m%n${sys:LOG_EXCEPTION_CONVERSION_WORD}</Property>
	</Properties>
	<Appenders>
		<Console name="Console" target="SYSTEM_OUT" follow="true">
			<PatternLayout pattern="${sys:CONSOLE_LOG_PATTERN}" />
		</Console>
	</Appenders>
	<Loggers>
		<Logger name="org.apache.catalina.startup.DigesterFactory" level="error" />
		<Logger name="org.apache.catalina.util.LifecycleBase" level="error" />
		<Logger name="org.apache.coyote.http11.Http11NioProtocol" level="warn" />
		<logger name="org.apache.sshd.common.util.SecurityUtils" level="warn"/>
		<Logger name="org.apache.tomcat.util.net.NioSelectorPool" level="warn" />
		<Logger name="org.eclipse.jetty.util.component.AbstractLifeCycle" level="error" />
		<Logger name="org.hibernate.validator.internal.util.Version" level="warn" />
		<logger name="org.springframework.boot.actuate.endpoint.jmx" level="warn"/>
		<Root level="info">
			<AppenderRef ref="Console" />
		</Root>
	</Loggers>
</Configuration>
```
`log4j2-file.xml`
```
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="WARN">
	<Properties>
		<Property name="PID">????</Property>
		<Property name="LOG_EXCEPTION_CONVERSION_WORD">%xwEx</Property>
		<Property name="LOG_LEVEL_PATTERN">%5p</Property>
		<Property name="LOG_DATEFORMAT_PATTERN">yyyy-MM-dd HH:mm:ss.SSS</Property>
		<Property name="CONSOLE_LOG_PATTERN">%clr{%d{${LOG_DATEFORMAT_PATTERN}}}{faint} %clr{${LOG_LEVEL_PATTERN}} %clr{${sys:PID}}{magenta} %clr{---}{faint} %clr{[%15.15t]}{faint} %clr{%-40.40c{1.}}{cyan} %clr{:}{faint} %m%n${sys:LOG_EXCEPTION_CONVERSION_WORD}</Property>
		<Property name="FILE_LOG_PATTERN">%d{${LOG_DATEFORMAT_PATTERN}} ${LOG_LEVEL_PATTERN} ${sys:PID} --- [%t] %-40.40c{1.} : %m%n${sys:LOG_EXCEPTION_CONVERSION_WORD}</Property>
	</Properties>
	<Appenders>
		<Console name="Console" target="SYSTEM_OUT" follow="true">
			<PatternLayout pattern="${sys:CONSOLE_LOG_PATTERN}" />
		</Console>
		<RollingFile name="File" fileName="${sys:LOG_FILE}" filePattern="${sys:LOG_PATH}/$${date:yyyy-MM}/app-%d{yyyy-MM-dd-HH}-%i.log.gz">
			<PatternLayout>
				<Pattern>${sys:FILE_LOG_PATTERN}</Pattern>
			</PatternLayout>
			<Policies>
				<SizeBasedTriggeringPolicy size="10 MB" />
			</Policies>
		</RollingFile>
	</Appenders>
	<Loggers>
		<Logger name="org.apache.catalina.startup.DigesterFactory" level="error" />
		<Logger name="org.apache.catalina.util.LifecycleBase" level="error" />
		<Logger name="org.apache.coyote.http11.Http11NioProtocol" level="warn" />
		<logger name="org.apache.sshd.common.util.SecurityUtils" level="warn"/>
		<Logger name="org.apache.tomcat.util.net.NioSelectorPool" level="warn" />
		<Logger name="org.eclipse.jetty.util.component.AbstractLifeCycle" level="error" />
		<Logger name="org.hibernate.validator.internal.util.Version" level="warn" />
		<logger name="org.springframework.boot.actuate.endpoint.jmx" level="warn"/>
		<Root level="info">
			<AppenderRef ref="Console" />
			<AppenderRef ref="File" />
		</Root>
	</Loggers>
</Configuration>
```

## References
- [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.1.1.RELEASE/gradle-plugin/reference/html/)
- [A Guide to Logging in Spring Boot](https://howtodoinjava.com/spring-boot2/spring-boot-logging-configurations/)
- [Spring boot log4j2 configuration example](https://howtodoinjava.com/spring-boot2/spring-boot-log4j2-config/)
- [Log4j2 Layouts](https://logging.apache.org/log4j/2.x/manual/layouts.html)
- [Spring Boot log4j2.xml](https://github.com/spring-projects/spring-boot/blob/2.1.x/spring-boot-project/spring-boot/src/main/resources/org/springframework/boot/logging/log4j2/log4j2.xml)
- [Spring Boot log4j2-file.xml](https://github.com/spring-projects/spring-boot/blob/2.1.x/spring-boot-project/spring-boot/src/main/resources/org/springframework/boot/logging/log4j2/log4j2-file.xml)
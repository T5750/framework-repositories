# Spring Boot Admin Reference Guide

## Getting started

### Setting up Spring Boot Admin Server
1. `build.gradle`
	```
	compile group: 'de.codecentric', name: 'spring-boot-admin-starter-server', version: '1.5.7'
	```
1. `@EnableAdminServer`
	```
	@Configuration
	@EnableAutoConfiguration
	@EnableAdminServer
	public class SpringBootAdmin {
		public static void main(String[] args) {
			SpringApplication.run(SpringBootAdminApplication.class, args);
		}
	}
	```

### Registering client applications
1. `build.gradle`
	```
	compile group: 'de.codecentric', name: 'spring-boot-admin-starter-client', version: '1.5.7'
	```
1. `application.properties`
	```
	spring.boot.admin.url=http://localhost:8070
	management.security.enabled=false
	```

## Client applications

### Show version in application list
`META-INF/build-info.properties`

`pom.xml`
```
<build>
	<plugins>
		<plugin>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-maven-plugin</artifactId>
			<executions>
				<execution>
					<goals>
						<goal>build-info</goal>
					</goals>
				</execution>
			</executions>
		</plugin>
	</plugins>
</build>
```

`build.gradle`
```
springBoot {
    buildInfo()
}
```

### Loglevel management
`logback-spring.xml`
```
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
  <include resource="org/springframework/boot/logging/logback/base.xml"/>
  <jmxConfigurator/>
</configuration>
```

## Spring Boot Admin Server

### Notifications
```
@Configuration
@EnableScheduling
public class NotifierConfiguration {
  @Autowired
  private Notifier delegate;

  @Bean
  public FilteringNotifier filteringNotifier() { // 1.Add the FilteringNotifier bean using a delegate (e.g. MailNotifier when configured)
    return new FilteringNotifier(delegate);
  }

  @Bean
  @Primary
  public RemindingNotifier remindingNotifier() { // 2.Add the RemindingNotifier as primary bean using the FilteringNotifier as delegate.
    RemindingNotifier notifier = new RemindingNotifier(filteringNotifier());
    notifier.setReminderPeriod(TimeUnit.SECONDS.toMillis(10)); // 3.The reminders will be sent every 10 seconds.
    return notifier;
  }

  @Scheduled(fixedRate = 1_000L) // 4.Schedules sending of due reminders every 1 seconds.
  public void remind() {
    remindingNotifier().sendReminders();
  }
}
```

### Login UI Module
1. `build.gradle`
	```
	compile group: 'de.codecentric', name: 'spring-boot-admin-server-ui-login', version: '1.5.7'
	```

## Security

### Securing Spring Boot Admin Server
1. `SpringBootAdmin`
	```
	@Configuration
	  public static class SecurityConfig extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
		  // Page with login form is served as /login.html and does a POST on /login
		  http.formLogin().loginPage("/login.html").loginProcessingUrl("/login").permitAll();
		  // The UI does a POST on /logout on logout
		  http.logout().logoutUrl("/logout");
		  // The ui currently doesn't support csrf
		  http.csrf().disable();
	
		  // Requests for the login page and the static assets are allowed
		  http.authorizeRequests()
			  .antMatchers("/login.html", "/**/*.css", "/img/**", "/third-party/**")
			  .permitAll();
		  // ... and any other request needs to be authorized
		  http.authorizeRequests().antMatchers("/**").authenticated();
	
		  // Enable so that the clients can authenticate via HTTP basic for registering
		  http.httpBasic();
		}
	  }
	```
1. `spring-boot/application.properties`
	```
	spring.boot.admin.username=user
	spring.boot.admin.password=pass
	```

### Securing Client Actuator Endpoints
Submitting the credentials using SBA Client:
```
spring.boot.admin:
  url: http://localhost:8080
  client:
    metadata:
      user.name: ${security.user.name}
      user.password: ${security.user.password}
```

## References
- [Spring Boot Admin Reference Guide 1.5.7](http://codecentric.github.io/spring-boot-admin/1.5.7/)
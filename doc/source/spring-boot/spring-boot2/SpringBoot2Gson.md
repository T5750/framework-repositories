# Spring Boot 2 Gson

## Include Gson dependency
```
compile group: 'com.google.code.gson', name: 'gson', version: '2.8.5'
```
```xml
<dependencies>
	<dependency>
		<groupId>com.google.code.gson</groupId>
		<artifactId>gson</artifactId>
		<version>2.8.5</version>
	</dependency>
</dependencies>
```

## Gson auto-configuration
### Default Gson instance
```
@Autowire
private Gson gson;
```

### Custom Gson instance
`application.properties`
```
# Format to use when serializing Date objects.
spring.gson.date-format=
# Whether to disable the escaping of HTML characters such as '<', '>', etc.
spring.gson.disable-html-escaping=
# Whether to exclude inner classes during serialization.
spring.gson.disable-inner-class-serialization=
# Whether to enable serialization of complex map keys (i.e. non-primitives).
spring.gson.enable-complex-map-key-serialization=
# Whether to exclude all fields from consideration for serialization or deserialization that do not have the "Expose" annotation.
spring.gson.exclude-fields-without-expose-annotation=
# Naming policy that should be applied to an object's field during serialization and deserialization.
spring.gson.field-naming-policy=
# Whether to generate non executable JSON by prefixing the output with some special text.
spring.gson.generate-non-executable-json=
# Whether to be lenient about parsing JSON that doesn't conform to RFC 4627.
spring.gson.lenient=
# Serialization policy for Long and long types.
spring.gson.long-serialization-policy=
# Whether to output serialized JSON that fits in a page for pretty printing.
spring.gson.pretty-printing=
# Whether to serialize null fields.
spring.gson.serialize-nulls=
```

## Make Gson preferred json mapper
`application.properties`
```
spring.http.converters.preferred-json-mapper=gson
```

## Exclude Jackson completely
### Exclude Jackson from project dependencies
```
implementation("org.springframework.boot:spring-boot-starter-web") {
	exclude group: 'org.springframework.boot', module: 'spring-boot-starter-json'
}
```
```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-web</artifactId>
	<!-- Exclude the default Jackson dependency -->
	<exclusions>
		<exclusion>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-json</artifactId>
		</exclusion>
	</exclusions>
</dependency>
```

### Disable auto-configuration
`Boot2Provider.java`
```
@SpringBootApplication(exclude = {JacksonAutoConfiguration.class})
```

## References
- [Gson with Spring boot 2](https://howtodoinjava.com/spring-boot2/gson-with-spring-boot/)
- [Gson with Spring Boot](https://www.javadevjournal.com/spring-boot/gson-with-spring-boot/)
# Spring Boot 2 H2

Remember an in-memory database is created/initialized when an application starts up; and destroyed when the application shuts down.

## H2 Configuration Options
### Simple configuration
`application.properties`
```
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
```
Please note by default, Spring Boot configures the in-memory database connection with the username `'sa'` and an empty password `' '`.

### Configure data persistence
```
# temporary data storage
spring.datasource.url = jdbc:h2:mem:testdb
# temporary data storage
spring.datasource.url = jdbc:h2:file:/data/sample
spring.datasource.url = jdbc:h2:file:C:/data/sample (Windows only)
```

## Create schema and insert data on initialization
- `schema.sql` – To initialize the schema ie.create tables and dependencies.
- `data.sql` – To insert default data rows.

## H2 Console
### Enable H2 console
`application.properties`
```
# Enabling H2 Console
spring.h2.console.enabled=true
# Custom H2 Console URL
spring.h2.console.path=/h2
```

### Accessing H2 console
- http://localhost:18090/h2
- Saved Settings: Generic H2 (Server)
- JDBC URL: `jdbc:h2:file:D:/code/spring-boot2`

### Other configuration options
We can enable/disable the database trace logs and we can enable/disable the remote access of H2 console.
```
# Whether to enable trace output.
spring.h2.console.settings.trace=false
# Whether to enable remote access.
spring.h2.console.settings.web-allow-others=false
```

## References
- [Spring Boot with H2 Database](https://howtodoinjava.com/spring-boot2/h2-database-example/)
- [Spring boot crud operations example with hibernate](https://howtodoinjava.com/spring-boot2/spring-boot-crud-hibernate/)
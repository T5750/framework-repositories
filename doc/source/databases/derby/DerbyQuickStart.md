# Derby Quick Start

## What is Apache Derby?
- Derby has a small footprint -- about 3.5 megabytes for the base engine and embedded JDBC driver.
- Derby is based on the Java, JDBC, and SQL standards.
- Derby provides an embedded JDBC driver that lets you embed Derby in any Java-based solution.
- Derby also supports the more familiar client/server mode with the Derby Network Client JDBC driver and Derby Network Server.
- Derby is easy to install, deploy, and use.

## Embedded Derby
- `SimpleApp`

### A quick look at the code
#### Load the Embedded JDBC Driver
The `SimpleApp` application loads the Derby Embedded JDBC driver and starts Derby up with this code:
```
public String driver = "org.apache.derby.jdbc.EmbeddedDriver";
...
Class.forName(driver).newInstance();
```

#### Get an Embedded Connection
The `SimpleApp` application creates and connects to the `derbyDB` database with this code:
```
public String protocol = "jdbc:derby:";
...
conn = DriverManager.getConnection(protocol + "derbyDB;create=true", props);
```
That embedded connection URL, fully constructed, looks like this:
```
jdbc:derby:derbyDB;create=true
```

#### Shut Derby down
Code to shut down a specific database looks like this:
```
DriverManager.getConnection("jdbc:derby:MyDbTest;shutdown=true");
```
Code to shut down all databases and the Derby engine looks like this:
```
DriverManager.getConnection("jdbc:derby:;shutdown=true");
```

## IDEA
1. Database
1. New -> Data Source -> Derby
1. Name: `derbydb`
1. General -> Path: `D:\code\derbydb`, Driver: Derby (Embedded)
1. Schemas -> SA

## References
- [spring-boot-apache-derby-example](https://github.com/springframeworkguru/spring-boot-apache-derby-example)
- [Embedded Derby](https://db.apache.org/derby/papers/DerbyTut/embedded_intro.html)
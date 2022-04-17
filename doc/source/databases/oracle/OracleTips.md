# Oracle Tips

## Ojdbc6
Oracle JDBC Driver compatible with JDK6, JDK7, and JDK8

### 11.2.0.1
```xml
<dependency>
    <groupId>com.oracle</groupId>
    <artifactId>ojdbc</artifactId>
    <version>6</version>
</dependency>
```
```
mvn install:install-file -DgroupId=com.oracle -DartifactId=ojdbc -Dpackaging=jar -Dversion=6 -Dfile=ojdbc6.jar
```

### 11.2.0.4
```xml
<dependency>
    <groupId>com.oracle.database.jdbc</groupId>
    <artifactId>ojdbc6</artifactId>
    <version>11.2.0.4</version>
</dependency>
```

## References
- [Ojdbc6 11.2.0.4](https://mvnrepository.com/artifact/com.oracle.database.jdbc/ojdbc6/11.2.0.4)
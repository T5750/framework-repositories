# Keycloak MySQL Setup

## Prepare Keycloak schema and user
```
$ mysql -uroot -p
Welcome to the MySQL monitor.  Commands end with ; or \g.
Your MySQL connection id is 3
Server version: 5.7.17 MySQL Community Server (GPL)

Copyright (c) 2000, 2016, Oracle and/or its affiliates. All rights reserved.

Oracle is a registered trademark of Oracle Corporation and/or its
affiliates. Other names may be trademarks of their respective
owners.

Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

mysql> CREATE USER 'keycloak'@'%' IDENTIFIED BY 'keycloak';
Query OK, 0 rows affected (0.01 sec)

mysql> CREATE DATABASE keycloak CHARACTER SET utf8 COLLATE utf8_unicode_ci;
Query OK, 1 row affected (0.00 sec)

mysql> GRANT ALL PRIVILEGES ON keycloak.* TO 'keycloak'@'%';
Query OK, 0 rows affected (0.00 sec)
```
- created `keycloak` database
- created `keycloak` user with the password `keycloak` (please use a strong password for production)
- granted all privileges to the `keycloak` on the `keycloak` database

## RDBMS Setup Checklist
- Locate and download a JDBC driver for your database
- Package the driver JAR into a module and install this module into the server
- Declare the JDBC driver in the configuration profile of the server
- Modify the datasource configuration to use your database's JDBC driver
- Modify the datasource configuration to define the connection parameters to your database

## JDBC Setup
```
$ wget -P ~/tmp/  https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-java-5.1.42.zip
$ unzip ~/tmp/mysql-connector-java-5.1.42.zip -d ~/tmp
$ sudo mkdir -p /opt/keycloak/modules/system/layers/keycloak/com/mysql/main
$ sudo cd /opt/keycloak/modules/system/layers/keycloak/com/mysql/main
$ sudo cp ~/tmp/mysql-connector-java-5.1.42/mysql-connector-java-5.1.42-bin.jar .
$ sudo touch module.xml
```
```xml
<?xml version="1.0" ?>
<module xmlns="urn:jboss:module:1.3" name="com.mysql">
    <resources>
        <resource-root path="mysql-connector-java-5.1.42-bin.jar" />
    </resources>
    <dependencies>
        <module name="javax.api"/>
        <module name="javax.transaction.api"/>
    </dependencies>
</module>
```

### Declare and Load JDBC Driver
`vim /opt/keycloak/standalone/configuration/standalone.xml`
```xml
<subsystem xmlns="urn:jboss:domain:datasources:4.0">
    <datasources>
      <drivers>
          <driver name="mysql" module="com.mysql">
              <driver-class>com.mysql.jdbc.Driver</driver-class>
          </driver>      
          <driver name="h2" module="com.h2database.h2">
              <xa-datasource-class>org.h2.jdbcx.JdbcDataSource</xa-datasource-class>
          </driver>
      </drivers>
  </datasources>
</subsystem>
```
- `name` is set to `mysql`, but can be everything we want
- we speciy the `module` attribute which points to the `module` package we created earlier for the driver JAR
- finally, we specify the driver's Java class, which in case of MySQL is [com.mysql.jdbc.Driver](https://dev.mysql.com/doc/connector-j/en/connector-j-usagenotes-connect-drivermanager.html)

### Datasource setup
```xml
<subsystem xmlns="urn:jboss:domain:datasources:4.0">
    <datasources>
        <datasource jndi-name="java:jboss/datasources/ExampleDS" pool-name="ExampleDS" enabled="true" use-java-context="true">
            <connection-url>jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE</connection-url>
            <driver>h2</driver>
            <security>
                <user-name>sa</user-name>
                <password>sa</password>
            </security>
        </datasource>
        <datasource jndi-name="java:/jboss/datasources/KeycloakDS" pool-name="KeycloakDS" enabled="true">
            <connection-url>jdbc:mysql://localhost:3306/keycloak?useSSL=false&amp;characterEncoding=UTF-8</connection-url>
            <driver>mysql</driver>
            <pool>
                <min-pool-size>5</min-pool-size>
                <max-pool-size>15</max-pool-size>
            </pool>
            <security>
                <user-name>keycloak</user-name>
                <password>keycloak</password>
            </security>
            <validation>
                <valid-connection-checker class-name="org.jboss.jca.adapters.jdbc.extensions.mysql.MySQLValidConnectionChecker"/>
                <validate-on-match>true</validate-on-match>
                <exception-sorter class-name="org.jboss.jca.adapters.jdbc.extensions.mysql.MySQLExceptionSorter"/>
            </validation>
        </datasource>
        <drivers>
            <driver name="h2" module="com.h2database.h2">
                <xa-datasource-class>org.h2.jdbcx.JdbcDataSource</xa-datasource-class>
            </driver>
            <driver name="mysql" module="com.mysql">
                <driver-class>com.mysql.jdbc.Driver</driver-class>
            </driver>
        </drivers>
    </datasources>
</subsystem>
```
What we modified:
- we've searched for `datasource` definition for `KeycloakDS`.
- we modified the `connection-url` to point to the MySQL server
- we defined the driver we use for the connection; this is the **logical name** of the JDBC driver we declared in the previous section (`mysql`)
- it is expensive to open a new connection to a database every time you want to perform a transaction. To compensate, the datasource implementation maintains a pool of open connections. The `max-pool-size` specifies the maximum number of connections it will pool. We may want to change the value of this depending on the load of the system.
- finally we need to define the database `user-name` and `password`, that is needed to connect to the database.

## Database configuration
```xml
<subsystem xmlns="urn:jboss:domain:keycloak-server:1.1">
    <spi name="connectionsJpa">
        <provider name="default" enabled="true">
            <properties>
                <property name="dataSource" value="java:jboss/datasources/KeycloakDS"/>
                <property name="initializeEmpty" value="true"/>
                <property name="migrationStrategy" value="update"/>
                <property name="migrationExport" value="${jboss.home.dir}/keycloak-database-update.sql"/>
            </properties>
        </provider>
    </spi>
</subsystem>
```
- `dataSource`- JNDI name of the dataSource
- `initializeEmpty` - initialize database if empty. If set to false the database has to be manually initialized. If you want to manually initialize the database set migrationStrategy to `manual` which will create a file with SQL commands to initialize the database. Defaults to true.
- `migrationStrategy` - strategy to use to migrate database. Valid values are `update`, `manual` and `validate`.
    * Update will automatically migrate the database schema.
    * Manual will export the required changes to a file with SQL commands that you can manually execute on the database.
    * Validate will simply check if the database is up-to-date.
- `migrationExport` - path for where to write manual database initialization/migration file.

## Unicode Considerations for MySQL
Please note that `utf8mb4` character set does not work due to different storage requirements to `utf8` character set footnote:[Tracked as https://issues.jboss.org/browse/KEYCLOAK-3873]).

## References
- [Keycloak MySQL Setup](https://github.com/CodepediaOrg/bookmarks.dev/wiki/Keycloak-MySQL-Setup)
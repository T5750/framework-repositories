# GRANT Statement

```
GRANT
    priv_type [(column_list)]
      [, priv_type [(column_list)]] ...
    ON [object_type] priv_level
    TO user [auth_option] [, user [auth_option]] ...
    [REQUIRE {NONE | tls_option [[AND] tls_option] ...}]
    [WITH {GRANT OPTION | resource_option} ...]

GRANT PROXY ON user
    TO user [, user] ...
    [WITH GRANT OPTION]

object_type: {
    TABLE
  | FUNCTION
  | PROCEDURE
}

priv_level: {
    *
  | *.*
  | db_name.*
  | db_name.tbl_name
  | tbl_name
  | db_name.routine_name
}

user:
    (see Section 6.2.4, “Specifying Account Names”)

auth_option: {
    IDENTIFIED BY [PASSWORD] 'auth_string'
  | IDENTIFIED WITH auth_plugin
  | IDENTIFIED WITH auth_plugin AS 'auth_string'
}

tls_option: {
    SSL
  | X509
  | CIPHER 'cipher'
  | ISSUER 'issuer'
  | SUBJECT 'subject'
}

resource_option: {
  | MAX_QUERIES_PER_HOUR count
  | MAX_UPDATES_PER_HOUR count
  | MAX_CONNECTIONS_PER_HOUR count
  | MAX_USER_CONNECTIONS count
}
```

## GRANT General Overview
For example:
```
GRANT ALL ON db1.* TO 'jeffrey'@'localhost';
```
The host name part of the account name, if omitted, defaults to `'%'`.

Normally, a database administrator first uses CREATE USER to create an account, then GRANT to define its privileges and characteristics. For example:
```
CREATE USER 'jeffrey'@'localhost' IDENTIFIED BY 'password';
GRANT ALL ON db1.* TO 'jeffrey'@'localhost';
GRANT SELECT ON db2.invoice TO 'jeffrey'@'localhost';
GRANT USAGE ON *.* TO 'jeffrey'@'localhost' WITH MAX_QUERIES_PER_HOUR 90;
```

## Object Quoting Guidelines
To specify quoted values:
- Quote database, table, column, and routine names as identifiers.
- Quote user names and host names as identifiers or as strings.
- Quote passwords as strings.

## Account Names and Passwords
MySQL does not support wildcards in user names. To refer to an anonymous user, specify an account with an empty user name with the `GRANT` statement:
```
GRANT ALL ON test.* TO ''@'localhost' ...;
```

## Privileges Supported by MySQL
Permissible Privileges for GRANT and REVOKE

Privilege | Meaning and Grantable Levels
---|-----
`ALL [PRIVILEGES]` | Grant all privileges at specified access level except `GRANT OPTION` and `PROXY`.
`ALTER` | Enable use of `ALTER TABLE`. Levels: Global, database, table.
`ALTER ROUTINE` | Enable stored routines to be altered or dropped. Levels: Global, database, routine.
`CREATE` | Enable database and table creation. Levels: Global, database, table.
`CREATE ROUTINE` | Enable stored routine creation. Levels: Global, database.
`CREATE TABLESPACE` | Enable tablespaces and log file groups to be created, altered, or dropped. Level: Global.
`CREATE TEMPORARY TABLES` | Enable use of `CREATE TEMPORARY TABLE`. Levels: Global, database.
`CREATE USER` | Enable use of `CREATE USER`, `DROP USER`, `RENAME USER`, and `REVOKE ALL PRIVILEGES`. Level: Global.
`CREATE VIEW` | Enable views to be created or altered. Levels: Global, database, table.
`DELETE` | Enable use of `DELETE`. Level: Global, database, table.
`DROP` | Enable databases, tables, and views to be dropped. Levels: Global, database, table.
`EVENT` | Enable use of events for the Event Scheduler. Levels: Global, database.
`EXECUTE` | Enable the user to execute stored routines. Levels: Global, database, routine.
`FILE` | Enable the user to cause the server to read or write files. Level: Global.
`GRANT OPTION` | Enable privileges to be granted to or removed from other accounts. Levels: Global, database, table, routine, proxy.
`INDEX` | Enable indexes to be created or dropped. Levels: Global, database, table.
`INSERT` | Enable use of `INSERT`. Levels: Global, database, table, column.
`LOCK TABLES` | Enable use of `LOCK TABLES` on tables for which you have the `SELECT` privilege. Levels: Global, database.
`PROCESS` | Enable the user to see all processes with `SHOW PROCESSLIST`. Level: Global.
`PROXY` | Enable user proxying. Level: From user to user.
`REFERENCES` | Enable foreign key creation. Levels: Global, database, table, column.
`RELOAD` | Enable use of `FLUSH` operations. Level: Global.
`REPLICATION CLIENT` | Enable the user to ask where master or slave servers are. Level: Global.
`REPLICATION SLAVE` | Enable replication slaves to read binary log events from the master. Level: Global.
`SELECT` | Enable use of `SELECT`. Levels: Global, database, table, column.
`SHOW DATABASES` | Enable `SHOW DATABASES` to show all databases. Level: Global.
`SHOW VIEW` | Enable use of `SHOW CREATE VIEW`. Levels: Global, database, table.
`SHUTDOWN` | Enable use of `mysqladmin shutdown`. Level: Global.
`SUPER` | Enable use of other administrative operations such as `CHANGE MASTER TO`, `KILL`, `PURGE BINARY LOGS`, `SET GLOBAL`, and `mysqladmin debug` command. Level: Global.
`TRIGGER` | Enable trigger operations. Levels: Global, database, table.
`UPDATE` | Enable use of `UPDATE`. Levels: Global, database, table, column.
`USAGE` | Synonym for “no privileges”

The privileges that a user holds for a database, table, column, or routine are formed additively as the logical `OR` of the account privileges at each of the privilege levels, including the global level. It is not possible to deny a privilege granted at a higher level by absence of that privilege at a lower level. For example, this statement grants the `SELECT` and `INSERT` privileges globally:
```
GRANT SELECT, INSERT ON *.* TO u1;
```

## Global Privileges
Global privileges are administrative or apply to all databases on a given server. To assign global privileges, use `ON *.*` syntax:
```
GRANT ALL ON *.* TO 'someuser'@'somehost';
GRANT SELECT, INSERT ON *.* TO 'someuser'@'somehost';
```

## Database Privileges
Database privileges apply to all objects in a given database. To assign database-level privileges, use `ON db_name.*` syntax:
```
GRANT ALL ON mydb.* TO 'someuser'@'somehost';
GRANT SELECT, INSERT ON mydb.* TO 'someuser'@'somehost';
```

## Table Privileges
Table privileges apply to all columns in a given table. To assign table-level privileges, use `ON db_name.tbl_name` syntax:
```
GRANT ALL ON mydb.mytbl TO 'someuser'@'somehost';
GRANT SELECT, INSERT ON mydb.mytbl TO 'someuser'@'somehost';
```

## Column Privileges
Column privileges apply to single columns in a given table. Each privilege to be granted at the column level must be followed by the column or columns, enclosed within parentheses.
```
GRANT SELECT (col1), INSERT (col1, col2) ON mydb.mytbl TO 'someuser'@'somehost';
```

## Stored Routine Privileges
The `ALTER ROUTINE`, `CREATE ROUTINE`, `EXECUTE`, and `GRANT OPTION` privileges apply to stored routines (procedures and functions). They can be granted at the global and database levels. Except for `CREATE ROUTINE`, these privileges can be granted at the routine level for individual routines.
```
GRANT CREATE ROUTINE ON mydb.* TO 'someuser'@'somehost';
GRANT EXECUTE ON PROCEDURE mydb.myproc TO 'someuser'@'somehost';
```

## Proxy User Privileges
The `PROXY` privilege enables one user to be a proxy for another. The proxy user impersonates or takes the identity of the proxied user; that is, it assumes the privileges of the proxied user.
```
GRANT PROXY ON 'localuser'@'localhost' TO 'externaluser'@'somehost';
```

## Implicit Account Creation
If an account named in a `GRANT` statement does not exist, the action taken depends on the `NO_AUTO_CREATE_USER` SQL mode:
- If `NO_AUTO_CREATE_USER` is not enabled, `GRANT` creates the account. This is very insecure unless you specify a nonempty password using `IDENTIFIED BY`.
- If `NO_AUTO_CREATE_USER` is enabled, `GRANT` fails and does not create the account, unless you specify a nonempty password using `IDENTIFIED BY` or name an authentication plugin using `IDENTIFIED WITH`.

As of MySQL 5.6.12, if the account already exists, `IDENTIFIED` WITH is prohibited because it is intended only for use when creating new accounts.

## Other Account Characteristics
`GRANT` permits these `tls_option` values:
- `NONE`
    ```
    GRANT ALL PRIVILEGES ON test.* TO 'root'@'localhost'
      REQUIRE NONE;
    ```
- `SSL`
    ```
    GRANT ALL PRIVILEGES ON test.* TO 'root'@'localhost'
      REQUIRE SSL;
    ```
- `X509`
    ```
    GRANT ALL PRIVILEGES ON test.* TO 'root'@'localhost'
      REQUIRE X509;
    ```
- `ISSUER` 'issuer'
    ```
    GRANT ALL PRIVILEGES ON test.* TO 'root'@'localhost'
      REQUIRE ISSUER '/C=SE/ST=Stockholm/L=Stockholm/
        O=MySQL/CN=CA/emailAddress=ca@example.com';
    ```
- `SUBJECT` 'subject'
    ```
    GRANT ALL PRIVILEGES ON test.* TO 'root'@'localhost'
      REQUIRE SUBJECT '/C=SE/ST=Stockholm/L=Stockholm/
        O=MySQL demo client certificate/
        CN=client/emailAddress=client@example.com';
    ```
- `CIPHER` 'cipher'
    ```
    GRANT ALL PRIVILEGES ON test.* TO 'root'@'localhost'
    REQUIRE CIPHER 'EDH-RSA-DES-CBC3-SHA';
    ```

The `SUBJECT`, `ISSUER`, and `CIPHER` options can be combined in the `REQUIRE` clause like this:
```
GRANT ALL PRIVILEGES ON test.* TO 'root'@'localhost'
  REQUIRE SUBJECT '/C=SE/ST=Stockholm/L=Stockholm/
    O=MySQL demo client certificate/
    CN=client/emailAddress=client@example.com'
  AND ISSUER '/C=SE/ST=Stockholm/L=Stockholm/
    O=MySQL/CN=CA/emailAddress=ca@example.com'
  AND CIPHER 'EDH-RSA-DES-CBC3-SHA';
```
To grant the `GRANT OPTION` privilege to an account without otherwise changing its privileges, do this:
```
GRANT USAGE ON *.* TO 'someuser'@'somehost' WITH GRANT OPTION;
```

GRANT permits these resource_option values:
- `MAX_QUERIES_PER_HOUR count`, `MAX_UPDATES_PER_HOUR count`, `MAX_CONNECTIONS_PER_HOUR count`
- `MAX_USER_CONNECTIONS count`

To specify resource limits for an existing user without affecting existing privileges, use `GRANT USAGE` at the global level (`ON *.*`) and name the limits to be changed. For example:
```
GRANT USAGE ON *.* TO ...
WITH MAX_QUERIES_PER_HOUR 500 MAX_UPDATES_PER_HOUR 100;
```

## References
- [13.7.1.4 GRANT Statement](https://dev.mysql.com/doc/refman/5.6/en/grant.html)
# REVOKE Statement

```
REVOKE
    priv_type [(column_list)]
      [, priv_type [(column_list)]] ...
    ON [object_type] priv_level
    FROM user [, user] ...

REVOKE ALL [PRIVILEGES], GRANT OPTION
    FROM user [, user] ...

REVOKE PROXY ON user
    FROM user [, user] ...
```
For example:
```
REVOKE INSERT ON *.* FROM 'jeffrey'@'localhost';
```
The host name part of the account name, if omitted, defaults to `'%'`.

To revoke all privileges, use the second syntax, which drops all global, database, table, column, and routine privileges for the named user or users:
```
REVOKE ALL PRIVILEGES, GRANT OPTION FROM user [, user] ...
```

## References
- [13.7.1.6 REVOKE Statement](https://dev.mysql.com/doc/refman/5.6/en/revoke.html)
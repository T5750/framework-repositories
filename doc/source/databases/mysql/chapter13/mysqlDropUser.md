# DROP USER Statement

```
DROP USER user [, user] ...
```
For example:
```
DROP USER 'jeffrey'@'localhost';
```
The host name part of the account name, if omitted, defaults to `'%'`.

## References
- [13.7.1.3 DROP USER Statement](https://dev.mysql.com/doc/refman/5.6/en/drop-user.html)
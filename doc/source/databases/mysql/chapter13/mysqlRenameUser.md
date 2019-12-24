# RENAME USER Statement

```
RENAME USER old_user TO new_user
    [, old_user TO new_user] ...
```
For example:
```
RENAME USER 'jeffrey'@'localhost' TO 'jeff'@'127.0.0.1';
```
The host name part of the account name, if omitted, defaults to '`%`'.

## References
- [13.7.1.5 RENAME USER Statement](https://dev.mysql.com/doc/refman/5.6/en/rename-user.html)
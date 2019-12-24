# ALTER USER Statement

```
ALTER USER user_specification [, user_specification] ...

user_specification:
    user PASSWORD EXPIRE
```
For each account, `ALTER USER` expires its password. For example:
```
ALTER USER 'jeffrey'@'localhost' PASSWORD EXPIRE;
```
Password expiration for an account affects the corresponding row of the `mysql.user` system table: The server sets the `password_expired` column to '`Y`'.

## References
- [13.7.1.1 ALTER USER Statement](https://dev.mysql.com/doc/refman/5.6/en/alter-user.html)
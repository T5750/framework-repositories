# CREATE USER Statement

```
CREATE USER
    user [auth_option] [, user [auth_option]] ...

user:
    (see Section 6.2.4, “Specifying Account Names”)

auth_option: {
    IDENTIFIED BY [PASSWORD] 'auth_string'
  | IDENTIFIED WITH auth_plugin [AS 'auth_string']
}
```
For example:
```
CREATE USER 't5750'@'localhost' IDENTIFIED BY '123456';
```

The server assigns the given authentication plugin to the account but no password. Clients must provide no password when they connect. However, an account with no password is insecure. To ensure that an account uses a specific authentication plugin and has a password with the corresponding hash format, specify the plugin explicitly with IDENTIFIED WITH, then use `SET PASSWORD` to set the password:
```
CREATE USER 'jeffrey'@'localhost' IDENTIFIED WITH mysql_native_password;
SET old_passwords = 0;
SET PASSWORD FOR 'jeffrey'@'localhost' = PASSWORD('123456');
```

## References
- [13.7.1.2 CREATE USER Statement](https://dev.mysql.com/doc/refman/5.6/en/create-user.html)
# SET PASSWORD Statement

```
SET PASSWORD [FOR user] = password_option

password_option: {
    PASSWORD('auth_string')
  | OLD_PASSWORD('auth_string')
  | 'hash_string'
}
```

- '`auth_string`' represents a cleartext password.
- '`hash_string`' represents an encrypted password.

`SET PASSWORD` can be used with or without a `FOR` clause that explicitly names a user account:
- With a FOR `user` clause, the statement sets the password for the named account, which must exist:
    ```
    SET PASSWORD FOR 'jeffrey'@'localhost' = password_option;
    ```
- With no FOR `user` clause, the statement sets the password for the current user:
    ```
    SET PASSWORD = password_option;
    ```
Any client who connects to the server using a nonanonymous account can change the password for that account. (In particular, you can change your own password.) To see which account the server authenticated you as, invoke the `CURRENT_USER()` function:
```
SELECT CURRENT_USER();
```

The following table shows, for each password hashing method, the permitted value of `old_passwords` and which authentication plugins use the hashing method.

Password Hashing Method old_passwords | Value Associated | Authentication Plugin
---|---|---
MySQL 4.1 native hashing | 0 | `mysql_native_password`
Pre-4.1 (“old”) hashing | 1 | `mysql_old_password`
SHA-256 hashing | 2 | `sha256_password`

## References
- [13.7.1.7 SET PASSWORD Statement](https://dev.mysql.com/doc/refman/5.6/en/set-password.html)
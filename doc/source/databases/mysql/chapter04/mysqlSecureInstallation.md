# MySQL mysql_secure_installation

This program enables you to improve the security of your MySQL installation in the following ways:
- You can set a password for `root` accounts.
- You can remove `root` accounts that are accessible from outside the local host.
- You can remove anonymous-user accounts.
- You can remove the `test` database (which by default can be accessed by all users, even anonymous users), and privileges that permit anyone to access databases with names that start with `test_`.

Invoke **mysql_secure_installation** without arguments:
```
shell> mysql_secure_installation
```

## References
- [4.4.5 mysql_secure_installation — Improve MySQL Installation Security](https://dev.mysql.com/doc/refman/5.6/en/mysql-secure-installation.html)
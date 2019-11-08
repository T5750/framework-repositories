## MySQL Using Option Files

### Option Files Read on Windows Systems

File Name | Purpose
---|---
%WINDIR%\my.ini, %WINDIR%\my.cnf | Global options
C:\my.ini, C:\my.cnf | Global options
BASEDIR\my.ini, BASEDIR\my.cnf | Global options
defaults-extra-file | The file specified with `--defaults-extra-file`, if any
%APPDATA%\MySQL\.mylogin.cnf | Login path options (clients only)

### Option Files Read on Unix and Unix-Like Systems

File Name | Purpose
---|---
/etc/my.cnf | Global options
/etc/mysql/my.cnf | Global options
SYSCONFDIR/my.cnf | Global options
$MYSQL_HOME/my.cnf | Server-specific options (server only)
defaults-extra-file | The file specified with `--defaults-extra-file`, if any
~/.my.cnf | User-specific options
~/.mylogin.cnf | User-specific login path options (clients only)

### Option File Syntax
Here is a typical global option file:
```
[client]
port=3306
socket=/tmp/mysql.sock

[mysqld]
port=3306
socket=/tmp/mysql.sock
key_buffer_size=16M
max_allowed_packet=8M

[mysqldump]
quick
```
Here is a typical user option file:
```
[client]
# The following password will be sent to all standard MySQL clients
password="my password"

[mysql]
no-auto-rehash
connect_timeout=2

[mysqlhotcopy]
interactive-timeout
```

### References
- [4.2.2.2 Using Option Files](https://dev.mysql.com/doc/refman/5.6/en/option-files.html)
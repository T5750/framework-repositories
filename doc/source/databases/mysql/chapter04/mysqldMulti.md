## MySQL mysqld_multi

To invoke **mysqld_multi**, use the following syntax:
```
shell> mysqld_multi [options] {start|stop|reload|report} [GNR[,GNR] ...]
```
This command starts a single server using option group [mysqld17]:
```
shell> mysqld_multi start 17
```
This command stops several servers, using option groups [mysqld8] and [mysqld10] through [mysqld13]:
```
shell> mysqld_multi stop 8,10-13
```
For an example of how you might set up an option file, use this command:
```
shell> mysqld_multi --example
```

```
# This is an example of a my.cnf file for mysqld_multi.
# Usually this file is located in home dir ~/.my.cnf or /etc/my.cnf

[mysqld_multi]
mysqld     = /usr/local/mysql/bin/mysqld_safe
mysqladmin = /usr/local/mysql/bin/mysqladmin
user       = multi_admin
password   = my_password

[mysqld2]
socket     = /tmp/mysql.sock2
port       = 3307
pid-file   = /usr/local/mysql/data2/hostname.pid2
datadir    = /usr/local/mysql/data2
language   = /usr/local/mysql/share/mysql/english
user       = unix_user1

[mysqld3]
mysqld     = /path/to/mysqld_safe
ledir      = /path/to/mysqld-binary/
mysqladmin = /path/to/mysqladmin
socket     = /tmp/mysql.sock3
port       = 3308
pid-file   = /usr/local/mysql/data3/hostname.pid3
datadir    = /usr/local/mysql/data3
language   = /usr/local/mysql/share/mysql/swedish
user       = unix_user2

[mysqld4]
socket     = /tmp/mysql.sock4
port       = 3309
pid-file   = /usr/local/mysql/data4/hostname.pid4
datadir    = /usr/local/mysql/data4
language   = /usr/local/mysql/share/mysql/estonia
user       = unix_user3

[mysqld6]
socket     = /tmp/mysql.sock6
port       = 3311
pid-file   = /usr/local/mysql/data6/hostname.pid6
datadir    = /usr/local/mysql/data6
language   = /usr/local/mysql/share/mysql/japanese
user       = unix_user4
```

### References
- [4.3.4 mysqld_multi — Manage Multiple MySQL Servers](https://dev.mysql.com/doc/refman/5.6/en/mysqld-multi.html)
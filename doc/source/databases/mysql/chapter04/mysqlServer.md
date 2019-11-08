## MySQL mysql.server

To start or stop the server manually using the mysql.server script, invoke it from the command line with start or stop arguments:
```
shell> mysql.server start
shell> mysql.server stop
```

You can add options for mysql.server in a global `/etc/my.cnf` file. A typical `my.cnf` file might look like this:
```
[mysqld]
datadir=/usr/local/mysql/var
socket=/var/tmp/mysql.sock
port=3306
user=mysql

[mysql.server]
basedir=/usr/local/mysql
```

### mysql.server Option-File Options

Option Name | Description | Type
---|---|---
basedir | Path to MySQL installation directory | Directory name
datadir | Path to MySQL data directory | Directory name
pid-file | File in which server should write its process ID | File name
service-startup-timeout | How long to wait for server startup | Integer

### References
- [4.3.3 mysql.server — MySQL Server Startup Script](https://dev.mysql.com/doc/refman/5.6/en/mysql-server.html)
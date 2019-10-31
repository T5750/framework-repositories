# MySQL Installation

## Installing MySQL on Unix/Linux Using Generic Binaries
**Warnings**
- You should also check for configuration files such as `/etc/my.cnf` or the `/etc/mysql` directory and delete them.
- `yum install libaio`

```
groupadd mysql
useradd -r -g mysql -s /bin/false mysql
cd /usr/local
wget https://cdn.mysql.com//Downloads/MySQL-5.6/mysql-5.6.46-linux-glibc2.12-x86_64.tar.gz
tar zxvf mysql-5.6.46-linux-glibc2.12-x86_64.tar.gz -C /usr/local/src/
ln -s /usr/local/src/mysql-5.6.46-linux-glibc2.12-x86_64/ mysql
cd mysql
scripts/mysql_install_db --user=mysql
bin/mysqld_safe --user=mysql &
# Next command is optional
cp support-files/mysql.server /etc/init.d/mysql.server
export PATH=$PATH:/usr/local/mysql/bin
```

## Postinstallation Setup and Testing
### Initializing the Data Directory
```
cd /usr/local/mysql
scripts/mysql_install_db --user=mysql
```

### Starting the Server
```
bin/mysqld_safe --user=mysql &
```

### Testing the Server
```
mysqladmin version
mysqladmin variables
mysqladmin -u root password 123456
mysqladmin -u root -p version
mysqladmin -u root shutdown -p123456
mysqlshow -u root -p123456
mysqlshow mysql -u root -p123456
mysql -e "SELECT User, Host, plugin FROM mysql.user" mysql -u root -p123456
```

### Securing the Initial MySQL Accounts
```
mysql -h 192.168.1.110
SET PASSWORD FOR 'root'@'localhost' = PASSWORD('123456');
SET PASSWORD FOR 'root'@'127.0.0.1' = PASSWORD('123456');
SET PASSWORD FOR 'root'@'::1' = PASSWORD('123456');
SET PASSWORD FOR 'root'@'%' = PASSWORD('123456');
mysql -h 192.168.1.110 -u root -p123456
```

### Tips
```
mysql
ERROR 2002 (HY000): Can't connect to local MySQL server through socket '/var/lib/mysql/mysql.sock' (111)
vi my.cnf
socket=/var/lib/mysql/mysql.sock
```
```
GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY '123456' WITH GRANT OPTION;
```

## References
- [Installing MySQL on Unix/Linux Using Generic Binaries](https://dev.mysql.com/doc/refman/5.6/en/binary-installation.html)
- [Postinstallation Setup and Testing](https://dev.mysql.com/doc/refman/5.6/en/postinstallation.html)
# Installation Using Yum

## Steps for a Fresh Installation of MySQL
1. Adding the MySQL Yum Repository

    For an EL6-based system, the command is in the form of:
    ```
    wget http://repo.mysql.com/mysql-community-release-el6-5.noarch.rpm
    shell> sudo yum localinstall mysql-community-release-el6-5.noarch.rpm
    ```
    For an EL7-based system:
    ```
    shell> sudo yum localinstall mysql57-community-release-el7-{version-number}.noarch.rpm
    ```
    You can check that the MySQL Yum repository has been successfully added by the following command:
    ```
    shell> yum repolist enabled | grep "mysql.*-community.*"
    ```
2. Selecting a Release Series
    ```
    shell> yum repolist all | grep mysql
    shell> sudo yum-config-manager --disable mysql57-community
    shell> sudo yum-config-manager --enable mysql56-community
    ```
    Besides **using yum-config-manager** command, you can also select a release series by editing manually the `/etc/yum.repos.d/mysql-community.repo` file. This is a typical entry for a release series' subrepository in the file:
    ```
    # Enable to use MySQL 5.6
    [mysql56-community]
    name=MySQL 5.6 Community Server
    baseurl=http://repo.mysql.com/yum/mysql-5.6-community/el/6/$basearch/
    enabled=1
    gpgcheck=1
    gpgkey=file:///etc/pki/rpm-gpg/RPM-GPG-KEY-mysql
    ```
    Verify that the correct subrepositories have been enabled and disabled by running the following command and checking its output:
    ```
    shell> yum repolist enabled | grep mysql
    ```
3. Disabling the Default MySQL Module
    ```
    shell> sudo yum module disable mysql
    ```
4. Installing MySQL
    ```
    shell> sudo yum install mysql-community-server
    ```
5. Starting the MySQL Server
    ```
    shell> sudo service mysqld start
    shell> sudo service mysqld status
    ```
6. Securing the MySQL Installation
    ```
    shell> mysql_secure_installation
    ```

## Installing Additional MySQL Products and Components with Yum
You can use the following command to list the packages for all the MySQL components available for your platform from the MySQL Yum repository:
```
shell> sudo yum --disablerepo=\* --enablerepo='mysql*-community*' list available
```
Install any packages of your choice with the following command, replacing `package-name` with name of the package:
```
shell> sudo yum install package-name
```
For example, to install MySQL Workbench:
```
shell> sudo yum install mysql-workbench-community
```
To install the shared client libraries:
```
shell> sudo yum install mysql-community-libs
```

## References
- [2.5.1 Installing MySQL on Linux Using the MySQL Yum Repository](https://dev.mysql.com/doc/refman/5.6/en/linux-installation-yum-repo.html)
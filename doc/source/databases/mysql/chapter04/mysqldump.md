## MySQL mysqldump

### Invocation Syntax
```
shell> mysqldump [options] db_name [tbl_name ...]
shell> mysqldump [options] --databases db_name ...
shell> mysqldump [options] --all-databases
```

### Option Syntax - Alphabetical Summary

Option Name | Description | Introduced
---|---|---
--add-drop-database | Add DROP DATABASE statement before each CREATE DATABASE statement | 
--add-drop-table | Add DROP TABLE statement before each CREATE TABLE statement | 
--add-drop-trigger | Add DROP TRIGGER statement before each CREATE TRIGGER statement | 
--add-locks | Surround each table dump with LOCK TABLES and UNLOCK TABLES statements | 
--all-databases | Dump all tables in all databases | 
--allow-keywords | Allow creation of column names that are keywords | 
--apply-slave-statements | Include STOP SLAVE prior to CHANGE MASTER statement and START SLAVE at end of output | 
--bind-address | Use specified network interface to connect to MySQL Server | 5.6.1
--character-sets-dir | Directory where character sets are installed | 
--comments | Add comments to dump file | 
--compact | Produce more compact output | 
--compatible | Produce output that is more compatible with other database systems or with older MySQL servers | 
--complete-insert | Use complete INSERT statements that include column names | 
--compress | Compress all information sent between client and server | 
--create-options | Include all MySQL-specific table options in CREATE TABLE statements | 
--databases | Interpret all name arguments as database names | 
--debug | Write debugging log | 
--debug-check | Print debugging information when program exits | 
--debug-info | Print debugging information, memory, and CPU statistics when program exits | 
--default-auth | Authentication plugin to use | 
--default-character-set | Specify default character set | 
--defaults-extra-file | Read named option file in addition to usual option files | 
--defaults-file | Read only named option file | 
--defaults-group-suffix | Option group suffix value | 
--delayed-insert | Write INSERT DELAYED statements rather than INSERT statements | 
--delete-master-logs | On a master replication server, delete the binary logs after performing the dump operation | 
--disable-keys | For each table, surround INSERT statements with statements to disable and enable keys | 
--dump-date | Include dump date as "Dump completed on" comment if --comments is given | 
--dump-slave | Include CHANGE MASTER statement that lists binary log coordinates of slave's master | 
--enable-cleartext-plugin | Enable cleartext authentication plugin | 5.6.28
--events | Dump events from dumped databases | 
--extended-insert | Use multiple-row INSERT syntax | 
--fields-enclosed-by | This option is used with the --tab option and has the same meaning as the corresponding clause for LOAD DATA | 
--fields-escaped-by | This option is used with the --tab option and has the same meaning as the corresponding clause for LOAD DATA | 
--fields-optionally-enclosed-by | This option is used with the --tab option and has the same meaning as the corresponding clause for LOAD DATA | 
--fields-terminated-by | This option is used with the --tab option and has the same meaning as the corresponding clause for LOAD DATA | 
--flush-logs | Flush MySQL server log files before starting dump | 
--flush-privileges | Emit a FLUSH PRIVILEGES statement after dumping mysql database | 
--force | Continue even if an SQL error occurs during a table dump | 
--help | Display help message and exit | 
--hex-blob | Dump binary columns using hexadecimal notation | 
--host | Host on which MySQL server is located | 
--ignore-table | Do not dump given table | 
--include-master-host-port | Include MASTER_HOST/MASTER_PORT options in CHANGE MASTER statement produced with --dump-slave | 
--insert-ignore | Write INSERT IGNORE rather than INSERT statements | 
--lines-terminated-by | This option is used with the --tab option and has the same meaning as the corresponding clause for LOAD DATA | 
--lock-all-tables | Lock all tables across all databases | 
--lock-tables | Lock all tables before dumping them | 
--log-error | Append warnings and errors to named file | 
--login-path | Read login path options from .mylogin.cnf | 5.6.6
--master-data | Write the binary log file name and position to the output | 
--max-allowed-packet | Maximum packet length to send to or receive from server | 
--net-buffer-length | Buffer size for TCP/IP and socket communication | 
--no-autocommit | Enclose the INSERT statements for each dumped table within SET autocommit = 0 and COMMIT statements | 
--no-create-db | Do not write CREATE DATABASE statements | 
--no-create-info | Do not write CREATE TABLE statements that re-create each dumped table | 
--no-data | Do not dump table contents | 
--no-defaults | Read no option files | 
--no-set-names | Same as --skip-set-charset | 
--no-tablespaces | Do not write any CREATE LOGFILE GROUP or CREATE TABLESPACE statements in output | 
--opt | Shorthand for --add-drop-table --add-locks --create-options --disable-keys --extended-insert --lock-tables --quick --set-charset. | 
--order-by-primary | Dump each table's rows sorted by its primary key, or by its first unique index | 
--password | Password to use when connecting to server | 
--pipe | Connect to server using named pipe (Windows only) | 
--plugin-dir | Directory where plugins are installed | 
--port | TCP/IP port number for connection | 
--print-defaults | Print default options | 
--protocol | Connection protocol to use | 
--quick | Retrieve rows for a table from the server a row at a time | 
--quote-names | Quote identifiers within backtick characters | 
--replace | Write REPLACE statements rather than INSERT statements | 
--result-file | Direct output to a given file | 
--routines | Dump stored routines (procedures and functions) from dumped databases | 
--secure-auth | Do not send passwords to server in old (pre-4.1) format | 5.6.17
--set-charset | Add SET NAMES default_character_set to output | 
--set-gtid-purged | Whether to add SET @@GLOBAL.GTID_PURGED to output | 5.6.9
--shared-memory-base-name | Name of shared memory to use for shared-memory connections | 
--single-transaction | Issue a BEGIN SQL statement before dumping data from server | 
--skip-add-drop-table | Do not add a DROP TABLE statement before each CREATE TABLE statement | 
--skip-add-locks | Do not add locks | 
--skip-comments | Do not add comments to dump file | 
--skip-compact | Do not produce more compact output | 
--skip-disable-keys | Do not disable keys | 
--skip-extended-insert | Turn off extended-insert | 
--skip-opt | Turn off options set by --opt | 
--skip-quick | Do not retrieve rows for a table from the server a row at a time | 
--skip-quote-names | Do not quote identifiers | 
--skip-set-charset | Do not write SET NAMES statement | 
--skip-triggers | Do not dump triggers | 
--skip-tz-utc | Turn off tz-utc | 
--socket | Unix socket file or Windows named pipe to use | 
--ssl | Enable connection encryption | 
--ssl-ca | File that contains list of trusted SSL Certificate Authorities | 
--ssl-capath | Directory that contains trusted SSL Certificate Authority certificate files | 
--ssl-cert | File that contains X.509 certificate | 
--ssl-cipher | Permissible ciphers for connection encryption | 
--ssl-crl | File that contains certificate revocation lists | 5.6.3
--ssl-crlpath | Directory that contains certificate revocation-list files | 5.6.3
--ssl-key | File that contains X.509 key | 
--ssl-mode | Desired security state of connection to server | 5.6.30
--ssl-verify-server-cert | Verify host name against server certificate Common Name identity | 
--tab | Produce tab-separated data files | 
--tables | Override --databases or -B option | 
--triggers | Dump triggers for each dumped table | 
--tz-utc | Add SET TIME_ZONE='+00:00' to dump file | 
--user | MySQL user name to use when connecting to server | 
--verbose | Verbose mode | 
--version | Display version information and exit | 
--where | Dump only rows selected by given WHERE condition | 
--xml | Produce XML output | 

### Examples
To make a backup of an entire database:
```
mysqldump test > backup-test.sql -u root -p123456
```
To load the dump file back into the server:
```
mysql test < backup-test.sql -u root -p123456
```
Another way to reload the dump file:
```
mysql -e "source /data/mysqldump/backup-test.sql" test -u root -p123456
```
**mysqldump** is also very useful for populating databases by copying data from one MySQL server to another:
```
mysqldump --opt db_name | mysql --host=remote_host -C db_name
```
You can dump several databases with one command:
```
mysqldump --databases db_name1 [db_name2 ...] > my_databases.sql
```
To dump all databases, use the `--all-databases` option:
```
mysqldump --all-databases > all_databases.sql -u root -p123456
```
For `InnoDB` tables, **mysqldump** provides a way of making an online backup:
```
mysqldump --all-databases --master-data --single-transaction > all_databases.sql -u root -p123456
```
For point-in-time recovery (also known as “roll-forward,” when you need to restore an old backup and replay the changes that happened since that backup)
```
mysqldump --all-databases --master-data=2 > all_databases.sql -uroot -p123456
mysqldump --all-databases --flush-logs --master-data=2 all_databases.sql -uroot -p123456
```

### Tips
```
mysqldump: Error: Binlogging on server not active
vi /usr/local/mysql/my.cnf
log_bin=mysql-bin
```

### References
- [4.5.4 mysqldump — A Database Backup Program](https://dev.mysql.com/doc/refman/5.6/en/mysqldump.html)
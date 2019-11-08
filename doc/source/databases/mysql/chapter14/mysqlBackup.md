# MySQL InnoDB Backup and Recovery

## InnoDB Backup
### Hot Backups
The **mysqlbackup** command, part of the MySQL Enterprise Backup component

### Cold Backups
1. Perform a [slow shutdown](https://dev.mysql.com/doc/refman/5.6/en/glossary.html#glos_slow_shutdown) of the MySQL server and make sure that it stops without errors.
1. Copy all `InnoDB` data files (`ibdata` files and `.ibd` files) into a safe place.
1. Copy all the `.frm` files for `InnoDB` tables to a safe place.
1. Copy all `InnoDB` log files (`ib_logfile` files) to a safe place.
1. Copy your `my.cnf` configuration file or files to a safe place.

### Logical Backups Using mysqldump
In addition to physical backups, it is recommended that you regularly create logical backups by dumping your tables using [mysqldump](https://dev.mysql.com/doc/refman/5.6/en/mysqldump.html).

### Tips
- `backup-mysql.sh`

## InnoDB Recovery
### Point-in-Time Recovery


### Recovery from Data Corruption or Disk Failure


### InnoDB Crash Recovery
The InnoDB crash recovery process consists of several steps:
- Redo log application
- Roll back of incomplete transactions
- Change buffer merge
- Purge

## References
- [14.18 InnoDB Backup and Recovery](https://dev.mysql.com/doc/refman/5.6/en/innodb-backup-recovery.html)
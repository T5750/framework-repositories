#!/bin/bash
prefix="`date '+%Y-%m-%d %T'` INFO"
suffix="--- [sh] MySQL Backup: "
today="_`date "+%Y%m%d"`"
mysql_user="root"
mysql_password="123456"
mysql_host="localhost"
db_array=("mysql" "test")
backup_dir="/data/mysqldump"
cd $backup_dir
for db in ${db_array[@]}
do
	/usr/bin/mysqldump --opt --master-data --single-transaction -u$mysql_user -p$mysql_password -h$mysql_host $db > $backup_dir/$db$today.sql
	printf "%s %-5s %s\n" "$prefix" $$ "$suffix$db" >> /var/spool/cron/backup-mysql.log
done
scp -r -p * root@192.168.1.101:$backup_dir
printf "%s %-5s %s %s %s\n" "$prefix" $$ "$suffix""scp" "$backup_dir" >> /var/spool/cron/backup-mysql.log
rm -f $backup_dir/*.sql
printf "%s %-5s %s %s %s\n" "$prefix" $$ "$suffix""rm" "$backup_dir" >> /var/spool/cron/backup-mysql.log
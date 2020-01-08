#!/bin/bash
logs_home="/usr/local/nginx/logs"
log_path=$logs_home/access.log
backup_path=$logs_home/backup/access.log
save_path=$logs_home/backup/access_$(date +%Y%m%d -d 'yesterday').log
cat $log_path >> $backup_path
cp -avx $log_path $save_path && echo > $log_path
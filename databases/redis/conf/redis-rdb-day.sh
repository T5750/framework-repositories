#!/bin/sh
cur_date=`date +%Y%m%d%k`   #当前时间精确到小时
rm -rf /usr/local/redis-3.2.9/backups/$cur_date   #删除当前时间的目录
mkdir /usr/local/redis-3.2.9/backups/$cur_date  #新建当前时间的目录
cp /usr/local/redis-3.2.9/dump.rdb /usr/local/redis-3.2.9/appendonly.aof /usr/local/redis-3.2.9/backups/$cur_date   #将rdb文件copy到当前时间创建的目录
del_date=`date -d -1month +%Y%m%d`  #一个月之前的时间
rm -rf /usr/local/redis-3.2.9/backups/$del_date   #删除一个月之前的目录

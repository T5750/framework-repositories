#!/bin/bash
used=`free -m | awk 'NR==2' | awk '{print $3}'`
free=`free -m | awk 'NR==2' | awk '{print $4}'`
prefix="`date '+%Y-%m-%d %T'` INFO $$ --- [sh] Mem: [used: ${used}M][free: ${free}M]"

if [ $free -le 2000 ] ; then
	sync && echo 1 > /proc/sys/vm/drop_caches
	echo "$prefix Ram-cache Cleared" >> /var/spool/cron/dropcache.log
else
	echo "$prefix Not required" >> /var/spool/cron/dropcache.log
fi
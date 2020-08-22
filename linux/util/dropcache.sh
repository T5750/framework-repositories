#!/bin/bash
used=`free -m | awk 'NR==2' | awk '{print $3}'`
free=`free -m | awk 'NR==2' | awk '{print $4}'`
prefix="`date '+%Y-%m-%d %T'` INFO"
suffix="--- [sh] Mem: [used: ${used}M][free: ${free}M]"

if [ $free -le 2000 ] ; then
    sync && echo 1 > /proc/sys/vm/drop_caches
    suffix="$suffix Ram-cache Cleared"
else
    suffix="$suffix Not required"
fi
printf "%s %-5s %s\n" "$prefix" $$ "$suffix" >> /var/spool/cron/dropcache.log
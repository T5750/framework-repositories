#!/bin/bash
prefix="`date '+%Y-%m-%d %T'` INFO"
suffix="--- [sh]"
hn=`hostname`
mem_total=`free -m | awk 'NR==2' | awk '{print $2}'`
mem_used=`free -m | awk 'NR==2' | awk '{print $3}'`
vers=`cat /etc/redhat-release`
ipp=`ifconfig |grep 'inet addr' |grep -v '127.0.0.1' |awk -F ' ' '{print $2}' |awk -F ':' '{print $2}' |sed -n 1p`
pycpu=`cat /proc/cpuinfo | grep "physical id"|sort | uniq | wc -l`
locpu=`cat /proc/cpuinfo | grep "processor"| wc -l`
cpu_type=`grep "model name" /proc/cpuinfo | awk -F ': ' '{print $2}' | sort | uniq`
cpu_arch=`uname -m`
selinux=`/usr/sbin/sestatus | grep "SELinux status: " | awk '{print $3}'`
uptime=`uptime | sed 's/.*up \([^,]*\), .*/\1/'`
gtway01=`cat /etc/sysconfig/network|grep GATEWAY|awk -F "=" '{print $2}'`
gtway02=`netstat -rn | awk '/^0.0.0.0/ {print $2}'`

printf "%s %-5s %s\n" "$prefix" $$ "$suffix hostname: $hn"
printf "%s %-5s %s\n" "$prefix" $$ "$suffix Total memory: $mem_total MB"
printf "%s %-5s %s\n" "$prefix" $$ "$suffix Used memory: $mem_used MB"
printf "%s %-5s %s\n" "$prefix" $$ "$suffix OS version: $vers"
printf "%s %-5s %s\n" "$prefix" $$ "$suffix IP address: $ipp"
printf "%s %-5s %s\n" "$prefix" $$ "$suffix Physical CPU: $pycpu"
printf "%s %-5s %s\n" "$prefix" $$ "$suffix Logical CPU: $locpu"
printf "%s %-5s %s\n" "$prefix" $$ "$suffix CPU Type: $cpu_type"
printf "%s %-5s %s\n" "$prefix" $$ "$suffix CPU Arch: $cpu_arch"
printf "%s %-5s %s\n" "$prefix" $$ "$suffix SELINUX: $selinux"
printf "%s %-5s %s\n" "$prefix" $$ "$suffix uptime: $uptime"
if [ "${gtway01}" = "" ];then
    printf "%s %-5s %s\n" "$prefix" $$ "$suffix Server gateway: $gtway02"
else
    printf "%s %-5s %s\n" "$prefix" $$ "$suffix Server gateway: $gtway01"
fi
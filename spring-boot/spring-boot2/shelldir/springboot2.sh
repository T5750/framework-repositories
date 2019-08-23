#!/bin/bash
version="1.0.1";
appName=$2
prefix="`date '+%Y-%m-%d %T'` INFO $$ --- [jar]"
if [ -z $appName ];then
	appName=`ls -t |grep .jar$ |head -n1`
fi

function start() {
	count=`ps -ef |grep java|grep $appName|wc -l`
	if [ $count != 0 ];then
		echo "$prefix Maybe $appName is running, please check it..."
	else
		echo "$prefix The $appName is starting..."
		nohup java -jar -Xms512M -Xmx512M -XX:+UseG1GC -XX:+HeapDumpOnOutOfMemoryError ./$appName >/dev/null 2>&1 &
	fi
}

function stop() {
	appId=`ps -ef |grep java|grep $appName|awk '{print $2}'`
	if [ -z $appId ];then
		echo "$prefix Maybe $appName not running, please check it..."
	else
		echo "$prefix The $appName is stopping..."
		kill $appId
	fi
}

function restart() {
	# get release version
	releaseApp=`ls -t |grep .jar$ |head -n1`
	# get last version
	lastVersionApp=`ls -t |grep .jar$ |head -n2 |tail -n1`
	appName=$lastVersionApp
	stop
	for i in {5..1}
	do
		echo -n "$i "
		sleep 1
	done
	echo 0
	backup
	appName=$releaseApp
	start
}

function backup() {
	# get backup version
	backupApp=`ls |grep -wv $releaseApp$ |grep .jar$`
	# create backup dir
	if [ ! -d "backup" ];then
		mkdir backup
	fi
	# backup
	for i in ${backupApp[@]}
	do
		echo "$prefix backup" $i
		mv $i backup
	done
}

function status() {
	appId=`ps -ef |grep java|grep $appName|awk '{print $2}'`
	if [ -z $appId ]
	then
		echo -e "$prefix \033[31m Not running \033[0m"
	else
		echo -e "$prefix \033[32m Running [$appId] \033[0m"
	fi
}

function usage() {
	echo "Usage: $0 {start|stop|restart|status -f}"
	echo "Example: $0 start"
	exit 1
}

case $1 in
	start)
	start;;
	stop)
	stop;;
	restart)
	restart;;
	status)
	status;;
	*)
	usage;;
esac
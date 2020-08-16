#!/bin/bash
source /etc/profile
tomcatMonitorLog=/home/bm/logs/tomcatMonitor.log

checkTomcat() {
    tomcatPath=$1;
    now=`date '+%Y-%m-%d %T'`
    echo "$now start check $tomcatPath ..."
    tomcatPid=`ps aux|grep "java"|grep $tomcatPath|awk '{printf $2}'`
    if test -z ${tomcatPid}
    then
        echo "stoped"
        #su - root -lc "sh $tomcatPath/bin/startup.sh" # 以 root 的身份运行 tomcat 实例
        sh $tomcatPath/bin/startup.sh
        echo "starting..."
    else
        echo "running, pid: $tomcatPid"
    fi
}

checkTomcat "/usr/local/tomcat-8.5.55" >> $tomcatMonitorLog
#checkTomcat "/home/utomcat/tomcat/backend/" >> $tomcatMonitorLog
echo '--------------' >> $tomcatMonitorLog
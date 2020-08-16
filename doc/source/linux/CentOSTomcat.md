# CentOS Tomcat

## Init Script
- `mkdir -p ~/shell`
- `cd ~/shell/`
- `vi tomcat-startup.sh`
```
#!/bin/bash
export JAVA_HOME=/usr/local/jdk1.8.0_181
cd /usr/local/tomcat-8.5.55/bin/
./startup.sh
```
- `vi tomcat-shutdown.sh`
```
#!/bin/bash
export JAVA_HOME=/usr/local/jdk1.8.0_181
cd /usr/local/tomcat-8.5.55/bin/
./shutdown.sh
```
- `chmod +x *.sh`

- `su root`
- `vi /etc/init.d/tomcat`
```
#!/bin/bash
#/etc/rc.d/init.d/tomcatservice
#init script for tomcatservice precesses
#processname: tomcatservice
#chkconfig: 2345 86 16
#description: tomcatservice is a j2se server， Start up | shutdown the Tomcat servlet engine.
export JAVA_HOME=/usr/local/jdk1.8.0_181
case $1 in
    start)
        su - bm -lc "sh /home/bm/shell/tomcat-startup.sh";;
    stop)
        su - bm -lc "sh /home/bm/shell/tomcat-shutdown.sh";;
    *)
        echo "parameter error, usage:(start|stop)";;
esac
```
- `chmod +x /etc/init.d/tomcat`

### chkconfig
```
chkconfig --add /etc/init.d/tomcat
chkconfig tomcat on
chkconfig --list|grep tomcat
service tomcat start
service tomcat stop
```

## Monitor
- `vi /usr/local/tomcat-8.5.55/bin/catalina.sh`: `export JAVA_HOME=/usr/local/jdk1.8.0_181`
- `mkdir -p ~/logs`
- `vi ~/shell/tomcatMonitor.sh`
- `chmod +x ~/shell/tomcatMonitor.sh`
- `crontab -e`
```
*/3 * * * * /home/bm/shell/tomcatMonitor.sh > /dev/null 2>&1
```

## References
- [在 CentOS 上以非 root 用户运行 tomcat，并配置成开机自启动](https://uncleandychen.gitee.io/linux/centOS7TomcatUserConfig.html)
- [CentOS 7，tomcat 监控脚本](https://uncleandychen.gitee.io/linux/centOS7TomcatMonitor.html)
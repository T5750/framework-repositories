[PREV 7.x 软件包管理](LinuxNoteYum.md)

## 8.1.1 Shell编程
Shel编程的语法非常简单，使用`sh example`运行脚本程序即可：
```
#!/bin/bash
echo "Hello Shell!"
/bin/pwd
echo
/bin/ls
```

## 8.1.2 Shell应用实例
如果想使用Shell脚本指定一个计划任务，比如每周的周一到周五给管理员发一个信息（比如：当前主机的信息，内存使用情况，在线人数，磁盘空间等）：
```
#!/bin/bash
/bin/date +%F >> /home/shelldir/send-root.info
echo >> /home/shelldir/send-root.info
echo "Disk info: " >> /home/shelldir/send-root.info
/bin/df -h >> /home/shelldir/send-root.info
echo >> /home/shelldir/send-root.info
echo "Online users: " >> /home/shelldir/send-root.info
echo >> /home/shelldir/send-root.info
/usr/bin/who | /bin/grep -v root >> /home/shelldir/send-root.info
echo "Memory info: " >> /home/shelldir/send-root.info
/usr/bin/free -m >> /home/shelldir/send-root.info
echo >> /home/shelldir/send-root.info
/usr/bin/write root < /home/shelldir/send-root.info && /bin/rm /home/shelldir/send-root.info
```

[PREV 7.x 软件包管理](LinuxNoteYum.md)
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

## 8.2.1 Shell变量
变量：是Shell传递数据的一种方法，用来代表每个取值的符号名

Shell有两类变量：临时变量和永久变量
- 临时变量是Shell程序内部定义的，其使用范围仅限于定义它的程序，对其它程序不可见。包括：用户自定义变量、位置变量
- 永久变量是环境变量，其值不随Shell脚本的执行结束而消失
	```
	echo $PATH
	echo $LANG
	```

## 8.2.2 自定义变量
- 用户定义的变量由字母或下划线开头，由字母、数字或下划线序列组成，并且大小写字母意义不同
- 变量名长度没有限制。在使用变量值时，要在变量名前加上前缀"$"
- 一般变量使用大写字母表示，并且是英文字母开头，赋值号"="两边应没有空格，如`NUM=5`、`STR="A String"`
- 可以将一个命令的执行结果赋值给变量，但是需要使用命令替换符号
- 注意：单引号和双引号的区别
    - ""号：会把里面的变量值进行输出
    - ''号：会把内容原封不动输出，不会识别里面的变量
- 使用set命令查看所有的变量
- 使用unset命令删除指定的变量

```
[root]# P1=10
[root]# echo $P1
10
[root]# P1="A String"
[root]# set | grep P1
P1='A String'
[root]# P1=`date`
[root]# echo $P1
Thu Feb 14 05:46:49 CST 2019
[root]# P2=$P1
[root]# echo $P2
Thu Feb 14 05:46:49 CST 2019
[root]# P2="time is $P1"
[root]# echo $P2
time is Thu Feb 14 05:46:49 CST 2019
[root]# P2='time is $P1'
[root]# echo $P2
time is $P1
[root]# set
[root]# unset P2
```


[PREV 7.x 软件包管理](LinuxNoteYum.md)
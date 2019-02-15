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

## 8.2.3 占位变量
在Shell里面还有2种特殊的变量，一种是位置变量，还有种是特殊的变量，在编写Shell时十分常用
- 位置变量：`ls -l file1 file2 file3...(n范围=1-9)`在代码里使用$0-9进行替代
	```
	sh placeholder-variable.sh /home /home/shelldir/ /usr
	```

## 8.2.4 特殊变量
```
sh /home/shelldir/special-variable.sh /home /home/shelldir/ /usr
Today is 2019-02-15
$#:  3
$*:  /home /home/shelldir/ /usr
$?:  0
$$:  2160
$0:  /home/shelldir/special-variable.sh
```
- `$*`：这个程序的所有参数
- `$#`：这个程序的参数个数
- `$$`：这个程序的PID
- `$!`：执行上一个后台命令的PID
- `$?`：执行上一个命令的返回值
- `$(0-9)`：显示位置变量

## 8.2.5 read键盘录入
read从键盘读入数据，赋给变量
```
read f s t
echo "The first is $f"
echo "The second is $s"
echo "The third is $t"
```
```
sh -x /home/shelldir/read.sh
+ read f s t
10 20 30
+ echo 'The first is 10'
The first is 10
+ echo 'The second is 20'
The second is 20
+ echo 'The third is 30'
The third is 30
```

## 8.2.6 Shell运算
expr命令，对整数进行运算。注意点：
1. expr的运算必须用空格间隔开
2. `\*`表示转义字符
3. 保持先算乘除后算加减，如果需要优先运算，则需要加命令替换符
4. 也可以对变量进行运算操作

```
[root]# expr 10 + 5
15
[root]# expr 10 - 5
5
[root]# expr 10 / 5
2
[root]# expr 10 \* 5
50
[root]# expr 10 - 3 \* 2
4
[root]# expr `expr 10 - 3` \* 2 
14
[root]# NUM=30
[root]# echo `expr $NUM + 8`
38
```

## 8.3.1 测试命令
使用test命令可以对文件、字符串等进行测试，一般配合控制语句使用，不应该单独使用。如下:
- 字符串测试：
    - `test str1=str2`：测试字符串是否相等
    - `test str1!=str2`：测试字符串是否不相等
    - `test str1`：测试字符串是否不为空
    - `test -n str1`：测试字符串是否不为空
    - `test -z str1`：测试字符串是否为空
- int测试：
    - `test int1 -eq int2`：测试整数是否相等
    - `test int1 -ne int2`：测试整数是否不相等
    - `test int1 -ge int2`：测试int1是否>=int2
    - `test int1 -gt int2`：测试int1是否>int2
    - `test int1 -le int2`：测试int1是否<=int2
    - `test int1 -lt int2`：测试int1是否<int2
- 文件测试：
    - `test -d file`：指定文件是否目录
    - `test -f file`：指定文件是否常规文件
    - `test -x file`：指定文件是否可执行
    - `test -r file`：指定文件是否可读
    - `test -w file`：指定文件是否可写
    - `test -a file`：指定文件是否存在
    - `test -s file`：文件的大小是否非0

## 8.3.2 if语句
语法格式：`if test -d $1 then ... else ... fi`
- 变量测试语句可用[]进行简化，如`test -d $1`等价于`[ -d $1 ]`
```
if [ -d $1 ]
then
        echo "This is a directory!"
else
        echo "This is not a directory!"
fi
```
注意：["空格" -d $1 "空格"]

## 8.3.3 if elif语句
语法格式：
```
if [ -d $1 ]
then ...
elif [ -f $1 ]
then ...
else ...
fi
```
```
if [ -d $1 ]
then
        echo "This is a directory!"
elif [ -f $1 ]
then
        echo "This is a file!"
else
        echo "Error!"
fi
```

## 8.3.4 逻辑与和逻辑或
使用`-a`和`-o`表示逻辑与和逻辑或
```
if [ $1 -eq $2 -a $1 = 1 ]
then
        echo "param1 == param2 and param1 = 1"
elif [ $1 -ne $2 -o $1 = 2 ]
then
        echo "param1 != param2 or param1 = 2"
else
        echo "others"
fi
```

[PREV 7.x 软件包管理](LinuxNoteYum.md)
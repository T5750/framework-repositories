# Linux笔记 Chapter 4

[PREV 3.x vi/vim文本编辑器](LinuxNoteVim.md) | [NEXT 5.x 进程管理](LinuxNoteProcess.md)

## 4.1.1 用户管理配置文件
- 用户信息文件：`/etc/passwd`
- 密码文件：`/etc/shadow`
- 用户配置文件：`/etc/login.defs`，`/etc/default/useradd`
- 新用户信息文件：`/etc/skel`
- 用户组文件：`/etc/group`
- 用户组密码文件：`/etc/gshadow`

## 4.1.2 用户信息文件
用户信息文件：`/etc/passwd`

字段 | 含义
---|-----
用户名 | 用户登录系统时使用的用户名
密码 | 密码位
UID | 用户标识号
GID | 缺省组标识号
注释性措述 | 例如：存放用户全名等信息
宿主日录 | 用户登录系统后的缺省目录
命令解释器 | 用户使用的Shell，默认为bash

## 4.1.3 用户类型
Linux用户一般分为三种
1. 超级用户（root UID = 0）一般UID为0的用户就是超级用户，但root权限特别的大，很多Linux操作命令，都会不考虑root
1. 普通用户（UID 500-60000）添加则按照UID = 500开始默认递增
1. 伪用户（UID 1-499）一般是Linux系统和进程服务相关的，比如bin、daemon、shutdown等，任何的Linux系统都会有这些伪用户，比如进行关机操作，其实就调用了一个系统的伪用户的身份。在Linux系统里任何的进程操作都必须要有一个用户身份，伪用户一般无法登录系统

## 4.1.4 密码文件

字段 | 含义
---|-----
用户名 | 用户登录系统时使用的用户名
密码 | 加密密码
最后一次修改时间 | 用户最后一次修改密码的天数
最小时间间隔 | 两次修改密码之间的最小天数
最大时间间隔 | 密码保持有效的最多天数
警告时间 | 从系统开始警告到密码失效的天数
帐号闲置时间 | 帐号闲置时间
失效时间 | 密码失效的绝对天数
标志 | 一般不使用

密码写入和密码回写命令：pwconv、pwunconv

## 4.1.5 用户配置文件
`/etc/login.defs`定义了添加用户的缺省信息
```
MAIL_DIR  /var/spool/mail
PASS_MAX_DAYS 99999
PASS_MIN_DAYS 0
PASS_MIN_LEN 5
PASS_WARN_AGE 7
UID_MIN  500
GID_MIN  500
GID_MAX  60000
UID_MAX  60000
CREATE_HOME  yes
UMASK  077
```
`/etc/default/useradd`
```
GROUP=100
HOME=/home
INACTIVE=-1
EXPIRE=
SHELL=/bin/bash
SKEL=/etc/skel
CREATE_MAIL_SPOOL=yes
```
- GROUP：用户组定义
- HOME：用户的缺省宿主目录
- INACTIVE：是否禁用账号，如果是0就是禁用
- EXPRIE：账号失效时间
- SHELL：缺省的解释器
- SKEL：新添加用户的一些默认配置文件位置
- CREATE_MAIL_SPOOL：添加用户后，是否为该用户创建一个邮件目录

## 4.1.6 用户组
- 每个用户至少属于一个用户组
- 每个用户组可以包括多个用户
- 同一用户组的用户享有该组共有的权限
- `/etc/group`文件格式：

字段 | 含义
---|-----
组名 | 用户登录时所在的组
组密码 | 一般不使用
GID | 组标识号
组内用户列表 | 属于该组的所有用户列表

## 4.2.1 用户组管理命令
- 添加用户组：`groupadd [配置选项] [组名]`
    - 形如：`groupadd -g 1001 webs`
- 删除用户组：`groupdel [组名]`
    - 形如：`groupdel webs`
- 修改用户组：`groupmod -n [新组名] [旧组名]`
- 查看用户属于哪些组：`groups root`

## 4.2.2 用户管理命令
添加用户useradd设置选项用户名`-D`查看缺省参数
```
useradd -u 1002 -g webapps -G sys,root -d /home/user2 -s /bin/bash -c "is a user2" -e 2019-02-13 user2
```
- u：UID
- g：缺省所属用户组的名称或GID
- G：指定用户所属多个组
- d：宿主目录
- s：命令解释器Shell
- c：描述信息
- e：指定用户失效时间

修改用户：`usermod -l [新用户名] [旧用户名]`
- `usermod -l user3 user2`

删除用户：`userdel -r [用户名]`
- `userdel -r user3`

设置用户的禁用：
- `usermod -L [用户名]`
- `passwd -l [用户名]`

设置用户的恢复：
- `usermod -U [用户名]`
- `passwd -u [用户名]`

## 4.2.3 用户组操作
gpasswd：管理组内成员
- `-a`：添加用户到用户组中，`gpasswd -a user2 webapps`
- `-d`：从用户组中删除用户，`gpasswd -d user2 root`
- `-A`：设置用户组管理员
- `grep user2 /etc/group`

## 4.2.4 用户组授权
- 给一个文件授权很简单，就是使用chmod命令加上相应的权限即可，比如`chmod u+rwx`或`chnmod 751`即可，这只能为其中一个用户设置权限
- 如果想要多个普通用户同时对这个文件有读写权限，那么，使用用户组授权
	```
	groupadd g1
	gpasswd -a u1 g1
	gpasswd -a u2 g1
	chgrp g1 /home
	chmod g+rwx
	```

[PREV 3.x vi/vim文本编辑器](LinuxNoteVim.md) | [NEXT 5.x 进程管理](LinuxNoteProcess.md)
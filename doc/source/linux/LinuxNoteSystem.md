# Linux笔记 Chapter 6

[PREV 5.x 进程管理](LinuxNoteProcess.md) | [NEXT 7.x 软件包管理](LinuxNoteYum.md)

## 6.1 系统文件构成
- /usr/bin、/bin：存放所有用户可以执行的命令
- /usr/sbin、/sbin：存放只有root可以执行的命令
- /home：用户缺省的宿主目录
- /proc：虚拟文件系统，存放当前进程信息
- /dev：存放设备文件
- /lib：存系统程序运行所需的共享库
- /lost+found：存放系统出错的检查结果
- /tmp：存放临时文件
- /etc：存放系统配置文件
- /var：包含经常发生变动的文件，如日志文件、计划任务等
- /usr：存放所有命令、库、手册等
- /boot：内核文件及自举程序文件保存位置
- /mnt：临时文件系统的安装点

[PREV 5.x 进程管理](LinuxNoteProcess.md) | [NEXT 7.x 软件包管理](LinuxNoteYum.md)
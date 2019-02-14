[PREV 6.x 系统文件构成](LinuxNoteSystem.md) | [NEXT 8.x Shell编程](LinuxNoteShell.md)

## 7.1 软件包管理
- 二进制软件包管理（RPM、YUM）
- YUM：应用的好处
    - 自动解决软件包依赖关系
    - 方便的软件包升级
- 查找软件包`yum search [软件包名称]`
- 安装`yum install [软件包名称]`
- 检测升级`yum check-update [软件包名称]`
- 升级`yum update [软件包名称]`
- 软件包查询`yum list | grep [软件包名称]`
- 软件包信息`yum info [软件包名称]`
- 卸载`yum remove [软件包名称]`
- 帮助`yum -help`、`man yum`
- 安装gcc（`yum clean all`）
- 安装telnet

[PREV 6.x 系统文件构成](LinuxNoteSystem.md) | [NEXT 8.x Shell编程](LinuxNoteShell.md)
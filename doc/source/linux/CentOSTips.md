# CentOS Tips

## lrzsz
- [lrzsz官网](http://freshmeat.sourceforge.net/projects/lrzsz/)
- `yum -y install lrzsz`
- `sz /home/shelldir/*.sh`：下载目录

## iptables
- 暂时关闭/打开
    - `service iptables stop`
    - `service iptables start`
- 永久打开/关闭
    - `chkconfig iptables on`
    - `chkconfig iptables off`
- 验证
    - `chkconfig --list | grep iptables`

## network
- `service network restart`

## ntpdate
- `yum -y install ntp ntpdate`
- `vim /etc/rc.d/rc.local`
    - `/usr/sbin/ntpdate cn.pool.ntp.org`

## telnet
- `yum install telnet`

## zip
- `yum -y install zip`

## Commands
### bc
- `scale=2`

## sar
- `yum install sysstat`

## mail
- `yum -y install mailx`
- `yum install sendmail`

## vim
- `yum -y install vim*`

## sudo
```
sudo visudo
sysadmin ALL=(ALL) NOPASSWD:ALL
```

## AppStream
```
cd /etc/yum.repos.d
sudo mkdir backup
sudo mv *.repo backup/
sudo curl -o /etc/yum.repos.d/Centos-vault-8.5.2111.repo https://mirrors.aliyun.com/repo/Centos-vault-8.5.2111.repo
yum clean all && yum makecache
```

## References
- [Centos8 yum安装报错Error: Failed to download metadata for repo ‘AppStream‘](https://www.cnblogs.com/Fengdengshuai/p/15988606.html)
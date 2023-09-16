# CentOS Tips

```sh
yum list installed | grep gcc
yum list installed | grep lrzsz
```

## lrzsz
- [lrzsz官网](http://freshmeat.sourceforge.net/projects/lrzsz/)
- `yum -y install lrzsz`
- `sz /home/shelldir/*.sh`：下载目录

### 离线安装
[lrzsz-0.12.20.tar.gz](https://ohse.de/uwe/software/lrzsz.html)

```sh
mkdir -p ~/tool/lrzsz/
mv lrzsz-0.12.20.tar.gz ~/tool/lrzsz/
cd ~/tool/lrzsz/
tar -zxvf lrzsz-0.12.20.tar.gz
cd lrzsz-0.12.20
./configure --prefix=~/tool/lrzsz/
make && make install
```
```sh
cd ~/tool/lrzsz/bin
ln -s lsz sz
ln -s lrz rz
#修改环境变量，在~/.bashrc文件末尾加入export PATH="~/tool/lrzsz/bin:$PATH"
vi ~/.bashrc
source ~/.bashrc
```

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
- [lrzsz离线安装方法](https://www.cnblogs.com/lcgbk/p/14848852.html)
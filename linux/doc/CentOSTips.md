# CentOS Tips

## lrzsz
- [lrzsz官网](http://freshmeat.sourceforge.net/projects/lrzsz/)
- `yum -y install lrzsz`

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
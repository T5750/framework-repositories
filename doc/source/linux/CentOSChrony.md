# CentOS Chrony

```
sudo yum install -y chrony
sudo systemctl enable chronyd.service
sudo systemctl restart chronyd.service
sudo systemctl status chronyd.service
sudo firewall-cmd --add-service=ntp --permanent
sudo firewall-cmd --reload
```
```
timedatectl
#timedatectl list-timezones | grep -E "Asia/S.*"
#timedatectl set-timezone Asia/Shanghai
sudo chronyc -a makestep
#sudo chronyc sources -v
#sudo chronyc sourcestats -v
#timedatectl set-local-rtc 1
sudo timedatectl set-ntp yes
sudo chronyc tracking
sudo netstat -antup | grep chrony
```

## References
- [Chrony详解：代替ntp的时间同步服务](https://chegva.com/3265.html)
- [chrony.conf(5) Manual Page](https://chrony.tuxfamily.org/doc/devel/chrony.conf.html)
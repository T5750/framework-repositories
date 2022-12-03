# nginx Init Script

`vi /etc/init.d/nginx`
- [Red Hat NGINX Init Script](https://www.nginx.com/resources/wiki/start/topics/examples/redhatnginxinit/)
- `/usr/sbin/nginx` -> `/usr/local/nginx/sbin/nginx`
- `/etc/nginx/nginx.conf` -> `/usr/local/nginx/conf/nginx.conf`

```
chmod a+x /etc/init.d/nginx
/usr/local/nginx/sbin/nginx
/etc/init.d/nginx start
/etc/init.d/nginx stop
```

## chkconfig
- `chkconfig --add /etc/init.d/nginx`
- `service nginx start`
- `service nginx stop`
- `chkconfig nginx on`

## References
- [Linux(CentOS)下设置nginx开机自动启动](https://www.cnblogs.com/whatmiss/p/7091220.html)
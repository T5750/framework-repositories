# Linux Boot

## rc.local
The `rc.local` script in some Linux distributions and Unix systems is a superuser startup script, usually located under the directory `/etc/etc/rc.d`. The file name rc refers to Run Control.
```sh
sudo vi /etc/rc.local
sudo chmod +x /etc/rc.local
#sudo chmod +x /etc/rc.d/rc.local
```
```sh
sudo systemctl enable rc-local
sudo systemctl start rc-local.service
sudo systemctl status rc-local.service
```

## Systemd
Systemd is a services manager who assigns services control groups (cgroup) and tracks processes. Systemd is the process (PID) 1 responsible for the system startup.

`sudo vi /etc/systemd/system/linuxhint.service`
```
[Unit]
Description= <Script name or description>
[Service]
ExecStart=/bin/bash /usr/sbin/linuxhint.sh  #in this line specify the path to the script.
[Install]
WantedBy=multi-user.target
```
```sh
#sudo systemctl enable linuxhint
sudo systemctl start linuxhint
sudo systemctl status linuxhint
```

### [Unit]
- **Description=** This directive describes the unit; you can set the unit name.
- **Requires=** Here, you can specify dependencies to prevent startup failures.
- **Wants=** Like the previous, it keeps the service working even if it doesn’t find the defined dependencies.
- **After=** The unit will start after the specified in this directive.

### [Service]
- **Type=** In the example shown above, _**forking**_ indicates the service will be killed, keeping child processes that must be assigned a PID.
- **PIDFile=** Forking directive requires the PIDFile directive, which must contain the path to the file pid of the child process for Systemd to identify it.
- **ExecStart=** Here, you specify the path and commands you want to be executed. This is similar to the rc.local file.
- **Restart=** This directive instructs Systemd when to restart the unit. The available options are on-failure, on-abort, always, on-success, on-watchdog, or on-abnormal.
- **StartLimitInterval=** This directive indicates the unit has 60 seconds for 10 attempts to restart upon failure.
- **StartLimitBurst=** This directive indicates the attempts limit, in the example above, 10 attempts in 60 seconds.

### [Install]
- **WantedBy=** Here, you can specify this unit as a dependency; it is similar to the Wants directive, but to define the current unit is considered a dependency by another unit.

**Note**: You can check all Systemd directives at
[https://www.freedesktop.org/software/systemd/man/systemd.directives.html](https://www.freedesktop.org/software/systemd/man/systemd.directives.html)

## Remove service
```sh
sudo systemctl stop linuxhint
sudo systemctl disable linuxhint
sudo rm /etc/systemd/system/linuxhint.service
#sudo rm /etc/systemd/system/xxx symlinks that might be related
sudo systemctl daemon-reload
sudo systemctl reset-failed
```

## Tips
```sh
# 10. 检查某个单元（如 cron.service）是否启用
systemctl is-enabled docker.service
# 11. 检查某个单元或服务是否运行
systemctl status docker.service
# 12. 列出所有服务（包括启用的和禁用的）
systemctl list-unit-files --type=service
# 22. 在Linux中启动、重启、停止、重载套接口并检查其状态
systemctl start cups.socket
systemctl restart cups.socket
systemctl stop cups.socket
systemctl reload cups.socket
systemctl status cups.socket
# 27. 检查某个服务的所有配置细节
systemctl show httpd
```

## References
- [How to use /etc/rc.local at boot](https://linuxhint.com/use-etc-rc-local-boot/)
- [CentOS使用systemctl彻底删除服务](https://ruiruigeblog.com/2017/01/21/CentOS%E4%BD%BF%E7%94%A8systemctl%E5%BD%BB%E5%BA%95%E5%88%A0%E9%99%A4%E6%9C%8D%E5%8A%A1/)
- [systemctl 命令完全指南](https://linux.cn/article-5926-1.html)
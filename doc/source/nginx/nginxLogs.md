# nginx Logs

## 对access.log进行分割
通过shell脚本+linux的定时任务进行平滑切分，不需要重启nginx。代码`nginx-logs.sh`
```
#!/bin/bash
logs_home="/usr/local/nginx/logs"
log_path=$logs_home/access.log
backup_path=$logs_home/backup/access.log
save_path=$logs_home/backup/access_$(date +%Y%m%d -d 'yesterday').log
cat $log_path >> $backup_path
cp -avx $log_path $save_path && echo > $log_path
```
设置定时任务
```
$ crontab -e
0 0 * * * sh nginx-logs.sh
```

## 不记录特定IP的访问记录
### 利用 map 指令实现
- `map` 指令是由 `ngx_http_map_module` 模块提供的，默认自动加载
- `access_log` 默认使用 `combined` 的配置来记录访问日志

```
# /data/app/nginx/conf/conf.d/xxx.conf
map $remote_addr $log_ip {
    "192.168.1.100" 0;
    default 1;
}

server {
    access_log /data/logs/nginx/xxx.access.log combined if=$log_ip;
}
```

### 利用 if 匹配实现
```
# /data/app/nginx/conf/conf.d/xxx.conf
server {
    location / {
        if ($remote_addr = "192.168.1.100") {
            access_log off;
        }
    }
}
```

## References
- [Nginx access.log文件太大，自动释放清理](https://www.uedbox.com/post/59024/)
- [Nginx access_log 不记录特定IP的访问记录](https://www.xuxuyu.com/2017-12-07/disable-logging-in-nginx-for-certain-ip/)
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

## References
- [Nginx access.log文件太大，自动释放清理](https://www.uedbox.com/post/59024/)
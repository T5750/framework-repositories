## Redis数据备份
测试`redis-rdb-day.sh`

`crontab -e`
```
*/5 * * * * sh /usr/local/redis-3.2.9/backups/redis-rdb-day.sh
```
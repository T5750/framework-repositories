# nginx Statistics

## 访问量
- **PV（Page View）**：即页面浏览量或者点击量，用户每一次对网站中每个页面访问均记录1个PV。用户对同一页面的多次访问，访问量累积。
- **UV（Unique Visitor）**：指通过互联网浏览这个网页的人，电脑称为一个访客、手机也称为一个访客，一天之内相同的客户端只能被计算一次。
- **IP（Internet Protocol）**：指独立IP访问站点的IP总数，一天内相同IP只能算一次。
- **VV（Visit View）**：指所有访客一天内访问网站的次数，当访客完成所有浏览并最终关闭网站的所有页面时变完成了一次访问，同一访客一天内可能有多次访问行为，访问次数累积。

### 查看各个访问量
1. 根据访问IP统计UV
    ```
    awk '{print $1}' /usr/local/nginx/logs/access.log|sort | uniq -c |wc -l
    ```
2. 统计访问URL统计PV
    ```
    awk '{print $7}' /usr/local/nginx/logs/access.log|wc -l
    ```
3. 查询访问最频繁的URL
    ```
    awk '{print $7}' /usr/local/nginx/logs/access.log|sort | uniq -c |sort -n -k 1 -r|more
    ```
4. 查询访问最频繁的IP
    ```
    awk '{print $1}' /usr/local/nginx/logs/access.log|sort | uniq -c |sort -n -k 1 -r|more
    ```
5. 根据时间段统计查看日志
    ```
    cat /usr/local/nginx/logs/access.log| sed -n '/10\/Dec\/2019:14/,/11\/Dec\/2019:15/p'|more
    ```

针对每天的访问信息写一个脚本，并将统计信息输出到`pv.html`文件里面，之保留30天的信息。方便直接浏览此页面查看，但要限制特定IP才能访问此页面，其他IP的403!
```
year=`date +%Y`  
month=`date +%m`
datedate=`date +%F`
date=`date +%Y%m%d`
pv=`awk '{print $7}' /xx/logs/nginx/xxx/"$year"/"$month"/"$datedate"-access.log | wc -l`
ip=`awk '{print $1}' /xx/logs/nginx/xxx/"$year"/"$month"/"$datedate"-access.log | sort -n | uniq -c | wc -l`
echo -e "\n$date Pv is $pv;; $date Ip is $ip.." >> /xx/xxx/pv.htm l sort -rn /xx/xxx/pv.html | sed -i '31d' /xx/xxx/pv.html | sort -r
```

此外还要修改nginx配置文件：
```
location = /pv.html {
    allow xxx.xxx.xxx.xxx;
    deny all;
}
```

## 屏蔽IP
```
vi nginxDeny.conf
#deny 192.168.1.100;
```
```
vi nginx.conf
include nginxDeny.conf;
```
- 单独网站屏蔽IP的方法，把`include nginxDeny.conf;`放到网址对应的在`server{}`语句块
- 所有网站屏蔽IP的方法，把`include nginxDeny.conf;`放到`http{}`语句块
```
//屏蔽单个ip访问
deny IP;
//允许单个ip访问
allow IP;
//屏蔽所有ip访问
deny all;
//允许所有ip访问
allow all;
//屏蔽整个段即从123.0.0.1到123.255.255.254访问的命令
deny 123.0.0.0/8
//屏蔽IP段即从123.45.0.1到123.45.255.254访问的命令
deny 124.45.0.0/16
//屏蔽IP段即从123.45.6.1到123.45.6.254访问的命令
deny 123.45.6.0/24
//如果你想实现这样的应用，除了几个IP外，其他全部拒绝，那需要你在nginxDeny.conf中这样写
allow 1.1.1.1;
allow 1.1.1.2;
deny all;
```

## Results
- `nginxPv.conf`
- `nginxDeny.conf`
- `pv.html`
- `nginx-pv.sh`

## References
- [统计Nginx访问量](https://www.jianshu.com/p/537a0bddda94)
- [nginx统计访问量最高的ip并封禁IP](https://blog.51cto.com/meiling/2174251)
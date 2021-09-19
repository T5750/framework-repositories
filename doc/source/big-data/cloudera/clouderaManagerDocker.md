# Cloudera Manager Docker

## Docker
```
docker run -d -e TZ=Asia/Shanghai -v /etc/localtime:/etc/localtime:ro --name cdh --hostname=quickstart.cloudera --privileged=true -p 8020:8020 -p 8022:8022 -p 7180:7180 -p 21050:21050 -p 50070:50070 -p 50075:50075 -p 50010:50010 -p 50020:50020 -p 8890:8890 -p 60010:60010 -p 10002:10002 -p 25010:25010 -p 25020:25020 -p 18088:18088 -p 8088:8088 -p 19888:19888 -p 7187:7187 -p 11000:11000 -p 8888:8888 -p 2181:2181 -p 10000:10000 cloudera/quickstart /bin/bash -c '/usr/bin/docker-quickstart && /home/cloudera/cloudera-manager --express --force && service ntpd start && tail -F /var/log/*.log'
```

- Cloudera Manager: [http://quickstart.cloudera:7180/](http://quickstart.cloudera:7180/)
- Cloudera Manager User: cloudera / cloudera
- Startup: HFDS, Hive, Hue, Yarn, zookeeper
- Hue: [http://quickstart.cloudera:8888/](http://quickstart.cloudera:8888/)
- Hue User: cloudera / cloudera
- HDFS UI: [http://quickstart.cloudera:8888/filebrowser/](http://quickstart.cloudera:8888/filebrowser/)
- Hive UI: [http://quickstart.cloudera:8888/beeswax/](http://quickstart.cloudera:8888/beeswax/)

## HDFS
`/user/cloudera/users/data.csv`
```
user_id,name,sex,age
10001,张三,1,20
10002,李四,0,18
10003,王五,1,27
10004,赵六,1,33
```
`/user/cloudera/fans/data.csv`
```
user_id,fans_id,time
10001,10002,2019-10-01
10001,10003,2019-11-03
10002,10003,2019-10-22
10002,10004,2019-11-02
10003,10001,2019-09-13
10004,10001,2019-09-08
10004,10002,2019-10-08
10004,10003,2019-11-15
```

## Hive
```
create external table users
(user_id int, name string, sex int, age int)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
STORED AS TEXTFILE
location '/user/cloudera/users'
tblproperties("skip.header.line.count"="1");

create external table fans
(user_id int, fans_id int, time string)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY ','
STORED AS TEXTFILE
location '/user/cloudera/fans'
tblproperties("skip.header.line.count"="1");
```
```
SELECT name, count(*) AS fans_count
FROM users
LEFT JOIN fans
    ON fans.user_id = users.user_id
GROUP BY name;
```

## Snapshots
![cloudera-manager-demos](https://www.cloudera.com/content/dam/www/marketing/images/screenshots/cloudera-manager/cloudera-manager-demos.jpg)

## References
- [Cloudera QuickStart Docker Image](https://hub.docker.com/r/cloudera/quickstart)
- [20 分钟自动搭建大数据平台](https://www.jianshu.com/p/5ecf73668b4d)
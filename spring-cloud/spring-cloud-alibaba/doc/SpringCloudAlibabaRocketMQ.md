# Spring Cloud Alibaba RocketMQ

## RocketMQ
[Download RocketMQ](https://rocketmq.apache.org/release_notes/release-notes-4.4.0/)
```
unzip rocketmq-all-4.4.0-bin-release.zip -d /usr/local/
vi bin/runbroker.sh
JAVA_OPT="${JAVA_OPT} -server -Xms2g -Xmx2g -Xmn1g"
```

### Start RocketMQ
``` 
nohup sh mqnamesrv &
nohup sh mqbroker -n localhost:9876 &
sh mqadmin updateTopic -n localhost:9876 -c DefaultCluster -t test-topic
```

### Shutdown Servers
```
sh mqshutdown broker
sh mqshutdown namesrv
```

## CLI Admin Tool
```
sh mqadmin
mqadmin topicList -n 127.0.0.1:9876
```

## References
- [RocketMQ Example](https://github.com/alibaba/spring-cloud-alibaba/blob/master/spring-cloud-alibaba-examples/rocketmq-example/readme-zh.md)
- [Quick Start](https://rocketmq.apache.org/docs/quick-start/)
- [CLI Admin Tool](http://rocketmq.apache.org/docs/cli-admin-tool/)
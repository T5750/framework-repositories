# Spring Cloud Alibaba RocketMQ

## RocketMQ
[Download RocketMQ](https://rocketmq.apache.org/release_notes/release-notes-4.4.0/)
```
unzip rocketmq-all-4.4.0-bin-release.zip -d /usr/local/
mkdir logs

vi bin/runbroker.sh
JAVA_OPT="${JAVA_OPT} -server -Xms2g -Xmx2g -Xmn1g"

vi ~/.bashrc
export ROCKETMQ_HOME=/usr/local/rocketmq-all-4.4.0-bin-release
export PATH=${JAVA_HOME}/bin:${ROCKETMQ_HOME}/bin:$PATH
```

### Start RocketMQ
```
nohup sh mqnamesrv &
nohup sh mqbroker -n localhost:9876 &

nohup mqnamesrv > $ROCKETMQ_HOME/logs/mqnamesrv.log  2> $ROCKETMQ_HOME/logs/mqnamesrv.log &
nohup mqbroker -n localhost:9876 > $ROCKETMQ_HOME/logs/mqbroker.log  2> $ROCKETMQ_HOME/logs/mqbroker.log &
```

### Shutdown Servers
```
sh mqshutdown broker
sh mqshutdown namesrv
```

## CLI Admin Tool
```
sh mqadmin
sh mqadmin updateTopic -n localhost:9876 -c DefaultCluster -t test-topic
mqadmin topicRoute -n 127.0.0.1:9876 -t test-topic
mqadmin topicStatus -n 127.0.0.1:9876 -t test-topic
mqadmin topicClusterList -n 127.0.0.1:9876 -t test-topic
mqadmin brokerStatus -b 127.0.0.1:10911
mqadmin printMsg -n 127.0.0.1:9876 -t test-topic
mqadmin clusterList -n 127.0.0.1:9876
mqadmin topicList -n 127.0.0.1:9876
```

## Spring Cloud Stream
![SpringCloudStream-min-min](https://s0.wailian.download/2019/07/12/SpringCloudStream-min-min.png)

## References
- [RocketMQ Example](https://github.com/alibaba/spring-cloud-alibaba/blob/master/spring-cloud-alibaba-examples/rocketmq-example/readme-zh.md)
- [Quick Start](https://rocketmq.apache.org/docs/quick-start/)
- [CLI Admin Tool](http://rocketmq.apache.org/docs/cli-admin-tool/)
- [Spring Cloud Stream 体系及原理介绍](https://mp.weixin.qq.com/s?__biz=MzU4NzU0MDIzOQ==&mid=2247486235&idx=4&sn=03a0468424a2987becf9bc20feb01f55&chksm=fdeb397bca9cb06ddca880f305f1019ba7a2734c340987cfc1ef04a58eb4a5226dd25ce66edd&scene=21#wechat_redirect)
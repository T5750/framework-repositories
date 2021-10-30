# ActiveMQ Docker

## Quick Start
For test purpose :
```
docker run --name='activemq' -it --rm -P \
webcenter/activemq:5.14.3
```

For production purpose :
```
docker run --name='activemq' -d \
-e 'ACTIVEMQ_CONFIG_NAME=amqp-srv1' \
-e 'ACTIVEMQ_CONFIG_DEFAULTACCOUNT=false' \
-e 'ACTIVEMQ_ADMIN_LOGIN=admin' -e 'ACTIVEMQ_ADMIN_PASSWORD=your_password' \
-e 'ACTIVEMQ_USERS_myproducer=producerpassword' -e 'ACTIVEMQ_GROUPS_writes=myproducer' \
-e 'ACTIVEMQ_USERS_myconsumer=consumerpassword' -e 'ACTIVEMQ_GROUPS_reads=myconsumer' \
-e 'ACTIVEMQ_JMX_user1_role=readwrite' -e 'ACTIVEMQ_JMX_user1_password=jmx_password' \
-e 'ACTIVEMQ_JMX_user2_role=read' -e 'ACTIVEMQ_JMX_user2_password=jmx2_password'
-e 'ACTIVEMQ_CONFIG_TOPICS_topic1=mytopic1' -e 'ACTIVEMQ_CONFIG_TOPICS_topic2=mytopic2'  \
-e 'ACTIVEMQ_CONFIG_QUEUES_queue1=myqueue1' -e 'ACTIVEMQ_CONFIG_QUEUES_queue2=myqueue2'  \
-e 'ACTIVEMQ_CONFIG_MINMEMORY=1024' -e  'ACTIVEMQ_CONFIG_MAXMEMORY=4096' \
-e 'ACTIVEMQ_CONFIG_SCHEDULERENABLED=true' \
-v /data/activemq:/data \
-v /var/log/activemq:/var/log/activemq \
-p 8161:8161 \
-p 61616:61616 \
-p 61613:61613 \
webcenter/activemq:5.14.3
```

## Docker Compose
`activemq.yml`
- [http://localhost:8161/admin/](http://localhost:8161/admin/)
- User: admin / admin

## Screenshots
![](https://svn.apache.org/repos/infra/websites/production/activemq/content/web-console.data/web_console.png)

## References
- [webcenter/activemq](https://hub.docker.com/r/webcenter/activemq)
# ActiveMQ网摘笔记

## ActiveMQ的独占消费（Exclusive Consumer）
我们经常希望维持队列中的消息，按一定次序转发给消息者。然而当有多个JMS Session和消息消费者实例的从同一个队列中获取消息的时候，就不能保证消息顺序处理。因为消息被多个不同线程并发处理着。

在ActiveMQ4.x中可以采用Exclusive Consumer或者Exclusive Queues，避免这种情况，Broker会从消息队列中，一次发送消息给一个消息消费者来保证顺序。

配置如下：
```
queue = new ActiveMQQueue("TEST.QUEUE?consumer.exclusive=true");

consumer = session.createConsumer(queue);
```

- 在接收信息时，有一个或者多个备份接收消息者和一个独占消息者的同时接收时候，无论两者创建先后，在接收时，均为独占消息者接收。
- 在接收信息时，有多个独占消费者时，只有一个独占消费者可以接收到消息。
- 当有多个备份消息者和多个独占消费者时，当所有的独占消费者均close时，只有一个备份消费者接收到消息。

备注：备份消费者为不带任何参数的消费者。

## References
- [ActiveMQ 的独占消费（Exclusive Consumer）](https://blog.csdn.net/czp11210/article/details/8837901)
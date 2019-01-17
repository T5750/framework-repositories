# ActiveMQ网摘笔记

## ZooKeeper+ActiveMQ+LevelDB集群

### 高可用原理
![activemq-mater-slave-min](https://www.wailian.work/images/2018/11/13/activemq-mater-slave-min.png)

使用ZooKeeper（集群）注册所有的ActiveMQ Broker。只有其中的一个Broker可以提供服务，被视为Master，其他的Broker（节点）处于待机状态，被视为Slave。如果Master因故障而不能提供服务，ZooKeeper会从Slave中选举出一个Broker充当Master
Slave连接Master并同步他们的存储状态，Slave不接受客户端连接。所有的存储操作都将被复制到连接至Master的Slaves。如果Master宕了，得到了最新的Slave会成为Master。故障节点在回复后会重新加入到集群中并连接Master进入Slave模式。

所有需同步的disk的消息操作都将等待存储状态被复制到其他法定节点的操作完成才能完成。所以，如果你配置了replicas=3，那么法定大小是（3/2）+1=2。Master将会存储并更新然后等待（2-1）=1个Slave存储和更新完成，才汇报success。至于为什么是2-1，熟悉ZooKeeper的应该知道，有一个node要作为观察者存在。当一个新的Master被选中，你需要至少保障一个法定node在线以能够找到拥有最新状态的node。这个node可以成为新的Master。因此，推荐运行至少3个replica nodes，以防止一个node失败了，服务中断。（原理与ZooKeeper集群的高可用实现方式类似）

### 集群部署
主机 | 集群端口 | 消息端口 | 管控台端口 | 节点安装目录
---- | --- | --- | --- | -----
192.168.100.163 | 62621 | 51511 | 8161 | /usr/local/apache-activemq-5.11.1
192.168.100.164 | 62622 | 51512 | 8162 | /usr/local/apache-activemq-5.11.1
192.168.100.165 | 62623 | 51513 | 8163 | /usr/local/apache-activemq-5.11.1

1. 防火墙打开对应端口
1. 在3台虚拟机中部署好单节点的mq
1. 修改管理控制台端口（默认为8161）可在`conf/jetty.xml`中修改，如下：
    ```
    #activemq-8161
    <bean id="jettyPort" class="org.apache.activemq.web.WebConsolePort" init-method="start">
             <!-- the default port number for the web console -->
        <property name="host" value="0.0.0.0"/>
        <property name="port" value="8161"/>
    </bean>
    
    #activemq-8162
    <bean id="jettyPort" class="org.apache.activemq.web.WebConsolePort" init-method="start">
             <!-- the default port number for the web console -->
        <property name="host" value="0.0.0.0"/>
        <property name="port" value="8162"/>
    </bean>
    
    #activemq-8163
    <bean id="jettyPort" class="org.apache.activemq.web.WebConsolePort" init-method="start">
             <!-- the default port number for the web console -->
        <property name="host" value="0.0.0.0"/>
        <property name="port" value="8163"/>
    </bean>
    ```
1. 集群配置。在3个ActiveMQ节点配置`conf/activemq.xml`持久化适配器。修改其中`bind`、`zkAddress`、`hostname`和`zkPath`。注意：每个activemq的`BrokerName`必须相同，否则不能加入集群。
    - 最为重要的是修改`persistenceAdapter`部分，将其默认的注释，加入如下：
    ```
    #activemq-8161
    <persistenceAdapter>
        <!--kahaDB directory="${activemq.data}/kahadb"/ -->
        <replicatedLevelDB
        directory="${activemq.data}/leveldb"
        replicas="3"
        bind="tcp://0.0.0.0:62621"
        zkAddress="192.168.100.163:2181,192.168.100.164:2181,192.168.100.165:2181"
        hostname="192.168.100.163" zkPath="/activemq/leveldb-stores"/>
    </persistenceAdapter>
    
    #activemq-8162
    <persistenceAdapter>
        <!--kahaDB directory="${activemq.data}/kahadb"/ -->
        <replicatedLevelDB
        directory="${activemq.data}/leveldb"
        replicas="3"
        bind="tcp://0.0.0.0:62622"
        zkAddress="192.168.100.163:2181,192.168.100.164:2181,192.168.100.165:2181"
        hostname="192.168.100.164" zkPath="/activemq/leveldb-stores"/>
    </persistenceAdapter>
    
    #activemq-8163
    <persistenceAdapter>
        <!--kahaDB directory="${activemq.data}/kahadb"/ -->
        <replicatedLevelDB
        directory="${activemq.data}/leveldb"
        replicas="3"
        bind="tcp://0.0.0.0:62623"
        zkAddress="192.168.100.163:2181,192.168.100.164:2181,192.168.100.165:2181"
        hostname="192.168.100.165" zkPath="/activemq/leveldb-stores"/>
    </persistenceAdapter>
    ```
    - 修改各节点的消息端口（注意：避免端口冲突）
    ```
    #activemq-8161:改为51511
    <transportConnectors>
        <!-- DOS protection, limit concurrent connections to 1000 and frame size to 100MB -->
        <transportConnector name="openwire" uri="tcp://0.0.0.0:51511?maximumConnections=1000&wireFormat.maxFrameSize=104857600"/>
        <transportConnector name="amqp" uri="amqp://0.0.0.0:5672?maximumConnections=1000&wireFormat.maxFrameSize=104857600"/>
        <transportConnector name="stomp" uri="stomp://0.0.0.0:61613?maximumConnections=1000&wireFormat.maxFrameSize=104857600"/>
        <transportConnector name="mqtt" uri="mqtt://0.0.0.0:1883?maximumConnections=1000&wireFormat.maxFrameSize=104857600"/>
        <transportConnector name="ws" uri="ws://0.0.0.0:61614?maximumConnections=1000&wireFormat.maxFrameSize=104857600"/>
    </transportConnectors>
    
    #activemq-8162:改为51512
    <transportConnectors>
        <!-- DOS protection, limit concurrent connections to 1000 and frame size to 100MB -->
        <transportConnector name="openwire" uri="tcp://0.0.0.0:51512?maximumConnections=1000&wireFormat.maxFrameSize=104857600"/>
        <transportConnector name="amqp" uri="amqp://0.0.0.0:5672?maximumConnections=1000&wireFormat.maxFrameSize=104857600"/>
        <transportConnector name="stomp" uri="stomp://0.0.0.0:61613?maximumConnections=1000&wireFormat.maxFrameSize=104857600"/>
        <transportConnector name="mqtt" uri="mqtt://0.0.0.0:1883?maximumConnections=1000&wireFormat.maxFrameSize=104857600"/>
        <transportConnector name="ws" uri="ws://0.0.0.0:61614?maximumConnections=1000&wireFormat.maxFrameSize=104857600"/>
    </transportConnectors>
    
     #activemq-8163:改为51513
    <transportConnectors>
        <!-- DOS protection, limit concurrent connections to 1000 and frame size to 100MB -->
        <transportConnector name="openwire" uri="tcp://0.0.0.0:51513?maximumConnections=1000&wireFormat.maxFrameSize=104857600"/>
        <transportConnector name="amqp" uri="amqp://0.0.0.0:5672?maximumConnections=1000&wireFormat.maxFrameSize=104857600"/>
        <transportConnector name="stomp" uri="stomp://0.0.0.0:61613?maximumConnections=1000&wireFormat.maxFrameSize=104857600"/>
        <transportConnector name="mqtt" uri="mqtt://0.0.0.0:1883?maximumConnections=1000&wireFormat.maxFrameSize=104857600"/>
        <transportConnector name="ws" uri="ws://0.0.0.0:61614?maximumConnections=1000&wireFormat.maxFrameSize=104857600"/>
    </transportConnectors>
    ```
1. 启动服务并监听日志（需先启动ZooKeeper集群服务）
    ```
    bin/activemq start
    bin/activemq start
    bin/activemq start
    
    tail -f data/activemq.log
    tail -f data/activemq.log
    tail -f data/activemq.log
    ```

### Results
- `application.yml`配置：```broker-url: failover:(tcp://192.168.100.163:51511,tcp://192.168.100.164:51512,tcp://192.168.100.165:51513)```
- 示例：`ActiveMQProviderApplication`，`ActiveMQConsumerApplication`
- queue测试：[http://localhost:8080/activemq-provider/publish/queue](http://localhost:8080/activemq-provider/publish/queue)
- topic测试：[http://localhost:8080/activemq-provider/publish/topic](http://localhost:8080/activemq-provider/publish/topic)

## References
- [ActiveMQ（二）ActiveMQ集群部署与应用](https://www.jianshu.com/p/dcf0623b3036)
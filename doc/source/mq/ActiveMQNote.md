# ActiveMQ笔记

## 1.1 背景 & JMS概述
CORBA、DCOM、RMI等RPC中间件技术已广泛应用于各个领域。但是，面对规模和复杂度都越来越高的分布式系统，这些技术也显示出其局限性：
1. 同步通信：客户发出调用后，必须等待服务对象完成处理，并返回结果后才能继续执行；
1. 客户和服务对象的生命周期紧密耦合：客户进程和服务对象进程都必须正常运行；如果由于服务对象崩溃或者网络故障导致客户的请求不可达，客户会接收到异常；
1. 点对点通信：客户的一次调用只发送给某个单独的目标对象。

面向消息的中间件（Message Oriented Middleware, MOM）较好的解决了以上问题。发送者将消息发送给消息服务器，消息服务器将消息存放在若千队列中，在合适的时候再将消息转发给接收者。这种模式下，发送和接收是异步的，发送者无需等待二者的生命周期未必相同：发送消息的时候接收者不一定运行，接收消息的时候发送者也不一定运行；一对多通信：对于一个消息可以有多个接收者。

JAVA消息服务（JMS）定义了Java中访问消息中间件的接口。JMS只是接口，并没有给予实现，实现JMS接口的消息中间件称为JMS Provider，已有的MOM系统包括Apache的ActiveMQ、以及阿里巴巴的RocketMQ、IBM的MQSeries、Microsoft的MSMQ和BEA的MessageQ、RabbitMQ、Kafka等等，它们基本都遵循JMS规范

## 1.2 JMS术语
JMS实现JMS接口的消息中间件
- Provider(MessageProvider)：生产者
- Consumer(MessageConsumer)：消费者
- PTP：Point to Point，即点对点的消息模型
- Pub/Sub： Publish/Subscribe，即发布/订阅的消息模型
- Queue：队列目标
- Topic：主题目标
- ConnectionFactory：连接工厂，JMS用它创建连接
- Connection：JMS客户端到JMS Provider的连接
- Destination：消息的目的地
- Session：会话，一个发送或接收消息的线程

## 1.3 JMS术语概念
- ConnectionFactory接口（连接工厂）：用户用来创建到JMS提供者的连接的被管对象。JMS客户通过可移植的接口访问连接，这样当下层的实现改变时，代码不需要进行修改。管理员在JNDI名字空间中配置连接工厂，这样，JMS客户才能够查找到它们。根据消息类型的不同，用户将使用队列连接工厂，或者主题连接工厂。
- Connection接口（连接）：连接代表了应用程序和消息服务器之间的通信链路。在获得了连接工厂后，就可以创建一一个与JMS提供者的连接。根据不同的连接类型，连接允许用户创建会话，以发送和接收队列和主题到目标。
- Destination接口（目标）：目标是一个包装了消息目标标识符的被管对象，消息目标是指消息发布和接收的地点，或者是队列，或者是主题。JMS管理员创建这些对象，然后用户通过JNDI发现它们。和连接工厂一样，管理员可以创建两种类型的目标，点对点模型的队列，以及发布者/订阅者模型的主题。
- MessageConsumer接口（消息消费者）：由会话创建的对象，用于接收发送到目标的消息。消费者可以同步地（阻塞模式），或异步（非阻塞）接收队列和主题类型的消息。
- MessageProducer接口（消息生产者）：由会话创建的对象，用于发送消息到目标。用户可以创建某个目标的发送者，也可以创建一个通用的发送者，在发送消息时指定目标。
- Message接口（消息）：是在消费者和生产者之间传送的对象，也就是说从一个应用程序创送到另一个应用程序。一个消息有三个主要部分：
    - 消息头（必须）：包含用于识别和为消息寻找路由的操作设置。
    - 一组消息属性（可选）：包含额外的属性，支持其他提供者和用户的兼容。可以创建定制的字段和过滤器（消息选择器）。
    - 一个消息体（可选）：允许用户创建五种类型的消息（文本消息，映射消息，字节消息，流消息和对象消息）。
- 消息接口非常灵活，并提供了许多方式来定制消息的内容。
- Session接口（会话）：表示一个单线程的上下文，用于发送和接收消息。由于会话是单线程的，所以消息是连续的，就是说消息是按照发送的顺序一个一个接收的。会话的好处是它支持事务。如果用户选择了事务支持，会话上下文将保存一组消息，直到事务被提交才发送这些消息。在提交事务之前，用户可以使用回滚操作取消这些消息。一个会话允许用户创建消息生产者来发送消息，创建消息消费者来接收消息。

## 1.4 消息格式定义
JMS定义了五种不同的消息正文格式，以及调用的消息类型，允许你发送并接收以一些不同形式的数据，提供现有消息格式的一些级别的兼容性。
- StreamMessage：Java原始值的数据流
- MapMessage：一个键值对
- TextMessage：一个字符串对象
- ObjectMessage：一个序列化的Java对象
- BytesMessage：一个未解释字节的数据流

## 2.1 ActiveMQ简介
ActiveMQ是Apache出品，最流行的，能力强劲的开源消息总线。

ActiveMQ是一个完全支持JMS1.1和J2EE 1.4规范的JMS Provider实现，尽管JMS规范出台已经是很久的事情了，但是JMS在当今的J2EE应用中间仍然扮演着特殊的地位，可以说ActiveMQ在业界应用最广泛，当然如果想要有更强大的性能和海量数据处理能力，ActiveMQ还需要不断地升级版本，80%以上的业务我们使用ActiveMQ以及足够满足需求，当然后续如天猫、淘宝网这种大型的电商网站，尤其是双11这种特殊时间，ActiveMQ需要进行很复杂的优化源码以及架构设计才能完成，我们之后会学习一个更强大的分布式消息中间件，RocketMQ，可以说ActiveMQ是核心，是基础，所以我们必须要掌握好。

时间（是否开源）、地点（公司的业务、环境）、人物（Spring）
1. RocketMQ（使用RocketMQ）商用
2. pk：（Kafka RabbitMQ）
    - 第一点：Kafka ===> 性能吞吐量非常高的 ---- RabbitMQ（*）性能上还不错，数据高可靠，支持天然的集群
    - 空中接力：pageCache 生产 p ----> memeory <---- c 消费
    - 设计原则：Kafka并不是使用持久化方式（数据落地方式、刷盘方式）来保证数据的可靠性的，而是使用replicate的方式来保证高可用的，可能会产生少量数据的丢失
3. ActiveMQ（io：随机读写，顺序读写），满足80%以上的场景，kahadb、leveldb、MySQL

## 2.2 ActiveMQ使用
官方网站下载：[http://activemq.apache.org/](http://activemq.apache.org/)，`apache-activemq-5.11.1-bin.zip`。下载好进行解压缩，目录如下：
```
bin
conf
data
docs
examples
lib
webapps
webapps-demo
activemq-all-5.11.1.jar
LICENSE
NOTICE
README.txt
```

## 2.3 ActiveMQ Hello World
一个简单的Hello World示例，实现接收者和发送者。`Sender/Receiver`：
1. 建立`ConnectionFactory`工厂对象，需要填入用户名、密码、以及要连接的地址，均使用默认即可，默认端口为`tcp:/localhost:61616`
1. 通过`ConnectionFactory`工厂对象我们创建一个`Connection`连接，并且调用`Connection`的`start`方法开启连接，`Connection`默认是关闭的。
1. 通过`Connection`对象创建`Session`会话（上下文环境对象），用于接收消息，参数配置1为是否启用是事务，参数配置2为签收模式，一般我们设置自动签收。
1. 通过Session创建`Destination`对象，指的是一个客户端用来指定生产消息目标和消费消息来源的对象，在PTP模式中，`Destination`被称作Queue即队列；在Pub/Sub模式，`Destination`被称作Topic即主题。在程序中可以使用多个Queue和Topic。
1. 我们需要通过`Session`对象创建消息的发送和接收对象（生产者和消费者）`MessageProducer/MessageConsumer`。
1. 我们可以使用`MessageProducer`的`setDeliveryMode`方法为其设置持久化特性和非持久化特性（DeliveryMode）。
1. 最后我们使用JMS规范的`TextMessage`形式创建数据（通过`Session`对象），并用`MessageProducer`的`send`方法发送数据。同理客户端使用`receive`方法进行接收数据。最后不要忘记关闭`Connection`连接。

## 2.4 ActiveMQ安全机制
- ActiveMQ的web管理界面：[http://127.0.0.1:8161/admin](http://127.0.0.1:8161/admin)
    - ActiveMQ管控台使用jetty部署，所以需要修改密码则需要到相应的配置文件
    - `C:\devsoftware\apache-activemq-5.11.1\conf\jetty-realm.properties`
- ActiveMQ应该设置有安全机制，只有符合认证的用户才能进行发送和获取消息，所以我们也可以在`activemq.xml`里去添加安全验证配置
    - `C:\devsoftware\apache-activemq-5.11.1\conf\activemq.xml`，添加配置（添加一个插件配置即可）
    ```
    <tempUsage>
        <tempUsage limit="1 gb"/>
    </tempUsage>
    
    <persistenceAdapter>
        <jdbcPersistenceAdapter dataSource="#mysql-ds"/>
    </persistenceAdapter>
    
    <bean id="mysql-ds" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close">
        <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql://127.0.0.1:3306/test?relaxAutoCommit=true"/>
        <property name="username" value="root"/>
        <property name="password" value="root"/>
        <property name="maxActive" value="200"/>
        <property name="poolPreparedStatements" value="true"/>
    </bean>
    ```
- 5.13.3版本：
- ActiveMQ持久化存储：可以切换不同的存储技术（默认是kahadb、leveldb、MySQL、Oracle）
- 引入三个jar包：`mysql-connector-java-5.1.21.jar`，`commons-dbcp-1.4.jar`，`commons-pool-1.6.jar`

## 3.1 Connection方法使用
在成功创建正确的`ConnectionFactory`后，下一步是创建一个连接，它是JMS定义的一个接口。`ConnectionFactory`负责返回可以与底居消息传递系统进行通信的`Connection`实现。通常客户端只使用单一连接。根据JMS文档，`Connection`的目的是“利用JMS提供者封装幵放的连接”，以及表示“客户端与提供者服务例程之间的幵放TCP/IP套接字”。该文档还指出`Connection`应该是进行客户端身价验证的地方等等。
- 当一个`Connection`被创建时，它的传输默认是关闭的，必须使用`start`方法幵启。一个`Connection`可以建立一个或多个的`Session`。
- 当一个程序执行完成后，必须关闭之前创建的`Connection`，否则ActiveMQ不能释放资源，关闭一个`Connection`同样也关闭了`Session`，`MessageProducer`和`MessageConsumer`。
- `Connection createConnection();`
- `Connection createConnection(String userName, String password);`

## 3.2 Session方法使用
一旦从`ConnectionFactory`中获得一个`Connection`，必须从`Connection`中创建一个或者多个`Session`。`Session`是一个发送或接收消息的线程，可以使用`Session`创建`MessageProducer`，`MessageConsumer`和`Message`。

`Session`可以被事务化，也可以不被事务化，通常，可以通过向`Connection`上的适当创建方法传递一个希尔参数对此进行设置。

`Session createSession(boolean transacted, int acknowledgeMode);`

其中`transacted`为使用事务标识，`acknowledgeMode`为签收模式。

结束事务有两种方法：提交或者回滚。当一个事务提交，消息被处理。如果事务中有一个步骤失败，事务就回滚，这个事务中的已经执行的动作将被撤销。在发送消息最后也必须要使用`session.commit()`方法表示提交事务。

签收模式有三种形式：
- `Session.AUTO_ACKNOWLEDGE`当客户端从`receive`或`onMessage`成功返回时，`Session`自动签收客户端的这条消息的收条。
- `Session.CLIENT_ACKNOWLEDGE`客户端通过调用消息（`Message`），的`acknowledge`方法签收消息。在这种情况下，签收发生在`Session`层面：签收一个已消费的消息会自动地签收这个`Session`所有已消费消息的收条。
- `Session.DUPS_OK_ACKNOWLEDGE`此选项指示`Session`不必确保对传送消息的签收。它可能引起消息的重复，但是降低了`Session`的开销，所以只有客户瑞能容忍重复的消息，才可使用

## 3.3 MessageProducer
`MessageProducer`：`MessageProducer`是一个由`Session`创建的对象，用来向`Destination`发送消息。
```
void send(Destination destination, Message message);
void send(Destination destination, Message message, int delveryMode, intpriority, long timeToLive);
void send(Message message);
void send(Message message, int deliveryMode, int priority, long timeToLive);
```
- 其中`deliveryMode`为传送模式，`priority`为消息优先级，`timeToLive`为消息过期时间。

ActiveMQ支持两种消息传送模式：PERSISTENT和NON_PERSISTENT两种。如果不指定传送模式，那么默认是持久性消息。如果容忍消息丢失，那么使用非持久性消息可以改善性能和减少存储的开销。

`activemq.xml`配置：
```
<policyEntry queue="queueName" prioritizedMessages="true" />
```
消息优先级从0-9十个级别，0-4是普通消息，5-9是加急消息。如果不指定优先级，则默认为4。JMS不要求严格按照这十个优先级发送消息，但必须保证**单次**加急消息要先于普通消息到达，但也仅仅是先到达，并不能保证顺序消费机制。

默认情况下，消息永不会过期。如果消息在特定周期内失去意义，那么可以设置过期时间，时间单位为亳秒。

## 3.4 MessageConsumer
`MessageConsumer`是一个由`Session`创建的对象，用来从`Destination`接收消息。
```
MessageConsumer createConsumer(Destination destination);
MessageConsumer createConsumer(Destination destination, String messageSelector);
MessageConsumer createConsumer(Destination destination, String messageSelector, boolean noLocal);
TopicSubscriber createDurableSubscriber(Topic topic, String name);
TopicSubscriber createDurableSubscriber(Topic topic, String name, String messageSelector, boolean noLocal);
```
- `messageSelector`为消息选择器；
- `noLocal`标志默认为`false`，当设置为`true`时限制消费者只能接收和自己相同的连接（`Connection`）所发布的消息，此标志只适用于主题，不适用于队列；
- `name`标识订阅主题所对应的订阅名称，持久订阅时需要设置此参数。
- `public final String SELECTOR = "JMS_TYPE = 'MY_TAG1'";`该选择器检查了传入消息的`JMS_TYPE`属性，并确定了这个属性的值是否等于`MY_TAG1`。如果相等，则消息被消费：如果不相等，那么消息会被忽略。

消息的同步和异步接收：
- 消息的同步接收是当客户端主动去接收消息，客户端可以采用`MessageConsumer`的`recelve`方法去接收下一个消息。
    - `Message receive()`
    - `Message receive(long timeout)`
    - `Message recelveNoWait()`
- 消息的异步接收是指当消息到达时，ActiveMQ主动通知客户端，可以通过注册一个实现`MessageListener`接口的对象到`MessageConsumer`。`MessageListener`只有一个必须实现的方法——`onMessage`，它只接收一个参数，即`Message`。在为每个发送到`Destination`的消息实现`onMessage`时，将调用该方法。

## 3.5 Message
JMS程序的最终目的是生产和消费的消息能被其他程序使用，JMS的`Message`是一个既简单又不乏灵活性的基本格式，允许创建不同平台上符合非JMS程序格式的消息。`Message`由以下几部分组成：**消息头，属性和消息体**。
```
BlobMessage createBlobMessage(File file)
BlobMessage createBlobMessage(nputStream in)
BlobMessage createBlobMessage(URL url)
BlobMessage createBlobMessage(URL url, boolean deletedByBroker)
BytesMessage createBytesMessage()
MapMessage createMapMessage()
Message createMessage()
ObjectMessage createObjectMessage()
ObjectMessage createObjectMessage(Serializable object)
TextMessage create TextMessage()
TextMessage create TextMessage(String text)
```
我们一般会在接收端通过`instanceof`方法去区别数据类型。

## 3.6 创建临时消息
ActiveMQ通过`createTemporaryQueue`和`createTemporaryTopic`创建临时目标，这些目标持续到创建它的`Connection`关闭。只有创建临时目标的`Connection`所创建的客户端才可以从临时目标中接收消息，但是任何的生产者都可以向临时目标中发送消息。如果关闭了创建此目标的`Connection`，那么临时目标被关闭，内容也将消失。
```
TemporaryQueue create TemporaryQueue();
TemporaryTopic create Temporary Topic();
```

## 4.1 高级主题（p2p）
p2p的过程理解起来更加简单。它好比是两个人打电话，这两个人是独享这一条通信链路的。一方发送消息，另外一方接收，就这么简单。在实际应用中因为有多个用户对使用p2p的链路，它的通信场景如下图所示：

![activemq-p2p-min](https://www.wailian.work/images/2018/11/12/activemq-p2p-min.jpg)

## 4.2 高级主题（publish-subscribe）
发布订阅模式有点类似于我们日常生活中订阅报纸。每年到年尾的时候，邮局就会发一本报纸集合让我们来选择订阅哪一个。在这个表里头列了所有出版发行的报纸，那么对于我们每一个订阅者来说，我们可以选择一份或者多份报纸。比如北京日报、潇湘晨报等。那么这些个我们订阅的报纸就相当于发布订阅模式里的topic。有很多个人订阅报纸，也有人可能和我订阅了相同的报纸。那么在这里相当于我们在同一个topic里注册了。对于一份报纸发行方来说，它和所有的订阅者就构成了一个1对多的关系。这种关系如下图所示：

![activemq-topic-min](https://www.wailian.work/images/2018/11/12/activemq-topic-min.jpg)

## 4.3 高级主题（与Spring进行整合）
使用Spring框架整合ActiveMQ，利用消息中间件，异步处理任务的机制，比如：异步消费数据、异步发送邮件、异步做查询操作等。

## 4.4 高级主题（ActiveMQ集群）
- ZooKeeper + LevelDB + ActiveMQ 搭建集群，提供高可用操作。
- 官方文档：[http://activemq.apache.org/replicated-leveldb-store.html](http://activemq.apache.org/replicated-leveldb-store.html)
- 步骤：详细见ActiveMQ集群使用文档

## Results
- 示例：
    - `ActiveMQProviderApplication`，`ActiveMQConsumerApplication`，`TestMQProducer`
    - 使用`messageSelector`：`Producer`，`ConsumerA`，`ConsumerB`
- activemq-provider中，`MailUtil`设置`username`
- activemq-consumer中，`application.yml`设置`username`，`password`
- 发送邮件：[http://localhost:8080/activemq-provider/mail/send](http://localhost:8080/activemq-provider/mail/send)

## References
- 尚学堂互联网架构师课程
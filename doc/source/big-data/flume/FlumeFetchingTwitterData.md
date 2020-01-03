# Flume Fetching Twitter Data

![FlumeFetchingTwitterData](https://s1.wailian.download/2020/01/03/FlumeFetchingTwitterData.png)

## Creating a Twitter Application
1. [https://apps.twitter.com/](https://apps.twitter.com/)
1. Create New App
1. Developer Agreement, Create your Twitter application button
1. Under keys and Access Tokens tab, Create my access token
1. Finally, click on the Test OAuth button. This will lead to a page which displays your Consumer key, Consumer secret, Access token, and Access token secret

## Starting HDFS
```
start-all.sh
$ hdfs dfs -mkdir hdfs://localhost:9000/user/Hadoop/twitter_data
```

## Configuring Flume

### Twitter 1% Firehose Source
This source is highly experimental. It connects to the 1% sample Twitter Firehose using streaming API and continuously downloads tweets, converts them to Avro format, and sends Avro events to a downstream Flume sink.

We will get this source by default along with the installation of Flume. The **jar** files corresponding to this source can be located in the **lib** folder as shown below.

![FlumeFetchingTwitterDataJarfiles](https://s1.wailian.download/2020/01/03/FlumeFetchingTwitterDataJarfiles.png)

### Setting the classpath
```
export CLASSPATH=$CLASSPATH:/FLUME_HOME/lib/*
```

- **Channels**
- **Source type** : `org.apache.flume.source.twitter.TwitterSource`
- **consumerKey** − The OAuth consumer key
- **consumerSecret** − OAuth consumer secret
- **accessToken** − OAuth access token
- **accessTokenSecret** − OAuth token secret
- **maxBatchSize** − Maximum number of twitter messages that should be in a twitter batch. The default value is 1000 (optional).
- **maxBatchDurationMillis** − Maximum number of milliseconds to wait before closing a batch. The default value is 1000 (optional).

### Channel
- **type** − It holds the type of the channel. In our example, the type is **MemChannel**.
- **Capacity** − It is the maximum number of events stored in the channel. Its default value is 100 (optional).
- **TransactionCapacity** − It is the maximum number of events the channel accepts or sends. Its default value is 100 (optional).

### HDFS Sink
- **Channel**
- **type** − hdfs
- **hdfs.path** − the path of the directory in HDFS where data is to be stored.

Given below are the optional properties of the HDFS sink that we are configuring in our application.
- **fileType** − This is the required file format of our HDFS file. **SequenceFile**, **DataStream** and **CompressedStream** are the three types available with this stream. In our example, we are using the **DataStream**.
- **writeFormat** − Could be either text or writable.
- **batchSize** − It is the number of events written to a file before it is flushed into the HDFS. Its default value is 100.
- **rollsize** − It is the file size to trigger a roll. It default value is 100.
- **rollCount** − It is the number of events written into the file before it is rolled. Its default value is 10.

## Example – Configuration File
`twitter.conf`
```
# Naming the components on the current agent.
TwitterAgent.sources = Twitter
TwitterAgent.channels = MemChannel
TwitterAgent.sinks = HDFS

# Describing/Configuring the source
TwitterAgent.sources.Twitter.type = org.apache.flume.source.twitter.TwitterSource
TwitterAgent.sources.Twitter.consumerKey = Your OAuth consumer key
TwitterAgent.sources.Twitter.consumerSecret = Your OAuth consumer secret
TwitterAgent.sources.Twitter.accessToken = Your OAuth consumer key access token
TwitterAgent.sources.Twitter.accessTokenSecret = Your OAuth consumer key access token secret
TwitterAgent.sources.Twitter.keywords = tutorials point,java, bigdata, mapreduce, mahout, hbase, nosql

# Describing/Configuring the sink

TwitterAgent.sinks.HDFS.type = hdfs
TwitterAgent.sinks.HDFS.hdfs.path = hdfs://localhost:9000/user/Hadoop/twitter_data/
TwitterAgent.sinks.HDFS.hdfs.fileType = DataStream
TwitterAgent.sinks.HDFS.hdfs.writeFormat = Text
TwitterAgent.sinks.HDFS.hdfs.batchSize = 1000
TwitterAgent.sinks.HDFS.hdfs.rollSize = 0
TwitterAgent.sinks.HDFS.hdfs.rollCount = 10000
 
# Describing/Configuring the channel
TwitterAgent.channels.MemChannel.type = memory
TwitterAgent.channels.MemChannel.capacity = 10000
TwitterAgent.channels.MemChannel.transactionCapacity = 100
  
# Binding the source and sink to the channel
TwitterAgent.sources.Twitter.channels = MemChannel
TwitterAgent.sinks.HDFS.channel = MemChannel
```

## Execution
```
$ cd $FLUME_HOME 
$ bin/flume-ng agent --conf ./conf/ -f conf/twitter.conf 
Dflume.root.logger=DEBUG,console -n TwitterAgent
```

## Verifying HDFS
- http://localhost:50070/

## Tips
//TODO

## References
- [Flume - Fetching Twitter Data](https://www.tutorialspoint.com/apache_flume/fetching_twitter_data.htm)
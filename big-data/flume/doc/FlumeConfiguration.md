# Flume Configuration

## Naming the Components
```
agent_name.sources = source_name
agent_name.sinks = sink_name
agent_name.channels = channel_name
```

Sources
- Avro Source
- Thrift Source
- Exec Source
- JMS Source
- Spooling Directory Source
- Twitter 1% firehose Source
- Kafka Source
- NetCat Source
- Sequence Generator Source
- Syslog Sources
- Syslog TCP Source
- Multiport Syslog TCP Source
- Syslog UDP Source
- HTTP Source
- Stress Source
- Legacy Sources
- Thrift Legacy Source
- Custom Source
- Scribe Source

Channels
- Memory Channel
- JDBC Channel
- Kafka Channel
- File Channel
- Spillable Memory Channel
- Pseudo Transaction Channel

Sinks
- HDFS Sink
- Hive Sink
- Logger Sink
- Avro Sink
- Thrift Sink
- IRC Sink
- File Roll Sink
- Null Sink
- HBaseSink
- AsyncHBaseSink
- MorphlineSolrSink
- ElasticSearchSink
- Kite Dataset Sink
- Kafka Sink

```
TwitterAgent.sources = Twitter
TwitterAgent.channels = MemChannel
TwitterAgent.sinks = HDFS
```

## Describing the Source
```
agent_name.sources.source_name.type = value
agent_name.sources.source_name.property2 = value
agent_name.sources.source_name.property3 = value
```
```
TwitterAgent.sources.Twitter.type = Twitter (type name)
TwitterAgent.sources.Twitter.consumerKey =
TwitterAgent.sources.Twitter.consumerSecret =
TwitterAgent.sources.Twitter.accessToken =
TwitterAgent.sources.Twitter.accessTokenSecret =
```

## Describing the Sink
```
agent_name.sinks.sink_name.type = value
agent_name.sinks.sink_name.property2 = value
agent_name.sinks.sink_name.property3 = value
```
```
TwitterAgent.sinks.HDFS.type = hdfs (type name)
TwitterAgent.sinks.HDFS.hdfs.path = HDFS directory’s Path to store the data
```

## Describing the Channel
```
agent_name.channels.channel_name.type = value
agent_name.channels.channel_name.property2 = value
agent_name.channels.channel_name.property3 = value
```
```
TwitterAgent.channels.MemChannel.type = memory (type name)
```

## Binding the Source and the Sink to the Channel
```
agent_name.sources.source_name.channels = channel_name
agent_name.sinks.sink_name.channels = channel_name
```
```
TwitterAgent.sources.Twitter.channels = MemChannel
TwitterAgent.sinks.HDFS.channels = MemChannel
```

## Starting a Flume Agent
```
$ bin/flume-ng agent --conf ./conf/ -f conf/twitter.conf
Dflume.root.logger=DEBUG,console -n TwitterAgent
```

- **agent** − Command to start the Flume agent
- **--conf ,-c<conf>** − Use configuration file in the conf directory
- **-f<file>** − Specifies a config file path, if missing
- **--name, -n <name>** − Name of the twitter agent
- **-D property =value** − Sets a Java system property value.

## References
- [Flume - Configuration](https://www.tutorialspoint.com/apache_flume/apache_flume_configuration.htm)
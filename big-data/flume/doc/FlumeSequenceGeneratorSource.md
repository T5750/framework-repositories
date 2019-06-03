# Flume Sequence Generator Source

## Prerequisites
- [Enviornment Setup](../../hadoop/doc/HadoopEnviornmentSetup.md)

## Configuring Flume
### Sequence Generator Source
- **Channels**
- **Source type** − seq

### Memory Channel
- **type** − It holds the type of the channel. In our example the type is MemChannel.
- **Capacity** − It is the maximum number of events stored in the channel. Its default value is 100. (optional)
- **TransactionCapacity** − It is the maximum number of events the channel accepts or sends. Its default is 100. (optional).

### HDFS Sink
- **Channel**
- **type** − hdfs
- **hdfs.path** − the path of the directory in HDFS where data is to be stored.

## Example – Configuration File
`seq_gen.conf`
```
# Naming the components on the current agent

SeqGenAgent.sources = SeqSource
SeqGenAgent.channels = MemChannel
SeqGenAgent.sinks = HDFS

# Describing/Configuring the source
SeqGenAgent.sources.SeqSource.type = seq

# Describing/Configuring the sink
SeqGenAgent.sinks.HDFS.type = hdfs
SeqGenAgent.sinks.HDFS.hdfs.path = hdfs://localhost:9000/user/Hadoop/seqgen_data/
SeqGenAgent.sinks.HDFS.hdfs.filePrefix = log
SeqGenAgent.sinks.HDFS.hdfs.rollInterval = 0
SeqGenAgent.sinks.HDFS.hdfs.rollCount = 10000
SeqGenAgent.sinks.HDFS.hdfs.fileType = DataStream

# Describing/Configuring the channel
SeqGenAgent.channels.MemChannel.type = memory
SeqGenAgent.channels.MemChannel.capacity = 1000
SeqGenAgent.channels.MemChannel.transactionCapacity = 100

# Binding the source and sink to the channel
SeqGenAgent.sources.SeqSource.channels = MemChannel
SeqGenAgent.sinks.HDFS.channel = MemChannel
```

## Execution
```
$ cd $FLUME_HOME
$./bin/flume-ng agent --conf $FLUME_HOME/conf --conf-file $FLUME_HOME/conf/seq_gen.conf --name SeqGenAgent
```

## Verifying the HDFS
- [http://192.168.100.210:50070](http://192.168.100.210:50070)
- Utilities -> Browse the file system -> /user/Hadoop/seqgen_data/
- [http://192.168.100.210:50070/explorer.html#/user/Hadoop/seqgen_data](http://192.168.100.210:50070/explorer.html#/user/Hadoop/seqgen_data)

## Verifying the Contents of the File
```
hdfs dfs -cat hdfs://localhost:9000/user/Hadoop/seqgen_data/log.1559538145091
```

## Results
```
tail -f -n 99 /usr/local/apache-flume-1.9.0/logs/flume.log
```

## References
- [Flume - Sequence Generator Source](https://www.tutorialspoint.com/apache_flume/sequence_generator_source.htm)
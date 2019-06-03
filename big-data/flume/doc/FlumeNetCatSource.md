# Flume NetCat Source

## Prerequisites
- [Enviornment Setup](../../hadoop/doc/HadoopEnviornmentSetup.md)

## Configuring Flume
### NetCat Source
- **channels**
- **Source type** − netcat
- **bind** − Host name or IP address to bind.
- **port** − Port number to which we want the source to listen.

### Channel
- **type** − It holds the type of the channel. In our example, the type is MemChannel.
- **Capacity** − It is the maximum number of events stored in the channel. Its default value is 100. (optional)
- **TransactionCapacity** − It is the maximum number of events the channel accepts or sends. Its default value is 100. (optional).

### Logger Sink
- **Channel**
- **type** − logger

## Example Configuration File
`netcat.conf`
```
# Naming the components on the current agent
NetcatAgent.sources = Netcat
NetcatAgent.channels = MemChannel
NetcatAgent.sinks = LoggerSink

# Describing/Configuring the source
NetcatAgent.sources.Netcat.type = netcat
NetcatAgent.sources.Netcat.bind = localhost
NetcatAgent.sources.Netcat.port = 56565

# Describing/Configuring the sink
NetcatAgent.sinks.LoggerSink.type = logger

# Describing/Configuring the channel
NetcatAgent.channels.MemChannel.type = memory
NetcatAgent.channels.MemChannel.capacity = 1000
NetcatAgent.channels.MemChannel.transactionCapacity = 100

# Bind the source and sink to the channel
NetcatAgent.sources.Netcat.channels = MemChannel
NetcatAgent.sinks.LoggerSink.channel = MemChannel
```

## Execution
```
$ cd $FLUME_HOME
$ ./bin/flume-ng agent --conf $FLUME_HOME/conf --conf-file $FLUME_HOME/conf/netcat.conf --name NetcatAgent -Dflume.root.logger=INFO,console
```

### Passing Data to the Source
```
$ curl telnet://localhost:56565
```

## References
- [Flume - NetCat Source](https://www.tutorialspoint.com/apache_flume/apache_flume_netcat_source.htm)
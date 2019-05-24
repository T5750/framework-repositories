# HBase Shell

## General Commands
- `status`
- `version`
- `table_help`
- `whoami`

## Data Definition Language
- `create`
- `list` - Lists all the tables in HBase.
- `disable`
- `is_disabled`
- `enable`
- `is_enabled`
- `describe`
- `alter`
- `exists`
- `drop`
- `drop_all`
- Java Admin API

## Data Manipulation Language
- `put`
- `get`
- `delete`
- `deleteall`
- `scan` - Scans and returns the table data.
- `count`
- `truncate`
- Java client API

## Starting HBase Shell
`./bin/hbase shell`

## Results
```
@shell.hbase.configuration.get("hbase.zookeeper.quorum")
@shell.hbase.configuration.get("hbase.zookeeper.property.clientPort")
@shell.hbase.configuration.get("hbase.master.info.port")
@shell.hbase.configuration.get("hbase.master.port")
@shell.hbase.configuration.get("hbase.rootdir")
@shell.hbase.configuration.get("hbase.zookeeper.property.dataDir")
```

## References
- [HBase - Shell](https://www.tutorialspoint.com/hbase/hbase_shell.htm)
# HDFS

## Features of HDFS
- Highly Scalable
- Replication
- Fault tolerance
- Distributed data storage
- Portable

## Where to use HDFS
- Very Large Files
- Streaming Data Access
- Commodity Hardware

## Where not to use HDFS
- Low Latency data access
- Lots Of Small Files
- Multiple Writes

## HDFS Architecture
![hdfs_architecture-min-min](https://s0.wailian.download/2019/05/14/hdfs_architecture-min-min.jpg)

HDFS Read Image:

![HDFS-Read](https://s0.wailian.download/2019/05/22/HDFS-Read.png)

HDFS Write Image:

![HDFS-Write](https://s0.wailian.download/2019/05/22/HDFS-Write.png)

## Starting HDFS
```
hadoop namenode -format
start-dfs.sh
```

## Listing Files in HDFS
```
$HADOOP_HOME/bin/hadoop fs -ls <args>
```

## Inserting Data into HDFS
1. `hadoop fs -mkdir -p /user/input`
1. `hadoop fs -put /home/hadoop/input/README.txt /user/input`
1. `hadoop fs -ls /user/input`

## Retrieving Data from HDFS
1. `hadoop fs -cat /user/input/README.txt`
1. `hadoop fs -get /user/input/README.txt /home/hadoop/`

## Shutting Down the HDFS
```
stop-dfs.sh
```

## HDFS Basic File Operations
1. Putting data to HDFS from local file system: `hadoop fs -copyFromLocal /usr/home/Desktop/data.txt /user/test`
1. Copying data from HDFS to local file system: `hadoop fs -copyToLocal /user/test/data.txt /usr/bin/data_copy.txt`
1. Compare the files and see that both are same: `md5 /usr/bin/data_copy.txt /usr/home/Desktop/data.txt`
    - Recursive deleting: `hadoop fs -rmr <arg>`

## HDFS Other commands
- `put <localSrc><dest>`
- `copyFromLocal <localSrc><dest>`
- `moveFromLocal <localSrc><dest>`
- `get [-crc] <src><localDest>`
- `cat <filen-ame>`
- `moveToLocal <src><localDest>`
- `setrep [-R] [-w] rep <path>`
- `touchz <path>`
- `test -[ezd] <path>`
- `stat [format] <path>`

## References
- [Hadoop - HDFS Operations](https://www.tutorialspoint.com/hadoop/hadoop_hdfs_operations.htm)
- [HDFS](https://www.javatpoint.com/hdfs)
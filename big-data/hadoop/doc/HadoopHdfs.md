# HDFS

## HDFS Architecture
![hdfs_architecture-min-min](https://www.wailian.work/images/2019/05/14/hdfs_architecture-min-min.jpg)

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

## References
- [Hadoop - HDFS Operations](https://www.tutorialspoint.com/hadoop/hadoop_hdfs_operations.htm)
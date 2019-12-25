# MapReduce

>MapReduce: Simplified Data Processing on Large Clusters

Steps in Map Reduce

![mapreduce-introduction](https://www.wailian.work/images/2019/05/22/mapreduce-introduction.png)

Data Flow In MapReduce

![data-flow-in-mapreduce](https://www.wailian.work/images/2019/05/22/data-flow-in-mapreduce.png)

## The Algorithm
![mapreduce_algorithm-min-min](https://www.wailian.work/images/2019/05/15/mapreduce_algorithm-min-min.jpg)

## Inputs and Outputs (Java Perspective)
Input and Output types of a MapReduce job − (Input) <k1, v1> → map → <k2, v2> → reduce → <k3, v3>(Output).

MapReduce | Input | Output
---|----|----
Map | &lt;k1, v1&gt; | list (&lt;k2, v2&gt;)
Reduce | &lt;k2, list(v2)&gt; | list (&lt;k3, v3&gt;)

## Terminology
- PayLoad − Applications implement the Map and the Reduce functions, and form the core of the job.
- Mapper − Mapper maps the input key/value pairs to a set of intermediate key/value pair.
- NamedNode − Node that manages the Hadoop Distributed File System (HDFS).
- DataNode − Node where data is presented in advance before any processing takes place.
- MasterNode − Node where JobTracker runs and which accepts job requests from clients.
- SlaveNode − Node where Map and Reduce program runs.
- JobTracker − Schedules jobs and tracks the assign jobs to Task tracker.
- Task Tracker − Tracks the task and reports status to JobTracker.
- Job − A program is an execution of a Mapper and Reducer across a dataset.
- Task − An execution of a Mapper or a Reducer on a slice of data.
- Task Attempt − A particular instance of an attempt to execute a task on a SlaveNode.

## Example Scenario
### Input Data
- sample.txt

```
1979 23 23 2 43 24 25 26 26 26 26 25 26 25
1980 26 27 28 28 28 30 31 31 31 30 30 30 29
1981 31 32 32 32 33 34 35 36 36 34 34 34 34
1984 39 38 39 39 39 41 42 43 40 39 38 38 40
1985 38 39 39 39 39 41 41 41 00 40 39 39 45
```

### Example Program
- `ProcessUnits.java`

## Compilation and Execution of Process Units Program
1. `mkdir units`
1. [/home/hadoop/hadoop-core-1.2.1.jar](http://mvnrepository.com/artifact/org.apache.hadoop/hadoop-core/1.2.1)
1. `javac -classpath hadoop-core-1.2.1.jar -d units ProcessUnits.java`
    - `jar -cvf units.jar -C units/ .`
1. `hadoop fs -mkdir /input_dir`
1. `hadoop fs -put /home/hadoop/sample.txt /input_dir`
1. `hadoop fs -ls /input_dir/`
1. `hadoop jar units.jar t5750.hadoop.ProcessUnits /input_dir /output_dir`
1. `hadoop fs -ls /output_dir/`
1. `hadoop fs -cat /output_dir/part-00000`
	```
	1981    34
	1984    40
	1985    45
	```
1. `hadoop fs -cat /output_dir/part-00000 | hadoop fs -get /output_dir /home/hadoop`

## Important Commands
Usage − `hadoop [--config confdir] COMMAND`

Option | Description
---|------
namenode -format | Formats the DFS filesystem.
secondarynamenode | Runs the DFS secondary namenode.
namenode | Runs the DFS namenode.
datanode | Runs a DFS datanode.
dfsadmin | Runs a DFS admin client.
mradmin | Runs a Map-Reduce admin client.
fsck | Runs a DFS filesystem checking utility.
fs | Runs a generic filesystem user client.
balancer | Runs a cluster balancing utility.
oiv | Applies the offline fsimage viewer to an fsimage.
fetchdt | Fetches a delegation token from the NameNode.
jobtracker | Runs the MapReduce job Tracker node.
pipes | Runs a Pipes job.
tasktracker | Runs a MapReduce task Tracker node.
historyserver | Runs job history servers as a standalone daemon.
job | Manipulates the MapReduce jobs.
queue | Gets information regarding JobQueues.
version | Prints the version.
jar &lt;jar&gt; | Runs a jar file.
distcp &lt;srcurl&gt; &lt;desturl&gt; | Copies file or directories recursively.
distcp2 &lt;srcurl&gt; &lt;desturl&gt; | DistCp version 2.
archive -archiveName NAME -p &lt;parent path&gt; &lt;src&gt;* &lt;dest&gt; | Creates a hadoop archive.
classpath | Prints the class path needed to get the Hadoop jar and the required libraries.
daemonlog | Get/Set the log level for each daemon

## How to Interact with MapReduce Jobs
Usage − `hadoop job [GENERIC_OPTIONS]`

GENERIC_OPTION | Description
---|------	
`-submit <job-file>` | Submits the job.
`-status <job-id>` | Prints the map and reduce completion percentage and all job counters.
`-counter <job-id> <group-name> <countername>` | Prints the counter value.
`-kill <job-id>` | Kills the job.
`-events <job-id> <fromevent-#> <#-of-events>` | Prints the events' details received by jobtracker for the given range.
`-history [all] <jobOutputDir> - history < jobOutputDir>` | Prints job details, failed and killed tip details. More details about the job such as successful tasks and task attempts made for each task can be viewed by specifying the [all] option.
`-list[all]` | Displays all jobs. -list displays only jobs which are yet to complete.
`-kill-task <task-id>` | Kills the task. Killed tasks are NOT counted against failed attempts.
`-fail-task <task-id>` | Fails the task. Failed tasks are counted against failed attempts.
`-set-priority <job-id> <priority>` | Changes the priority of the job. Allowed priority values are VERY_HIGH, HIGH, NORMAL, LOW, VERY_LOW

### To see the status of job
```
hadoop job -status <JOB-ID>
e.g. 
hadoop job -status job_1557970197535_0001
```

### To see the history of job output-dir
```
hadoop job -history <DIR-NAME>
e.g. 
hadoop job -history job_1557970197535_0001
```

### To kill the job
```
hadoop job -kill <JOB-ID>
e.g. 
hadoop job -kill job_1557970197535_0001
```

## Word Count Example
### Steps to execute MapReduce word count example
```
mkdir word-count
vi ~/word-count/word-count-data.txt
HDFS is a storage unit of Hadoop
MapReduce is a processing tool of Hadoop
start-dfs.sh
start-yarn.sh
hdfs dfs -mkdir /word-count
hdfs dfs -put ~/word-count/word-count-data.txt /word-count
WordCountMapper, WordCountReducer, WordCountRunner
# IDE-->Gradle-->Tasks-->build-->jar
hadoop jar ~/word-count/hadoop-demos.jar t5750.hadoop.mapred.WordCountRunner /word-count /word-count-output
# Browsing HDFS: http://192.168.100.210:50070/explorer.html#/
hdfs dfs -cat /word-count-output/part-00000
```

## MapReduce Char Count Example
### Steps to execute MapReduce char count example
```
mkdir char-count
vi ~/char-count/char-count-info.txt
hdfs dfs -mkdir /char-count
hdfs dfs -put ~/char-count/char-count-info.txt /char-count
CharCountMapper, CharCountReducer, CharCountRunner
# IDE-->Gradle-->Tasks-->build-->jar
hadoop jar ~/char-count/hadoop-demos.jar t5750.hadoop.mapred.CharCountRunner /char-count /char-count-output
hdfs dfs -cat /char-count-output/part-00000
```

## References
- [Hadoop - MapReduce](https://www.tutorialspoint.com/hadoop/hadoop_mapreduce.htm)
- [What is MapReduce](https://www.javatpoint.com/mapreduce)
- [Data Flow In MapReduce](https://www.javatpoint.com/data-flow-in-mapreduce)
- [MapReduce Word Count Example](https://www.javatpoint.com/mapreduce-word-count-example)
- [MapReduce Char Count Example](https://www.javatpoint.com/mapreduce-char-count-example)
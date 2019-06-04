# Pig Group Operator

## Syntax
```
grunt> Group_data = GROUP Relation_name BY age;
```

## Example
`vi ~/pig/student_details.txt`
```
001,Rajiv,Reddy,21,9848022337,Hyderabad
002,siddarth,Battacharya,22,9848022338,Kolkata
003,Rajesh,Khanna,22,9848022339,Delhi
004,Preethi,Agarwal,21,9848022330,Pune
005,Trupthi,Mohanthy,23,9848022336,Bhuwaneshwar
006,Archana,Mishra,23,9848022335,Chennai
007,Komal,Nayak,24,9848022334,trivendram
008,Bharathi,Nambiayar,24,9848022333,Chennai
```
```
$ hdfs dfs -put ~/pig/student_details.txt hdfs://localhost:9000/pig_data/
grunt> student_details = LOAD 'hdfs://localhost:9000/pig_data/student_details.txt' USING PigStorage(',')
   as (id:int, firstname:chararray, lastname:chararray, age:int, phone:chararray, city:chararray);
grunt> group_data = GROUP student_details by age;
grunt> Dump group_data;
grunt> Describe group_data;
grunt> Illustrate group_data;
```

### Grouping by Multiple Columns
```
grunt> group_multiple = GROUP student_details by (age, city);
grunt> Dump group_multiple;
```

### Group All
```
grunt> group_all = GROUP student_details All;
grunt> Dump group_all;
```

## Tips
```
netstat -ano | grep 10020
vi /usr/local/hadoop/etc/hadoop/mapred-site.xml
<property>
	<name>mapreduce.jobhistory.address</name>
	<value>192.168.100.210:10020</value>
</property>
<property>
	<name>mapreduce.jobhistory.webapp.address</name>
	<value>192.168.100.210:19888</value>
</property>
$HADOOP_HOME/sbin/mr-jobhistory-daemon.sh start historyserver
```

## References
- [Pig - Group Operator](https://www.tutorialspoint.com/apache_pig/apache_pig_group_operator.htm)
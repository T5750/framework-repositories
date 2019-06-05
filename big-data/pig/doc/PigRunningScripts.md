# Pig Running Scripts

## Comments in Pig Script
### Multi-line comments
```
/* These are the multi-line comments
  In the pig script */
```

### Single –line comments
```
--we can write single line comments like this.
```

## Executing Pig Script in Batch mode
Local mode | MapReduce mode
---|---
`$ pig -x local sample_script.pig` | `$ pig -x mapreduce sample_script.pig`

```
grunt> exec /home/hadoop/pig/sample_script.pig
```

### Executing a Pig Script from HDFS
```
$ hdfs dfs -put ~/pig/sample_script.pig hdfs://localhost:9000/pig_data/
$ pig -x mapreduce hdfs://localhost:9000/pig_data/sample_script.pig
student = LOAD 'hdfs://localhost:9000/pig_data/student_details.txt' USING PigStorage(',')
   as (id:int, firstname:chararray, lastname:chararray, age:int, phone:chararray, city:chararray);
student_order = ORDER student BY age DESC;
student_limit = LIMIT student_order 4;
Dump student_limit;
```

## References
- [Pig - Running Scripts](https://www.tutorialspoint.com/apache_pig/apache_pig_running_scripts.htm)
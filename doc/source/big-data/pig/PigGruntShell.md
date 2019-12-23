# Pig Grunt Shell

## Shell Commands
### sh Command
```
grunt> sh shell command parameters
grunt> sh ls
```

### fs Command
```
grunt> sh File System command parameters
grunt> fs -ls /
```

## Utility Commands
### clear Command
```
grunt> clear
```

### help Command
```
grunt> help
```

### history Command
```
grunt> history
```

### set Command

Key | Description and values
---|-----
default_parallel | You can set the number of reducers for a map job by passing any whole number as a value to this key.
debug | You can turn off or turn on the debugging freature in Pig by passing on/off to this key.
job.name | You can set the Job name to the required job by passing a string value to this key.
job.priority | You can set the job priority to a job by passing one of the following values to this key − very_low, low, normal, high, very_high
stream.skippath | For streaming, you can set the path from where the data is not to be transferred, by passing the desired path in the form of a string to this key.

### quit Command
```
grunt> quit
```

### exec Command
```
grunt> exec [–param param_name = param_value] [–param_file file_name] [script]
```

`vi student.txt`
```
001,Rajiv,Hyderabad
002,siddarth,Kolkata
003,Rajesh,Delhi
```
```
hdfs dfs -mkdir /pig_data
hdfs dfs -put ~/pig/student.txt /pig_data
```

`vi sample_script.pig`
```
student = LOAD 'hdfs://localhost:9000/pig_data/student.txt' USING PigStorage(',') 
   as (id:int,name:chararray,city:chararray);
Dump student;
```
```
grunt> exec sample_script.pig
```

### kill Command
```
grunt> kill JobId
grunt> kill Id_0055
```

### run Command
```
grunt> run [–param param_name = param_value] [–param_file file_name] script
grunt> run sample_script.pig
grunt> Dump;
```

**Note** − The difference between exec and the run command is that if we use run, the statements from the script are available in the command history.

## References
- [Pig - Grunt Shell](https://www.tutorialspoint.com/apache_pig/apache_pig_grunt_shell.htm)
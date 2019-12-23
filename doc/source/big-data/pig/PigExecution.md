# Pig Execution

## Execution Modes
- Local Mode
- Tez Local Mode
- Spark Local Mode
- MapReduce Mode
- Tez Mode
- Spark Mode

```
/* local mode */
$ pig -x local ...
 
/* Tez local mode */
$ pig -x tez_local ...
 
/* Spark local mode */
$ pig -x spark_local ...

/* mapreduce mode */
$ pig ...
or
$ pig -x mapreduce ...

/* Tez mode */
$ pig -x tez ...

/* Spark mode */
$ pig -x spark ...
```

## Pig Execution Mechanisms
- **Interactive Mode** (Grunt shell) − You can run Apache Pig in interactive mode using the Grunt shell. In this shell, you can enter the Pig Latin statements and get the output (using Dump operator).
- **Batch Mode** (Script) − You can run Apache Pig in Batch mode by writing the Pig Latin script in a single file with .pig extension.
- **Embedded Mode** (UDF) − Apache Pig provides the provision of defining our own functions (User Defined Functions) in programming languages such as Java, and using them in our script.

## Invoking the Grunt Shell

Local mode | MapReduce mode
---|---
`$ ./pig -x local` | `$ ./pig -x mapreduce`

## Batch Mode
Local mode | MapReduce mode
---|---
`$ pig -x local sample_script.pig` | `$ pig -x mapreduce sample_script.pig`

```
mkdir pig
cp /etc/passwd ~/pig
```
`vi id.pig`
```
/* id.pig */

A = load 'passwd' using PigStorage(':');  -- load the passwd file 
B = foreach A generate $0 as id;  -- extract the user IDs 
store B into 'id.out';  -- write the results to a file name id.out
```
```
pig -x local id.pig
cat id.out/part-m-00000
```

## Tips
```
$ pig -help
vi $PIG_HOME/conf/pig.properties
pig.logfile=/tmp/pig-err.log
tail -f -n 99 /tmp/pig-err.log
```

## References
- [Pig - Execution](https://www.tutorialspoint.com/apache_pig/apache_pig_execution.htm)
- [Getting Started](http://pig.apache.org/docs/r0.17.0/start.html)
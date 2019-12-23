# Pig Load & Store Operators

## Preparing HDFS
- [Enviornment Setup](../hadoop/HadoopEnviornmentSetup.md)

`vi ~/pig/student_data.txt`
```
001,Rajiv,Reddy,9848022337,Hyderabad
002,siddarth,Battacharya,9848022338,Kolkata
003,Rajesh,Khanna,9848022339,Delhi
004,Preethi,Agarwal,9848022330,Pune
005,Trupthi,Mohanthy,9848022336,Bhuwaneshwar
006,Archana,Mishra,9848022335,Chennai.
```
```
$ hdfs dfs -put ~/pig/student_data.txt hdfs://localhost:9000/pig_data/
$ hdfs dfs -cat hdfs://localhost:9000/pig_data/student_data.txt
```

## Reading Data
### The Load Operator
```
Relation_name = LOAD 'Input file path' USING function as schema;
```

- **relation_name** − We have to mention the relation in which we want to store the data.
- **Input file path** − We have to mention the HDFS directory where the file is stored. (In MapReduce mode)
- **function** − We have to choose a function from the set of load functions provided by Apache Pig (BinStorage, JsonLoader, PigStorage, TextLoader).
- **Schema** − We have to define the schema of the data. We can define the required schema as follows −
	```
	(column1 : data type, column2 : data type, column3 : data type);
	```

#### Start the Pig Grunt Shell
```
pig -x mapreduce
```

#### Execute the Load Statement
```
grunt> student = LOAD 'hdfs://localhost:9000/pig_data/student_data.txt'
   USING PigStorage(',')
   as ( id:int, firstname:chararray, lastname:chararray, phone:chararray,
   city:chararray );
```

## Storing Data
```
grunt> STORE student INTO 'hdfs://localhost:9000/pig_output/' USING PigStorage (',');
```

### Verification
```
hdfs dfs -ls 'hdfs://localhost:9000/pig_output/'
hdfs dfs -cat 'hdfs://localhost:9000/pig_output/part-m-00000'
```

## References
- [Pig - Reading Data](https://www.tutorialspoint.com/apache_pig/apache_pig_reading_data.htm)
- [Pig - Storing Data](https://www.tutorialspoint.com/apache_pig/apache_pig_storing_data.htm)
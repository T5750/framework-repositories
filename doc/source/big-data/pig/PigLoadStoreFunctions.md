# Pig Load & Store Functions

Function | Description
---|-----
PigStorage() | To load and store structured files.
TextLoader() | To load unstructured data into Pig.
BinStorage() | To load and store data into Pig using machine readable format.
Handling Compression | In Pig Latin, we can load and store compressed data.

## PigStorage()
```
grunt> PigStorage(field_delimiter)
grunt> student = LOAD 'hdfs://localhost:9000/pig_data/student_data.txt' USING PigStorage(',')
   as ( id:int, firstname:chararray, lastname:chararray, phone:chararray, city:chararray );
grunt> STORE student INTO ' hdfs://localhost:9000/pig_output/ ' USING PigStorage (',');
$ hdfs dfs -ls 'hdfs://localhost:9000/pig_output/'
$ hdfs dfs -cat 'hdfs://localhost:9000/pig_output/part-m-00000'
```

## TextLoader()
```
grunt> TextLoader()
grunt> details = LOAD 'hdfs://localhost:9000/pig_data/student_details.txt' USING TextLoader();
grunt> dump details;
```

## BinStorage()
```
grunt> BinStorage();
grunt> student_details = LOAD 'hdfs://localhost:9000/pig_data/student_details.txt' USING PigStorage(',')
   as (id:int, firstname:chararray, age:int, city:chararray);
grunt> STORE student_details INTO 'hdfs://localhost:9000/pig_output/mydata' USING BinStorage();
$ hdfs dfs -ls hdfs://localhost:9000/pig_output/mydata/
grunt> result = LOAD 'hdfs://localhost:9000/pig_output/mydata/part-m-00000' USING BinStorage();
grunt> Dump result;
```

## Handling Compression
```
$ zip employee.txt.zip employee.txt
$ hdfs dfs -put ~/pig/employee.txt.zip hdfs://localhost:9000/pig_data/
grunt> data = LOAD 'hdfs://localhost:9000/pig_data/employee.txt.zip' USING PigStorage(',');
grunt> data = LOAD 'hdfs://localhost:9000/pig_data/employee.txt.zip' USING TextLoader;
grunt> store data INTO 'hdfs://localhost:9000/pig_output/data.bz' USING PigStorage(',');
```

## References
- [Pig - Load & Store Functions](https://www.tutorialspoint.com/apache_pig/apache_pig_load_store_functions.htm)
- [Pig - PigStorage()](https://www.tutorialspoint.com/apache_pig/apache_pig_pigstore.htm)
- [Pig - BinStorage()](https://www.tutorialspoint.com/apache_pig/apache_pig_binstorage.htm)
- [Pig - Handling Compression](https://www.tutorialspoint.com/apache_pig/apache_pig_handling_compression.htm)
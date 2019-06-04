# Combining & Splitting

## Union Operator
```
grunt> Relation_name3 = UNION Relation_name1, Relation_name2;
```
`vi ~/pig/student_data2.txt`
```
7,Komal,Nayak,9848022334,trivendram.
8,Bharathi,Nambiayar,9848022333,Chennai.
```
```
$ hdfs dfs -put ~/pig/student_data2.txt hdfs://localhost:9000/pig_data/
grunt> student1 = LOAD 'hdfs://localhost:9000/pig_data/student_data.txt' USING PigStorage(',')
   as (id:int, firstname:chararray, lastname:chararray, phone:chararray, city:chararray);
grunt> student2 = LOAD 'hdfs://localhost:9000/pig_data/student_data2.txt' USING PigStorage(',')
   as (id:int, firstname:chararray, lastname:chararray, phone:chararray, city:chararray);
grunt> student = UNION student1, student2;
grunt> Dump student;
```

## Split Operator
```
grunt> SPLIT Relation1_name INTO Relation2_name IF (condition1), Relation2_name (condition2)
```
```
grunt> student_details = LOAD 'hdfs://localhost:9000/pig_data/student_details.txt' USING PigStorage(',')
   as (id:int, firstname:chararray, lastname:chararray, age:int, phone:chararray, city:chararray);
grunt> SPLIT student_details into student_details1 if age<23, student_details2 if (22<age and age>25);
grunt> Dump student_details1;
grunt> Dump student_details2;
```

## References
- [Pig - Union Operator](https://www.tutorialspoint.com/apache_pig/apache_pig_union_operator.htm)
- [Pig - Split Operator](https://www.tutorialspoint.com/apache_pig/apache_pig_split_operator.htm)
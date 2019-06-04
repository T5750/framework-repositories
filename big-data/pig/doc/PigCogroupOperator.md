# Pig Cogroup Operator

The only difference between the two operators is that the group operator is normally used with one relation, while the cogroup operator is used in statements involving two or more relations.

## Grouping Two Relations using Cogroup
`vi ~/pig/employee_details.txt`
```
001,Robin,22,newyork
002,BOB,23,Kolkata
003,Maya,23,Tokyo
004,Sara,25,London
005,David,23,Bhuwaneshwar
006,Maggy,22,Chennai
```
```
$ hdfs dfs -put ~/pig/employee_details.txt hdfs://localhost:9000/pig_data/
grunt> student_details = LOAD 'hdfs://localhost:9000/pig_data/student_details.txt' USING PigStorage(',')
   as (id:int, firstname:chararray, lastname:chararray, age:int, phone:chararray, city:chararray);
grunt> employee_details = LOAD 'hdfs://localhost:9000/pig_data/employee_details.txt' USING PigStorage(',')
   as (id:int, name:chararray, age:int, city:chararray);
grunt> cogroup_data = COGROUP student_details by age, employee_details by age;
grunt> Dump cogroup_data;
```

## References
- [Pig - Cogroup Operator](https://www.tutorialspoint.com/apache_pig/apache_pig_cogroup_operator.htm)
# Sorting

## Order By
```
grunt> Relation_name2 = ORDER Relatin_name1 BY (ASC|DESC);
grunt> student_details = LOAD 'hdfs://localhost:9000/pig_data/student_details.txt' USING PigStorage(',')
   as (id:int, firstname:chararray, lastname:chararray,age:int, phone:chararray, city:chararray);
grunt> order_by_data = ORDER student_details BY age DESC;
grunt> Dump order_by_data;
```

## Limit Operator
```
grunt> Result = LIMIT Relation_name required number of tuples;
grunt> student_details = LOAD 'hdfs://localhost:9000/pig_data/student_details.txt' USING PigStorage(',')
   as (id:int, firstname:chararray, lastname:chararray,age:int, phone:chararray, city:chararray);
grunt> limit_data = LIMIT student_details 4;
grunt> Dump limit_data;
```

## References
- [Pig - Order By](https://www.tutorialspoint.com/apache_pig/apache_pig_order_by.htm)
- [Pig - Limit Operator](https://www.tutorialspoint.com/apache_pig/apache_pig_limit_operator.htm)
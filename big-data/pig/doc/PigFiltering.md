# Filtering

## Filter Operator
```
grunt> Relation2_name = FILTER Relation1_name BY (condition);
grunt> student_details = LOAD 'hdfs://localhost:9000/pig_data/student_details.txt' USING PigStorage(',')
   as (id:int, firstname:chararray, lastname:chararray, age:int, phone:chararray, city:chararray);
grunt> filter_data = FILTER student_details BY city == 'Chennai';
grunt> Dump filter_data;
```

## Distinct Operator
```
grunt> Relation_name2 = DISTINCT Relatin_name1;
grunt> student_details = LOAD 'hdfs://localhost:9000/pig_data/student_details.txt' USING PigStorage(',')
   as (id:int, firstname:chararray, lastname:chararray, phone:chararray, city:chararray);
grunt> distinct_data = DISTINCT student_details;
grunt> Dump distinct_data;
```

## Foreach Operator
```
grunt> Relation_name2 = FOREACH Relatin_name1 GENERATE (required data);
grunt> student_details = LOAD 'hdfs://localhost:9000/pig_data/student_details.txt' USING PigStorage(',')
   as (id:int, firstname:chararray, lastname:chararray,age:int, phone:chararray, city:chararray);
grunt> foreach_data = FOREACH student_details GENERATE id,age,city;
grunt> Dump foreach_data;
```

## References
- [Pig - Filter Operator](https://www.tutorialspoint.com/apache_pig/apache_pig_filter_operator.htm)
- [Pig - Distinct Operator](https://www.tutorialspoint.com/apache_pig/apache_pig_distinct_operator.htm)
- [Pig - Foreach Operator](https://www.tutorialspoint.com/apache_pig/apache_pig_foreach_operator.htm)
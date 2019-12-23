# Pig Bag & Tuple Functions

Function | Description
---|-----
TOBAG() | To convert two or more expressions into a bag.
TOP() | To get the top N tuples of a relation.
TOTUPLE() | To convert one or more expressions into a tuple.
TOMAP() | To convert the key-value pairs into a Map.

## TOBAG()
```
TOBAG(expression [, expression ...])
grunt> emp_data = LOAD 'hdfs://localhost:9000/pig_data/employee_details.txt' USING PigStorage(',')
   as (id:int, name:chararray, age:int, city:chararray);
grunt> tobag = FOREACH emp_data GENERATE TOBAG (id,name,age,city);
grunt> DUMP tobag;
```

## TOP()
```
grunt> TOP(topN,column,relation)
grunt> emp_data = LOAD 'hdfs://localhost:9000/pig_data/employee_details.txt' USING PigStorage(',')
   as (id:int, name:chararray, age:int, city:chararray);
grunt> emp_group = Group emp_data BY age;
grunt> Dump emp_group;

grunt> data_top = FOREACH emp_group {
   top = TOP(2, 0, emp_data);
   GENERATE top; 
}
grunt> Dump data_top;
```

## TOTUPLE()
```
grunt> TOTUPLE(expression [, expression ...])
grunt> emp_data = LOAD 'hdfs://localhost:9000/pig_data/employee_details.txt' USING PigStorage(',')
   as (id:int, name:chararray, age:int, city:chararray);
grunt> totuple = FOREACH emp_data GENERATE TOTUPLE (id,name,age);
grunt> DUMP totuple;
```

## TOMAP()
```
grunt> TOMAP(key-expression, value-expression [, key-expression, valueexpression ...])
grunt> emp_data = LOAD 'hdfs://localhost:9000/pig_data/employee_details.txt' USING PigStorage(',')
   as (id:int, name:chararray, age:int, city:chararray);
grunt> tomap = FOREACH emp_data GENERATE TOMAP(name, age);
grunt> DUMP tomap;
```

## References
- [Pig - Bag & Tuple Functions](https://www.tutorialspoint.com/apache_pig/apache_pig_bag_tuple_functions.htm)
- [Pig - TOBAG()](https://www.tutorialspoint.com/apache_pig/apache_pig_tobag.htm)
- [Pig - TOP()](https://www.tutorialspoint.com/apache_pig/apache_pig_top.htm)
- [Pig - TOTUPLE()](https://www.tutorialspoint.com/apache_pig/apache_pig_totuple.htm)
- [Pig - TOMAP()](https://www.tutorialspoint.com/apache_pig/apache_pig_tomap.htm)
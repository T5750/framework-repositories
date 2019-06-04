# Pig Cross Operator

The CROSS operator computes the cross-product of two or more relations.

## Syntax
```
grunt> Relation3_name = CROSS Relation1_name, Relation2_name;
```

## Example
```
grunt> customers = LOAD 'hdfs://localhost:9000/pig_data/customers.txt' USING PigStorage(',')
   as (id:int, name:chararray, age:int, address:chararray, salary:int);
grunt> orders = LOAD 'hdfs://localhost:9000/pig_data/orders.txt' USING PigStorage(',')
   as (oid:int, date:chararray, customer_id:int, amount:int);
grunt> cross_data = CROSS customers, orders;
grunt> Dump cross_data;
```

## References
- [Pig - Cross Operator](https://www.tutorialspoint.com/apache_pig/apache_pig_cross_operator.htm)
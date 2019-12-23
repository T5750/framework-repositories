# Pig Join Operator

`vi ~/pig/customers.txt`
```
1,Ramesh,32,Ahmedabad,2000.00
2,Khilan,25,Delhi,1500.00
3,kaushik,23,Kota,2000.00
4,Chaitali,25,Mumbai,6500.00
5,Hardik,27,Bhopal,8500.00
6,Komal,22,MP,4500.00
7,Muffy,24,Indore,10000.00
```
`vi ~/pig/orders.txt`
```
102,2009-10-08 00:00:00,3,3000
100,2009-10-08 00:00:00,3,1500
101,2009-11-20 00:00:00,2,1560
103,2008-05-20 00:00:00,4,2060
```
```
$ hdfs dfs -put ~/pig/customers.txt hdfs://localhost:9000/pig_data/
$ hdfs dfs -put ~/pig/orders.txt hdfs://localhost:9000/pig_data/
grunt> customers = LOAD 'hdfs://localhost:9000/pig_data/customers.txt' USING PigStorage(',')
   as (id:int, name:chararray, age:int, address:chararray, salary:int);
grunt> orders = LOAD 'hdfs://localhost:9000/pig_data/orders.txt' USING PigStorage(',')
   as (oid:int, date:chararray, customer_id:int, amount:int);
```

## Self - join
```
grunt> customers1 = LOAD 'hdfs://localhost:9000/pig_data/customers.txt' USING PigStorage(',')
   as (id:int, name:chararray, age:int, address:chararray, salary:int);
grunt> customers2 = LOAD 'hdfs://localhost:9000/pig_data/customers.txt' USING PigStorage(',')
   as (id:int, name:chararray, age:int, address:chararray, salary:int);
grunt> Relation3_name = JOIN Relation1_name BY key, Relation2_name BY key;
grunt> customers3 = JOIN customers1 BY id, customers2 BY id;
grunt> Dump customers3;
```

## Inner Join
```
grunt> result = JOIN relation1 BY columnname, relation2 BY columnname;
grunt> coustomer_orders = JOIN customers BY id, orders BY customer_id;
grunt> Dump coustomer_orders;
```

## Left Outer Join
```
grunt> Relation3_name = JOIN Relation1_name BY id LEFT OUTER, Relation2_name BY customer_id;
grunt> outer_left = JOIN customers BY id LEFT OUTER, orders BY customer_id;
grunt> Dump outer_left;
```

## Right Outer Join
```
grunt> outer_right = JOIN customers BY id RIGHT, orders BY customer_id;
grunt> outer_right = JOIN customers BY id RIGHT, orders BY customer_id;
grunt> Dump outer_right
```

## Full Outer Join
```
grunt> outer_full = JOIN customers BY id FULL OUTER, orders BY customer_id;
grunt> outer_full = JOIN customers BY id FULL OUTER, orders BY customer_id;
grun> Dump outer_full;
```

## Using Multiple Keys
```
grunt> Relation3_name = JOIN Relation2_name BY (key1, key2), Relation3_name BY (key1, key2);
```
`vi ~/pig/employee.txt`
```
001,Rajiv,Reddy,21,programmer,003
002,siddarth,Battacharya,22,programmer,003
003,Rajesh,Khanna,22,programmer,003
004,Preethi,Agarwal,21,programmer,003
005,Trupthi,Mohanthy,23,programmer,003
006,Archana,Mishra,23,programmer,003
007,Komal,Nayak,24,teamlead,002
008,Bharathi,Nambiayar,24,manager,001
```
`vi ~/pig/employee_contact.txt`
```
001,9848022337,Rajiv@gmail.com,Hyderabad,003
002,9848022338,siddarth@gmail.com,Kolkata,003
003,9848022339,Rajesh@gmail.com,Delhi,003
004,9848022330,Preethi@gmail.com,Pune,003
005,9848022336,Trupthi@gmail.com,Bhuwaneshwar,003
006,9848022335,Archana@gmail.com,Chennai,003
007,9848022334,Komal@gmail.com,trivendram,002
008,9848022333,Bharathi@gmail.com,Chennai,001
```
```
$ hdfs dfs -put ~/pig/employee.txt hdfs://localhost:9000/pig_data/
$ hdfs dfs -put ~/pig/employee_contact.txt hdfs://localhost:9000/pig_data/
grunt> employee = LOAD 'hdfs://localhost:9000/pig_data/employee.txt' USING PigStorage(',')
   as (id:int, firstname:chararray, lastname:chararray, age:int, designation:chararray, jobid:int);
grunt> employee_contact = LOAD 'hdfs://localhost:9000/pig_data/employee_contact.txt' USING PigStorage(',') 
   as (id:int, phone:chararray, email:chararray, city:chararray, jobid:int);
grunt> emp = JOIN employee BY (id,jobid), employee_contact BY (id,jobid);
grunt> Dump emp;
```

## References
- [Pig - Join Operator](https://www.tutorialspoint.com/apache_pig/apache_pig_join_operator.htm)
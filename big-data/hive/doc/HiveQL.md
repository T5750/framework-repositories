# HiveQL

## Select-Where
### Syntax
```
SELECT [ALL | DISTINCT] select_expr, select_expr, ... 
FROM table_reference 
[WHERE where_condition] 
[GROUP BY col_list] 
[HAVING having_condition] 
[CLUSTER BY col_list | [DISTRIBUTE BY col_list] [SORT BY col_list]] 
[LIMIT number];
```

### Example
```
hive> SELECT * FROM employee WHERE salary>30000;
```

### JDBC Program
- `HiveQLWhere`

## Select-Order By
### Syntax
```
SELECT [ALL | DISTINCT] select_expr, select_expr, ... 
FROM table_reference 
[WHERE where_condition] 
[GROUP BY col_list] 
[HAVING having_condition] 
[ORDER BY col_list]] 
[LIMIT number];
```

### Example
```
hive> SELECT Id, Name, Dept FROM employee ORDER BY DEPT;
```

### JDBC Program
- `HiveQLOrderBy`

## Select-Group By
### Syntax
```
SELECT [ALL | DISTINCT] select_expr, select_expr, ... 
FROM table_reference 
[WHERE where_condition] 
[GROUP BY col_list] 
[HAVING having_condition] 
[ORDER BY col_list]] 
[LIMIT number];
```

### Example
```
hive> SELECT Dept,count(*) FROM employee GROUP BY DEPT;
```

### JDBC Program
- `HiveQLGroupBy`

## Select-Joins
### Syntax
```
join_table:

   table_reference JOIN table_factor [join_condition]
   | table_reference {LEFT|RIGHT|FULL} [OUTER] JOIN table_reference
   join_condition
   | table_reference LEFT SEMI JOIN table_reference join_condition
   | table_reference CROSS JOIN table_reference [join_condition]
```

### Example
`orders`
```
hive> CREATE TABLE IF NOT EXISTS orders ( oid int, odate DATE, customer_id int, amount Double)
COMMENT 'Employee orders'
ROW FORMAT DELIMITED
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
STORED AS TEXTFILE;
```
```
102	2009-10-08	1203	3000
100	2009-10-08	1203	1500
101	2009-11-20	1202	1560
103	2008-05-20	1204	2060
```
```
hive> LOAD DATA LOCAL INPATH '/home/hadoop/hive/orders.txt'
OVERWRITE INTO TABLE orders;
```

### JOIN
```
hive> SELECT c.ID, c.NAME, c.salary, o.AMOUNT 
FROM employee c JOIN ORDERS o 
ON (c.ID = o.CUSTOMER_ID);
```

### LEFT OUTER JOIN
```
hive> SELECT c.ID, c.NAME, o.AMOUNT, o.odate 
FROM employee c 
LEFT OUTER JOIN ORDERS o 
ON (c.ID = o.CUSTOMER_ID);
```

### RIGHT OUTER JOIN
```
hive> SELECT c.ID, c.NAME, o.AMOUNT, o.odate FROM employee c RIGHT OUTER JOIN ORDERS o ON (c.ID = o.CUSTOMER_ID);
```

### FULL OUTER JOIN
```
hive> SELECT c.ID, c.NAME, o.AMOUNT, o.odate 
FROM employee c 
FULL OUTER JOIN ORDERS o 
ON (c.ID = o.CUSTOMER_ID);
```

## References
- [HiveQL - Select-Where](https://www.tutorialspoint.com/hive/hiveql_select_where.htm)
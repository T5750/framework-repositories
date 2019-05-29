# Hive Built-in Operators

## Relational Operators
Operator | Operand | Description
---|---|-----
A = B | all primitive types | TRUE if expression A is equivalent to expression B otherwise FALSE.
A != B | all primitive types | TRUE if expression A is not equivalent to expression B otherwise FALSE.
A < B | all primitive types | TRUE if expression A is less than expression B otherwise FALSE.
A <= B | all primitive types | TRUE if expression A is less than or equal to expression B otherwise FALSE.
A > B | all primitive types | TRUE if expression A is greater than expression B otherwise FALSE.
A >= B | all primitive types | TRUE if expression A is greater than or equal to expression B otherwise FALSE.
A IS NULL | all types | TRUE if expression A evaluates to NULL otherwise FALSE.
A IS NOT NULL | all types | FALSE if expression A evaluates to NULL otherwise TRUE.
A LIKE B | Strings | TRUE if string pattern A matches to B otherwise FALSE.
A RLIKE B | Strings | NULL if A or B is NULL, TRUE if any substring of A matches the Java regular expression B , otherwise FALSE.
A REGEXP B | Strings | Same as RLIKE.

### Tips
```
hive> CREATE TABLE IF NOT EXISTS employee ( id int, name String,
salary Double, destination String, dept String)
COMMENT 'Employee details'
ROW FORMAT DELIMITED
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
STORED AS TEXTFILE;
```

`vi ~/hive/employee.txt`
```
1201    Gopal   45000   Technical manager       TP
1202    Manisha 45000   Proof reader    PR
1203    Masthanvali     40000   Technical writer        TP
1204    Kiran   40000   Hr Admin        HR
1205    Kranthi 30000   Op Admin        Admin
```
```
hive> LOAD DATA LOCAL INPATH '/home/hadoop/hive/employee.txt'
OVERWRITE INTO TABLE employee;
```

### Example
```
hive> SELECT * FROM employee WHERE Id=1205;
hive> SELECT * FROM employee WHERE Salary>=40000;
```

## Arithmetic Operators
Operators | Operand | Description
---|---|-----
A + B | all number types | Gives the result of adding A and B.
A - B | all number types | Gives the result of subtracting B from A.
A * B | all number types | Gives the result of multiplying A and B.
A / B | all number types | Gives the result of dividing B from A.
A % B | all number types | Gives the reminder resulting from dividing A by B.
A & B | all number types | Gives the result of bitwise AND of A and B.
A | B | all number types | Gives the result of bitwise OR of A and B.
A ^ B | all number types | Gives the result of bitwise XOR of A and B.
~A | all number types | Gives the result of bitwise NOT of A.

### Example
```
hive> SELECT 20+30 ADD FROM employee;
```

## Logical Operators
Operators | Operands | Description
---|---|-----
A AND B | boolean | TRUE if both A and B are TRUE, otherwise FALSE.
A && B | boolean | Same as A AND B.
A OR B | boolean | TRUE if either A or B or both are TRUE, otherwise FALSE.
A || B | boolean | Same as A OR B.
NOT A | boolean | TRUE if A is FALSE, otherwise FALSE.
!A | boolean | Same as NOT A.

### Example
```
hive> SELECT * FROM employee WHERE Salary>40000 AND Dept='TP';
```

## Complex Operators
Operator | Operand | Description
---|---|-----
A[n] | A is an Array and n is an int | It returns the nth element in the array A. The first element has index 0.
M[key] | M is a Map<K, V> and key has type K | It returns the value corresponding to the key in the map.
S.x | S is a struct | It returns the x field of S.

## References
- [Hive - Built-in Operators](https://www.tutorialspoint.com/hive/hive_built_in_operators.htm)
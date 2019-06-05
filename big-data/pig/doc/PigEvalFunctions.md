# Pig Eval Functions

## Eval Functions
Function | Description
---|-----
AVG() | To compute the average of the numerical values within a bag.
BagToString() | To concatenate the elements of a bag into a string. While concatenating, we can place a delimiter between these values (optional).
CONCAT() | To concatenate two or more expressions of same type.
COUNT() | To get the number of elements in a bag, while counting the number of tuples in a bag.
COUNT_STAR() | It is similar to the COUNT() function. It is used to get the number of elements in a bag.
DIFF() | To compare two bags (fields) in a tuple.
IsEmpty() | To check if a bag or map is empty.
MAX() | To calculate the highest value for a column (numeric values or chararrays) in a single-column bag.
MIN() | To get the minimum (lowest) value (numeric or chararray) for a certain column in a single-column bag.
PluckTuple() | Using the Pig Latin PluckTuple() function, we can define a string Prefix and filter the columns in a relation that begin with the given prefix.
SIZE() | To compute the number of elements based on any Pig data type.
SUBTRACT() | To subtract two bags. It takes two bags as inputs and returns a bag which contains the tuples of the first bag that are not in the second bag.
SUM() | To get the total of the numeric values of a column in a single-column bag.
TOKENIZE() | To split a string (which contains a group of words) in a single tuple and return a bag which contains the output of the split operation.

### AVG()
```
grunt> AVG(expression)
grunt> student_details = LOAD 'hdfs://localhost:9000/pig_data/student_details.txt' USING PigStorage(',')
   as (id:int, firstname:chararray, lastname:chararray, age:int, phone:chararray, city:chararray, gpa:int);
grunt> student_group_all = Group student_details All;
grunt> Dump student_group_all;
grunt> student_age_avg = foreach student_group_all Generate
   (student_details.firstname, student_details.age), AVG(student_details.age);
grunt> Dump student_age_avg;
```

### BagToString()
```
grunt> BagToString(vals:bag [, delimiter:chararray])
grunt> group_dob = Group student_details All;
grunt> Dump group_dob;
grunt> dob_string = foreach group_dob Generate BagToString(student_details);
grunt> Dump dob_string;
```

### CONCAT()
```
grunt> CONCAT (expression, expression, [...expression])
grunt> student_name_concat = foreach student_details Generate CONCAT (firstname, lastname);
grunt> Dump student_name_concat;
grunt> CONCAT(firstname, '_',lastname);
grunt> student_name_concat = foreach student_details GENERATE CONCAT(firstname, '_',lastname);
grunt> Dump student_name_concat;
```

### DIFF()
```
grunt> DIFF (expression, expression)
```
`vi ~/pig/emp_sales.txt`
```
1,Robin,22,25000,sales
2,BOB,23,30000,sales
3,Maya,23,25000,sales
4,Sara,25,40000,sales
5,David,23,45000,sales
6,Maggy,22,35000,sales
```
`vi ~/pig/emp_bonus.txt`
```
1,Robin,22,25000,sales
2,Jaya,23,20000,admin
3,Maya,23,25000,sales
4,Alia,25,50000,admin
5,David,23,45000,sales
6,Omar,30,30000,admin
```
```
$ hdfs dfs -put ~/pig/emp_sales.txt hdfs://localhost:9000/pig_data/
$ hdfs dfs -put ~/pig/emp_bonus.txt hdfs://localhost:9000/pig_data/
grunt> emp_sales = LOAD 'hdfs://localhost:9000/pig_data/emp_sales.txt' USING PigStorage(',')
   as (sno:int, name:chararray, age:int, salary:int, dept:chararray);
grunt> emp_bonus = LOAD 'hdfs://localhost:9000/pig_data/emp_bonus.txt' USING PigStorage(',')
   as (sno:int, name:chararray, age:int, salary:int, dept:chararray);
grunt> cogroup_data = COGROUP emp_sales by sno, emp_bonus by sno;
grunt> Dump cogroup_data;
grunt> diff_data = FOREACH cogroup_data GENERATE DIFF(emp_sales,emp_bonus);
grunt> Dump diff_data;
```

### PluckTuple()
```
DEFINE pluck PluckTuple(expression1) 
DEFINE pluck PluckTuple(expression1,expression3) 
pluck(expression2)

a = load 'a' as (x, y);
b = load 'b' as (x, y);
c = join a by x, b by x;
DEFINE pluck PluckTuple('a::');
d = foreach c generate FLATTEN(pluck(*));
describe c;
c: {a::x: bytearray,a::y: bytearray,b::x: bytearray,b::y: bytearray}
describe d;
d: {plucked::a::x: bytearray,plucked::a::y: bytearray}
DEFINE pluckNegative PluckTuple('a::','false');
d = foreach c generate FLATTEN(pluckNegative(*));
describe d;
d: {plucked::b::x: bytearray,plucked::b::y: bytearray}
```

## References
- [Pig - Eval Functions](https://www.tutorialspoint.com/apache_pig/apache_pig_eval_functions.htm)
- [Pig - AVG()](https://www.tutorialspoint.com/apache_pig/apache_pig_avg.htm)
- [Pig - BagToString()](https://www.tutorialspoint.com/apache_pig/apache_pig_bagtostring.htm)
- [Pig - CONCAT()](https://www.tutorialspoint.com/apache_pig/apache_pig_concat.htm)
- [Pig - DIFF()](https://www.tutorialspoint.com/apache_pig/apache_pig_diff.htm)
- [Pig - PluckTuple()](https://www.tutorialspoint.com/apache_pig/apache_pig_plucktuple.htm)
- [Built In Functions](http://pig.apache.org/docs/r0.17.0/func.html#plucktuple)
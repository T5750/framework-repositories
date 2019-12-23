# Pig Diagnostic Operators

## Dump Operator
```
grunt> Dump Relation_Name
grunt> student = LOAD 'hdfs://localhost:9000/pig_data/student_data.txt' USING PigStorage(',')
   as ( id:int, firstname:chararray, lastname:chararray, phone:chararray, city:chararray );
grunt> Dump student
```

## Describe Operator
```
grunt> Describe Relation_name
grunt> describe student;
```

## Explain Operator
```
grunt> explain Relation_name;
grunt> explain student;
```

## Illustrate Operator
```
grunt> illustrate Relation_name;
grunt> illustrate student;
```

## References
- [Pig - Diagnostic Operators](https://www.tutorialspoint.com/apache_pig/apache_pig_diagnostic_operators.htm)
- [Pig - Describe Operator](https://www.tutorialspoint.com/apache_pig/apache_pig_describe_operator.htm)
- [Pig - Explain Operator](https://www.tutorialspoint.com/apache_pig/apache_pig_explain_operator.htm)
- [Pig - Illustrate Operator](https://www.tutorialspoint.com/apache_pig/apache_pig_illustrate_operator.htm)
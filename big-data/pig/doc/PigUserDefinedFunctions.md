# Pig User Defined Functions

## Writing UDF’s using Java
- `build.gradle`
- `SampleEval`

## Using the UDF
### Registering the Jar file
```
REGISTER path;
$./pig -x local
grunt> REGISTER '/home/hadoop/pig/pig-demos.jar'
```

### Defining Alias
```
DEFINE alias {function | [`command` [input] [output] [ship] [cache] [stderr] ] };
DEFINE sample_eval t5750.pig.SampleEval();
```

### Using the UDF
```
grunt> emp_data = LOAD 'hdfs://localhost:9000/pig_data/employee_details.txt' USING PigStorage(',')
   as (id:int, name:chararray, age:int, city:chararray);
grunt> Upper_case = FOREACH emp_data GENERATE sample_eval(name);
grunt> Dump Upper_case;
```

## References
- [Pig - User Defined Functions](https://www.tutorialspoint.com/apache_pig/apache_pig_user_defined_functions.htm)
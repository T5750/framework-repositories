# Pig Math Functions

Functions | Description
---|-----
ABS(expression) | To get the absolute value of an expression.
ACOS(expression) | To get the arc cosine of an expression.
ASIN(expression) | To get the arc sine of an expression.
ATAN(expression) | This function is used to get the arc tangent of an expression.
CBRT(expression) | This function is used to get the cube root of an expression.
CEIL(expression) | This function is used to get the value of an expression rounded up to the nearest integer.
COS(expression) | This function is used to get the trigonometric cosine of an expression.
COSH(expression) | This function is used to get the hyperbolic cosine of an expression.
EXP(expression) | This function is used to get the Euler’s number e raised to the power of x.
FLOOR(expression) | To get the value of an expression rounded down to the nearest integer.
LOG(expression) | To get the natural logarithm (base e) of an expression.
LOG10(expression) | To get the base 10 logarithm of an expression.
RANDOM( ) | To get a pseudo random number (type double) greater than or equal to 0.0 and less than 1.0.
ROUND(expression) | To get the value of an expression rounded to an integer (if the result type is float) or rounded to a long (if the result type is double).
SIN(expression) | To get the sine of an expression.
SINH(expression) | To get the hyperbolic sine of an expression.
SQRT(expression) | To get the positive square root of an expression.
TAN(expression) | To get the trigonometric tangent of an angle.
TANH(expression) | To get the hyperbolic tangent of an expression.

## ABS()
```
grunt> ABS(expression)
```
`vi ~/pig/math.txt`
```
5
16
-9
2.5
-5.9
3.1
```
```
$ hdfs dfs -put ~/pig/math.txt hdfs://localhost:9000/pig_data/
grunt> math_data = LOAD 'hdfs://localhost:9000/pig_data/math.txt' USING PigStorage(',')
   as (data:float);
grunt> abs_data = foreach math_data generate (data), ABS(data);
grunt> Dump abs_data;
```

## References
- [Pig - Math Functions](https://www.tutorialspoint.com/apache_pig/apache_pig_math_functions.htm)
- [Pig - ABS()](https://www.tutorialspoint.com/apache_pig/apache_pig_abs.htm)
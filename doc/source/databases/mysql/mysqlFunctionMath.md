## MySQL Math Functions

### ABS()
Returns the absolute value of a number
```
SELECT ABS(-10), ABS(0), ABS(10);
#SELECT productName, productLine,msrp,ABS(ROUND(msrp - AVG(msrp) OVER(PARTITION BY productLine))) deviation FROM products ORDER BY productName;
SELECT productName, productLine,msrp,ABS(ROUND(msrp - AVG(msrp))) deviation FROM products GROUP BY productLine ORDER BY productName;
```

### CEIL()
Returns the smallest integer value greater than or equal to the input number (n).
```
SELECT CEIL(1.59);
SELECT CEIL(-1.59);
SELECT productLine, CEIL(AVG(msrp)) averageMsrp FROM products GROUP BY productLine ORDER BY averageMsrp;
```

### FLOOR()
Returns the largest integer value not greater than the argument
```
SELECT FLOOR(1.59);-- 1
SELECT FLOOR(-1.59);-- -2
SELECT productLine, FLOOR(AVG(quantityInStock)) averageStock FROM products GROUP BY productLine ORDER BY averageStock;
```

### MOD()
Returns the remainder of a number divided by another
```
SELECT MOD(11, 3);
SELECT 11 % 3;
SELECT MOD(10.5, 3);
SELECT orderNumber,SUM(quantityOrdered) Qty,IF(MOD(SUM(quantityOrdered),2),'Odd','Even') oddOrEven FROM orderdetails GROUP BY orderNumber ORDER BY orderNumber;
```

### ROUND()
Rounds a number to a specified number of decimal places.
```
SELECT ROUND(20.5);-- 21
SELECT ROUND(20.5, 0);-- 21
SELECT ROUND(121.55,-2);-- 100
SELECT ROUND(10.5);-- 11
SELECT ROUND(10.6);-- 11
SELECT ROUND(-10.5);-- -11
SELECT ROUND(-10.6);-- -11
SELECT ROUND(10.4);-- 10
SELECT ROUND(-10.4);-- -10
SELECT productCode,AVG(quantityOrdered * priceEach) avg_order_item_value FROM orderDetails GROUP BY productCode;
SELECT productCode,ROUND(AVG(quantityOrdered * priceEach)) avg_order_item_value FROM orderDetails GROUP BY productCode;
```

### TRUNCATE()
Truncates a number to a specified number of decimal places
```
SELECT TRUNCATE(1.555,1);
SELECT TRUNCATE(199.99,-2);
SELECT TRUNCATE(1.999,1), ROUND(1.999,1);
```

### Others

Name | Description
---|------
ACOS(n) | Returns the arc cosine of n or null if n is not in the range -1 and 1.
ASIN(n) | Returns the arc sine of n which is the value whose sine is n. It returns null if n is not in the range -1 to 1.
ATAN() | Returns the arc tangent of n.
ATAN2(n,m), ATAN(m,n) | Returns the arc tangent of the two variables n and m
CONV(n,from_base,to_base) | Converts a number between different number bases
COS(n) | Returns the cosine of n, where n is in radians
COT(n) | Returns the cotangent of n.
CRC32() | Computes a cyclic redundancy check value and returns a 32-bit unsigned value
DEGREES(n) | Converts radians to degrees of the argument n
EXP(n) | Raises to the power of e raised to power of n
LN(n) | Returns the natural logarithm of n
LOG(n) | Returns the natural logarithm of the first argument
LOG10() | Returns the base-10 logarithm of the argument
LOG2() | Returns the base-2 logarithm of the argument
PI() | Returns the value of PI
POW() | Returns the argument raised to the specified power
POWER() | Returns the argument raised to the specified power
RADIANS() | Returns argument converted to radians
RAND() | Returns a random floating-point value
SIGN(n) | Returns the sign of n that can be -1, 0, or 1 depending on whether n is negative, zero, or positive.
SIN(n) | Returns the sine of n
SQRT(n) | Returns the square root of n
TAN(n) | Returns the tangent of n

### References
- [Math Functions](http://www.mysqltutorial.org/mysql-math-functions/)
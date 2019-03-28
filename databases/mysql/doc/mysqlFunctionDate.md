# MySQL Functions

## Date Functions

### CURDATE
Returns the current date.
```
SELECT CURDATE();
SELECT CURDATE() + 0;
SELECT CURRENT_DATE(), CURRENT_DATE, CURDATE();
SELECT DATE(NOW());
```

### DATEDIFF
Calculates the number of days between two DATE values.
```
SELECT DATEDIFF('2011-08-17','2011-08-17'); --  0 day
SELECT DATEDIFF('2011-08-17','2011-08-08'); --  9 days
SELECT DATEDIFF('2011-08-08','2011-08-17'); -- -9 days
SELECT orderNumber, DATEDIFF(requiredDate, shippedDate) daysLeft FROM orders ORDER BY daysLeft DESC;
SELECT orderNumber, DATEDIFF(requiredDate, orderDate) remaining_days FROM orders WHERE status = 'In Process' ORDER BY remaining_days;
SELECT orderNumber, ROUND(DATEDIFF(requiredDate, orderDate) / 7, 2), ROUND(DATEDIFF(requiredDate, orderDate) / 30,2) FROM orders WHERE status = 'In Process';
```

### DAY
Gets the day of the month of a specified date.
```
SELECT DAY('2010-01-15');
SELECT DAY(LAST_DAY('2016-02-03'));
SELECT DAY(orderdate) dayofmonth, COUNT(*) FROM orders WHERE YEAR(orderdate) = 2004 GROUP BY dayofmonth ORDER BY dayofmonth;
```

### DATE_ADD
Adds a time value to date value.
```
SELECT DATE_ADD('1999-12-31 23:59:59', INTERVAL 1 SECOND) result;
SELECT DATE_ADD('1999-12-31 00:00:01', INTERVAL 1 DAY) result;
SELECT DATE_ADD('1999-12-31 23:59:59', INTERVAL '1:1' MINUTE_SECOND) result;
SELECT DATE_ADD('2000-01-01 00:00:00', INTERVAL '-1 5' DAY_HOUR) result;
SELECT DATE_ADD('1999-12-31 23:59:59.000002', INTERVAL '1.999999' SECOND_MICROSECOND) result;
SELECT DATE_ADD('2000-01-01', INTERVAL 5 / 2 HOUR_MINUTE) result;
SELECT DATE_ADD('2000-01-01', INTERVAL CAST(6/4 AS DECIMAL(3,1)) HOUR_MINUTE) result;
SELECT DATE_ADD('2000-01-01', INTERVAL 12 HOUR) result;
SELECT DATE_ADD('2000-02-30', INTERVAL 1 DAY) result;
SHOW WARNINGS;
SELECT DATE_ADD('2010-01-30', INTERVAL 1 MONTH) result;
SELECT DATE_ADD('2012-01-30', INTERVAL 1 MONTH) result;
```

### DATE_SUB
Subtracts a time value from a date value.
```
SELECT DATE_SUB('2017-07-04',INTERVAL 1 DAY) result;
SELECT DATE_SUB('2017-07-04',INTERVAL 3 HOUR) result;
SELECT DATE_SUB('2017-07-03',INTERVAL -1 DAY) result;
SELECT DATE_SUB('2017-02-29', INTERVAL - 1 DAY) result;
SHOW WARNINGS;
SELECT DATE_SUB('03/07/2017', INTERVAL 1 DAY) result;
SELECT DATE_SUB(NULL, INTERVAL 1 DAY) result;
SELECT DATE_SUB('2017-03-30', INTERVAL 1 MONTH) result;
```

### DATE_FORMAT
Formats a date value based on a specified date format.

Specifier | Meaning
---|------
%a | Three-characters abbreviated weekday name e.g., Mon, Tue, Wed, etc.
%b | Three-characters abbreviated month name e.g., Jan, Feb, Mar, etc.
%c | Month in numeric e.g., 1, 2, 3…12
%D | Day of the month with English suffix e.g., 0th, 1st, 2nd, etc.
%d | Day of the month with leading zero if it is 1 number e.g., 00, 01,02, …31
%e | Day of the month without leading zero e.g., 1,2,…31
%f | Microseconds in the range of 000000..999999
%H | Hour in 24-hour format with leading zero e.g., 00..23
%h | Hour in 12-hour format with leading zero e.g., 01, 02…12
%I | Same as %h
%i | Minutes with leading zero e.g., 00, 01,…59
%j | Day of year with leading zero e.g., 001,002,…366
%k | Hour in 24-hour format without leading zero e.g., 0,1,2…23
%l | Hour in 12-hour format without leading zero e.g., 1,2…12
%M | Full month name e.g., January, February,…December
%m | Month name with leading zero e.g., 00,01,02,…12
%p | AM or PM, depending on other time specifiers
%r | Time in 12-hour format hh:mm:ss AM or PM
%S | Seconds with leading zero 00,01,…59
%s | Same as %S
%T | Time in 24-hour format hh:mm:ss
%U | Week number with leading zero when the first day of week is Sunday e.g., 00,01,02…53
%u | Week number with leading zero when the first day of week is Monday e.g., 00,01,02…53
%V | Same as %U; it is used with %X
%v | Same as %u; it is used with %x
%W | Full name of weekday e.g., Sunday, Monday,…, Saturday
%w | Weekday in number (0=Sunday, 1= Monday,etc.)
%X | Year for the week in four digits where the first day of the week is Sunday; often used with %V
%x | Year for the week, where the first day of the week is Monday, four digits; used with %v
%Y | Four digits year e.g., 2000 and 2001.
%y | Two digits year e.g., 10,11,and 12.
%% | Add percentage (%) character to the output

```
SELECT orderNumber,DATE_FORMAT(orderdate, '%Y-%m-%d') orderDate,DATE_FORMAT(requireddate, '%a %D %b %Y') requireddate,DATE_FORMAT(shippedDate, '%W %D %M %Y') shippedDate FROM orders;
SELECT orderNumber,DATE_FORMAT(shippeddate, '%W %D %M %Y') shippeddate FROM orders WHERE shippeddate IS NOT NULL ORDER BY shippeddate;
SELECT orderNumber,DATE_FORMAT(shippeddate, '%W %D %M %Y') 'Shipped date'FROM orders WHERE shippeddate IS NOT NULL ORDER BY shippeddate;
```

### DAYNAME
Gets the name of a weekday for a specified date.
```
SELECT DAYNAME('2000-01-01') dayname;
SELECT @@lc_time_names;
SET @@lc_time_names = 'zh_CN';
SELECT DAYNAME(orderdate) weekday, COUNT(*) total_orders FROM orders WHERE YEAR(orderdate) = 2004 GROUP BY weekday ORDER BY total_orders DESC;
```

### DAYOFWEEK
Returns the weekday index for a date.
```
SELECT DAYNAME('2012-12-01'), DAYOFWEEK('2012-12-01');
```

### EXTRACT
Extracts part of a date.
```
SELECT EXTRACT(DAY FROM '2017-07-14 09:04:44') DAY;
SELECT EXTRACT(DAY_HOUR FROM '2017-07-14 09:04:44') DAYHOUR;
SELECT EXTRACT(DAY_MICROSECOND FROM '2017-07-14 09:04:44') DAY_MS;
SELECT EXTRACT(DAY_MINUTE FROM '2017-07-14 09:04:44') DAY_M;
SELECT EXTRACT(DAY_SECOND FROM '2017-07-14 09:04:44') DAY_S;
SELECT EXTRACT(HOUR FROM '2017-07-14 09:04:44') HOUR;
SELECT EXTRACT(HOUR_MICROSECOND FROM '2017-07-14 09:04:44') HOUR_MS;
SELECT EXTRACT(HOUR_MINUTE FROM '2017-07-14 09:04:44') HOUR_M;
SELECT EXTRACT(HOUR_SECOND FROM '2017-07-14 09:04:44') HOUR_S;
SELECT EXTRACT(MICROSECOND FROM '2017-07-14 09:04:44') MICROSECOND;
SELECT EXTRACT(MINUTE FROM '2017-07-14 09:04:44') MINUTE;
SELECT EXTRACT(MINUTE_MICROSECOND FROM '2017-07-14 09:04:44') MINUTE_MS;
SELECT EXTRACT(MINUTE_SECOND FROM '2017-07-14 09:04:44') MINUTE_S;
SELECT EXTRACT(MONTH FROM '2017-07-14 09:04:44') MONTH;
SELECT EXTRACT(QUARTER FROM '2017-07-14 09:04:44') QUARTER;
SELECT EXTRACT(SECOND FROM '2017-07-14 09:04:44') SECOND;
SELECT EXTRACT(SECOND_MICROSECOND FROM '2017-07-14 09:04:44') SECOND_MS;
SELECT EXTRACT(WEEK FROM '2017-07-14 09:04:44') WEEK;
SELECT EXTRACT(YEAR FROM '2017-07-14 09:04:44') YEAR;
SELECT EXTRACT(YEAR_MONTH FROM '2017-07-14 09:04:44') YEARMONTH;
```

### NOW
Returns the current date and time at which the statement executed.
```
SELECT NOW();
SELECT NOW() + 0;
SELECT NOW(), SLEEP(5), NOW();
SELECT SYSDATE(), SLEEP(5), SYSDATE();
       -- mysql now minus 1 hour
SELECT (NOW() - INTERVAL 1 HOUR) 'NOW - 1 hour',
        NOW(),
       -- mysql now plus 1 hour
       NOW() + INTERVAL 1 HOUR 'NOW + 1 hour';
        -- mysql now minus 1 day
SELECT (NOW() - INTERVAL 1 DAY) 'NOW - 1 day',
        NOW(),
        -- mysql now plus 1 day
        (NOW() + INTERVAL 1 DAY) 'NOW + 1 day';
CREATE TABLE tmp(
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    created_on DATETIME NOT NULL DEFAULT NOW() -- or CURRENT_TIMESTAMP
);
INSERT INTO tmp(title) VALUES('Test NOW() function');
SELECT * FROM tmp;
```

### MONTH
Returns an integer that represents a month of a specified date.
```
SELECT MONTH('2010-01-01');
SELECT MONTH(NOW()) CURRENT_MONTH;
SELECT MONTH('0000-00-00');
SELECT MONTH(NULL);
SELECT MONTH(orderDate) month,ROUND(SUM(quantityOrdered * priceEach)) subtotal FROM orders INNER JOIN orderdetails USING (orderNumber) WHERE YEAR(orderDate) = 2004 GROUP BY month;
```

### STR_TO_DATE
Converts a string into a date and time value based on a specified format.
```
SELECT STR_TO_DATE('21,5,2013','%d,%m,%Y');
SELECT STR_TO_DATE('21,5,2013 extra characters','%d,%m,%Y');
SELECT STR_TO_DATE('2013','%Y');
SELECT STR_TO_DATE('113005','%h%i%s');
SELECT STR_TO_DATE('11','%h');
SELECT STR_TO_DATE('20130101 1130','%Y%m%d %h%i');
```

### SYSDATE
Returns the current date.
```
SELECT SYSDATE();
SELECT SYSDATE(3);
SELECT SYSDATE(), NOW();
```


## References
- [MySQL Date Functions](http://www.mysqltutorial.org/mysql-date-functions/)
# MySQL Functions

## Date Functions

### CURDATE
```
SELECT CURDATE();
SELECT CURDATE() + 0;
SELECT CURRENT_DATE(), CURRENT_DATE, CURDATE();
SELECT DATE(NOW());
```

### DATEDIFF
```
SELECT DATEDIFF('2011-08-17','2011-08-17'); --  0 day
SELECT DATEDIFF('2011-08-17','2011-08-08'); --  9 days
SELECT DATEDIFF('2011-08-08','2011-08-17'); -- -9 days
SELECT orderNumber, DATEDIFF(requiredDate, shippedDate) daysLeft FROM orders ORDER BY daysLeft DESC;
SELECT orderNumber, DATEDIFF(requiredDate, orderDate) remaining_days FROM orders WHERE status = 'In Process' ORDER BY remaining_days;
SELECT orderNumber, ROUND(DATEDIFF(requiredDate, orderDate) / 7, 2), ROUND(DATEDIFF(requiredDate, orderDate) / 30,2) FROM orders WHERE status = 'In Process';
```

### DAY
```
SELECT DAY('2010-01-15');
SELECT DAY(LAST_DAY('2016-02-03'));
SELECT DAY(orderdate) dayofmonth, COUNT(*) FROM orders WHERE YEAR(orderdate) = 2004 GROUP BY dayofmonth ORDER BY dayofmonth;
```

### DATE_ADD
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


## References
- [MySQL Date Functions](http://www.mysqltutorial.org/mysql-date-functions/)
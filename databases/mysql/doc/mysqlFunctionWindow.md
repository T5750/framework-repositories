# MySQL Functions

## Window Functions
MySQL has supported window functions since version 8.0.
```
CREATE TABLE sales(
    sales_employee VARCHAR(50) NOT NULL,
    fiscal_year INT NOT NULL,
    sale DECIMAL(14,2) NOT NULL,
    PRIMARY KEY(sales_employee,fiscal_year)
);
INSERT INTO sales(sales_employee,fiscal_year,sale)
VALUES('Bob',2016,100),
      ('Bob',2017,150),
      ('Bob',2018,200),
      ('Alice',2016,150),
      ('Alice',2017,100),
      ('Alice',2018,200),
       ('John',2016,200),
      ('John',2017,150),
      ('John',2018,250);
SELECT * FROM sales;
SELECT SUM(sale) FROM sales;
SELECT fiscal_year, SUM(sale) FROM sales GROUP BY fiscal_year;
SELECT fiscal_year, sales_employee,sale,SUM(sale) OVER (PARTITION BY fiscal_year) total_sales FROM sales;
```

### Window Function list
Name | Description
---|------
CUME_DIST | Calculates the cumulative distribution of a value in a set of values.
DENSE_RANK | Assigns a rank to every row within its partition based on the ORDER BY clause. It assigns the same rank to the rows with equal values. If two or more rows have the same rank, then there will be no gaps in the sequence of ranked values.
FIRST_VALUE | Returns the value of the specified expression with respect to the first row in the window frame.
LAG | Returns the value of the Nth row before the current row in a partition. It returns NULL if no preceding row exists.
LAST_VALUE | Returns the value of the specified expression with respect to the last row in the window frame.
LEAD | Returns the value of the Nth row after the current row in a partition. It returns NULL if no subsequent row exists.
NTH_VALUE | Returns value of argument from Nth row of the window frame
NTILE | Distributes the rows for each window partition into a specified number of ranked groups.
PERCENT_RANK | Calculates the percentile rank of a row in a partition or result set
RANK | Similar to the DENSE_RANK() function except that there are gaps in the sequence of ranked values when two or more rows have the same rank.
ROW_NUMBER | Assigns a sequential integer to every row within its partition

## References
- [Window Functions](http://www.mysqltutorial.org/mysql-window-functions/)
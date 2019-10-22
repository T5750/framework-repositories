# MySQL Functions

## Aggregate Functions

### AVG function
```
SELECT AVG(buyPrice) average_buy_price FROM products;
```

### COUNT function
```
SELECT COUNT(*) AS Total FROM products;
```

### SUM function
```
SELECT productCode,sum(priceEach * quantityOrdered) total FROM orderdetails GROUP by productCode;
SELECT P.productCode,P.productName,SUM(priceEach * quantityOrdered) total FROM orderdetails O INNER JOIN products P ON O.productCode = P.productCode GROUP by productCode ORDER BY total;
```

### MAX function
```
SELECT MAX(buyPrice) highest_price FROM Products;
```

### MIN function
```
SELECT MIN(buyPrice) lowest_price FROM Products;
```

## Control Flow Functions

### CASE
return the corresponding result in THEN branch if the condition in the WHEN branch is satisfied, otherwise return the result in the ELSE branch.
```
SELECT customerName, state, country FROM customers ORDER BY (CASE WHEN state IS NULL THEN country ELSE state END);
SELECT 
	SUM(CASE WHEN status = 'Shipped' THEN 1 ELSE 0 END) AS 'Shipped',
	SUM(CASE WHEN status = 'On Hold' THEN 1 ELSE 0 END) AS 'On Hold',
	SUM(CASE WHEN status = 'In Process' THEN 1 ELSE 0 END) AS 'In Process',
	SUM(CASE WHEN status = 'Resolved' THEN 1 ELSE 0 END) AS 'Resolved',
	SUM(CASE WHEN status = 'Cancelled' THEN 1 ELSE 0 END) AS 'Cancelled',
	SUM(CASE WHEN status = 'Disputed' THEN 1 ELSE 0 END) AS 'Disputed',
	COUNT(*) AS Total FROM orders;
```

### IFNULL
return the first argument if it is not NULL , otherwise returns the second argument.
```
SELECT IFNULL(1,0); -- returns 1
SELECT IFNULL('',1); -- returns ''
SELECT IFNULL(NULL,'IFNULL function'); -- returns IFNULL function
CREATE TABLE IF NOT EXISTS contacts (
    contactid INT AUTO_INCREMENT PRIMARY KEY,
    contactname VARCHAR(20) NOT NULL,
    bizphone VARCHAR(15),
    homephone VARCHAR(15)
);
INSERT INTO contacts(contactname,bizphone,homephone)
VALUES('John Doe','(541) 754-3009',NULL),
      ('Cindy Smith',NULL,'(541) 754-3110'),
      ('Sue Greenspan','(541) 754-3010','(541) 754-3011'),
      ('Lily Bush',NULL,'(541) 754-3111');
SELECT contactName, bizphone, homephone FROM contacts;
SELECT contactname, IFNULL(bizphone, homephone) phone FROM contacts;
```

### NULLIF
return NULL if the first argument is equal to the second argument, otherwise, returns the first argument.
```
SELECT NULLIF(1,1); -- return NULL
SELECT NULLIF(1,2); -- return 1
SELECT NULLIF('MySQL NULLIF','MySQL NULLIF'); -- return NULL
SELECT NULLIF('MySQL NULLIF','MySQL IFNULL'); -- return MySQL NULLIF
SELECT NULLIF(1,NULL); -- return 1 because 1 <=> NULL
SELECT NULLIF(NULL,1); -- return NULL the first argument
SELECT 1/0; -- cause error
SELECT 1/NULLIF(0,0); -- return NULL
SELECT orderNumber, orderdate, requiredDate, shippedDate, status FROM orders WHERE orderDate BETWEEN '2003-06-01' AND '2003-06-30';
SELECT SUM(IF(status = 'Shipped',1,0)) / SUM(IF(status = 'Cancelled',1,0)) FROM orders WHERE orderDate BETWEEN '2003-06-01' and '2003-06-30';
SELECT SUM(IF(status = 'Shipped', 1, 0)) / NULLIF(SUM(IF(status = 'Cancelled', 1, 0)), 0) FROM orders WHERE orderDate BETWEEN '2003-06-01' AND '2003-06-30';
```

## Comparison Functions

### COALESCE
return the first non-NULL arguments, which is very handy for substitution of NULL.
```
SELECT COALESCE(NULL, 0);  -- 0
SELECT COALESCE(NULL, NULL); -- NULL;
SELECT customerName, city, state, country FROM customers;
SELECT customerName, city, COALESCE(state, 'N/A'), country FROM customers;
CREATE TABLE articles (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    excerpt TEXT,
    body TEXT NOT NULL,
    published_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
INSERT INTO articles(title,excerpt,body)
VALUES('MySQL COALESCE Tutorial','This tutorial is about MySQL COALESCE function', 'all about COALESCE function'),
      ('MySQL 8.0 New Features',null, 'The following is a list of new features in MySQL 8.0');
SELECT id, title, excerpt, published_at FROM articles;
SELECT id, title, COALESCE(excerpt, LEFT(body, 150)), published_at FROM articles;
SELECT id, title, (CASE WHEN excerpt IS NULL THEN LEFT(body, 150) ELSE excerpt END) AS excerpt, published_at FROM articles;
```

### GREATEST & LEAST
take n arguments and return the greatest and least values of the n arguments respectively.
```
SELECT GREATEST(10, 20, 30),  -- 30
       LEAST(10, 20, 30); -- 10
SELECT GREATEST(10, null, 30),  -- null
       LEAST(10, null , 30); -- null
CREATE TABLE IF NOT EXISTS revenues (
    company_id INT PRIMARY KEY,
    q1 DECIMAL(19 , 2 ),
    q2 DECIMAL(19 , 2 ),
    q3 DECIMAL(19 , 2 ),
    q4 DECIMAL(19 , 2 )
);
INSERT INTO revenues(company_id,q1,q2,q3,q4)
VALUES (1,100,120,110,130),
       (2,250,260,300,310);
SELECT company_id, LEAST(q1, q2, q3, q4) low, GREATEST(q1, q2, q3, q4) high FROM revenues;
INSERT INTO revenues(company_id,q1,q2,q3,q4) VALUES (3,100,120,110,null);
SELECT company_id, LEAST(IFNULL(q1, 0), IFNULL(q2, 0), IFNULL(q3, 0), IFNULL(q4, 0)) low, GREATEST(IFNULL(q1, 0), IFNULL(q2, 0), IFNULL(q3, 0), IFNULL(q4, 0)) high FROM revenues;
```

### ISNULL
return 1 if the argument is NULL, otherwise return zero.
```
SELECT ISNULL(NULL); -- 1    
SELECT ISNULL(1);  -- 0
SELECT ISNULL(1 + NULL); -- 1;
SELECT ISNULL(1 / 0 ); -- 1;
CREATE TABLE special_isnull (
    start_date DATE NOT NULL
);
INSERT INTO special_isnull(start_date) 
VALUES('2000-01-01'),
      ('0000-00-00');
SELECT * FROM special_isnull WHERE ISNULL(start_date);
```

## References
- [MySQL Aggregate Functions](http://www.mysqltutorial.org/mysql-aggregate-functions.aspx)
- [MySQL CASE Expression](http://www.mysqltutorial.org/mysql-case-function/)
- [MySQL Comparison Functions](http://www.mysqltutorial.org/mysql-comparison-functions/)
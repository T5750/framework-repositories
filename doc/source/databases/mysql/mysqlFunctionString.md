## MySQL String Functions

### CONCAT
Concatenate two or more strings into one.
```
SELECT 'MySQL ' 'String ' 'Concatenation';
SELECT CONCAT('MySQL','CONCAT');
SELECT CONCAT('MySQL',NULL,'CONCAT');
SELECT concat(contactFirstName,' ',contactLastName) Fullname FROM customers;
SELECT CONCAT_WS(',','John','Doe');
SELECT CONCAT_WS(NULL ,'Jonathan', 'Smith');
SELECT CONCAT_WS(',','Jonathan', 'Smith',NULL);
SELECT CONCAT_WS(CHAR(13),CONCAT_WS(' ', contactLastname, contactFirstname),addressLine1,addressLine2,CONCAT_WS(' ', postalCode, city),country,CONCAT_WS(CHAR(13), '')) AS Customer_Address FROM customers;
```

### LENGTH & CHAR_LENGTH
Get the length of a string in bytes and in characters.
```
SHOW CHARACTER SET;
SET @s = CONVERT('MySQL String Length' USING ucs2);
SELECT CHAR_LENGTH(@s), LENGTH(@s);
SET @s = CONVERT('MySQL string length' USING latin1);
SELECT LENGTH(@s), CHAR_LENGTH(@s);
SET @s = CONVERT('MySQL string length' USING utf8);
SELECT LENGTH(@s), CHAR_LENGTH(@s);
SET @s = CONVERT('á' USING utf8);
SELECT CHAR_LENGTH(@s), LENGTH(@s);
CREATE TABLE posts(
  postid int auto_increment primary key,
  title varchar(255) NOT NULL,
  excerpt varchar(255) NOT NULL,
  content text,
  pubdate datetime
)Engine=InnoDB;
INSERT INTO posts(title,excerpt,content)
VALUES('MySQL Length','MySQL string length function tutorial','dummy'),
      ('Second blog post','Second blog post','dummy');
SELECT postid,title,IF(CHAR_LENGTH(excerpt) > 20,CONCAT(LEFT(excerpt,20), '...'),excerpt) summary FROM posts;
```

### LEFT
Get the left part of a string with a specified length.
```
SELECT LEFT('MySQL LEFT', 5);
SELECT LEFT('MySQL LEFT', 9999);
SELECT LEFT('MySQL LEFT', 0);
SELECT LEFT('MySQL LEFT', -2);
SELECT LEFT('MySQL LEFT', NULL);
SELECT productname, LEFT(productDescription, 50) summary FROM products;
SELECT LEFT(productdescription, 50) FROM products;
SELECT REVERSE(LEFT(productdescription, 50)) FROM products;
SELECT LOCATE(' ',REVERSE(LEFT(productdescription, 50))) first_space_pos FROM products;
SELECT IFNULL(NULLIF(LOCATE(' ', REVERSE(LEFT(productDescription, 50))), 0) - 1, 0) FROM products;
SELECT productDescription,(50 - IFNULL(NULLIF(LOCATE(' ', REVERSE(LEFT(productDescription, 50))), 0) - 1, 0)) last_space_pos FROM products;
SELECT productDescription, LEFT(productDescription, last_space_pos) FROM (SELECT productDescription,(50 - IFNULL(NULLIF(LOCATE(' ', REVERSE(LEFT(productDescription, 50))), 0) - 1, 0)) last_space_pos FROM products) AS t;
```

### REPLACE
Search and replace a substring in a string.
```
UPDATE products SET productDescription = REPLACE(productDescription,'abuot','about');
```

### SUBSTRING
Extract a substring starting from a position with a specific length.
```
SELECT SUBSTRING('MYSQL SUBSTRING', 7);
SELECT SUBSTRING('MySQL SUBSTRING',-10);
SELECT SUBSTRING('MYSQL SUBSTRING', 0);
SELECT SUBSTRING('MySQL SUBSTRING' FROM -10);
SELECT SUBSTRING('MySQL SUBSTRING',1,5);
SELECT SUBSTRING('MySQL SUBSTRING' FROM 1 FOR 5);
SELECT SUBSTRING('MySQL SUBSTRING',-15,5);
SELECT SUBSTRING('MySQL SUBSTRING' FROM -15 FOR 5);
```

### TRIM
Remove unwanted characters from a string.
```
SELECT TRIM(' MySQL TRIM Function ');
SELECT TRIM(LEADING FROM '    MySQL TRIM Function   ');
SELECT TRIM(TRAILING FROM '    MySQL TRIM Function   ');
UPDATE products SET productname = TRIM(productname);
SELECT LTRIM('  MySQL LTRIM function');
SELECT RTRIM('MySQL RTRIM function   ');
```

### FIND_IN_SET
Find a string within a comma-separated list of strings.
```
SELECT FIND_IN_SET('y','x,y,z'); -- 2
SELECT FIND_IN_SET('a','x,y,z');
SELECT FIND_IN_SET('a','');
SELECT FIND_IN_SET(NULL,'x,y,z');
SELECT FIND_IN_SET('a',NULL);
CREATE TABLE IF NOT EXISTS divisions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(25) NOT NULL,
    belts VARCHAR(200) NOT NULL
);
INSERT INTO divisions(name,belts)
VALUES ('O-1','white,yellow,orange'),
 ('O-2','purple,green,blue'),
 ('O-3','brown,red,black'),
 ('O-4','white,yellow,orange'),
 ('O-5','purple,green,blue'),
 ('O-6','brown,red'),
 ('O-7','black'),
 ('O-8','white,yellow,orange'),
 ('O-9','purple,green,blue'),
 ('O-10','brown,red');
SELECT name, belts FROM divisions WHERE FIND_IN_SET('red', belts);
SELECT name, belts FROM divisions WHERE NOT FIND_IN_SET('black', belts);
SELECT name, belts FROM divisions WHERE name IN ('O-1' , 'O-2');
SELECT name, belts FROM divisions WHERE FIND_IN_SET(name, 'O-1,O-2');
```

### FORMAT
Format a number with a specific locale, rounded to the number of decimals
```
SELECT FORMAT(12500.2015, 2);
SELECT FORMAT(12500.2015, 0);
SELECT FORMAT(12500.2015, 2,'de_DE');
SELECT productname, quantityInStock * buyPrice stock_value FROM products;
SELECT productname,CONCAT('$',FORMAT(quantityInStock * buyPrice, 2)) stock_value FROM products;
SELECT productname,CONCAT('$',FORMAT(quantityInStock * buyPrice, 2)) stock_value FROM products ORDER BY stock_value;
SELECT productname,CONCAT('$',FORMAT(quantityInStock * buyPrice, 2)) stock_value FROM products ORDER BY quantityInStock * buyPrice;
```

### References
- [String Functions](http://www.mysqltutorial.org/mysql-string-functions/)
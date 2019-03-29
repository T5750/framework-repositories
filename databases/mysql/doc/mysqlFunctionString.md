# MySQL Functions

## String Functions

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



## References
- [String Functions](http://www.mysqltutorial.org/mysql-string-functions/)
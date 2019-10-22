# MySQL Index

## Creating indexes

### CREATE INDEX statement
```
CREATE INDEX index_name ON table_name (column_list)
```

Storage Engine | Allowed Index Types
---|---
InnoDB | BTREE
MyISAM | BTREE
MEMORY/HEAP | HASH, BTREE

### CREATE INDEX example
```
EXPLAIN SELECT 
    employeeNumber, 
    lastName, 
    firstName
FROM
    employees
WHERE
    jobTitle = 'Sales Rep';
```
```
CREATE INDEX jobTitle ON employees(jobTitle);
```
```
SHOW INDEXES FROM employees;
```

## Removing indexes

### DROP INDEX syntax
```
DROP INDEX index_name ON table_name
[algorithm_option | lock_option];
```

#### Algorithm
```
ALGORITHM [=] {DEFAULT|INPLACE|COPY}
```
- INPLACE The table is rebuilt in place instead of copied to the new one
- COPY: The table is copied to the new table row by row

#### Lock
```
LOCK [=] {DEFAULT|NONE|SHARED|EXCLUSIVE}
```

### DROP INDEX examples
```
CREATE TABLE leads(
    lead_id INT AUTO_INCREMENT,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL,
    information_source VARCHAR(255),
    INDEX name(first_name,last_name),
    UNIQUE email(email),
    PRIMARY KEY(lead_id)
);
```
```
DROP INDEX name ON leads;
```
```
DROP INDEX email ON leads
ALGORITHM = INPLACE 
LOCK = DEFAULT;
```

### DROP PRIMARY KEY index
```
DROP INDEX `PRIMARY` ON table_name;
```
```
CREATE TABLE t(
    pk INT PRIMARY KEY,
    c VARCHAR(10)
);
```
```
DROP INDEX `PRIMARY` ON t;
```

## Listing table indexes

### SHOW INDEXES command
```
SHOW INDEXES FROM table_name;
SHOW INDEXES FROM table_name IN database_name;
SHOW INDEXES FROM database_name.table_name;
SHOW INDEX IN table_name FROM database_name;
SHOW KEY FROM tablename IN databasename;
```

#### Filter index information
```
SHOW INDEXES FROM table_name
WHERE condition;
```

### SHOW INDEXES examples
```
CREATE TABLE contacts(
    contact_id INT AUTO_INCREMENT,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),
    PRIMARY KEY(contact_id),
    UNIQUE(email),
    ##INDEX phone(phone) INVISIBLE,
    INDEX name(first_name, last_name) comment 'By first name and/or last name'
);
```
```
SHOW INDEXES FROM contacts;
```

## Unique indexes

### CREATE UNIQUE INDEX statement
```
CREATE UNIQUE INDEX index_name
ON table_name(index_column_1,index_column_2,...);
```
```
CREATE TABLE table_name(
...
    UNIQUE KEY(index_column_,index_column_2,...) 
);
```
```
ALTER TABLE table_name
ADD CONSTRAINT constraint_name UNIQUE KEY(column_1,column_2,...);
```

### UNIQUE Index & NULL
Unlike other database systems, MySQL considers `NULL` values as distinct values.

### UNIQUE index examples
```
CREATE TABLE IF NOT EXISTS contacts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    phone VARCHAR(15) NOT NULL,
    email VARCHAR(100) NOT NULL,
    UNIQUE KEY unique_email (email)
);
```
```
INSERT INTO contacts(first_name,last_name,phone,email)
VALUES('John','Doe','(408)-999-9765','john.doe@mysqltutorial.org');
INSERT INTO contacts(first_name,last_name,phone,email)
VALUES('Johny','Doe','(408)-999-4321','john.doe@mysqltutorial.org');
```
```
CREATE UNIQUE INDEX idx_name_phone
ON contacts(first_name,last_name,phone);
```
```
INSERT INTO contacts(first_name,last_name,phone,email)
VALUES('john','doe','(408)-999-9765','john.d@mysqltutorial.org');
```

## Prefix indexes
```
CREATE TABLE table_name(
    column_list,
    INDEX(column_name(length))
);

CREATE INDEX index_name
ON table_name(column_name(length));
```
choose the length of the prefix
1. Find the number of rows in the table: `SELECT COUNT(*) FROM products;`
1. Evaluate different prefix length until you can achieve the reasonable uniqueness of rows: `SELECT COUNT(DISTINCT LEFT(productName, 20)) unique_rows FROM products;`

```
CREATE INDEX idx_productname ON products(productName(20));
EXPLAIN SELECT productName, buyPrice, msrp FROM products WHERE productName LIKE '1970%';
```

## Invisible indexes
```
CREATE INDEX index_name
ON table_name( c1, c2, ...) INVISIBLE;

ALTER TABLE table_name
ALTER INDEX index_name [VISIBLE | INVISIBLE];
```

### Invisible index system variables
By default, the `use_invisible_indexes` is off:
```
SELECT @@optimizer_switch;
```

## Descending indexes
```
CREATE TABLE desc_index(
    a INT,
    b INT,
    INDEX a_asc_b_asc (a ASC , b ASC),
    INDEX a_asc_b_desc (a ASC , b DESC),
    INDEX a_desc_b_asc (a DESC , b ASC),
    INDEX a_desc_b_desc (a DESC , b DESC)
);
```
```
CREATE PROCEDURE insertSampleData(
    IN rowCount INT, 
    IN low INT, 
    IN high INT
)
BEGIN
    DECLARE counter INT DEFAULT 0;
    REPEAT
        SET counter := counter + 1;
        -- insert data
        INSERT INTO desc_index(a,b)
        VALUES(
            ROUND((RAND() * (high-low))+high),
            ROUND((RAND() * (high-low))+high)
        );
    UNTIL counter >= rowCount
    END REPEAT;
END;
```
```
CALL insertSampleData(10000,1,1000);
```
```
EXPLAIN SELECT * FROM desc_index ORDER BY a , b; -- use index a_asc_b_asc
EXPLAIN SELECT * FROM desc_index ORDER BY a , b DESC; -- use index a_asc_b_desc
EXPLAIN SELECT * FROM desc_index ORDER BY a DESC , b; -- use index a_desc_b_asc
EXPLAIN SELECT * FROM desc_index ORDER BY a DESC , b DESC; -- use index a_desc_b_desc
```

## Composite indexes
```
CREATE TABLE table_name (
    c1 data_type PRIMARY KEY,
    c2 data_type,
    c3 data_type,
    c4 data_type,
    INDEX index_name (c2,c3,c4)
);

CREATE INDEX index_name 
ON table_name(c2,c3,c4);
```
```
CREATE INDEX name ON employees(lastName, firstName);
```
```
EXPLAIN SELECT firstName, lastName, email FROM employees WHERE lastName = 'Patterson';
EXPLAIN SELECT firstName, lastName, email FROM employees WHERE lastName = 'Patterson' AND firstName = 'Steve';
EXPLAIN SELECT firstName, lastName, email FROM employees WHERE lastName = 'Patterson' AND (firstName = 'Steve' OR firstName = 'Mary');

EXPLAIN SELECT firstName, lastName, email FROM employees WHERE firstName = 'Leslie';
EXPLAIN SELECT firstName, lastName, email FROM employees WHERE firstName = 'Anthony' OR lastName = 'Steve';
```

## Clustered indexes
- A clustered index is actually the table
- Each InnoDB table always has one and only one clustered index

## Index cardinality
```
mysql> SHOW INDEXES FROM orders;
+--------+------------+----------------+--------------+----------------+-----------+-------------+----------+--------+------+------------+---------+---------------+
| Table  | Non_unique | Key_name       | Seq_in_index | Column_name    | Collation | Cardinality | Sub_part | Packed | Null | Index_type | Comment | Index_comment |
+--------+------------+----------------+--------------+----------------+-----------+-------------+----------+--------+------+------------+---------+---------------+
| orders |          0 | PRIMARY        |            1 | orderNumber    | A         |         326 | NULL     | NULL   |      | BTREE      |         |               |
| orders |          1 | customerNumber |            1 | customerNumber | A         |         326 | NULL     | NULL   |      | BTREE      |         |               |
+--------+------------+----------------+--------------+----------------+-----------+-------------+----------+--------+------+------------+---------+---------------+
```
```
ANALYZE TABLE orders;
```

## USE INDEX hint
```
SELECT select_list
FROM table_name USE INDEX(index_list)
WHERE condition;
```
```
SHOW INDEXES FROM customers;
```
```
CREATE INDEX idx_c_ln ON customers(contactLastName);
CREATE INDEX idx_c_fn ON customers(contactFirstName);
CREATE INDEX idx_name_fl ON customers(contactFirstName,contactLastName);
CREATE INDEX idx_name_lf ON customers(contactLastName,contactFirstName);
```
```
EXPLAIN SELECT * FROM customers WHERE contactFirstName LIKE 'A%' OR contactLastName LIKE 'A%';
+----+-------------+-----------+-------------+-------------------------------------------+-------------------+---------+------+------+--------------------------------------------------+
| id | select_type | table     | type        | possible_keys                             | key               | key_len | ref  | rows | Extra                                            |
+----+-------------+-----------+-------------+-------------------------------------------+-------------------+---------+------+------+--------------------------------------------------+
|  1 | SIMPLE      | customers | index_merge | idx_c_ln,idx_c_fn,idx_name_fl,idx_name_lf | idx_c_fn,idx_c_ln | 52,52   | NULL |   16 | Using sort_union(idx_c_fn,idx_c_ln); Using where |
+----+-------------+-----------+-------------+-------------------------------------------+-------------------+---------+------+------+--------------------------------------------------+
```
```
EXPLAIN SELECT * FROM customers USE INDEX (idx_name_fl, idx_name_lf) WHERE contactFirstName LIKE 'A%' OR contactLastName LIKE 'A%';
+----+-------------+-----------+-------------+-------------------------+-------------------------+---------+------+------+--------------------------------------------------------+
| id | select_type | table     | type        | possible_keys           | key                     | key_len | ref  | rows | Extra                                                  |
+----+-------------+-----------+-------------+-------------------------+-------------------------+---------+------+------+--------------------------------------------------------+
|  1 | SIMPLE      | customers | index_merge | idx_name_fl,idx_name_lf | idx_name_fl,idx_name_lf | 52,52   | NULL |   16 | Using sort_union(idx_name_fl,idx_name_lf); Using where |
+----+-------------+-----------+-------------+-------------------------+-------------------------+---------+------+------+--------------------------------------------------------+
```

## FORCE INDEX hint
```
SELECT * 
FROM table_name 
FORCE INDEX (index_list)
WHERE condition;
```
```
SHOW INDEXES FROM products;
EXPLAIN SELECT productName, buyPrice FROM products WHERE buyPrice BETWEEN 10 AND 80 ORDER BY buyPrice;
CREATE INDEX idx_buyprice ON products(buyPrice);
EXPLAIN SELECT productName, buyPrice FROM products FORCE INDEX (idx_buyPrice) WHERE buyPrice BETWEEN 10 AND 80 ORDER BY buyPrice;
```

## References
- [MySQL Index](http://www.mysqltutorial.org/mysql-index/)
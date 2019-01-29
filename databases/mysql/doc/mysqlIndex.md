# MySQL Index

## CREATE INDEX

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

## DROP INDEX

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

## SHOW INDEXES

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

## UNIQUE Index

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


## References
- [MySQL Index](http://www.mysqltutorial.org/mysql-index/)
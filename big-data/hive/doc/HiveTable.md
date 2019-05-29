# Hive Table

## Create Table
### Create Table Statement
```
CREATE [TEMPORARY] [EXTERNAL] TABLE [IF NOT EXISTS] [db_name.] table_name

[(col_name data_type [COMMENT col_comment], ...)]
[COMMENT table_comment]
[ROW FORMAT row_format]
[STORED AS file_format]
```
```
hive> CREATE TABLE IF NOT EXISTS employee ( eid int, name String,
salary String, destination String)
COMMENT 'Employee details'
ROW FORMAT DELIMITED
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
STORED AS TEXTFILE;
```

#### JDBC Program
- `HiveCreateTable`

### Load Data Statement
```
LOAD DATA [LOCAL] INPATH 'filepath' [OVERWRITE] INTO TABLE tablename
[PARTITION (partcol1=val1, partcol2=val2 ...)]
```

`sample.txt`
```
1201    Gopal   45000   Technical manager
1202    Manisha 45000   Proof reader
1203    Masthanvali     40000   Technical writer
1204    Kiran   40000   Hr Admin
1205    Kranthi 30000   Op Admin
```
```
hive> LOAD DATA LOCAL INPATH '/home/hadoop/hive/sample.txt'
OVERWRITE INTO TABLE employee;
```

#### JDBC Program
- `HiveLoadData`

## Alter Table
### Alter Table Statement
```
ALTER TABLE name RENAME TO new_name
ALTER TABLE name ADD COLUMNS (col_spec[, col_spec ...])
ALTER TABLE name DROP [COLUMN] column_name
ALTER TABLE name CHANGE column_name new_name new_type
ALTER TABLE name REPLACE COLUMNS (col_spec[, col_spec ...])
```

### Rename To… Statement
```
hive> ALTER TABLE employee RENAME TO emp;
```

#### JDBC Program
- `HiveAlterRenameTo`

### Change Statement
Field Name | Convert from Data Type | Change Field Name | Convert to Data Type
---|---|---|---
eid | int | eid | int
**name** | String | **ename** | String
salary | **String** | salary | **Double**
designation | String | designation | String

```
hive> ALTER TABLE emp CHANGE name ename String;
hive> ALTER TABLE emp CHANGE salary salary Double;
```

#### JDBC Program
- `HiveAlterChangeColumn`

### Add Columns Statement
```
ALTER TABLE table_name 
  [PARTITION partition_spec]                 -- (Note: Hive 0.14.0 and later)
  ADD|REPLACE COLUMNS (col_name data_type [COMMENT col_comment], ...)
  [CASCADE|RESTRICT]                         -- (Note: Hive 1.1.0 and later)
```
```
hive> ALTER TABLE emp ADD COLUMNS (
dept STRING COMMENT 'Department name');
```

#### JDBC Program
- `HiveAlterAddColumn`

### Replace Statement
```
hive> ALTER TABLE emp REPLACE COLUMNS (empid Int,name String);
```

#### JDBC Program
- `HiveAlterReplaceColumn`

## Tips
```
show tables;
desc emp;
```

## Drop Table
### Drop Table Statement
```
DROP TABLE [IF EXISTS] table_name;
hive> DROP TABLE IF EXISTS employee;
```

#### JDBC Program
- `HiveDropTable`

## References
- [Hive - Create Table](https://www.tutorialspoint.com/hive/hive_create_table.htm)
- [Hive - Alter Table](https://www.tutorialspoint.com/hive/hive_alter_table.htm)
- [Add/Replace Columns](https://cwiki.apache.org/confluence/display/Hive/LanguageManual+DDL#LanguageManualDDL-Add/ReplaceColumns)
- [Hive - Drop Table](https://www.tutorialspoint.com/hive/hive_drop_table.htm)
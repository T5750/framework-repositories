# Hive View and Indexes

## Creating a View
```
CREATE VIEW [IF NOT EXISTS] view_name [(column_name [COMMENT column_comment], ...) ]
[COMMENT table_comment]
AS SELECT ...
```

### Example
```
hive> CREATE VIEW emp_30000 AS
SELECT * FROM employee
WHERE salary>30000;
```

## Dropping a View
```
DROP VIEW view_name
hive> DROP VIEW emp_30000;
```

## Creating an Index
```
CREATE INDEX index_name
ON TABLE base_table_name (col_name, ...)
AS 'index.handler.class.name'
[WITH DEFERRED REBUILD]
[IDXPROPERTIES (property_name=property_value, ...)]
[IN TABLE index_table_name]
[PARTITIONED BY (col_name, ...)]
[
   [ ROW FORMAT ...] STORED AS ...
   | STORED BY ...
]
[LOCATION hdfs_path]
[TBLPROPERTIES (...)]
```

### Example
```
hive> CREATE INDEX inedx_salary ON TABLE employee(salary)
AS 'org.apache.hadoop.hive.ql.index.compact.CompactIndexHandler'
WITH DEFERRED REBUILD;
```

## Dropping an Index
```
DROP INDEX <index_name> ON <table_name>
hive> DROP INDEX index_salary ON employee;
```

## References
- [Hive - View and Indexes](https://www.tutorialspoint.com/hive/hive_views_and_indexes.htm)
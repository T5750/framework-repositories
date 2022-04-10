# HBase Overview

## Why HBase
- RDBMS get exponentially slow as the data becomes large
- Expects data to be highly structured, i.e. ability to fit in a well-defined schema
- Any change in schema might require a downtime
- For sparse datasets, too much of overhead of maintaining NULL values

## HBase Data Model
![HBase_Data_Model-min-min](https://s0.wailian.download/2019/05/22/HBase_Data_Model-min-min.png)

## HBase Commands
- Create: Creates a new table identified by 'table1' and Column Family identified by 'colf'.
- Put: Inserts a new record into the table with row identified by 'row..'
- Scan: returns the data stored in table
- Get: Returns the records matching the row identifier provided in the table
- Help: Get a list of commands

## HBase and HDFS

HDFS | HBase
----|----
HDFS is a distributed file system suitable for storing large files. | HBase is a database built on top of the HDFS.
HDFS does not support fast individual record lookups. | HBase provides fast lookups for larger tables.
It provides high latency batch processing; no concept of batch processing. | It provides low latency access to single rows from billions of records (Random access).
It provides only sequential access of data. | HBase internally uses Hash tables and provides random access, and it stores the data in indexed HDFS files for faster lookups.

## HBase and RDBMS

HBase | RDBMS
----|----
HBase is schema-less, it doesn't have the concept of fixed columns schema; defines only column families. | An RDBMS is governed by its schema, which describes the whole structure of tables.
It is built for wide tables. HBase is horizontally scalable. | It is thin and built for small tables. Hard to scale.
No transactions are there in HBase. | RDBMS is transactional.
It has de-normalized data. | It will have normalized data.
It is good for semi-structured as well as structured data. | It is good for structured data.

## References
- [What is HBase](https://www.javatpoint.com/what-is-hbase)
- [Data Model](https://www.javatpoint.com/hbase-data-model)
- [HBase - Overview](https://www.tutorialspoint.com/hbase/hbase_overview.htm)
# Hive Introduction

## What is Hive
Hive is a data warehouse infrastructure tool to process structured data in Hadoop. It resides on top of Hadoop to summarize Big Data, and makes querying and analyzing easy.

### Hive is not
- A relational database
- A design for OnLine Transaction Processing (OLTP)
- A language for real-time queries and row-level updates

## Features of Hive
- It stores schema in a database and processed data into HDFS.
- It is designed for OLAP.
- It provides SQL type language for querying called HiveQL or HQL.
- It is familiar, fast, scalable, and extensible.

## Architecture of Hive
![hive_architecture-min](https://www.wailian.work/images/2019/05/27/hive_architecture-min.jpg)

Unit Name | Operation
---|------
User Interface | Hive is a data warehouse infrastructure software that can create interaction between user and HDFS. The user interfaces that Hive supports are Hive Web UI, Hive command line, and Hive HD Insight (In Windows server).
Meta Store | Hive chooses respective database servers to store the schema or Metadata of tables, databases, columns in a table, their data types, and HDFS mapping.
HiveQL Process Engine | HiveQL is similar to SQL for querying on schema info on the Metastore. It is one of the replacements of traditional approach for MapReduce program. Instead of writing MapReduce program in Java, we can write a query for MapReduce job and process it.
Execution Engine | The conjunction part of HiveQL process Engine and MapReduce is Hive Execution Engine. Execution engine processes the query and generates results as same as MapReduce results. It uses the flavor of MapReduce.
HDFS or HBASE | Hadoop distributed file system or HBASE are the data storage techniques to store data into file system.

## Working of Hive
![how_hive_works-min](https://www.wailian.work/images/2019/05/27/how_hive_works-min.jpg)

Step No. | Operation | Description
---|---|------
1 | Execute Query | The Hive interface such as Command Line or Web UI sends query to Driver (any database driver such as JDBC, ODBC, etc.) to execute.
2 | Get Plan | The driver takes the help of query compiler that parses the query to check the syntax and query plan or the requirement of query.
3 | Get Metadata | The compiler sends metadata request to Metastore (any database).
4 | Send Metadata | Metastore sends metadata as a response to the compiler.
5 | Send Plan | The compiler checks the requirement and resends the plan to the driver. Up to here, the parsing and compiling of a query is complete.
6 | Execute Plan | The driver sends the execute plan to the execution engine.

## References
- [Hive - Introduction](https://www.tutorialspoint.com/hive/hive_introduction.htm)
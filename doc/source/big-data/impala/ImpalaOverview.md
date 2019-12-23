# Impala Overview

## What is Impala?
Impala is a MPP (Massive Parallel Processing) SQL query engine for processing huge volumes of data that is stored in Hadoop cluster.

## Relational Databases and Impala

Impala | Relational databases
---|---
Impala uses an SQL like query language that is similar to HiveQL. | Relational databases use SQL language.
In Impala, you cannot update or delete individual records. | In relational databases, it is possible to update or delete individual records.
Impala does not support transactions. | Relational databases support transactions.
Impala does not support indexing. | Relational databases support indexing.
Impala stores and manages large amounts of data (petabytes). | Relational databases handle smaller amounts of data (terabytes) when compared to Impala.

## Hive, Hbase, and Impala

HBase | Hive | Impala
---|---|---
HBase is wide-column store database based on Apache Hadoop. It uses the concepts of BigTable. | Hive is a data warehouse software. Using this, we can access and manage large distributed datasets, built on Hadoop. | Impala is a tool to manage, analyze data that is stored on Hadoop.
The data model of HBase is wide column store. | Hive follows Relational model. | Impala follows Relational model.
HBase is developed using Java language. | Hive is developed using Java language. | Impala is developed using C++.
The data model of HBase is schema-free. | The data model of Hive is Schema-based. | The data model of Impala is Schema-based.
HBase provides Java, RESTful and, Thrift API’s. | Hive provides JDBC, ODBC, Thrift API’s. | Impala provides JDBC and ODBC API’s.
Supports programming languages like C, C#, C++, Groovy, Java PHP, Python, and Scala. | Supports programming languages like C++, Java, PHP, and Python. | Impala supports all languages supporting JDBC/ODBC.
HBase provides support for triggers. | Hive does not provide any support for triggers. | Impala does not provide any support for triggers.

## References
- [Impala - Overview](https://www.tutorialspoint.com/impala/impala_overview.htm)
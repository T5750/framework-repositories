# Pig Overview

## Why Do We Need Apache Pig?
- Pig Latin
- multi-query approach
- SQL-like language

## Features of Pig
- Rich set of operators
- Ease of programming
- Optimization opportunities
- Extensibility
- UDF’s
- Handles all kinds of data

## Pig Vs MapReduce

Apache Pig | MapReduce
---|---
Apache Pig is a data flow language. | MapReduce is a data processing paradigm.
It is a high level language. | MapReduce is low level and rigid.
Performing a Join operation in Apache Pig is pretty simple. | It is quite difficult in MapReduce to perform a Join operation between datasets.
Any novice programmer with a basic knowledge of SQL can work conveniently with Apache Pig. | Exposure to Java is must to work with MapReduce.
Apache Pig uses multi-query approach, thereby reducing the length of the codes to a great extent. | MapReduce will require almost 20 times more the number of lines to perform the same task.
There is no need for compilation. On execution, every Apache Pig operator is converted internally into a MapReduce job. | MapReduce jobs have a long compilation process.

## Pig Vs SQL

Pig | SQL
---|---
Pig Latin is a procedural language. | SQL is a declarative language.
In Apache Pig, schema is optional. We can store data without designing a schema (values are stored as $01, $02 etc.) | Schema is mandatory in SQL.
The data model in Apache Pig is nested relational. | The data model used in SQL is flat relational.
Apache Pig provides limited opportunity for Query optimization. | There is more opportunity for query optimization in SQL.

## Pig Vs Hive

Apache Pig | Hive
---|---
Apache Pig uses a language called Pig Latin. It was originally created at Yahoo. | Hive uses a language called HiveQL. It was originally created at Facebook.
Pig Latin is a data flow language. | HiveQL is a query processing language.
Pig Latin is a procedural language and it fits in pipeline paradigm. | HiveQL is a declarative language.
Apache Pig can handle structured, unstructured, and semi-structured data. | Hive is mostly for structured data.

## References
- [Pig - Overview](https://www.tutorialspoint.com/apache_pig/apache_pig_overview.htm)
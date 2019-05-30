# Hive vs. HBase

## Summary
Hive and HBase are two different Hadoop based technologies
- Hive is an SQL-like engine that runs MapReduce jobs
- HBase is a NoSQL key/value database on Hadoop.
- Hive can be used for analytical queries while HBase for real-time querying.

## NoSQL and Big Data Processing
1. RDBMS
1. NoSQL
1. HBase is an open-source,  distributed, column-oriented database built on top of HDFS based on BigTable!
1. Hive: data warehousing application in Hadoop
    - Query language is HQL, variant of SQL
    - Tables stored on HDFS as flat files
1. Hive + HBase. Reasons to use Hive on HBase:
    - A lot of data sitting in HBase due to its usage in a real-time environment, but never used for analysis
    - Give access to data in HBase usually only queried through MapReduce to people that don’t code (business analysts)
    - When needing a more flexible storage solution, so that rows can be updated live by either a Hive job or an application and can be seen immediately to the other

## References
- [Hive vs. HBase](https://www.xplenty.com/blog/hive-vs-hbase/)
- [Hbase, Hive and Pig](http://www.cs.kent.edu/~jin/Cloud12Spring/HbaseHivePig.pptx)
# Spark SQL Data Sources

Data Sources | Description
---|-----
JSON Datasets | Spark SQL can automatically capture the schema of a JSON dataset and load it as a DataFrame.
Hive Tables | Hive comes bundled with the Spark library as HiveContext, which inherits from SQLContext.
Parquet Files | Parquet is a columnar format, supported by many data processing systems.

## Spark SQL JSON Datasets
- [Spark SQL DataFrames](SparkSqlDataFrames.md)

## Spark SQL Hive Tables
### Start the Spark Shell
```
$ spark-shell
```

### Create SQLContext Object
```
scala> val sqlContext = new org.apache.spark.sql.hive.HiveContext(sc)
```

### Create Table using HiveQL
```
scala> sqlContext.sql("CREATE TABLE IF NOT EXISTS employee(id INT, name STRING, age INT) ROW FORMAT DELIMITED FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n'")
```

### Load Data into Table using HiveQL
```
scala> sqlContext.sql("LOAD DATA LOCAL INPATH 'employee.txt' INTO TABLE employee")
```

### Select Fields from the Table
```
scala> val result = sqlContext.sql("FROM employee SELECT id, name, age")
scala> result.show()
```

## Spark SQL Parquet Files
Parquet is a columnar format
- Columnar storage limits IO operations.
- Columnar storage can fetch specific columns that you need to access.
- Columnar storage consumes less space.
- Columnar storage gives better-summarized data and follows type-specific encoding.

`employee.parquet`
```
$ spark-shell
scala> val sqlContext = new org.apache.spark.sql.SQLContext(sc)
scala> val employee = sqlContext.read.json("employee.json")
scala> employee.write.parquet("employee.parquet")
```
```
$ cd employee.parquet/
$ ls
part-00000-86c92cba-7c98-45fc-9278-099c34214f4b-c000.snappy.parquet  _SUCCESS
```

### Open Spark Shell
```
$ spark-shell
```

### Create SQLContext Object
```
scala> val sqlContext = new org.apache.spark.sql.SQLContext(sc)
```

### Read Input from Text File
```
scala> val parqfile = sqlContext.read.parquet("employee.parquet")
```

### Store the DataFrame into the Table
```
scala> parqfile.registerTempTable("employee")
```

### Select Query on DataFrame
```
scala> val allrecords = sqlContext.sql("SELeCT * FROM employee")
scala> allrecords.show()
```

## References
- [Spark SQL - Data Sources](https://www.tutorialspoint.com/spark_sql/spark_sql_data_sources.htm)
- [Spark SQL - JSON Datasets](https://www.tutorialspoint.com/spark_sql/spark_sql_json_datasets.htm)
- [Spark SQL - Hive Tables](https://www.tutorialspoint.com/spark_sql/spark_sql_hive_tables.htm)
- [Spark SQL - Parquet Files](https://www.tutorialspoint.com/spark_sql/spark_sql_parquet_files.htm)
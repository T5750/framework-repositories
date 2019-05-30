# Spark SQL DataFrames

## SQLContext
```
spark-shell
scala> val sqlcontext = new org.apache.spark.sql.SQLContext(sc)
```

`employee.json`
```
	{"id" : "1201", "name" : "satish", "age" : "25"},
	{"id" : "1202", "name" : "krishna", "age" : "28"},
	{"id" : "1203", "name" : "amith", "age" : "39"},
	{"id" : "1204", "name" : "javed", "age" : "23"},
	{"id" : "1205", "name" : "prudvi", "age" : "23"}
```

## DataFrame Operations
```
scala> val dfs = spark.sqlContext.read.json("employee.json")
scala> dfs.show()
scala> dfs.printSchema()
scala> dfs.select("name").show()
scala> dfs.filter(dfs("age") > 23).show()
scala> dfs.groupBy("age").count().show()
```

## Running SQL Queries Programmatically
Methods | Description
---|----
Inferring the Schema using Reflection | This method uses reflection to generate the schema of an RDD that contains specific types of objects.
Programmatically Specifying the Schema | The second method for creating DataFrame is through programmatic interface that allows you to construct a schema and then apply it to an existing RDD.

## References
- [Spark SQL - DataFrames](https://www.tutorialspoint.com/spark_sql/spark_sql_dataframes.htm)
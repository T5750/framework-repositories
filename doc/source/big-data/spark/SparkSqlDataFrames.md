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

## Inferring the Schema using Reflection
### Example
`employee.txt`
```
1201, satish, 25
1202, krishna, 28
1203, amith, 39
1204, javed, 23
1205, prudvi, 23
```

### Start the Spark Shell
`$ spark-shell`

### Create SQLContext
```
scala> val sqlContext = new org.apache.spark.sql.SQLContext(sc)
```

### Import SQL Functions
```
scala> import sqlContext.implicits._
```

### Create Case Class
```
scala> case class Employee(id: Int, name: String, age: Int)
defined class Employee
```

### Create RDD and Apply Transformations
```
scala> val empl=sc.textFile("employee.txt").map(_.split(",")).map(e=> Employee(e(0).trim.toInt,e(1), e(2).trim.toInt)).toDF()
```

Output: empl: org.apache.spark.sql.DataFrame = [id: int, name: string ... 1 more field]

### Store the DataFrame Data in a Table
```
scala> empl.registerTempTable("employee")
```

### Select Query on DataFrame
```
scala> val allrecords = sqlContext.sql("SELeCT * FROM employee")
scala> allrecords.show()
```

### Where Clause SQL Query on DataFrame
```
scala> val agefilter = sqlContext.sql("SELeCT * FROM employee WHERE age >=20 AND age <= 35")
scala> agefilter.show()
```

### Fetch ID values from agefilter DataFrame using column index
```
scala> agefilter.map(t=>"ID: "+t(0)).collect().foreach(println)
```

## Programmatically Specifying the Schema
### Example
`employee.txt`

### Start the Spark Shell
`$ spark-shell`

### Create SQLContext
```
scala> val sqlContext = new org.apache.spark.sql.SQLContext(sc)
```

### Read Input from Text File
```
scala> val employee = sc.textFile("employee.txt")
```

### Create an Encoded Schema in a String Format
```
scala> val schemaString = "id name age"
```

### Import Respective APIs
```
scala> import org.apache.spark.sql.Row;
scala> import org.apache.spark.sql.types.{StructType, StructField, StringType};
```

### Generate Schema
```
scala> val schema = StructType(schemaString.split(" ").map(fieldName => StructField(fieldName, StringType, true)))
```

### Apply Transformation for Reading Data from Text File
```
scala> val rowRDD = employee.map(_.split(",")).map(e => Row(e(0).trim.toInt, e(1), e(2).trim.toInt))
scala> val rowRDD = employee.map(_.split(",")).map(e => Row(e(0).trim, e(1), e(2).trim))
```

### Apply RowRDD in Row Data based on Schema
```
scala> val employeeDF = sqlContext.createDataFrame(rowRDD, schema)
```

### Store DataFrame Data into Table
```
scala> employeeDF.registerTempTable("employee")
```

### Select Query on DataFrame
```
scala> val allrecords = sqlContext.sql("SELECT * FROM employee")
scala> allrecords.show()
```

## References
- [Spark SQL - DataFrames](https://www.tutorialspoint.com/spark_sql/spark_sql_dataframes.htm)
- [Inferring the Schema using Reflection](https://www.tutorialspoint.com/spark_sql/inferring_schema_using-reflection.htm)
- [Programmatically Specifying the Schema](https://www.tutorialspoint.com/spark_sql/programmatically_specifying_schema.htm)
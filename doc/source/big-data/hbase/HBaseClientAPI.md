# HBase Client API

## Class HBase Configuration

Methods | Description
---|---
static org.apache.hadoop.conf.Configuration create() | This method creates a Configuration with HBase resources.

## Class HTable

Constructors | Description
---|---
HTable() | 
HTable(TableName tableName, ClusterConnection connection, ExecutorService pool) | Using this constructor, you can create an object to access an HBase table.

Methods | Description
---|---
void close() | Releases all the resources of the HTable.
void delete(Delete delete) | Deletes the specified cells/row.
boolean exists(Get get) | Using this method, you can test the existence of columns in the table, as specified by Get.
Result get(Get get) | Retrieves certain cells from a given row.
org.apache.hadoop.conf.Configuration getConfiguration() | Returns the Configuration object used by this instance.
TableName getName() | Returns the table name instance of this table.
HTableDescriptor getTableDescriptor() | Returns the table descriptor for this table.
byte[] getTableName() | Returns the name of this table.
void put(Put put) | Using this method, you can insert data into the table.

## Class Put

Constructors | Description
---|---
Put(byte[] row) | Using this constructor, you can create a Put operation for the specified row.
Put(byte[] rowArray, int rowOffset, int rowLength) | Using this constructor, you can make a copy of the passed-in row key to keep local.
Put(byte[] rowArray, int rowOffset, int rowLength, long ts) | Using this constructor, you can make a copy of the passed-in row key to keep local.
Put(byte[] row, long ts) | Using this constructor, we can create a Put operation for the specified row, using a given timestamp.

Methods | Description
---|---
Put add(byte[] family, byte[] qualifier, byte[] value) | Adds the specified column and value to this Put operation.
Put add(byte[] family, byte[] qualifier, long ts, byte[] value) | Adds the specified column and value, with the specified timestamp as its version to this Put operation.
Put add(byte[] family, ByteBuffer qualifier, long ts, ByteBuffer value) | Adds the specified column and value, with the specified timestamp as its version to this Put operation.
Put add(byte[] family, ByteBuffer qualifier, long ts, ByteBuffer value) | Adds the specified column and value, with the specified timestamp as its version to this Put operation.

## Class Get

Constructors | Description
---|---
Get(byte[] row) | Using this constructor, you can create a Get operation for the specified row.
Get(Get get) | 

Methods | Description
---|---
Get addColumn(byte[] family, byte[] qualifier) | Retrieves the column from the specific family with the specified qualifier.
Get addFamily(byte[] family) | Retrieves all columns from the specified family.

## Class Delete

Constructors | Description
---|---
Delete(byte[] row) | Creates a Delete operation for the specified row.
Delete(byte[] rowArray, int rowOffset, int rowLength) | Creates a Delete operation for the specified row and timestamp.
Delete(byte[] rowArray, int rowOffset, int rowLength, long ts) | Creates a Delete operation for the specified row and timestamp.
Delete(byte[] row, long timestamp) | Creates a Delete operation for the specified row and timestamp.

Methods | Description
---|---
Delete addColumn(byte[] family, byte[] qualifier) | Deletes the latest version of the specified column.
Delete addColumns(byte[] family, byte[] qualifier, long timestamp) | Deletes all versions of the specified column with a timestamp less than or equal to the specified timestamp.
Delete addFamily(byte[] family) | Deletes all versions of all columns of the specified family.
Delete addFamily(byte[] family, long timestamp) | Deletes all columns of the specified family with a timestamp less than or equal to the specified timestamp.

## Class Result

Constructors | Description
---|---
Result() | Using this constructor, you can create an empty Result with no KeyValue payload; returns null if you call raw Cells().

Methods | Description
---|---
byte[] getValue(byte[] family, byte[] qualifier) | This method is used to get the latest version of the specified column.
byte[] getRow() | This method is used to retrieve the row key that corresponds to the row from which this Result was created.

- [HBase - Client API](https://www.tutorialspoint.com/hbase/hbase_client_api.htm)
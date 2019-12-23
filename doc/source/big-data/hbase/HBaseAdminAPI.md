# HBase Admin API

## Class HBaseAdmin
`HBaseAdmin` is a class representing the Admin. This class belongs to the `org.apache.hadoop.hbase.client` package.You can get the instance of Admin using `Connection.getAdmin()` method.

Methods | Description
-----|----
void createTable(HTableDescriptor desc) | Creates a new table.
void createTable(HTableDescriptor desc, byte[][] splitKeys) | Creates a new table with an initial set of empty regions defined by the specified split keys.
void deleteColumn(byte[] tableName, String columnName) | Deletes a column from a table.
void deleteColumn(String tableName, String columnName) | Delete a column from a table.
void deleteTable(String tableName) | Deletes a table.

## Class Descriptor
### Constructors

Constructor | Summary
----|----
HTableDescriptor(TableName name) | Constructs a table descriptor specifying a TableName object.

### Methods and Description

Methods | Description
-----|----
HTableDescriptor addFamily(HColumnDescriptor family) | Adds a column family to the given descriptor

## Shutting Down
- exit: `exit`
- Stopping HBase: `./bin/stop-hbase.sh`

### Stopping HBase Using Java API
- `ShutDownHbase`

## References
- [HBase - Admin API](https://www.tutorialspoint.com/hbase/hbase_admin_api.htm)
- [HBase - Shutting Down](https://www.tutorialspoint.com/hbase/hbase_shutting_down.htm)
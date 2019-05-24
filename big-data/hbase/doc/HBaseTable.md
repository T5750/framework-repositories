# HBase Table

## Create Table
### Creating a Table using HBase Shell
- `create ‘<table name>’,’<column family>’`
- Example: `hbase(main):002:0> create 'emp', 'personal data', 'professional data'`
- Verification: `list`

### Creating a Table Using java API
- `CreateTable`
- `AdminExample`

```
$javac CreateTable.java
$java CreateTable
```

### Tips
- [hadoop-common-2.2.0-bin](https://github.com/srccodes/hadoop-common-2.2.0-bin)

```
HADOOP_HOME
dir\hadoop-common-2.2.0-bin-master
Path
%HADOOP_HOME%\bin;

compile group: 'org.apache.hbase', name: 'hbase-client', version: '1.4.9'
compile group: 'org.apache.hadoop', name: 'hadoop-hdfs', version: '2.9.2'
compile group: 'org.apache.hadoop', name: 'hadoop-common', version: '2.9.2'

vi /etc/hosts
192.168.100.210 tc210

tail -f -n 99 /usr/local/hbase-1.4.9/logs/hbase-hadoop-master-tc210.log
```

## Listing Table
### Listing a Table using HBase Shell
- `list`

### Listing Tables Using Java API
- `ListTables`

## Disabling a Table
### Disabling a Table using HBase Shell
- `disable 'emp'`
- Verification: `scan 'emp'`
- is_disabled: `is_disabled 'emp'`
- disable_all: `disable_all 'r.*'`

### Disable a Table Using Java API
- `DisableTable`

## Enabling a Table
### Enabling a Table using HBase Shell
- `enable 'emp'`
- Verification: `scan 'emp'`
- is_enabled: `is_enabled 'emp'`

### Enable a Table Using Java API
- `EnableTable`

## Describe & Alter
### describe
- `describe 'emp'`

### alter
- Changing the Maximum Number of Cells of a Column Family: `alter 'emp', NAME => 'personal data', VERSIONS => 5`
- Setting Read Only: `alter 'emp', READONLY`
- Removing Table Scope Operators: `alter 't1', METHOD => 'table_att_unset', NAME => 'MAX_FILESIZE'`
- Deleting a Column Family: `alter 'table name', 'delete' => 'column family'`

### Adding a Column Family Using Java API
- `AddColumn`

### Deleting a Column Family Using Java API
- `DeleteColumn`

## Exists
### Existence of Table using HBase Shell
- `exists 'emp'`

### Verifying the Existence of Table Using Java API
- `TableExists`

## Drop a Table
### Dropping a Table using HBase Shell
- `disable 'emp'`
- `drop 'emp'`
- Verification: `exists 'emp'`
- drop_all: `disable_all 'raj.*'`, `drop_all 'raj.*'`

### Deleting a Table Using Java API
- `DeleteTable`

## References
- [HBase - Create Table](https://www.tutorialspoint.com/hbase/hbase_create_table.htm)
- [HBase - Listing Table](https://www.tutorialspoint.com/hbase/hbase_listing_table.htm)
- [HBase - Disabling a Table](https://www.tutorialspoint.com/hbase/hbase_disabling_table.htm)
- [HBase - Enabling a Table](https://www.tutorialspoint.com/hbase/hbase_enabling_table.htm)
- [HBase - Describe & Alter](https://www.tutorialspoint.com/hbase/hbase_describe_and_alter.htm)
- [HBase - Exists](https://www.tutorialspoint.com/hbase/hbase_exists.htm)
- [HBase - Drop a Table](https://www.tutorialspoint.com/hbase/hbase_drop_table.htm)
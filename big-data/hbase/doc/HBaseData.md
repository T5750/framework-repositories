# HBase Data

## Create Data
### Inserting Data using HBase Shell
```
put 'emp','1','personal data:name','raju'
put 'emp','1','personal data:city','hyderabad'
put 'emp','1','professional data:designation','manager'
put 'emp','1','professional data:salary','50000'
scan 'emp'
```

### Inserting Data Using Java API
- `InsertData`

## Update Data
### Updating Data using HBase Shell
```
put 'emp','1','personal data:city','Delhi'
scan 'emp'
```

### Updating Data Using Java API
- `UpdateData`

## Read Data
### Reading Data using HBase Shell
- `get 'emp', '1'`

### Reading a Specific Column
- `get 'emp', '1', {COLUMN => 'personal data:name'}`

### Reading Data Using Java API
- `RetriveData`

## Delete Data
### Deleting a Specific Cell in a Table
- `delete 'emp', '1', 'personal data:city', 1558927088517`

### Deleting All Cells in a Table
```
deleteall 'emp','1'
scan 'emp'
```

### Deleting Data Using Java API
- `DeleteData`

## Scan
### Scaning using HBase Shell
- `scan 'emp'`

### Scanning Using Java API
- `ScanTable`

## Count & Truncate
### count
- `count 'emp'`

### truncate
- `truncate 'emp'`

## Security
### grant
```
grant <user> <permissions> [<table> [<column family> [<column; qualifier>]]
grant 'Tutorialspoint', 'RWXCA'
```

### revoke
```
revoke <user>
revoke 'Tutorialspoint'
```

### user_permission
```
user_permission 'tablename'
user_permission 'emp'
```

## References
- [HBase - Create Data](https://www.tutorialspoint.com/hbase/hbase_create_data.htm)
- [HBase - Update Data](https://www.tutorialspoint.com/hbase/hbase_update_data.htm)
- [HBase - Read Data](https://www.tutorialspoint.com/hbase/hbase_read_data.htm)
- [HBase - Delete Data](https://www.tutorialspoint.com/hbase/hbase_delete_data.htm)
- [HBase - Scan](https://www.tutorialspoint.com/hbase/hbase_scan.htm)
- [HBase - Count & Truncate](https://www.tutorialspoint.com/hbase/hbase_count_truncate.htm)
- [HBase - Security](https://www.tutorialspoint.com/hbase/hbase_security.htm)
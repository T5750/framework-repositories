# Hive Partitioning

## Adding a Partition
```
ALTER TABLE table_name ADD [IF NOT EXISTS] PARTITION partition_spec
[LOCATION 'location1'] partition_spec [LOCATION 'location2'] ...;

partition_spec:
: (p_column = p_col_value, p_column = p_col_value, ...)
```
```
hive> ALTER TABLE employee
> ADD PARTITION (year=’2012’)
> location '/2012/part2012';
```

## Renaming a Partition
```
ALTER TABLE table_name PARTITION partition_spec RENAME TO PARTITION partition_spec;
```
```
hive> ALTER TABLE employee PARTITION (year=’1203’)
   > RENAME TO PARTITION (Yoj=’1203’);
```

## Dropping a Partition
```
ALTER TABLE table_name DROP [IF EXISTS] PARTITION partition_spec, PARTITION partition_spec,...;
```
```
hive> ALTER TABLE employee DROP [IF EXISTS]
   > PARTITION (year=’1203’);
```

## References
- [Hive - Partitioning](https://www.tutorialspoint.com/hive/hive_partitioning.htm)
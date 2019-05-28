# Hive Data Types

## Column Types
### Integral Types
Type | Postfix | Example
---|---|---
TINYINT | Y | 10Y
SMALLINT | S | 10S
INT | - | 10
BIGINT | L | 10L

### String Types
Data Type | Length
---|---
VARCHAR | 1 to 65355
CHAR | 255

### Timestamp
It supports `java.sql.Timestamp` format “YYYY-MM-DD HH:MM:SS.fffffffff” and format “yyyy-mm-dd hh:mm:ss.ffffffffff”.

### Dates
DATE values are described in year/month/day format in the form {{YYYY-MM-DD}}.

### Decimals
```
DECIMAL(precision, scale)
decimal(10,0)
```

### Union Types
```
UNIONTYPE<int, double, array<string>, struct<a:int,b:string>>

{0:1} 
{1:2.0} 
{2:["three","four"]} 
{3:{"a":5,"b":"five"}} 
{2:["six","seven"]} 
{3:{"a":8,"b":"eight"}} 
{0:9} 
{1:10.0}
```

## Literals
### Floating Point Types
Floating point types are nothing but numbers with decimal points. Generally, this type of data is composed of DOUBLE data type.

### Decimal Type
Decimal type data is nothing but floating point value with higher range than DOUBLE data type.

## Null Value
Missing values are represented by the special value NULL.

## Complex Types
### Arrays
```
Syntax: ARRAY<data_type>
```

### Maps
```
Syntax: MAP<primitive_type, data_type>
```

### Structs
Structs in Hive is similar to using complex data with comment.
```
Syntax: STRUCT<col_name : data_type [COMMENT col_comment], ...>
```

## References
- [Hive - Data Types](https://www.tutorialspoint.com/hive/hive_data_types.htm)
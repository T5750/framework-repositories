# Oracle ORAPWD

UNIX syntax:
```
orapwd file=$ORACLE_HOME/dbs/orapw$ORACLE_SID password=the_secret_password
```
UNIX example:
```
orapwd file=/u01/app/oracle/product/11.2.0/db_1/dbs/orapwkrengerdb password=the_secret_password
```
Windows syntax:
```
orapwd file=%ORACLE_HOME%\database\PWD%ORACLE_SID%.ora password=the_secret_password
```
Windows example:
```
orapwd file=D:\oracle\app\product\11.2.0\db_1\database\PWDKRENGERDB.ora password=the_secret_password
```

## Creating a Password File with ORAPWD
```
ORAPWD FILE=filename [ENTRIES=numusers] [FORCE={Y|N}] [IGNORECASE={Y|N}]
```

Argument | Description
---|-----
`FILE` | Name to assign to the password file. You must supply a complete path. If you supply only a file name, the file is written to the current directory.
`ENTRIES` | (Optional) Maximum number of entries (user accounts) to permit in the file.
`FORCE` | (Optional) If `y`, permits overwriting an existing password file.
`IGNORECASE` | (Optional) If `y`, passwords are treated as case-insensitive.

### ORAPWD Command Line Argument Descriptions

Platform | Required Name | Required Location)
---|---|---
UNIX and Linux | `orapwORACLE_SID` | ORACLE_HOME`/dbs`
Windows | `PWDORACLE_SID.ora` | ORACLE_HOME`\database`

## References
- [ORAPWD example](https://www.krenger.ch/blog/orapwd-example/)
- [Creating and Maintaining a Password File](https://docs.oracle.com/cd/E11882_01/server.112/e25494/dba.htm#ADMIN10241)
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

**ORAPWD Command Line Argument Descriptions**

Platform | Required Name | Required Location)
---|---|---
UNIX and Linux | `orapwORACLE_SID` | ORACLE_HOME`/dbs`
Windows | `PWDORACLE_SID.ora` | ORACLE_HOME`\database`

### Create the Oracle Password
1. Log on to the database as an administrative user.
2. Shutdown the database.
3. On Linux/UNIX: `cd $ORACLE_HOME/dbs`, Windows: `cd %ORACLE_HOME%\database`
4. Issue the `orapwd` command:
    ```
    $ orapwd
    Usage: orapwd file=[fname] password=[sys password] entries=[users] force=[y/n] ignorecase=[y/n] nosysdba=[y/n]
    $ orapwd file=orapwTEST01 password=geeklab ignorecase=n
    ```
    - `file` – name of password file (required)
    - `password` – password for SYS will be prompted if not specified at command line
    - `entries` – maximum number of distinct DBA (optional)
    - `force` – whether to overwrite existing file (optional)
    - `ignorecase` – passwords are case-insensitive (optional)
    - `nosysdba` – whether to shut out the SYSDBA logon (optional Database Vault only, deprecated in 11.2)
5. Add the below parameter in the pfile of the `TEST01` database instance:
    ```
    REMOTE_LOGIN_PASSWORDFILE=exclusive
    ```
6. After all the above has been done, run this statement:
    ```
    SQL> STARTUP;
    SQL> select * from v$pwfile_users;
    ```

Should this statement return any records, attempt a passwordfile authenticated sysdba connection. Should the connection work, everything is working fine. If the statement returns no records, the database instance must be restarted.

## Adding Users to a Password File
**Creating a Password File and Adding New Users to It**
1. Follow the instructions for creating a password file as explained in ["Creating a Password File with ORAPWD"](#creating-a-password-file-with-orapwd).
1. Set the `REMOTE_LOGIN_PASSWORDFILE` initialization parameter to `EXCLUSIVE`. (This is the default.)
    >Note:
    `REMOTE_LOGIN_PASSWORDFILE` is a static initialization parameter and therefore cannot be changed without restarting the database.
1. Connect with `SYSDBA` privileges as shown in the following example, and enter the `SYS` password when prompted:
    ```
    CONNECT SYS AS SYSDBA
    ```
1. Start up the instance and create the database if necessary, or mount and open an existing database.
1. Create users as necessary. Grant `SYSDBA` or `SYSOPER` privileges to yourself and other users as appropriate. See "Granting and Revoking SYSDBA and SYSOPER Privileges", later in this section.

## Maintaining a Password File
**Expanding the Number of Password File Users**
1. Identify the users who have `SYSDBA` or `SYSOPER` privileges by querying the `V$PWFILE_USERS` view.
1. Delete the existing password file.
1. Follow the instructions for creating a new password file using the `ORAPWD` utility in ["Creating a Password File with ORAPWD"](#creating-a-password-file-with-orapwd). Ensure that the `ENTRIES` parameter is set to a number larger than you think you will ever need.
1. Follow the instructions in ["Adding Users to a Password File"](#adding-users-to-a-password-file).

**Removing a Password File**

If you determine that you no longer require a password file to authenticate users, you can delete the password file and then optionally reset the `REMOTE_LOGIN_PASSWORDFILE` initialization parameter to `NONE`. After you remove this file, only those users who can be authenticated by the operating system can perform `SYSDBA` or `SYSOPER` database administration operations.

## References
- [ORAPWD example](https://www.krenger.ch/blog/orapwd-example/)
- [Creating and Maintaining a Password File](https://docs.oracle.com/cd/E11882_01/server.112/e25494/dba.htm#ADMIN10241)
- [How to Create the Oracle Password File using orapwd Command](https://www.thegeekdiary.com/how-to-create-the-oracle-password-file-using-orapwd-command/)
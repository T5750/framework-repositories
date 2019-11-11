## MySQL mysqld_safe

**mysqld_safe** reads all options from the `[mysqld]`, `[server]`, and `[mysqld_safe]` sections in option files. For example, if you specify a `[mysqld]` section like this, **mysqld_safe** will find and use the `--log-error` option:
```
[mysqld]
log-error=error.log
```

### mysqld_safe Options

Option | Name Description
---|---
--basedir | Path to MySQL installation directory
--core-file-size | Size of core file that mysqld should be able to create
--datadir | Path to data directory
--defaults-extra-file | Read named option file in addition to usual option files
--defaults-file | Read only named option file
--help | Display help message and exit
--ledir | Path to directory where server is located
--log-error | Write error log to named file
--malloc-lib | Alternative malloc library to use for mysqld
--mysqld | Name of server program to start (in ledir directory)
--mysqld-version | Suffix for server program name
--nice | Use nice program to set server scheduling priority
--no-defaults | Read no option files
--open-files-limit | Number of files that mysqld should be able to open
--pid-file | Path name of server process ID file
--plugin-dir | Directory where plugins are installed
--port | Port number on which to listen for TCP/IP connections
--skip-kill-mysqld | Do not try to kill stray mysqld processes
--skip-syslog | Do not write error messages to syslog; use error log file
--socket | Socket file on which to listen for Unix socket connections
--syslog | Write error messages to syslog
--syslog-tag | Tag suffix for messages written to syslog
--timezone | Set TZ time zone environment variable to named value
--user | Run mysqld as user having name user_name or numeric user ID user_id

If you execute **mysqld_safe** with the `--defaults-file` or `--defaults-extra-file` option to name an option file, the option must be the first one given on the command line or the option file will not be used. For example, this command will not use the named option file:
```
mysql> mysqld_safe --port=port_num --defaults-file=file_name
```
Instead, use the following command:
```
mysql> mysqld_safe --defaults-file=file_name --port=port_num
```

### References
- [4.3.2 mysqld_safe — MySQL Server Startup Script](https://dev.mysql.com/doc/refman/5.6/en/mysqld-safe.html)
## MySQL mysqlslap

**mysqlslap** is a diagnostic program designed to emulate client load for a MySQL server and to report the timing of each stage. It works as if multiple clients are accessing the server.

Invoke **mysqlslap** like this:
```
shell> mysqlslap [options]
```
**mysqlslap** runs in three stages:
1. Create schema, table, and optionally any stored programs or data to use for the test. This stage uses a single client connection.
2. Run the load test. This stage can use many client connections.
3. Clean up (disconnect, drop table if specified). This stage uses a single client connection.

Supply your own create and query SQL statements, with 50 clients querying and 200 selects for each (enter the command on a single line):
```
mysqlslap --delimiter=";" --create="CREATE TABLE a (b int);INSERT INTO a VALUES (23)" --query="SELECT * FROM a" --concurrency=50 --iterations=200 -uroot -p123456
```
Let **mysqlslap** build the query SQL statement with a table of two `INT` columns and three `VARCHAR` columns. Use five clients querying 20 times each. Do not create the table or insert the data (that is, use the previous test's schema and data):
```
mysqlslap --concurrency=5 --iterations=20 --number-int-cols=2 --number-char-cols=3 --auto-generate-sql -uroot -p123456
```
Tell the program to load the create, insert, and query SQL statements from the specified files, where the `create.sql` file has multiple table creation statements delimited by `';'` and multiple insert statements delimited by `';'`. The `--query` file will have multiple queries delimited by `';'`. Run all the load statements, then run all the queries in the query file with five clients (five times each):
```
mysqlslap --concurrency=5 --iterations=5 --query=query.sql --create=create.sql --delimiter=";" -uroot -p123456
```

### mysqlslap Options

Option Name | Description | Introduced
---|---|---
--auto-generate-sql | Generate SQL statements automatically when they are not supplied in files or using command options | 
--auto-generate-sql-add-autoincrement | Add AUTO_INCREMENT column to automatically generated tables | 
--auto-generate-sql-execute-number | Specify how many queries to generate automatically | 
--auto-generate-sql-guid-primary | Add a GUID-based primary key to automatically generated tables | 
--auto-generate-sql-load-type | Specify the test load type | 
--auto-generate-sql-secondary-indexes | Specify how many secondary indexes to add to automatically generated tables | 
--auto-generate-sql-unique-query-number | How many different queries to generate for automatic tests. | 
--auto-generate-sql-unique-write-number | How many different queries to generate for --auto-generate-sql-write-number | 
--auto-generate-sql-write-number | How many row inserts to perform on each thread | 
--commit | How many statements to execute before committing. | 
--compress | Compress all information sent between client and server | 
--concurrency | Number of clients to simulate when issuing the SELECT statement | 
--create | File or string containing the statement to use for creating the table | 
--create-schema | Schema in which to run the tests | 
--csv | Generate output in comma-separated values format | 
--debug | Write debugging log | 
--debug-check | Print debugging information when program exits | 
--debug-info | Print debugging information, memory, and CPU statistics when program exits | 
--default-auth | Authentication plugin to use | 5.6.2
--defaults-extra-file | Read named option file in addition to usual option files | 
--defaults-file | Read only named option file | 
--defaults-group-suffix | Option group suffix value | 
--delimiter | Delimiter to use in SQL statements | 
--detach | Detach (close and reopen) each connection after each N statements | 
--enable-cleartext-plugin | Enable cleartext authentication plugin | 5.6.7
--engine | Storage engine to use for creating the table | 
--help | Display help message and exit | 
--host | Host on which MySQL server is located | 
--iterations | Number of times to run the tests | 
--login-path | Read login path options from .mylogin.cnf | 5.6.6
--no-defaults | Read no option files | 
--no-drop | Do not drop any schema created during the test run | 5.6.3
--number-char-cols | Number of VARCHAR columns to use if --auto-generate-sql is specified | 
--number-int-cols | Number of INT columns to use if --auto-generate-sql is specified | 
--number-of-queries | Limit each client to approximately this number of queries | 
--only-print | Do not connect to databases. mysqlslap only prints what it would have done | 
--password | Password to use when connecting to server | 
--pipe | Connect to server using named pipe (Windows only) | 
--plugin-dir | Directory where plugins are installed | 5.6.2
--port | TCP/IP port number for connection | 
--post-query | File or string containing the statement to execute after the tests have completed | 
--post-system | String to execute using system() after the tests have completed | 
--pre-query | File or string containing the statement to execute before running the tests | 
--pre-system | String to execute using system() before running the tests | 
--print-defaults | Print default options | 
--protocol | Connection protocol to use | 
--query | File or string containing the SELECT statement to use for retrieving data | 
--secure-auth | Do not send passwords to server in old (pre-4.1) format | 5.6.17
--shared-memory-base-name | Name of shared memory to use for shared-memory connections | 
--silent | Silent mode | 
--socket | Unix socket file or Windows named pipe to use | 
--ssl | Enable connection encryption | 
--ssl-ca | File that contains list of trusted SSL Certificate Authorities | 
--ssl-capath | Directory that contains trusted SSL Certificate Authority certificate files | 
--ssl-cert | File that contains X.509 certificate | 
--ssl-cipher | Permissible ciphers for connection encryption | 
--ssl-crl | File that contains certificate revocation lists | 5.6.3
--ssl-crlpath | Directory that contains certificate revocation-list files | 5.6.3
--ssl-key | File that contains X.509 key | 
--ssl-mode | Desired security state of connection to server | 5.6.30
--ssl-verify-server-cert | Verify host name against server certificate Common Name identity | 
--user | MySQL user name to use when connecting to server | 
--verbose | Verbose mode | 
--version | Display version information and exit | 

### References
- [4.5.7 mysqlslap — Load Emulation Client](https://dev.mysql.com/doc/refman/5.6/en/mysqlslap.html)
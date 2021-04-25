# Cassandra Cqlsh

Cassandra Query Language (CQL)

Options | Usage
---|---
cqlsh --help | Shows help topics about the options of cqlsh commands.
cqlsh --version | Provides the version of the cqlsh you are using.
cqlsh --color | Directs the shell to use colored output.
cqlsh --debug | Shows additional debugging information.
cqlsh --execute cql_statement | Directs the shell to accept and execute a CQL command.
cqlsh --file= “file name” | If you use this option, Cassandra executes the command in the given file and exits.
cqlsh --no-color | Directs Cassandra not to use colored output.
cqlsh -u “user name” | Using this option, you can authenticate a user. The default user name is: cassandra.
cqlsh -p “pass word” | Using this option, you can authenticate a user with a password. The default password is: cassandra.

## Documented Shell Commands
Given below are the Cqlsh documented shell commands. These are the commands used to perform tasks such as displaying help topics, exit from cqlsh, describe,etc.
- `HELP` − Displays help topics for all cqlsh commands.
- `CAPTURE` − Captures the output of a command and adds it to a file.
- `CONSISTENCY` − Shows the current consistency level, or sets a new consistency level.
- `COPY` − Copies data to and from Cassandra.
- `DESCRIBE` − Describes the current cluster of Cassandra and its objects.
- `EXPAND` − Expands the output of a query vertically.
- `EXIT` − Using this command, you can terminate cqlsh.
- `PAGING` − Enables or disables query paging.
- `SHOW` − Displays the details of current cqlsh session such as Cassandra version, host, or data type assumptions.
- `SOURCE` − Executes a file that contains CQL statements.
- `TRACING` − Enables or disables request tracing.

## CQL Data Definition Commands
- `CREATE KEYSPACE` − Creates a KeySpace in Cassandra.
- `USE` − Connects to a created KeySpace.
- `ALTER KEYSPACE` − Changes the properties of a KeySpace.
- `DROP KEYSPACE` − Removes a KeySpace
- `CREATE TABLE` − Creates a table in a KeySpace.
- `ALTER TABLE` − Modifies the column properties of a table.
- `DROP TABLE` − Removes a table.
- `TRUNCATE` − Removes all the data from a table.
- `CREATE INDEX` − Defines a new index on a single column of a table.
- `DROP INDEX` − Deletes a named index.

## CQL Data Manipulation Commands
- `INSERT` − Adds columns for a row in a table.
- `UPDATE` − Updates a column of a row.
- `DELETE` − Deletes data from a table.
- `BATCH` − Executes multiple DML statements at once.

## CQL Clauses
- `SELECT` − This clause reads data from a table
- `WHERE` − The where clause is used along with select to read a specific data.
- `ORDERBY` − The orderby clause is used along with select to read a specific data in a specific order.

## References
- [Cassandra - Cqlsh](https://www.tutorialspoint.com/cassandra/cassandra_cqlsh.htm)
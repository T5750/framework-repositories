# Command Reference

- `hadoop fs`
- `hadoop fs -help`

```
"<path>" means any file or directory name.
"<path>..." means one or more file or directory names.
"<file>" means any filename.
"<src>" and "<dest>" are path names in a directed operation.
"<localSrc>" and "<localDest>" are paths as above, but on the local file system.
```

Command | Description
---|------
-ls &lt;path&gt; | Lists the contents of the directory specified by path, showing the names, permissions, owner, size and modification date for each entry.
-lsr &lt;path&gt; | Behaves like -ls, but recursively displays entries in all subdirectories of path.
-du &lt;path&gt; | Shows disk usage, in bytes, for all the files which match path; filenames are reported with the full HDFS protocol prefix.
-dus &lt;path&gt; | Like -du, but prints a summary of disk usage of all files/directories in the path.
-mv &lt;src&gt;&lt;dest&gt; | Moves the file or directory indicated by src to dest, within HDFS.
-cp &lt;src&gt; &lt;dest&gt; | Copies the file or directory identified by src to dest, within HDFS.
-rm &lt;path&gt; | Removes the file or empty directory identified by path.
-rmr &lt;path&gt; | Removes the file or directory identified by path. Recursively deletes any child entries (i.e., files or subdirectories of path).
-put &lt;localSrc&gt; &lt;dest&gt; | Copies the file or directory from the local file system identified by localSrc to dest within the DFS.
-copyFromLocal &lt;localSrc&gt; &lt;dest&gt; | Identical to -put
-moveFromLocal &lt;localSrc&gt; &lt;dest&gt; | Copies the file or directory from the local file system identified by localSrc to dest within HDFS, and then deletes the local copy on success.
-get [-crc] &lt;src&gt; &lt;localDest&gt; | Copies the file or directory in HDFS identified by src to the local file system path identified by localDest.
-getmerge &lt;src&gt; &lt;localDest&gt; | Retrieves all files that match the path src in HDFS, and copies them to a single, merged file in the local file system identified by localDest.
-cat &lt;filen-ame&gt; | Displays the contents of filename on stdout.
-copyToLocal &lt;src&gt; &lt;localDest&gt; | Identical to -get
-moveToLocal &lt;src&gt; &lt;localDest&gt; | Works like -get, but deletes the HDFS copy on success.
-mkdir &lt;path&gt; | Creates a directory named path in HDFS. Creates any parent directories in path that are missing (e.g., mkdir -p in Linux).
-setrep [-R] [-w] rep &lt;path&gt; | Sets the target replication factor for files identified by path to rep. (The actual replication factor will move toward the target over time)
-touchz &lt;path&gt; | Creates a file at path containing the current time as a timestamp. Fails if a file already exists at path, unless the file is already size 0.
-test -[ezd] &lt;path&gt; | Returns 1 if path exists; has zero length; or is a directory or 0 otherwise.
-stat [format] &lt;path&gt; | Prints information about path. Format is a string which accepts file size in blocks (%b), filename (%n), block size (%o), replication (%r), and modification date (%y, %Y).
-tail [-f] &lt;file2name&gt; | Shows the last 1KB of file on stdout.
-chmod [-R] mode,mode,... &lt;path&gt;... | Changes the file permissions associated with one or more objects identified by path.... Performs changes recursively with R. mode is a 3-digit octal mode, or {augo}+/-{rwxX}. Assumes if no scope is specified and does not apply an umask.
-chown [-R] [owner][:[group]] &lt;path&gt;... | Sets the owning user and/or group for files or directories identified by path.... Sets owner recursively if -R is specified.
-chgrp [-R] group &lt;path&gt;... | Sets the owning group for files or directories identified by path.... Sets group recursively if -R is specified.
-help &lt;cmd-name&gt; | Returns usage information for one of the commands listed above. You must omit the leading '-' character in cmd.

## References
- [Hadoop - Command Reference](https://www.tutorialspoint.com/hadoop/hadoop_command_reference.htm)
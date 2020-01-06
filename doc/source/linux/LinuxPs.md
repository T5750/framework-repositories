# Linux ps command

The ps command on linux is one of the most basic commands for viewing the processes running on the system. It provides a snapshot of the current processes along with detailed information like user id, cpu usage, memory usage, command name etc.

## Note on syntax
>Note : `ps aux` is not the same as `ps -aux`. For example `-u` is used to show process of that user. But `u` means show detailed information.

- BSD style: `ps aux`
- UNIX/LINUX style: `ps -ef`

## How to use ps command
### 1. Display all processes
```
$ ps ax
$ ps -ef
```
Use the `u` option or `-f` option to display detailed information about the processes
```
$ ps aux
$ ps -ef -f
```

### 2. Display process by user
To filter the processes by the owning user use the `-u` option followed by the username. Multiple usernames can be provided separated by a comma.
```
$ ps -f -u www-data
UID        PID  PPID  C STIME TTY          TIME CMD
www-data  1329  1328  0 09:32 ?        00:00:00 nginx: worker process
www-data  1330  1328  0 09:32 ?        00:00:00 nginx: worker process
www-data  1332  1328  0 09:32 ?        00:00:00 nginx: worker process
```

### 3. Show process by name or process id
To search the processes by their name or command use the `-C` option followed by the search term.
```
$ ps -C apache2
  PID TTY          TIME CMD
 2359 ?        00:00:00 apache2
 4524 ?        00:00:00 apache2
```
To display processes by process id, use the `-p` option and provides the process ids separated by comma.
```
$ ps -f -p 3150,7298,6544
```

### 4. Sort process by cpu or memory usage
```
$ ps aux --sort=-pcpu,+pmem
```
Display the top 5 processes consuming most of the cpu.
```
$ ps aux --sort=-pcpu | head -5
```

### 5. Display process hierarchy in a tree style
```
$ ps -f --forest -C apache2
UID        PID  PPID  C STIME TTY          TIME CMD
root      2359     1  0 09:32 ?        00:00:00 /usr/sbin/apache2 -k start
www-data  4524  2359  0 10:03 ?        00:00:00  \_ /usr/sbin/apache2 -k start
www-data  4525  2359  0 10:03 ?        00:00:00  \_ /usr/sbin/apache2 -k start
```

### 6. Display child processes of a parent process
```
$ ps -o pid,uname,comm -C apache2
  PID USER     COMMAND
 2359 root     apache2
 4524 www-data apache2
 4525 www-data apache2
```
The first process that is owned by root is the main apache2 process and all other apache2 processes have been forked out of this main process. The next command lists all child apache2 processes using the pid of the main apache2 process
```
$ ps --ppid 2359
  PID TTY          TIME CMD
 4524 ?        00:00:00 apache2
 4525 ?        00:00:00 apache2
```

### 7. Display threads of a process
The `-L` option will display the threads along with the processes. It can be used to display all threads of a particular process or all processes.
```
$ ps -p 3150 -L
```

### 8. Change the columns to display
```
$ ps -e -o pid,uname,pcpu,pmem,comm
```
It is possible to rename the column labels
```
$ ps -e -o pid,uname=USERNAME,pcpu=CPU_USAGE,pmem,comm
```

### 9. Display elapsed time of processes
The elapsed time indicates, how long the process has been running for. The column for elapsed time is not shown by default, and has to be brought in using the `-o` option
```
$ ps -e -o pid,comm,etime
```

### 10. Turn ps into an realtime process viewer
As usual, the watch command can be used to turn ps into a realtime process reporter.
```
$ watch -n 1 'ps -e -o pid,uname,cmd,pmem,pcpu --sort=-pmem,-pcpu | head -15'
```

## References
- [10 basic examples of Linux ps command](https://www.binarytides.com/linux-ps-command/)
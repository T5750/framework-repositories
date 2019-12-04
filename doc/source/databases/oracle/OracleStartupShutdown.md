# Oracle Startup and Shutdown

1. As the oracle user, edit the `/etc/oratab` file and add a line for every instance on this particular server in the format `SID:ORACLE_HOME:{Y|N|W}`, in my example for the instance kdb1:
    ```
    $ vim /etc/oratab
    kdb1:/u01/app/oracle/product/10.2.0/db_1:Y
    ```
2. Then, let’s create **the actual init.d script** for our database. Note that you need to adjust the `ORACLE_HOME` variable and the `ORACLE_OWNER` variable for the script to work properly. Also, the chkconfig line contains information on when to start or stop the instances, which you might have to change as well.
    ```
    $ sudo vim /etc/init.d/oracle
    #!/bin/bash
    # chkconfig: 35 99 10
    # description: Starts and stops Oracle processes
    ORACLE_HOME=/u01/app/oracle/product/11.1.0/db_1
    ORACLE_OWNER=oracle
    PATH=${PATH}:$ORACLE_HOME/bin
    HOST=`hostname`
    PLATFORM=`uname`
    export ORACLE_HOME PATH
    case $1 in
    'start')
            su - $ORACLE_OWNER -c "$ORACLE_HOME/bin/lsnrctl start"
        su - $ORACLE_OWNER -c "$ORACLE_HOME/bin/dbstart"
            ;;
    'stop')
            su - $ORACLE_OWNER -c "$ORACLE_HOME/bin/lsnrctl stop"
        su - $ORACLE_OWNER -c "$ORACLE_HOME/bin/dbshut"
            ;;
    *)
            echo "usage: $0 {start|stop}"
            exit 1
    esac
    #
    exit
    ```
3. At last, **add the newly created init script to the runlevel configuration**:
    ```
    $ sudo chmod +x /etc/init.d/oracle
    $ sudo chkconfig --add oracle
    ```
    You can then check if the service was successfully added by running
    ```
    $ sudo chkconfig --list
    ```

## References
- [Automating Database Startup and Shutdown on Red Hat](https://www.krenger.ch/blog/automating-database-startup-and-shutdown-on-red-hat/)
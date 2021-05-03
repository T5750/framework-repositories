#! /bin/sh
#
# chkconfig: - 55 45
# description:  Twitter's twemproxy nutcracker
# processname: nutcracker
# config: /etc/sysconfig/nutcracker

# Source function library.
#. /etc/rc.d/init.d/functions

USER="nobody"
#OPTIONS="-d -c /etc/nutcracker/nutcracker.yml"
OPTIONS="-d -c /usr/servers/templates/nutcracker.yml -o /usr/servers/templates/redisproxy.log"

if [ -f /etc/sysconfig/nutcracker ];then
    . /etc/sysconfig/nutcracker
fi

# Check that networking is up.
if [ "$NETWORKING" = "no" ]
then
    exit 0
fi

RETVAL=0
prog="nutcracker"

start () {
    echo -n $"Starting $prog: "
    #Test the config before start.
    #daemon --user ${USER} ${prog} $OPTIONS -t >/dev/null 2>&1
    /usr/servers/twemproxy-0.4.1/src/nutcracker $OPTIONS -t >/dev/null 2>&1
    RETVAL=$?
    if [ $RETVAL -ne 0 ] ; then
        echo "Config check fail! Please use 'nutcracker -c /etc/nutcracker/nutcracker.yml' for detail."
        #echo_failure;
        echo;
        exit 1
    fi
    #daemon --user ${USER} ${prog} $OPTIONS
    /usr/servers/twemproxy-0.4.1/src/nutcracker $OPTIONS
    RETVAL=$?
    echo
    [ $RETVAL -eq 0 ] && touch /var/lock/subsys/${prog}
}
stop () {
    echo -n $"Stopping $prog: "
    #killproc ${prog}
    killall ${prog}
    RETVAL=$?
    echo
    if [ $RETVAL -eq 0 ] ; then
        rm -f /var/lock/subsys/${prog}
    fi
}
restart () {
    stop
    start
}
# See how we were called.
case "$1" in
  start)
    start
    ;;
  stop)
    stop
    ;;
  status)
    status ${prog}
    ;;
  restart|reload)
    restart
    ;;
  condrestart)
    [ -f /var/lock/subsys/nutcracker ] && restart || :
    ;;
  *)
    echo $"Usage: $0 {start|stop|status|restart|reload|condrestart}"
    exit 1
esac

exit $?
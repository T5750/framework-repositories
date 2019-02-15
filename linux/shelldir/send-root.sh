#!/bin/bash
/bin/date +%F >> /home/shelldir/send-root.info
echo >> /home/shelldir/send-root.info
echo "Disk info: " >> /home/shelldir/send-root.info
/bin/df -h >> /home/shelldir/send-root.info
echo >> /home/shelldir/send-root.info
echo "Online users: " >> /home/shelldir/send-root.info
echo >> /home/shelldir/send-root.info
/usr/bin/who | /bin/grep -v root >> /home/shelldir/send-root.info
echo "Memory info: " >> /home/shelldir/send-root.info
/usr/bin/free -m >> /home/shelldir/send-root.info
echo >> /home/shelldir/send-root.info
/usr/bin/write root < /home/shelldir/send-root.info && /bin/rm /home/shelldir/send-root.info

# Linux Cron Scheduling Task

## Cron Syntax
```
A B C D E USERNAME /path/to/command arg1 arg2
OR
A B C D E USERNAME /root/backup.sh
```
- `A: Minutes` range: `0 – 59`
- `B: Hours` range: `0 – 23`
- `C: Days` range: `0 – 31`
- `D: Months` range: `0 – 12`
- `E: Days of the week` range: `0 – 7`. Starting from Monday, 0 or 7 represents Sunday
- `USERNAME`: replace this with your username
- `/path/to/command` – The name of the script or command you want to schedule

## 11 Cron Scheduling Task Examples
### 1. List Crontab Entries
```
# crontab -l
```

### 2. Edit Crontab Entries
```
# crontab -e
```

### 3. List Scheduled Cron Jobs
```
# crontab -u root -l
```

### 4. Remove Crontab Entry
```
# crontab -r
```

### 5. Prompt Before Deleting Crontab
```
# crontab -i -r
```

### 6. Allowed special character (*, -, /, ?, #)
- `Asterik(*)` – Match all values in the field or any possible value.
- `Hyphen(-)` – To define range.
- `Slash (/)` – 1st field /10 meaning every ten minute or increment of range.
- `Comma (,)` – To separate items.

### 7. System Wide Cron Schedule
- /etc/cron.d
- /etc/cron.daily
- /etc/cron.hourly
- /etc/cron.monthly
- /etc/cron.weekly

### 8. Schedule a Jobs for Specific Time
```
# crontab -e
30 0 * * * root find /tmp -type f -empty -delete
```

### 9. Special Strings for Common Schedule

Strings | Meanings
---|-----
`@reboot` | Command will run when the system reboot.
`@daily` | Once per day or may use `@midnight`.
`@weekly` | Once per week.
`@yearly` | Once per year. we can use `@annually` keyword also.

### 10. Multiple Commands with Double amper-sand(&&)
```
# crontab -e
@daily <command1> && <command2>
```

### 11. Disable Email Notification
```
# crontab -e
* * * * * >/dev/null 2>&1
```

## Cron Job Examples
Run `/root/backup.sh` at 3 am every day:
```
0 3 * * * /root/backup.sh
```
Run `script.sh` at 4:30 pm on the second of every month:
```
30 16 2 * * /path/to/script.sh
```
Run `/scripts/phpscript.php` at 10 pm during the week:
```
0 22 * * 1-5 /scripts/phpscript.php
```
Run `perlscript.pl` at 23 minutes after midnight, 2am and 4am, everyday:
```
23 0-23/2 * * * /path/to/perlscript.pl
```
Run Linux command at 04:05 every Sunday:
```
5 4 * * sun /path/to/linuxcommand
```

## Cron Options
Delete Cron job for a specific user.
```
# crontab -r -u username
```

## Strings in Crontab
- `@hourly`: Run once every hour i.e. “0 * * * *“
- `@midnight`: Run once every day i.e. “0 0 * * *“
- `@daily`: same as midnight
- `@weekly`: Run once every week, i.e. “0 0 * * 0“
- `@monthly`: Run once every month i.e. “0 0 1 * *“
- `@annually`: Run once every year i.e. “0 0 1 1 *“
- `@yearly`: same as `@annually`
- `@reboot`: Run once at every startup

## References
- [11 Cron Scheduling Task Examples in Linux](https://www.tecmint.com/11-cron-scheduling-task-examples-in-linux/)
- [How to Create and Manage Cron Jobs on Linux](https://www.tecmint.com/create-and-manage-cron-jobs-on-linux/)
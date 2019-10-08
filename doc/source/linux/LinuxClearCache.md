# Linux Clear Cache

## How to Clear Cache in Linux?
1. Clear PageCache only.
```
# sync; echo 1 > /proc/sys/vm/drop_caches
```
2. Clear dentries and inodes.
```
# sync; echo 2 > /proc/sys/vm/drop_caches
```
3. Clear PageCache, dentries and inodes.
```
# sync; echo 3 > /proc/sys/vm/drop_caches 
```
`sync` will flush the file system buffer. Command Separated by `“;”` run sequentially.

Now we will be creating a shell script to auto clear RAM cache daily at 2am via a cron scheduler task. Create a shell script `clearcache.sh` and add the following lines.
```
#!/bin/bash
# Note, we are using "echo 3", but it is not recommended in production instead use "echo 1"
echo "echo 3 > /proc/sys/vm/drop_caches"
```
Set execute permission on the clearcache.sh file.
```
# chmod 755 clearcache.sh
```
Now set a cron to clear RAM cache everyday at 2am. Open `crontab` for editing.
```
# crontab -e
```
Append the below line, save and exit to run it at 2am daily.
```
0 2 * * * /path/to/clearcache.sh
```

## How to Clear Swap Space in Linux?
```
# swapoff -a && swapon -a
```
Now we will be combining both above commands into one single command to make a proper script to clear RAM Cache and Swap Space.
```
# echo 3 > /proc/sys/vm/drop_caches && swapoff -a && swapon -a && printf '\n%s\n' 'Ram-cache and Swap Cleared'
OR
$ su -c "echo 3 >'/proc/sys/vm/drop_caches' && swapoff -a && swapon -a && printf '\n%s\n' 'Ram-cache and Swap Cleared'" root
```

## Tips
- `dropcache.sh`

## References
- [How to Clear RAM Memory Cache, Buffer and Swap Space on Linux](https://www.tecmint.com/clear-ram-memory-cache-buffer-and-swap-space-on-linux/)
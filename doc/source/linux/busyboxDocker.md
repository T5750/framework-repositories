# BusyBox Docker

Coming in somewhere between 1 and 5 Mb in on-disk size (depending on the variant), [BusyBox](http://www.busybox.net/) is a very good ingredient to craft space-efficient distributions.

BusyBox combines tiny versions of many common UNIX utilities into a single small executable. It provides replacements for most of the utilities you usually find in GNU fileutils, shellutils, etc. The utilities in BusyBox generally have fewer options than their full-featured GNU cousins; however, the options that are included provide the expected functionality and behave very much like their GNU counterparts. BusyBox provides a fairly complete environment for any small or embedded system.

## Docker
```
docker run -it --rm busybox:stable
```

## Tests
```
grep
telnet
mount
```

## References
- [busybox Docker](https://hub.docker.com/_/busybox)
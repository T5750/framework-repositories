# Docker Note

## Run vi Editor
### For Ubuntu/Debian
```
docker exec -it ubuntu bash -c "apt-get update && apt-get install -y vim"
```

### For CentOS/Fedora
```
docker exec -it centos bash -c "yum update -y && yum install -y vim"
```

## ifconfig
### For Ubuntu/Debian
```
apt-get install net-tools
```

### For CentOS/Fedora
```
yum install -y net-tools
```

## ps
### For Ubuntu/Debian
```
apt-get install procps
```

### For CentOS/Fedora
```
yum install -y procps
```

## ping
### For Ubuntu/Debian
```
apt-get install inetutils-ping
```

### For CentOS/Fedora
```
yum install -y iputils
```

## curl
### For Ubuntu/Debian
```
apt-get install curl
```

### For CentOS/Fedora
```
yum install -y curl
```

## References
- [Run vi Editor Inside Docker Container](https://www.baeldung.com/linux/vi-editor-inside-docker-container)
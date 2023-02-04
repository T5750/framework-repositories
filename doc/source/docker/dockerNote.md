# Docker Note

## Run vi Editor
### For Ubuntu/Debian
```sh
docker exec -it ubuntu bash -c "apt-get update && apt-get install -y vim"
```

### For CentOS/Fedora
```sh
docker exec -it centos bash -c "yum update -y && yum install -y vim"
```

## `ifconfig`/`netstat`
### For Ubuntu/Debian
```sh
apt-get install net-tools
```

### For CentOS/Fedora
```sh
yum install -y net-tools
```

## `ps`
### For Ubuntu/Debian
```sh
apt-get install procps
```

### For CentOS/Fedora
```sh
yum install -y procps
```

## `ping`
### For Ubuntu/Debian
```sh
apt-get install inetutils-ping
```

### For CentOS/Fedora
```sh
yum install -y iputils
```

## `curl`
### For Ubuntu/Debian
```sh
apt-get install curl
```

### For CentOS/Fedora
```sh
yum install -y curl
```

## `dig`
### For Ubuntu/Debian
```sh
apt-get install -y dnsutils
```

### For CentOS/Fedora
```sh
yum install -y bind-utils
```

## References
- [Run vi Editor Inside Docker Container](https://www.baeldung.com/linux/vi-editor-inside-docker-container)
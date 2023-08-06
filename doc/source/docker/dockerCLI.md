# Docker CLI

## docker load
### Description
Load an image from a tar archive or STDIN

### Usage
```
docker load [OPTIONS]
```

### Examples
```sh
docker load < busybox.tar.gz
docker images
docker load --input fedora.tar
docker images
```

## docker save
### Description
Save one or more images to a tar archive (streamed to STDOUT by default)

### Usage
```
docker save [OPTIONS] IMAGE [IMAGE...]
```

### Examples
```sh
docker save busybox > busybox.tar
ls -sh busybox.tar
docker save --output busybox.tar busybox
ls -sh busybox.tar
docker save -o fedora-all.tar fedora
docker save -o fedora-latest.tar fedora:latest
```

## docker tag
### Description
Create a tag TARGET_IMAGE that refers to SOURCE_IMAGE

### Usage
```
docker tag SOURCE_IMAGE[:TAG] TARGET_IMAGE[:TAG]
```

### Examples
#### Tag an image referenced by ID
```sh
docker tag 0e5574283393 fedora/httpd:version1.0
```

#### Tag an image referenced by Name
```sh
docker tag httpd fedora/httpd:version1.0
```

#### Tag an image referenced by Name and Tag
```sh
docker tag httpd:test fedora/httpd:version1.0.test
```

#### Tag an image for a private repository
```sh
docker tag 0e5574283393 myregistryhost:5000/fedora/httpd:version1.0
```

## docker top
Display the running processes of a container

### Usage
```
docker top CONTAINER [ps OPTIONS]
```

## docker update
### Description
The docker update command dynamically updates container configuration

### Usage
```
docker update [OPTIONS] CONTAINER [CONTAINER...]
```

### Examples
```sh
docker update --cpu-shares 512 abebf7571666
docker update --cpu-shares 512 -m 300M abebf7571666 hopeful_morse
docker update --kernel-memory 80M test
docker update --restart=on-failure:3 abebf7571666 hopeful_morse
```

## References
- [docker save](https://docs.docker.com/engine/reference/commandline/save/)
- [docker load](https://docs.docker.com/engine/reference/commandline/load/)
- [docker tag](https://docs.docker.com/engine/reference/commandline/tag/)
- [docker top](https://docs.docker.com/engine/reference/commandline/top/)
- [docker update](https://docs.docker.com/engine/reference/commandline/update/)
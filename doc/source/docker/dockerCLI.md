# Docker CLI

## docker save
### Description
Save one or more images to a tar archive (streamed to STDOUT by default)

### Usage
```
docker save [OPTIONS] IMAGE [IMAGE...]
```

### Examples
```
docker save busybox > busybox.tar
ls -sh busybox.tar
docker save --output busybox.tar busybox
ls -sh busybox.tar
docker save -o fedora-all.tar fedora
docker save -o fedora-latest.tar fedora:latest
```

## docker load
### Description
Load an image from a tar archive or STDIN

### Usage
```
docker load [OPTIONS]
```

### Examples
```
docker load < busybox.tar.gz
docker images
docker load --input fedora.tar
docker images
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
```
docker tag 0e5574283393 fedora/httpd:version1.0
```

#### Tag an image referenced by Name
```
docker tag httpd fedora/httpd:version1.0
```

#### Tag an image referenced by Name and Tag
```
docker tag httpd:test fedora/httpd:version1.0.test
```

#### Tag an image for a private repository
```
docker tag 0e5574283393 myregistryhost:5000/fedora/httpd:version1.0
```

## References
- [docker save](https://docs.docker.com/engine/reference/commandline/save/)
- [docker load](https://docs.docker.com/engine/reference/commandline/load/)
- [docker tag](https://docs.docker.com/engine/reference/commandline/tag/)
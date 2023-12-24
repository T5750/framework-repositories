# Ubuntu VNC Docker

A Docker image to provide web VNC interface to access Ubuntu LXDE/LxQT desktop environment.

## Docker
```sh
docker run -d --name ubuntu-vnc -p 6080:80 -v /dev/shm:/dev/shm dorowu/ubuntu-desktop-lxde-vnc
```
[http://localhost:6080/](http://localhost:6080/)

### HTTP Base Authentication
```sh
docker run -p 6080:80 -e HTTP_PASSWORD=mypassword -v /dev/shm:/dev/shm dorowu/ubuntu-desktop-lxde-vnc
```

### Screen Resolution
```sh
docker run -p 6080:80 -e RESOLUTION=1920x1080 -v /dev/shm:/dev/shm dorowu/ubuntu-desktop-lxde-vnc
```

### Default Desktop User
```sh
docker run -p 6080:80 -e USER=doro -e PASSWORD=password -v /dev/shm:/dev/shm dorowu/ubuntu-desktop-lxde-vnc
```

### Deploy to a subdirectory (relative url root)
```sh
docker run -p 6080:80 -e RELATIVE_URL_ROOT=some-prefix dorowu/ubuntu-desktop-lxde-vnc
```

## Runtime Environment
- [Python 3.9.x](https://www.python.org/downloads/)

## Screenshots
![](https://raw.github.com/fcwu/docker-ubuntu-vnc-desktop/master/screenshots/lxde.png?v1)

## References
- [VNC GitHub](https://github.com/fcwu/docker-ubuntu-vnc-desktop)
# UI For Docker

A web interface for Docker, formerly known as DockerUI. This repo is not maintained

## Docker
```sh
docker run -d --name ui-for-docker -p 9000:9000 --privileged -v /var/run/docker.sock:/var/run/docker.sock uifd/ui-for-docker
```
[http://localhost:9000/](http://localhost:9000/)

## Runtime Environment
- [Go](https://golang.org/)

## Screenshots
![](https://raw.githubusercontent.com/kevana/ui-for-docker/master/containers.png)

![](https://raw.githubusercontent.com/kevana/ui-for-docker/master/container.png)

## References
- [UI For Docker GitHub](https://github.com/kevana/ui-for-docker)
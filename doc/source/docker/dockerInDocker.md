# Docker in Docker

Although running Docker inside Docker is generally not recommended, there are some legitimate use cases, such as development of Docker itself.

## Docker
```
docker run --privileged --name docker -d docker:dind
```

### Start a daemon instance
```
$ docker run --privileged --name some-docker -d \
    --network some-network --network-alias docker \
    -e DOCKER_TLS_CERTDIR=/certs \
    -v some-docker-certs-ca:/certs/ca \
    -v some-docker-certs-client:/certs/client \
    docker:dind
```

### Custom daemon flags
```
$ docker run --privileged --name some-docker -d \
    --network some-network --network-alias docker \
    -e DOCKER_TLS_CERTDIR=/certs \
    -v some-docker-certs-ca:/certs/ca \
    -v some-docker-certs-client:/certs/client \
    docker:dind --storage-driver overlay2
```

### Where to Store Data
```
$ docker run --privileged --name some-docker -v /my/own/var-lib-docker:/var/lib/docker -d docker:dind
```

## Screenshots
![](https://asciinema.org/a/378669.svg)

## References
- [Docker in Docker](https://hub.docker.com/_/docker/)
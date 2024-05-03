# Fabio Docker

Fabio is an HTTP and TCP reverse proxy that configures itself with data from Consul.

## Docker
```sh
wget https://github.com/fabiolb/fabio/blob/master/fabio.properties
docker run -d --name fabio -p 9999:9999 -p 9998:9998 -v $PWD/fabio/fabio.properties:/etc/fabio/fabio.properties fabiolb/fabio
# Consul
docker run -d -p 8500:8500 -p 8600:8600/udp --name=badger hashicorp/consul agent -server -ui -node=server-1 -bootstrap-expect=1 -client=0.0.0.0
docker run -d --name fabio -p 9999:9999 -p 9998:9998 -e 'registry_consul_addr=Consul_IP:8500' fabiolb/fabio
```
[http://localhost:9998/](http://localhost:9998/)

### Registrator
```sh
$ docker run -d \
    --name=registrator \
    --net=host \        
    --volume=/var/run/docker.sock:/tmp/docker.sock \
    gliderlabs/registrator:latest \
    consul://localhost:8500

$ docker run -d -p 80:8000 \
    -e SERVICE_8000_CHECK_HTTP=/foo/healthcheck  \
    -e SERVICE_8000_NAME=foo \
    -e SERVICE_CHECK_INTERVAL=10s \
    -e SERVICE_CHECK_TIMEOUT=5s  \
    -e SERVICE_TAGS=urlprefix-/foo \
    test/foo
```

## Runtime Environment
- [Go](https://golang.org/)

## References
- [Fabio](https://fabiolb.net/)
- [Fabio GitHub](https://github.com/fabiolb/fabio)
- [Fabio Docker](https://fabiolb.net/feature/docker/)
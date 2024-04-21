# Consul Docker

Consul is a distributed, highly available, and data center aware solution to connect and configure applications across dynamic, distributed infrastructure.

## Docker
### Configure and run a Consul server
```sh
docker run \
    -d \
    -p 8500:8500 \
    -p 8600:8600/udp \
    --name=badger \
    consul agent -server -ui -node=server-1 -bootstrap-expect=1 -client=0.0.0.0

docker run \
    -d \
    -p 8500:8500 \
    -p 8600:8600/udp \
    --name=badger \
    hashicorp/consul agent -server -ui -node=server-1 -bootstrap-expect=1 -client=0.0.0.0
```
[http://localhost:8500/](http://localhost:8500/)

Discover the server IP address
```sh
docker exec badger consul members
```

### Configure and run a Consul client
```sh
docker run \
   --name=fox \
   consul agent -node=client-1 -retry-join=172.17.0.2

docker run \
   --name=fox \
   hashicorp/consul agent -node=client-1 -retry-join=172.17.0.2
```

### Register a service
```sh
docker run \
   -p 9001:9001 \
   -d \
   --name=weasel \
   hashicorp/counting-service:0.0.2
```
[http://localhost:9001/](http://localhost:9001/)

```sh
docker exec fox /bin/sh -c "echo '{\"service\": {\"name\": \"counting\", \"tags\": [\"go\"], \"port\": 9001}}' >> /consul/config/counting.json"
docker exec fox consul reload
```

## Runtime Environment
- [Go](https://golang.org/)

## Architecture
![](https://developer.hashicorp.com/_next/image?url=https%3A%2F%2Fcontent.hashicorp.com%2Fapi%2Fassets%3Fproduct%3Dconsul%26version%3Drefs%252Fheads%252Frelease%252F1.18.x%26asset%3Dwebsite%252Fpublic%252Fimg%252Fconsul-arch%252Fconsul-arch-overview-control-plane.svg%26width%3D960%26height%3D540&w=1920&q=75)

## Screenshots
![](https://developer.hashicorp.com/_next/image?url=https%3A%2F%2Fcontent.hashicorp.com%2Fapi%2Fassets%3Fproduct%3Dtutorials%26version%3Dmain%26asset%3Dpublic%252Fimg%252Fconsul-containers-ui-services.png%26width%3D1020%26height%3D711&w=2048&q=75)

## References
- [Consul](https://www.consul.io/)
- [Consul GitHub](https://github.com/hashicorp/consul)
- [Consul with containers](https://developer.hashicorp.com/consul/tutorials/docker/docker-container-agents)
- [Secure Consul with Docker Compose](https://developer.hashicorp.com/consul/tutorials/docker/docker-compose-datacenter)
- [Consul Architecture](https://developer.hashicorp.com/consul/docs/architecture)
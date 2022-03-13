# Docker Swarm

## Feature highlights
- Cluster management integrated with Docker Engine
- Decentralized design
- Declarative service model
- Scaling
- Desired state reconciliation
- Multi-host networking
- Service discovery
- Load balancing
- Secure by default
- Rolling updates

## Create a swarm
```
docker swarm init
```

## Add nodes to the swarm
```
docker swarm join-token worker
docker swarm join-token manager
docker node ls
```

## Deploy a service
```
docker service create --replicas 1 --name helloworld alpine ping docker.com
docker service ls
```

## Inspect the service
```
docker service inspect --pretty helloworld
docker service inspect helloworld
docker service ps helloworld
docker ps
```

## Scale the service
```
docker service scale helloworld=5
docker service ps helloworld
docker ps
```

## Delete the service
```
docker service rm helloworld
```

## Apply rolling updates
```
docker service create \
  --replicas 3 \
  --name redis \
  --update-delay 10s \
  redis:3.0.6
docker service inspect --pretty redis
docker service update --image redis:3.0.7 redis
docker service inspect --pretty redis
docker service update redis
docker service ps redis
```

## Drain a node
```
docker service create --replicas 3 --name redis --update-delay 10s redis:3.0.6
docker node update --availability drain worker1
docker node inspect --pretty worker1
docker service ps redis
docker node update --availability active worker1
```

## Use swarm mode routing mesh
### Publish a port for a service
```
docker service create \
  --name my-web \
  --publish published=8080,target=80 \
  --replicas 2 \
  nginx
```

![](https://docs.docker.com/engine/swarm/images/ingress-routing-mesh.png)

```
docker service inspect --format="{{json .Endpoint.Spec.Ports}}" my-web
```

#### Publish a port for TCP only or UDP only
Long syntax:
```
docker service create --name dns-cache \
  --publish published=53,target=53 \
  --publish published=53,target=53,protocol=udp \
  dns-cache
```
Short syntax:
```
docker service create --name dns-cache \
  -p 53:53 \
  -p 53:53/udp \
  dns-cache
```

### Bypass the routing mesh
```
docker service create --name dns-cache \
  --publish published=53,target=53,protocol=udp,mode=host \
  --mode global \
  dns-cache
```

### Configure an external load balancer
![](https://docs.docker.com/engine/swarm/images/ingress-lb.png)

`/etc/haproxy/haproxy.cfg`
```
global
        log /dev/log    local0
        log /dev/log    local1 notice
...snip...

# Configure HAProxy to listen on port 80
frontend http_front
   bind *:80
   stats uri /haproxy?stats
   default_backend http_back

# Configure HAProxy to route requests to swarm nodes on port 8080
backend http_back
   balance roundrobin
   server node1 192.168.99.100:8080 check
   server node2 192.168.99.101:8080 check
   server node3 192.168.99.102:8080 check
```

## How nodes work
![](https://docs.docker.com/engine/swarm/images/swarm-diagram.png)

### Change roles
`docker node promote`

## How services work

### Services, tasks, and containers
![](https://docs.docker.com/engine/swarm/images/services-diagram.png)

### Tasks and scheduling
![](https://docs.docker.com/engine/swarm/images/service-lifecycle.png)

### Replicated and global services
![](https://docs.docker.com/engine/swarm/images/replicated-vs-global.png)

## References
- [Swarm mode overview](https://docs.docker.com/engine/swarm/)
- [Create a swarm](https://docs.docker.com/engine/swarm/swarm-tutorial/create-swarm/)
- [Use swarm mode routing mesh](https://docs.docker.com/engine/swarm/ingress/)
- [Run Docker Engine in swarm mode](https://docs.docker.com/engine/swarm/swarm-mode/)
- [How services work](https://docs.docker.com/engine/swarm/how-swarm-mode-works/services/)
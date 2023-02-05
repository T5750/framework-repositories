# Unbound Docker

Unbound is a validating, recursive, and caching DNS resolver.

## Docker
### Standard usage
```sh
docker run --name unbound -d -p 53:53/udp -p 53:53/tcp --restart=always mvance/unbound
```

### Serve Custom DNS Records for Local Network
The `a-records.conf` file should use the following format:
```
# A Record
  #local-data: "somecomputer.local. A 192.168.1.1"
  local-data: "laptop.local. A 192.168.1.2"

# PTR Record
  #local-data-ptr: "192.168.1.1 somecomputer.local."
  local-data-ptr: "192.168.1.2 laptop.local."
```

```sh
docker run --name my-unbound -d -p 53:53/udp -v \
$(pwd)/a-records.conf:/opt/unbound/etc/unbound/a-records.conf:ro \
--restart=always mvance/unbound
```
```sh
dig laptop.local.
dig laptop.local. @localhost
dig baidu.com @localhost
```

### Serve Custom DNS Records for Local Network
```sh
docker run --name=my-unbound \
--volume=/my-directory/unbound:/opt/unbound/etc/unbound/ \
--publish=53:53/tcp \
--publish=53:53/udp \
--restart=unless-stopped \
--detach=true \
mvance/unbound
```

## References
- [mvance/unbound Docker](https://hub.docker.com/r/mvance/unbound)
- [MatthewVance/unbound-docker](https://github.com/MatthewVance/unbound-docker)
- [Unbound GitHub](https://github.com/NLnetLabs/unbound)
- [Unbound](https://nlnetlabs.nl/unbound)
- [Unbound documentation](https://unbound.docs.nlnetlabs.nl/)
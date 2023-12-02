# FreeRADIUS Docker

FreeRADIUS is a high performance multi-protocol policy server, for RADIUS, DHCP and more. 

## Linux
```sh
yum install freeradius
apt-get install freeradius
```

## Docker
### Starting the server
```sh
docker run --name my-radius -d freeradius/freeradius-server
```

### Defining a local configuration
`vi Dockerfile`
```
FROM freeradius/freeradius-server:latest
COPY raddb/ /etc/raddb/
```

```sh
mkdir -p raddb/mods-config/files
vi raddb/clients.conf
```
```
client dockernet {
    ipaddr = 172.17.0.0/16
    secret = testing123
}
```

`vi raddb/mods-config/files/authorize`
```
bob Cleartext-Password := "test"
```

```sh
docker build -t my-radius-image -f Dockerfile .
```

### Using the local configuration
```sh
docker run --rm -d --name my-radius -p 1812-1813:1812-1813/udp my-radius-image -X
docker exec -it my-radius bash
radtest bob test 127.0.0.1 0 testing123
```

### Running in debug mode
```sh
docker run --rm --name my-radius -t -p 1812-1813:1812-1813/udp freeradius/freeradius-server -X
```

## References
- [FreeRADIUS](https://freeradius.org/)
- [FreeRADIUS GitHub](https://github.com/FreeRADIUS/freeradius-server)
- [FreeRADIUS Docker](https://hub.docker.com/r/freeradius/freeradius-server/)
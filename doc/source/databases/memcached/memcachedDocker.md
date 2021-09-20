# Memcached Docker

Memcached is a general-purpose distributed memory caching system. It is often used to speed up dynamic database-driven websites by caching data and objects in RAM to reduce the number of times an external data source (such as a database or API) must be read.

## Docker
```
docker run --name memcached -p 11211:11211 -d memcached:1.6 memcached -m 64
```

## Tests
```
telnet 127.0.1.1 11211
set foo 0 0 3
bar
get foo
quit
```

## References
- [Memcached](https://hub.docker.com/_/memcached)
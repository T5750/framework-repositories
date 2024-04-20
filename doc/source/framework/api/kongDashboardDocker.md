# Kong Dashboard Docker

## Compatibility matrix

| Kong-Dashboard versions | Kong versions | Node versions |
|--------------|------------------------------|--------------|
| 1.x.x        | >= 0.6, < 0.10 |              |
| 2.x.x        | 0.10           |              |
| 3.0.x        | >= 0.9, <0.12  | >= 6.0.0     |
| 3.1.x, 3.2.x | >= 0.9, <0.13  | >= 6.0.0     |
| 3.3.x, 3.4.x | >= 0.9, <0.14  | >= 6.0.0     |
| 3.5.x        | >= 0.9, <0.15  | >= 6.0.0     |    
| 3.6.x        | >= 0.9, <2.0.0 | >= 6.0.0     |    

## Using Docker
```sh
# Start Kong Dashboard
docker run --rm -p 8080:8080 pgbi/kong-dashboard start --kong-url http://kong:8001

# Start Kong Dashboard on a custom port
docker run --rm -p [port]:8080 pgbi/kong-dashboard start --kong-url http://kong:8001

# Start Kong Dashboard with basic auth
docker run --rm -p 8080:8080 pgbi/kong-dashboard start \
  --kong-url http://kong:8001
  --basic-auth user1=password1 user2=password2

# See full list of start options
docker run --rm -p 8080:8080 pgbi/kong-dashboard start --help
```

## References
- [Kong Dashboard GitHub](https://github.com/PGBI/kong-dashboard)
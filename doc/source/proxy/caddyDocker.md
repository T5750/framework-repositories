# Caddy Docker

[Caddy 2](https://caddyserver.com/) is a powerful, enterprise-ready, open source web server with automatic HTTPS written in Go.

## Docker
```sh
$ echo "hello world" > index.html
$ docker run -d -p 80:80 \
    -v $PWD/index.html:/usr/share/caddy/index.html \
    -v caddy_data:/data \
    caddy
$ curl http://localhost/
hello world
```
To override the default [`Caddyfile`](https://github.com/caddyserver/dist/blob/master/config/Caddyfile), you can mount a new one at `/etc/caddy/Caddyfile`:
```sh
$ docker run -d -p 80:80 \
    -v $PWD/Caddyfile:/etc/caddy/Caddyfile \
    -v caddy_data:/data \
    caddy
```

## Automatic TLS with the Caddy image
```sh
docker run -d -p 80:80 -p 443:443 -p 443:443/udp \
    -v /site:/srv \
    -v caddy_data:/data \
    -v caddy_config:/config \
    caddy caddy file-server --domain example.com
```

## Graceful reloads
```sh
caddy_container_id=$(docker ps | grep caddy | awk '{print $1;}')
docker exec -w /etc/caddy $caddy_container_id caddy reload
```

## Docker Compose
```
version: "3.7"
services:
  caddy:
    image: caddy
    restart: unless-stopped
    ports:
      - "80:80"
      - "443:443"
      - "443:443/udp"
    volumes:
      - $PWD/Caddyfile:/etc/caddy/Caddyfile
      - $PWD/site:/srv
      - caddy_data:/data
      - caddy_config:/config
volumes:
  caddy_data:
    external: true
  caddy_config:
```

## Runtime Environment
- [Go v1.13](https://github.com/golang/go)

## Screenshots
![](https://caddyserver.com/resources/images/moving-parts.svg)

## References
- [Caddy Docker](https://registry.hub.docker.com/_/caddy)
- [Caddy GitHub](https://github.com/caddyserver/caddy)
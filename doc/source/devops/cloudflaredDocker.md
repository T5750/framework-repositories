# Cloudflare Docker

Cloudflare Tunnel client (formerly Argo Tunnel)

## Docker
```sh
docker run --name cloudflared --rm --net host visibilityspots/cloudflared
```

### custom upstream DNS service
```sh
$ docker run --name cloudflared --rm --net host -e UPSTREAM1=https://dns.google/dns-query visibilityspots/cloudflared:latest
```

### custom port
```sh
$ docker run --name cloudflared --rm --net host -e PORT=5053 visibilityspots/cloudflared:latest
```

### dualstack ipv4/ipv6
```sh
$ docker run --name cloudflared --rm --net host -e ADDRESS=:: visibilityspots/cloudflared:latest
```

## Test
```sh
dig +short @127.0.0.1 -p 5054 visibilityspots.org
```

## How it works
![](https://developers.cloudflare.com/cloudflare-one/static/documentation/connections/connect-apps/handshake.jpg)

## References
- [Cloudflare Docker](https://hub.docker.com/r/visibilityspots/cloudflared)
- [Cloudflare GitHub](https://github.com/cloudflare/cloudflared)
- [Cloudflare Tunnel](https://developers.cloudflare.com/cloudflare-one/connections/connect-apps)
- [Set up a tunnel locally](https://developers.cloudflare.com/cloudflare-one/connections/connect-apps/install-and-setup/tunnel-guide/local/)
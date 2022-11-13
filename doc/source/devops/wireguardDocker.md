# WireGuard Docker

[WireGuard®](https://www.wireguard.com/) is an extremely simple yet fast and modern VPN that utilizes state-of-the-art cryptography.

## Docker
```sh
docker run -d \
  --name=wireguard \
  --cap-add=NET_ADMIN \
  --cap-add=SYS_MODULE \
  -e PUID=1000 \
  -e PGID=1000 \
  -e TZ=Asia/Shanghai \
  -e SERVERURL=wireguard.domain.com `#optional` \
  -e SERVERPORT=51820 `#optional` \
  -e PEERS=1 `#optional` \
  -e PEERDNS=auto `#optional` \
  -e INTERNAL_SUBNET=10.13.13.0 `#optional` \
  -e ALLOWEDIPS=0.0.0.0/0 `#optional` \
  -e LOG_CONFS=true `#optional` \
  -p 51820:51820/udp \
  -v $PWD/wireguard/config:/config \
  -v $PWD/wireguard/lib/modules:/lib/modules \
  --sysctl="net.ipv4.conf.all.src_valid_mark=1" \
  --restart unless-stopped \
  linuxserver/wireguard
```

## WireGuard Easy
The easiest way to run WireGuard VPN + Web-based Admin UI.

### Docker
```sh
docker run -d \
  --name=wg-easy \
  -e WG_HOST=🚨YOUR_SERVER_IP \
  -e PASSWORD=🚨YOUR_ADMIN_PASSWORD \
  -v ~/.wg-easy:/etc/wireguard \
  -p 51820:51820/udp \
  -p 51821:51821/tcp \
  --cap-add=NET_ADMIN \
  --cap-add=SYS_MODULE \
  --sysctl="net.ipv4.conf.all.src_valid_mark=1" \
  --sysctl="net.ipv4.ip_forward=1" \
  --restart unless-stopped \
  weejewel/wg-easy
```
[http://localhost:51821/](http://localhost:51821/)

### Screenshots
![](https://github.com/WeeJeWel/wg-easy/raw/master/assets/screenshot.png)

## References
- [WireGuard Docker](https://hub.docker.com/r/linuxserver/wireguard)
- [WireGuard GitHub](https://github.com/WireGuard)
- [WireGuard](https://www.wireguard.com/)
- [WireGuard Easy GitHub](https://github.com/WeeJeWel/wg-easy)
- [WireGuard Easy Docker](https://hub.docker.com/r/weejewel/wg-easy)
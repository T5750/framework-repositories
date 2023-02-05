# Pi-hole Docker

Network-wide Ad Blocking

## Docker Compose
```yaml
version: "3"
# More info at https://github.com/pi-hole/docker-pi-hole/ and https://docs.pi-hole.net/
services:
  pihole:
    container_name: pihole
    image: pihole/pihole:latest
    # For DHCP it is recommended to remove these ports and instead add: network_mode: "host"
    ports:
      - "53:53/tcp"
      - "53:53/udp"
      - "67:67/udp" # Only required if you are using Pi-hole as your DHCP server
      - "80:80/tcp"
    environment:
      TZ: 'America/Chicago'
      # WEBPASSWORD: 'set a secure password here or it will be random'
    # Volumes store your data between container upgrades
    volumes:
      - './etc-pihole:/etc/pihole'
      - './etc-dnsmasq.d:/etc/dnsmasq.d'
    #   https://github.com/pi-hole/docker-pi-hole#note-on-capabilities
    cap_add:
      - NET_ADMIN # Required if you are using Pi-hole as your DHCP server, else not needed
    restart: unless-stopped
```

## Pi-hole with cloudflared DoH
This example provides a base setup for using [Pi-hole](https://docs.pi-hole.net/) with the [cloudflared DoH](https://docs.pi-hole.net/guides/dns/cloudflared/) service.
More details on how to customize the installation and the compose file can be found in [Docker Pi-hole documentation](https://github.com/pi-hole/docker-pi-hole).

[compose.yaml](https://github.com/docker/awesome-compose/blob/master/pihole-cloudflared-DoH/compose.yaml)
```yaml
version: '3.7'
services:
  pihole:
    image: pihole/pihole:latest
    container_name: pihole
    ports:
      - "53:53/tcp"
      - "53:53/udp"
      - "67:67/udp"
      - "8080:80/tcp"
      - "8443:443/tcp"
    network_mode: "host"
  cloudflared:
    image: visibilityspots/cloudflared
    container_name: cloudflared
    ports:
      - "5054:5054/tcp"
      - "5054:5054/udp"
    network_mode: "host"
```

## Screenshots
![](https://i0.wp.com/pi-hole.net/wp-content/uploads/2018/12/dashboard.png?zoom=1.75&w=3840&ssl=1)

![](https://wp-cdn.pi-hole.net/wp-content/uploads/2018/12/Screenshot-2018-12-19-17.39.58.png.webp)

![](https://wp-cdn.pi-hole.net/wp-content/uploads/2018/12/Screenshot-2018-12-19-17.50.40.png.webp)

## References
- [Pi-hole](https://pi-hole.net/)
- [Pi-hole Docker](https://github.com/pi-hole/docker-pi-hole)
- [Pi-hole with cloudflared DoH](https://github.com/docker/awesome-compose/tree/master/pihole-cloudflared-DoH)
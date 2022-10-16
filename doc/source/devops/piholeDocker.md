# Pi-hole Docker

Network-wide Ad Blocking

## Docker Compose
```
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

## Screenshots
![](https://i0.wp.com/pi-hole.net/wp-content/uploads/2018/12/dashboard.png?zoom=1.75&w=3840&ssl=1)

![](https://wp-cdn.pi-hole.net/wp-content/uploads/2018/12/Screenshot-2018-12-19-17.39.58.png.webp)

![](https://wp-cdn.pi-hole.net/wp-content/uploads/2018/12/Screenshot-2018-12-19-17.50.40.png.webp)

## References
- [Pi-hole](https://pi-hole.net/)
- [Pi-hole Docker](https://github.com/pi-hole/docker-pi-hole)
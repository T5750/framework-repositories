# Homarr Docker

A simple, yet powerful dashboard for your server.

## Demo
[Demo](https://demo.homarr.dev/)

## Docker
```sh
docker run  \
  --name homarr \
  --restart unless-stopped \
  -p 7575:7575 \
  -v /var/run/docker.sock:/var/run/docker.sock \
  -v <your-path>/homarr/configs:/app/data/configs \
  -v <your-path>/homarr/data:/data \
  -v <your-path>/homarr/icons:/app/public/icons \
  -d ghcr.io/ajnart/homarr:latest
docker run --name homarr --restart unless-stopped -p 7575:7575 -d ghcr.io/ajnart/homarr:latest
```
[http://localhost:7575/](http://localhost:7575/)

## Docker Compose
```
version: '3'
#---------------------------------------------------------------------#
#     Homarr - A simple, yet powerful dashboard for your server.     #
#---------------------------------------------------------------------#
services:
  homarr:
    container_name: homarr
    image: ghcr.io/ajnart/homarr:latest
    restart: unless-stopped
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock # Optional, only if you want docker integration
      - ./homarr/configs:/app/data/configs
      - ./homarr/icons:/app/public/icons
      - ./homarr/data:/data
    ports:
      - '7575:7575'
```

## Runtime Environment
- [TypeScript](https://www.typescriptlang.org/)

## Screenshots
![](https://homarr.dev/img/pictures/homarr-devices-preview/compressed/homarr-devices-2d-mockup-flat-shadow-dark-compressed.webp)

![](https://homarr.dev/assets/images/add-app-modal-d83cff75bb43f2e496c8f419f3424694.webp)

![](https://homarr.dev/assets/images/homarr-organize-fc9b4ffafb0cffcee08adf32a8ad0818.gif)

## References
- [Homarr](https://homarr.dev/)
- [Homarr GitHub](https://github.com/ajnart/homarr)
- [Homarr Docker](https://homarr.dev/docs/getting-started/installation/)
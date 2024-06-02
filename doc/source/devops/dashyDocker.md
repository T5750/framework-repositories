# Dashy Docker

A self-hostable personal dashboard built for you. Includes status-checking, widgets, themes, icon packs, a UI editor and tons more!

## Demo
[Live Demo](https://demo.dashy.to/)

## Docker
```sh
docker run -d \
  -p 4000:8080 \
  -v /root/my-local-conf.yml:/app/user-data/conf.yml \
  --name my-dashboard \
  --restart=always \
  lissy93/dashy:latest
docker run -d --name dashy -p 8080:8080 lissy93/dashy
```
[http://localhost:8080/](http://localhost:8080/)

## Docker Compose
```
---
version: "3.8"
services:
  dashy:
    # To build from source, replace 'image: lissy93/dashy' with 'build: .'
    # build: .
    image: lissy93/dashy
    container_name: Dashy
    # Pass in your config file below, by specifying the path on your host machine
    # volumes:
      # - /root/my-config.yml:/app/user-data/conf.yml
    ports:
      - 4000:8080
    # Set any environmental variables
    environment:
      - NODE_ENV=production
    # Specify your user ID and group ID. You can find this by running `id -u` and `id -g`
    #  - UID=1000
    #  - GID=1000
    # Specify restart policy
    restart: unless-stopped
    # Configure healthchecks
    healthcheck:
      test: ['CMD', 'node', '/app/services/healthcheck']
      interval: 1m30s
      timeout: 10s
      retries: 3
      start_period: 40s
```

## Runtime Environment
- [Vue.js](https://github.com/vuejs/vue)
- [Node.js](https://nodejs.org/en/download)

## Screenshots
![](https://dashy.to/img/homepage-assets/theme-slideshow.gif)

![](https://dashy.to/img/homepage-assets/status-check-demo.gif)

![](https://dashy.to/img/homepage-assets/workspace-demo.gif)

![](https://dashy.to/img/homepage-assets/searching-demo.gif)

![](https://dashy.to/img/homepage-assets/config-editor-demo.gif)

## References
- [Dashy](https://dashy.to/)
- [Dashy GitHub](https://github.com/Lissy93/dashy)
- [Dashy Docker](https://dashy.to/docs/deployment)
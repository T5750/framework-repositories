# IT Tools Docker

Handy tools for developers

## Docker
```sh
docker run -d --name it-tools -p 8080:80 corentinth/it-tools
```
[http://localhost:8080/](http://localhost:8080/)

## Docker Compose
```
version: '3'
services:
    it-tools:
        image: corentinth/it-tools:latest
        container_name: it-tools
        ports:
            - 8080:80
        restart: unless-stopped
```

## Runtime Environment
- [Vue.js](https://github.com/vuejs/vue)

## References
- [IT Tools](https://it-tools.tech/)
- [IT Tools GitHub](https://github.com/CorentinTh/it-tools)
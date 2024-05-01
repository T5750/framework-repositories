# Moodist Docker

有助于集中注意力和平静的环境声音

## Docker
```sh
docker run -d --restart=unless-stopped -p 8080:8080 --name moodist geekyouth/moodist:v1.2.0
```
[http://localhost:8080/](http://localhost:8080/)

## Docker Compose
```
version: '3.9'
services:
  moodist:
    image: geekyouth/moodist:v1.2.0
    logging:
      options:
        max-size: 100m
    restart: unless-stopped
    ports:
      - '8080:8080'
```

## Runtime Environment
- [TypeScript](https://www.typescriptlang.org/)

## References
- [Moodist](https://moodist.java666.cn/)
- [Moodist GitHub](https://github.com/geekyouth/moodist)
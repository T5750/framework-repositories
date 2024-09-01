# Airsonic-Advanced Docker

Airsonic is a free, web-based media streamer, providing ubiquitous access to your music. Use it to share your music with friends, or to listen to your own music while at work. You can stream to multiple players simultaneously, for instance to one player in your kitchen and another in your living room.

Airsonic-Advanced is a more modern implementation of the Airsonic fork with several key performance and feature enhancements. It adds and supersedes several features in Airsonic.

## Demo
[Airsonic new UI](https://airsonic.github.io/airsonic-ui/)

## Docker
```sh
docker run -d \
  --name=airsonic-advanced \
  -e PUID=1000 \
  -e PGID=1000 \
  -e TZ=Etc/UTC \
  -e CONTEXT_PATH= `#optional` \
  -e JAVA_OPTS= `#optional` \
  -p 4040:4040 \
  -v /path/to/airsonic-advanced/config:/config \
  -v /path/to/music:/music \
  -v /path/to/playlists:/playlists \
  -v /path/to/podcasts:/podcasts \
  -v /path/to/other media:/media `#optional` \
  --device /dev/snd:/dev/snd `#optional` \
  --restart unless-stopped \
  lscr.io/linuxserver/airsonic-advanced:latest
docker run -d --name=airsonic-advanced -p 4040:4040 quay.io/linuxserver.io/airsonic-advanced
```
[http://localhost:4040/](http://localhost:4040/)

## Docker Compose
```
---
services:
  airsonic-advanced:
    image: lscr.io/linuxserver/airsonic-advanced:latest
    container_name: airsonic-advanced
    environment:
      - PUID=1000
      - PGID=1000
      - TZ=Etc/UTC
      - CONTEXT_PATH= #optional
      - JAVA_OPTS= #optional
    volumes:
      - /path/to/airsonic-advanced/config:/config
      - /path/to/music:/music
      - /path/to/playlists:/playlists
      - /path/to/podcasts:/podcasts
      - /path/to/other media:/media #optional
    ports:
      - 4040:4040
    devices:
      - /dev/snd:/dev/snd #optional
    restart: unless-stopped
```

## Runtime Environment
- [Java 11](https://openjdk.java.net/projects/jdk/11/)

## Screenshots
![](https://airsonic.github.io/demo/screenshot_02.png)

![](https://airsonic.github.io/demo/screenshot_03.png)

![](https://airsonic.github.io/demo/screenshot_04.png)

## References
- [Airsonic](https://airsonic.github.io/)
- [Airsonic GitHub](https://github.com/airsonic/airsonic)
- [Airsonic Docker](https://airsonic.github.io/docs/install/docker/)
- [Airsonic-Advanced GitHub](https://github.com/airsonic-advanced/airsonic-advanced)
- [linuxserver/airsonic-advanced Docker](https://docs.linuxserver.io/images/docker-airsonic-advanced/)
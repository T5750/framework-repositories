# Plex Media Server Docker

Watch free live TV, instantly.

## Watch Free
- [TV Channel Finder](https://www.plex.tv/zh/live-tv-channels/)
- [What to Watch](https://www.plex.tv/what-to-watch)
- [A24 collection](https://www.plex.tv/a24-movies)

## Docker
```sh
docker run \
-d \
--name plex \
-p 32400:32400/tcp \
-p 3005:3005/tcp \
-p 8324:8324/tcp \
-p 32469:32469/tcp \
-p 1900:1900/udp \
-p 32410:32410/udp \
-p 32412:32412/udp \
-p 32413:32413/udp \
-p 32414:32414/udp \
-e TZ="<timezone>" \
-e PLEX_CLAIM="<claimToken>" \
-e ADVERTISE_IP="http://<hostIPAddress>:32400/" \
-h <HOSTNAME> \
-v <path/to/plex/database>:/config \
-v <path/to/transcode/temp>:/transcode \
-v <path/to/media>:/data \
plexinc/pms-docker
```
[http://localhost:32400/](http://localhost:32400/)

### Parameters
- **HOSTNAME** Sets the hostname inside the docker container. For example `-h PlexServer` will set the servername to `PlexServer`.  Not needed in Host Networking.
- **TZ** Set the timezone inside the container.  For example: `Europe/London`.  The complete list can be found here: [https://en.wikipedia.org/wiki/List_of_tz_database_time_zones](https://en.wikipedia.org/wiki/List_of_tz_database_time_zones)
- **PLEX_CLAIM** The claim token for the server to obtain a real server token.  If not provided, server will not be automatically logged in.  If server is already logged in, this parameter is ignored.  You can obtain a claim token to login your server to your plex account by visiting [https://www.plex.tv/claim](https://www.plex.tv/claim)
- **ADVERTISE_IP** This variable defines the additional IPs on which the server may be found.  For example: `http://10.1.1.23:32400`.  This adds to the list where the server advertises that it can be found.  This is only needed in Bridge Networking.

## linuxserver/plex
```sh
docker run -d \
  --name=plex \
  --net=host \
  -e PUID=1000 \
  -e PGID=1000 \
  -e TZ=Etc/UTC \
  -e VERSION=docker \
  -e PLEX_CLAIM= `#optional` \
  -v /path/to/plex/library:/config \
  -v /path/to/tvseries:/tv \
  -v /path/to/movies:/movies \
  --restart unless-stopped \
  lscr.io/linuxserver/plex:latest
docker run -d --name=plex -p 32400:32400 quay.io/linuxserver.io/plex
```
[http://localhost:32400/web](http://localhost:32400/web)

## Screenshots
![](https://www.plex.tv/wp-content/uploads/2022/04/image-avod-devices-lastknights.png)

## References
- [Plex](https://www.plex.tv/)
- [Plex GitHub](https://github.com/plexinc/pms-docker)
- [Plex Docker](https://hub.docker.com/r/plexinc/pms-docker)
- [linuxserver/plex Docker](https://docs.linuxserver.io/images/docker-plex/)
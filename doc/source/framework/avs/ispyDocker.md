# iSpy Docker

A new video surveillance solution for the Internet Of Things.

## Docker
```sh
docker run -it -p 8090:8090 -p 3478:3478/udp -p 50000-50010:50000-50010/udp \
-v /appdata/ispyagentdvr/config/:/agent/Media/XML/ \
-v /appdata/ispyagentdvr/media/:/agent/Media/WebServerRoot/Media/ \
-v /appdata/ispyagentdvr/commands:/agent/Commands/ \
--name ispyagentdvr doitandbedone/ispyagentdvr
```
[http://localhost:8090/](http://localhost:8090/)

### VLC Support
```sh
docker run -it -p 8090:8090 -p 3478:3478/udp -p 50000-50010:50000-50010/udp \
-v /appdata/ispyagentdvr/config/:/agent/Media/XML/ \
-v /appdata/ispyagentdvr/media/:/agent/Media/WebServerRoot/Media/ \
-v /appdata/ispyagentdvr/commands:/agent/Commands/ \
--name ispyagentdvr doitandbedone/ispyagentdvr:vlc
```

## Runtime Environment
- [.NET 6.0](https://dotnet.microsoft.com/download/dotnet)

## Screenshots
![](https://ispycontent.azureedge.net/content/screenshots/agent/h1.jpg)

![](https://ispycontent.azureedge.net/content/screenshots/agent/h2.jpg)

![](https://ispycontent.azureedge.net/content/screenshots/agent/h4.jpg)

![](https://ispycontent.azureedge.net/img/Agent/motion/live.jpg)

![](https://ispycontent.azureedge.net/img/Agent/motion/playback.jpg)

![](https://ispycontent.azureedge.net/img/screenshots/agent/floorplans.jpg)

## References
- [iSpy](https://www.ispyconnect.com/)
- [iSpy GitHub](https://github.com/ispysoftware/iSpy)
- [iSpy Docker](https://hub.docker.com/r/doitandbedone/ispyagentdvr)
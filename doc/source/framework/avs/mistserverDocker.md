# MistServer Docker

MistServer is an open source multimedia multi-standard streaming server

## Docker
```sh
docker run -d --name mistserver -p 4242:4242 -p 8080:8080 -p 1935:1935 -v $PWD/video:/video ddvtech/mistserver
```
[http://localhost:4242/](http://localhost:4242/)

## Screenshots
![](https://news.mistserver.org/img/MistServer_streamsettings.png)

![](https://news.mistserver.org/img/OBS_streamsettings.png)

![](https://news.mistserver.org/img/Streambusy.jpg)

![](https://news.mistserver.org/img/MistServer_embedcode.png)

## References
- [MistServer](https://www.mistserver.org/)
- [MistServer Docker](https://hub.docker.com/r/ddvtech/mistserver/)
- [MistServer GitHub](https://github.com/DDVTECH/mistserver)
- [Live streaming with MistServer and OBS Studio](https://news.mistserver.org/news/59/Live+streaming+with+MistServer+and+OBS+Studio)
# Mosquitto Docker

Eclipse Mosquitto is an open source implementation of a server for versions 5, 3.1.1, and 3.1 of the MQTT protocol. Main homepages: [http://mosquitto.org/](http://mosquitto.org/)

## Docker
```
mkdir -p ./mosquitto/config ./mosquitto/data ./mosquitto/log
chmod -R 755 ./mosquitto
vi ./mosquitto/config/mosquitto.conf
```
```
persistence true
persistence_location /mosquitto/data/
log_dest file /mosquitto/log/mosquitto.log
```
```
docker run -d --name=mosquitto \
-p 1883:1883 -p 9001:9001 \
-v $PWD/mosquitto/config/mosquitto.conf:/mosquitto/config/mosquitto.conf \
-v $PWD/mosquitto/data:/mosquitto/data \
-v $PWD/mosquitto/log:/mosquitto/log \
eclipse-mosquitto:1.6.15
```

## Docker Compose
`mosquitto.yml`

## Quick start
```
mosquitto_sub -t 'test/topic' -v
mosquitto_pub -t 'test/topic' -m 'hello world'
```

## Documentation
- [Documentation for the broker, clients and client library API](https://mosquitto.org/man/)
- [Detailed client library API](https://mosquitto.org/api/)

## References
- [eclipse-mosquitto](https://hub.docker.com/_/eclipse-mosquitto/)
- [Docker - 通过容器安装部署Mosquitto服务教程（MQTT服务器）](https://www.hangge.com/blog/cache/detail_2896.html)
- [Eclipse Mosquitto](https://github.com/eclipse/mosquitto)
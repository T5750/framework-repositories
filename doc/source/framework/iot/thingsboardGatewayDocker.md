# ThingsBoard Gateway Docker

## Installing ThingsBoard using Docker

### Running
```
docker run -it -v ~/.tb-gateway/logs:/thingsboard_gateway/logs -v ~/.tb-gateway/extensions:/thingsboard_gateway/extensions -v ~/.tb-gateway/config:/thingsboard_gateway/config --name tb-gateway --restart always thingsboard/tb-gateway
```
- `-v ~/.tb-gateway/config:/thingsboard_gateway/config` - mounts the host’s dir `~/.tb-gateway/config` to Gateway config directory
- `-v ~/.tb-gateway/extensions:/thingsboard_gateway/extensions` - mounts the host’s dir `~/.tb-gateway/extensions` to Gateway extensions directory
- `-v ~/.tb-gateway/logs:/thingsboard_gateway/logs` - mounts the host’s dir `~/.tb-gateway/logs` to Gateway logs directory

#### Tips
- `vi ~/.tb-gateway/config/tb_gateway.yaml`: host, port
- `vi ~/.tb-gateway/config/mqtt.json`: host, port

### Upgrading
```
$ docker pull thingsboard/tb-gateway
$ docker stop tb-gateway
$ docker rm tb-gateway
$ docker run -it -v ~/.tb-gateway/logs:/var/log/thingsboard-gateway -v ~/.tb-gateway/extensions:/var/lib/thingsboard_gateway/extensions -v ~/.tb-gateway/config:/etc/thingsboard-gateway/config --name tb-gateway --restart always thingsboard/tb-gateway
```

## References
- [Install ThingsBoard IoT Gateway using Docker](https://thingsboard.io/docs/iot-gateway/install/docker-installation/)
- [IoT Gateway Configuration](https://thingsboard.io/docs/iot-gateway/configuration/)
# ThingsBoard Gateway Docker

The Thingsboard **IoT Gateway** is an open-source solution that allows you to integrate devices connected to legacy and third-party systems with Thingsboard.

Thingsboard is an open-source IoT platform for data collection, processing, visualization, and device management.

## Running Gateway 2.5.x
```
docker run -it -v ~/.tb-gateway/logs:/thingsboard_gateway/logs -v ~/.tb-gateway/extensions:/thingsboard_gateway/extensions -v ~/.tb-gateway/config:/thingsboard_gateway/config --name tb-gateway --restart always thingsboard/tb-gateway:2.5.1
```
- `-v ~/.tb-gateway/config:/thingsboard_gateway/config` - mounts the host’s dir `~/.tb-gateway/config` to Gateway config directory
- `-v ~/.tb-gateway/extensions:/thingsboard_gateway/extensions` - mounts the host’s dir `~/.tb-gateway/extensions` to Gateway extensions directory
- `-v ~/.tb-gateway/logs:/thingsboard_gateway/logs` - mounts the host’s dir `~/.tb-gateway/logs` to Gateway logs directory

## Running Gateway 3.0.x
```
docker run -it -v ~/.tb-gateway/logs:/var/log/thingsboard-gateway -v ~/.tb-gateway/extensions:/var/lib/thingsboard_gateway/extensions -v ~/.tb-gateway/config:/etc/thingsboard-gateway/config --name tb-gateway --restart always thingsboard/tb-gateway:3.0.1
```
- `-v ~/.tb-gateway/config:/etc/thingsboard-gateway/config` - mounts the host's dir `~/.tb-gateway/config` to Gateway config directory
- `-v ~/.tb-gateway/extensions:/var/lib/thingsboard_gateway/extensions` - mounts the host's dir `~/.tb-gateway/extensions` to Gateway extensions directory
- `-v ~/.tb-gateway/logs:/var/log/thingsboard-gateway` - mounts the host's dir `~/.tb-gateway/logs` to Gateway logs directory

### Tips
- `vi ~/.tb-gateway/config/tb_gateway.yaml`: host, port
- `vi ~/.tb-gateway/config/mqtt.json`: host, port

## Upgrading
```
$ docker pull thingsboard/tb-gateway
$ docker stop tb-gateway
$ docker rm tb-gateway
$ docker run -it -v ~/.tb-gateway/logs:/var/log/thingsboard-gateway -v ~/.tb-gateway/extensions:/var/lib/thingsboard_gateway/extensions -v ~/.tb-gateway/config:/etc/thingsboard-gateway/config --name tb-gateway --restart always thingsboard/tb-gateway
```

## Architecture
The IoT Gateway is built on top of **Python**, however is different from similar projects that leverage OSGi technology.
The idea is distantly similar to microservices architecture.  
The gateway supports custom connectors to connect to new devices or servers and custom converters for processing data from devices.  
Especially, when we are talking about language APIs and existing libraries to work with serial ports, GPIOs, I2C, and new modules and sensors that are released every day.  

The Gateway provides simple integration APIs, and encapsulates common Thingsboard related tasks: device provisioning, local data persistence and delivery, message converters and other.  
For processing data from devices you also can write custom converter, it will receive information from device and send it to converter to convert to unified format before sending it to the ThingsBoard cluster. 

![](https://thingsboard.io/images/gw_animation.gif)

## References
- [Install ThingsBoard IoT Gateway using Docker](https://thingsboard.io/docs/iot-gateway/install/docker-installation/)
- [IoT Gateway Configuration](https://thingsboard.io/docs/iot-gateway/configuration/)
- [thingsboard/tb-gateway Docker](https://hub.docker.com/r/thingsboard/tb-gateway)
- [thingsboard/tb-gateway Github](https://github.com/thingsboard/thingsboard-gateway)
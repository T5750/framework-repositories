# Minecraft Docker

## Minecraft Server
Docker image that provides a Minecraft Server that will automatically download selected version at startup

### Docker
```sh
docker run -d -it -p 25565:25565 -e EULA=TRUE itzg/minecraft-server
docker run -d -it --name mc -v mc:/data -p 25565:25565 -e EULA=TRUE -e MEMORY=2G itzg/minecraft-server
```

### Docker Compose
```
version: "3"
services:
  mc:
    image: itzg/minecraft-server
    ports:
      - 25565:25565
    environment:
      EULA: "TRUE"
    tty: true
    stdin_open: true
    restart: unless-stopped
    volumes:
      # attach a directory relative to the directory containing this compose file
      - ./minecraft-data:/data
```

### Data directory
![](https://docker-minecraft-server.readthedocs.io/en/latest/img/level-vs-world.drawio.png)

## PocketMine-MP
A server software for Minecraft: Bedrock Edition in PHP

### Docker
```sh
mkdir wherever-you-want
cd wherever-you-want
mkdir data plugins
sudo chown -R 1000:1000 data plugins
docker run -it -p 19132:19132/udp -v $PWD/data:/data -v $PWD/plugins:/plugins ghcr.io/pmmp/pocketmine-mp
```

## References
- [Minecraft Server on Docker (Java Edition)](https://docker-minecraft-server.readthedocs.io/)
- [itzg/minecraft-server GitHub](https://github.com/itzg/docker-minecraft-server)
- [itzg/minecraft-server Docker](https://hub.docker.com/r/itzg/minecraft-server/)
- [itzg/minecraft-server Data directory](https://docker-minecraft-server.readthedocs.io/en/latest/data-directory/)
- [DOWNLOAD THE MINECRAFT: JAVA EDITION SERVER](https://www.minecraft.net/en-us/download/server)
- [PocketMine-MP](https://pmmp.io/)
- [PocketMine-MP GitHub](https://github.com/pmmp/PocketMine-MP)
- [PocketMine-MP Docker](https://github.com/pmmp/PocketMine-Docker)
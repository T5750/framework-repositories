# Docker Compose Installation

## Install Compose
```
sudo curl -L "https://github.com/docker/compose/releases/download/1.27.4/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose
```
```
docker-compose version
```

## Uninstallation
```
sudo rm /usr/local/bin/docker-compose
```

## References
- [Install Docker Compose](https://docs.docker.com/compose/install/)
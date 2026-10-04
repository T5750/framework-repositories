# Docker Compose Installation

Define and run multi-container applications with Docker

## Install Compose V1
```sh
sudo curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose
```
```sh
docker-compose version
```

## Uninstall Compose V1
```sh
sudo rm /usr/local/bin/docker-compose
```

## Install Compose V2
```sh
sudo mkdir -p ~/.docker/cli-plugins/
sudo curl -SL https://github.com/docker/compose/releases/download/v2.27.0/docker-compose-linux-x86_64 -o ~/.docker/cli-plugins/docker-compose
sudo chmod +x ~/.docker/cli-plugins/docker-compose
sudo cp ~/.docker/cli-plugins/docker-compose /usr/local/bin/
```
```sh
docker compose version
```

## Uninstall Compose V2
```sh
sudo rm ~/.docker/cli-plugins/docker-compose
```

## Compose Switch
[Compose Switch](https://github.com/docker/compose-switch/) is a replacement to the Compose V1 `docker-compose` (python) executable. Compose switch translates the command line into Compose V2 `docker compose` and then runs the latter.

## References
- [Install Docker Compose](https://docs.docker.com/compose/install/)
- [Install Docker Compose V2](https://docs.docker.com/compose/cli-command/)
- [Docker Compose GitHub Releases](https://github.com/docker/compose/releases)
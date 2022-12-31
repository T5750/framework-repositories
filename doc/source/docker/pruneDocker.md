# Prune unused Docker objects

## Prune images
Clean up unused Docker Images
```sh
docker image prune
```

## Prune containers
Clean up unused Docker Containers
```sh
docker container prune
```

## Prune volumes
Clean up unused Docker Volumes
```sh
docker volume prune
```

## Prune networks
Clean up unused Docker Networks
```sh
docker network prune
```

## Prune everything
Cleaning up everything at once
```sh
docker system prune
```

## References
- [Prune unused Docker objects](https://docs.docker.com/config/pruning/)
- [5 Simple Commands to Clean up Docker](https://codeopolis.com/posts/simple-commands-to-clean-up-docker/)
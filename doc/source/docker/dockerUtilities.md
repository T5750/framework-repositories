# Docker Utilities

## Ctop
![](https://codeopolis.com/wp-content/uploads/2020/04/ctop.gif)

```sh
docker run --rm -ti \
  --name=ctop \
  --volume /var/run/docker.sock:/var/run/docker.sock:ro \
  quay.io/vektorlab/ctop
```

## Portainer
![](https://codeopolis.com/wp-content/uploads/2020/04/Portainter-Containers-Screen-1536x494.png)

## Watchtower
![](https://codeopolis.com/wp-content/uploads/2020/04/Watchtower-Log.png)

## Dockly
![](https://codeopolis.com/wp-content/uploads/2020/04/dockly.png)

## Dozzle
![](https://codeopolis.com/wp-content/uploads/2020/04/Dozzle.png)

## Nginx Proxy Manager
![](https://codeopolis.com/wp-content/uploads/2020/04/nginx-proxy-manager.png)

## get_container_run_command
通过容器获取容器的docker run命令/get the docker run by a container.
```sh
docker run --rm -v /var/run/docker.sock:/var/run/docker.sock cucker/get_command_4_run_container CONTAINER
```
```sh
# Command alias
echo "alias get_run_command='docker run --rm -v /var/run/docker.sock:/var/run/docker.sock cucker/get_command_4_run_container'" >> ~/.bashrc
. ~/.bashrc

# Excute command
## For all runing containers
get_run_command {allrun}

## For all containers include shutdown
get_run_command {all}

## For one or more containers
get_run_command <CONTAINER> [CONTAINER...]
```

## References
- [6 Awesome Docker Utilities Everyone Should Try](https://codeopolis.com/posts/6-docker-utilities-everyone-should-try/)
- [get_command_4_run_container Docker](https://hub.docker.com/r/cucker/get_command_4_run_container)
- [get_command_4_run_container GitHub](https://github.com/cucker0/dockerfile/tree/main/get_command_4_run_container)
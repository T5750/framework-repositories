# Docker Quickstart

## Docker concepts
**build, run, and share**
- **Flexible**: Even the most complex applications can be containerized.
- **Lightweight**: Containers leverage and share the host kernel, making them much more efficient in terms of system resources than virtual machines.
- **Portable**: You can build locally, deploy to the cloud, and run anywhere.
- **Loosely coupled**: Containers are highly self sufficient and encapsulated, allowing you to replace or upgrade one without disrupting others.
- **Scalable**: You can increase and automatically distribute container replicas across a datacenter.
- **Secure**: Containers apply aggressive constraints and isolations to processes without any configuration required on the part of the user.

### Containers and virtual machines
![docker_Container_VM-min](https://s1.wailian.download/2020/07/19/docker_Container_VM-min.png)

## Set up your Docker environment

### Test Docker version
```
docker --version
docker -v
```

### Test Docker installation
1. `docker run hello-world`
2. `docker image ls`
3. `docker ps --all`

## References
- [Orientation and setup](https://docs.docker.com/get-started/)
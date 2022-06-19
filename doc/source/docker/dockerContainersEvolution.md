# Docker Containers Evolution

## What Are Containers?
![](https://www.baeldung.com/wp-content/uploads/sites/2/2020/11/Virtaul-Machines-vs.-Containers.jpg)

**containers start much faster and use far fewer hardware resources**

## Understanding Docker Containers
### Docker Architecture
![](https://www.baeldung.com/wp-content/uploads/sites/2/2020/11/Docker-Components.jpg)

- [dockerd](https://github.com/docker/engine): This is the central part of Docker — It comprises the Docker daemon that listens to the API requests and manages Docker objects
- [containerd](https://containerd.io/): perform tasks like downloading images and running them as containers
- [runc](https://github.com/opencontainers/runc): It provides a standard mechanism to create namespaces and control groups

### A Typical Docker Workflow
![](https://www.baeldung.com/wp-content/uploads/sites/2/2020/11/Docker-Workflow.jpg)

### Understanding Docker Images
![](https://www.baeldung.com/wp-content/uploads/sites/2/2020/11/Docker-Images.jpg)

**all the layers in this final image are read-only**

![](https://www.baeldung.com/wp-content/uploads/sites/2/2020/11/Docker-Containers.jpg)

### Storage with Docker
![](https://www.baeldung.com/wp-content/uploads/sites/2/2020/11/Docker-Storage.jpg)

### Networking with Docker
![](https://www.baeldung.com/wp-content/uploads/sites/2/2020/11/Docker-Bridge-Network.jpg)



## References
- [Evolution of Docker from Linux Containers](https://www.baeldung.com/linux/docker-containers-evolution)
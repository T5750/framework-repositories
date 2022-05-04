# Portainer Docker

## Docker
### Portainer Server Deployment
```
docker volume create portainer_data
```
```
docker run -d -p 8000:8000 -p 9000:9000 --name=portainer --restart=always -v /var/run/docker.sock:/var/run/docker.sock -v portainer_data:/data portainer/portainer-ce:2.6.3
```

### Portainer Agent Only Deployment
Run the following command to deploy the Agent in your Docker host.
```
docker run -d -p 9001:9001 --name portainer_agent --restart=always -v /var/run/docker.sock:/var/run/docker.sock -v /var/lib/docker/volumes:/var/lib/docker/volumes portainer/agent:2.6.3
```

## Docker Compose
`portainer.yml`

- [http://localhost:9000/](http://localhost:9000/)
- User: admin

## Endpoints
Add endpoint

### Portainer agent
- Name
- Endpoint URL

### Docker API
- Name
- Endpoint URL

`sudo vi /usr/lib/systemd/system/docker.service`
```
#ExecStart=/usr/bin/dockerd -H fd:// --containerd=/run/containerd/containerd.sock
ExecStart=/usr/bin/dockerd -H tcp://0.0.0.0:2375 -H unix:///var/run/docker.sock
```
```
sudo systemctl daemon-reload
sudo systemctl restart docker
ss -ntulp|grep 2375
```

### Edge Agent
[Edge Agent Guide](https://downloads.portainer.io/edge_agent_guide.pdf)
- Name
- Portainer server URL

```
docker run -d -v /var/run/docker.sock:/var/run/docker.sock -v /var/lib/docker/volumes:/var/lib/docker/volumes -v /:/host -v portainer_agent_data:/data --restart always -e EDGE=1 -e EDGE_ID=xx -e EDGE_KEY=xx -e CAP_HOST_MANAGEMENT=1 --name portainer_edge_agent portainer/agent:2.6.3
```

## Screenshots
![](https://user-images.githubusercontent.com/3629071/43952333-ee279786-9c95-11e8-9e2f-0ffd5bd6ba6f.png)

## References
- [Deploying Portainer CE in Docker](https://documentation.portainer.io/v2.0/deploy/ceinstalldocker/)
- [Portainer CE GitHub](https://github.com/portainer/portainer)
- [Docker轻量级可视化管理工具Portainer](https://www.jianshu.com/p/8c4e644e36f9)
- [Add an Edge endpoint](https://docs.portainer.io/v/ce-2.6/admin/endpoints/add/edge)
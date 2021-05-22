# Portainer Docker

## Docker
### Portainer Server Deployment
```
docker volume create portainer_data
```
```
docker run -d -p 8000:8000 -p 9000:9000 --name=portainer --restart=always -v /var/run/docker.sock:/var/run/docker.sock -v portainer_data:/data portainer/portainer-ce
```

### Portainer Agent Only Deployment
Run the following command to deploy the Agent in your Docker host.
```
docker run -d -p 9001:9001 --name portainer_agent --restart=always -v /var/run/docker.sock:/var/run/docker.sock -v /var/lib/docker/volumes:/var/lib/docker/volumes portainer/agent
```

## Docker Compose
`portainer.yml`

- [http://localhost:9000/](http://localhost:9000/)
- User: admin

## References
- [Deploying Portainer CE in Docker](https://documentation.portainer.io/v2.0/deploy/ceinstalldocker/)
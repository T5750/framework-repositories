# Docker Registry

This image contains an implementation of the Docker Registry HTTP API V2 for use with Docker 1.6+. See [github.com/docker/distribution](https://github.com/docker/distribution) for more details about what it is.

## Run a local registry
```
docker run -d -p 5000:5000 --restart always --name registry-srv registry:2
docker pull alpine:3.12
docker tag alpine:3.12 localhost:5000/alpine:3.12
docker push localhost:5000/alpine:3.12
```
```
curl -XGET http://xx.xx.xx.xx:5000/v2/_catalog
curl -XGET http://xx.xx.xx.xx:5000/v2/image_name/tags/list
```

## docker-registry-web
Quick start (config with environment variables, no authentication)
```
docker run -it -p 8080:8080 --name registry-web --link registry-srv -e REGISTRY_URL=http://registry-srv:5000/v2 -e REGISTRY_NAME=localhost:5000 hyper/docker-registry-web
```
Connecting to docker registry with basic authentication and self-signed certificate
```
docker run -it -p 8080:8080 --name registry-web --link registry-srv \
           -e REGISTRY_URL=http://registry-srv:5000/v2 \
           -e REGISTRY_TRUST_ANY_SSL=true \
           -e REGISTRY_BASIC_AUTH="YWRtaW46Y2hhbmdlbWU=" \
           -e REGISTRY_NAME=localhost:5000 hyper/docker-registry-web
```
[http://localhost:8080/](http://localhost:8080/)

## Docker Compose
`registry.yml`

## References
- [Docker Registry](https://hub.docker.com/_/registry)
- [hyper/docker-registry-web](https://hub.docker.com/r/hyper/docker-registry-web)
- [Docker私有仓库搭建与界面化管理](https://www.cnblogs.com/leozhanggg/p/12050322.html)
# MinIO Docker Swarm

## 创建Swarm
```
docker swarm init --advertise-addr <MANAGER-IP>
```
```
docker swarm join \
  --token  SWMTKN-1-49nj1cmql0jkz5s954yi3oex3nedyz0fb0xx14ie39trti4wxv-8vxv8rssmk743ojnwacrr2e7c \
  192.168.99.100:2377
```

## 为MinIO创建Docker secret
```
echo "AKIAIOSFODNN7EXAMPLE" | docker secret create access_key -
echo "wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY" | docker secret create secret_key -
```

## 部署分布式minio服务
```
docker node update --label-add minio1=true <DOCKER-NODE1>
docker node update --label-add minio2=true <DOCKER-NODE2>
docker node update --label-add minio3=true <DOCKER-NODE3>
docker node update --label-add minio4=true <DOCKER-NODE4>
```
`minio-swarm.yaml`
```
docker stack deploy --compose-file=minio-swarm.yaml minio_stack
```
[http://localhost:9001/](http://localhost:9001/)

## 删除分布式MinIO services
```
docker stack rm minio_stack
docker volume ls
docker volume rm volume_name 
```

## References
- [使用Docker Swarm部署MinIO](http://docs.minio.org.cn/docs/master/deploy-minio-on-docker-swarm)
- [使用Docker Swarm部署 MinIO 集群 并 使用 nginx 进行负载均衡](https://blog.csdn.net/DZP_dream/article/details/110876214)
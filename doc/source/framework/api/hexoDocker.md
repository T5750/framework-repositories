# Hexo Docker
docker-hexo is a Docker container for the Hexo blog framework based on Alpine Linux.

## Docker
```
docker volume create hexo_data
docker run -d -it --name hexo -p 4000:4000 -v hexo_data:/home/hexo/.hexo taskbjorn/hexo

docker exec -it hexo sh
hexo generate
```

## Blog
```
docker volume inspect hexo_data
docker run -d -it --name hexo -p 4000:4000 -p 4001:4001 -v hexo_data:/home/hexo/.hexo taskbjorn/hexo
cd T5750.github.io
npm install
hexo g
hexo s -p 4001
```

## Screenshots
![](https://d33wubrfki0l68.cloudfront.net/5997a40576f3beca7bbbd86fe79a795e9d520d8e/87f88/themes/screenshots/landscape.png)

## References
- [taskbjorn/hexo](https://hub.docker.com/r/taskbjorn/hexo)
- [Hexo](https://hexo.io/zh-cn/)
- [Hexo Plugins](https://hexo.io/plugins/)
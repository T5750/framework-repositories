# Docker Dockerfile

`Dockerfile-nginx`

## Ubuntu nginx
`vi default`

```
server {
    listen 80 default_server;
    listen [::]:80 default_server;
    
    root /usr/share/nginx/html;
    index index.html index.htm;

    server_name _;
    location / {
        try_files $uri $uri/ =404;
    }
}
```

`Dockerfile-ubuntu-nginx`

## Building the Image
```sh
docker build -t t5750/nginx .
```

## Running the Image
```sh
docker run -d --name nginx --restart=always -v /etc/localtime:/etc/localtime:ro -p 80:80 t5750/nginx
```

## .dockerignore file
`vi .dockerignore`
```
# comment
*/temp*
*/*/temp*
temp?
```

## Image to Dockerfile
### P3GLEG/Whaler
Program to reverse Docker images into Dockerfiles
```sh
docker run -t --rm -v /var/run/docker.sock:/var/run/docker.sock:ro pegleg/whaler -sV=1.36 redis:5
```
```sh
docker build --rm -t pegleg/whaler .
alias whaler="docker run -t --rm -v /var/run/docker.sock:/var/run/docker.sock:ro pegleg/whaler"
whaler -sV=1.36 nginx:latest
```

### cucker0/DockerImage2Df
Generate Dockerfile from a image
```sh
# Command alias
echo "alias image2df='docker run --rm -v /var/run/docker.sock:/var/run/docker.sock cucker/image2df'" >> ~/.bashrc
. ~/.bashrc

# Excute command
image2df <IMAGE>
```
```sh
docker run --rm -v /var/run/docker.sock:/var/run/docker.sock cucker/image2df redis:5
```

## Tips
### E: Failed to fetch http://deb.debian.org/
可以使用国内163，阿里或者清华的源替换
```
#RUN sed -i s:/archive.ubuntu.com:/mirrors.aliyun.com/ubuntu:g /etc/apt/sources.list
RUN sed -i s:/archive.ubuntu.com:/mirrors.tuna.tsinghua.edu.cn/ubuntu:g /etc/apt/sources.list
RUN cat /etc/apt/sources.list
RUN apt-get clean
RUN apt-get -y update --fix-missing
```

## References
- [Dockerfile reference](https://docs.docker.com/engine/reference/builder/)
- [Running the NGINX Server in a Docker Container](https://www.baeldung.com/linux/nginx-docker-container)
- [解决Dockerfile 生成镜像或Ubuntu update时出现Failed to fetch hash sum mismatch的问题](https://blog.csdn.net/ctwy291314/article/details/104967075)
- [P3GLEG/Whaler GitHub](https://github.com/P3GLEG/Whaler)
- [cucker0/DockerImage2Df GitHub](https://github.com/cucker0/DockerImage2Df)
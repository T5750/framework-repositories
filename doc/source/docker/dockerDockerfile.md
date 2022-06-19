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
```
docker build -t t5750/nginx .
```

## Running the Image
```
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

## References
- [Dockerfile reference](https://docs.docker.com/engine/reference/builder/)
- [Running the NGINX Server in a Docker Container](https://www.baeldung.com/linux/nginx-docker-container)
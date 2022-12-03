# Nginx Proxy Manager Docker

Expose your services easily and securely

## Docker
```sh
docker run -d \
  --name=nginx-proxy-manager \
  -v $pwd/data:/data \
  -v $pwd/letsencrypt:/etc/letsencrypt \
  -p 80:80 \
  -p 443:443 \
  -p 81:81 \
  --restart unless-stopped \
  jc21/nginx-proxy-manager
```
- [http://localhost:81/](http://localhost:81/)
- Default Administrator User: admin@example.com / changeme

## Docker Compose
```
version: "3"
services:
  app:
    image: 'jc21/nginx-proxy-manager:latest'
    restart: unless-stopped
    ports:
      # These ports are in format <host-port>:<container-port>
      - '80:80' # Public HTTP Port
      - '443:443' # Public HTTPS Port
      - '81:81' # Admin Web Port
      # Add any other Stream port you want to expose
      # - '21:21' # FTP
    # Uncomment the next line if you uncomment anything in the section
    # environment:
      # Uncomment this if you want to change the location of 
      # the SQLite DB file within the container
      # DB_SQLITE_FILE: "/data/database.sqlite"
      # Uncomment this if IPv6 is not enabled on your host
      # DISABLE_IPV6: 'true'
    volumes:
      - ./data:/data
      - ./letsencrypt:/etc/letsencrypt
```

## Using MySQL / MariaDB Database
```
version: "3"
services:
  app:
    image: 'jc21/nginx-proxy-manager:latest'
    restart: unless-stopped
    ports:
      - '80:80' # Public HTTP Port
      - '443:443' # Public HTTPS Port
      - '81:81' # Admin Web Port
    environment:
      DB_MYSQL_HOST: "db"
      DB_MYSQL_PORT: 3306
      DB_MYSQL_USER: "npm"
      DB_MYSQL_PASSWORD: "npm"
      DB_MYSQL_NAME: "npm"
    volumes:
      - ./data:/data
      - ./letsencrypt:/etc/letsencrypt
```

## Custom Nginx Configurations
You can add your custom configuration snippet files at `/data/nginx/custom` as follow:
- `/data/nginx/custom/root.conf`: Included at the very end of nginx.conf
- `/data/nginx/custom/http_top.conf`: Included at the top of the main http block
- `/data/nginx/custom/http.conf`: Included at the end of the main http block
- `/data/nginx/custom/stream.conf`: Included at the end of the main stream block
- `/data/nginx/custom/server_proxy.conf`: Included at the end of every proxy server block
- `/data/nginx/custom/server_redirect.conf`: Included at the end of every redirection server block
- `/data/nginx/custom/server_stream.conf`: Included at the end of every stream server block
- `/data/nginx/custom/server_stream_tcp.conf`: Included at the end of every TCP stream server block
- `/data/nginx/custom/server_stream_udp.conf`: Included at the end of every UDP stream server block

## Screenshots
![](https://nginxproxymanager.com/screenshots/login.png)

![](https://nginxproxymanager.com/screenshots/dashboard.png)

![](https://nginxproxymanager.com/screenshots/proxy-hosts.png)

![](https://nginxproxymanager.com/screenshots/proxy-hosts-add.png)

![](https://nginxproxymanager.com/screenshots/custom-settings.png)

## References
- [Nginx Proxy Manager](https://nginxproxymanager.com/)
- [Nginx Proxy Manager GitHub](https://github.com/jc21/nginx-proxy-manager)
- [Nginx Proxy Manager Docker](https://nginxproxymanager.com/setup/#running-the-app)
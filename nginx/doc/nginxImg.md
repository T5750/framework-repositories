# nginx img

## Static
`nginxImg.conf`
```
location /img/ {
    alias /usr/local/software/;
    expires 1d;
}
```

## Proxy
### ngx_cache_purge
```
tar -zxvf /usr/local/software/nginx-1.14.1.tar.gz -C /usr/local/
tar -zxvf /usr/local/software/ngx_cache_purge-2.3.tar.gz -C /usr/local/src
useradd -s /bin/false -M www
yum install gcc gcc-c++ pcre pcre-devel openssl openssl-devel zlib zlib-devel
cd /usr/local/nginx-1.14.1/
./configure --user=www --group=www --prefix=/usr/local/nginx --with-http_stub_status_module --with-http_ssl_module --with-http_v2_module --with-http_gzip_static_module --with-http_sub_module --add-module=/usr/local/src/ngx_cache_purge-2.3
make && make install
```

### Tomcat
```
tar -zxvf /usr/local/software/apache-tomcat-8.5.43.tar.gz -C /usr/local/
cd /usr/local/apache-tomcat-8.5.43/webapps
mkdir img
cp -avx ROOT/tomcat.png img/
/usr/local/apache-tomcat-8.5.43/bin/startup.sh
```

### nginx.conf
```
http {
	...
	proxy_cache_path /data/ngx_cache levels=1:2 keys_zone=whsir_com:10m max_size=10g inactive=60m use_temp_path=off;
	...
	server {
		listen 8080;
		server_name 127.0.0.1;
		root /data/;
	}
	server {
	...
		listen 80;
		server_name 192.168.50.101;
		location / {
			proxy_cache whsir_com;
			proxy_cache_valid 200 302 304 60m;
			proxy_cache_key $host$uri$is_args$args;
			proxy_set_header Host $host;
			proxy_set_header X-Forwarded-For $remote_addr;
			proxy_pass http://127.0.0.1:8080;
			expires 3d;
		}
		location ~ /purge(/.*) {
			allow 127.0.0.1;
			allow 192.168.50.0/24;
			deny all;
			proxy_cache_purge whsir_com $host$1$is_args$args;
		}
		location ~ .*\.(jsp|php)?$ {
			proxy_set_header Host $host;
			proxy_set_header X-Forwarded-For $remote_addr;
			proxy_pass http://127.0.0.1:8080;
		}
	...
	}
} 
```

### Tips
- `proxy_cache_path`：定义缓存存储位置
- `levels=1:2`：设置缓存目录深度，最多能创建3层
- `keys_zone=my_cache:10m`：定义缓存区域名称和内存缓存空间大小
- `max_size=10g`：磁盘缓存空间最大使用值，达到配额后删除最少使用的缓存文件
- `inactive=60m`：设置缓存时间，60分钟内没有被访问过就删除
- `use_temp_path=off`：不使用temp_path指定的临时存储路径，直接将缓存文件写入指定的cache文件中，建议off
- `proxy_cache my_cache`：缓存区域名称，要和keys_zone定义的名称一致
- `proxy_cache_valid 200 302 304 60m`：设置状态码为200 302 304过期时间为60分钟
- `proxy_cache_key $host$uri$is_args$args`：设置缓存的key，这里是以域名、URI、参数组成web缓存的key值，根据key值哈希存储缓存内容到二级缓存目录内
- `expires 3d`：缓存时间3天
- `location ~ /purge(/.*)`：用于手动清除缓存，allow表示只允许指定的IP才可以清除URL缓存
- `location ~ .*\.(jsp|php)?$`：扩展名以jsp或php结尾的不做缓存

### Results
- `nginxImgProxy.conf`
- Chrome: F12 -> Manage Header Columns -> Add custom header -> X-Cache
```
nginx -t
http://192.168.1.110:9090/img/tomcat.png
http://192.168.1.110:9090/purge/img/tomcat.png
curl -s -I http://192.168.1.110:9090/img/tomcat.png|grep "X-Cache"
```

## References
- [Nginx配置缓存服务器及缓存清除](https://blog.whsir.com/post-3173.html)
- [ngx_cache_purge](https://github.com/FRiCKLE/ngx_cache_purge)
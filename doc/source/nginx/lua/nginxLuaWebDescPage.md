## nginx Lua Web Desc Page

### Architecture
![nginxLuaWebDescPageArchitecture-min](https://www.wailian.work/images/2019/09/23/nginxLuaWebDescPageArchitecture-min.png)

- 商品页基本信息（基本信息、图片列表、颜色/尺码关系、扩展属性、规格参数、包装清单、售后保障等）
- 商品介绍（异步加载）
- 其它信息（分类、品牌、店铺、店内分类、同类相关品牌等）
- 其它需要实时展示的数据（价格、库存等）

### 数据存储实现
![nginxLuaWebDescPageDataStorage-min](https://www.wailian.work/images/2019/09/23/nginxLuaWebDescPageDataStorage-min.png)

#### 商品基本信息SSDB集群配置
```
vi /usr/servers/templates/ssdb_basic_7770.conf
vi /usr/servers/templates/ssdb_basic_7771.conf
vi /usr/servers/templates/ssdb_basic_7772.conf
vi /usr/servers/templates/ssdb_basic_7773.conf
mkdir -p /usr/data/ssdb_7770
mkdir -p /usr/data/ssdb_7771
mkdir -p /usr/data/ssdb_7772
mkdir -p /usr/data/ssdb_7773
nohup /usr/servers/ssdb-1.9.4/ssdb-server -d /usr/servers/templates/ssdb_basic_7770.conf &
nohup /usr/servers/ssdb-1.9.4/ssdb-server -d /usr/servers/templates/ssdb_basic_7771.conf &
nohup /usr/servers/ssdb-1.9.4/ssdb-server -d /usr/servers/templates/ssdb_basic_7772.conf &
nohup /usr/servers/ssdb-1.9.4/ssdb-server -d /usr/servers/templates/ssdb_basic_7773.conf &
```

#### 商品介绍SSDB集群配置
```
vi /usr/servers/templates/ssdb_desc_8880.conf
vi /usr/servers/templates/ssdb_desc_8881.conf
vi /usr/servers/templates/ssdb_desc_8882.conf
vi /usr/servers/templates/ssdb_desc_8883.conf
mkdir -p /usr/data/ssdb_888{0,1,2,3}
nohup /usr/servers/ssdb-1.9.4/ssdb-server -d /usr/servers/templates/ssdb_desc_8880.conf &
nohup /usr/servers/ssdb-1.9.4/ssdb-server -d /usr/servers/templates/ssdb_desc_8881.conf &
nohup /usr/servers/ssdb-1.9.4/ssdb-server -d /usr/servers/templates/ssdb_desc_8882.conf &
nohup /usr/servers/ssdb-1.9.4/ssdb-server -d /usr/servers/templates/ssdb_desc_8883.conf &
ps aux | grep ssdb
```

#### 其它信息Redis配置
```
vi /usr/servers/templates/redis_6660.conf
port 6660
pidfile "/var/run/redis_6660.pid"
#设置内存大小，根据实际情况设置，此处测试仅设置20mb
maxmemory 20mb
#内存不足时，所有KEY按照LRU算法进行删除
maxmemory-policy allkeys-lru
#Redis的过期算法不是精确的而是通过采样来算的，默认采样为3个，此处改成10
maxmemory-samples 10
#不进行RDB持久化
save ""
#不进行AOF持久化
appendonly no
bind 192.168.1.110
```
```
vi /usr/servers/templates/redis_6661.conf
vi /usr/servers/templates/redis_6662.conf
port 6661
pidfile "/var/run/redis_6661.pid"
#设置内存大小，根据实际情况设置，此处测试仅设置20mb
maxmemory 20mb
#内存不足时，所有KEY按照LRU算法进行删除
maxmemory-policy allkeys-lru
#Redis的过期算法不是精确的而是通过采样来算的，默认采样为3个，此处改成10
maxmemory-samples 10
#不进行RDB持久化
save ""
#不进行AOF持久化
appendonly no
#主从
slaveof 192.168.1.110 6660
bind 192.168.1.110
```
```
nohup redis-server /usr/servers/templates/redis_6660.conf &
nohup redis-server /usr/servers/templates/redis_6661.conf &
nohup redis-server /usr/servers/templates/redis_6662.conf &
```

#### Tests
```
redis-cli -p 7770
set i 1
redis-cli -p 7772
get i
redis-cli -p 8881
redis-cli -p 8883
redis-cli -h 192.168.1.110 -p 6660
redis-cli -h 192.168.1.110 -p 6662
```

#### Twemproxy配置
```
vi /usr/servers/templates/nutcracker.yml
vi /usr/servers/templates/nutcracker.sh
/usr/servers/templates/nutcracker.sh start
```
```
redis-cli -p 1111
set i 1
redis-cli -p 1112
get i
redis-cli -p 1113
set i 1
redis-cli -p 1114
get i
redis-cli -p 1115
set i 1
redis-cli -p 1116
get i
```

### 动态服务实现
```
cd /usr/servers/templates/default/
unzip nginx-1.0.war
cd /usr/servers/
cp -avx tomcat-8-foo/ tomcat-8-desc-page
vi /usr/servers/tomcat-8-desc-page/conf/Catalina/localhost/ROOT.xml
<Context path="" docBase="/usr/servers/templates/default"></Context>
```
```
/usr/servers/tomcat-8-desc-page/bin/startup.sh
http://192.168.1.110:8080/info?type=basic&skuId=1
http://192.168.1.110:8080/info?type=desc&skuId=1
http://192.168.1.110:8080/info?type=other&ps3Id=655&brandId=14026
```

#### nginx配置
```
vi /usr/servers/nginx/conf/nginx.conf
include /usr/servers/templates/nginx_desc_page.conf;
nginx -s reload
```

#### 绑定hosts
```
vi /etc/hosts
192.168.1.110 item.jd.com
192.168.1.110 item2015.jd.com
192.168.1.110 d.3.cn
```
http://item.jd.com/backend/info?type=basic&skuId=1

### 前端展示实现
#### 基础组件
- `lualib/common.lua`

#### 商品介绍
- `desc.lua`
- `nginx_desc_page.conf`: `^/desc/(\d+)$`
- http://d.3.cn/desc/1

#### 前端展示
- `item.lua`
- `lualib/item.lua`
- `nginx_desc_page.conf`: `^/(\d+).html$`
- http://item.jd.com/1217499.html

### 优化
#### local cache
`local_cache.lua`
```
vim /usr/servers/nginx/conf/nginx.conf
#http部分分配内存大小
lua_shared_dict item_local_cache 1m;
```

#### ngx_cache_purge
为了防止恶意刷页面/热点页面访问频繁，可以使用nginx proxy_cache做页面缓存，当然更好的选择是使用CDN技术，如通过Apache Traffic Server、Squid、Varnish
```
vim /usr/servers/nginx/conf/nginx.conf
proxy_buffering on;
proxy_buffer_size 8k;
proxy_buffers 256 8k;
proxy_busy_buffers_size 64k;
proxy_temp_file_write_size 64k;
proxy_temp_path /usr/servers/nginx/proxy_temp;
#设置Web缓存区名称为cache_one，内存缓存空间大小为200MB，1分钟没有被访问的内容自动清除，硬盘缓存空间大小为30GB。
proxy_cache_path /usr/servers/nginx/proxy_cache levels=1:2 keys_zone=cache_item:200m inactive=1m max_size=30g;
```
[Module ngx_http_proxy_module](http://nginx.org/cn/docs/http/ngx_http_proxy_module.html)

`nginx_desc_page.conf`
```
map $host $item_dynamic {
	default "0";
	item2015.jd.com "1";
}
server {
	location ~ ^/(\d+).html$ {
		set $skuId $1;
		if ($host !~ "^(item|item2015)\.jd\.com$") {
		 return 403;
		}
		expires 3m;
		proxy_cache cache_item;
		proxy_cache_key $uri;
		proxy_cache_bypass $item_dynamic;
		proxy_no_cache $item_dynamic;
		proxy_cache_valid 200 301 3m;
		proxy_cache_use_stale updating error timeout invalid_header http_500 http_502 http_503 http_504;
		proxy_pass_request_headers off;
		proxy_set_header Host $host;
		#支持keep-alive
		proxy_http_version 1.1;
		proxy_set_header Connection "";
		proxy_pass http://127.0.0.1/proxy/$skuId.html;
		add_header X-Cache '$upstream_cache_status';
	}

	location ~ ^/proxy/(\d+).html$ {
		allow 127.0.0.1;
		deny all;
		keepalive_timeout 30s;
		keepalive_requests 1000;
		default_type 'text/html';
		charset utf-8;
		lua_code_cache on;
		set $skuId $1;
		content_by_lua_file /usr/servers/templates/item.lua;
	}

	location /purge {
		allow 127.0.0.1;
		allow 192.168.0.0/16;
		allow 192.168.1.100;
		deny all;
		proxy_cache_purge cache_item $arg_url;
	}
}
```
- `expires`：设置响应缓存头信息，此处是3分钟；将会得到`Cache-Control:max-age=180`和类似`Expires:Sat, 28 Feb 2015 10:01:10 GMT`的响应头
- `proxy_cache`：使用之前在`nginx.conf`中配置的`cache_item`缓存
- `proxy_cache_key`：缓存key为uri，不包括host和参数，这样不管用户怎么通过在url上加随机数都是走缓存的
- `proxy_cache_bypass`：nginx不从缓存取响应的条件，可以写多个；如果存在一个字符串条件且不是“0”，那么nginx就不会从缓存中取响应内容；此处如果使用的host为item2015.jd.com时就不会从缓存取响应内容
- `proxy_no_cache`：nginx不将响应内容写入缓存的条件，可以写多个；如果存在一个字符串条件且不是“0”，那么nginx就不会从将响应内容写入缓存；此处如果使用的host为item2015.jd.com时就不会将响应内容写入缓存
- `proxy_cache_valid`：为不同的响应状态码设置不同的缓存时间，此处对200、301缓存3分钟
- `proxy_cache_use_stale`：什么情况下使用不新鲜（过期）的缓存内容；配置和`proxy_next_upstream`内容类似；此处配置了如果连接出错、超时、404、500等都会使用不新鲜的缓存内容；此外配置了updating配置，通过配置它可以在nginx正在更新缓存（其中一个Worker进程）时（其他的Worker进程）使用不新鲜的缓存进行响应，这样可以减少回源的数量
- `proxy_pass_request_headers`：不需要请求头，所以不传递
- `proxy_http_version 1.1`和`proxy_set_header Connection ""`：支持keepalive
- `add_header X-Cache '$upstream_cache_status'`：添加是否缓存命中的响应头；比如命中HIT、不命中MISS、不走缓存BYPASS；比如命中会看到`X-Cache：HIT`响应头
- `allow/deny`：允许和拒绝访问的ip列表，此处只允许本机访问
- `keepalive_timeout 30s`和`keepalive_requests 1000`：支持keepalive
- 清理缓存：http://item.jd.com/purge?url=/1217499.html

`item.lua`
```
--添加Last-Modified，用于响应304缓存
ngx.header["Last-Modified"] = ngx.http_time(ngx.now())
local template = require "resty.template"
template.caching(true)
template.render("item.html", basicInfo)
```

#### GZIP压缩
```
gzip on;
gzip_min_length 4k;
gzip_buffers 4 16k;
gzip_http_version 1.0;
gzip_proxied any;#前端是squid的情况下要加此参数，否则squid上不缓存gzip文件
gzip_comp_level 2;
gzip_types text/plain application/x-javascript text/css application/xml;
gzip_vary on;
```

### Tips

Module | HTTP Port | Command or Url | Start
---|---|---|---
SSDB Basic | 7770-7773 | redis-cli -p 7770 | nohup /usr/servers/ssdb-1.9.4/ssdb-server -d /usr/servers/templates/ssdb_basic_7770.conf &
SSDB Desc | 8880-8883 | redis-cli -p 8880 | nohup /usr/servers/ssdb-1.9.4/ssdb-server -d /usr/servers/templates/ssdb_desc_8880.conf &
Redis | 6660-6662 | redis-cli -h 192.168.1.110 -p 6660 | nohup redis-server /usr/servers/templates/redis_6660.conf &
Twemproxy | 1111-1116 | redis-cli -p 1111 | /usr/servers/templates/nutcracker.sh start
tomcat-8-desc-page | 8080 | [Other API](http://192.168.1.110:8080/info?type=other&ps3Id=655&brandId=14026) | /usr/servers/tomcat-8-desc-page/bin/startup.sh
nginx | 80 | [Desc Page](http://item.jd.com/1217499.html) | nginx

### References
- [第七章 Web开发实战2——商品详情页](https://www.iteye.com/blog/jinnianshilongnian-2188538)
## nginx Lua Installation

### OpenResty
```
mkdir -p /usr/servers
cd /usr/servers/
yum install pcre-devel openssl-devel gcc curl
wget https://openresty.org/download/ngx_openresty-1.7.7.2.tar.gz
tar -xzvf ngx_openresty-1.7.7.2.tar.gz
cd /usr/servers/ngx_openresty-1.7.7.2/bundle/LuaJIT-2.1-20150120/
make clean && make && make install
cd /usr/servers/ngx_openresty-1.7.7.2/bundle
wget https://github.com/FRiCKLE/ngx_cache_purge/archive/2.3.tar.gz
tar -xvf 2.3.tar.gz
wget https://github.com/yaoweibin/nginx_upstream_check_module/archive/v0.3.0.tar.gz
tar -xvf v0.3.0.tar.gz
cd /usr/servers/ngx_openresty-1.7.7.2
./configure --prefix=/usr/servers --with-http_realip_module --with-pcre --with-luajit --with-http_iconv_module --add-module=./bundle/ngx_cache_purge-2.3/ --add-module=./bundle/nginx_upstream_check_module-0.3.0/ -j2
make && make install
/usr/servers/nginx/sbin/nginx -V
/usr/servers/nginx/sbin/nginx
```

### nginx
```
vi /usr/servers/nginx/conf/lua.conf
#lua.conf
server {
    listen       80;
    server_name  _;
}
```
```
vim /usr/servers/nginx/conf/nginx.conf
#http部分添加
#lua模块路径，多个之间”;”分隔，其中”;;”表示默认搜索路径，默认到/usr/servers/nginx下找
lua_package_path "/usr/servers/lualib/?.lua;;"; #lua模块
lua_package_cpath "/usr/servers/lualib/?.so;;"; #c模块
include lua.conf;
```
```
/usr/servers/nginx/sbin/nginx -t
```

### HelloWorld
```
vi /usr/servers/nginx/conf/lua.conf
#server部分添加
location /lua {
    default_type 'text/html';
    content_by_lua 'ngx.say("hello world")';
}
```
http://192.168.1.110/lua
```
vim /usr/servers/nginx/conf/lua/test.lua
#添加如下内容
ngx.say("hello world");
#lua.conf修改为
location /lua {
    default_type 'text/html';
    content_by_lua_file conf/lua/test.lua; #相对于nginx安装目录
}
#开发阶段可以通过lua_code_cache  off;关闭缓存
/usr/servers/nginx/sbin/nginx -s reload
#nginx: [alert] lua_code_cache is off; this will hurt performance in /usr/servers/nginx/conf/lua.conf:8
tail -fn 99 /usr/servers/nginx/logs/error.log
```

### Tips
```
vi ~/.bashrc
#export NGINX_HOME=/usr/local/nginx
export NGINX_HOME=/usr/servers/nginx
```

#### 分布式限流
- `lua.conf`: /lua_limit
- `test_lua_limit.lua`
```
vim /usr/servers/nginx/conf/nginx.conf
#http部分添加
lua_shared_dict locks 10m;
lua_shared_dict limit_counter 10m;
```

### References
- [第一章 安装OpenResty(Nginx+Lua)开发环境](https://www.iteye.com/blog/jinnianshilongnian-2186270)
- [OpenResty 安装](https://openresty.org/cn/installation.html)
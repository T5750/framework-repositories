## nginx Lua Dev Lib

### Redis Client
lua-resty-redis是为基于cosocket API的ngx_lua提供的Lua redis客户端。默认安装OpenResty时已自带了该模块，使用文档https://github.com/openresty/lua-resty-redis

#### 基本操作
- `lua.conf`: /lua_redis_basic
- `test_redis_basic.lua`
- http://192.168.1.110/lua_redis_basic

#### 连接池
```
local function close_redis(red)
    if not red then
        return
    end
    --释放连接(连接池实现)
    local pool_max_idle_time = 10000 --毫秒
    local pool_size = 100 --连接池大小
    local ok, err = red:set_keepalive(pool_max_idle_time, pool_size)
    if not ok then
        ngx.say("set keepalive error : ", err)
    end
end
```
```
vim /usr/servers/nginx/conf/nginx.conf
#http部分添加
#默认连接池大小，默认30
lua_socket_pool_size 30;
#默认超时时间,默认60s
lua_socket_keepalive_timeout 60s;
```

#### pipeline
- `lua.conf`: /lua_redis_pipeline
- `test_redis_pipeline.lua`
- http://192.168.1.110/lua_redis_pipeline

### MySQL Client
lua-resty-mysql是为基于cosocket API的ngx_lua提供的Lua MySQL客户端。默认安装OpenResty时已自带了该模块，使用文档https://github.com/openresty/lua-resty-mysql
- `lua.conf`: /lua_mysql
- `test_mysql.lua`
- http://192.168.1.110/lua_mysql?ch=hello

### Http Client
OpenResty默认没有提供Http客户端，需要使用第三方提供，比如https://github.com/ledgetech/lua-resty-http

#### lua-resty-http
```
cd /usr/servers/lualib/resty
wget https://raw.githubusercontent.com/pintsized/lua-resty-http/master/lib/resty/http_headers.lua
wget https://raw.githubusercontent.com/pintsized/lua-resty-http/master/lib/resty/http.lua
```
```
vim /usr/servers/nginx/conf/nginx.conf
#http部分添加
resolver 8.8.8.8;
```
- `lua.conf`: /lua_resty_http
- `test_resty_http.lua`
- http://192.168.1.110/lua_resty_http



### References
- [第五章 常用Lua开发库1-redis、mysql、http客户端](https://www.iteye.com/blog/jinnianshilongnian-2187328)
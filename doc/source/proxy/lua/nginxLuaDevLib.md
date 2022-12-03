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

#### ngx.location.capture
```
vim /usr/servers/nginx/conf/nginx.conf
#http部分添加
upstream backend {
    server tc110;
    keepalive 100;
}
```
```
vi /usr/servers/nginx/conf/lua.conf
	location ~ /proxy/(.*) {
		internal;
		proxy_pass http://backend/$1$is_args$args;
	}

	location /lua_location_capture_http {
		default_type 'text/html';
		lua_code_cache on;
		content_by_lua_file conf/lua/test_location_capture_http.lua;
	}
```
- `test_location_capture_http.lua`
- http://192.168.1.110/lua_location_capture_http

### JSON
#### cjson
lua-cjson文档https://www.kyne.com.au/~mark/software/lua-cjson-manual.html
- `lua.conf`: /lua_cjson
- `test_cjson.lua`
- http://192.168.1.110/lua_cjson

#### dkjson
dkjson文档http://dkolf.de/src/dkjson-lua.fsl/home和http://dkolf.de/src/dkjson-lua.fsl/wiki?name=Documentation
```
cd /usr/servers/lualib/
wget http://dkolf.de/src/dkjson-lua.fsl/raw/dkjson.lua?name=16cbc26080996d9da827df42cb0844a25518eeb3 -O dkjson.lua
```
- `lua.conf`: /lua_dkjson
- `test_dkjson.lua`
- http://192.168.1.110/lua_dkjson

### 编码转换
#### luarocks
```
wget https://luarocks.org/releases/luarocks-2.4.1.tar.gz
tar -xvf luarocks-2.4.1.tar.gz
cd /usr/servers/luarocks-2.4.1
./configure --prefix=/usr/servers/luajit --with-lua=/usr/servers/luajit --lua-suffix=jit-2.1.0-alpha --with-lua-include=/usr/servers/luajit/include/luajit-2.1
make build
make install
```
- `--prefix`：设定luarocks的安装目录
- `--with-lua`：则是系统中安装的lua的根目录
- `--lua-suffix`：版本后缀，此处因为OpenResty的lua解释器使用的是`luajit-2.1.0-alpha`，所以此处得写`jit-2.1.0-alpha`
- `--with-lua-include`：设置lua引入一些头文件头文件的目录

#### lua-iconv
[Lua iconv](http://ittner.github.io/lua-iconv/)
```
/usr/servers/luajit/bin/luarocks install lua-iconv
cp /usr/servers/luajit/lib/lua/5.1/iconv.so /usr/servers/lualib
```
- `lua.conf`: /lua_iconv
- `test_iconv.lua`
- http://192.168.1.110/lua_iconv
- iconv在转换时遇到非法字符或不能转换的字符就会失败，此时可以使用如下方式忽略转换失败的字符：`local togbk_ignore = iconv.new("GBK//IGNORE", "UTF-8")`

### 位运算
- `lua.conf`: /lua_bit
- `test_bit.lua`
- http://192.168.1.110/lua_bit

#### cache
[lua-resty-lrucache](https://github.com/openresty/lua-resty-lrucache)和`ngx.shared.DICT`不一样的是它是每Worker进程共享，即每个Worker进行会有一份缓存，而且经过实际使用发现其性能不如`ngx.shared.DICT`。其好处是不需要进行全局配置
- `lua.conf`: /lua_lrucache
- `/usr/servers/lualib/resty_lrucache.lua`
- `test_lrucache.lua`
- http://192.168.1.110/lua_lrucache

### 字符串处理
[UTF-8 module for Lua 5.x](https://github.com/starwing/luautf8)
```
yum install git
/usr/servers/luajit/bin/luarocks install utf8
cp /usr/servers/luajit/lib/lua/5.1/utf8.so /usr/servers/lualib
```
- `lua.conf`: /lua_utf8
- `test_utf8.lua`
- http://192.168.1.110/lua_utf8

字符串转换为unicode编码：
- `lua.conf`: /lua_str2unicode
- `test_str2unicode.lua`
- http://192.168.1.110/lua_str2unicode

删除空格：
- `lua.conf`: /lua_trim
- `test_trim.lua`
- http://192.168.1.110/lua_trim

字符串分割：
- `lua.conf`: /lua_split
- `test_split.lua`
- http://192.168.1.110/lua_split

### Template
#### lua-resty-template
[lua-resty-template](https://github.com/bungle/lua-resty-template)
```
cd /usr/servers/lualib/resty
wget https://raw.githubusercontent.com/bungle/lua-resty-template/master/lib/resty/template.lua
mkdir /usr/servers/lualib/resty/html
cd /usr/servers/lualib/resty/html
wget https://raw.githubusercontent.com/bungle/lua-resty-template/master/lib/resty/template/html.lua
```
```
mkdir /usr/servers/templates
mkdir /usr/servers/templates/default
mkdir /usr/servers/templates/foo
vim /usr/servers/templates/foo/index.html
Template Foo
vim /usr/servers/templates/default/index.html
Template Default
```
- `lua.conf`: /lua_template_index
- `test_template_index.lua`
- http://192.168.1.110/lua_template_index

#### API
- `lua.conf`: /lua_template_print
- `test_template_print.lua`
- http://192.168.1.110/lua_template_print
- `vi /usr/servers/templates/default/student.html`
- `lua.conf`: /lua_template_student
- `test_template_student.lua`
- http://192.168.1.110/lua_template_student

- `{(include_file)}`：包含另一个模板文件
- `{* var *}`：变量输出
- `{{ var }}`：变量转义输出
- `{% code %}`：代码片段
- `{# comment #}`：注释
- `{-raw-}`：中间的内容不会解析，作为纯文本输出

### References
- [第五章 常用Lua开发库1-redis、mysql、http客户端](https://www.iteye.com/blog/jinnianshilongnian-2187328)
- [ngx.location.capture](https://github.com/openresty/lua-nginx-module#ngxlocationcapture)
- [第五章 常用Lua开发库2-JSON库、编码转换、字符串处理](https://www.iteye.com/blog/jinnianshilongnian-2187643)
- [OpenResty下安装luarocks](https://segmentfault.com/a/1190000008658146)
- [Lua 5.3 参考手册](http://cloudwu.github.io/lua53doc/manual.html)
- [第五章 常用Lua开发库3-模板渲染](https://www.iteye.com/blog/jinnianshilongnian-2187775)
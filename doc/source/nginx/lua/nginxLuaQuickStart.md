## nginx Lua Quick Start

### API
#### 接收请求
- `lua.conf`: /lua_request/(\d+)/(\d+)
- `test_request.lua`
- `wget --post-data 'a=1&b=2' 'http://127.0.0.1/lua_request/1/2?a=3&b=4' -O -`

- `ngx.var`：nginx变量，如果要赋值如`ngx.var.b = 2`，此变量必须提前声明；另外对于nginx location中使用正则捕获的捕获组可以使用ngx.var[捕获组数字]获取
- `ngx.req.get_headers`：获取请求头，默认只获取前100，如果想要获取所以可以调用`ngx.req.get_headers(0)`；获取带中划线的请求头时请使用如`headers.user_agent`这种方式；如果一个请求头有多个值，则返回的是table
- `ngx.req.get_uri_args`：获取url请求参数，其用法和get_headers类似
- `ngx.req.get_post_args`：获取post请求内容体，其用法和get_headers类似，但是必须提前调用`ngx.req.read_body()`来读取body体（也可以选择在nginx配置文件使用`lua_need_request_body on;`开启读取body体，但是官方不推荐）
- `ngx.req.raw_header`：未解析的请求头字符串
- `ngx.req.get_body_data`：为解析的请求body体内容字符串

#### 输出响应
- `lua.conf`: /lua_response, /lua_response_redirect
- `test_response.lua`
- `test_response_redirect.lua`

- `ngx.header`：输出响应头
- `ngx.print`：输出响应内容体
- `ngx.say`：同`ngx.print`，但是会最后输出一个换行符
- `ngx.exit`：指定状态码退出
- `ngx.redirect`：重定向
- `ngx.status=状态码`，设置响应的状态码
- `ngx.resp.get_headers()`获取设置的响应状态码
- `ngx.send_headers()`发送响应状态码，当调用`ngx.say/ngx.print`时自动发送响应状态码；可以通过`ngx.headers_sent=true`判断是否发送了响应状态码

#### 其它API
- `lua.conf`: /lua_other

- `ngx.escape_uri/ngx.unescape_uri`：uri编码解码
- `ngx.encode_args/ngx.decode_args`：参数编码解码
- `ngx.encode_base64/ngx.decode_base64`：BASE64编码解码
- `ngx.re.match`：nginx正则表达式匹配

#### Nginx全局内存
```
vim /usr/servers/nginx/conf/nginx.conf
#http部分分配内存大小
#共享全局变量，在所有worker间共享
lua_shared_dict shared_data 1m;
```

### 模块指令

指令 | 所处处理阶段 | 使用范围 | 解释
---|---|---|---
init_by_lua, init_by_lua_file | loading-config | http | nginx Master进程加载配置时执行；通常用于初始化全局配置/预加载Lua模块
init_worker_by_lua, init_worker_by_lua_file | starting-worker | http | 每个Nginx Worker进程启动时调用的计时器，如果Master进程不允许则只会在init_by_lua之后调用；通常用于定时拉取配置/数据，或者后端服务的健康检查
set_by_lua, set_by_lua_file | rewrite | server,server if,location,location if | 设置nginx变量，可以实现复杂的赋值逻辑；此处是阻塞的，Lua代码要做到非常快
rewrite_by_lua, rewrite_by_lua_file | rewrite tail | http,server,location,location if | rewrite阶段处理，可以实现复杂的转发/重定向逻辑
access_by_lua, access_by_lua_file | access tail | http,server,location,location if | 请求访问阶段处理，用于访问控制
content_by_lua, content_by_lua_file | content | location，location if | 内容处理器，接收请求处理并输出响应
header_filter_by_lua, header_filter_by_lua_file | output-header-filter | http，server，location，location if | 设置header和cookie
body_filter_by_lua, body_filter_by_lua_file | output-body-filter | http，server，location，location if | 对响应数据进行过滤，比如截断、替换。
log_by_lua, log_by_lua_file | log | http，server，location，location if | log阶段处理，比如记录访问量/统计平均响应时间

#### init_by_lua
```
vim /usr/servers/nginx/conf/nginx.conf
#http部分添加
#共享全局变量，在所有worker间共享
lua_shared_dict shared_data 1m;  
init_by_lua_file conf/lua/init.lua;
```
- `init.lua`
- `lua.conf`: /lua_init
- `test_init.lua`

#### init_worker_by_lua
```
vim /usr/servers/nginx/conf/nginx.conf
#http部分添加
init_worker_by_lua_file conf/lua/init_worker.lua;
```
- `init_worker.lua`

- `ngx.timer.at`：延时调用相应的回调方法；`ngx.timer.at`(秒单位延时，回调函数，回调函数的参数列表)；可以将延时设置为0即得到一个立即执行的任务，任务不会在当前请求中执行不会阻塞当前请求，而是在一个轻量级线程中执行
- `lua_max_pending_timers 1024;`#最大等待任务数
- `lua_max_running_timers 256;`#最大同时运行任务数

#### set_by_lua
- `lua.conf`: /lua_set
- `test_set.lua`
- http://192.168.1.110/lua_set?i=1&j=10
- `set_by_lua_file`：语法`set_by_lua_file $var lua_file arg1 arg2...;`在lua代码中可以实现所有复杂的逻辑，但是要执行速度很快，不要阻塞

#### rewrite_by_lua
- `lua.conf`: /lua_rewrite_github
- `test_rewrite_github.lua`
- http://192.168.1.110/lua_rewrite_github?jump=1
- `lua.conf`: /lua_rewrite_uri
- `test_rewrite_uri.lua`
- http://192.168.1.110/lua_rewrite_uri?jump=0
- http://192.168.1.110/lua_rewrite_uri?jump=1
- `lua.conf`: /lua_rewrite_foo
- `test_rewrite_foo.lua`
- http://192.168.1.110/lua_rewrite_foo?jump=1

- `ngx.req.set_uri(uri, false)`：可以内部重写uri（可以带参数），等价于`rewrite ^ /lua_rewrite_3；`通过配合if/else可以实现`rewrite ^ /lua_rewrite_3 break；`这种功能；此处两者都是location内部url重写，不会重新发起新的location匹配
- `ngx.req.set_uri_args`：重写请求参数，可以是字符串(a=1&b=2)也可以是table
- `ngx.req.set_uri(uri, true)`：可以内部重写uri，即会发起新的匹配location请求，等价于 `rewrite ^ /lua_rewrite_4 last；`此处看error log是看不到记录的log
- `rewrite ^ /lua_rewrite_3;` 等价于 `ngx.req.set_uri("/lua_rewrite_3", false);`
- `rewrite ^ /lua_rewrite_3 break;` 等价于 `ngx.req.set_uri("/lua_rewrite_3", false);`加if/else判断/break/return
- `rewrite ^ /lua_rewrite_4 last;` 等价于 `ngx.req.set_uri("/lua_rewrite_4", true);`

#### access_by_lua
- `lua.conf`: /lua_access
- `test_access.lua`
- http://192.168.1.110/lua_access?token=123
- http://192.168.1.110/lua_access?token=1234

### Module
- `lua.conf`: /lua_module_hello
- `module_hello.lua`
- `test_module_hello.lua`
- http://192.168.1.110/lua_module_hello

### References
- [第二章 OpenResty(Nginx+Lua)开发入门](https://www.iteye.com/blog/jinnianshilongnian-2186448)
- [第四章 Lua模块开发](https://www.iteye.com/blog/jinnianshilongnian-2187067)
## nginx Lua Web Test

### 流量复制
- 使用如[tcpcopy](https://github.com/session-replay-tools/tcpcopy)引流
- 使用nginx的HttpLuaModule模块中的`ngx.location.capture_multi`进行并发执行来模拟复制
- http://192.168.1.110/test?a=123

### AB测试
- AB测试即多版本测试，有时候开发了新版本需要灰度测试，即让一部分人看到新版，一部分人看到老版，然后通过访问数据决定是否切换到新版。比如可以通过根据区域、用户等信息进行切版本
- 判断规则可以比较多的选择，比如通过尾号；要切30%的流量到新版，可以通过选择第二个数字串尾号为`1,3,5`的切到新版，其余的还停留在老版

#### map
[Module ngx_http_map_module](http://nginx.org/en/docs/http/ngx_http_map_module.html)，通过`ngx.var.ab_key`获取到该数据
- `nginx_desc_page.conf`: /abtestFoo
- http://192.168.1.110/abtestFoo?a=123
- ab -kc 100 -n 100 http://192.168.1.110/abtestFoo?a=123

使用`proxy_pass`到不同版本的服务器上
- `nginx_desc_page.conf`: /abtestBar
- http://192.168.1.110/abtestBar?a=123

#### lua-resty-cookie
[lua-resty-cookie](https://github.com/cloudflare/lua-resty-cookie)
```
cd /usr/servers/lualib/resty
wget https://raw.githubusercontent.com/cloudflare/lua-resty-cookie/master/lib/resty/cookie.lua
```
- `nginx_desc_page.conf`: /abtestCookie
- http://192.168.1.110/abtestCookie?a=123

### 协程
- 个人认为协程是在A运行中发现自己忙则把CPU使用权让出来给B使用，最后A能从中断位置继续执行，本地还是单线程，CPU独占的；因此如果写网络程序需要配合非阻塞I/O来实现
- 通过Lua协程可以并发的调用多个接口，然后谁先执行成功谁先返回，类似于BigPipe模型

#### 串行实现
- `nginx_desc_page.conf`: /serial
- http://192.168.1.110/serial?a=123

#### ngx.location.capture_multi实现
- `nginx_desc_page.conf`: /concurrencyFoo
- http://192.168.1.110/concurrencyFoo?a=123

#### 协程API实现
- `nginx_desc_page.conf`: /concurrencyBar
- http://192.168.1.110/concurrencyBar?a=123
- 使用[ngx.thread.spawn](http://wiki.nginx.org/HttpLuaModule#ngx.thread.spawn)创建一个轻量级线程，然后使用[ngx.thread.wait](http://wiki.nginx.org/HttpLuaModule#ngx.thread.wait)等待该线程的执行成功

### References
- [第八章 流量复制/AB测试/协程](https://www.iteye.com/blog/jinnianshilongnian-2190343)
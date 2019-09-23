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
nohup /usr/servers/ssdb-1.9.4/ssdb-server /usr/servers/templates/ssdb_basic_7770.conf &
nohup /usr/servers/ssdb-1.9.4/ssdb-server /usr/servers/templates/ssdb_basic_7771.conf &
nohup /usr/servers/ssdb-1.9.4/ssdb-server /usr/servers/templates/ssdb_basic_7772.conf &
nohup /usr/servers/ssdb-1.9.4/ssdb-server /usr/servers/templates/ssdb_basic_7773.conf &
```

#### 商品介绍SSDB集群配置
```
vi /usr/servers/templates/ssdb_desc_8880.conf
vi /usr/servers/templates/ssdb_desc_8881.conf
vi /usr/servers/templates/ssdb_desc_8882.conf
vi /usr/servers/templates/ssdb_desc_8883.conf
mkdir -p /usr/data/ssdb_888{0,1,2,3}
nohup /usr/servers/ssdb-1.9.4/ssdb-server /usr/servers/templates/ssdb_desc_8880.conf &
nohup /usr/servers/ssdb-1.9.4/ssdb-server /usr/servers/templates/ssdb_desc_8881.conf &
nohup /usr/servers/ssdb-1.9.4/ssdb-server /usr/servers/templates/ssdb_desc_8882.conf &
nohup /usr/servers/ssdb-1.9.4/ssdb-server /usr/servers/templates/ssdb_desc_8883.conf &
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

#### Test
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



### References
- [第七章 Web开发实战2——商品详情页](https://www.iteye.com/blog/jinnianshilongnian-2188538)
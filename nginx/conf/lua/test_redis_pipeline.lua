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

local redis = require("resty.redis")
--创建实例
local red = redis:new()
--设置超时（毫秒）
red:set_timeout(1000)
--建立连接
local ip = "127.0.0.1"
local port = 6379
local ok, err = red:connect(ip, port)
if not ok then
    ngx.say("connect to redis error : ", err)
    return close_redis(red)
end
--pipeline
red:init_pipeline()
red:set("msg1", "hello1")
red:set("msg2", "hello2")
red:get("msg1")
red:get("msg2")
local respTable, err = red:commit_pipeline()
--得到的数据为空处理
if respTable == ngx.null then
    respTable = {} --比如默认值
end
--结果是按照执行顺序返回的一个table
for i, v in ipairs(respTable) do
    ngx.say("msg : ", v, "<br/>")
end
--function 1
local resp, err = red:eval("return redis.call('get', KEYS[1])", 1, "msg");
ngx.say("eval msg : ", resp, "<br/>")
--function 2
local sha1, err = red:script("load", "return redis.call('get', KEYS[1])");
if not sha1 then
    ngx.say("load script error : ", err)
    return close_redis(red)
end
ngx.say("sha1 : ", sha1, "<br/>")
local resp, err = red:evalsha(sha1, 1, "msg");
ngx.say("sha1 msg : ", resp, "<br/>")
close_redis(red)
local mycache = require("resty_lrucache")
local count = mycache.get("count") or 0
count = count + 1
mycache.set("count", count, 10 * 60 * 60) --10分钟
ngx.say(mycache.get("count"))
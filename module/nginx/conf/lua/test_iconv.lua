local iconv = require("iconv")
local togbk = iconv.new("gbk", "utf-8")
local str, err = togbk:iconv("中文")
ngx.say(str)
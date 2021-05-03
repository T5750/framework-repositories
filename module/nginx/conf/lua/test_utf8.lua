local utf8 = require("utf8")
local str = "abc中文"
ngx.say("len : ", utf8.len(str), "<br/>")
ngx.say("sub : ", utf8.sub(str, 1, 4))
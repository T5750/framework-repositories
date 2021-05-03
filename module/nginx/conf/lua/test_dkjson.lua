local dkjson = require("dkjson")

--lua对象到字符串
local obj = {
    id = 1,
    name = "zhangsan",
    age = nil,
    is_male = false,
    hobby = { "film", "music", "read" }
}

local str = dkjson.encode(obj, { indent = true })
ngx.say(str, "<br/>")

--字符串到lua对象
str = '{"hobby":["film","music","read"],"is_male":false,"name":"zhangsan","id":1,"age":null}'
local obj, pos, err = dkjson.decode(str, 1, nil)

ngx.say(obj.age, "<br/>")
ngx.say(obj.age == nil, "<br/>")
ngx.say(obj.hobby[1], "<br/>")

--循环引用
obj = {
    id = 1
}
obj.obj = obj
--reference cycle
--ngx.say(dkjson.encode(obj), "<br/>")
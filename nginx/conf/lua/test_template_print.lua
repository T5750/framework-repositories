local template = require("resty.template")
--是否缓存解析后的模板，默认true
template.caching(true)
--渲染模板需要的上下文(数据)
local context = { title = "title" }
--渲染模板
template.render("index.html", context)

ngx.say("<br/>")
--编译得到一个lua函数
local func = template.compile("index.html")
--执行函数，得到渲染之后的内容
local content = func(context)
--通过ngx API输出
ngx.say(content)
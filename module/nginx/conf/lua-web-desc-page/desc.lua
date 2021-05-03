--local common = require("item.common")
package.path = package.path..";/usr/servers/templates/lualib/?.lua"
local common = require("common")
local read_redis = common.read_redis
local read_http = common.read_http
local ngx_log = ngx.log
local ngx_ERR = ngx.ERR
local ngx_exit = ngx.exit
local ngx_print = ngx.print
local ngx_re_match = ngx.re.match
local ngx_var = ngx.var
local skuId = ngx_var.skuId
local descKey = "d:" .. skuId .. ":"
local descInfoStr = read_redis("127.0.0.1", 1114, { descKey })
if not descInfoStr then
    ngx_log(ngx_ERR, "redis not found desc info, back to http, skuId : ", skuId)
    descInfoStr = read_http({ type = "desc", skuId = skuId })
end
if not descInfoStr then
    ngx_log(ngx_ERR, "http not found basic info, skuId : ", skuId)
    return ngx_exit(404)
end
ngx_print("showdesc(")
ngx_print(descInfoStr)
ngx_print(")")
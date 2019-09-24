--local common = require("item.common")
package.path = package.path .. ";/usr/servers/templates/lualib/?.lua"
local common = require("common")
local item = require("item_util")
local localCache = require("local_cache")
local read_redis = common.read_redis
local read_http = common.read_http
local cjson = require("cjson")
local cjson_decode = cjson.decode
local ngx_log = ngx.log
local ngx_ERR = ngx.ERR
local ngx_exit = ngx.exit
local ngx_print = ngx.print
local ngx_var = ngx.var
local skuId = ngx_var.skuId
--获取基本信息
local basicInfoKey = "p:" .. skuId .. ":"
local basicInfoStr = read_redis("127.0.0.1", 1112, { basicInfoKey })
if not basicInfoStr then
    ngx_log(ngx_ERR, "redis not found basic info, back to http, skuId : ", skuId)
    basicInfoStr = read_http({ type = "basic", skuId = skuId })
end
if not basicInfoStr then
    ngx_log(ngx_ERR, "http not found basic info, skuId : ", skuId)
    return ngx_exit(404)
end

local basicInfo = cjson_decode(basicInfoStr)
local ps3Id = basicInfo["ps3Id"]
local brandId = basicInfo["brandId"]
--获取其它信息
local breadcrumbKey = "s:" .. ps3Id .. ":"
local brandKey = "b:" .. brandId .. ":"
local breadcrumbStrLocal = localCache.cache_get(breadcrumbKey)
local brandStrLocal = localCache.cache_get(brandKey)
if breadcrumbStrLocal and brandStrLocal then
    basicInfo["breadcrumb"] = cjson_decode(breadcrumbStrLocal)
    basicInfo["brand"] = cjson_decode(brandStrLocal)
else
    --local otherInfo = read_redis("127.0.0.1", 1116, { breadcrumbKey, brandKey }) or {}
    --local breadcrumbStr = otherInfo[1]
    --local brandStr = otherInfo[2]
    local breadcrumbStr = read_redis("127.0.0.1", 1116, { breadcrumbKey })
    local brandStr = read_redis("127.0.0.1", 1116, { brandKey })
    if breadcrumbStr then
        basicInfo["breadcrumb"] = cjson_decode(breadcrumbStr)
        localCache.cache_set(breadcrumbKey, breadcrumbStr)
    end
    if brandStr then
        basicInfo["brand"] = cjson_decode(brandStr)
        localCache.cache_set(brandKey, brandStr)
    end
    if not breadcrumbStr and not brandStr then
        ngx_log(ngx_ERR, "redis not found other info, back to http, skuId : ", brandId)
        local otherInfoStr = read_http({ type = "other", ps3Id = ps3Id, brandId = brandId })
        if not otherInfoStr then
            ngx_log(ngx_ERR, "http not found other info, skuId : ", skuId)
        else
            local otherInfo = cjson_decode(otherInfoStr)
            basicInfo["breadcrumb"] = otherInfo["breadcrumb"]
            basicInfo["brand"] = otherInfo["brand"]
        end
    end
end

local name = basicInfo["name"]
--name to unicode
basicInfo["unicodeName"] = item.utf8_to_unicode(name)
--字符串截取，超长显示...
basicInfo["moreName"] = item.trunc(name, 10)
--初始化各分类的url
item.init_breadcrumb(basicInfo)
--初始化扩展属性
item.init_expand(basicInfo)
--初始化颜色尺码
item.init_color_size(basicInfo)
--添加Last-Modified，用于响应304缓存
ngx.header["Last-Modified"] = ngx.http_time(ngx.now())
local template = require "resty.template"
template.caching(true)
template.render("item.html", basicInfo)
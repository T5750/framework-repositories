local nginx_shared = ngx.shared
--item.jd.com配置的缓存
local local_cache = nginx_shared.item_local_cache
local STATUS_OK = 200
local STATUS_NOT_FOUND = 404
local ttl = 600
local function cache_get(key)
    if not local_cache then
        return nil
    end
    return local_cache:get(key)
end

local function cache_set(key, value)
    if not local_cache then
        return nil
    end
    return local_cache:set(key, value, 10 * 60) --10分钟
end

local function get(ip, port, keys)
    local tables = {}
    local fetchKeys = {}
    local resp = nil
    local status = STATUS_OK
    --如果tables是个map #tables拿不到长度
    local has_value = false
    --先读取本地缓存
    for i, key in ipairs(keys) do
        local value = cache_get(key)
        if value then
            if value == "" then
                value = nil
            end
            tables[key] = value
            has_value = true
        else
            fetchKeys[#fetchKeys + 1] = key
        end
    end
    --如果还有数据没获取 从redis获取
    if #fetchKeys > 0 then
        if #fetchKeys == 1 then
--            status, resp = redis_get(ip, port, fetchKeys[1])
        else
--            status, resp = redis_mget(ip, port, fetchKeys)
        end
        if status == STATUS_OK then
            for i = 1, #fetchKeys do
                local key = fetchKeys[i]
                local value = nil
                if #fetchKeys == 1 then
                    value = resp
                else
--                    value = get_data(resp, i)
                end
                tables[key] = value
                has_value = true
                cache_set(key, value or "", ttl)
            end
        end
    end
    --如果从缓存查到 就认为ok
    if has_value and status == STATUS_NOT_FOUND then
        status = STATUS_OK
    end
    return status, tables
end

local _M = {
    cache_get = cache_get,
    cache_set = cache_set
}
return _M
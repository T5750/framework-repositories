local string_find = string.find
local string_sub = string.sub
local function ltrim(s)
    if not s then
        return s
    end
    local res = s
    local tmp = string_find(res, '%S')
    if not tmp then
        res = ''
    elseif tmp ~= 1 then
        res = string_sub(res, tmp)
    end
    return res
end

local function rtrim(s)
    if not s then
        return s
    end
    local res = s
    local tmp = string_find(res, '%S%s*$')
    if not tmp then
        res = ''
    elseif tmp ~= #res then
        res = string_sub(res, 1, tmp)
    end

    return res
end

local function trim(s)
    if not s then
        return s
    end
    local res1 = ltrim(s)
    local res2 = rtrim(res1)
    return res2
end

ngx.say("trim : ", trim(" abc "), "<br/>")
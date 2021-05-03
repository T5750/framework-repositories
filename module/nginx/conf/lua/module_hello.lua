local count = 0
local function hello()
    count = count + 1
    ngx.say("count : ", count)
end

local _M = {
    hello = hello
}

return _M
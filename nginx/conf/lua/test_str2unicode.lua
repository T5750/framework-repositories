local bit = require("bit")
local bit_band = bit.band
local bit_bor = bit.bor
local bit_lshift = bit.lshift
local string_format = string.format
local string_byte = string.byte
local table_concat = table.concat

local function utf8_to_unicode(str)
    if not str or str == "" or str == ngx.null then
        return nil
    end
    local res, seq, val = {}, 0, nil
    for i = 1, #str do
        local c = string_byte(str, i)
        if seq == 0 then
            if val then
                res[#res + 1] = string_format("%04x", val)
            end

            seq = c < 0x80 and 1 or c < 0xE0 and 2 or c < 0xF0 and 3 or
                    c < 0xF8 and 4 or --c < 0xFC and 5 or c < 0xFE and 6 or
                    0
            if seq == 0 then
                ngx.log(ngx.ERR, 'invalid UTF-8 character sequence' .. ",,," .. tostring(str))
                return str
            end

            val = bit_band(c, 2 ^ (8 - seq) - 1)
        else
            val = bit_bor(bit_lshift(val, 6), bit_band(c, 0x3F))
        end
        seq = seq - 1
    end
    if val then
        res[#res + 1] = string_format("%04x", val)
    end
    if #res == 0 then
        return str
    end
    return "\\u" .. table_concat(res, "\\u")
end

ngx.say("utf8 to unicode : ", utf8_to_unicode("abc中文"), "<br/>")
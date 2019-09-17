package.path = package.path..";/usr/servers/nginx/conf/lua/?.lua"
local module_hello = require("module_hello")
module_hello.hello()
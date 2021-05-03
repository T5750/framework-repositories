local function close_db(db)
    if not db then
        return
    end
    db:close()
end

local mysql = require("resty.mysql")
--创建实例
local db, err = mysql:new()
if not db then
    ngx.say("new mysql error : ", err)
    return
end
--设置超时时间(毫秒)
db:set_timeout(1000)

local props = {
    host = "192.168.1.100",
    port = 3306,
    database = "test",
    user = "root",
    password = "123456"
}

local res, err, errno, sqlstate = db:connect(props)

if not res then
    ngx.say("connect to mysql error : ", err, " , errno : ", errno, " , sqlstate : ", sqlstate)
    return close_db(db)
end

--删除表
local drop_table_sql = "drop table if exists test"
res, err, errno, sqlstate = db:query(drop_table_sql)
if not res then
    ngx.say("drop table error : ", err, " , errno : ", errno, " , sqlstate : ", sqlstate)
    return close_db(db)
end

--创建表
local create_table_sql = "create table test(id int primary key auto_increment, ch varchar(100))"
res, err, errno, sqlstate = db:query(create_table_sql)
if not res then
    ngx.say("create table error : ", err, " , errno : ", errno, " , sqlstate : ", sqlstate)
    return close_db(db)
end

--插入
local insert_sql = "insert into test (ch) values('hello')"
res, err, errno, sqlstate = db:query(insert_sql)
if not res then
    ngx.say("insert error : ", err, " , errno : ", errno, " , sqlstate : ", sqlstate)
    return close_db(db)
end

res, err, errno, sqlstate = db:query(insert_sql)

ngx.say("insert rows : ", res.affected_rows, " , id : ", res.insert_id, "<br/>")

--更新
local update_sql = "update test set ch = 'hello2' where id =" .. res.insert_id
res, err, errno, sqlstate = db:query(update_sql)
if not res then
    ngx.say("update error : ", err, " , errno : ", errno, " , sqlstate : ", sqlstate)
    return close_db(db)
end

ngx.say("update rows : ", res.affected_rows, "<br/>")
--查询
local select_sql = "select id, ch from test"
res, err, errno, sqlstate = db:query(select_sql)
if not res then
    ngx.say("select error : ", err, " , errno : ", errno, " , sqlstate : ", sqlstate)
    return close_db(db)
end

for i, row in ipairs(res) do
    for name, value in pairs(row) do
        ngx.say("select row ", i, " : ", name, " = ", value, "<br/>")
    end
end

ngx.say("<br/>")
--防止sql注入
local ch_param = ngx.req.get_uri_args()["ch"] or ''
--使用ngx.quote_sql_str防止sql注入
local query_sql = "select id, ch from test where ch = " .. ngx.quote_sql_str(ch_param)
res, err, errno, sqlstate = db:query(query_sql)
if not res then
    ngx.say("select error : ", err, " , errno : ", errno, " , sqlstate : ", sqlstate)
    return close_db(db)
end

for i, row in ipairs(res) do
    for name, value in pairs(row) do
        ngx.say("select row ", i, " : ", name, " = ", value, "<br/>")
    end
end

--删除
local delete_sql = "delete from test"
res, err, errno, sqlstate = db:query(delete_sql)
if not res then
    ngx.say("delete error : ", err, " , errno : ", errno, " , sqlstate : ", sqlstate)
    return close_db(db)
end

ngx.say("delete rows : ", res.affected_rows, "<br/>")

close_db(db)
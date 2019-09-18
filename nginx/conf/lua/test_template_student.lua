local template = require("resty.template")

local context = {
    title = "测试",
    name = "张三",
    description = "<script>alert(1);</script>",
    age = 20,
    hobby = { "电影", "音乐", "阅读" },
    score = { "语 文 = 90", "数 学 = 80", "英 语 = 70" },
    score2 = {
        { name = "语文", score = 90 },
        { name = "数学", score = 80 },
        { name = "英语", score = 70 }
    }
}

template.render("student.html", context)
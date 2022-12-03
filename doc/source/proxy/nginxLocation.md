# nginx Location

## location几种匹配模式
- `location = /uri` 精确匹配，不能使用正则且区分字符大小写
- `location ^~ /uri` 前缀匹配，不能使用正则且区分字符大小写
- `location ~` 正则匹配，区分字符大小写
- `location ~*` 正则匹配，不区分字符大小写
- `location /uri` 正常匹配（正常匹配和前缀匹配的差别在于优先级。前缀匹配的优先级高于正常匹配），不能使用正则且区分字符大小写
- `location /` 全匹配

匹配模式优先级：精确匹配 > 前缀匹配 > 正则匹配 > 正常匹配 > 全匹配

## 精确匹配
```
location = /demo {
    rewrite ^ https://t5750.github.io;
}
```
精确匹配不能使用正则

## 前缀匹配
```
location ^~ /demo {
    rewrite ^ https://t5750.github.io;
}
```
以/demo为前缀开头的url都能匹配
- http://192.168.1.110:9090/demo/
- http://192.168.1.110:9090/demo/123
- http://192.168.1.110:9090/demo/123a/bbb
- http://192.168.1.110:9090/demo123
- http://192.168.1.110:9090/demo.aaa

## 正则匹配区分大小写
```
location ~ /[0-9]emo {
    rewrite ^ https://t5750.github.io;
}
```
- http://192.168.1.110:9090/3emo
- http://192.168.1.110:9090/4emo/aaa

## 正则匹配不区分大小写
```
location ~* /[0-9]emo {
    rewrite ^ https://t5750.github.io;
}
```
- http://192.168.1.110:9090/3eMo
- http://192.168.1.110:9090/4emO/aaa

## 正常匹配
```
location /demo {
    rewrite ^ https://t5750.github.io;
}
```
对URI的左半部分做匹配检查，不能使用正则且区分字符大小写

## 全匹配
```
location / {
    rewrite ^ https://t5750.github.io;
}
```

## References
- [Nginx之location匹配规则](https://blog.whsir.com/post-4101.html)
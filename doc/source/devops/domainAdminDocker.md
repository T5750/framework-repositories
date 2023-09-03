# Domain Admin Docker

基于Python + Vue3.js 技术栈实现的域名和SSL证书监测平台

用于解决，不同业务域名SSL证书，申请自不同的平台，到期后不能及时收到通知，导致线上访问异常，被老板责骂的问题

## Demo
- [网页版](https://mouday.github.io/domain-admin-web/)
- [移动端版](https://mouday.github.io/domain-admin-mini/)

## Docker
```sh
docker run \
-v $(pwd)/database:/app/database \
-v $(pwd)/logs:/app/logs \
-p 8000:8000 \
--name domain-admin \
mouday/domain-admin
```
- [http://localhost:8000/](http://localhost:8000/)
- User: admin / 123456

## Runtime Environment
- [Python 3.7.x](https://www.python.org/downloads/)
- [Vue.js](https://github.com/vuejs/vue)
- [Node.js](https://nodejs.org/en/download)

## Screenshots
![](https://raw.githubusercontent.com/mouday/domain-admin/master/image/screencapture.png)

![](https://raw.githubusercontent.com/mouday/domain-admin/master/image/screencapture-mini.png)

## References
- [Domain Admin](https://domain-admin.readthedocs.io/)
- [Domain Admin GitHub](https://github.com/mouday/domain-admin)
- [Domain Admin 安装方式](https://domain-admin.readthedocs.io/zh_CN/latest/manual/install.html)
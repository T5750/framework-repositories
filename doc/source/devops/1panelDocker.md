# 1Panel Docker

1Panel 是新一代的 Linux 服务器运维管理面板

## Demo
[在线体验](https://demo.1panel.cn/)

## Install
```sh
curl -sSL https://resource.fit2cloud.com/1panel/package/quick_start.sh -o quick_start.sh && sudo bash quick_start.sh
```
[http://localhost:8090/](http://localhost:8090/)

## Runtime Environment
- [Go v1.13](https://github.com/golang/go)
- [Vue.js](https://github.com/vuejs/vue)

## Tips

### 是否考虑增加多主机管理？
1Panel 会关注在单机 Linux 的运维管理。

多主机管理推荐使用 JumpServer 堡垒机 来实现。尤其现在 JumpServer 也支持 Web 资产，可以通过Web 可视化连上 1Panel，实现多主机管理。

## Screenshots
![](https://1panel.cn/img/overview.png)

## References
- [1Panel](https://1panel.cn/)
- [1Panel GitHub](https://github.com/1Panel-dev/1Panel)
# KubePi Docker

KubePi [kubəˈpaɪ]，一个现代化的 K8s 面板。

## Docker
```sh
docker run --privileged -d --name kubepi --restart=unless-stopped -p 80:80 1panel/kubepi
```
- [http://localhost:80/](http://localhost:80/)
- User: admin / kubepi

## Runtime Environment
- [Vue.js](https://github.com/vuejs/vue)
- [Go](https://golang.org/)

## Screenshots
![](https://kubeoperator.oss-cn-beijing.aliyuncs.com/kubepi/img/02-dashboard.png)

## References
- [KubePi GitHub](https://github.com/1Panel-dev/KubePi)
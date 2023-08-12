# Halo Docker

Halo [ˈheɪloʊ]，强大易用的开源建站工具

## Demo
- 环境地址：[https://demo.halo.run](https://demo.halo.run)
- 后台地址：[https://demo.halo.run/console](https://demo.halo.run/console)
- 用户名：`demo`
- 密码：`P@ssw0rd123..`

## Docker
```sh
docker run \
  -it -d \
  --name halo \
  -p 8090:8090 \
  -v ~/.halo2:/root/.halo2 \
  halohub/halo:2.8 \
  --halo.security.initializer.superadminusername=admin \
  --halo.security.initializer.superadminpassword=P@88w0rd
```
- [http://localhost:8090/](http://localhost:8090/)
- [http://localhost:8090/console](http://localhost:8090/console)

## Runtime Environment
- [Java 17](https://github.com/openjdk/jdk)
- [Node.js 18 LTS](https://nodejs.org/en/download)
- [pnpm 7](https://pnpm.io/)

## Screenshots
![](https://halo.run/themes/halo-theme-official-v3/assets/images/dashboard.webp)

![](https://halo.run/themes/halo-theme-official-v3/assets/images/plugins.webp)

![](https://halo.run/themes/halo-theme-official-v3/assets/images/theme.webp)

![](https://halo.run/themes/halo-theme-official-v3/assets/images/editor.webp)

## References
- [Halo](https://halo.run/)
- [Halo GitHub](https://github.com/halo-dev/halo)
- [Halo Docker](https://docs.halo.run/getting-started/install/docker)
- [Halo Docker Compose](https://docs.halo.run/getting-started/install/docker-compose)
- [Halo 应用市场](https://halo.run/store/apps)
# JetBrains License Server Docker

License Server (FLS) is a Java-based application to manage JetBrains product licenses within your company network.

## Image

Registry | Image
---|---
[Docker Hub](https://hub.docker.com/r/crazymax/jetbrains-license-server/) | `crazymax/jetbrains-license-server`
[GitHub Container Registry](https://github.com/users/crazy-max/packages/container/package/jetbrains-license-server) | `ghcr.io/crazy-max/jetbrains-license-server`

## Docker
```sh
docker run -d -p 8000:8000 --name jetbrains_license_server \
  -e TZ="Europe/Paris" \
  -e JLS_VIRTUAL_HOSTS=jls.example.com \
  -v $(pwd)/data:/data \
  crazymax/jetbrains-license-server:latest
```

## Tips
### You need admin access to your organization profile for license server registration.
许可证服务器可以提供给至少具有 **50 个有效**的 JetBrains 产品订阅或许可证的团队。 FLS 可以免费使用。

少于 50 个有效订阅的帐户将需要由[管理员](https://sales.jetbrains.com/hc/en-gb/articles/207739159-Permissions-matrix)通过我们的在线[许可证管理门户](https://sales.jetbrains.com/hc/en-gb/articles/207240825-How-do-I-manage-and-distribute-licenses-within-my-organization-)手动分配和撤销许可证给最终用户。

## Runtime Environment
- [Java 11](https://openjdk.java.net/projects/jdk/11/)

## Screenshots
![](https://resources.jetbrains.com.cn/help/img/license_server/FLS-register-server.png)

![](https://resources.jetbrains.com.cn/help/img/license_server/FLS-JBA-signin.png)

![](https://resources.jetbrains.com.cn/help/img/license_server/FLS-registered-success.png)

## References
- [License Server](https://www.jetbrains.com.cn/en-us/help/license_server/getting_started.html)
- [crazy-max/docker-jetbrains-license-server GitHub](https://github.com/crazy-max/docker-jetbrains-license-server)
- [我可以使用 JetBrains 许可证服务器吗？](https://sales.jetbrains.com/hc/zh-cn/articles/207240645-%E6%88%91%E5%8F%AF%E4%BB%A5%E4%BD%BF%E7%94%A8-JetBrains-%E8%AE%B8%E5%8F%AF%E8%AF%81%E6%9C%8D%E5%8A%A1%E5%99%A8%E5%90%97)
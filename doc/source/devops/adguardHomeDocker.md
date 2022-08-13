# AdguardHome Docker

Network-wide ads & trackers blocking DNS server

## Docker
```
docker run --name adguardhome \
    --restart unless-stopped \
    -v adguard_data:/opt/adguardhome/work \
    -v adguard_config:/opt/adguardhome/conf \
    -p 53:53/tcp -p 53:53/udp \
    -p 80:80/tcp -p 443:443/tcp -p 443:443/udp -p 3000:3000/tcp \
    -d adguard/adguardhome
```
- [http://localhost:3000/](http://localhost:3000/)
- [http://localhost:80/](http://localhost:80/)

## Screenshots
![](https://cdn.adtidy.org/public/Adguard/Common/adguard_home.gif)

![](https://adguard.com/img/products/windows/main@2x.png?version=3583)

## References
- [AdguardHome](https://adguard.com/zh_cn/adguard-home/overview.html)
- [AdguardHome GitHub](https://github.com/AdguardTeam/AdguardHome)
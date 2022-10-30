# Docker Networks

## Network
```sh
docker network create --driver bridge --subnet=172.18.0.0/16 --gateway=172.18.0.1 bridge_compose
docker network ls
docker inspect bridge_compose
```

## Docker compose
```sh
docker-compose -f postgres.yml up -d
```

## PostgreSQL
```sh
docker exec -it pgmaster bash
psql -h 172.18.0.101 -U postgres -d pg
\conninfo
```
```sh
docker exec -it pgslave bash
psql -h 172.18.0.100 -U postgres -d pg
\conninfo
```

## CentOS 8
```sh
sudo nmcli connection modify docker0 connection.zone trusted
sudo systemctl stop NetworkManager.service
sudo firewall-cmd --permanent --zone=trusted --change-interface=docker0
sudo systemctl start NetworkManager.service
sudo nmcli connection modify docker0 connection.zone trusted
sudo systemctl restart docker.service
```

## Tips
### Docker pull: server misbehaving
```sh
sudo vi /etc/resolv.conf
nameserver 8.8.8.8
```

## References
- [解决 CentOS 8 下 Docker 容器间通信问题](https://www.ricensoftwares.com.cn/index.php?control=doc&view=detail&id=278)
- [Docker network issue: Server misbehaving](https://stackoverflow.com/questions/28332845/docker-network-issue-server-misbehaving)
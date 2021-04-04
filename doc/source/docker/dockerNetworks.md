# Docker Networks

## Network
```
docker network create --driver bridge --subnet=172.18.0.0/16 --gateway=172.18.0.1 bridge_compose
docker network ls
docker inspect bridge_compose
```

## Docker compose
```
docker-compose -f postgres.yml up -d
```

## PostgreSQL
```
docker exec -it pgmaster bash
psql -h 172.18.0.101 -U postgres -d pg
\conninfo
```
```
docker exec -it pgslave bash
psql -h 172.18.0.100 -U postgres -d pg
\conninfo
```

## CentOS 8
```
sudo nmcli connection modify docker0 connection.zone trusted
sudo systemctl stop NetworkManager.service
sudo firewall-cmd --permanent --zone=trusted --change-interface=docker0
sudo systemctl start NetworkManager.service
sudo nmcli connection modify docker0 connection.zone trusted
sudo systemctl restart docker.service
```

## References
- [解决 CentOS 8 下 Docker 容器间通信问题](https://www.ricensoftwares.com.cn/index.php?control=doc&view=detail&id=278)
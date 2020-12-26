# Docker Networks

## Network
```
docker network create --driver bridge --subnet=172.60.0.0/16 --gateway=172.60.0.1 bridge_db
docker network ls
docker inspect bridge_db
```

## Docker compose
```
docker-compose -f compose-pg.yml up -d
```

## PostgreSQL
```
docker exec -it pgmaster bash
psql -h 172.60.0.101 -U postgres -d pg
\conninfo
```
```
docker exec -it pgslave bash
psql -h 172.60.0.100 -U postgres -d pg
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
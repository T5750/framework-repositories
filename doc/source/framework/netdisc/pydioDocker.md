# Pydio Cells Docker

## Docker
```
docker run -d \
--restart=always \
--name pydio \
-v /opt/pydio:/var/cells \
-p 8080:8080 \
-e CELLS_NO_TLS=1 \
-e CELLS_BIND=0.0.0.0:8080 \
pydio/cells
```

[https://localhost:8080/](https://localhost:8080/)

## Runtime Environment
- [Go v1.13](https://github.com/golang/go)
- [MySQL 5.x](http://www.mysql.com/)

## Screenshots
![](https://pydio.com/sites/default/files/git-importer/admin-guide-cells-v2/images/1_quick_start/yourdata/06-mydata.png)

![](https://pydio.com/sites/default/files/git-importer/admin-guide-cells-v2/images/1_quick_start/yourdata/01-people.png)

![](https://pydio.com/sites/default/files/git-importer/admin-guide-cells-v2/images/3_connecting_your_users/ldap/ldap_2.png)

## References
- [Docker | Pydio](https://pydio.com/en/docs/cells/v2/docker)
- [pydio/cells](https://github.com/pydio/cells)
- [Pydio搭建私有网盘](https://zhuanlan.zhihu.com/p/88631372)
- [[ED] Binding to LDAP](https://pydio.com/en/docs/cells/v2/ed-binding-ldap)
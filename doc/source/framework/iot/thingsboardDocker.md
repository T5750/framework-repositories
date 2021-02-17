# ThingsBoard Docker

```
mkdir -p ~/.mytb-data && sudo chown -R 799:799 ~/.mytb-data
mkdir -p ~/.mytb-logs && sudo chown -R 799:799 ~/.mytb-logs
```

## Installing ThingsBoard using Docker
`tb-postgres-standalone.yml`

- [http://localhost:9090/](http://localhost:9090/)
- System Administrator: sysadmin@thingsboard.org / sysadmin
- Tenant Administrator: tenant@thingsboard.org / tenant
- Customer User: customer@thingsboard.org / customer

## Cluster setup with Docker Compose
1. Pull ThingsBoard CE Images
2. Review the architecture page
3. Clone ThingsBoard CE repository
4. Configure ThingsBoard database
5. Choose ThingsBoard queue service
6. Running

- `.env`
- `tb.yml`
- `docker-compose.postgres.yml`
- `docker-compose.kafka.yml`

### Running
```
./docker-create-log-folders.sh
./docker-install-tb.sh --loadDemo
./docker-start-services.sh
./docker-stop-services.sh
```

### Upgrading
```
$ ./docker-stop-services.sh
$ ./docker-remove-services.sh
$ ./docker-update-service.sh [SERVICE...]
```

## References
- [Installing ThingsBoard using Docker (Linux or Mac OS)](https://thingsboard.io/docs/user-guide/install/docker/)
- [With image thingsboard/tb-postgres:latest, thingsboard_3.1 docker failed to start](https://github.com/thingsboard/thingsboard/issues/3347)
- [Cluster setup with Docker Compose](https://thingsboard.io/docs/user-guide/install/cluster/docker-compose-setup/)
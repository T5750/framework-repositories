# ThingsBoard Docker

```
mkdir -p ~/.mytb-data && sudo chown -R 799:799 ~/.mytb-data
mkdir -p ~/.mytb-logs && sudo chown -R 799:799 ~/.mytb-logs
```

## Docker Compose
`tb-postgres-standalone.yml`

- [http://localhost:9090/](http://localhost:9090/)
- Tenant Administrator: tenant@thingsboard.org / tenant

## References
- [Installing ThingsBoard using Docker (Linux or Mac OS)](https://thingsboard.io/docs/user-guide/install/docker/)
- [With image thingsboard/tb-postgres:latest, thingsboard_3.1 docker failed to start](https://github.com/thingsboard/thingsboard/issues/3347)
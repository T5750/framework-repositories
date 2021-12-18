# ThingsBoard Docker

## What is ThingsBoard?
ThingsBoard is an open-source IoT platform that enables rapid development, management, and scaling of IoT projects. Our goal is to provide the out-of-the-box IoT cloud or on-premises solution that will enable server-side infrastructure for your IoT applications.

```
mkdir -p ~/.mytb-data && sudo chown -R 799:799 ~/.mytb-data
mkdir -p ~/.mytb-logs && sudo chown -R 799:799 ~/.mytb-logs
```

## Installing ThingsBoard using Docker
`tb-postgres-standalone.yml`

- [http://localhost:9090/](http://localhost:9090/)
- **System Administrator**: sysadmin@thingsboard.org / sysadmin
- **Tenant Administrator**: tenant@thingsboard.org / tenant
- **Customer User**: customer@thingsboard.org / customer

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

## ThingsBoard 3.1.1
[release-3.1](https://github.com/thingsboard/thingsboard/tree/release-3.1)

### Installing ThingsBoard using Docker (Windows)
```
docker volume create mytb-data
docker volume create mytb-logs
docker run -it -p 9090:9090 -p 1883:1883 -p 5683:5683/udp -v mytb-data:/data -v ~/mytb-logs:/var/log/thingsboard --name mytb --restart always thingsboard/tb-postgres:3.1.1
```

## ThingsBoard 3.2.2
[release-3.2](https://github.com/thingsboard/thingsboard/tree/release-3.2)

## ThingsBoard 3.3.2
[release-3.3](https://github.com/thingsboard/thingsboard/tree/release-3.3)

## Screenshots
![](https://thingsboard.io/images/helloworld/hello-world-step-1-item-1.png)

![](https://thingsboard.io/images/helloworld/hello-world-step-1-item-2.png)

![](https://thingsboard.io/images/helloworld/hello-world-step-1-item-3.png)

## References
- [Installing ThingsBoard using Docker (Linux or Mac OS)](https://thingsboard.io/docs/user-guide/install/docker/)
- [With image thingsboard/tb-postgres:latest, thingsboard_3.1 docker failed to start](https://github.com/thingsboard/thingsboard/issues/3347)
- [Cluster setup with Docker Compose](https://thingsboard.io/docs/user-guide/install/cluster/docker-compose-setup/)
- [Getting Started with ThingsBoard](https://thingsboard.io/docs/getting-started-guides/helloworld/)
# CAS Note

## CAS Config Server
```
git clone https://github.com/apereo/cas-configserver-overlay.git
git chekcout 5.x
./build.sh bootrun
```

Parameter | Description
---|---
/encrypt | Accepts a POST to encrypt CAS configuration settings.
/decrypt | Accepts a POST to decrypt CAS configuration settings.
/refresh | Accepts a POST and attempts to refresh the internal state of configuration server.
/env | Accepts a GET and describes all configuration sources of the configuration server.
/cas/default | Describes what the configuration server knows about the `default` settings profile.
/cas/native | Describes what the configuration server knows about the `native` settings profile.
/bus/refresh | Reload the configuration of all CAS nodes in the cluster if the cloud bus is turned on.
/bus/env | Sends key/values pairs to update each CAS node if the cloud bus is turned on.

- [http://localhost:8888/casconfigserver/status/configserver](http://localhost:8888/casconfigserver/status/configserver)
- [http://localhost:8888/casconfigserver/cas/native](http://localhost:8888/casconfigserver/cas/native)

```
curl -u casuser:Mellon http://localhost:8888/casconfigserver/cas/native
curl -u casuser:Mellon http://localhost:8888/casconfigserver/env
```

## CAS Management
```
git clone https://github.com/apereo/cas-management-overlay/tree/5.3
./build.sh package
./build.sh run
```
- [https://localhost:8443/cas-management](https://localhost:8443/cas-management)

### Screenshots
![](https://user-images.githubusercontent.com/1205228/31142213-73581fc0-a886-11e7-9fed-61e7e89b1021.png)

## References
- [cas-configserver-overlay](https://github.com/apereo/cas-configserver-overlay/tree/5.x)
- [Configuration Server](https://apereo.github.io/cas/5.3.x/installation/Configuration-Server-Management.html)
- [cas-management-overlay](https://github.com/apereo/cas-management-overlay/tree/5.3)
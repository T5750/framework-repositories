# CAS Docker

## CAS 5.3.x
### Docker Compose
`cas.yml`

- [http://localhost:8080/](http://localhost:8080/)
- [https://localhost:8443/](https://localhost:8443/)

### Dockerfile
- `sso/Dockerfile`
- `sso-server.yml`

```
git clone https://github.com/apereo/cas-overlay-template.git
git chekcout 5.3
./build.sh package

#replace etc/cas/thekeystore
docker build -t sso-server .
```
User: casuser / Mellon

## CAS 6.4.x
### Docker
```
docker run -it --rm --name=cas -p 8443:8443 -v /etc/cas/thekeystore:/etc/cas/thekeystore apereo/cas:6.4.0
```
- [https://localhost:8443/](https://localhost:8443/)
- User: casuser / Mellon

## CAS Protocol
### Web flow diagram
![cas_flow_diagram](https://apereo.github.io/cas/5.3.x/images/cas_flow_diagram.png)

### Proxy web flow diagram
![cas_proxy_flow_diagram](https://apereo.github.io/cas/5.3.x/images/cas_proxy_flow_diagram.jpg)

## References
- [cas-overlay-template/Dockerfile](https://github.com/apereo/cas-overlay-template/blob/master/Dockerfile)
- [apereo/cas Docker](https://hub.docker.com/r/apereo/cas)
- [CAS Protocol](https://apereo.github.io/cas/5.3.x/protocol/CAS-Protocol.html)
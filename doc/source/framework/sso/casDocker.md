# CAS Docker

## Docker Compose
`cas.yml`

- [http://localhost:8080/](http://localhost:8080/)
- [https://localhost:8443/](https://localhost:8443/)

## Dockerfile
- `sso/Dockerfile`
- `sso-server.yml`

```
git clone https://github.com/apereo/cas-overlay-template.git
git chekcout 5.3
./build.sh package

docker build -t sso-server .
```

## References
- [cas-overlay-template/Dockerfile](https://github.com/apereo/cas-overlay-template/blob/master/Dockerfile)
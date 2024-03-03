# Etherpad Docker

Etherpad is a highly customizable open source online editor providing collaborative editing in really real-time.

## Docker
```sh
docker run -d --name etherpad -p 9001:9001 --restart always etherpad/etherpad
```
[http://localhost:9001/](http://localhost:9001/)

### Examples
Use a Postgres database, no admin user enabled:
```sh
docker run -d \
    --name etherpad         \
    -p 9001:9001            \
    -e 'DB_TYPE=postgres'   \
    -e 'DB_HOST=db.local'   \
    -e 'DB_PORT=4321'       \
    -e 'DB_NAME=etherpad'   \
    -e 'DB_USER=dbusername' \
    -e 'DB_PASS=mypassword' \
    etherpad/etherpad
```
Run enabling the administrative user admin:
```sh
docker run -d \
    --name etherpad \
    -p 9001:9001 \
    -e 'ADMIN_PASSWORD=supersecret' \
    etherpad/etherpad
```
Run a test instance running DirtyDB on a persistent volume:
```sh
docker run -d \
    -v etherpad_data:/opt/etherpad-lite/var \
    -p 9001:9001 \
    etherpad/etherpad
```

## Runtime Environment
- [Node.js](https://nodejs.org/en/download)
- [TypeScript](https://www.typescriptlang.org/)

## Screenshots
![](https://etherpad.org/assets/etherpad_demo-D8tyyg5C.gif)

![](https://etherpad.org/assets/etherpad_full_features-C0aKyHXN.png)

## References
- [Etherpad](https://etherpad.org/)
- [Etherpad GitHub](https://github.com/ether/etherpad-lite)
- [Etherpad Docker](https://github.com/ether/etherpad-lite/blob/develop/doc/docker.adoc)
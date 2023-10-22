# Azkaban Docker

Open-source Workflow Manager

Azkaban is a batch workflow job scheduler created at LinkedIn to run Hadoop jobs. Azkaban resolves the ordering through job dependencies and provides an easy to use web user interface to maintain and track your workflows.

## Build
Pick a release from [the release page](https://github.com/azkaban/azkaban/releases)
```sh
git checkout 3.30.1
./gradlew clean build
```

## Installing the Solo Server
```sh
git clone https://github.com/azkaban/azkaban.git
cd azkaban; ./gradlew build installDist
cd azkaban-solo-server/build/install/azkaban-solo-server; bin/start-solo.sh
```
```sh
bin/shutdown-solo.sh
```

## Installing Azkaban Executor Server
```sh
cd azkaban-exec-server/build/install/azkaban-exec-server
./bin/start-exec.sh
```

## Installing Azkaban Web Server
```sh
cd azkaban-web-server/build/install/azkaban-web-server
./bin/start-web.sh
```
- [http://localhost:8081/](http://localhost:8081/)
- User: azkaban / azkaban

## Docker Compose
```sh
git clone https://github.com/puckel/docker-azkaban.git
docker-compose up -d
```

## Screenshots
![](https://azkaban.github.io/img/app.png)

## References
- [Azkaban](https://azkaban.github.io/)
- [Azkaban GitHub](https://github.com/azkaban/azkaban)
- [Azkaban Getting Started](https://azkaban.readthedocs.io/en/latest/getStarted.html)
- [puckel/docker-azkaban](https://github.com/puckel/docker-azkaban)
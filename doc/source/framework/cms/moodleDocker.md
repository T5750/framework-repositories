# Moodle Docker

The place to get support, ask and answer questions and contribute to the open source learning platform, Moodle LMS.

## Demo
[Demo](https://moodle.org/demo)

## Docker
#### Step 1: Create a network
```sh
docker network create moodle-network
```

#### Step 2: Create a volume for MariaDB persistence and create a MariaDB container
```sh
docker volume create --name mariadb_data
docker run -d --name mariadb \
  --env ALLOW_EMPTY_PASSWORD=yes \
  --env MARIADB_USER=bn_moodle \
  --env MARIADB_PASSWORD=bitnami \
  --env MARIADB_DATABASE=bitnami_moodle \
  --network moodle-network \
  --volume mariadb_data:/bitnami/mariadb \
  bitnami/mariadb:latest
```

#### Step 3: Create volumes for Moodle&trade; persistence and launch the container
```sh
docker volume create --name moodle_data
docker run -d --name moodle \
  -p 8080:8080 -p 8443:8443 \
  --env ALLOW_EMPTY_PASSWORD=yes \
  --env MOODLE_DATABASE_USER=bn_moodle \
  --env MOODLE_DATABASE_PASSWORD=bitnami \
  --env MOODLE_DATABASE_NAME=bitnami_moodle \
  --network moodle-network \
  --volume moodle_data:/bitnami/moodle \
  bitnami/moodle:latest
```
- [http://localhost:8080/](http://localhost:8080/)
- User: user / bitnami

## Docker Compose
```sh
curl -sSL https://raw.githubusercontent.com/bitnami/containers/main/bitnami/moodle/docker-compose.yml > docker-compose.yml
docker-compose up -d
```

## Runtime Environment
- [PHP](https://www.php.net/downloads)
- [MariaDB](https://mariadb.org/download/)

## Screenshots
![](https://docs.moodle.org/402/en/images_en/thumb/3/30/Moodle_Modern_Interface2_March_2017.png/1148px-Moodle_Modern_Interface2_March_2017.png)

![](https://docs.moodle.org/402/en/images_en/thumb/8/8f/MyCourses4%2B.png/1148px-MyCourses4%2B.png)

![](https://docs.moodle.org/402/en/images_en/thumb/7/74/dashboard4%2B.png/1148px-dashboard4%2B.png)

![](https://docs.moodle.org/402/en/images_en/thumb/7/74/FeaturesForum4.png/1148px-FeaturesForum4.png)

## References
- [Moodle](https://moodle.org/)
- [Moodle GitHub](https://github.com/moodle/moodle)
- [bitnami/moodle Docker](https://github.com/bitnami/containers/tree/main/bitnami/moodle)
- [Moodle Features](https://docs.moodle.org/402/en/Features)
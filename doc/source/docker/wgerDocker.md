# wger Docker

Self hosted FLOSS fitness/workout, nutrition and weight tracker

## Docker Compose
```sh
git clone https://github.com/wger-project/docker
docker compose up -d
```
- [http://localhost:80/](http://localhost:80/)
- User: admin / adminadmin

## Backup
```sh
# Stop all other containers so the db is not changed while you export it
docker compose stop web nginx cache celery_worker celery_beat
docker compose exec db pg_dumpall --clean --username wger > backup.sql
docker compose start

# When you need to restore it
docker compose stop
docker volume remove docker_postgres-data
docker compose up db
cat backup.sql | docker compose exec -T db psql --username wger --dbname wger
docker compose up
```

## Runtime Environment
- [Python 3.10.x](https://www.python.org/downloads/)

## Screenshots
![](https://wger.de/static/images/screens-1.avif)

![](https://wger.de/static/images/screens-2.avif)

![](https://wger.de/static/images/screens-3.avif)

## References
- [wger](https://wger.de/)
- [wger GitHub](https://github.com/wger-project/wger)
- [wger Docker](https://wger.readthedocs.io/en/latest/production/docker.html)
# Redmine Docker

Redmine is a free and open source, web-based project management and issue tracking tool. It allows users to manage multiple projects and associated subprojects. It features per project wikis and forums, time tracking, and flexible role based access control. It includes a calendar and Gantt charts to aid visual representation of projects and their deadlines. Redmine integrates with various version control systems and includes a repository browser and diff viewer.

## Run Redmine with SQLite3
```
$ docker run -d --name some-redmine redmine
```

## Run Redmine with a Database Container
1.start a database container
- PostgreSQL
```
$ docker run -d --name some-postgres --network some-network -e POSTGRES_PASSWORD=secret -e POSTGRES_USER=redmine postgres
```
- MySQL (replace `-e REDMINE_DB_POSTGRES=some-postgres` with `-e REDMINE_DB_MYSQL=some-mysql` when running Redmine)
```
$ docker run -d --name some-mysql --network some-network -e MYSQL_USER=redmine -e MYSQL_PASSWORD=secret -e MYSQL_DATABASE=redmine -e MYSQL_RANDOM_ROOT_PASSWORD=1 mysql:5.7
```
2.start redmine
```
$ docker run -d --name some-redmine --network some-network -e REDMINE_DB_POSTGRES=some-postgres -e REDMINE_DB_USERNAME=redmine -e REDMINE_DB_PASSWORD=secret redmine
```

## Docker Compose
`redmine.yml`

- [http://localhost:8080/](http://localhost:8080/)
- User: admin / admin

## Screenshots
![](https://www.redmine.org/attachments/download/19137/redmine-login.png)

![](https://www.redmine.org/attachments/download/19564/redmine-project-overview.png)

![](https://www.redmine.org/attachments/download/2463/projects_redmine_activity.png)

## References
- [Redmine Docker](https://hub.docker.com/_/redmine/)
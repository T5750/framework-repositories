# Jira Docker

Jira Software is a software development tool used by agile teams.

## Docker
```
docker volume create --name jiraVolume
docker run -v jiraVolume:/var/atlassian/application-data/jira --name="jira" -d -p 8080:8080 atlassian/jira-software
```
[http://localhost:8080/](http://localhost:8080/)

## Configuring Jira
### Memory / Heap Size
- `JVM_MINIMUM_MEMORY` (default: 384m)
- `JVM_MAXIMUM_MEMORY` (default: 768m)
- `JVM_RESERVED_CODE_CACHE_SIZE` (default: 512m)

### Reverse Proxy Settings
- `ATL_PROXY_NAME` (default: NONE)
- `ATL_PROXY_PORT` (default: NONE)
- `ATL_TOMCAT_PORT` (default: 8080)
- `ATL_TOMCAT_SCHEME` (default: http)
- `ATL_TOMCAT_SECURE` (default: false)
- `ATL_TOMCAT_CONTEXTPATH` (default: NONE)
- `ATL_TOMCAT_MGMT_PORT` (default: 8005)
- `ATL_TOMCAT_MAXTHREADS` (default: 100)
- `ATL_TOMCAT_MINSPARETHREADS` (default: 10)
- `ATL_TOMCAT_CONNECTIONTIMEOUT` (default: 20000)
- `ATL_TOMCAT_ENABLELOOKUPS` (default: false)
- `ATL_TOMCAT_PROTOCOL` (default: HTTP/1.1)
- `ATL_TOMCAT_ACCEPTCOUNT` (default: 10)
- `ATL_TOMCAT_MAXHTTPHEADERSIZE` (default: 8192)

### JVM configuration
- `JVM_SUPPORT_RECOMMENDED_ARGS`

### Jira-specific settings
- `ATL_AUTOLOGIN_COOKIE_AGE` (default: 1209600; two weeks, in seconds)

### Database configuration
- `ATL_JDBC_URL`
- `ATL_JDBC_USER`
- `ATL_JDBC_PASSWORD`
- `ATL_DB_DRIVER`
    * `com.microsoft.sqlserver.jdbc.SQLServerDriver`
    * `com.mysql.jdbc.Driver`
    * `oracle.jdbc.OracleDriver`
    * `org.postgresql.Driver`
- `ATL_DB_TYPE`
- `ATL_DB_SCHEMA_NAME`

## Screenshots
![](https://wac-cdn.atlassian.com/dam/jcr:69814c14-1db0-4013-9342-8a05e851d169/Screen-scrum%20board.png?cdnVersion=331)

![](https://wac-cdn.atlassian.com/dam/jcr:dc544fa5-97a9-4c2c-9d43-88126c776981/Screen-roadmaps.png?cdnVersion=331)

![](https://wac-cdn.atlassian.com/dam/jcr:be598d14-ddd1-46b5-a0e4-691353253b10/Screen-insights.png?cdnVersion=331)

![](https://wac-cdn.atlassian.com/dam/jcr:1f1ba8c7-0869-4aaa-9f21-7f3da8847f59/screen-jira-see-code.png?cdnVersion=331)

![](https://wac-cdn.atlassian.com/dam/jcr:731a54d8-14a3-468b-a581-accfa0c535d0/screen-dev%20status%20in%20issue.png?cdnVersion=331)

![](https://wac-cdn.atlassian.com/dam/jcr:5e0cf3f0-2297-43a3-9fec-460aabf0115f/screen-advanced%20reporting.png?cdnVersion=331)

## References
- [Jira Docker](https://hub.docker.com/r/atlassian/jira-software)
- [Jira Software features](https://www.atlassian.com/software/jira/features)
- [Jira 定价](https://www.atlassian.com/zh/software/jira/pricing)
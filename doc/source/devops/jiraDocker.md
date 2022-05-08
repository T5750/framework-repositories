# Jira Docker

Jira Software is a software development tool used by agile teams.

## Docker
```
docker volume create --name jiraVolume
docker run -v jiraVolume:/var/atlassian/application-data/jira --name="jira" -d -p 8080:8080 atlassian/jira-software
```
[http://localhost:8080/](http://localhost:8080/)

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
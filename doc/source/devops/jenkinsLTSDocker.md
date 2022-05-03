# Jenkins LTS Docker

The leading open source automation server 

## Docker
```
mkdir $PWD/jenkins
chown -R 1000:1000 $PWD/jenkins
docker run -d --name jenkins -p 8080:8080 -p 50000:50000 -v $PWD/jenkins:/var/jenkins_home jenkins/jenkins:lts-jdk11
```

## Docker Compose
`jenkins.yml`

[http://localhost:8080/](http://localhost:8080/)

## Screenshots
![](https://www.jenkins.io/doc/book/resources/node/credentials-1.png)

![](https://www.jenkins.io/doc/book/resources/jmeter/jmeter-13.png)

## References
- [Jenkins](https://www.jenkins.io/)
- [Jenkins GitHub](https://github.com/jenkinsci/jenkins)
- [Jenkins LTS Docker](https://hub.docker.com/r/jenkins/jenkins)
# Jenkins Docker

## Docker
```
docker run --name jenkins -p 8080:8080 -p 50000:50000 -v $PWD/jenkins:/var/jenkins_home jenkins
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
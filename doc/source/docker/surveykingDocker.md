# SurveyKing Docker

卷王问卷考试系统

## Demo
[查看演示(开源)](https://s.surveyking.cn/)

## Docker
```sh
docker run -d --name surveyking -p 1991:1991 surveyking/surveyking
docker run -d --name surveyking -p 1991:1991 -v /my/logs:/files -v /my/logs:/logs surveyking/surveyking
```
- [http://localhost:1991/](http://localhost:1991/)
- User: admin / 123456

## Runtime Environment
- [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

## Screenshots
![](https://surveyking.cn/static/landing-page/hero-light.png)

![](https://gitee.com/surveyking/surveyking/raw/master/docs/images/exam-pc-prev.jpg)

![](https://gitee.com/surveyking/surveyking/raw/master/docs/images/exam-mb-preview.jpeg)

## References
- [SurveyKing](https://surveyking.cn/)
- [SurveyKing GitHub](https://github.com/javahuang/SurveyKing)
- [SurveyKing 如何部署](https://surveyking.cn/open-source/deploy)
- [SurveyKing 使用手册](https://surveyking.cn/help/quickstart)
# Zeppelin Docker

Apache Zeppelin: Web-based notebook that enables data-driven, interactive data analytics and collaborative documents with SQL, Scala, Python, R and more.

## Docker
```
docker run -p 8080:8080 --rm --name zeppelin apache/zeppelin:0.10.0
```
To persist `logs` and `notebook` directories, use the [volume](https://docs.docker.com/engine/reference/commandline/run/#mount-volume--v-read-only) option for docker container.
```
docker run -u $(id -u) -p 8080:8080 --rm -v $PWD/logs:/logs -v $PWD/notebook:/notebook \
-e ZEPPELIN_LOG_DIR='/logs' -e ZEPPELIN_NOTEBOOK_DIR='/notebook' \
--name zeppelin apache/zeppelin:0.10.0
```
`-u $(id -u)` is to make sure you have the permission to write logs and notebooks.

For many interpreters, they require other dependencies, e.g. Spark interpreter requires Spark binary distribution and Flink interpreter requires Flink binary distribution. You can also mount them via docker volumn. e.g.
```
docker run -u $(id -u) -p 8080:8080 --rm -v /mnt/disk1/notebook:/notebook \
-v /usr/lib/spark-current:/opt/spark -v /mnt/disk1/flink-1.12.2:/opt/flink -e FLINK_HOME=/opt/flink  \
-e SPARK_HOME=/opt/spark  -e ZEPPELIN_NOTEBOOK_DIR='/notebook' --name zeppelin apache/zeppelin:0.10.0
```
[http://localhost:8080/](http://localhost:8080/)

## Create new note
Shell Note
```
%sh
date
```

## Screenshots
![](https://zeppelin.apache.org/docs/0.10.0/assets/themes/zeppelin/img/ui-img/homepage.png)

![](https://zeppelin.apache.org/docs/0.10.0/assets/themes/zeppelin/img/ui-img/interpreter_menu.png)

![](https://zeppelin.apache.org/assets/themes/zeppelin/img/notebook.png)

![](https://zeppelin.apache.org/assets/themes/zeppelin/img/screenshots/pivot.png)

## References
- [Install Zeppelin](https://zeppelin.apache.org/docs/0.10.0/quickstart/install.html)
- [Explore Apache Zeppelin UI](https://zeppelin.apache.org/docs/0.10.0/quickstart/explore_ui.html)
- [apache/zeppelin](https://github.com/apache/zeppelin)
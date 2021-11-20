# MinIO Console Docker

Management UI for MinIO and MinIO operator

## Docker
```
docker run -i -t -d -p 9090:9000 -p 9001:9001 --name minioconsole \
-v $PWD/data:/data \
-e MINIO_ROOT_USER=minioadmin \
-e MINIO_ROOT_PASSWORD=minioadmin \
-d minio/minio server /data --console-address ":9001"
```
[http://localhost:9001/](http://localhost:9001/)

## Screenshots
![](https://raw.githubusercontent.com/minio/minio/master/docs/screenshots/pic1.png)

![](https://raw.githubusercontent.com/minio/minio/master/docs/screenshots/pic2.png)

## References
- [MinIO Console](https://github.com/minio/console)
- [minio/console](https://hub.docker.com/r/minio/console)
- [MinIO Console](https://docs.min.io/minio/baremetal/console/minio-console.html)
# MinIO Console Docker

Management UI for MinIO and MinIO operator

## Demo
- [MinIO Console Demo](https://play.min.io/)
- access_key: `Q3AM3UQ867SPQQA43P2F`
- secret_key: `zuf+tfteSlswRu7BJ86wekitnifILbZam1KYY3TG`

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
![](https://docs.min.io/minio/baremetal/_images/console-dashboard1.png)

![](https://docs.min.io/minio/baremetal/_images/console-object-browser1.png)

![](https://docs.min.io/minio/baremetal/_images/console-service-accounts1.png)

## References
- [MinIO Console GitHub](https://github.com/minio/console)
- [MinIO Console Docker](https://hub.docker.com/r/minio/console)
- [MinIO Console](https://docs.min.io/minio/baremetal/console/minio-console.html)
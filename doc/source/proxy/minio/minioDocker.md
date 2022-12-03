# MinIO Docker

High Performance, Kubernetes Native Object Storage

MinIO is a High Performance Object Storage released under Apache License v2.0. It is API compatible with Amazon S3 cloud storage service. Use MinIO to build high performance infrastructure for machine learning, analytics and application data workloads.

## Docker Compose
- `nginx.conf`
- `minio.yaml`

[http://localhost:9000/](http://localhost:9000/)

### 永久的访问路径
- Edit policy -> Prefix: `public` -> Add
- http://localhost:9000/bucket/public/filename

## Architecture
![](http://www.minio.org.cn/static/picture/architecture_diagram.svg)

![](https://raw.githubusercontent.com/minio/minio/master/docs/screenshots/Architecture-diagram_distributed_nm.png)

## Screenshots
![](https://d1q6f0aelx0por.cloudfront.net/screenshots/689ef571-9a3d-43a2-a5f0-eade262052d1-minio-browser.png)

## References
- [使用Docker Compose部署MinIO](https://docs.min.io/cn/deploy-minio-on-docker-compose.html)
- [MinIO GitHub](https://github.com/minio/minio)
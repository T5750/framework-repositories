# Rclone Docker

Rclone is a command-line program to manage files on cloud storage. It is a feature-rich alternative to cloud vendors' web storage interfaces.

## Docker
```sh
docker run --rm rclone/rclone version
# config on host at ~/.config/rclone/rclone.conf
# data on host at ~/data
docker run --rm \
    --volume ~/.config/rclone:/config/rclone \
    --volume ~/data:/data:shared \
    --user $(id -u):$(id -g) \
    rclone/rclone \
    listremotes
```

### MinIO
`vi rclone.conf`
```
[minio]
type = s3
provider = Minio
env_auth = false
access_key_id = minio
secret_access_key = minio123
endpoint = http://localhost:9000/
```

```sh
docker run --rm \
    --volume ~/.config/rclone:/config/rclone \
    --volume ~/data:/data:shared \
    --user $(id -u):$(id -g) \
    rclone/rclone \
    copy minio:/bucket /data
# rclone copy /path/to/files minio:bucket
# rclone sync /path/to/files minio:bucket
```

## References
- [Rclone](https://rclone.org/)
- [Rclone GitHub](https://github.com/rclone/rclone)
- [Rclone Install](https://rclone.org/install/)
- [Rclone Minio Config](https://rclone.org/s3/#minio)
- [Manage Your Cloud Object Storage Data with the MinIO Client and rclone](https://www.ibm.com/cloud/blog/manage-your-cloud-object-storage-data-with-the-minio-client-and-rclone)
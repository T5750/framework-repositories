# MinIO Client Docker

MinIO Client (mc) provides a modern alternative to UNIX commands like ls, cat, cp, mirror, diff, find etc. It supports filesystems and Amazon S3 compatible cloud storage service (AWS Signature v2 and v4).
```
alias       set, remove and list aliases in configuration file
ls          list buckets and objects
mb          make a bucket
rb          remove a bucket
cp          copy objects
mirror      synchronize object(s) to a remote site
cat         display object contents
head        display first 'n' lines of an object
pipe        stream STDIN to an object
share       generate URL for temporary access to an object
find        search for objects
sql         run sql queries on objects
stat        show object metadata
mv          move objects
tree        list buckets and objects in a tree format
du          summarize disk usage recursively
retention   set retention for object(s)
legalhold   set legal hold for object(s)
diff        list differences in object name, size, and date between two buckets
rm          remove objects
encrypt    manage bucket encryption config
event       manage object notifications
watch       listen for object notification events
undo        undo PUT/DELETE operations
policy      manage anonymous access to buckets and objects
tag         manage tags for bucket(s) and object(s)
ilm         manage bucket lifecycle
version     manage bucket versioning
replicate   configure server side bucket replication
admin       manage MinIO servers
update      update mc to latest release
```

## Docker
```
docker run minio/mc ls play
```
**Note**: Above examples run `mc` against MinIO [play environment](https://hub.docker.com/r/minio/mc#test-your-setup) by default. To run `mc` against other S3 compatible servers, start the container this way:
```
docker run -it --entrypoint=/bin/sh minio/mc
```

## Example - MinIO Cloud Storage
MinIO server displays URL, access and secret keys.
```
mc alias set minio http://192.168.1.51 BKIKJAA5BMMU2RHO6IBB V7f1CwQqAcwo80UEIJEjc5gVQUSSx5ohQ9GSrr12
```

## Test Your Setup
```
mc ls play
mc mb play/mybucket
mc cp myobject.txt play/mybucket
```

## References
- [minio/mc Docker](https://hub.docker.com/r/minio/mc)
- [MinIO Client](https://github.com/minio/mc)
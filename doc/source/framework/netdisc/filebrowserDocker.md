# File Browser Docker

Web File Browser

## Docker
```sh
docker run -d --name filebrowser -p 8080:80 filebrowser/filebrowser
docker run \
    -v /path/to/root:/srv \
    -v /path/filebrowser.db:/database.db \
    -v /path/.filebrowser.json:/.filebrowser.json \
    -u $(id -u):$(id -g) \
    -p 8080:80 \
    filebrowser/filebrowser
```
- [http://localhost:8080/](http://localhost:8080/)
- User: admin / admin

## Runtime Environment
- [Go v1.13](https://github.com/golang/go)

## Screenshots
![](https://user-images.githubusercontent.com/5447088/50716739-ebd26700-107a-11e9-9817-14230c53efd2.gif)

![](https://3149836655-files.gitbook.io/~/files/v0/b/gitbook-x-prod.appspot.com/o/spaces%2F-M8KDxOujDoPpJyJJ5_i%2Fuploads%2Fgit-blob-b6cab252432e211dec693fc77d036c15259a8087%2F1.PNG?alt=media)

![](https://3149836655-files.gitbook.io/~/files/v0/b/gitbook-x-prod.appspot.com/o/spaces%2F-M8KDxOujDoPpJyJJ5_i%2Fuploads%2Fgit-blob-9390768b0cbb83b1e7da55c0ae13ecd2d8fcb114%2F2.PNG?alt=media)

## References
- [File Browser Docker](https://filebrowser.org/installation)
- [File Browser GitHub](https://github.com/filebrowser/filebrowser)
- [File Browser Features](https://filebrowser.org/features)
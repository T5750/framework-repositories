# ZooNavigator Docker

Web-based ZooKeeper UI / editor / browser

## Docker
```sh
docker run -d \
  -p 9000:9000 \
  -e HTTP_PORT=9000 \
  --name zoonavigator \
  --restart unless-stopped \
  elkozmon/zoonavigator
```
[http://localhost:9000/](http://localhost:9000/)

## Screenshots
![](https://zoonavigator.elkozmon.com/en/stable/_static/images/screenshots/connect-form.png)

![](https://zoonavigator.elkozmon.com/en/stable/_static/images/screenshots/znode-data-editor.png)

![](https://zoonavigator.elkozmon.com/en/stable/_images/znode-acl-editor.png)

![](https://zoonavigator.elkozmon.com/en/stable/_images/create-znode.png)

![](https://zoonavigator.elkozmon.com/en/stable/_images/import-znodes.png)

![](https://zoonavigator.elkozmon.com/en/stable/_images/znode-regex-search.png)

## References
- [ZooNavigator](https://zoonavigator.elkozmon.com/en/stable/)
- [ZooNavigator GitHub](https://github.com/elkozmon/zoonavigator)
- [ZooNavigator Docker](https://hub.docker.com/r/elkozmon/zoonavigator)
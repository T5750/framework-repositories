# Hue Docker

Query. Explore. Share.

Hue is an open source SQL Assistant for Databases & Data Warehouses

## Demo
[Demo](http://demo.gethue.com/)

## Docker
```sh
docker run -it -p 8888:8888 gethue/hue
docker run -it -p 8888:8888 -v $PWD/hue.ini:/usr/share/hue/desktop/conf/z-hue.ini gethue/hue
```
[http://localhost:8888/](http://localhost:8888/)

## Screenshots
![](https://cdn.gethue.com/uploads/2019/08/hue_4.5.png)

![](https://cdn.gethue.com/uploads/2018/04/blog_top_search_.png)

## References
- [Hue](https://gethue.com/)
- [Hue GitHub](https://github.com/cloudera/hue)
- [Hue Docker](https://gethue.com/hue-in-docker/)
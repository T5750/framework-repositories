# Label Studio Docker

Label Studio is a multi-type data labeling and annotation tool with standardized output format

## Docker
```sh
docker run -it --name label-studio -p 8080:8080 -v `pwd`/mydata:/label-studio/data heartexlabs/label-studio
```
You can find all the generated assets, including SQLite3 database storage `label_studio.sqlite3` and uploaded files, in the `./mydata` directory.

[http://localhost:8080/](http://localhost:8080/)

## Docker Compose
- Label Studio
- [Nginx](https://www.nginx.com/) - proxy web server used to load various static data, including uploaded audio, images, etc.
- [PostgreSQL](https://www.postgresql.org/) - production-ready database that replaces less performant SQLite3.

To start using the app from `http://localhost` run this command:
```bash
docker-compose up
```

## Install locally with pip
```bash
# Requires Python >=3.7 <=3.9
pip install label-studio
# Start the server at http://localhost:8080
label-studio
```

## Install locally with Anaconda
```bash
conda create --name label-studio
conda activate label-studio
pip install label-studio
```

## Screenshots
![Gif of Label Studio annotating different types of data](https://raw.githubusercontent.com/heartexlabs/label-studio/master/images/annotation_examples.gif)

![](https://labelstud.io/images/ls-modules-scheme.png)

![](https://labelstud.io/_astro/images-tab.64279c16_ZaBSvC.avif)

![](https://labelstud.io/_astro/video-tab.0ad16d1f_ZIpzuy.avif)

## Label Studio ML backend
Configs and boilerplates for Label Studio's Machine Learning backend

## References
- [Label Studio](https://labelstud.io/)
- [Label Studio GitHub](https://github.com/heartexlabs/label-studio)
- [Label Studio Docker](https://hub.docker.com/r/heartexlabs/label-studio)
- [Get started with Label Studio](https://labelstud.io/guide/index.html)
- [Label Studio ML backend GitHub](https://github.com/heartexlabs/label-studio-ml-backend)
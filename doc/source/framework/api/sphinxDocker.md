# Sphinx Docker

Sphinx is a tool that makes it easy to create intelligent and beautiful documentation, written by Georg Brandl and licensed under the BSD license.

## Usage
Create a Sphinx project:
```
$ docker run -it --rm -v /path/to/document:/docs sphinxdoc/sphinx sphinx-quickstart
```
Build HTML document:
```
$ docker run --rm -v /path/to/document:/docs sphinxdoc/sphinx make html
```
Build EPUB document:
```
$ docker run --rm -v /path/to/document:/docs sphinxdoc/sphinx make epub
```
Build PDF document:
```
$ docker run --rm -v /path/to/document:/docs sphinxdoc/sphinx-latexpdf make latexpdf
```

## Docker
```
docker run -it --rm -v $PWD/sphinx:/docs sphinxdoc/sphinx sphinx-quickstart
docker run --rm -v $PWD/sphinx:/docs sphinxdoc/sphinx make html
```

## Tips
If you would like to install dependencies, use `sphinxdoc/sphinx` as a base image:
```
# in your Dockerfile
FROM sphinxdoc/sphinx
WORKDIR /docs
ADD requirements.txt /docs
RUN pip3 install -r requirements.txt
```

## Screenshots
![](https://www.sphinx-doc.org/en/master/_images/lumache-first-light.png)

## References
- [Sphinx](https://www.sphinx-doc.org/)
- [sphinxdoc/sphinx](https://hub.docker.com/r/sphinxdoc/sphinx)
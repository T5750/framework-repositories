# Sphinx Docker

## Docker
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

## Screenshots
![](https://www.sphinx-doc.org/en/master/_images/lumache-first-light.png)

## References
- [Sphinx](https://www.sphinx-doc.org/)
- [sphinxdoc/sphinx](https://hub.docker.com/r/sphinxdoc/sphinx)
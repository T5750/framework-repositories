# GitBook Docker

Modern documentation format and toolchain using Git and Markdown

## Docker
```sh
# vi gitbook/README.md
# vi gitbook/SUMMARY.md
docker run -d --name gitbook -p 4000:4000 -v $PWD/gitbook:/srv/gitbook fellah/gitbook
```
- [http://localhost:4000/](http://localhost:4000/)
- `4000` – GitBook default service port.
- `35729` – Live reload server port.
- `/srv/gitbook` – Default working directory for GitBook container.

### Build Static Website
```sh
docker run -v /srv/gitbook -v /srv/html fellah/gitbook gitbook build . /srv/html
```

## Tips
```sh
gitbook help
```

## Screenshots
![](https://raw.github.com/GitbookIO/gitbook/master/preview.png)

![](https://assets-global.website-files.com/600ead1452cf056d0e52dbed/603a7bc0fa052dd5d52be9b8_Stacked%20Screens%20(1).png)

![](https://assets-global.website-files.com/600ead1452cf056d0e52dbed/603f67d3723505b948cb08a8_Design%20System%20(1).png)

## References
- [GitBook](https://www.gitbook.com/)
- [GitBook Docker](https://hub.docker.com/r/fellah/gitbook)
- [GitBook GitHub](https://github.com/GitbookIO/gitbook)
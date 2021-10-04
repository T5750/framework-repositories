# ONLYOFFICE Docker

ONLYOFFICE Document Server is an online office suite comprising viewers and editors for texts, spreadsheets and presentations, fully compatible with Office Open XML formats: .docx, .xlsx, .pptx and enabling collaborative editing in real time.

## Docker
```
docker run -i -t -d --name onlyoffice -p 8080:80 -p 8000:8000 onlyoffice/documentserver
```

- [http://localhost:8080/](http://localhost:8080/)
- [http://localhost:8080/example/](http://localhost:8080/example/)

## Community Edition
- Simultaneous connections: up to 20 maximum
- Number of users: up to 20 recommended
- License: GNU AGPL v.3

## Screenshots
![](https://helpcenter.onlyoffice.com/OfficeWeb/apps/documenteditor/main/resources/help/en/images/interface/editorwindow.png)

![](https://helpcenter.onlyoffice.com/OfficeWeb/apps/spreadsheeteditor/main/resources/help/en/images/interface/desktop_editorwindow.png)

![](https://helpcenter.onlyoffice.com/OfficeWeb/apps/presentationeditor/main/resources/help/en/images/interface/editorwindow.png)

## References
- [Installing ONLYOFFICE Docs Community Edition for Docker on a local server](https://helpcenter.onlyoffice.com/installation/docs-community-install-docker.aspx)
- [Onlyoffice Document Server 搭建](https://wineee.github.io/post/onlyoffice-docker-documentserver/)
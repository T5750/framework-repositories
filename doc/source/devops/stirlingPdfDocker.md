# Stirling PDF Docker

#1 Locally hosted web application that allows you to perform various operations on PDF files

## Docker
```sh
docker run -d \
  -p 8080:8080 \
  -v ./trainingData:/usr/share/tessdata \
  -v ./extraConfigs:/configs \
  -v ./logs:/logs \
  -e DOCKER_ENABLE_SECURITY=false \
  -e INSTALL_BOOK_AND_ADVANCED_HTML_OPS=false \
  -e LANGS=en_GB \
  --name stirling-pdf \
  frooodle/s-pdf:latest

  #Can also add these for customisation but are not required
  #-v /location/of/customFiles:/customFiles \

docker run -d -p 8080:8080 -e DOCKER_ENABLE_SECURITY=false -e INSTALL_BOOK_AND_ADVANCED_HTML_OPS=false -e LANGS=zh_CN --name stirling-pdf ghcr.io/frooodle/s-pdf
```
[http://localhost:8080/](http://localhost:8080/)

## Docker Compose
```
version: '3.3'
services:
  stirling-pdf:
    image: frooodle/s-pdf:latest
    ports:
      - '8080:8080'
    volumes:
      - ./trainingData:/usr/share/tessdata #Required for extra OCR languages
      - ./extraConfigs:/configs
#      - ./customFiles:/customFiles/
#      - ./logs:/logs/
    environment:
      - DOCKER_ENABLE_SECURITY=false
      - INSTALL_BOOK_AND_ADVANCED_HTML_OPS=false
      - LANGS=en_GB
```

## Tips
其他 -> 在PDF中添加图片: [http://localhost:8080/add-image](http://localhost:8080/add-image)

## Runtime Environment
- [Java 17](https://github.com/openjdk/jdk)
- [Spring Boot 3.x](https://spring.io/projects/spring-boot)
- [PDFBox](https://github.com/apache/pdfbox/tree/trunk)
- [LibreOffice](https://www.libreoffice.org/discover/libreoffice/)
- [OcrMyPdf](https://github.com/ocrmypdf/OCRmyPDF)

## Screenshots
![](https://github.com/Stirling-Tools/Stirling-PDF/blob/main/images/stirling-home.jpg)

## References
- [Stirling PDF GitHub](https://github.com/Stirling-Tools/Stirling-PDF)
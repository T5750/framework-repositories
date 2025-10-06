# OCRmyPDF Docker

OCRmyPDF adds an OCR text layer to scanned PDF files, allowing them to be searched
```bash
ocrmypdf                      # it's a scriptable command line program
   -l eng+fra                 # it supports multiple languages
   --rotate-pages             # it can fix pages that are misrotated
   --deskew                   # it can deskew crooked PDFs!
   --title "My PDF"           # it can change output metadata
   --jobs 4                   # it uses multiple cores by default
   --output-type pdfa         # it produces PDF/A by default
   input_scanned.pdf          # takes PDF input (or images)
   output_searchable.pdf      # produces validated PDF output
```

## Docker
```sh
docker run --rm -i jbarlow83/ocrmypdf-alpine (... all other arguments here...)
# Using the OCRmyPDF web service wrapper
docker run -d --name ocrmypdf --entrypoint python -p 8501:8501 jbarlow83/ocrmypdf-alpine webservice.py
```
[http://localhost:8501/](http://localhost:8501/)

## Feature demo
```bash
# Add an OCR layer and convert to PDF/A
ocrmypdf input.pdf output.pdf

# Convert an image to single page PDF
ocrmypdf input.jpg output.pdf

# Add OCR to a file in place (only modifies file on success)
ocrmypdf myfile.pdf myfile.pdf

# OCR with non-English languages (look up your language's ISO 639-3 code)
ocrmypdf -l fra LeParisien.pdf LeParisien.pdf

# OCR multilingual documents
ocrmypdf -l eng+fra Bilingual-English-French.pdf Bilingual-English-French.pdf

# Deskew (straighten crooked pages)
ocrmypdf --deskew input.pdf output.pdf
```

## Tests
注：PDF中文扫描件对比Chrome识别准确率一般
```sh
tesseract --list-langs | grep chi_sim
ocrmypdf -l chi_sim input.pdf output.pdf
ocrmypdf -l chi_sim+eng input.pdf output.pdf
```

## Runtime Environment
- [Python 3.12.x](https://www.python.org/downloads/)

## Screenshots
![](https://github.com/ocrmypdf/OCRmyPDF/raw/main/misc/screencast/demo.svg)

## References
- [OCRmyPDF](http://ocrmypdf.readthedocs.io/)
- [OCRmyPDF GitHub](https://github.com/ocrmypdf/OCRmyPDF)
- [OCRmyPDF Docker](https://ocrmypdf.readthedocs.io/en/latest/docker.html)
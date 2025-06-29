# TrWebOCR Docker

开源易用的中文离线OCR

## Docker
```sh
docker run -itd --rm -p 8089:8089 --name trwebocr mmmz/trwebocr:latest
docker run -d -p 8089:8089 --name trwebocr mmmz/trwebocr
```
[http://localhost:8089/](http://localhost:8089/)

## 接口调用示例  
* Python 使用File上传文件  
``` python
import requests
url = 'http://192.168.31.108:8089/api/tr-run/'
img1_file = {
    'file': open('img1.png', 'rb')
}
res = requests.post(url=url, data={'compress': 0}, files=img1_file)
```  

* Python 使用Base64  
``` python
import requests
import base64
def img_to_base64(img_path):
    with open(img_path, 'rb')as read:
        b64 = base64.b64encode(read.read())
    return b64
    
url = 'http://192.168.31.108:8089/api/tr-run/'
img_b64 = img_to_base64('./img1.png')
res = requests.post(url=url, data={'img': img_b64})
```

## Runtime Environment
- [Python 3.7.x](https://www.python.org/downloads/)
- [Vue.js](https://github.com/vuejs/vue)

## Screenshots
![](https://camo.githubusercontent.com/a5fb73f75f58a7532d69e318dbaf3dd94ccdc88c81adb0bf53db871a0e3f20ce/68747470733a2f2f696d616765732e616c6973656e33392e636f6d2f32303230303531373138343631392e706e67)

## References
- [TrWebOCR GitHub](https://github.com/alisen39/TrWebOCR)
- [TrWebOCR 接口文档](https://github.com/alisen39/TrWebOCR/wiki/%E6%8E%A5%E5%8F%A3%E6%96%87%E6%A1%A3)
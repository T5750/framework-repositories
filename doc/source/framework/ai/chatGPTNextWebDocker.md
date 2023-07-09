# ChatGPT Next Web Docker

One-Click to get well-designed cross-platform ChatGPT web UI.

一键免费部署你的跨平台私人 ChatGPT 应用。

## Docker
准备好你的 [OpenAI API Key](https://platform.openai.com/account/api-keys)
```sh
docker run -d --name chatgpt -p 3000:3000 -e OPENAI_API_KEY="sk-xxxx" -e CODE="123456" yidadaa/chatgpt-next-web
```
[http://localhost:3000/](http://localhost:3000/)

You can start service behind a proxy:
```sh
docker run -d -p 3000:3000 \
   -e OPENAI_API_KEY="sk-xxxx" \
   -e CODE="your-password" \
   -e PROXY_URL="http://localhost:7890" \
   yidadaa/chatgpt-next-web
```
If your proxy needs password, use:
```sh
-e PROXY_URL="http://127.0.0.1:7890 user pass"
```

## Screenshots
![](https://github.com/Yidadaa/ChatGPT-Next-Web/raw/2f2aefd48ec77e51bd7d230f9bcd466860918a48/docs/images/cover.png)

## References
- [ChatGPT Next Web](https://chatgpt1.nextweb.fun/)
- [ChatGPT Next Web GitHub](https://github.com/Yidadaa/ChatGPT-Next-Web)
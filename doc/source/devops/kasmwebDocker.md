# Kasm Docker

Kasm Workspaces is a docker container streaming platform for delivering browser-based access to desktops, applications, and web services.

## Kasm Desktop
### Docker
This Image contains a browser-accessible Ubuntu Bionic Desktop with Chrome and Firefox installed.
```sh
docker run --rm -it --shm-size=512m -p 6901:6901 -e VNC_PW=password kasmweb/desktop:1.13.0
```
- [https://localhost:6901/](https://localhost:6901/)
- User: kasm_user / password

### Screenshots
![](https://www.kasmweb.com/assets/images/App_Launcher_v1.12_Dark.jpg)

## KasmVNC
A modern open source VNC server.

### Installation
#### Debian/Ubuntu/Kali
```sh
# Please choose the package for your distro here (under Assets):
# https://github.com/kasmtech/KasmVNC/releases
wget <package_url>

sudo apt-get install ./kasmvncserver_*.deb

# Add your user to the ssl-cert group
sudo addgroup $USER ssl-cert

# YOU MUST DISCONNECT AND RECONNECT FOR GROUP MEMBERSHIP CHANGE TO APPLY

# start KasmVNC, you will be prompted to create a KasmVNC user and select a desktop environment
vncserver

# Tail the logs
tail -f ~/.vnc/*.log
```

### Screenshots
![](https://www.kasmweb.com/assets/images/accroImageLight.webp)

## Postman
This Image contains a browser-accessible version of [Postman](https://www.postman.com/).
```sh
sudo docker run --rm -it --shm-size=512m -p 6901:6901 -e VNC_PW=password kasmweb/postman:1.13.0
```

## Chrome
This Image contains a browser-accessible version of [Google Chrome](https://www.google.com/chrome/).
```sh
sudo docker run --rm -it --shm-size=512m -p 6901:6901 -e VNC_PW=password kasmweb/chrome:1.13.0
```

### Environment Variables
- `LAUNCH_URL` - The default URL the browser launches to when created.
- `APP_ARGS` - Additional arguments to pass to the browser when launched.
- `KASM_RESTRICTED_FILE_CHOOSER` - Confine "File Upload" and "File Save" dialogs to ~/Desktop. On by default.

## References
- [Kasm](https://www.kasmweb.com/)
- [KasmVNC](https://www.kasmweb.com/kasmvnc)
- [KasmVNC GitHub](https://github.com/kasmtech/KasmVNC)
- [KasmVNC Installation](https://www.kasmweb.com/kasmvnc/docs/1.0.0/install.html)
- [kasmweb/desktop Docker](https://hub.docker.com/r/kasmweb/desktop)
- [Kasm Workspaces Community Edition](https://www.kasmweb.com/community-edition)
- [kasmweb/postman Docker](https://hub.docker.com/r/kasmweb/postman)
- [kasmweb/chrome Docker](https://hub.docker.com/r/kasmweb/chrome)
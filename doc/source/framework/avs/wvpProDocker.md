# WVP-PRO Docker

WEB VIDEO PLATFORM是一个基于GB28181-2016标准实现的开箱即用的网络视频平台，负责实现核心信令与设备管理后台部分，支持NAT穿透，支持海康、大华、宇视等品牌的IPC、NVR接入。支持国标级联，支持将不带国标功能的摄像机/直播流/直播推流转发到其他国标平台。

## 应用场景
- 支持浏览器无插件播放摄像头视频。
- 支持摄像机、平台、NVR等设备接入。 支持国标级联。
- 支持rtsp/rtmp等视频流转发到国标平台。
- 支持rtsp/rtmp等推流转发到国标平台。

## Docker
```sh
docker run --env WVP_IP="你的IP" -it -p 18080:18080 -p 30000-30500:30000-30500/udp -p 30000-30500:30000-30500/tcp -p 80:80 -p 5060:5060 -p 5060:5060/udp 648540858/wvp_pro
docker run -d --name wvp_pro --env WVP_IP="你的IP" -it -p 18080:18080 -p 30000-30500:30000-30500/udp -p 30000-30500:30000-30500/tcp -p 80:80 -p 5060:5060 -p 5060:5060/udp 648540858/wvp_pro
```
- [http://localhost:18080/](http://localhost:18080/)
- User: admin / admin

## 使用的端口

端口 | 描述 | 类型
---|---|---
80 | zlm的http端口 | tcp
554 | zlm的rtsp端口，非必须 | tcp&udp
1935 | zlm的rtmp端口，非必须 | tcp
5060 | 28181sip信令端口 | tcp&udp
6379 | redis端口，非必须 | tcp
18080 | wvp的http端口 | tcp
18081 | 录像管理服务端口，非必须 | tcp
30000-30500 | zlm接收视频推流端口 | tcp&udp

## 目录说明
- wvp程序：`/opt/wvp`
- zlm程序：`/opt/media`
- wvp录像管理服务：`/opt/assist`

## 日志查看
- `/opt/wvp/logs` 保存wvp日志
- `/opt/media/log` 保存zlm日志
- `/opt/assist/logs` 保存录像管理服务日志

## 录像持久化
- `/opt/media/www/record` 保存录像文件

## 修改wvp的其他配置
容器内通过环境变量`WVP_CONFIG`来修改wvp的配置，**注意，使用此变量WVP_IP会失效，对于ip的设置也需要添加到`WVP_CONFIG`里**，必须添加的内容：
```
--media.ip=127.0.0.1 --media.sdp-ip=你的IP --sip.ip=你的IP --media.stream-ip=你的IP
```
以修改sip密码为例
```sh
docker run --env WVP_CONFIG="--media.ip=127.0.0.1 --media.sdp-ip=你的IP --sip.ip=你的IP --media.stream-ip=你的IP --sip.password=12345678" -it -p 18080:18080 -p 30000-30500:30000-30500/udp -p 30000-30500:30000-30500/tcp -p 80:80 -p 5060:5060 -p 5060:5060/udp 648540858/wvp_pro
```

## Runtime Environment
- [Java 11](https://openjdk.java.net/projects/jdk/11/)

## Screenshots
![](https://images.gitee.com/uploads/images/2022/0304/101513_79632720_1018729.png "2022-03-04_09-51.png")

![](https://images.gitee.com/uploads/images/2022/0304/103025_5df016f9_1018729.png "2022-03-04_10-27.png")

![](https://images.gitee.com/uploads/images/2022/0304/101706_088fbafa_1018729.png "2022-03-04_09-52_1.png")

![](https://images.gitee.com/uploads/images/2022/0304/101756_3d662828_1018729.png "2022-03-04_10-00_1.png")

![](https://images.gitee.com/uploads/images/2022/0304/101823_19050c66_1018729.png "2022-03-04_10-12_1.png")

![](https://images.gitee.com/uploads/images/2022/0304/101848_e5a39557_1018729.png "2022-03-04_10-12_2.png")

![](https://images.gitee.com/uploads/images/2022/0304/101919_ee5b8c79_1018729.png "2022-03-04_10-13.png")

## References
- [WVP-PRO Docker](https://hub.docker.com/r/648540858/wvp_pro)
- [WVP-PRO GitHub](https://github.com/648540858/wvp-GB28181-pro)
- [WVP-PRO使用文档](https://doc.wvp-pro.cn/)
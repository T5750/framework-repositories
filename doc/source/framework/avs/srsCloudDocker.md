# SRS Cloud Docker

SRS Cloud is a lightweight open-source video cloud based on Go, Reactjs, SRS, FFmpeg, WebRTC, etc.

## Features
- 私人直播间，公网可以直接使用的直播间，带鉴权只有自己能推流
- 超清实时直播，指码率很高（测试过2~8Mbps），延迟很低（200~500ms）且无累计延迟的直播
- 多平台转播，将流转发给其他平台，比如视频号直播、快手、B站等
- 录制
  + 本地录制，指录制视频流到云SRS的本地磁盘，只要推送到服务器的流都可以录制
  + 云录制，指录制视频流到云存储，只要推送到服务器的流都可以录制
  + 云点播，指转换视频流到云点播，只要推送到服务器的流都可以对接云点播
- 虚拟直播，是将一个视频文件，用FFmpeg转成直播流，推送到云SRS或其他平台

## Docker
```sh
docker run --rm -it -p 2022:2022 -p 1935:1935/tcp -p 1985:1985/tcp \
  -p 8080:8080/tcp -p 8000:8000/udp -p 10080:10080/udp \
  ossrs/srs-cloud:platform-1
```
- [http://localhost:2022/mgmt](http://localhost:2022/mgmt)
- [http://localhost:2022/console/ng_index.html#/summaries?port=2022&http=2022](http://localhost:2022/console/ng_index.html#/summaries?port=2022&http=2022)

All data will be reset when restarting, so please mount volumes if want to save data to local disk:
```sh
docker run --rm -it -p 2022:2022 -p 1935:1935/tcp -p 1985:1985/tcp \
  -p 8080:8080/tcp -p 8000:8000/udp -p 10080:10080/udp \
  -v $HOME/db:/data ossrs/srs-cloud:platform-1
```

## Architecture
The architecture of [srs-cloud](https://github.com/ossrs/srs-cloud#architecture) by 
[mermaid](https://mermaid.live/edit#pako:eNqNkcluwjAQhl_F8qFKJELuaYWECKhSVyUtF9KDiSdLie3ImVAQ4t1rO0I0nHrwMuNv_ll8orniQCNaNOonr5hG8pzcZ5IQWdbyQIJgRkQp0LPbw1aHs-2RSBPy3fkOs35HpUnqzkeldp3XNgwLpUVYWdMfCNTAxLzHityReJ2Yfa1ip2Itiyze0rHqaiVaKK9yRWHtUeqAXF6dd6gj-E_kjHy2pWYc3D2BBlgHY-ADZA4SF43q-VXrr3fobTF_2ZgVmgZC09TXWOVdKwFYQd-50l7N_JaHVmkEPQZjle9ufUu533hTkHs3_lwDN6lr1nRTPKB_kykBXncbzx3kaW2e6YQK0ILV3PzyycIZNaUIyGhkrhwK1jeY0UyeDdq3nCEseY1K0wh1DxPKelTpUeYXe2DimpnZCRoVphQ4_wK2Lb2m)

## References
- [SRS Cloud GitHub](https://github.com/ossrs/srs-cloud)
- [SRS云服务器合集](https://ossrs.net/lts/zh-cn/docs/v4/tutorial/srs-cloud-server)
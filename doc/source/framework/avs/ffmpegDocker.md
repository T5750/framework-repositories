# FFmpeg Docker

A complete, cross-platform solution to record, convert and stream audio and video.

## Detailed description
```
 _______              ______________
|       |            |              |
| input |  demuxer   | encoded data |   decoder
| file  | ---------> | packets      | -----+
|_______|            |______________|      |
                                           v
                                       _________
                                      |         |
                                      | decoded |
                                      | frames  |
                                      |_________|
 ________             ______________       |
|        |           |              |      |
| output | <-------- | encoded data | <----+
| file   |   muxer   | packets      |   encoder
|________|           |______________|

```

## Tools
* [ffmpeg](https://ffmpeg.org/ffmpeg.html) is a command line toolbox to manipulate, convert and stream multimedia content.
* [ffplay](https://ffmpeg.org/ffplay.html) is a minimalistic multimedia player.
* [ffprobe](https://ffmpeg.org/ffprobe.html) is a simple analysis tool to inspect multimedia content.
* Additional small tools such as `aviocat`, `ismindex` and `qt-faststart`.

## Generic options
```sh
ffmpeg -help
ffmpeg -encoders
# libopenh264
```

## Docker
```sh
# mp4 -> avi
docker run --rm -it \
  -v $(pwd)/config:/config \
  linuxserver/ffmpeg \
  -i /config/input.mp4 -c:v copy -c:a copy /config/output.avi
# avi -> mp4
docker run --rm -it \
  -v $(pwd)/config:/config \
  linuxserver/ffmpeg \
  -i /config/output.avi -c:v copy -c:a copy /config/mp4output.mp4
# 宽度固定，高度按比例
docker run --rm -it \
  -v $(pwd)/config:/config \
  linuxserver/ffmpeg \
  -i /config/input.mp4 -vf scale=640:640/a -c:v libx264 -c:a copy /config/output-640.mp4
# 高度固定，宽度按比例
docker run --rm -it \
  -v $(pwd)/config:/config \
  linuxserver/ffmpeg \
  -i /config/input.mp4 -vf scale=480*a:480 -c:v libx264 -c:a copy /config/output-480.mp4
docker run --rm -it \
  -v $(pwd)/config:/config \
  linuxserver/ffmpeg \
  -i /config/input.mp4 -ss 00:00:01 -y -vframes 1 /config/new.jpg
```

### Basic Transcode
```sh
docker run --rm -it \
  -v $(pwd):/config \
  linuxserver/ffmpeg \
  -i /config/input.mkv \
  -c:v libx264 \
  -b:v 4M \
  -vf scale=1280:720 \
  -c:a copy \
  /config/output.mkv
```

### Hardware accelerated (VAAPI)
```sh
docker run --rm -it \
  --device=/dev/dri:/dev/dri \
  -v $(pwd):/config \
  linuxserver/ffmpeg \
  -vaapi_device /dev/dri/renderD128 \
  -i /config/input.mkv \
  -c:v h264_vaapi \
  -b:v 4M \
  -vf 'format=nv12|vaapi,hwupload,scale_vaapi=w=1280:h=720' \
  -c:a copy \
  /config/output.mkv
```

### Nvidia Hardware accelerated
```sh
docker run --rm -it \
  --runtime=nvidia \
  -v $(pwd):/config \
  linuxserver/ffmpeg \
  -hwaccel nvdec \
  -i /config/input.mkv \
  -c:v h264_nvenc \
  -b:v 4M \
  -vf scale=1280:720 \
  -c:a copy \
  /config/output.mkv
```

## Tips

规格 | 文件码率 | 分辨率 | 视频编码格式 | 视频编码码率 | 视频帧率 | 音频编码格式 | 音频编码码率 | 音频编码通道
---|---|---|---|---|---|---|---|---
4K | 5966kbps | 3840x2160 | h264 | 5862kbps | 25fps | aac | 185kbps | 2ch
1080P | 2327kbps | 1920x1080 | h264 | 2569kbps | 25fps | aac | 62kbps | 2ch
720P | 1247kbps | 1280x720 | h264 | 983kbps | 25fps | aac | 125kbps | 2ch
高清 | 590kbps | 896x504 | h264 | 542kbps | 25fps | aac | 64kbps | 2ch
流畅 | 330kbps | 640x360 | h264 | 316kbps | 25fps | aac | 47kbps | 2ch

### 合并视频
`vi video.txt`
```
file 'test.mp4'
file 'test2.mp4'
```
```sh
ffmpeg -f concat -i video.txt -c copy concat.mp4
#ffmpeg -i "concat:0.mp4|1.mp4" -c copy concat.mp4
```

## Screenshots
![](https://ffmpeg.org/pipermail/ffmpeg-user/attachments/20150223/498ab7be/attachment.png)

## References
- [FFmpeg](https://ffmpeg.org/)
- [FFmpeg GitHub](https://github.com/FFmpeg/FFmpeg)
- [linuxserver/ffmpeg Docker](https://hub.docker.com/r/linuxserver/ffmpeg)
- [linuxserver/ffmpeg GitHub](https://github.com/linuxserver/docker-ffmpeg)
- [视频标清、高清、全高清的分类分辨率码率帧率参考](https://blog.csdn.net/ffffffff8/article/details/84950014)
- [ffmpeg合并多个MP4视频](https://blog.csdn.net/first_shun/article/details/108502532)
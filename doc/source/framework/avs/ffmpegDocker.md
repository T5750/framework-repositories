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

## Docker
```sh
# mp4 -> avi
docker run --rm -it \
  -v $(pwd)/config:/config \
  linuxserver/ffmpeg \
  -i /config/input.mp4 /config/output.avi
# 宽度固定，高度按比例
docker run --rm -it \
  -v $(pwd)/config:/config \
  linuxserver/ffmpeg \
  -i /config/input.mp4 -vf scale=640:640/a -c:a copy /config/output-640.mp4
# 高度固定，宽度按比例
docker run --rm -it \
  -v $(pwd)/config:/config \
  linuxserver/ffmpeg \
  -i /config/input.mp4 -vf scale=480*a:480 -c:a copy /config/output-480.mp4
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

## Screenshots
![](https://ffmpeg.org/pipermail/ffmpeg-user/attachments/20150223/498ab7be/attachment.png)

## References
- [FFmpeg](https://ffmpeg.org/)
- [FFmpeg GitHub](https://github.com/FFmpeg/FFmpeg)
- [linuxserver/ffmpeg Docker](https://hub.docker.com/r/linuxserver/ffmpeg)
- [linuxserver/ffmpeg GitHub](https://github.com/linuxserver/docker-ffmpeg)
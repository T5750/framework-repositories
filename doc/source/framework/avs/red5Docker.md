# Red5 Docker

Red5 open-sourse is a free media streaming server solution written in Java that supports:
 * Streaming Video (FLV, F4V, MP4, 3GP)
 * Streaming Audio (MP3, F4A, M4A, AAC)
 * Recording Client Streams (FLV and AVC+AAC in FLV container)
 * Live Stream Publishing
 * Remoting
 * Protocols: RTMP, RTMPT, RTMPS, and RTMPE

## Docker
```sh
docker run --name red5 -d -p 5080:5080 -p 1935:1935 mondain/red5
```

## Maven
```xml
<dependencyManagement>
    <dependencies>
      <dependency>
          <groupId>org.red5</groupId>
          <artifactId>red5-parent</artifactId>
          <version>${red5.version}</version>
          <type>pom</type>
      </dependency>
    </dependencies>
</dependencyManagement>
```

## Runtime Environment
- [Java 11](https://openjdk.java.net/projects/jdk/11/)

## References
- [Red5's official website](https://www.red5.net/open-source-live-streaming/)
- [mondain/red5 Docker](https://hub.docker.com/r/mondain/red5/)
- [Red5 GitHub](https://github.com/Red5/red5-server)
- [Installation on Linux](https://www.red5.net/docs/installation/installation/ubuntuinstall/)
- [How to Set Up a Free RTMP Server with Open Source Red5](https://www.red5.net/blog/online-rtmp-server-free/)

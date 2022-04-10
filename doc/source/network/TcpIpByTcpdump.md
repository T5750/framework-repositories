# 从tcpdump抓包看TCP/IP协议

## 报文获取
1. 在192.168.100.211机器的6379端口启一个Redis服务
1. 通过tcpdump这个工具来抓取数据包，命令：`tcpdump -w /tmp/logs -i eth1 port 6379 -s0`
1. 在192.168.100.162机器上访问Redis实例192.168.100.211:6379，可以用redis-cli客户端，也可以用telnet，发送一个ping，得到对端回复PONG
1. 停止抓包，用tcpdump读取数据包（`-x`以16进制形式展示）：`tcpdump -r /tmp/logs -n -nn -A -x| vim -`

```
15:35:30.714212 IP 192.168.100.162.58110 > 192.168.100.211.6379: Flags [P.], seq 1532104887:1532104901, ack 1449112335, win 16400, length 14
        0x0000:  4500 0036 5957 4000 4006 96a4 c0a8 64a2
        0x0010:  c0a8 64d3 e2fe 18eb 5b52 10b7 565f b30f
        0x0020:  5018 4010 5f31 0000 2a31 0d0a 2434 0d0a
        0x0030:  7069 6e67 0d0a
15:35:30.714463 IP 192.168.100.211.6379 > 192.168.100.162.58110: Flags [P.], seq 1:8, ack 14, win 229, length 7
        0x0000:  4500 002f 5fb5 4000 4006 904d c0a8 64d3
        0x0010:  c0a8 64a2 18eb e2fe 565f b30f 5b52 10c5
        0x0020:  5018 00e5 4ae8 0000 2b50 4f4e 470d 0a
15:35:30.914860 IP 192.168.100.162.58110 > 192.168.100.211.6379: Flags [.], ack 8, win 16398, length 0
        0x0000:  4500 0028 5958 4000 4006 96b1 c0a8 64a2
        0x0010:  c0a8 64d3 e2fe 18eb 5b52 10c5 565f b316
        0x0020:  5010 400e b388 0000 0000 0000 0000
```

## 报文分析
![TCP_IP-min](https://s1.wailian.download/2020/01/03/TCP_IP-min.jpg)

### IP层解析
![IP-packet-min-min](https://s0.wailian.download/2019/02/26/IP-packet-min-min.png)

字节值 | 字节含义
---|------
0x4 | IP版本为ipv4
0x5 | 首部长度为5 * 4字节=20B
0x00 | 服务类型，现在基本都置为0
0x0036 | 总长度为3*16+6=54字节，上面所有的长度就是54字节
0x5957 | 标识。同一个数据报的唯一标识。当IP数据报被拆分时，会复制到每一个数据中
0x4000 | 3bit标志 + 13bit片偏移。3bit标志对应R、DF、MF。目前只有后两位有效，DF位：为1表示不分片，为0表示分片。MF：为1表示“更多的片”，为0表示这是最后一片。13bit片位移：本分片在原先数据报文中相对首位的偏移位。（需要再乘以8)
0x40 | 生存时间TTL。IP报文所允许通过的路由器的最大数量。每经过一个路由器，TTL减1，当为0时，路由器将该数据报丢弃。TTL字段是由发送端初始设置一个8 bit字段。推荐的初始值由分配数字RFC指定。发送ICMP回显应答时经常把TTL设为最大值255。TTL可以防止数据报陷入路由循环。此处为64
0x06 | 协议类型。指出IP报文携带的数据使用的是哪种协议，以便目的主机的IP层能知道要将数据报上交到哪个进程。TCP的协议号为6，UDP的协议号为17。ICMP的协议号为1，IGMP的协议号为2。该IP报文携带的数据使用TCP协议，得到了验证
0x96a4 | 16bit IP首部校验和
0xc0a8 64a2 | 32bit源IP地址
0xc0a8 64d3 | 32bit目的IP地址

### 传输层解析
本报文携带的数据使用的TCP协议，采用`固定长度(20B) + 可变长度`的形式

![TcpHeaderZh-min-min](https://s0.wailian.download/2019/02/26/TcpHeaderZh-min-min.jpg)

字节值 | 字节含义
---|------
0xe2fe | 16bit源端口。58110
0x18eb | 16bit目的端口。6379
0x5b52 10b7 | 32bit序列号。1532104887
0x565f b30f | 32bit确认号。1449112335
0x5 | 4bit首部长度，以4byte为单位。共5*4=20字节。因此TCP报文的可选长度为20-20=0
0b000000 | 6bit保留位。目前置为0
0b010010 | 6bit TCP标志位。从左到右依次是紧急URG、确认ACK、推送PSH、复位RST、同步SYN、终止FIN
0x4010 | 滑动窗口大小，滑动窗口即TCP接收缓冲区的大小，用于TCP拥塞控制。16400
0x5f31 | 16bit校验和
0x0000 | 紧急指针。仅在URG=1时才有意义，它指出本报文段中的紧急数据的字节数。当URG=1时，发送方TCP就把紧急数据插入到本报文段数据的最前面，而在紧急数据后面的数据仍是普通数据

![TcpLogsCap-min](https://s0.wailian.download/2019/02/27/TcpLogsCap-min.png)

### 数据部分解析
ping转换成Redis协议如下：
```
*1\r\n
$4\r\n
ping\r\n
```
对照ascii码表来看，`man 7 ascii`
```
0x2a31         -> *1
0x0d0a         -> \r\n
0x2434         -> $4
0x0d0a         -> \r\n
0x7069 0x6e67  -> ping
0x0d0a         -> \r\n
```
```
2b50 4f4e 470d 0a -> +PONG\r\n
```

## TCP Options
TCP可选项，其格式如下：

![TcpOptions-min-min](https://s0.wailian.download/2019/02/27/TcpOptions-min-min.jpg)

Kind(Type) | Length | Name | Reference | 描述 & 用途
---|---|---|---|---
0 | 1 | EOL | RFC 793 | 选项列表结束
1 | 1 | NOP | RFC 793 | 无操作（用于补位填充）
2 | 4 | MSS | RFC 793 | 最大Segment长度
3 | 3 | WSOPT | RFC 1323 | 窗口扩大系数（Window Scaling Factor）
4 | 2 | SACK-Premitted | RFC 2018 | 表明支持SACK
5 | 可变 | SACK | RFC 2018 | SACK Block（收到乱序数据）
8 | 10 | TSPOT | RFC 1323 | Timestamps
19 | 18 | TCP-MD5 | RFC 2385 | MD5认证
28 | 4 | UTO | RFC 5482 | User Timeout（超过一定闲置时间后拆除连接）
29 | 可变 | TCP-AO | RFC 5925 | 认证（可选用各种算法）
253/254 | 可变 | Experimental | RFC 4727 | 保留，用于科研实验

```
16:17:48.467330 IP 192.168.100.162.61863 > 192.168.100.211.6379: Flags [S], seq 3430253984, win 8192, options [mss 1460,nop,wscale 2,nop,nop,sackOK], length 0
        0x0000:  4500 0034 37e2 4000 4006 b81b c0a8 64a2
        0x0010:  c0a8 64d3 f1a7 18eb cc75 85a0 0000 0000
        0x0020:  8002 2000 a7a6 0000 0204 05b4 0103 0302
        0x0030:  0101 0402
16:17:48.467364 IP 192.168.100.211.6379 > 192.168.100.162.61863: Flags [R.], seq 0, ack 3430253985, win 0, length 0
        0x0000:  4500 0028 0000 4000 4006 f009 c0a8 64d3
        0x0010:  c0a8 64a2 18eb f1a7 0000 0000 cc75 85a1
        0x0020:  5014 0000 0860 0000
```

字节值 | 字节含义
---|------
0x0204 05b4 | 05b4=1460，mss 1460
0x01 | nop
0x03 0302 | wscale 2
0x01 | nop
0x01 | nop
0x0402 | sackOK

## tcpdump
tcpdump filter可以简单地分为三类：
- type：区分报文的类型，主要由host（主机），net（网络，支持CIDR）和port（支持范围，portrange 21-23）组成
- dir：区分方向，主要由src和dst组成
- proto：区分协议支持TCP、UDP、ICMP等

**注意**：单位是字节，不是位！
- 打印80端口，有数据的TCP包：`tcpdump 'tcp port 80 and (((ip[2:2] - ((ip[0]&0xf)<<2)) - ((tcp[12]&0xf0)>>2)) != 0)'`
- 打印80端口，长度超过576的IP包：`tcpdump 'port 80 and ip[2:2] > 576'`
- 打印特定TCP Flag的数据包：
    - `[S]`：SYN（开始连接）
    - `[.]`：没有Flag
    - `[P]`：PSH（推送数据）
    - `[F]`：FIN（结束连接）
    - `[R]`：RST（重置连接）
    - `[S.]`：SYN-ACK，就是SYN报文的应答报文
	```
	tcpdump 'tcp[13] & 16!=0'
	# 等价于
	tcpdump 'tcp[tcpflags] == tcp-ack'
	
	# 打印出所有的ACK包
	tcpdump 'tcp[13] & 4!=0'
	# 等价于
	tcpdump 'tcp[tcpflags] == tcp-rst'
	```

## References
- [从tcpdump抓包看TCP/IP协议](https://segmentfault.com/a/1190000015044878)
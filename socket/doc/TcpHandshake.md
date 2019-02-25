# TCP Handshake

## TCP头部
![TcpHeader-min](https://www.wailian.work/images/2019/02/20/TcpHeader-min.png)

- ACK：确认，使得确认号有效
- RST：重置连接（经常看到的reset by peer）就是此字段搞的鬼
- SYN：用于初如化一个连接的序列号
- FIN：该报文段的发送方已经结束向对方发送数据

名称 | 作用说明
---|------
Source Port Number | 源端口号，16bits，能表示的数值范围0~65535，对于linux系统而言，1024以下的端口是特权端口，仅能root使用
Destination Port Number | 目标端口号，16bits，也能表示0~65535
Sequence Number | 32bits，TCP报文的序列号
Acknowledgement Number | 32bits，确认Sequence Number的确认号，一般确认号的表示方式是：被确认的Sequence Numbe + 1，必要要确认对方100号报文，那么确认号就是101
Header Length | 4bits，TCP Header的长度最小20字节，最大60字节，因为Options最大40bytes
Reserved | 4bits，保留位
CWR | 1bit，Congestion Window Reduced
ECN | 1bit，Explicit Congestion Notice
URG | 1bit，Urgent的缩写，紧急位，大部分报文会在TCP/IP协议栈的内核缓存区等待处理，URG的意思就是把这个请求插入到内核缓存区的前面，让报文尽快被处理
ACK | 1bit，Acknowledgement的缩写，确认位，将该位设置1既可开启此位，开启此位表示此报文为确认报文，Acknowledgement Number才会有效，因此仅当3次握手的第一次时ACK为0，其它时候都为1
PSH | 1bit，推送位，不经过内核缓存区，直接接收处理
RST | 1bit，重置位，用于重置TCP连接
SYN | 1bit，请求位，synchronization的缩写，用于发起TCP连接请求，将该位设置1既可开启此位，表示这是一条TCP请求报文，因此，3次握手当做仅有第一次SYN位才为1，其它时候都为0
FIN | 1bit，结束位，finish的缩写，用于结束TCP连接请求，将该位设置1既可开启此位，4次挥手中，Initiator请求断开时FIN为1，Receiver处理完请求，也请求断开时FIN为1
Window Size | 16bits，滑动窗口，根据接收缓冲进行调整，为了提高效率，报文不会1个1个的发送，而是一批一批的发送，确认也是一批一批的进行确认（及延迟确认），那么一批发送多少个报文合适喃？这个值是根据对方的发送缓冲、线路容纳、自己接收缓冲这3个条件，取最小的那个作为批量发送的报文数，当这一批报文到达自己的接收缓冲过后，如果接收缓冲还有空间缓冲报文，就会以Window Size这个字段告诉发送方，下次可以一批发多少个报文过来因此，这个Window Size是动态变化的
TCP Checksum | TCP报文校验码

## 三次握手
易于理解的视角
1. 客户端发送网络包，服务端收到了。服务端结论：客户端的发送能力、服务端的接收能力是正常的
2. 服务端发包，客户端收到了。客户端结论：服务端的接收、发送能力，客户端的接收、发送能力是正常的
3. 客户端发包，服务端收到了。服务端结论：客户端的接收、发送能力，服务端的发送、接收能力是正常的

视角 | 客收 / 服发 | 客发 / 服收
---|---|---
客视角 | 二 | 一 + 二
服视角 | 二 + 三 | 一

![TcpHandshake3-min](https://www.wailian.work/images/2019/02/20/TcpHandshake3-min.png)

1. 客户端发送一个SYN段，并指明客户端的初始序列号，即ISN(x)
1. 服务端发送自己的SYN段作为应答，同样指明自己的ISN(y)。为了确认客户端的SYN，将ISN(x)+1作为ACK数值。这样，每发送一个SYN，序列号就会加1。如果有丢失的情况，则会重传
1. 为了确认服务器端的SYN，客户端将ISN(y)+1作为返回的ACK数值

## 四次挥手
![TcpHandshake4-min](https://www.wailian.work/images/2019/02/20/TcpHandshake4-min.png)

1. 客户端发送一个FIN段，并包含一个希望接收者看到的自己当前的序列号x。同时还包含一个ACK表示确认对方最近一次发过来的数据
1. 服务端将x值加1作为ACK序号值，表明收到了上一个包。这时上层的应用程序会被告知另一端发起了关闭操作，通常这将引起应用程序发起自己的关闭操作
1. 服务端发起自己的FIN段，ACK=x+1, Seq=y
1. 客户端确认。ACK=y+1

注意，接收到FIN报文的一方只能回复一个ACK，它是无法马上返回对方一个FIN报文段的，因为结束数据传输的“指令”是上层应用层给出的，我只是一个“搬运工”，我无法了解“上层的意志”

## 为什么建立连接是三次握手，而关闭连接却是四次挥手呢？
- 因为服务端在LISTEN状态下，收到建立连接请求的SYN报文后，把ACK和SYN放在一个报文里发送给客户端
- 而关闭连接时，当收到对方的FIN报文时，仅仅表示对方不再发送数据了但是还能接收数据，己方是否现在关闭发送数据通道，需要上层应用来决定，因此，己方ACK和FIN一般都会分开发送

## ISN
ISN(Initial Sequence Number): `ISN = M + F(localhost, localport, remotehost, remoteport)`
- M是一个计时器，每隔4微秒加1
- F是一个Hash算法，根据源IP、目的IP、源端口、目的端口生成一个随机数值。要保证hash算法不能被外部轻易推算得出

## 序列号回绕
一个TCP流的初始序列号（ISN）并不是从0开始的，而是采用一定的随机算法产生的，因此ISN可能很大（比如(2^32-10)），因此同一个TCP流的seq号可能会回绕到0。而对于丢包和乱序等问题的判断都是依赖于序列号大小比较的，此时就出现了TCP序列号回绕（sequence wraparound）问题

### 内核解决办法
- __s32是有符号整型，而__u32则是无符号整型
- 序列号发生回绕后，序列号变小，相减之后，把结果变成有符号数了，因此结果成了负数
```
/*
* The next routines deal with comparing 32 bit unsigned ints
* and worry about wraparound (automatic with unsigned arithmetic).
*/
static inline int before(__u32 seq1, __u32 seq2)
{
    return (__s32)(seq1-seq2) < 0;
}
#define after(seq2, seq1) before(seq1, seq2)
```
1. 第一步计算两个无符号数据seq1-seq2的结果值，类型还是无符号
2. 通过__s32将无符号结果值转化为有符号数
3. 判断有符号的结果值是否小于0

以unsigned char和char为例来说明：
```
假设seq1=255，seq2=1（发生了回绕）
seq1 = 1111 1111 seq2 = 0000 0001
我们希望比较结果是
 seq1-seq2=
 1111 1111
-0000 0001
-----------
 1111 1110
由于将结果转化成了有符号数，由于最高位是1，因此，结果是一个负数，负数的绝对值为：0000 0001 + 1 = 0000 0010 = 2
因此seq1 - seq2 < 0
```
```
如果seq2=128的话：
 seq1-seq2=
 1111 1111
-1000 0000
-----------
 0111 1111
此时结果为正，判断的结果是seq1>seq2。因此，上述算法正确的前提是：回绕后的增量小于2^(n-1)-1。
```
由于TCP序列号用的32位无符号数，因此，可以支持的回绕幅度是2^31-1，满足要求

## 从tcpdump抓包看TCP/IP协议
1. 在192.168.100.211机器的6379端口启一个Redis服务
1. 通过tcpdump这个工具来抓取数据包，命令：`tcpdump -w /tmp/logs -i eth1 port 6379 -s0`
1. 在192.168.100.162机器上访问Redis实例192.168.100.211:6379，可以用redis-cli客户端，也可以用telnet，发送一个ping，得到对端回复pong
1. 停止抓包，用tcpdump读取数据包（-x以16进制形式展示）：`tcpdump -r /tmp/logs -n -nn -A -x| vim -`

```
16:00:14.937135 IP 192.168.100.162.56427 > 192.168.100.211.6379: Flags [P.], seq 3741217703:3741217717, ack 647125300, win 16423, length 14
        0x0000:  4500 0036 0685 4000 4006 e976 c0a8 64a2
        0x0010:  c0a8 64d3 dc6b 18eb defe 73a7 2692 5934
        0x0020:  5018 4027 08b9 0000 2a31 0d0a 2434 0d0a
        0x0030:  7069 6e67 0d0a
```

### IP层解析
字节值 | 字节含义
---|------
0x4 | IP版本为ipv4
0x5 | 首部长度为5 * 4字节=20B
0x00 | 服务类型，现在基本都置为0
0x0036 | 总长度为3*16+12=60字节，上面所有的长度就是60字节
0x0685 | 标识。同一个数据报的唯一标识。当IP数据报被拆分时，会复制到每一个数据中
0x4000 | 3bit标志 + 13bit片偏移。3bit标志对应R、DF、MF。目前只有后两位有效，DF位：为1表示不分片，为0表示分片。MF：为1表示“更多的片”，为0表示这是最后一片。13bit片位移：本分片在原先数据报文中相对首位的偏移位。（需要再乘以8)
0x40 | 生存时间TTL。IP报文所允许通过的路由器的最大数量。每经过一个路由器，TTL减1，当为0时，路由器将该数据报丢弃。TTL字段是由发送端初始设置一个8 bit字段。推荐的初始值由分配数字RFC指定。发送ICMP回显应答时经常把TTL设为最大值255。TTL可以防止数据报陷入路由循环。此处为54
0x06 | 协议类型。指出IP报文携带的数据使用的是哪种协议，以便目的主机的IP层能知道要将数据报上交到哪个进程。TCP的协议号为6，UDP的协议号为17。ICMP的协议号为1，IGMP的协议号为2。该IP报文携带的数据使用TCP协议，得到了验证
0xe976 | 16bitIP首部校验和
0xc0a8 64a2 | 32bit源ip地址
0xc0a8 64d3 | 32bit目的ip地址

### 传输层解析
字节值 | 字节含义
---|------
0xdc6b | 16bit源端口。56427
0x18eb | 16bit目的端口。6379
0xdefe 73a7 | 32bit序列号。3741217703
0x2692 5934 | 32bit确认号
0x5 | 4bit首部长度，以4byte为单位。共10*4=40字节。因此TCP报文的可选长度为40-20=20
0b000000 | 6bit保留位。目前置为0
0b000010 | 6bitTCP标志位。从左到右依次是紧急URG、确认ACK、推送PSH、复位RST、同步SYN、终止FIN
0x4027 | 滑动窗口大小，滑动窗口即tcp接收缓冲区的大小，用于tcp拥塞控制。16423
0x08b9 | 16bit校验和
0x0000 | 紧急指针。仅在URG=1时才有意义，它指出本报文段中的紧急数据的字节数。当URG=1时，发送方TCP就把紧急数据插入到本报文段数据的最前面，而在紧急数据后面的数据仍是普通数据

### 数据部分解析
```
0x2a31         -> *1
0x0d0a         -> \r\n
0x2434         -> $4
0x0d0a         -> \r\n
0x7069 0x6e67  -> ping
0x0d0a         -> \r\n
```

## References
- [“三次握手，四次挥手”你真的懂吗？](https://www.cnblogs.com/qcrao-2018/p/10182185.html)
- [IPv4 Header与TCP Header简介](https://www.jianshu.com/p/57ecf2add764)
- [tcp序列号回绕与解决](https://blog.csdn.net/hi_software/article/details/51768026)
- [从tcpdump抓包看TCP/IP协议](https://segmentfault.com/a/1190000015044878)
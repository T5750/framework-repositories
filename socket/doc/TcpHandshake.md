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

## ISN
ISN(Initial Sequence Number): `ISN = M + F(localhost, localport, remotehost, remoteport)`
- M是一个计时器，每隔4微秒加1
- F是一个Hash算法，根据源IP、目的IP、源端口、目的端口生成一个随机数值。要保证hash算法不能被外部轻易推算得出

## References
- [“三次握手，四次挥手”你真的懂吗？](https://www.cnblogs.com/qcrao-2018/p/10182185.html)
- [IPv4 Header与TCP Header简介](https://www.jianshu.com/p/57ecf2add764)
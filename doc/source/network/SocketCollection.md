# Socket网络编程网摘笔记

## 4种IO的对比

对比项 | BIO | 伪异步IO | NIO | AIO
---|---|---|---|---
客户端个数:IO线程 | 1:1 | M:N（M可以大于N） | M:1（1个IO线程处理多个客户端连接） | M:0（不需要启动额外的IO线程，被动回调）
IO类型（阻塞） | 阻塞IO | 阻塞IO | 非阻塞IO | 非阻塞IO
IO类型（同步） | 同步IO | 同步IO | 同步IO（IO多路复用） | 异步IO
API使用难度 | 简单 | 简单 | 非常复杂 | 复杂
调试难度 | 简单 | 简单 | 复杂 | 复杂
可靠性 | 非常差 | 差 | 高 | 高
吞吐量 | 低 | 中 | 高 | 高

## NIO服务端序列图
![nioServerSequenceDiagram-min](https://s0.wailian.download/2019/01/31/nioServerSequenceDiagram-min.png)

## NIO客户端序列图
![nioClientSequenceDiagram-min](https://s0.wailian.download/2019/01/31/nioClientSequenceDiagram-min.png)

## References
- [《Netty 权威指南》—— 4种IO的对比](http://ifeve.com/netty-2-5/)
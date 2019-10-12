# nginx网摘笔记

## split-brain脑裂问题（Keepalived）

### 脑裂（split-brain）
指在一个高可用（HA）系统中，当联系着的两个节点断开联系时，本来为一个整体的系统，分裂为两个独立节点，这时两个节点开始争抢共享资源，结果会导致系统混乱，数据损坏。

对于无状态服务的HA，无所谓脑裂不脑裂；但对有状态服务(比如MySQL)的HA，必须要严格防止脑裂。（但有些生产环境下的系统按照无状态服务HA的那一套去配置有状态服务，结果可想而知...）

### 如何防止HA集群脑裂
一般采用2个方法
1. 仲裁：当两个节点出现分歧时，由第3方的仲裁者决定听谁的。这个仲裁者，可能是一个锁服务，一个共享盘或者其它什么东西。
1. fencing：当不能确定某个节点的状态时，通过fencing把对方干掉，确保共享资源被完全释放，前提是必须要有可靠的fence设备。

理想的情况下，以上两者一个都不能少。

但是，如果节点没有使用共享资源，比如基于主从复制的数据库HA，也可以安全的省掉fence设备，只保留仲裁。而且很多时候我们的环境里也没有可用的fence设备，比如在云主机里。

那么可不可以省掉仲裁，只留fence设备呢？

不可以。因为，当两个节点互相失去联络时会同时fencing对方。如果fencing的方式是reboot，那么两台机器就会不停的重启。如果fencing的方式是power off，那么结局有可能是2个节点同归于尽，也有可能活下来一个。但是如果两个节点互相失去联络的原因是其中一个节点的网卡故障，而活下来的正好又是那个有故障的节点，那么结局一样是悲剧。

所以，单纯的双节点，无论如何也防止不了脑裂。

### 如何实现上面的策略
可以自己完全从头开始实现一套符合上述逻辑的脚本。推荐使用基于成熟的集群软件去搭建，比如Pacemaker+Corosync+合适的资源Agent。Keepalived不太适合用于有状态服务的HA，即使把仲裁和fence那些东西都加到方案里，总觉得别扭。

使用Pacemaker+Corosync的方案也有一些注意事项
1. 了解资源Agent的功能和原理：了解资源Agent的功能和原理，才能知道它适用的场景。比如pgsql的资源Agent是比较完善的，支持同步和异步流复制，并且可以在两者之前自动切换，并且可以保证同步复制下数据不会丢失。但目前MySQL的资源Agent就很弱了，没有使用GTID又没有日志补偿，很容易丢数据，还是不要用的好，继续用MHA吧（但是，部署MHA时务必要防范脑裂）。
1. 确保法定票数(quorum)：quorum可以认为是Pacemkaer自带的仲裁机制，集群的所有节点中的多数选出一个协调者，集群的所有指令都由这个协调者发出，可以完美的杜绝脑裂问题。为了使这套机制有效运转，集群中至少有3个节点，并且把no-quorum-policy设置成stop，这也是默认值。（很多教程为了方便演示，都把no-quorum-policy设置成ignore，生产环境如果也这么搞，又没有其它仲裁机制，是很危险的！）

但是，如果只有2个节点该怎么办？
1. 拉一个机子借用一下凑足3个节点，再设置location限制，不让资源分配到那个节点上。
1. 把多个不满足quorum小集群拉到一起，组成一个大的集群，同样适用location限制控制资源的分配的位置。

但是如果你有很多双节点集群，找不到那么多用于凑数的节点，又不想把这些双节点集群拉到一起凑成一个大的集群（比如觉得不方便管理）。那么可以考虑第三种方法。

第三种方法是配置一个抢占资源，以及服务和这个抢占资源的colocation约束，谁抢到抢占资源谁提供服务。这个抢占资源可以是某个锁服务，比如基于zookeeper包装一个，或者干脆自己从头做一个，就像下面这个例子。这个例子是基于http协议的短连接，更细致的做法是使用长连接心跳检测，这样服务端可以及时检出连接断开而释放锁）但是，一定要同时确保这个抢占资源的高可用，可以把提供抢占资源的服务做成lingyig高可用的，也可以简单点，部署3个服务，双节点上个部署一个，第三个部署在另外一个专门的仲裁节点上，至少获取3个锁中的2个才视为取得了锁。这个仲裁节点可以为很多集群提供仲裁服务（因为一个机器只能部署一个Pacemaker实例，否则可以用部署了N个Pacemaker实例的仲裁节点做同样的事情。但是，如非迫不得已，尽量还是采用前面的方法，即满足Pacemaker法定票数，这种方法更简单，可靠。

#### 1.解决Keepalived脑裂问题
检测思路：正常情况下Keepalived的VIP地址是在主节点上的，如果在从节点发现了VIP，就设置报警信息。脚本（在从节点上）如下：
```
[root@slave-ha ~]# vim split-brainc_check.sh
#!/bin/bash
# 检查脑裂的脚本，在备节点上进行部署
LB01_VIP=192.168.1.229
LB01_IP=192.168.1.129
LB02_IP=192.168.1.130
while true
do
  ping -c 2 -W 3 $LB01_VIP &>/dev/null
    if [ $? -eq 0 -a `ip add|grep "$LB01_VIP"|wc -l` -eq 1 ];then
        echo "ha is brain."
    else
        echo "ha is ok"
    fi
    sleep 5
done
```
执行结果如下：
```
[root@slave-ha ~]# bash check_split_brain.sh
ha is ok
ha is ok
ha is ok
ha is ok
```
当发现异常时候的执行结果：
```
[root@slave-ha ~]# bash check_split_brain.sh
ha is ok
ha is ok
ha is ok
ha is ok
ha is brain.
ha is brain.
```

#### 2.曾经碰到的一个Keepalived脑裂的问题（如果启用了`iptables`，不设置"系统接收VRRP协议"的规则，就会出现脑裂）
曾经在做Keepalived+nginx主备架构的环境时，当重启了备用机器后，发现两台机器都拿到了VIP。这也就是意味着出现了Keepalived的脑裂现象，检查了两台主机的网络连通状态，发现网络是好的。然后在备机上抓包：
```
[root@localhost ~]#  tcpdump -i eth0|grep VRRP 
tcpdump: verbose output suppressed, use -v or -vv for full protocol decode 
listening on eth0, link-type EN10MB (Ethernet), capture size 65535 bytes 
22:10:17.146322 IP 192.168.1.54 > vrrp.mcast.net: VRRPv2, Advertisement, vrid 51, prio 160, authtype simple, intvl 1s, length 20 
22:10:17.146577 IP 192.168.1.96 > vrrp.mcast.net: VRRPv2, Advertisement, vrid 51, prio 50, authtype simple, intvl 1s, length 20 
22:10:17.146972 IP 192.168.1.54 > vrrp.mcast.net: VRRPv2, Advertisement, vrid 51, prio 160, authtype simple, intvl 1s, length 20 
22:10:18.147136 IP 192.168.1.96 > vrrp.mcast.net: VRRPv2, Advertisement, vrid 51, prio 50, authtype simple, intvl 1s, length 20 
22:10:18.147576 IP 192.168.1.54 > vrrp.mcast.net: VRRPv2, Advertisement, vrid 51, prio 160, authtype simple, intvl 1s, length 20 
22:10:25.151399 IP 192.168.1.96 > vrrp.mcast.net: VRRPv2, Advertisement, vrid 51, prio 50, authtype simple, intvl 1s, length 20 
22:10:25.151942 IP 192.168.1.54 > vrrp.mcast.net: VRRPv2, Advertisement, vrid 51, prio 160, authtype simple, intvl 1s, length 20 
22:10:26.151703 IP 192.168.1.96 > vrrp.mcast.net: VRRPv2, Advertisement, vrid 51, prio 50, authtype simple, intvl 1s, length 20 
22:10:26.152623 IP 192.168.1.54 > vrrp.mcast.net: VRRPv2, Advertisement, vrid 51, prio 160, authtype simple, intvl 1s, length 20 
22:10:27.152456 IP 192.168.1.96 > vrrp.mcast.net: VRRPv2, Advertisement, vrid 51, prio 50, authtype simple, intvl 1s, length 20 
22:10:27.153261 IP 192.168.1.54 > vrrp.mcast.net: VRRPv2, Advertisement, vrid 51, prio 160, authtype simple, intvl 1s, length 20 
22:10:28.152955 IP 192.168.1.96 > vrrp.mcast.net: VRRPv2, Advertisement, vrid 51, prio 50, authtype simple, intvl 1s, length 20 
22:10:28.153461 IP 192.168.1.54 > vrrp.mcast.net: VRRPv2, Advertisement, vrid 51, prio 160, authtype simple, intvl 1s, length 20 
22:10:29.153766 IP 192.168.1.96 > vrrp.mcast.net: VRRPv2, Advertisement, vrid 51, prio 50, authtype simple, intvl 1s, length 20 
22:10:29.155652 IP 192.168.1.54 > vrrp.mcast.net: VRRPv2, Advertisement, vrid 51, prio 160, authtype simple, intvl 1s, length 20 
22:10:30.154275 IP 192.168.1.96 > vrrp.mcast.net: VRRPv2, Advertisement, vrid 51, prio 50, authtype simple, intvl 1s, length 20 
22:10:30.154587 IP 192.168.1.54 > vrrp.mcast.net: VRRPv2, Advertisement, vrid 51, prio 160, authtype simple, intvl 1s, length 20 
22:10:31.155042 IP 192.168.1.96 > vrrp.mcast.net: VRRPv2, Advertisement, vrid 51, prio 50, authtype simple, intvl 1s, length 20 
22:10:31.155428 IP 192.168.1.54 > vrrp.mcast.net: VRRPv2, Advertisement, vrid 51, prio 160, authtype simple, intvl 1s, length 20 
22:10:32.155539 IP 192.168.1.96 > vrrp.mcast.net: VRRPv2, Advertisement, vrid 51, prio 50, authtype simple, intvl 1s, length 20 
22:10:32.155986 IP 192.168.1.54 > vrrp.mcast.net: VRRPv2, Advertisement, vrid 51, prio 160, authtype simple, intvl 1s, length 20 
22:10:33.156357 IP 192.168.1.96 > vrrp.mcast.net: VRRPv2, Advertisement, vrid 51, prio 50, authtype simple, intvl 1s, length 20 
22:10:33.156979 IP 192.168.1.54 > vrrp.mcast.net: VRRPv2, Advertisement, vrid 51, prio 160, authtype simple, intvl 1s, length 20 
22:10:34.156801 IP 192.168.1.96 > vrrp.mcast.net: VRRPv2, Advertisement, vrid 51, prio 50, authtype simple, intvl 1s, length 20 
22:10:34.156989 IP 192.168.1.54 > vrrp.mcast.net: VRRPv2, Advertisement, vrid 51, prio 160, authtype simple, intvl 1s, length 20 
```
备机能接收到master发过来的VRRP广播，那为什么还会有脑裂现象？接着发现重启后`iptables`开启着，检查了防火墙配置。发现系统不接收VRRP协议。于是修改`iptables`，添加允许系统接收VRRP协议的配置：
```
-A INPUT -i lo -j ACCEPT  
```
我自己添加了下面的`iptables`规则：
```
-A INPUT -s 192.168.1.0/24 -d 224.0.0.18 -j ACCEPT       #允许组播地址通信
-A INPUT -s 192.168.1.0/24 -p vrrp -j ACCEPT             #允许VRRP（虚拟路由器冗余协）通信
```
最后重启`iptables`，发现备机上的VIP没了。虽然问题解决了，但备机明明能抓到master发来的VRRP广播包，却无法改变自身状态。只能说明网卡接收到数据包是在`iptables`处理数据包之前发生的事情。

#### 3.预防Keepalived脑裂问题
1. 可以采用第三方仲裁的方法。由于Keepalived体系中主备两台机器所处的状态与对方有关。如果主备机器之间的通信出了网题，就会发生脑裂，此时Keepalived体系中会出现双主的情况，产生资源竞争。
1. 一般可以引入仲裁来解决这个问题，即每个节点必须判断自身的状态。最简单的一种操作方法是，在主备的Keepalived的配置文件中增加check配置，服务器周期性地`ping`一下网关，如果`ping`不通则认为自身有问题。
1. 最容易的是借助Keepalived提供的`vrrp_script`及`track_script`实现。如下所示：

```
# vim /etc/keepalived/keepalived.conf
   ......
   vrrp_script check_local {
    script "/root/check_gateway.sh"
    interval 5
    }
   ......
 
   track_script {    
   check_local                  
   }
 ```
脚本内容：
```
# cat /root/check_gateway.sh
#!/bin/sh
VIP=$1
GATEWAY=192.168.1.1
/sbin/arping -I em1 -c 5 -s $VIP $GATEWAY &>/dev/null
```
`check_gateway.sh`就是我们的仲裁逻辑，发现`ping`不通网关，则关闭Keepalived：`service keepalived stop`。

#### 4.推荐自己写脚本
写一个`while`循环，每轮`ping`网关，累计连续失败的次数，当连续失败达到一定次数则运行`service keepalived stop`关闭Keepalived服务。如果发现又能够`ping`通网关，再重启Keepalived服务。最后在脚本开头再加上脚本是否已经运行的判断逻辑，将该脚本加到`crontab`里面。

## References
- [split-brain 脑裂问题（Keepalived）](http://www.cnblogs.com/kevingrace/p/7205846.html)
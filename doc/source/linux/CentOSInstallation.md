# CentOS Installation

## Oracle VM VirtualBox

### Create
1. 新建
    - 类型：Linux
    - 版本：Red Hat（64-bit）
1. 内存大小：1024MB
1. 现在创建虚拟硬盘
1. VDI
1. 固定大小
1. 20GB
1. 创建

### Settings
1. 存储，属性，分配光驱：CentOS-6.10-x86_64-bin-DVD1.iso
1. 网络，网卡1，连接方式：桥接网卡

## CentOS 6.x

### Installation
1. Install or upgrade an existing system
1. Disc Found: Skip
1. Next
1. English
1. U.S. English
1. Basic Storage Devices
1. Yes, discard any data
1. Hostname
1. Configure Network
    - System eth0, Edit
    - Connect automatically
    - IPv4 Settings:
        - Method: Manual
        - Address: 192.168.1.100
        - Netmask: 255.255.255.0
        - Gateway: 192.168.1.1
        - DNS servers: 192.168.1.1
1. Asia/Shanghai
1. Root Password
1. Create Custom Layout
1. Hard Drives:
    - /: 10240MB
    - swap: 1024MB(File System Type: swap)
    - /home: Fill to maximum allowable size
1. Format
1. Write changes to disk
1. Next
1. Desktop

### Welcome
1. Forward
1. Yes, I agree to the License Agreement
1. Username, Password
1. Data and Time: Forward
1. Kdump: Finish

## CentOS 8.x
Oracle VM VirtualBox: 30GB

### Installation
1. Install CentOS Linux 8
2. Continue
3. Time & Date: Asia/Shanghai
4. Installation Destination -> Storage Configuration: Custom
5. Done
6. Standard Partition:
    - /: 20480
    - /boot: 1024
    - swap: 1024
    - /home:
7. Done -> Accept Changes
8. Network & Host Name:
    - Host Name: centos8
    - Ethernet: ON
    - Configure...
9. Begin Installation
10. Root Password
11. User Creation: Make this user administrator
12. Reboot

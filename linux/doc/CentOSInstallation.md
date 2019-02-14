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

## CentOS

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
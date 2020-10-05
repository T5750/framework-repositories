# Docker Desktop for Windows

[Download from Docker Hub](https://hub.docker.com/editions/community/docker-ce-desktop-windows/)

## What to know before you install
### System Requirements
- Windows 10 64-bit: Pro, Enterprise, or Education (Build 16299 or later).
- Hyper-V and Containers Windows features must be enabled.
- The following hardware prerequisites are required to successfully run Client Hyper-V on Windows 10

## Install Docker Desktop on Windows
1. Double-click **Docker Desktop Installer.exe** to run the installer.
2. When prompted, ensure the **Enable Hyper-V Windows Features** option is selected on the Configuration page.
3. Follow the instructions on the installation wizard to authorize the installer and proceed with the install.
4. When the installation is successful, click **Close** to complete the installation process.
5. If your admin account is different to your user account, you must add the user to the **docker-users** group. Run **Computer Management** as an administrator and navigate to **Local Users and Groups > Groups > docker-users**. Right-click to add the user to the group. Log out and log back in for the changes to take effect.

## Config Desktop
1. Settings -> Resources -> ADVANCED -> Disk image location -> Browse
2. Settings -> Docker Engine
```
{
  "registry-mirrors": ["https://docker.mirrors.ustc.edu.cn/","https://hub-mirror.c.163.com","https://registry.docker-cn.com"],
  "insecure-registries": [],
  "debug": true,
  "experimental": false
}
```

## References
- [Install Docker Desktop on Windows](https://docs.docker.com/docker-for-windows/install/)
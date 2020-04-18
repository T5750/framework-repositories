# Keycloak Guide

## Installing and Booting

### Installing Distribution Files
[Keycloak downloads](https://www.keycloak.org/downloads.html)

Linux/Unix
```
$ unzip keycloak-9.0.3.zip
or
$ tar -xvzf keycloak-9.0.3.tar.gz
```
Windows
```
> unzip keycloak-9.0.3.zip
```

### Booting the Server
Linux/Unix
```
$ cd bin
$ ./standalone.sh
```
Windows
```
> ...\bin\standalone.bat
```

### Creating the Admin Account
http://localhost:8080/auth

### Log in to the Admin Console
http://localhost:8080/auth/admin/

## Creating a Realm and User

### Creating a New Realm
1. From the **Master** drop-down menu, click **Add Realm**. When you are logged in to the master realm this drop-down menu lists all existing realms.
2. Type `Security` in the **Name** field and click **Create**.

### Creating a New User
1. From the menu, click **Users** to open the user list page.
2. On the right side of the empty user list, click **Add User** to open the add user page.
3. Enter a name in the Username field; this is the only required field. Flip the **Email Verified** switch from Off to On and click **Save** to save the data and open the management page for the new user.
4. Click the **Credentials** tab to set a temporary password for the new user.
5. Type a new password and confirm it. Click **Reset Password** to set the user password to the new one you specified.

>This password is temporary and the user will be required to change it after the first login. To create a password that is persistent, flip the **Temporary** switch from **On** to **Off** before clicking **Reset Password**.

### User Account Service
1. After you create the new user, log out of the management console by opening the user drop-down menu and selecting **Sign Out**.
2. Go to http://localhost:8080/auth/realms/Security/account and log in to the User Account Service of your `Security` realm with the user you just created.
3. Type the username and password you created. You will be required to create a permanent password after you successfully log in, unless you changed the **Temporary** setting to **Off** when you created the password.

## Securing a JBoss Servlet Application
To start the Keycloak server while also adjusting the port:

Linux/Unix
```
$ cd bin
$ ./standalone.sh -Djboss.socket.binding.port-offset=100
```
Windows
```
> ...\bin\standalone.bat -Djboss.socket.binding.port-offset=100
```
After starting Keycloak, go to http://localhost:8180/auth/admin/ to access the admin console.

## Documentation

Docs | Info
---|---
[Release Notes](https://www.keycloak.org/docs/latest/release_notes/index.html) | 9.0.3
[Getting Started Guide](https://www.keycloak.org/docs/latest/getting_started/index.html) | How to get started with Keycloak
[Server Installation and Configuration](https://www.keycloak.org/docs/latest/server_installation/index.html) | Installation and offline configuration of the Keycloak server
[Server Container Image](https://github.com/keycloak/keycloak-containers/blob/9.0.3/server/README.md) | Documentation specific to the server container image
[Securing Applications and Services Guide](https://www.keycloak.org/docs/latest/securing_apps/index.html) | How to secure applications and services with Keycloak
[Server Administration](https://www.keycloak.org/docs/latest/server_admin/index.html) | Management and runtime configuration of the Keycloak server
[Server Developer](https://www.keycloak.org/docs/latest/server_development/index.html) | Creating themes and providers to customize the Keycloak server
[Authorization Services](https://www.keycloak.org/docs/latest/authorization_services/index.html) | Centrally manage fine-grained permissions for applications and services
[Upgrading](https://www.keycloak.org/docs/latest/upgrading/index.html) | Upgrading Keycloak server and adapters
[JavaDoc](https://www.keycloak.org/docs-api/9.0/javadocs/index.html) | Documentation for Java API
[Administration REST API](https://www.keycloak.org/docs-api/9.0/rest-api/index.html) | Documentation for the Adminstration RESTful API

## References
- [Keycloak - Documentation](https://www.keycloak.org/documentation)
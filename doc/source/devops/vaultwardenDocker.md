# Vaultwarden Docker

Alternative implementation of the Bitwarden server API in Rust, including the Web Vault.

This project is not associated with the [Bitwarden](https://bitwarden.com/) project nor 8bit Solutions LLC.

## Docker
```sh
docker run -d --name vaultwarden -v /vw-data/:/data/ -p 8080:80 vaultwarden/server
```
[http://localhost:8080/](http://localhost:8080/)

## Enabling admin page
To enable the admin page, you need to set an authentication token. This token can be anything, but it's recommended to use a long, randomly generated string of characters, for example running `openssl rand -base64 48`. **Keep this token secret, this is the password to access the admin area of your server!**
```sh
docker run -d --name vaultwarden \
  -e ADMIN_TOKEN=some_random_token_as_per_above_explanation \
  -v /vw-data/:/data/ \
  -p 80:80 \
  vaultwarden/server
```
After this, the page will be available in the `/admin` subdirectory.

## Enabling HTTPS
```sh
docker run -d --name bitwarden \
  -e ROCKET_TLS='{certs="/ssl/certs.pem",key="/ssl/key.pem"}' \
  -v /ssl/keys/:/ssl/ \
  -v /vw-data/:/data/ \
  -p 443:80 \
  vaultwarden/server
```

## Backing up data
```
data
├── attachments          # Each attachment is stored as a separate file under this dir.
│   └── <uuid>           # (The attachments dir won't be present if no attachments have been created.)
│       └── <random_id>
├── config.json          # Stores admin page config; only exists if the admin page has been enabled before.
├── db.sqlite3           # Main SQLite database file.
├── db.sqlite3-shm       # SQLite shared memory file (not always present).
├── db.sqlite3-wal       # SQLite write-ahead log file (not always present).
├── icon_cache           # Site icons (favicons) are cached under this dir.
│   ├── <domain>.png
│   ├── example.com.png
│   ├── example.net.png
│   └── example.org.png
├── rsa_key.der          # `rsa_key.*` files are used to sign authentication tokens.
├── rsa_key.pem
├── rsa_key.pub.der
└── sends                # Each Send attachment is stored as a separate file under this dir.
    └── <uuid>           # (The sends dir won't be present if no Send attachments have been created.)
        └── <random_id>
```

## Screenshots
![](https://user-images.githubusercontent.com/72935517/147185157-e6e3be2f-9f12-482a-9911-5ac27d63f0c6.png)

## References
- [Vaultwarden Docker](https://hub.docker.com/r/vaultwarden/server)
- [Vaultwarden GitHub](https://github.com/dani-garcia/vaultwarden)
- [Enabling admin page](https://github.com/dani-garcia/vaultwarden/wiki/Enabling-admin-page)
- [Enabling HTTPS](https://github.com/dani-garcia/vaultwarden/wiki/Enabling-HTTPS)
- [Backing up your vault](https://github.com/dani-garcia/vaultwarden/wiki/Backing-up-your-vault)
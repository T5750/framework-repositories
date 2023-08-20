# iRedMail Docker

Open Source Mail Server Solution

## Docker
__WARNING__: THIS IS A BETA EDITION AND NOT ALWAYS STABLE, DO NOT TRY IT IN PRODUCTION (YET).

```sh
mkdir iredmail
cd iredmail
touch iredmail-docker.conf
echo HOSTNAME=mail.mydomain.com >> iredmail-docker.conf
echo FIRST_MAIL_DOMAIN=mydomain.com >> iredmail-docker.conf
echo FIRST_MAIL_DOMAIN_ADMIN_PASSWORD=my-secret-password >> iredmail-docker.conf
echo MLMMJADMIN_API_TOKEN=$(openssl rand -base64 32) >> iredmail-docker.conf
echo ROUNDCUBE_DES_KEY=$(openssl rand -base64 24) >> iredmail-docker.conf
echo MYSQL_ROOT_PASSWORD=123456 >> iredmail-docker.conf
```
```sh
docker run -d --name iredmail \
    --env-file iredmail-docker.conf \
    --hostname mail.mydomain.com \
    -p 8080:80 \
    -p 443:443 \
    -p 110:110 \
    -p 995:995 \
    -p 143:143 \
    -p 993:993 \
    -p 25:25 \
    -p 465:465 \
    -p 587:587 \
    -v $pwd/data/backup-mysql:/var/vmail/backup/mysql \
    -v $pwd/data/mailboxes:/var/vmail/vmail1 \
    -v $pwd/data/mlmmj:/var/vmail/mlmmj \
    -v $pwd/data/mlmmj-archive:/var/vmail/mlmmj-archive \
    -v $pwd/data/imapsieve_copy:/var/vmail/imapsieve_copy \
    -v $pwd/data/custom:/opt/iredmail/custom \
    -v $pwd/data/ssl:/opt/iredmail/ssl \
    -v $pwd/data/mysql:/var/lib/mysql \
    -v $pwd/data/clamav:/var/lib/clamav \
    -v $pwd/data/sa_rules:/var/lib/spamassassin \
    -v $pwd/data/postfix_queue:/var/spool/postfix \
    iredmail/mariadb:stable
```
- iRedMail: [https://localhost/mail](http://localhost/mail)
- iRedAdmin: [https://localhost/iredadmin](http://localhost/iredadmin)
- User: postmaster@mydomain.com / my-secret-password

### Installed softwares
- Postfix: SMTP server.
- Dovecot: POP3/IMAP/LMTP/Sieve server, also offers SASL AUTH service for Postfix.
- mlmmj: mailing list manager.
- mlmmjadmin: RESTful API server used to manage (mlmmj) mailing lists.
- Amavisd-new + ClamAV + SpamAssassin: anti-spam and anti-virus, DKIM signing and verification, etc.
- iRedAPD: Postfix policy server. Developed by iRedMail team.
- Fail2ban: scans log files and bans bad clients.
- Roundcube: webmail.
- iRedAdmin: web-based admin panel, open source edition.

You may want to check [this tutorial](https://docs.iredmail.org/network.ports.html)
to figure out the mapping of softwares and network ports.

## Exposed network ports
- 80: HTTP
- 443: HTTPS
- 25: SMTP
- 465: SMTPS (SMTP over SSL)
- 587: SUBMISSION (SMTP over TLS)
- 143: IMAP over TLS
- 993: IMAP over SSL
- 110: POP3 over TLS
- 995: POP3 over SSL
- 4190: Managesieve service

## References
- [iRedMail](https://www.iredmail.org/)
- [iRedMail GitHub](https://github.com/iredmail/iRedMail)
- [iRedMail Docker](https://github.com/iredmail/dockerized)
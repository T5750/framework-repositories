# CentOS Mail

## Installation
```
yum -y install sendmail
service sendmail start
yum install -y mailx
```

## Configuration
`vi /etc/mail.rc`
```
set from=username@sina.com
set smtp=smtp.sina.com
set smtp-auth-user=username
set smtp-auth-password=authorization_code
set smtp-auth=login
```

### Sina mail
设置 -> 客户端pop/imap/smtp -> 客户端授权码 -> 开启: authorization_code

## Send mail
1. 通过文件内容发送: `mail -s 'Mail Title' xxx@yyy.com < content.txt`
2. 通过管道符直接发送: `echo "This is my test mail" | mail -s 'Mail Title' xxx@yyy.com`

## References
- [centos下如何使用sendmail发送邮件](https://www.cnblogs.com/hanganglin/p/6510216.html)
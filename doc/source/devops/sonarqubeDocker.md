# SonarQube Docker

[SonarQube](https://www.sonarqube.org/) is the leading tool for continuously inspecting the Code Quality and Security of your codebases, and guiding development teams during Code Reviews. Covering 27 programming languages, while pairing-up with your existing software pipeline, SonarQube provides clear remediation guidance for developers to understand and fix issues, and for teams overall to deliver better and safer software.

## Requirements
由于 SonarQube 使用 Elasticsearch 作为全文模糊搜索引擎，故需要设置如下内核参数
```
# 查看
$ sysctl vm.max_map_count
$ sysctl fs.file-max
$ ulimit -n
$ ulimit -u

# 实时修改生效
$ sysctl -w vm.max_map_count=262144
$ sysctl -w fs.file-max=65536
$ ulimit -n 65536
$ ulimit -u 4096

# 永久生效
$ echo "sonar   -   nofile   65536
sonar   -   nproc    4096" > /etc/security/limits.d/99-sonarqube.conf
$ echo "vm.max_map_count=262144
fs.file-max=65536" > /etc/sysctl.d/99-sonarqube.conf
```

## Docker Compose
`sonarqube.yml`

- [http://localhost:9000/sonar](http://localhost:9000/sonar)
- User: admin / admin

## nginx
```
location ^~ /sonar {
    proxy_pass http://x.x.x.x:9000/sonar;
    sendfile off;
    proxy_set_header   Host             $host:$server_port;
    proxy_set_header   X-Real-IP        $remote_addr;
    proxy_set_header   X-Forwarded-For  $proxy_add_x_forwarded_for;
    proxy_max_temp_file_size 0;
    # This is the maximum upload size
    client_max_body_size       50m;
    client_body_buffer_size    128k;
    proxy_connect_timeout      90;
    proxy_send_timeout         90;
    proxy_read_timeout         90;
    proxy_temp_file_write_size 64k;
    # Required for new HTTP-based CLI
    proxy_http_version 1.1;
    proxy_request_buffering off;
    proxy_buffering off; # Required for HTTP-based CLI to work over SSL
}
```

## Screenshots
![](https://docs.sonarqube.org/latest/images/successfulproject.png)

![](https://www.sonarqube.org/sonarqube-7-5/index/pull-request.png)

## References
- [Docker Compose 方式安装 SonarQube 8.3.1](https://www.cnblogs.com/daodaotest/p/13123561.html)
- [SonarSource](https://github.com/SonarSource/docker-sonarqube)
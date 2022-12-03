# nginx Util

## Path
```
vi ~/.bashrc
export NGINX_HOME=/usr/local/nginx
export PATH=${JAVA_HOME}/bin:${NGINX_HOME}/sbin:$PATH
source ~/.bashrc
```

## Uninstall
```
ps -ef|grep nginx
find / -name nginx
rm -rf /usr/local/nginx/
rm -rf /usr/local/nginx-1.6.2/
```

## Command
- `-? | -h` — print help for command-line parameters.
- `-c file` — use an alternative configuration file instead of a default file.
- `-g directives` — set global configuration directives, for example,
>nginx -g "pid /var/run/nginx.pid; worker_processes `sysctl -n hw.ncpu`;"
- `-p prefix` — set nginx path prefix, i.e. a directory that will keep server files (default value is /usr/local/nginx).
- `-q` — suppress non-error messages during configuration testing.
- `-s signal` — send a signal to the master process. The argument signal can be one of:
    - `stop` — shut down quickly
    - `quit` — shut down gracefully
    - `reload` — reload configuration, start the new worker process with a new configuration, gracefully shut down old worker processes.
    - `reopen` — reopen log files
- `-t` — test the configuration file: nginx checks the configuration for correct syntax, and then tries to open files referred in the configuration.
- `-T` — same as `-t`, but additionally dump configuration files to standard output (1.9.2).
- `-v` — print nginx version.
- `-V` — print nginx version, compiler version, and configure parameters.

## References
- [Command-line parameters](http://nginx.org/en/docs/switches.html)
- [Alphabetical index of directives](http://nginx.org/en/docs/dirindex.html)
- [Alphabetical index of variables](http://nginx.org/en/docs/varindex.html)
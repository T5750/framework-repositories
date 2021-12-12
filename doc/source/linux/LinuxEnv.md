# Linux env

## env command
Syntax:
```
env [OPTION]... [-][NAME=VALUE]... [COMMAND [ARG]...]
```
Options of env command
1. **Without any argument**: print out a list of all environment variables
   - Syntax: `env`
2. **-i or –ignore-environment or only –**: runs a command with an empty environment
    - Syntax: `env -i your_command`
    - Example: `env -i /bin/sh`
3. **-u or –unset**: remove variable from the environment
    - Syntax: `env -u variable_name`
4. **-0 or –null**: End each output line with NULL, not newline
    - Syntax: `env -0`
5. **–version**: Display version information and exit
    - Syntax: `env --version`
6. **–help**: Display a help message and exit
    - Syntax: `env --help`

## Environment Variables

### To set a global ENV
```
$ export NAME=Value
or
$ set NAME=Value
```

### To set a local ENV
```
$ NAME=Value
```

### To set user wide ENVs
`~/.bashrc`, `~/.bash_profile`, `~/.bash_login`, `~/.profile`
```
$ sudo vi ~/.bashrc
export NAME=Value
$ source ~/.bashrc
```

### To set system wide ENVs
`/etc/environment`, `/etc/profile`, `/etc/profile.d/`, `/etc/bash`
```
$ sudo -H vi /etc/environment
NAME=Value
```

### Unset environment variables
```
$ unset NAME
or
$ NAME=''
```

### Some commonly used ENVs in Linux
```
$USER: Gives current user's name.
$PATH: Gives search path for commands.
$PWD: Gives the path of present working directory.
$HOME: Gives path of home directory.
$HOSTNAME: Gives name of the host.
$LANG: Gives the default system language.
$EDITOR: Gives default file editor.
$UID: Gives user ID of current user.
$SHELL: Gives location of current user's shell program.
```

### Example
```shell
echo $HOSTNAME
echo $SHELL
echo $HISTSIZE
echo $USER
echo $PATH
echo $PWD
echo $LANG
echo $HOME
echo $RANDOM
```

## References
- [env command in Linux with Examples](https://www.geeksforgeeks.org/env-command-in-linux-with-examples/)
- [Environment Variables in Linux/Unix](https://www.geeksforgeeks.org/environment-variables-in-linux-unix/)
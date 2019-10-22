# Kettle网摘笔记

## Hello World
Hello World With Java & Pentaho: `HelloWorldTest`

## Carte
### Command line options
```
carte.sh 127.0.0.1 8081
carte.bat 127.0.0.1 8081
 
carte.sh /Pentaho/Kettle/slave_dyn_8082.xml
carte.bat \Pentaho\Kettle\slave_dyn_8082.xml
```

### Security
The default user and password to use to gain control is `cluster`.
```
pwd/kettle.pwd
```

From version 3.1 on you can also put this password file in `$HOME/.kettle/` or `$KETTLE_HOME/.kettle/`

It is possible to obfuscate the password in the `kettle.pwd` file. We have a tool called `Encr` in the distribution that allows you to generate passwords that are obfuscated

For example:
```
sh encr.sh -carte my-l33t-passwd
OBF:1uh420ld1kqh1x881idt1lc21l1i1kxk1lfg1igd1x8m1kud20l91ugm
```

## URLs
- [http://127.0.0.1:8081/kettle](http://127.0.0.1:8081/kettle) <-- Home page
- [http://127.0.0.1:8081/kettle/status](http://127.0.0.1:8081/kettle/status) <-- status page
- [http://127.0.0.1:8081/kettle/status?xml=true](http://127.0.0.1:8081/kettle/status?xml=true) <-- machine readable status page
- [http://127.0.0.1:8081/kettle/getSlaves](http://127.0.0.1:8081/kettle/getSlaves) <-- get a list of the registered slaves

## References
- [Hello World With Java & Pentaho](http://ameethpaatil.blogspot.com/2011/08/hello-world-with-pentaho.html)
- [Running pentaho transformation stored in DB reposiotry from Java](http://ameethpaatil.blogspot.com/2010/11/pentaho-data-integration-java-maven.html)
- [Carte User Documentation](https://wiki.pentaho.com/display/EAI/Carte+User+Documentation)
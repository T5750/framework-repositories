# CentOS Python

## CentOS 8 Platform Python
```
find / -name platform-python* 2>/dev/null
sudo ln -s /usr/libexec/platform-python /usr/bin/python
python -V
sudo python -m pip install --upgrade pip
pip -V
```

## Python Installation
```
yum -y install zlib-devel bzip2-devel openssl-devel ncurses-devel sqlite-devel readline-devel tk-devel gdbm-devel db4-devel libpcap-devel xz-devel
yum install libffi-devel
wget https://www.python.org/ftp/python/3.7.4/Python-3.7.4.tgz
mkdir -p /usr/local/python3
tar -zxvf Python-3.7.4.tgz
cd /usr/local/software/Python-3.7.4
./configure --enable-optimizations --prefix=/usr/local/python3
make && make install
ln -s /usr/local/Python-3.7.4/bin/python3 /usr/bin/python3
vi ~/.bashrc
export PYTHON3_HOME=/usr/local/python3
export PATH=${JAVA_HOME}/bin:${NGINX_HOME}/sbin:${REDIS_HOME}/src:$ZOOKEEPER_HOME/bin:$PYTHON3_HOME/bin:$PATH
python3 -V
pip3 -V
```

### setuptools
```
unzip setuptools-41.2.0.zip
cd /usr/local/setuptools-41.2.0
python3 setup.py build
python3 setup.py install
```

### pip
```
tar -zxvf pip-19.2.3.tar.gz -C /usr/local/
cd /usr/local/pip-19.2.3
python3 setup.py build
python3 setup.py install
```

## Python 3.9.x
```
sudo yum -y install zlib-devel bzip2-devel openssl-devel ncurses-devel sqlite-devel readline-devel tk-devel gdbm-devel xz-devel libffi-devel
#No match for argument: db4-devel
#No match for argument: libpcap-devel
sudo yum -y install gcc-c++ make
wget https://www.python.org/ftp/python/3.9.12/Python-3.9.12.tgz
mkdir python && sudo mv python /usr/local/
tar -zxvf Python-3.9.12.tgz
cd Python-3.9.12
./configure --enable-optimizations --prefix=/usr/local/python
make && make install
vi ~/.bashrc
export PYTHON_HOME=/usr/local/python
export PATH=$PYTHON_HOME/bin:$PATH
python3 -V
pip3 -V
sudo ln -s /usr/local/python/bin/python3 /usr/bin/python
sudo ln -s /usr/local/python/bin/pip3 /usr/bin/pip
python -V
pip -V
```

## Runtime Environment
- [CentOS 8](https://www.centos.org/download/)
- [Python 3.9.x](https://www.python.org/downloads/)
- [pip 22.x](https://pypi.python.org/pypi/pip#downloads)
- [setuptools 58.x](https://pypi.python.org/pypi/setuptools#downloads)

## References
- [Linux安装python3.6](https://www.cnblogs.com/kimyeee/p/7250560.html)
- [No module named '_ctypes'/ssl module in Python is not available](https://www.cnblogs.com/momolei/p/9895218.html)
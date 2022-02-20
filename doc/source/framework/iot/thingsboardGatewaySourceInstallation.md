# ThingsBoard Gateway installation from sources

## Installation from sources
**1. Install required libraries to the system with apt:**
```bash
sudo apt install python3-dev python3-pip libglib2.0-dev git 
```

**2. Download repository from Github:**
```bash
git clone https://github.com/thingsboard/thingsboard-gateway.git
```

**3. Move into downloaded directory:**
```bash
cd thingsboard-gateway
```

**4. Install python module with setup.py script:**
```bash
python3 setup.py install
```

**5. Create "logs" folder:**
```bash
mkdir logs
```

**6. Configure gateway to work with your instance of ThingsBoard platform, using [this guide](https://thingsboard.io/docs/iot-gateway/configuration/)** *or just run to test result of installation such as in the next step.*
 
**7. Run gateway, to check installation result:**
```bash
python3 ./thingsboard_gateway/tb_gateway.py
```

## Tips
```
python setup.py install
python ./thingsboard_gateway/tb_gateway.py
```

## References
- [IoT Gateway installation from sources](https://thingsboard.io/docs/iot-gateway/install/source-installation/)
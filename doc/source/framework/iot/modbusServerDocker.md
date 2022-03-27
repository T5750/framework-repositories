# Modbus TCP Server Docker

The Modbus TCP Server is a simple Modbus server for debugging and simulation.

## Docker
```
docker run --name modbus-server -p 5020:5020 -d oitc/modbus-server
```

## Docker Compose
```
    modbus-server:
      container_name: modbus-server
      image: oitc/modbus-server
      restart: always
      command: -f /server_config.json
      ports:
        - 5020:5020
      volumes:
        - ./server.json:/server_config.json:ro
```

## Configuration
An example can be found in the GIT repo: [abb_coretec_example.json](https://bitbucket.org/Cybcon/modbus-server/src/master/examples/abb_coretec_example.json)

### Default configuration
```
{
"server": {
  "listenerAddress": "0.0.0.0",
  "listenerPort": 5020,
  "tlsParams": {
    "description": "path to certificate and private key to enable tls",
    "privateKey": null,
    "certificate": null
    },
  "logging": {
    "format": "%(asctime)-15s %(threadName)-15s  %(levelname)-8s %(module)-15s:%(lineno)-8s %(message)s",
    "logLevel": "DEBUG"
    }
  },
"registers": {
  "description": "initial values for the register types",
  "zeroMode": false,
  "initializeUndefinedRegisters": true,
  "discreteInput": {},
  "coils": {},
  "holdingRegister": {},
  "inputRegister": {}
  }
}
```

### Predefined registers
```
{
"server": {
  "listenerAddress": "0.0.0.0",
  "listenerPort": 5020,
  "tlsParams": {
    "description": "path to certificate and private key to enable tls",
    "privateKey": null,
    "certificate": null
    },
  "logging": {
    "format": "%(asctime)-15s %(threadName)-15s  %(levelname)-8s %(module)-15s:%(lineno)-8s %(message)s",
    "logLevel": "DEBUG"
    }
  },
"registers": {
  "description": "initial values for the register types",
  "zeroMode": false,
  "initializeUndefinedRegisters": true,
  "discreteInput": {},
  "coils": {},
  "holdingRegister": {
    "123": "0xAABB",
    "246": "0x0101"
  },
  "inputRegister": {}
  }
}
```

## References
- [oitc/modbus-server](https://hub.docker.com/r/oitc/modbus-server)
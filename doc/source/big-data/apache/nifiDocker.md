# NiFi Docker

An easy to use, powerful, and reliable system to process and distribute data

## Docker
```sh
docker run --name nifi \
  -p 8443:8443 \
  -d \
  apache/nifi:latest
docker logs nifi | grep Generated
```
[https://localhost:8443/nifi](https://localhost:8443/nifi)

Single User Authentication credentials can be specified using environment variables as follows:
```sh
docker run --name nifi \
  -p 8443:8443 \
  -d \
  -e SINGLE_USER_CREDENTIALS_USERNAME=admin \
  -e SINGLE_USER_CREDENTIALS_PASSWORD=ctsBtRBKHRAx69EqUghvvgEvjnaLjFEB \
  apache/nifi:latest
```

### Standalone Instance secured with HTTPS and LDAP Authentication
```sh
docker run --name nifi \
  -v /User/dreynolds/certs/localhost:/opt/certs \
  -p 8443:8443 \
  -e AUTH=ldap \
  -e KEYSTORE_PATH=/opt/certs/keystore.jks \
  -e KEYSTORE_TYPE=JKS \
  -e KEYSTORE_PASSWORD=QKZv1hSWAFQYZ+WU1jjF5ank+l4igeOfQRp+OSbkkrs \
  -e TRUSTSTORE_PATH=/opt/certs/truststore.jks \
  -e TRUSTSTORE_PASSWORD=rHkWR1gDNW3R9hgbeRsT3OM3Ue0zwGtQqcFKJD2EXWE \
  -e TRUSTSTORE_TYPE=JKS \
  -e INITIAL_ADMIN_IDENTITY='cn=admin,dc=example,dc=org' \
  -e LDAP_AUTHENTICATION_STRATEGY='SIMPLE' \
  -e LDAP_MANAGER_DN='cn=admin,dc=example,dc=org' \
  -e LDAP_MANAGER_PASSWORD='password' \
  -e LDAP_USER_SEARCH_BASE='dc=example,dc=org' \
  -e LDAP_USER_SEARCH_FILTER='cn={0}' \
  -e LDAP_IDENTITY_STRATEGY='USE_DN' \
  -e LDAP_URL='ldap://ldap:389' \
  -d \
  apache/nifi:latest
```

## Runtime Environment
- [Java 21](https://openjdk.java.net/projects/jdk/11/)
- [TypeScript](https://www.typescriptlang.org/)

## Architecture
![](https://nifi.apache.org/nifi-docs/images/zero-leader-node.png)

![](https://nifi.apache.org/nifi-docs/images/zero-leader-cluster.png)

## Screenshots
![](https://nifi.apache.org/images/main-hero.svg)

## References
- [NiFi](https://nifi.apache.org/)
- [NiFi GitHub](https://github.com/apache/nifi)
- [NiFi Docker](https://github.com/apache/nifi/tree/main/nifi-docker/dockerhub)
- [NiFi Overview](https://nifi.apache.org/nifi-docs/overview.html)
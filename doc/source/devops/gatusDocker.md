# Gatus Docker

The automated status page that you deserve

## Demo
- [Demo](https://gatus.io/demo)
- [Health Status](https://status.twin.sh/)

## Docker
```sh
docker run -p 8080:8080 --name gatus twinproduction/gatus
docker run -d -p 8080:8080 --name gatus ghcr.io/twin/gatus
```
[http://localhost:8080/](http://localhost:8080/)

Other than using one of the examples provided in the [.examples](https://github.com/TwiN/gatus/blob/master/.examples) folder, you can also try it out locally by creating a configuration file, we'll call it `config.yaml` for this example, and running the following command:
```sh
docker run -p 8080:8080 --mount type=bind,source="$(pwd)"/config.yaml,target=/config/config.yaml --name gatus twinproduction/gatus
```

## Configuration
`config/config.yaml`

configuration file example:
```
endpoints:
  - name: website                 # Name of your endpoint, can be anything
    url: "https://twin.sh/health"
    interval: 5m                  # Duration to wait between every status check (default: 60s)
    conditions:
      - "[STATUS] == 200"         # Status must be 200
      - "[BODY].status == UP"     # The json path "$.status" must be equal to UP
      - "[RESPONSE_TIME] < 300"   # Response time must be under 300ms

  - name: make-sure-header-is-rendered
    url: "https://example.org/"
    interval: 60s
    conditions:
      - "[STATUS] == 200"                          # Status must be 200
      - "[BODY] == pat(*<h1>Example Domain</h1>*)" # Body must contain the specified header
```
See [examples/docker-compose-postgres-storage/config/config.yaml](https://github.com/TwiN/gatus/blob/master/.examples/docker-compose-postgres-storage/config/config.yaml) for an example.

## Alerting
### Email
```
alerting:
  email:
    from: "from@example.com"
    username: "from@example.com"
    password: "hunter2"
    host: "mail.example.com"
    port: 587
    to: "recipient1@example.com,recipient2@example.com"
    client:
      insecure: false
    # You can also add group-specific to keys, which will
    # override the to key above for the specified groups
    overrides:
      - group: "core"
        to: "recipient3@example.com,recipient4@example.com"

endpoints:
  - name: website
    url: "https://twin.sh/health"
    interval: 5m
    conditions:
      - "[STATUS] == 200"
      - "[BODY].status == UP"
      - "[RESPONSE_TIME] < 300"
    alerts:
      - type: email
        description: "healthcheck failed"
        send-on-resolved: true

  - name: back-end
    group: core
    url: "https://example.org/"
    interval: 5m
    conditions:
      - "[STATUS] == 200"
      - "[CERTIFICATE_EXPIRATION] > 48h"
    alerts:
      - type: email
        description: "healthcheck failed"
        send-on-resolved: true
```

### Gotify
```
alerting:
  gotify:
    server-url: "https://gotify.example"
    token: "**************"

endpoints:
  - name: website
    url: "https://twin.sh/health"
    interval: 5m
    conditions:
      - "[STATUS] == 200"
      - "[BODY].status == UP"
      - "[RESPONSE_TIME] < 300"
    alerts:
      - type: gotify
        description: "healthcheck failed"
        send-on-resolved: true
```

## API
Gatus provides a simple read-only API that can be queried in order to programmatically determine endpoint status and history.
```
/api/v1/endpoints/statuses
/api/v1/endpoints/{group}_{endpoint}/statuses
```

## Runtime Environment
- [Go](https://golang.org/)

## Screenshots
![](https://gatus.io/img/example-services-dashboard-dark.png)

![](https://gatus.io/img/example-services-dashboard-conditions.png)

![](https://gatus.io/img/example-alerting-slack.png)

## References
- [Gatus](https://gatus.io/)
- [Gatus GitHub](https://github.com/TwiN/gatus)
- [Gatus Email](https://gatus.io/docs/alerting-email)
- [Gatus Gotify](https://gatus.io/docs/alerting-gotify)
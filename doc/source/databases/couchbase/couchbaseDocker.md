# Couchbase Docker

[Couchbase Server](https://www.couchbase.com/products/server) is a NoSQL document database with a distributed architecture for performance, scalability, and availability. It enables developers to build applications easier and faster by leveraging the power of SQL with the flexibility of JSON.

## Docker
```sh
docker run -d --name db -p 8091-8097:8091-8097 -p 9123:9123 -p 11207:11207 -p 11210:11210 -p 11280:11280 -p 18091-18097:18091-18097 couchbase
```

## Docker Compose
`couchbase.yml`

Visit `http://localhost:8091` on the host machine to see the Web Console and start Couchbase Server setup.

Walk through the Setup wizard and choose "Finish With Defaults."

## Multi Node Couchbase Server Cluster Deployment Topologies
With multi node Couchbase Server clusters, there are 2 popular topologies.

### All Couchbase Server containers on one physical machine
This model is commonly used for scale-minimized deployments simulating production deployments for development and test purposes.
```
┌──────────────────────────────────────────────────────────┐
│                     Host OS (Linux)                      │
│                                                          │
│  ┌───────────────┐ ┌───────────────┐  ┌───────────────┐  │
│  │ Container OS  │ │ Container OS  │  │ Container OS  │  │
│  │   (Ubuntu)    │ │   (Ubuntu)    │  │   (Ubuntu)    │  │
│  │ ┌───────────┐ │ │ ┌───────────┐ │  │ ┌───────────┐ │  │
│  │ │ Couchbase │ │ │ │ Couchbase │ │  │ │ Couchbase │ │  │
│  │ │  Server   │ │ │ │  Server   │ │  │ │  Server   │ │  │
│  │ └───────────┘ │ │ └───────────┘ │  │ └───────────┘ │  │
│  └───────────────┘ └───────────────┘  └───────────────┘  │
└──────────────────────────────────────────────────────────┘
```

### Each Couchbase Server container on its own machine
This model is commonly used for production deployments.
```
┌───────────────────────┐  ┌───────────────────────┐  ┌───────────────────────┐
│   Host OS (Linux)     │  │   Host OS (Linux)     │  │   Host OS (Linux)     │
│  ┌─────────────────┐  │  │  ┌─────────────────┐  │  │  ┌─────────────────┐  │
│  │  Container OS   │  │  │  │  Container OS   │  │  │  │  Container OS   │  │
│  │    (Ubuntu)     │  │  │  │    (Ubuntu)     │  │  │  │    (Ubuntu)     │  │
│  │  ┌───────────┐  │  │  │  │  ┌───────────┐  │  │  │  │  ┌───────────┐  │  │
│  │  │ Couchbase │  │  │  │  │  │ Couchbase │  │  │  │  │  │ Couchbase │  │  │
│  │  │  Server   │  │  │  │  │  │  Server   │  │  │  │  │  │  Server   │  │  │
│  │  └───────────┘  │  │  │  │  └───────────┘  │  │  │  │  └───────────┘  │  │
│  └─────────────────┘  │  │  └─────────────────┘  │  │  └─────────────────┘  │
└───────────────────────┘  └───────────────────────┘  └───────────────────────┘
```

## Running A N1QL Query on the Couchbase Server Cluster
N1QL is the SQL based query language for Couchbase Server. Simply switch to the Query tab on the Web Console at `http://localhost:8091` and run the following N1QL Query in the query window:
```
SELECT name FROM `beer-sample` WHERE brewery_id="mishawaka_brewing";
```
You can also execute N1QL queries from the command line.
```sh
$ docker exec -it db cbq --user Administrator
cbq> SELECT name FROM `beer-sample` WHERE brewery_id ="mishawaka_brewing";
```

## Screenshots
![](https://d774lla4im6mk.cloudfront.net/ui-home.jpg)

![](https://d774lla4im6mk.cloudfront.net/load-sample-data.jpg)

## References
- [Couchbase Server Docker](https://hub.docker.com/r/couchbase/server)
- [Create a Cluster](https://docs.couchbase.com/server/current/manage/manage-nodes/create-cluster.html)
- [Couchbase Docker](https://registry.hub.docker.com/_/couchbase)
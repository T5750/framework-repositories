# Couchbase Docker

[Couchbase Server](https://www.couchbase.com/products/server) is a NoSQL document database with a distributed architecture for performance, scalability, and availability. It enables developers to build applications easier and faster by leveraging the power of SQL with the flexibility of JSON.

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

## References
- [Couchbase Server Docker](https://hub.docker.com/r/couchbase/server)
- [Create a Cluster](https://docs.couchbase.com/server/current/manage/manage-nodes/create-cluster.html)
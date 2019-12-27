# YARN

## Architecture
![yarn_architecture](https://s1.wailian.download/2019/12/27/yarn_architecture-min.png)

## Components Of YARN
- Client: For submitting MapReduce jobs.
- Resource Manager: To manage the use of resources across the cluster
- Node Manager:For launching and monitoring the computer containers on machines in the cluster.
- Map Reduce Application Master: Checks tasks running the MapReduce job. The application master and the MapReduce tasks run in containers that are scheduled by the resource manager, and managed by the node managers.

## Benefits of YARN
- Scalability: Map Reduce 1 hits ascalability bottleneck at 4000 nodes and 40000 task, but Yarn is designed for 10,000 nodes and 1 lakh tasks.
- Utiliazation: Node Manager manages a pool of resources, rather than a fixed number of the designated slots thus increasing the utilization.
- Multitenancy: Different version of MapReduce can run on YARN, which makes the process of upgrading MapReduce more manageable.

## References
- [Apache Hadoop YARN](https://hadoop.apache.org/docs/current/hadoop-yarn/hadoop-yarn-site/YARN.html)
- [YARN](https://www.javatpoint.com/yarn)
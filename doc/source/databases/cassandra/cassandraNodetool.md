# Cassandra Nodetool

## Synopsis
`nodetool <options> status ( -r | --resolve-ip ) -- <keyspace>`

### Options

Short | Long | Description
---|---|---
-h | --host | Hostname or IP address.
-p | --port | Port number.
-pwf | --password-file | Password file path.
-pw | --password | Password.
-u | --username | Remote JMX agent username.
-r | --resolve-ip | Show node names instead of IP addresses.
keyspace |  | Name of keyspace.
-- |  | Separates an option from an argument that could be mistaken for a option.

## Description
The status command provides the following information:
- Status - U (up) or D (down)
    * Indicates whether the node is functioning or not.
- State - N (normal), L (leaving), J (joining), M (moving)
    * The state of the node in relation to the cluster.
- Address
    * The node's URL.
- Load - updates every 90 seconds
    * The amount of file system data under the cassandra data directory after excluding all content in the snapshots subdirectories. Because all SSTable data files are included, any data that is not cleaned up, such as TTL-expired cell or tombstoned data) is counted.
- Tokens
    * The number of tokens set for the node.
- Owns
    * The percentage of the data owned by the node per datacenter times the replication factor. For example, a node can own 33% of the ring, but show 100% if the replication factor is 3.
    * **Attention**: If your cluster uses keyspaces having different replication strategies or replication factors, specify a keyspace when you run `nodetool status` to get meaningful ownership information.
- Host ID
    * The network ID of the node.
- Rack
    * The rack or, in the case of Amazon EC2, the availability zone of the node.

## References
- [nodetool status](https://docs.datastax.com/en/archived/cassandra/3.0/cassandra/tools/toolsStatus.html)
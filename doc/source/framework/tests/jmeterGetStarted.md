# JMeter Getting Started

## Running JMeter
> GUI mode should only be used for creating the test script, CLI mode (NON GUI) must be used for load testing

- `jmeter.bat` run JMeter (in GUI mode by default)
- `jmeterw.cmd` run JMeter without the windows shell console (in GUI mode by default)
- `jmeter-n.cmd` drop a JMX file on this to run a CLI mode test
- `-jmeter-n-r.cmd` drop a JMX file on this to run a CLI mode test remotely
- `-jmeter-t.cmd` drop a JMX file on this to load it in GUI mode
- `-jmeter-server.bat` start JMeter in server mode
- `-mirror-server.cmd` runs the JMeter Mirror Server in CLI mode
- `-shutdown.cmd` Run the Shutdown client to stop a CLI mode instance gracefully
- `stoptest.cmd` Run the Shutdown client to stop a CLI mode instance abruptly

### CLI Mode
For load testing, you must run JMeter in this mode (Without the GUI) to get the optimal results from it. To do so, use the following command options:
- `-n`: This specifies JMeter is to run in cli mode
- `-t`: [name of JMX file that contains the Test Plan].
- `-l`: [name of JTL file to log sample results to].
- `-j`: [name of JMeter run log file].
- `-r`: Run the test in the servers specified by the JMeter property "remote_hosts"
- `-R`: [list of remote servers] Run the test in the specified remote servers
- `-g`: [path to CSV file] generate report dashboard only
- `-e`: generate report dashboard after load test
- `-o`: output folder where to generate the report dashboard after load test. Folder must not exist or be empty

The script also lets you specify the optional firewall/proxy server information:
- `-H`: [proxy server hostname or ip address]
- `-P`: [proxy server port]

Example
```
jmeter -n -t my_test.jmx -l log.jtl -H my.proxy.server -P 8000
```
If the property `jmeterengine.stopfail.system.exit` is set to `true` (default is `false`), then JMeter will invoke `System.exit(1)` if it cannot stop all threads. Normally this is not necessary.

## Tests
```
jmeter -n -t my_test.jmx -l report.csv -e -o report
```

## References
- [JMeter Getting Started](https://jmeter.apache.org/usermanual/get-started.html#top)
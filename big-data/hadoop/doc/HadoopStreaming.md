# Streaming

## Hadoop Streaming
```
hadoop jar /home/hadoop/hadoop-streaming-2.9.2.jar \
  -input /input_dir \
  -output myOutputDir \
  -mapper /bin/cat \
  -reducer /usr/bin/wc
```

## Streaming Command Options
`hadoop command [genericOptions] [streamingOptions]`

Parameter | Optional/Required | Description
---|---|-----
-input directoryname or filename | Required | Input location for mapper
-output directoryname | Required | Output location for reducer
-mapper executable or JavaClassName | Optional | Mapper executable. If not specified, IdentityMapper is used as the default
-reducer executable or JavaClassName | Optional | Reducer executable. If not specified, IdentityReducer is used as the default
-file filename | Optional | Make the mapper, reducer, or combiner executable available locally on the compute nodes
-inputformat JavaClassName | Optional | Class you supply should return key/value pairs of Text class. If not specified, TextInputFormat is used as the default
-outputformat JavaClassName | Optional | Class you supply should take key/value pairs of Text class. If not specified, TextOutputformat is used as the default
-partitioner JavaClassName | Optional | Class that determines which reduce a key is sent to
-combiner streamingCommand or JavaClassName | Optional | Combiner executable for map output
-cmdenv name=value | Optional | Pass environment variable to streaming commands
-inputreader | Optional | For backwards-compatibility: specifies a record reader class (instead of an input format class)
-verbose | Optional | Verbose output
-lazyOutput | Optional | Create output lazily. For example, if the output format is based on FileOutputFormat, the output file is created only on the first call to Context.write
-numReduceTasks | Optional | Specify the number of reducers
-mapdebug | Optional | Script to call when map task fails
-reducedebug | Optional | Script to call when reduce task fails

### Specifying a Java Class as the Mapper/Reducer
```
hadoop jar /home/hadoop/hadoop-streaming-2.9.2.jar \
  -input /input_dir \
  -output myOutputDir \
  -inputformat org.apache.hadoop.mapred.KeyValueTextInputFormat \
  -mapper org.apache.hadoop.mapred.lib.IdentityMapper \
  -reducer /usr/bin/wc
```

## References
- [Hadoop Streaming](http://hadoop.apache.org/docs/stable/hadoop-streaming/HadoopStreaming.html)
package t5750.hadoop.mapred;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

public class CharCountMapper extends MapReduceBase implements
		Mapper<LongWritable, Text, Text, IntWritable> {
	@Override
	public void map(LongWritable key, Text value,
			OutputCollector<Text, IntWritable> output, Reporter reporter)
			throws IOException {
		String line = value.toString();
		String tokenizer[] = line.split("");
		for (String SingleChar : tokenizer) {
			Text charKey = new Text(SingleChar);
			IntWritable One = new IntWritable(1);
			output.collect(charKey, One);
		}
	}
}
package t5750.hadoop.mapred;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.*;

public class CharCountRunner {
	public static void main(String[] args) throws IOException {
		JobConf conf = new JobConf(CharCountRunner.class);
		conf.setJobName("CharCount");
		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(IntWritable.class);
		conf.setMapperClass(CharCountMapper.class);
		conf.setCombinerClass(CharCountReducer.class);
		conf.setReducerClass(CharCountReducer.class);
		conf.setInputFormat(TextInputFormat.class);
		conf.setOutputFormat(TextOutputFormat.class);
		FileInputFormat.setInputPaths(conf, new Path(args[0]));
		FileOutputFormat.setOutputPath(conf, new Path(args[1]));
		JobClient.runJob(conf);
	}
}
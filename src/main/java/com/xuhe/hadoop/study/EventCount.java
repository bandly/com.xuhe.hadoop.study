package com.xuhe.hadoop.study;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class EventCount {

    public static class MyMapper extends Mapper<Object, Text, Text, IntWritable> {

        private final static IntWritable one   = new IntWritable(1);
        private Text                     event = new Text();

        @Override
        public void map(Object key, Text value, Context context) throws IOException,
                                                                 InterruptedException {
            int idx = value.toString().indexOf(" ");
            if (idx > 0) {
                String e = value.toString().substring(0, idx);
                event.set(e);
                context.write(event, one);
            }
        }
    }

    public static class MyReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
        private IntWritable result = new IntWritable();

        @Override
        public void reduce(Text key, Iterable<IntWritable> values,
                           Context context) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable val : values) {
                sum += val.get();
            }
            result.set(sum);
            context.write(key, result);
        }

    }

    public static void main(String[] args) throws Exception {
        args = new String[2];
        args[0] = "/user/hadoop";
        args[1] = "/user/hadoop/output1";
        Configuration conf = new Configuration();
        String url = "hdfs://192.168.5.165:9000/";
        Configuration config = new Configuration();
        conf.set("fs.defaultFS", url);
        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        if (otherArgs.length < 2) {
            System.err.println("Usage: EventCount <in> <out>");
            System.exit(2);
        }
        Job job = Job.getInstance(conf, "event count");
        job.setJarByClass(EventCount.class);
        job.setMapperClass(MyMapper.class);
        job.setCombinerClass(MyReducer.class);
        job.setReducerClass(MyReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);

    }

}

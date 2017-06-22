package com.xuhe.hadoop.study;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class Dedup {

    //map 将输入中的value复制到输出数据的key上，并直接输出
    public static class Map extends Mapper<Object, Text, Text, Text> {

        private static Text line = new Text();//每行数据
        //实现map函数

        @Override
        public void map(Object key, Text value, Context context) throws IOException,
                                                                 InterruptedException {
            line = value;
            context.write(line, new Text(""));
        }
    }

    //reduce 将输入中的key复制到输出数据的key上，并直接输出

    public static class Reduce extends Reducer<Text, Text, Text, Text> {

        //实现Reduce函数
        @Override
        public void reduce(Text key, Iterable<Text> values, Context context) throws IOException,
                                                                             InterruptedException {
            context.write(key, new Text(""));
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException,
                                           InterruptedException {
        /* Configuration conf = new Configuration();
        conf.set("mapred.job.tracker", "ubuntu:9000");*/
        String url = "hdfs://192.168.5.165:9000/";
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", url);
        //conf.set("mapred.job.tracker", url);
        String[] ioArgs = new String[] { "/user/hadoop/dedup_in", "/user/hadoop/dedup_out" };

        String[] otherArgs = new GenericOptionsParser(conf, ioArgs).getRemainingArgs();

        if (otherArgs.length != 2) {
            System.err.println("Usage: Data Deduplication <in> <out>");
            System.exit(2);
        }
        Job job = Job.getInstance(conf, "Data Deduplication");
        job.setJarByClass(Dedup.class);
        //设置map Combine 和Reduce 处理类
        job.setMapperClass(Map.class);
        job.setCombinerClass(Reducer.class);
        job.setReducerClass(Reducer.class);

        //设置输出类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        //设置输入和输出目录
        FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);

    }
}

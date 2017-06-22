package com.xuhe.hadoop.study;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileSplit;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

public class InvertedIndex {

    public static class Map extends Mapper<Object, Text, Text, Text> {

        private Text      keyInfo   = new Text();//存储单词和URL组合
        private Text      valueInfo = new Text();//存储字频
        private FileSplit split;

        @Override
        public void map(Object key, Text value, Context context) throws IOException,
                                                                 InterruptedException {

            split = (FileSplit) context.getInputSplit();
            StringTokenizer itr = new StringTokenizer(value.toString());

            while (itr.hasMoreTokens()) {
                int splitIndex = split.getPath().toString().indexOf("file");
                keyInfo
                    .set(itr.nextToken() + ":" + split.getPath().toString().substring(splitIndex));
                valueInfo.set("1");
                context.write(keyInfo, valueInfo);

            }
        }

    }

    public static class Combine extends Reducer<Text, Text, Text, Text> {

        private Text info = new Text();

        @Override
        public void reduce(Text key, Iterable<Text> values, Context context) throws IOException,
                                                                             InterruptedException {
            int sum = 0;
            for (Text value : values) {
                sum += Integer.parseInt(value.toString());
            }

            int splitIndex = key.toString().indexOf(":");

            info.set(key.toString().substring(splitIndex + 1) + ":" + sum);
            key.set(key.toString().substring(0, splitIndex));
            context.write(key, info);
        }
    }

    public static class Reduce extends Reducer<Text, Text, Text, Text> {
        private Text result = new Text();

        @Override
        public void reduce(Text key, Iterable<Text> values, Context context) throws IOException,
                                                                             InterruptedException {

            String fileList = new String();

            for (Text value : values) {
                fileList += value.toString() + ";";
            }
            result.set(fileList);

            context.write(key, result);

        }
    }

}

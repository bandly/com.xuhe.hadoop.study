package com.xuhe.hadoop.study.pi;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Mapper;

public class PiMapper extends Mapper<LongWritable, LongWritable, LongWritable, LongWritable> {

    @Override
    public void map(LongWritable ikey, LongWritable ivalue, Context context) {

        long startId = ikey.get();
        long numSamples = ivalue.get();
        long inside = 0;
        // HaltonSequence  halton = new 
    }
}

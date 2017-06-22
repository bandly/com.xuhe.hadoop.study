package com.xuhe.hadoop.study;

import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

public class MTjoin {

    public static int time = 0;

    public static class Map extends Mapper<Object, Text, Text, Text> {

        @Override
        public void map(Object key, Text value, Context context) throws IOException,
                                                                 InterruptedException {
            String line = value.toString();
            String relationtype = new String();

            if (line.contains("factoryname") == true || line.contains("addressed") == true) {
                return;
            }
            StringTokenizer itr = new StringTokenizer(line);
            String mapkey = new String();
            String mapvalue = new String();

            int i = 0;
            while (itr.hasMoreTokens()) {
                String token = itr.nextToken();
                if (token.charAt(0) >= '0' && token.charAt(0) <= '9') {
                    mapkey = token;
                    if (i > 0) {
                        relationtype = "1";
                    } else {
                        relationtype = "2";
                    }
                    continue;
                }
                //名称
                mapvalue += token + " ";
                i++;
            }
            // 输出左右表

            context.write(new Text(mapkey), new Text(relationtype + "+" + mapvalue));

        }
    }

    public static class Reduce extends Reducer<Text, Text, Text, Text> {

        @Override
        public void reduce(Text key, Iterable<Text> values, Context context) throws IOException,
                                                                             InterruptedException {
            if (0 == time) {
                context.write(new Text("factoryname"), new Text("addressname"));
                time++;
            }

            int factorynum = 0;
            String[] factory = new String[10];
            int addressnum = 0;
            String[] address = new String[10];

            Iterator<Text> ite = values.iterator();
            while (ite.hasNext()) {
                String record = ite.next().toString();
                int len = record.length();
                int i = 2;
                if (0 == len) {
                    continue;
                }
                // 取得左右表标识

                char relationtype = record.charAt(0);
                if ('1' == relationtype) {
                    factory[factorynum] = record.substring(i);
                    factorynum++;
                }
                if ('2' == relationtype) {
                    address[addressnum] = record.substring(i);
                    addressnum++;
                }

            }

            //求笛卡尔积
            if (0 != factorynum && 0 != addressnum) {
                for (int i = 0; i < factorynum; i++) {
                    for (int j = 0; j < address.length; j++) {
                        context.write(new Text(factory[i]), new Text(address[j]));
                    }
                }
            }
        }
    }

}

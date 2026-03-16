package com;
import java.io.IOException;
import java.util.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;
import java.math.BigInteger;
public class MapReduce extends MapReduceBase implements Mapper<LongWritable,Text,Text,Text> {
	IntWritable one = new IntWritable(1);
    Text locText = new Text();
	Text empty = new Text();

//this function continuously called by hadoop mapreduce in a loop to transfer all lines from a file
//if file size > 50 mb then it will consider traffic as abnormal
public void map(LongWritable key,Text value,OutputCollector<Text,Text> output,Reporter reporter) throws IOException {
	String line = value.toString();
	double size = Double.parseDouble(line);
	double kb = size / 1000.0;
	double mb = kb / 1000.0;
	String status = "normal";
	if(mb > 50) {
		NetworkMonitor.attack = NetworkMonitor.attack + 1;
		status = "abnormal";
	} else {
		NetworkMonitor.normal = NetworkMonitor.normal + 1;
	}
	NetworkMonitor.status = status;
}
}
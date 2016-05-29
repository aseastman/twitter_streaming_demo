#!/bin/sh
env HADOOP_CONF_DIR=/etc/hive/conf spark-submit --master yarn-cluster \
--conf spark.akka.frameSize=200 --files /etc/hive/conf/hive-site.xml \
--jars /home/a78084/.ivy2/cache/org.apache.spark/spark-streaming-twitter_2.10/jars/spark-streaming-twitter_2.10-1.5.0.jar,\
/home/a78084/.ivy2/cache/edu.stanford.nlp/stanford-corenlp/jars/stanford-corenlp-3.6.0.jar,\
/home/a78084/.ivy2/cache/edu.stanford.nlp/stanford-corenlp/jars/stanford-corenlp-3.6.0-models.jar,\
/home/a78084/.ivy2/cache/org.apache.httpcomponents/httpclient/jars/httpclient-4.5.2.jar,\
/home/a78084/.ivy2/cache/org.apache.httpcomponents/httpcore/jars/httpcore-4.4.4.jar,\
/home/a78084/.ivy2/cache/commons-codec/commons-codec/jars/commons-codec-1.5.jar,\
/home/a78084/.ivy2/cache/com.googlecode.efficient-java-matrix-library/ejml/jars/ejml-0.23.jar \
--num-executors 1 --executor-cores 20 --driver-memory 32G --executor-memory 64G target/scala-2.10/spark-h_2.10-0.1.jar

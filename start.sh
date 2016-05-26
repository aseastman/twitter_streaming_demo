#!/bin/sh
env HADOOP_CONF_DIR=/etc/hive/conf spark-submit --master yarn-cluster \
--conf spark.akka.frameSize=200 --files /etc/hive/conf/hive-site.xml \
--jars /home/a78084/.ivy2/cache/org.apache.spark/spark-streaming-twitter_2.10/jars/spark-streaming-twitter_2.10-1.5.0.jar, \
/home/a78084/.ivy2/cache/edu.stanford.nlp/stanford-corenlp/jars/stanford-corenlp-3.5.1.jar, \
/home/a78084/.ivy2/cache/edu.stanford.nlp/stanford-corenlp/jars/stanford-corenlp-3.5.1-models.jar \
--num-executors 1 --executor-cores 20 --driver-memory 32G --executor-memory 64G target/scala-2.10/spark-h_2.10-0.1.jar

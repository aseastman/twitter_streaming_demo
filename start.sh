#!/bin/sh
env HADOOP_CONF_DIR=/etc/hive/conf spark-submit --master yarn-cluster \
--conf spark.akka.frameSize=200 --files /etc/hive/conf/hive-site.xml \
--num-executors 1 --executor-cores 20 --driver-memory 32G --executor-memory 64G target/scala-2.10/spark-h_2.10-0.1.jar

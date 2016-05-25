import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.twitter._
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}
//import twitter4j.Status


object TwitterDriver {

  def main(args: Array[String]) : Unit = {
    val conf = new SparkConf()
      .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")

    val sc = new SparkContext(conf)
    val ssc = new StreamingContext(sc,Seconds(10))

    val filters = Seq("#Android")

    val tweets = TwitterUtils.createStream(ssc, None)


    tweets.foreachRDD(tweetRDD => println(tweetRDD.take(1)))



    ssc.start()
    ssc.awaitTermination()
  }
}

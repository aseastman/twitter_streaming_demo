import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.twitter._
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}
import twitter4j.auth.{AuthorizationFactory, OAuthAuthorization}
import twitter4j.conf.ConfigurationBuilder

import scala.io.Source
//import twitter4j.Status


object TwitterDriver {

  def main(args: Array[String]) : Unit = {
    val conf = new SparkConf()
      .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")

    val sc = new SparkContext(conf)
    val ssc = new StreamingContext(sc,Seconds(10))

    val config : Iterator[String] = Source.fromFile("file:///home/a78084/twitter_demo/twitter_streaming_demo/src/main/scala/twitter4j.properties").getLines()
    val consumerKey       = config.find(_.startsWith("oauth.consumerKey")).get.split("=")(1)
    val consumerSecret    = config.find(_.startsWith("oauth.consumerSecret")).get.split("=")(1)
    val accessToken       = config.find(_.startsWith("oauth.accessToken")).get.split("=")(1)
    val accessTokenSecret = config.find(_.startsWith("oauth.accessTokenSecret")).get.split("=")(1)

    val cb = new ConfigurationBuilder()
    cb.setDebugEnabled(true)
      .setOAuthConsumerKey(consumerKey)
      .setOAuthConsumerSecret(consumerSecret)
      .setOAuthAccessToken(accessToken)
      .setOAuthAccessTokenSecret(accessTokenSecret)

    val auth = AuthorizationFactory.getInstance(cb.build())

    val filters = Seq("#Android")

    val tweets = TwitterUtils.createStream(ssc, Option(auth),filters)


    tweets.foreachRDD(tweetRDD => tweetRDD.foreach(println))



    ssc.start()
    ssc.awaitTermination()
  }
}

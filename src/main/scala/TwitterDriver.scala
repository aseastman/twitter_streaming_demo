import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.twitter._
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}
import twitter4j.auth.{Authorization, AuthorizationFactory}
import twitter4j.conf.ConfigurationBuilder

import twitter4j.Status


object TwitterDriver {

  def main(args: Array[String]) : Unit = {
    val conf = new SparkConf()
      .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")

    val sc  = new SparkContext(conf)
    val ssc = new StreamingContext(sc,Seconds(10))

    val consumerKey       = config.consumerKey
    val consumerSecret    = config.consumerSecret
    val accessToken       = config.accessToken
    val accessTokenSecret = config.accessTokenSecret

    val cb = new ConfigurationBuilder()
    cb.setDebugEnabled(true)
      .setOAuthConsumerKey(consumerKey)
      .setOAuthConsumerSecret(consumerSecret)
      .setOAuthAccessToken(accessToken)
      .setOAuthAccessTokenSecret(accessTokenSecret)

    val auth : Authorization = AuthorizationFactory.getInstance(cb.build())

    val filters : Array[String] = Array("arrow.com","@ArrowGlobal","#fiveyearsout","#arrowdriven")

    val tweets : DStream[Status] = TwitterUtils.createStream(ssc, Option(auth),filters)


    tweets.foreachRDD{tweetRDD =>
      tweetRDD.foreach{tweet =>
        val username : String = tweet.getUser.getScreenName
        val friends  : Long   = tweet.getUser.getFriendsCount
        val text     : String = tweet.getText
        val textCount : Long = text.split(" ").length
        println(s"$username has tweeted '$text' ($textCount words) and has $friends friends.")
      }
    }
    ssc.start()
    ssc.awaitTermination()
  }
}

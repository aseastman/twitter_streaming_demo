
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

    val tweets : DStream[Status] = TwitterUtils.createStream(ssc, Option(auth))


    tweets.foreachRDD{tweetRDD =>
      tweetRDD.foreach{tweet =>
        val lat = Option(tweet.getGeoLocation.getLatitude).getOrElse(0.0)
        val lon = Option(tweet.getGeoLocation.getLongitude).getOrElse(0.0)
        if (lat <= -73.0 && lat >= -74.0 && lon <= 41.0 && lon >= 40.0) {
          val username: String = tweet.getUser.getScreenName
          val friends: Long = tweet.getUser.getFriendsCount
          val text: String = tweet.getText
          val textCount: Long = text.split(" ").length
          val sentimentValue = SentimentAnalysis.detectSentiment(text)
          val sentiment = if (sentimentValue <= 0.0) {
            "Not Understood"
          } else if (sentimentValue <= 1.0) {
            "Very Negative"
          } else if (sentimentValue <= 2.0) {
            "Negative"
          } else if (sentimentValue <= 3.0) {
            "Neutral"
          } else if (sentimentValue <= 4.0) {
            "Positive"
          } else if (sentimentValue <= 5.0) {
            "Very Positive"
          } else "Not Understood"
          println(s"$username is $sentiment has tweeted '$text' ($textCount words) and has $friends friends.")
        }
      }
    }
    ssc.start()
    ssc.awaitTermination()
  }
}

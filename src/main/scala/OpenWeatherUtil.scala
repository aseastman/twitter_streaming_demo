import org.apache.commons.httpclient.HttpClient
import org.apache.http.client.methods.{CloseableHttpResponse, HttpGet}
import org.apache.http.client.utils.URIBuilder
import org.apache.http.impl.client.HttpClients

/**
  * Created by a78084 on 5/27/16.
  */
class OpenWeatherUtil extends config{


  def getWeather (location : String) = {
    val client = HttpClients.createDefault()
    val baseURL = "api.openweathermap.org/data/2.5/weather?q="
    val url = baseURL + location + "&APPID=" + openWeatherKey

//    val uri = new URIBuilder()
//      .setScheme("http")
//      .setHost("api.openweathermap.org")
//      .setPath("/data/2.5/weather")
//      .setParameter("q",location)
//      .build()

    val httpGet : HttpGet = new HttpGet(url)

    val response : CloseableHttpResponse = client.execute(httpGet)
    val headers = response.getAllHeaders
    headers.foreach(println)
  }

}

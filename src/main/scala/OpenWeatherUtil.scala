import org.apache.commons.httpclient.HttpClient
import org.apache.http.client.methods.{CloseableHttpResponse, HttpGet}
import org.apache.http.client.utils.URIBuilder
import org.apache.http.impl.client.HttpClients

/**
  * Created by a78084 on 5/27/16.
  */
class OpenWeatherUtil {


  def getWeather (location : String) = {
    @transient val client = HttpClients.createDefault()
//    val baseURL = "api.openweathermap.org/data/2.5/weather?q="
//    val httpget = new HttpGet(baseURL + location)

    @transient val uri = new URIBuilder()
      .setScheme("http")
      .setHost("api.openweathermap.org")
      .setPath("/data/2.5/weather")
      .setParameter("q",location)
      .build()

    @transient val httpGet : HttpGet = new HttpGet(uri)

    @transient val response : CloseableHttpResponse = client.execute(httpGet)
    @transient val headers = response.getAllHeaders
    headers.foreach(println)
  }

}

import java.io.{BufferedReader, InputStreamReader, StringWriter}

import org.apache.commons.httpclient.HttpClient
import org.apache.http.client.methods.{CloseableHttpResponse, HttpGet}
import org.apache.http.client.utils.URIBuilder
import org.apache.http.impl.client.{HttpClientBuilder, HttpClients}

import scala.io.Source

/**
  * Created by a78084 on 5/27/16.
  */
class OpenWeatherUtil {
  val key : String = config.openWeatherKey


  def getWeather (location : String) : String = {
    val client = new HttpClientBuilder
    val baseURL = "https://api.openweathermap.org/data/2.5/weather?q="
    val url = baseURL + location + "&APPID=" + key



    val httpGet : HttpGet = new HttpGet(url)

    val response : CloseableHttpResponse = client.build().execute(httpGet)
    val entity = response.getEntity
    val content = entity.getContent
    val text = Source.fromInputStream(content).getLines().mkString
    content.close()
    text
  }

}

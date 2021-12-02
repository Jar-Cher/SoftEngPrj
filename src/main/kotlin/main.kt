import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils


fun main(args: Array<String>) {
    WeatherWidget.getTemperature("Moscow")
}

class WeatherWidget() {

    companion object {
        fun getTemperature(city: String) {
            val httpClient = HttpClients.createDefault()
            val get = HttpGet("http://api.openweathermap.org/data/2.5/weather?q=$city&appid=514618c1ab6dd23ac38d98bfc9ae6378")
            val response = httpClient.execute(get)
            val weatherString = EntityUtils.toString(response.entity)
            val actualObj = ObjectMapper().readTree(weatherString)
            println((actualObj.findValue("temp").toString().toDouble()-273.15).toInt())
        }
    }

}

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
import org.intellij.lang.annotations.Language
import java.io.File
import java.util.*


fun main(args: Array<String>) {
    //WeatherWidget.getTemperature(134)
    WeatherWidget.getTemperature("Saint Petersburg")
}

class WeatherWidget() {

    companion object {

        private val citiesDB = ObjectMapper().readTree(File("city.list.json").readText())

        fun getTemperature(id: Int) {
            val httpClient = HttpClients.createDefault()
            val get = HttpGet("http://api.openweathermap.org/data/2.5/weather?id=$id&appid=514618c1ab6dd23ac38d98bfc9ae6378")
            val response = httpClient.execute(get)
            val weatherString = EntityUtils.toString(response.entity)
            val actualObj = ObjectMapper().readTree(weatherString)
            println("Current weather in " + actualObj.findValue("name").toString().trim { it=='\"' } + ":")
            println("Temperature: " + (actualObj.findValue("temp").toString().toDouble()-273.15).toInt() + "°")
            println(actualObj.findValue("description").toString()
                .trim { it=='\"' }
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() })
            println("Humidity " + (actualObj.findValue("humidity").toString()).uppercase() + "%")
        }

        fun getTemperature(city: String) {
            val cityIds = citiesDB.findValues("id")
            val cityOrder = ObjectMapper()
                .readValue(citiesDB.findValues("name").toString(), object: TypeReference<List<String>>() {})
                .indexOf(city)
            //println(cityIds[cityOrder].asInt())
            getTemperature(cityIds[cityOrder].asInt())
        }
    }

}
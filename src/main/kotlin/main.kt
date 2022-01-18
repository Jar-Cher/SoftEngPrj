package me.jaros

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils
import org.springframework.boot.Banner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.io.IOException
import java.util.*


@SpringBootApplication
open class Main

fun main(args: Array<String>) {
    runApplication<Main>(*args)
    val app = SpringApplication(WeatherWidget::class.java)
    app.setBannerMode(Banner.Mode.OFF)
    WeatherWidget.main(args)
}

class WeatherWidget {

    companion object {

        fun main(args: Array<String>) {
            val input = args.fold("") {str, element -> "$str$element " }.trim()
            if (Regex("""\d+""").matches(input))
                getTemperature(input.toInt())
            else
                getTemperature(input)
        }

        private val citiesDB = ObjectMapper().readTree(javaClass.classLoader.getResourceAsStream("city.list.json"))

        fun getTemperature(id: Int): String {
            var answer = "Unknown error"
            try {
                val httpClient = HttpClients.createDefault()
                val get =
                    HttpGet("http://api.openweathermap.org/data/2.5/weather?id=$id&appid=514618c1ab6dd23ac38d98bfc9ae6378")
                val response = httpClient.execute(get)
                val weatherString = EntityUtils.toString(response.entity)
                val actualObj = ObjectMapper().readTree(weatherString)
                try {
                    val builder = StringBuilder()
                    builder.append("Current weather in ")
                        .append(actualObj.findValue("name").toString().trim { it == '\"' })
                        .append(":")
                        .append("\n")
                        .append("Temperature: ")
                        .append((actualObj.findValue("temp").toString().toDouble() - 273.15).toInt())
                        .append("°")
                        .append("\n")
                        .append(actualObj.findValue("description")
                                .toString()
                                .trim { it == '\"' }
                                .replaceFirstChar {
                                    if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
                                })
                        .append("\n")
                        .append("Humidity ")
                        .append((actualObj.findValue("humidity").toString()).uppercase())
                        .append("%")
                    answer = builder.toString()
                } catch (e: NullPointerException) {
                    answer = "Error: found no city with id# $id"
                }
                httpClient.close()
            } catch (e: IOException) {
                answer = "Network error"
            }
            println(answer)
            return answer
        }

        fun getTemperature(city: String): String {
            val cityIds = citiesDB.findValues("id")
            val cityOrder = ObjectMapper()
                .readValue(citiesDB.findValues("name").toString(), object : TypeReference<List<String>>() {})
                .indexOf(city)
            //println(cityIds[cityOrder].asInt())
            return try {
                getTemperature(cityIds[cityOrder].asInt())
            } catch (e: IndexOutOfBoundsException) {
                val errorMessage = "Error: found no city named $city"
                println(errorMessage)
                errorMessage
            }
        }
    }

}
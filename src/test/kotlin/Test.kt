import me.jaros.WeatherWidget
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.util.function.ToDoubleBiFunction
import kotlin.random.Random
import kotlin.test.assertEquals

class Test {

    // Small size of the tests due to the API limitations

    companion object {
        val cities = mapOf("Moscow" to 524894,
            "London" to 2643743,
            "Tokyo" to 1850147,
            "Paris" to 2968815,
            "Rome" to 3169070,
        )
    }

    @Test
    fun testCorrectHumidity() {
        for (i in cities.keys)
            assertTrue(WeatherWidget.getTemperature(i).contains(Regex("""Humidity ([0-9]|[1-9][0-9]|100)%""")))
    }

    @Test
    fun testFoundCity() {
        for (i in cities.keys)
            assertTrue(WeatherWidget.getTemperature(i).contains("Current weather in $i"))
    }

    @Test
    fun testIncorrectId() {
        val gibberish = List(5) { (it + Random.nextInt()) % 500 }
        for (i in gibberish)
            assertEquals("Error: found no city with id# $i", WeatherWidget.getTemperature(i))
    }

    @Test
    fun testCorrectTemperature() {
        for (i in cities.keys)
            assertTrue(WeatherWidget.getTemperature(i).contains(Regex("""Temperature: [-+]?\d+°""")))
    }

    @Test
    fun testIncorrectName() {
        val gibberish = List(5) { ((it + Random.nextInt()) % 500).toString() }
        for (i in gibberish)
            assertEquals("Error: found no city named $i", WeatherWidget.getTemperature(i))
    }
}
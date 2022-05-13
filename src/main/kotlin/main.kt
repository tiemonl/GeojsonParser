import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.io.File

fun main(args: Array<String>) {
    val format = Json { prettyPrint = true }

    val locations = format.decodeFromString<Route>(getResourceAsText("src/main/resources/points.json"))

    val output = format.encodeToString(locations.route.toGeoJson())
    println(output)
    saveToFile(output)
}

fun saveToFile(output: String) = File("src/main/resources/output.json").printWriter().use { out ->
        out.println(output)
}

fun List<List<Location>>.toGeoJson(): GeoJson {
    val coordinates = mutableListOf<List<Double>>()
    this.forEach {
        it.forEach {
            coordinates.add(
                listOf(it.longitude, it.latitude)
            )
        }
    }
    return GeoJson(
        type = "FeatureCollection", features =
        listOf(
            Feature(
                type = "Feature",
                properties = Properties(),
                geometry = Geometry(
                    type = "LineString",
                    coordinates = coordinates
                )
            )
        )
    )
}

fun getResourceAsText(path: String): String {
    return File(path).readText(Charsets.UTF_8)
}


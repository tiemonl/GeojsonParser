import kotlinx.serialization.*
import kotlinx.serialization.json.*
import java.io.File

fun main(args: Array<String>) {
    val format = Json { prettyPrint = true }

    val locations = format.decodeFromString<List<Location>>(getResourceAsText("src/main/resources/points.json"))

    val output = format.encodeToString(locations.toGeoJson())
    println(output)
    saveToFile(output)
}

fun saveToFile(output: String) = File("src/main/resources/output.json").printWriter().use { out ->
        out.println(output)
}

fun List<Location>.toGeoJson(): GeoJson {
    val coordinates = mutableListOf<List<Double>>()
    this.forEach {
        coordinates.add(
            listOf(it.long, it.lat)
        )
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


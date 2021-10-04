import kotlinx.serialization.Serializable

@Serializable
data class GeoJson(
    val features: List<Feature>,
    val type: String
)

@Serializable
data class Feature(
    val geometry: Geometry,
    val properties: Properties,
    val type: String
)

@Serializable
data class Geometry(
    val coordinates: List<List<Double>>,
    val type: String
)

@Serializable
class Properties
package com.example.LRPD.mapas

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json


@Serializable
data class OsrmGeometry(
    val coordinates: List<List<Double>>
)

@Serializable
data class OsrmRoute(
    val geometry: OsrmGeometry
)

@Serializable
data class OsrmResponse(
    val routes: List<OsrmRoute> = emptyList()
)

object OsrmService {

    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }

    suspend fun obtenerRuta(
        origenLat: Double,
        origenLng: Double,
        destinoLat: Double,
        destinoLng: Double
    ): List<Pair<Double, Double>> {
        return try {
            val url =
                "https://router.project-osrm.org/route/v1/driving/" +
                        "$origenLng,$origenLat;$destinoLng,$destinoLat" +
                        "?overview=full&geometries=geojson"

            val response: OsrmResponse = client.get(url).body()

            response.routes.firstOrNull()
                ?.geometry
                ?.coordinates
                ?.map { coordenada ->
                    Pair(coordenada[1], coordenada[0])
                } ?: emptyList()

        } catch (e: Exception) {
            emptyList()
        }
    }
}
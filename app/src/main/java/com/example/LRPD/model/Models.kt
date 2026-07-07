package com.example.LRPD.model

data class Order(
    val code: String = "",
    val description: String = "",
    val origin: String = "",
    val destino: String = "",
    val recipientName: String = "",
    val recipientDNI: String = "",
    var assignedTruckPlate: String? = null,
    var estado: String = "En Agencia",
    var ubicacionActual: String = "",
    val fechaRegistro: Long = System.currentTimeMillis()
)

data class Truck(
    val plate: String,
    val driver: String,
    var location: String = "Sede Origen"
)

data class User(
    val username: String,
    val pass: String,
    val role: String // "ADMIN" o "EMPLOYEE"
)

data class HistoryLog(
    val mensaje: String = "",
    val fecha: String = "",
    val timestamp: Long = System.currentTimeMillis()
)

// Lista de sedes
val SedesCarrion = listOf(
    "Lima (La Victoria)",
    "Lima (Villa El Salvador)",
    "Cañete",
    "Chincha",
    "Ica",
    "Pisco",
    "Nazca",
    "Marcona"
)
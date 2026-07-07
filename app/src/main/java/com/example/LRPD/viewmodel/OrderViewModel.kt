package com.example.LRPD.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.LRPD.data.OrderRepository
import com.example.LRPD.model.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class OrderViewModel : ViewModel() {

    private val orderRepository = OrderRepository()

    var currentUser by mutableStateOf<User?>(null)
    val orders = mutableStateListOf<Order>()
    val trucks = mutableStateListOf<Truck>()
    val historyLogs = mutableStateListOf<HistoryLog>()
    val users = mutableStateListOf<User>()

    init {

        // Usuarios iniciales
        users.add(User(username = "administrador", pass = "12345", role = "ADMIN"))
        users.add(User(username = "empleado1", pass = "12345", role = "EMPLOYEE"))

        // Datos de prueba (camión)
        trucks.add(Truck(plate = "V4X-123", driver = "Juan Pérez", location = "Chincha"))

        // Escucha los pedidos en tiempo real desde Firestore
        orderRepository.listenOrders { firestoreOrders ->
            orders.clear()
            orders.addAll(firestoreOrders)
        }

        // Escucha el historial en tiempo real desde Firestore
        orderRepository.listenHistory { firestoreLogs ->
            historyLogs.clear()
            historyLogs.addAll(firestoreLogs)
        }
    }

    private fun addLog(msg: String) {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        val log = HistoryLog(
            mensaje = msg,
            fecha = sdf.format(Date()),
            timestamp = System.currentTimeMillis()
        )
        orderRepository.saveLog(log)
    }

    // ========== LOGIN ==========
    fun login(
        username: String,
        password: String
    ): User? {

        val user = users.find {
            it.username == username &&
                    it.pass == password
        }

        currentUser = user

        return user
    }

    // ========== ADMINISTRAR EMPLEADOS ==========
    fun addEmployee(username: String, password: String): Boolean {
        if (username.isBlank() || password.isBlank()) return false
        if (users.any { it.username == username }) return false
        users.add(User(username = username, pass = password, role = "EMPLOYEE"))
        addLog("Empleado $username creado.")
        return true
    }

    fun deleteEmployee(username: String) {
        if (username == "administrador") return
        users.removeAll { it.username == username && it.role == "EMPLOYEE" }
        addLog("Empleado $username eliminado.")
    }

    // ========== PEDIDOS ==========

    fun generateUniqueCode(): String {
        var newCode: String
        do {
            val randomNumber = (100000..999999).random()
            newCode = "LRP-$randomNumber"
        } while (orders.any { it.code == newCode })
        return newCode
    }

    fun addOrder(c: String, d: String, o: String, de: String, n: String, dni: String) {
        val order = Order(
            code = c,
            description = d,
            origin = o,
            destino = de,
            recipientName = n,
            recipientDNI = dni,
            estado = "En Agencia",
            ubicacionActual = o
        )
        orderRepository.saveOrder(order)
        addLog("Pedido $c registrado en $o.")
    }

    fun addTruck(plate: String, driver: String) {
        trucks.add(Truck(plate = plate, driver = driver, location = "Agencia Origen"))
        addLog("Camión $plate registrado.")
    }
    fun generateUniquePlate(): String {
        val letras = ('A'..'Z')
        var newPlate: String
        do {
            val l = (1..3).map { letras.random() }.joinToString("")
            val n = (100..999).random()
            newPlate = "$l-$n"
        } while (trucks.any { it.plate.equals(newPlate, ignoreCase = true) })
        return newPlate
    }

    fun plateExists(plate: String): Boolean {
        return trucks.any { it.plate.equals(plate.trim(), ignoreCase = true) }
    }
    private fun normalizeName(name: String): String {
        val sinAcentos = java.text.Normalizer.normalize(name, java.text.Normalizer.Form.NFD)
            .replace(Regex("\\p{M}"), "") // quita tildes (á->a, é->e, etc.)

        return sinAcentos
            .lowercase()
            .replace("-", " ")           // guiones -> espacio
            .replace(Regex("\\s+"), " ") // espacios múltiples -> uno solo
            .trim()
    }

    fun driverExists(nombreCompleto: String): Boolean {
        val normalizedInput = normalizeName(nombreCompleto)
        return trucks.any { normalizeName(it.driver) == normalizedInput }
    }

    fun assignOrderToTruck(orderCode: String, truckPlate: String) {
        val index = orders.indexOfFirst { it.code == orderCode }
        if (index != -1) {
            val updated = orders[index].copy(assignedTruckPlate = truckPlate, estado = "En Camino")
            orderRepository.saveOrder(updated)
            addLog("Pedido $orderCode cargado al camión $truckPlate.")
        }
    }

    fun updateTruckLocation(plate: String, loc: String) {
        val tIndex = trucks.indexOfFirst { it.plate == plate }
        if (tIndex != -1) {
            trucks[tIndex] = trucks[tIndex].copy(location = loc)

            orders.forEach { order ->
                if (order.assignedTruckPlate == plate) {
                    val updated = order.copy(ubicacionActual = loc, estado = "En Camino")
                    orderRepository.saveOrder(updated)
                }
            }

            addLog("Camión $plate llegó a $loc.")
        }
    }

    fun markAsReady(orderCode: String) {
        val index = orders.indexOfFirst { it.code == orderCode }
        if (index != -1) {
            val updated = orders[index].copy(estado = "Listo para Recojo", assignedTruckPlate = null)
            orderRepository.saveOrder(updated)
            addLog("Pedido $orderCode listo para recojo.")
        }
    }

    fun markAsDelivered(orderCode: String) {
        val index = orders.indexOfFirst { it.code == orderCode }
        if (index != -1) {
            val updated = orders[index].copy(estado = "Entregado", assignedTruckPlate = null)
            orderRepository.saveOrder(updated)
            addLog("Pedido $orderCode entregado.")
        }
    }
}
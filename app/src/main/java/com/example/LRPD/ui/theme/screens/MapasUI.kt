package com.example.LRPD.ui.theme.screens

import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.viewinterop.AndroidView
import com.example.LRPD.mapas.OsrmService
import com.example.LRPD.model.Order
import com.example.LRPD.viewmodel.OrderViewModel
import kotlinx.coroutines.delay
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun MapasUI(vModel: OrderViewModel, onBack: () -> Unit) {
    var pedidoSeleccionado by remember { mutableStateOf<Order?>(null) }

    if (pedidoSeleccionado == null) {
        ListaPedidosMapa(vModel.orders, onBack) {
            pedidoSeleccionado = it
        }
    } else {
        MapaPedido(pedidoSeleccionado!!) {
            pedidoSeleccionado = null
        }
    }
}

@Composable
fun ListaPedidosMapa(
    pedidos: List<Order>,
    onBack: () -> Unit,
    onVerMapa: (Order) -> Unit
) {
    var textoBusqueda by remember { mutableStateOf("") }

    val pedidosFiltrados = pedidos.filter {
        it.code.contains(textoBusqueda, true) ||
                it.origin.contains(textoBusqueda, true) ||
                it.destino.contains(textoBusqueda, true) ||
                it.recipientName.contains(textoBusqueda, true) ||
                it.recipientDNI.contains(textoBusqueda, true) ||
                it.estado.contains(textoBusqueda, true)
    }.sortedByDescending { it.fechaRegistro }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFF2E7D32), Color(0xFF1B5E20))
                    )
                )
                .padding(18.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(32.dp)
                        .clickable { onBack() }
                )

                Spacer(modifier = Modifier.width(14.dp))

                Column {
                    Text(
                        "Seguimiento de Pedidos",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                    Text(
                        "Pedidos registrados desde Firebase",
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 13.sp
                    )
                }
            }
        }

        OutlinedTextField(
            value = textoBusqueda,
            onValueChange = { textoBusqueda = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            placeholder = { Text("Buscar código, destino, DNI o estado...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            singleLine = true
        )

        LazyColumn(
            modifier = Modifier.padding(horizontal = 14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(pedidosFiltrados) { pedido ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF1ECF5))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.LocalShipping,
                                contentDescription = null,
                                tint = Color(0xFF2E7D32),
                                modifier = Modifier.size(34.dp)
                            )

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(pedido.code, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                Text("Destino: ${pedido.destino}")
                                Text("Destinatario: ${pedido.recipientName}")
                                Text("Estado: ${pedido.estado}")
                                Text(formatearFecha(pedido.fechaRegistro), fontSize = 12.sp, color = Color.Gray)
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text("${pedido.origin}  →  ${pedido.destino}", fontWeight = FontWeight.Bold)

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = { onVerMapa(pedido) },
                            modifier = Modifier.align(Alignment.End),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
                        ) {
                            Icon(Icons.Default.Map, contentDescription = null)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Ver Mapa")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MapaPedido(
    pedido: Order,
    onBack: () -> Unit
) {
    val origen = coordenadasSede(pedido.origin)
    val destino = coordenadasSede(pedido.destino)

    var ruta by remember { mutableStateOf<List<GeoPoint>>(emptyList()) }
    var posicionCamion by remember { mutableStateOf(origen) }

    LaunchedEffect(pedido.code) {
        val rutaReal = OsrmService.obtenerRuta(
            origen.latitude,
            origen.longitude,
            destino.latitude,
            destino.longitude
        ).map { GeoPoint(it.first, it.second) }

        ruta = if (rutaReal.isNotEmpty()) rutaReal else listOf(origen, destino)

        for (punto in ruta) {
            posicionCamion = punto
            delay(900)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                Configuration.getInstance().userAgentValue = context.packageName

                MapView(context).apply {
                    setTileSource(TileSourceFactory.MAPNIK)
                    isTilesScaledToDpi = false
                    setUseDataConnection(true)
                    setMultiTouchControls(true)


                    setTileSource(TileSourceFactory.MAPNIK)
                    setMultiTouchControls(true)
                    isTilesScaledToDpi = true
                    minZoomLevel = 5.0
                    maxZoomLevel = 20.0

                    controller.setZoom(8.5)
                    controller.setCenter(origen)
                }
            },
            update = { mapView ->
                mapView.overlays.clear()

                val lineaRuta = Polyline().apply {
                    setPoints(ruta)
                    outlinePaint.color = android.graphics.Color.rgb(103, 58, 183)
                    outlinePaint.strokeWidth = 6f
                    outlinePaint.isAntiAlias = true
                    outlinePaint.strokeCap = android.graphics.Paint.Cap.ROUND
                    outlinePaint.strokeJoin = android.graphics.Paint.Join.ROUND
                }

                val puntoOrigen = CirculoMapa(
                    centro = origen,
                    color = android.graphics.Color.rgb(46, 125, 50),
                    radio = 12f
                )

                val puntoDestino = CirculoMapa(
                    centro = destino,
                    color = android.graphics.Color.rgb(233, 30, 99),
                    radio = 12f
                )

                val puntoCamion = CirculoMapa(
                    centro = posicionCamion,
                    color = android.graphics.Color.rgb(25, 118, 210),
                    radio = 10f
                )

                mapView.overlays.add(lineaRuta)
                mapView.overlays.add(puntoOrigen)
                mapView.overlays.add(puntoDestino)
                mapView.overlays.add(puntoCamion)
                mapView.invalidate()
            }
        )

        Card(
            modifier = Modifier
                .statusBarsPadding()
                .padding(14.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(6.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.96f)
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = Color(0xFF1A237E),
                        modifier = Modifier
                            .size(30.dp)
                            .clickable { onBack() }
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = "Mapa de Seguimiento",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color(0xFF1A237E)
                        )
                        Text(
                            text = "Pedido: ${pedido.code}",
                            fontSize = 13.sp,
                            color = Color.Gray
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text("📍 Origen: ${pedido.origin}")
                Text("🎯 Destino: ${pedido.destino}")
                Text("🚚 Estado: ${pedido.estado}")
                Text("🚛 Camión: ${pedido.assignedTruckPlate ?: "Sin asignar"}")
                Text("📅 Fecha: ${formatearFecha(pedido.fechaRegistro)}")
            }
        }
    }
}

fun coordenadasSede(sede: String): GeoPoint {
    return when (sede.trim()) {
        "Lima (La Victoria)" -> GeoPoint(-12.0640, -77.0365)
        "Lima (Villa El Salvador)" -> GeoPoint(-12.2130, -76.9360)
        "Cañete" -> GeoPoint(-13.0751, -76.3835)
        "Chincha" -> GeoPoint(-13.4197, -76.1323)
        "Ica" -> GeoPoint(-14.0678, -75.7286)
        "Pisco" -> GeoPoint(-13.7103, -76.2054)
        "Nazca" -> GeoPoint(-14.8300, -74.9400)
        "Marcona" -> GeoPoint(-15.3600, -75.1650)
        else -> GeoPoint(-13.4197, -76.1323)
    }
}

fun formatearFecha(timestamp: Long): String {
    val formato = SimpleDateFormat("dd MMM yyyy • hh:mm a", Locale("es", "PE"))
    return formato.format(Date(timestamp))
}
class CirculoMapa(
    private val centro: GeoPoint,
    private val color: Int,
    private val radio: Float
) : org.osmdroid.views.overlay.Overlay() {

    override fun draw(
        canvas: android.graphics.Canvas,
        mapView: MapView,
        shadow: Boolean
    ) {
        if (shadow) return

        val point = android.graphics.Point()
        mapView.projection.toPixels(centro, point)

        val paint = android.graphics.Paint().apply {
            isAntiAlias = true
            style = android.graphics.Paint.Style.FILL
            this.color = color
        }

        val borde = android.graphics.Paint().apply {
            isAntiAlias = true
            style = android.graphics.Paint.Style.STROKE
            strokeWidth = 4f
            this.color = android.graphics.Color.WHITE
        }

        canvas.drawCircle(point.x.toFloat(), point.y.toFloat(), radio, paint)
        canvas.drawCircle(point.x.toFloat(), point.y.toFloat(), radio, borde)
    }
}
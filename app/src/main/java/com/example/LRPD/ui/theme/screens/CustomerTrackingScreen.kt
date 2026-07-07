package com.example.LRPD.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.LRPD.ui.theme.*
import com.example.LRPD.viewmodel.OrderViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerTrackingScreen(vModel: OrderViewModel, onBack: () -> Unit) {
    var dniInput by remember { mutableStateOf("") }
    val misPedidos = vModel.orders.filter { it.recipientDNI == dniInput }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Seguimiento de Envíos", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(CarrionBlue)
            )
        },
        containerColor = CarrionWhite
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {

            // ===== BUSCADOR =====
            Card(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .background(CarrionBlue.copy(alpha = 0.1f), RoundedCornerShape(14.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Search, null, tint = CarrionBlue)
                        }
                        Spacer(Modifier.width(12.dp))
                        Column {
                            Text("Rastrea tu envío", style = MaterialTheme.typography.titleMedium, color = CarrionBlue, fontWeight = FontWeight.Bold)
                            Text("Ingresa tu número de DNI", fontSize = 12.sp, color = Color.Gray)
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    OutlinedTextField(
                        value = dniInput,
                        onValueChange = { dniInput = it },
                        label = { Text("Número de DNI") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        leadingIcon = { Icon(Icons.Default.Search, null, tint = CarrionBlue) },
                        singleLine = true
                    )
                }
            }

            // ===== CONTENIDO =====
            when {

                dniInput.isBlank() -> {
                    EmptyState(
                        emoji = "📦",
                        title = "Esperando tu DNI",
                        subtitle = "Ingresa tu número de DNI arriba para ver el estado de tus envíos"
                    )
                }

                misPedidos.isEmpty() -> {
                    EmptyState(
                        emoji = "🔍",
                        title = "Sin resultados",
                        subtitle = "No encontramos pedidos asociados a este DNI. Verifica el número e intenta de nuevo"
                    )
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        item {
                            Text(
                                "${misPedidos.size} pedido(s) encontrado(s)",
                                fontSize = 12.sp,
                                color = Color.Gray,
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                        }

                        items(misPedidos) { order ->
                            val (emoji, color, statusMsg) = when (order.estado) {
                                "En Agencia" -> Triple("🏠", CarrionError, "Recibido en Agencia")
                                "En Camino" -> Triple("🚚", Color(0xFF1976D2), "En Ruta a destino")
                                "Listo para Recojo" -> Triple("🎁", CarrionSuccess, "¡LISTO PARA RECOJO!")
                                else -> Triple("✅", Color.Gray, "Entregado")
                            }

                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(18.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                elevation = CardDefaults.cardElevation(3.dp)
                            ) {
                                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(54.dp)
                                            .background(color.copy(alpha = 0.12f), RoundedCornerShape(16.dp)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(emoji, fontSize = 26.sp)
                                    }
                                    Spacer(Modifier.width(16.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text("Código: ${order.code}", fontWeight = FontWeight.ExtraBold, fontSize = 13.sp, color = Color.Gray)
                                        Spacer(Modifier.height(2.dp))
                                        Text(statusMsg, color = color, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                        if (order.estado == "En Camino") {
                                            Spacer(Modifier.height(2.dp))
                                            Text("📍 ${order.ubicacionActual}", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                                        }
                                        Spacer(Modifier.height(2.dp))
                                        Text("Destino: ${order.destino}", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyState(emoji: String, title: String, subtitle: String) {
    Box(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(emoji, fontSize = 56.sp)
            Spacer(Modifier.height(16.dp))
            Text(title, fontSize = 17.sp, fontWeight = FontWeight.Bold, color = CarrionBlue)
            Spacer(Modifier.height(6.dp))
            Text(
                subtitle,
                fontSize = 13.sp,
                color = Color.Gray,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}
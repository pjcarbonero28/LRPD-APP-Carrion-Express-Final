package com.example.LRPD.ui.theme.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.LRPD.model.Order
import com.example.LRPD.ui.theme.*
import com.example.LRPD.viewmodel.OrderViewModel
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.ui.draw.scale
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackingScreen(vModel: OrderViewModel, onBack: () -> Unit) {

    val enRuta = vModel.orders.filter { it.estado == "En Camino" }
    val otros = vModel.orders.filter { it.estado != "En Camino" }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Seguimiento en Tiempo Real", color = Color.White, fontWeight = FontWeight.Bold) },
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

        if (vModel.orders.isEmpty()) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("📦", fontSize = 48.sp)
                    Spacer(Modifier.height(8.dp))
                    Text("Aún no hay pedidos registrados", color = Color.Gray)
                }
            }
            return@Scaffold
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            item {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    StatChip("🚚", enRuta.size.toString(), "En Camino", Modifier.weight(1f))
                    StatChip("📍", vModel.orders.count { it.estado == "En Agencia" }.toString(), "En Agencia", Modifier.weight(1f))
                    StatChip("✅", vModel.orders.count { it.estado == "Entregado" }.toString(), "Entregados", Modifier.weight(1f))
                }
            }

            if (enRuta.isNotEmpty()) {
                item { Text("En camino ahora", fontWeight = FontWeight.Bold, color = CarrionBlue, fontSize = 16.sp) }
                items(enRuta) { order -> OrderRouteCard(order) { code -> vModel.markAsDelivered(code) } }
            }

            if (otros.isNotEmpty()) {
                item {
                    Text(
                        "Otros pedidos",
                        fontWeight = FontWeight.Bold,
                        color = CarrionBlue,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
                items(otros) { order -> OrderRouteCard(order) { code -> vModel.markAsDelivered(code) } }
            }
        }
    }
}

@Composable
private fun StatChip(emoji: String, value: String, label: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp).fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(emoji, fontSize = 20.sp)
            Text(value, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = CarrionBlue)
            Text(label, fontSize = 11.sp, color = Color.Gray)
        }
    }
}

@Composable
private fun OrderRouteCard(order: Order, onDelivered: (String) -> Unit) {

    val baseProgress = when (order.estado) {
        "En Agencia" -> 0.05f
        "En Camino" -> 0.30f
        "Listo para Recojo" -> 0.97f
        else -> 1f
    }

    val progressAnim = remember { Animatable(baseProgress) }

    LaunchedEffect(order.estado, order.code) {
        if (order.estado == "En Camino") {
            progressAnim.snapTo(baseProgress)
            progressAnim.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 300000, easing = LinearEasing)
            )
            onDelivered(order.code) // llega al final -> se marca como entregado
        } else {
            progressAnim.snapTo(baseProgress)
        }
    }

    val animatedProgress = progressAnim.value

    val statusColor = when (order.estado) {
        "En Agencia" -> CarrionError
        "En Camino" -> Color(0xFF1976D2)
        "Listo para Recojo" -> CarrionSuccess
        else -> Color.Gray
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Column(Modifier.padding(16.dp)) {

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Código: ${order.code}", fontWeight = FontWeight.ExtraBold, fontSize = 14.sp)
                Box(
                    Modifier
                        .background(statusColor.copy(alpha = 0.12f), RoundedCornerShape(10.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(order.estado, color = statusColor, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                }
            }

            Spacer(Modifier.height(14.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(order.origin, fontSize = 11.sp, color = Color.Gray)
                Text(order.destino, fontSize = 11.sp, color = Color.Gray)
            }

            Spacer(Modifier.height(6.dp))

            Box(Modifier.fillMaxWidth().height(26.dp)) {
                Box(
                    Modifier
                        .align(Alignment.CenterStart)
                        .fillMaxWidth()
                        .height(4.dp)
                        .background(Color(0xFFE0E0E0), RoundedCornerShape(2.dp))
                )
                Box(
                    Modifier
                        .align(Alignment.CenterStart)
                        .fillMaxWidth(animatedProgress)
                        .height(4.dp)
                        .background(CarrionBlue, RoundedCornerShape(2.dp))
                )
                Box(
                    Modifier
                        .align(Alignment.CenterStart)
                        .fillMaxWidth(animatedProgress),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Text(
                        "🚚",
                        fontSize = 20.sp,
                        modifier = Modifier.scale(scaleX = -1f, scaleY = 1f)
                    )
                }
            }

            if (order.estado == "En Camino") {
                Spacer(Modifier.height(6.dp))
                Text("📍 Ubicación actual: ${order.ubicacionActual}", fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}
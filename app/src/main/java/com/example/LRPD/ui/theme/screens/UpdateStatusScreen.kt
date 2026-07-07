package com.example.LRPD.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.LRPD.model.Order
import com.example.LRPD.ui.theme.*
import com.example.LRPD.viewmodel.OrderViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateStatusScreen(vModel: OrderViewModel, onBack: () -> Unit) {

    val pedidosActivos = vModel.orders.filter { it.estado != "Entregado" }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Actualizar Estado", color = Color.White, fontWeight = FontWeight.Bold) },
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

        if (pedidosActivos.isEmpty()) {
            Box(
                Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("✅", fontSize = 48.sp)
                    Spacer(Modifier.height(8.dp))
                    Text("No hay pedidos pendientes por actualizar", color = Color.Gray)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(pedidosActivos) { order ->
                    UpdateStatusCard(order = order, vModel = vModel)
                }
            }
        }
    }
}

@Composable
private fun UpdateStatusCard(order: Order, vModel: OrderViewModel) {

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

            Spacer(Modifier.height(6.dp))

            Text("Destinatario: ${order.recipientName}", fontSize = 12.sp, color = Color.Gray)
            Text("Destino: ${order.destino}", fontSize = 12.sp, color = Color.Gray)

            Spacer(Modifier.height(12.dp))

            when (order.estado) {

                "En Agencia" -> {
                    Text(
                        "Asigna esta orden a un camión desde la pantalla de Asignación para que avance.",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                "En Camino" -> {
                    Button(
                        onClick = { vModel.markAsReady(order.code) },
                        modifier = Modifier.fillMaxWidth().height(46.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = CarrionSuccess)
                    ) {
                        Text("MARCAR LISTO PARA RECOJO", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }

                "Listo para Recojo" -> {
                    Button(
                        onClick = { vModel.markAsDelivered(order.code) },
                        modifier = Modifier.fillMaxWidth().height(46.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = CarrionBlue)
                    ) {
                        Text("MARCAR ENTREGADO", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }
            }
        }
    }
}
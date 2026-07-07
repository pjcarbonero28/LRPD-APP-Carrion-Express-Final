package com.example.LRPD.ui.theme.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.LRPD.model.Order
import com.example.LRPD.ui.theme.CarrionBlue
import com.example.LRPD.ui.theme.CarrionWhite

@Composable
fun OrderCard(order: Order, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = CarrionWhite),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Pedido: #${order.code}",
                color = CarrionBlue,
                style = MaterialTheme.typography.titleMedium
            )
            Text(text = "Producto: ${order.description}")
            Text(text = "Destino: ${order.destino}")
            Text(
                text = "Estado: ${order.estado}",
                color = CarrionBlue,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}
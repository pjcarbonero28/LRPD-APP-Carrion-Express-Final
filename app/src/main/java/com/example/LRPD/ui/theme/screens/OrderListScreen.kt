package com.example.LRPD.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.LRPD.ui.theme.*
import com.example.LRPD.ui.theme.components.OrderCard
import com.example.LRPD.viewmodel.OrderViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderListScreen(vModel: OrderViewModel, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Inventario de Envíos", color = CarrionWhite) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, null, tint = CarrionWhite)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = CarrionBlue)
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            if (vModel.orders.isEmpty()) {
                Text("No hay pedidos registrados.", modifier = Modifier.padding(20.dp))
            } else {
                LazyColumn {
                    items(vModel.orders) { order ->
                        OrderCard(order = order, onClick = { /* Ver detalle si deseas */ })
                    }
                }
            }
        }
    }
}
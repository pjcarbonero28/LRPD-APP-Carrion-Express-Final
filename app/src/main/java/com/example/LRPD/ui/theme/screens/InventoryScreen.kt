package com.example.LRPD.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.LRPD.model.SedesCarrion
import com.example.LRPD.ui.theme.*
import com.example.LRPD.viewmodel.OrderViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryScreen(vModel: OrderViewModel, onBack: () -> Unit) {
    var tabSelected by remember { mutableIntStateOf(0) }
    var sedeFiltro by remember { mutableStateOf(SedesCarrion[3]) } // Chincha

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Inventario General", color = Color.White) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = Color.White) } },
                colors = TopAppBarDefaults.topAppBarColors(CarrionBlue)
            )
        }
    ) { padding ->
        Column(Modifier.padding(padding)) {
            TabRow(selectedTabIndex = tabSelected) {
                Tab(selected = tabSelected == 0, onClick = { tabSelected = 0 }, text = { Text("Almacén") })
                Tab(selected = tabSelected == 1, onClick = { tabSelected = 1 }, text = { Text("Entregados") })
            }

            // Filtro de Sede
            ScrollableTabRow(selectedTabIndex = SedesCarrion.indexOf(sedeFiltro), edgePadding = 16.dp) {
                SedesCarrion.forEach { sede ->
                    Tab(selected = sedeFiltro == sede, onClick = { sedeFiltro = sede }, text = { Text(sede) })
                }
            }

            LazyColumn(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                val filtrados = vModel.orders.filter {
                    it.ubicacionActual == sedeFiltro &&
                            (if(tabSelected == 0) it.estado != "Entregado" else it.estado == "Entregado")
                }

                items(filtrados) { order ->
                    Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(2.dp)) {
                        Column(Modifier.padding(12.dp)) {
                            Text("Cód: ${order.code}", fontWeight = FontWeight.Bold, color = CarrionBlue)
                            Text("Producto: ${order.description}")
                            Text("Estado: ${order.estado}", fontWeight = FontWeight.Bold, color = if(order.estado == "Entregado") Color.Gray else CarrionYellow)

                            if (tabSelected == 0 && order.estado == "Listo para Recojo") {
                                Button(
                                    onClick = { vModel.markAsDelivered(order.code) },
                                    modifier = Modifier.padding(top = 8.dp).fillMaxWidth(),
                                    colors = ButtonDefaults.buttonColors(containerColor = CarrionSuccess)
                                ) {
                                    Text("ENTREGAR AL CLIENTE", color = Color.White)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
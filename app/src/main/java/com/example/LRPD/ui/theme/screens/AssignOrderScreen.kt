package com.example.LRPD.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.LRPD.ui.theme.*
import com.example.LRPD.viewmodel.OrderViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssignOrderScreen(vModel: OrderViewModel, onBack: () -> Unit) {
    var selectedTruck by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Carga de Camiones", color = Color.White, fontWeight = FontWeight.Bold) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = Color.White) } },
                colors = TopAppBarDefaults.topAppBarColors(CarrionBlue)
            )
        }
    ) { padding ->
        Column(Modifier.padding(padding).padding(16.dp)) {
            Text("1. Seleccione Camión de Flota:", style = MaterialTheme.typography.titleMedium, color = CarrionBlue)

            vModel.trucks.forEach { truck ->
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 4.dp)) {
                    RadioButton(selected = selectedTruck == truck.plate, onClick = { selectedTruck = truck.plate })
                    Text("${truck.plate} - Chofer: ${truck.driver}")
                }
            }

            HorizontalDivider(Modifier.padding(vertical = 12.dp))

            Text("2. Pedidos en Almacén:", fontWeight = FontWeight.Bold, color = CarrionBlue)
            LazyColumn(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(vModel.orders.filter { it.estado == "En Agencia" }) { order ->
                    Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = CarrionGray)) {
                        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Column(Modifier.weight(1f)) {
                                Text("Cód: ${order.code}", fontWeight = FontWeight.Bold)
                                Text("Destino: ${order.destino}", style = MaterialTheme.typography.bodySmall)
                            }
                            Button(
                                onClick = { if(selectedTruck.isNotEmpty()) vModel.assignOrderToTruck(order.code, selectedTruck) },
                                enabled = selectedTruck.isNotEmpty(),
                                colors = ButtonDefaults.buttonColors(containerColor = CarrionBlue)
                            ) {
                                Text("CARGAR", color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }
}
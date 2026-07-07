package com.example.LRPD.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.LRPD.model.SedesCarrion
import com.example.LRPD.ui.theme.*
import com.example.LRPD.viewmodel.OrderViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransportistUpdateScreen(vModel: OrderViewModel, onBack: () -> Unit) {
    var plate by remember { mutableStateOf("") }
    var currentSede by remember { mutableStateOf(SedesCarrion[3]) }
    var expSede by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Panel de Ruta", color = Color.White) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = Color.White) } },
                colors = TopAppBarDefaults.topAppBarColors(CarrionBlue)
            )
        }
    ) { padding ->
        Column(Modifier.padding(padding).padding(16.dp)) {
            OutlinedTextField(plate, { plate = it }, label = { Text("Placa del Camión") }, modifier = Modifier.fillMaxWidth())

            Text("Ubicación Actual (Sede):", modifier = Modifier.padding(top = 8.dp))
            Box(Modifier.fillMaxWidth()) {
                OutlinedTextField(currentSede, {}, readOnly = true, modifier = Modifier.fillMaxWidth(), trailingIcon = { IconButton(onClick = { expSede = true }) { Icon(Icons.Default.ArrowDropDown, null) } })
                DropdownMenu(expanded = expSede, onDismissRequest = { expSede = false }) {
                    SedesCarrion.forEach { s -> DropdownMenuItem(text = { Text(s) }, onClick = { currentSede = s; expSede = false }) }
                }
            }

            Button(onClick = { vModel.updateTruckLocation(plate, currentSede) },
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp), colors = ButtonDefaults.buttonColors(CarrionBlue)) {
                Text("ACTUALIZAR UBICACIÓN GPS", color = Color.White)
            }

            HorizontalDivider(Modifier.padding(vertical = 16.dp))
            Text("Pedidos en este camión:", fontWeight = FontWeight.Bold)

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(vModel.orders.filter { it.assignedTruckPlate == plate }) { order ->
                    Card(Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                        Row(Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                            Text(order.code, Modifier.weight(1f), fontWeight = FontWeight.Bold)
                            Button(onClick = { vModel.markAsReady(order.code) }, colors = ButtonDefaults.buttonColors(CarrionYellow)) {
                                Text("LLEGÓ A SEDE", color = Color.Black)
                            }
                        }
                    }
                }
            }
        }
    }
}
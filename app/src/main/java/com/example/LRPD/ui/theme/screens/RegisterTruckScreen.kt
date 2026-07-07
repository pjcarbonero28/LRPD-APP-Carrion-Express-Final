package com.example.LRPD.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
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
fun RegisterTruckScreen(vModel: OrderViewModel, onBack: () -> Unit) {
    var plate by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var mensajeError by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registrar Unidad", color = Color.White) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, null, tint = Color.White) } },
                colors = TopAppBarDefaults.topAppBarColors(CarrionBlue)
            )
        }
    ) { padding ->
        Column(Modifier.padding(padding).padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text("Alta de Camiones", style = MaterialTheme.typography.headlineSmall, color = CarrionBlue, fontWeight = FontWeight.Bold)

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = plate,
                    onValueChange = { plate = it; mensajeError = "" },
                    label = { Text("Placa") },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.weight(1f)
                )

                FilledIconButton(
                    onClick = {
                        plate = vModel.generateUniquePlate()
                        mensajeError = ""
                    },
                    modifier = Modifier.size(56.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = IconButtonDefaults.filledIconButtonColors(containerColor = CarrionYellow)
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = "Generar placa", tint = Color.Black)
                }
            }

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre *") },
                leadingIcon = { Icon(Icons.Default.Person, null) },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = apellido,
                onValueChange = { apellido = it },
                label = { Text("Apellido *") },
                leadingIcon = { Icon(Icons.Default.Person, null) },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth()
            )

            if (mensajeError.isNotEmpty()) {
                Text(
                    text = mensajeError,
                    color = CarrionError,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Button(
                onClick = {
                    val nombreCompleto = "${nombre.trim()} ${apellido.trim()}"

                    when {
                        plate.isBlank() -> mensajeError = "Ingrese la placa"
                        nombre.isBlank() -> mensajeError = "Ingrese el nombre del chofer"
                        apellido.isBlank() -> mensajeError = "Ingrese el apellido del chofer"
                        vModel.plateExists(plate) -> mensajeError = "Ya existe un camión registrado con esa placa"
                        vModel.driverExists(nombreCompleto) -> mensajeError = "Ya existe un camión registrado con ese chofer"
                        else -> {
                            vModel.addTruck(plate.trim().uppercase(), nombreCompleto)
                            onBack()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(55.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CarrionYellow)
            ) {
                Text("REGISTRAR CAMIÓN", color = CarrionDark, fontWeight = FontWeight.Bold)
            }
        }
    }
}
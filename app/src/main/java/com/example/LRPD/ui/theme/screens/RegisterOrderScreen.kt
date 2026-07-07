package com.example.LRPD.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.LRPD.model.SedesCarrion
import com.example.LRPD.ui.theme.*
import com.example.LRPD.viewmodel.OrderViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterOrderScreen(vModel: OrderViewModel, onBack: () -> Unit) {
    var code by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var dni by remember { mutableStateOf("") }
    var ori by remember { mutableStateOf(SedesCarrion[3]) }
    var dest by remember { mutableStateOf(SedesCarrion[0]) }
    var exp1 by remember { mutableStateOf(false) }
    var exp2 by remember { mutableStateOf(false) }
    var mensajeError by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nuevo Registro", color = Color.White, fontWeight = FontWeight.Bold) },
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
        Column(
            Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            // ===== TARJETA: DATOS DEL PAQUETE =====
            Card(
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {

                    Text("Datos del Paquete", fontWeight = FontWeight.Bold, color = CarrionBlue, fontSize = 16.sp)

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = code,
                            onValueChange = { code = it; mensajeError = "" },
                            label = { Text("Código de Seguimiento") },
                            leadingIcon = { Icon(Icons.Default.QrCode2, null) },
                            singleLine = true,
                            shape = RoundedCornerShape(14.dp),
                            modifier = Modifier.weight(1f)
                        )

                        FilledIconButton(
                            onClick = { code = vModel.generateUniqueCode() },
                            modifier = Modifier.size(56.dp),
                            shape = RoundedCornerShape(14.dp),
                            colors = IconButtonDefaults.filledIconButtonColors(containerColor = CarrionYellow)
                        ) {
                            Icon(Icons.Default.Refresh, contentDescription = "Generar código", tint = Color.Black)
                        }
                    }

                    OutlinedTextField(
                        value = desc,
                        onValueChange = { desc = it },
                        label = { Text("Descripción del Paquete") },
                        leadingIcon = { Icon(Icons.Default.Inventory2, null) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp)
                    )
                }
            }

            // ===== TARJETA: DATOS DEL DESTINATARIO =====
            Card(
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {

                    Text("Destinatario", fontWeight = FontWeight.Bold, color = CarrionBlue, fontSize = 16.sp)

                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = { Text("Nombre *") },
                        leadingIcon = { Icon(Icons.Default.Person, null) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp)
                    )

                    OutlinedTextField(
                        value = apellido,
                        onValueChange = { apellido = it },
                        label = { Text("Apellido *") },
                        leadingIcon = { Icon(Icons.Default.Person, null) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp)
                    )

                    OutlinedTextField(
                        value = dni,
                        onValueChange = { dni = it },
                        label = { Text("DNI del Destinatario *") },
                        leadingIcon = { Icon(Icons.Default.Badge, null) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp)
                    )
                }
            }

            // ===== TARJETA: RUTA =====
            Card(
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {

                    Text("Ruta", fontWeight = FontWeight.Bold, color = CarrionBlue, fontSize = 16.sp)

                    Text("Origen:", color = Color.Gray, fontSize = 13.sp)
                    Box {
                        OutlinedTextField(
                            value = ori,
                            onValueChange = {},
                            readOnly = true,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            trailingIcon = {
                                IconButton(onClick = { exp1 = true }) {
                                    Icon(Icons.Default.ArrowDropDown, null)
                                }
                            }
                        )
                        DropdownMenu(expanded = exp1, onDismissRequest = { exp1 = false }) {
                            SedesCarrion.forEach { s ->
                                DropdownMenuItem(text = { Text(s) }, onClick = { ori = s; exp1 = false })
                            }
                        }
                    }

                    Spacer(Modifier.height(4.dp))

                    Text("Destino:", color = Color.Gray, fontSize = 13.sp)
                    Box {
                        OutlinedTextField(
                            value = dest,
                            onValueChange = {},
                            readOnly = true,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            trailingIcon = {
                                IconButton(onClick = { exp2 = true }) {
                                    Icon(Icons.Default.ArrowDropDown, null)
                                }
                            }
                        )
                        DropdownMenu(expanded = exp2, onDismissRequest = { exp2 = false }) {
                            SedesCarrion.forEach { s ->
                                DropdownMenuItem(text = { Text(s) }, onClick = { dest = s; exp2 = false })
                            }
                        }
                    }
                }
            }

            if (mensajeError.isNotEmpty()) {
                Text(
                    text = mensajeError,
                    color = CarrionError,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }

            Button(
                onClick = {
                    when {
                        code.isBlank() -> mensajeError = "Ingrese o genere el código de seguimiento"
                        desc.isBlank() -> mensajeError = "Ingrese la descripción del paquete"
                        nombre.isBlank() -> mensajeError = "Ingrese el nombre del destinatario"
                        apellido.isBlank() -> mensajeError = "Ingrese el apellido del destinatario"
                        dni.isBlank() -> mensajeError = "Ingrese el DNI del destinatario"
                        else -> {
                            val fullName = "${nombre.trim()} ${apellido.trim()}"
                            vModel.addOrder(code.trim(), desc.trim(), ori, dest, fullName, dni.trim())
                            onBack()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CarrionYellow)
            ) {
                Text("FINALIZAR REGISTRO", color = Color.Black, fontWeight = FontWeight.Bold)
            }

            // Espacio extra al final para que el botón no quede pegado al borde inferior
            Spacer(Modifier.height(24.dp))
        }
    }
}
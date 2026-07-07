package com.example.LRPD.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Business
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.LRPD.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupplierScreen(onBack: () -> Unit) {
    val proveedores = listOf("Imprenta La Esperanza", "Papelera del Sur", "Logística Chincha SAC")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Módulo de Proveedores", color = CarrionWhite) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, null, tint = CarrionWhite) } },
                colors = TopAppBarDefaults.topAppBarColors(CarrionBlue)
            )
        }
    ) { padding ->
        Column(Modifier.padding(padding).padding(16.dp)) {
            Text("Abastecimiento de Insumos", style = MaterialTheme.typography.titleMedium)
            LazyColumn {
                items(proveedores.size) { index ->
                    Card(Modifier.fillMaxWidth().padding(vertical = 5.dp), colors = CardDefaults.cardColors(CarrionGray)) {
                        ListItem(
                            headlineContent = { Text(proveedores[index]) },
                            supportingContent = { Text("Estado de entrega: Completado") },
                            trailingContent = { Icon(Icons.Default.Business, null, tint = CarrionBlue) }
                        )
                    }
                }
            }
            Button(onClick = { }, Modifier.fillMaxWidth().padding(top = 20.dp)) {
                Text("REGISTRAR NUEVO PROVEEDOR")
            }
        }
    }
}
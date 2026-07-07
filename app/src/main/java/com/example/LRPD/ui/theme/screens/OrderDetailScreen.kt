package com.example.LRPD.ui.theme.screens
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun OrderDetailScreen(onTrack: () -> Unit, onUpdate: () -> Unit) {
    Column(Modifier.padding(20.dp)) {
        Text("Detalles", style = MaterialTheme.typography.headlineSmall)
        Button(onTrack, Modifier.fillMaxWidth().padding(top=10.dp)) { Text("RASTREAR") }
        Button(onUpdate, Modifier.fillMaxWidth().padding(top=10.dp)) { Text("ACTUALIZAR") }
    }
}
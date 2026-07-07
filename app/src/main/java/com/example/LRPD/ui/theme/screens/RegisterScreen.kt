package com.example.LRPD.ui.theme.screens
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RegisterScreen(onBack: () -> Unit) {
    Column(Modifier.padding(20.dp)) {
        Text("Registro", style = MaterialTheme.typography.headlineMedium)
        OutlinedTextField("", {}, label = {Text("DNI")}, modifier = Modifier.fillMaxWidth())
        Button(onBack, Modifier.padding(top=20.dp)) { Text("REGISTRAR") }
    }
}
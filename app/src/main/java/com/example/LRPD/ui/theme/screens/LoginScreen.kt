package com.example.LRPD.ui.theme.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.LRPD.ui.theme.CarrionBlue
import com.example.LRPD.ui.theme.CarrionWhite
import com.example.LRPD.ui.theme.CarrionYellow
import com.example.LRPD.viewmodel.OrderViewModel

@Composable
fun LoginScreen(
    vModel: OrderViewModel,
    onAdminLogin: () -> Unit,
    onEmployeeLogin: () -> Unit,
    onCustomerClick: () -> Unit
) {

    var mostrarAccesoInterno by remember { mutableStateOf(false) }
    var usuarioInput by remember { mutableStateOf("") }
    var passwordInput by remember { mutableStateOf("") }
    var mensajeError by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(CarrionBlue, Color(0xFF0D3A5C))
                )
            )
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                imageVector = Icons.Default.LocalShipping,
                contentDescription = null,
                tint = CarrionWhite,
                modifier = Modifier.size(56.dp)
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Carrión Express",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = CarrionWhite
            )

            Spacer(Modifier.height(32.dp))

            // ===== TARJETA PRINCIPAL: CLIENTE =====
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .background(CarrionYellow.copy(alpha = 0.15f), RoundedCornerShape(20.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                            tint = CarrionYellow,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Spacer(Modifier.height(16.dp))

                    Text(
                        text = "¿Esperas un paquete?",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = CarrionBlue
                    )

                    Spacer(Modifier.height(4.dp))

                    Text(
                        text = "Rastrea tu envío con tu DNI en segundos",
                        fontSize = 13.sp,
                        color = Color.Gray
                    )

                    Spacer(Modifier.height(20.dp))

                    Button(
                        onClick = onCustomerClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = CarrionYellow)
                    ) {
                        Text(
                            text = "RASTREAR MI PEDIDO",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            // ===== ACCESO INTERNO (colapsado por defecto) =====
            TextButton(onClick = { mostrarAccesoInterno = !mostrarAccesoInterno }) {
                Icon(
                    imageVector = Icons.Default.AdminPanelSettings,
                    contentDescription = null,
                    tint = CarrionWhite.copy(alpha = 0.8f),
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.width(6.dp))
                Text(
                    text = "Acceso de personal",
                    color = CarrionWhite.copy(alpha = 0.85f),
                    fontSize = 13.sp
                )
                Spacer(Modifier.width(4.dp))
                Icon(
                    imageVector = if (mostrarAccesoInterno) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = CarrionWhite.copy(alpha = 0.8f),
                    modifier = Modifier.size(18.dp)
                )
            }

            AnimatedVisibility(
                visible = mostrarAccesoInterno,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.97f)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        OutlinedTextField(
                            value = usuarioInput,
                            onValueChange = { usuarioInput = it },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            label = { Text("Usuario") },
                            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                            shape = RoundedCornerShape(14.dp)
                        )

                        Spacer(Modifier.height(10.dp))

                        OutlinedTextField(
                            value = passwordInput,
                            onValueChange = { passwordInput = it },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            label = { Text("Contraseña") },
                            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                            visualTransformation = PasswordVisualTransformation(),
                            shape = RoundedCornerShape(14.dp)
                        )

                        if (mensajeError.isNotEmpty()) {
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = mensajeError,
                                color = Color.Red,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }

                        Spacer(Modifier.height(16.dp))

                        Button(
                            onClick = {
                                if (usuarioInput.isBlank() || passwordInput.isBlank()) {
                                    mensajeError = "Complete todos los campos"
                                } else {
                                    val user = vModel.login(usuarioInput, passwordInput)
                                    if (user != null) {
                                        mensajeError = ""
                                        if (user.role == "ADMIN") onAdminLogin() else onEmployeeLogin()
                                    } else {
                                        mensajeError = "Usuario o contraseña incorrectos"
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = CarrionBlue)
                        ) {
                            Text("INGRESAR", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}
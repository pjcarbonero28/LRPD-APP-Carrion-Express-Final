package com.example.LRPD.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Update
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.LRPD.ui.theme.CarrionBlue
import com.example.LRPD.ui.theme.CarrionWhite
import com.example.LRPD.ui.theme.CarrionYellow
import com.example.LRPD.viewmodel.OrderViewModel

@Composable
fun HomeEmployeeScreen(
    vModel: OrderViewModel,
    onNavigate: (String) -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF2E7D32),
                            Color(0xFF1B5E20)
                        )
                    )
                )
                .padding(20.dp)
        ) {

            Column {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Default.LocalShipping,
                        contentDescription = null,
                        tint = CarrionWhite,
                        modifier = Modifier.size(42.dp)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {

                        Text(
                            text = "Carrión Express",
                            color = CarrionWhite,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp
                        )

                        Text(
                            text = "Panel de Empleado",
                            color = CarrionWhite.copy(alpha = 0.85f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Hola, ${vModel.currentUser?.username ?: "Empleado"} 👋",
                    color = CarrionYellow,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp
                )

                Text(
                    text = "Seleccione una opción para continuar",
                    color = CarrionWhite
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    StatCard(vModel.orders.size.toString(), "Pedidos")
                    StatCard(vModel.trucks.size.toString(), "Rutas")
                    StatCard(vModel.orders.map { it.estado }.distinct().size.toString(), "Estados")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFE8F5E9)
            )
        ) {

            Column(
                modifier = Modifier.padding(18.dp)
            ) {

                Text(
                    text = "🚚 Tu esfuerzo mueve al mundo",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Gracias por formar parte de Carrión Express. Cada entrega cuenta."
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            EmployeeButton(
                title = "Registrar Pedido",
                icon = Icons.Default.LocalShipping
            ) {
                onNavigate("register_order")
            }

            EmployeeButton(
                title = "Mapa de Rutas",
                icon = Icons.Default.Map
            ) {
                onNavigate("mapa")
            }

            EmployeeButton(
                title = "Actualizar Estado",
                icon = Icons.Default.Update
            ) {
                onNavigate("update_status")
            }

            EmployeeButton(
                title = "Ver Historial",
                icon = Icons.Default.History
            ) {
                onNavigate("history")
            }

            EmployeeButton(
                title = "Cerrar Sesión",
                icon = Icons.Default.Logout
            ) {
                onNavigate("login")
            }
        }
    }
}

@Composable
fun EmployeeButton(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit
) {

    val iconColor =
        if (title == "Cerrar Sesión")
            Color.Red
        else
            CarrionBlue

    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(28.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp
                )

                Text(
                    text = when (title) {
                        "Registrar Pedido" -> "Crear un nuevo pedido"
                        "Mapa de Rutas" -> "Ver ubicación y rutas en el mapa"
                        "Seguimiento de Pedidos" -> "Ver ubicación en tiempo real"
                        "Actualizar Estado" -> "Actualizar entregas"
                        "Ver Historial" -> "Consultar registros"
                        else -> "Salir del sistema"
                    },
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = null,
                tint = iconColor
            )
        }
    }
}

@Composable
fun StatCard(
    value: String,
    title: String
) {

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.15f)
        )
    ) {

        Column(
            modifier = Modifier.padding(
                horizontal = 18.dp,
                vertical = 12.dp
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = value,
                color = CarrionWhite,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )

            Text(
                text = title,
                color = CarrionWhite.copy(alpha = 0.8f),
                fontSize = 11.sp
            )
        }
    }
}
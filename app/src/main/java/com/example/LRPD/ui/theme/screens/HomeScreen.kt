package com.example.LRPD.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.LRPD.ui.theme.CarrionBlue
import com.example.LRPD.ui.theme.CarrionDark
import com.example.LRPD.ui.theme.CarrionError
import com.example.LRPD.ui.theme.CarrionWhite
import com.example.LRPD.ui.theme.CarrionYellow
import com.example.LRPD.viewmodel.OrderViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    vModel: OrderViewModel,
    onNavigate: (String) -> Unit
) {
    Scaffold(
        containerColor = CarrionWhite
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            // ── Header con gradiente azul ──────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(CarrionBlue, Color(0xFF0D5C82))
                        )
                    )
                    .padding(horizontal = 20.dp, vertical = 22.dp)
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(CarrionError),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocalShipping,
                                contentDescription = null,
                                tint = CarrionWhite,
                                modifier = Modifier.size(26.dp)
                            )
                        }

                        Spacer(Modifier.width(12.dp))

                        Column {
                            Text(
                                text = "Carrión Express",
                                color = CarrionWhite,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = "Panel principal",
                                color = CarrionWhite.copy(alpha = 0.65f),
                                fontSize = 12.sp
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        Card(
                            onClick = {
                                onNavigate("login")
                            },
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White.copy(alpha = 0.15f)
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(
                                    horizontal = 16.dp,
                                    vertical = 10.dp
                                ),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Icon(
                                    imageVector = Icons.Default.Logout,
                                    contentDescription = "Cerrar sesión",
                                    tint = CarrionWhite,
                                    modifier = Modifier.size(22.dp)
                                )

                                Spacer(modifier = Modifier.width(6.dp))

                                Text(
                                    text = "Salir",
                                    color = CarrionWhite,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                    }

                    Spacer(Modifier.height(28.dp))


                    Text(
                        text = "¡Bienvenido de nuevo!",
                        color = CarrionWhite.copy(alpha = 0.7f),
                        fontSize = 13.sp
                    )
                    Row {
                        Text(
                            text = "Hola, ",
                            color = CarrionWhite,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Administrador 👋",
                            color = CarrionYellow,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        text = "Seleccione una opción para continuar",
                        color = CarrionWhite.copy(alpha = 0.65f),
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            // ── Sección: Gestión de pedidos ────────────────────────────────
            SectionLabel("Gestión de pedidos")

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                DashboardCard(
                    title = "Registrar Pedido",
                    subtitle = "Nuevo envío",
                    icon = Icons.Default.AddBox,
                    iconBg = Color(0xFFEBF0FF),
                    iconTint = CarrionBlue,
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigate("register_order") }
                )
                DashboardCard(
                    title = "Ver Inventario",
                    subtitle = "Stock actual",
                    icon = Icons.Default.Inventory,
                    iconBg = Color(0xFFFFEEEE),
                    iconTint = CarrionError,
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigate("inventory") }
                )
            }

            Spacer(Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                DashboardCard(
                    title = "Nuevo Camión",
                    subtitle = "Registrar",
                    icon = Icons.Default.LocalShipping,
                    iconBg = Color(0xFFFFFBE6),
                    iconTint = Color(0xFFB88A00),
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigate("register_truck") }
                )
                DashboardCard(
                    title = "Asignar Carga",
                    subtitle = "A camión",
                    icon = Icons.Default.Assignment,
                    iconBg = Color(0xFFEDF5EE),
                    iconTint = Color(0xFF2E7D32),
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigate("assign") }
                )
            }

            // ── Sección: Acciones rápidas ──────────────────────────────────
            SectionLabel("Acciones rápidas")

            DashboardRowCard(
                title = "Panel del Transportista",
                subtitle = "Vista del conductor en ruta",
                icon = Icons.Default.Map,
                iconBg = Color(0xFFFFEEEE),
                iconTint = CarrionError,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp),
                onClick = { onNavigate("transportist") }
            )

            Spacer(Modifier.height(10.dp))

            DashboardRowCard(
                title = "Gestionar Empleados",
                subtitle = "Administración de personal",
                icon = Icons.Default.AdminPanelSettings,
                iconBg = Color(0xFFEBF0FF),
                iconTint = CarrionBlue,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp),
                onClick = { onNavigate("manage_employees") }
            )

            // ── CTA: Ver Historial ─────────────────────────────────────────
            Spacer(Modifier.height(14.dp))

            Card(
                onClick = { onNavigate("history") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp)
                    .height(85.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(CarrionError, Color(0xFFB50F10))
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                    ) {
                        Column {
                            Text(
                                text = "Ver Historial",
                                color = CarrionWhite,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                            Text(
                                text = "Todos los registros",
                                color = CarrionWhite.copy(alpha = 0.7f),
                                fontSize = 11.sp
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(Color.White.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.History,
                                contentDescription = null,
                                tint = CarrionYellow,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(28.dp))
        }
    }
}

// ── Componentes reutilizables ──────────────────────────────────────────────────

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text.uppercase(),
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        color = CarrionDark.copy(alpha = 0.4f),
        letterSpacing = 0.8.sp,
        modifier = Modifier.padding(start = 18.dp, top = 18.dp, bottom = 8.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DashboardCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    iconBg: Color,
    iconTint: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = modifier.height(135.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(RoundedCornerShape(11.dp))
                    .background(iconBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(24.dp)
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = CarrionDark,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = CarrionDark.copy(alpha = 0.45f),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DashboardRowCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    iconBg: Color,
    iconTint: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = modifier.height(85.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(13.dp))
                    .background(iconBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(26.dp)
                )
            }
            Spacer(Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = CarrionDark
                )
                Text(
                    text = subtitle,
                    fontSize = 11.sp,
                    color = CarrionDark.copy(alpha = 0.45f)
                )
            }
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFEBF0FF)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "›",
                    color = CarrionBlue,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
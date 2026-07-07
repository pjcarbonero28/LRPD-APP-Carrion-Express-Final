package com.example.LRPD.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.LRPD.ui.theme.screens.*
import com.example.LRPD.viewmodel.OrderViewModel

@Composable
fun NavGraph() {

    val navController = rememberNavController()
    val vModel: OrderViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        // LOGIN
        composable("login") {

            LoginScreen(
                vModel = vModel,

                onAdminLogin = {
                    navController.navigate("home")
                },

                onEmployeeLogin = {
                    navController.navigate("home_employee")
                },

                onCustomerClick = {
                    navController.navigate("customer_tracking")
                }
            )
        }

        // HOME
        composable("home") {
            HomeScreen(
                vModel = vModel,
                onNavigate = {
                    navController.navigate(it)
                }
            )
        }

        composable("home_employee") {
            HomeEmployeeScreen(
                vModel = vModel,
                onNavigate = {
                    navController.navigate(it)
                }
            )
        }

        // PEDIDOS
        composable("register_order") {
            RegisterOrderScreen(vModel) {
                navController.popBackStack()
            }
        }

        // CAMIONES
        composable("register_truck") {
            RegisterTruckScreen(vModel) {
                navController.popBackStack()
            }
        }

        // INVENTARIO
        composable("inventory") {
            InventoryScreen(vModel) {
                navController.popBackStack()
            }
        }

        // ASIGNACIÓN
        composable("assign") {
            AssignOrderScreen(vModel) {
                navController.popBackStack()
            }
        }

        // PANEL TRANSPORTISTA
        composable("transportist") {
            TransportistUpdateScreen(vModel) {
                navController.popBackStack()
            }
        }

        // HISTORIAL
        composable("history") {
            HistoryScreen(vModel) {
                navController.popBackStack()
            }
        }

        composable("tracking") {
            TrackingScreen(vModel) {
                navController.popBackStack()
            }
        }

        composable("update_status") {
            UpdateStatusScreen(vModel) {
                navController.popBackStack()
            }
        }

        // CLIENTE
        composable("customer_tracking") {
            CustomerTrackingScreen(vModel) {
                navController.popBackStack()
            }
        }

        // ADMINISTRAR EMPLEADOS
        composable("manage_employees") {
            ManageEmployeesScreen(
                vModel = vModel,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
        composable("mapa") {
            MapasUI(
                vModel = vModel,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
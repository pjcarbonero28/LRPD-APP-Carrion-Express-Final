package com.example.LRPD.navigation
sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object OrderList : Screen("list")
    object Detail : Screen("detail")
    object Tracking : Screen("track")
    object Update : Screen("update")
    object History : Screen("history")
}
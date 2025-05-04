package com.example.mpesa.data

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Payment : Screen("payment")

}

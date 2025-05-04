package com.example.mpesa.navigation

// AppNavigation.kt


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mpesa.data.Screen
import com.example.mpesa.screens.HomeScreen
import com.example.mpesa.screens.PaymentScreen


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToPayment = { navController.navigate(Screen.Payment.route) }
            )
        }



        composable(Screen.Payment.route) {
            PaymentScreen(
                onBackClick = { navController.popBackStack() }
            )
        }


    }
}
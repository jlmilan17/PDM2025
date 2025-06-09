package com.mercatto.myapplication.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mercatto.myapplication.viewmodel.AuthViewModel
import com.mercatto.myapplication.ui.home.HomeScreen
import com.mercatto.myapplication.ui.login.LoginScreen
import com.mercatto.myapplication.ui.register.RegisterScreen


object Destinations {
    const val LOGIN    = "login"
    const val REGISTER = "register"
    const val HOME     = "home"
}

@Composable
fun AppNavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.LOGIN
    ) {
        // Login
        composable(Destinations.LOGIN) {
            LoginScreen(
                viewModel            = authViewModel,
                onLoginSuccess       = {
                    navController.navigate(Destinations.HOME) {
                        popUpTo(Destinations.LOGIN) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Destinations.REGISTER)
                }
            )
        }

        // Registro
        composable(Destinations.REGISTER) {
            RegisterScreen(
                viewModel           = authViewModel,
                onRegisterSuccess   = {
                    navController.navigate(Destinations.HOME) {
                        popUpTo(Destinations.LOGIN) { inclusive = true }
                    }
                },
                onNavigateBack      = {
                    navController.popBackStack()
                }
            )
        }

        // Pantalla principal
        composable(Destinations.HOME) {
            HomeScreen()
        }
    }
}

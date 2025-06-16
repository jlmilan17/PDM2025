package com.mercatto.myapplication.navigation


import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mercatto.myapplication.ui.detailProduct.DetailScreen
import com.mercatto.myapplication.ui.favoritescreen.FavoritesScreen
import com.mercatto.myapplication.ui.home.HomeScreen
import com.mercatto.myapplication.ui.login.LoginScreen
import com.mercatto.myapplication.ui.messages.MessagesScreen
import com.mercatto.myapplication.ui.mypostscreen.MyPostsScreen
import com.mercatto.myapplication.ui.register.RegisterScreen
import com.mercatto.myapplication.ui.sell.SellScreen
import com.mercatto.myapplication.ui.profile.ProfileScreen
import com.mercatto.myapplication.viewmodel.AuthViewModel
import com.mercatto.myapplication.viewmodel.ProductViewModel

object Destinations {
    const val LOGIN            = "login"
    const val REGISTER         = "register"
    const val HOME             = "home"
    const val SELL             = "vender"
    const val PROFILE          = "perfil"
    const val MY_POSTS         = "mis_publicaciones"
    const val FAVORITES        = "favoritos"
    const val MESSAGES         = "mensajes"
    const val DETAIL           = "detail/{id}"
    const val DETAIL_BASE      = "detail"
}

@Composable
fun AppNavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
    val productViewModel: ProductViewModel = viewModel()

    NavHost(
        navController    = navController,
        startDestination = Destinations.LOGIN
    ) {
        composable(Destinations.LOGIN) {
            LoginScreen(
                viewModel = authViewModel,
                onLoginSuccess = {
                    navController.navigate(Destinations.HOME) {
                        popUpTo(Destinations.LOGIN) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Destinations.REGISTER)
                }
            )
        }

        composable(Destinations.REGISTER) {
            RegisterScreen(
                viewModel = authViewModel,
                onRegisterSuccess = {
                    navController.navigate(Destinations.HOME) {
                        popUpTo(Destinations.LOGIN) { inclusive = true }
                    }
                },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Destinations.HOME) {
            HomeScreen(
                navController = navController,
                viewModel     = productViewModel
            )
        }

        composable(Destinations.SELL) {
            SellScreen(
                navController = navController,
                viewModel     = productViewModel
            )
        }

        composable(Destinations.PROFILE) {
            ProfileScreen(
                navController    = navController,
                authViewModel    = authViewModel
            )
        }

        composable(
            Destinations.DETAIL,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            DetailScreen(
                entry     = backStackEntry,
                viewModel = productViewModel
            )
        }
        composable(Destinations.MY_POSTS) {
            MyPostsScreen(navController)
        }
        // “Favoritos”
        composable(Destinations.FAVORITES) {
            FavoritesScreen(navController)
        }
        // “Mensajes”
        composable(Destinations.MESSAGES) {
            MessagesScreen(navController)
        }
    }
}

package com.mercatto.myapplication.ui.components


import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Label
import androidx.compose.material.icons.filled.Person
import com.mercatto.myapplication.navigation.Destinations

@Composable
fun BottomNavBar(
    navController: NavController,
    currentRoute: String?
) {
    BottomAppBar {
        val items = listOf(
            Destinations.HOME   to (Icons.Filled.Home   to "Explorar"),
            Destinations.SELL   to (Icons.Filled.Label  to "Vender"),
            Destinations.PROFILE to (Icons.Filled.Person to "Perfil")
        )
        items.forEach { (route, iconAndLabel) ->
            val (icon, label) = iconAndLabel
            NavigationBarItem(
                icon = { Icon(icon, contentDescription = label) },
                label = { Text(label) },
                selected = currentRoute == route,
                onClick = {
                    if (currentRoute != route) {
                        navController.navigate(route) {
                            popUpTo(Destinations.HOME)
                            launchSingleTop = true
                        }
                    }
                }
            )
        }
    }
}
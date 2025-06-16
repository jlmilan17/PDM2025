package com.mercatto.myapplication.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mercatto.myapplication.viewmodel.AuthViewModel
import com.mercatto.myapplication.navigation.Destinations
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
@Composable
fun ProfileScreen(
    navController    : NavController,
    authViewModel    : AuthViewModel
) {
    var showDialog by remember { mutableStateOf(false) }
    val user = authViewModel.getCurrentUser()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(32.dp))

        // Avatar
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier.size(120.dp)
        ) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Icon(
                    imageVector   = Icons.Filled.Person,
                    contentDescription = "Avatar",
                    modifier      = Modifier.size(64.dp)
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = user?.displayName ?: "Nombre",
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = user?.email ?: "email@example.com",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(Modifier.height(32.dp))

        val buttonMod = Modifier
            .fillMaxWidth()
            .height(48.dp)

        Button(
            onClick = { navController.navigate(Destinations.MY_POSTS) },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            modifier = buttonMod
        ) { Text("Mis publicaciones") }
        Spacer(Modifier.height(8.dp))

        Button(
            onClick = { navController.navigate(Destinations.FAVORITES)  },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            modifier = buttonMod
        ) { Text("Favoritos") }
        Spacer(Modifier.height(8.dp))

        Button(
            onClick = { navController.navigate(Destinations.MESSAGES) },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            modifier = buttonMod
        ) { Text("Mensajes") }
        Spacer(Modifier.height(32.dp))

        Button(
            onClick = { showDialog = true },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor   = MaterialTheme.colorScheme.error
            ),
            modifier = buttonMod
        ) { Text("Cerrar sesión") }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title   = { Text("Confirmar") },
            text    = { Text("¿Desea continuar con el proceso?") },
            confirmButton = {
                TextButton(onClick = {
                    showDialog = false
                    authViewModel.signOut()
                    navController.navigate(Destinations.LOGIN) {
                        popUpTo(0) { inclusive = true }
                    }
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) { Text("Cancelar") }
            }
        )
    }
}
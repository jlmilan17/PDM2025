package com.mercatto.myapplication.ui.profile

import android.util.Log
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import com.mercatto.myapplication.data.model.User

@Composable
fun ProfileScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val mainColor = Color(14, 70, 61)
    var showDialog by remember { mutableStateOf(false) }
    val firebaseUser = authViewModel.getCurrentUser()

    val userDataState = rememberFirestoreUser(firebaseUser?.uid.orEmpty())
    val userData = userDataState.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(32.dp))

        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier.size(120.dp)
        ) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                if (!userData?.storeImageUrl.isNullOrEmpty()) {
                    AsyncImage(
                        model = userData!!.storeImageUrl,
                        contentDescription = "Avatar",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Avatar",
                        modifier = Modifier.size(64.dp)
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = userData?.fullName ?: firebaseUser?.displayName ?: "Nombre",
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = firebaseUser?.email ?: "email@example.com",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(Modifier.height(32.dp))

        val buttonMod = Modifier
            .fillMaxWidth()
            .height(48.dp)

        Button(
            onClick = { navController.navigate(Destinations.MY_POSTS) },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            modifier = buttonMod,
            
        ) { Text("Mis publicaciones", color = mainColor) }
        Spacer(Modifier.height(8.dp))

        Button(
            onClick = { navController.navigate(Destinations.FAVORITES) },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            modifier = buttonMod
        ) { Text("Favoritos", color = mainColor) }
        Spacer(Modifier.height(8.dp))

        Button(
            onClick = { navController.navigate(Destinations.MESSAGES) },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            modifier = buttonMod
        ) { Text("Mensajes", color = mainColor) }
        Spacer(Modifier.height(32.dp))

        Button(
            onClick = { showDialog = true },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Gray,
                contentColor = Color.Red.copy(alpha = 0.8f)
            ),
            modifier = buttonMod
        ) { Text("Cerrar sesión") }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Confirmar") },
            text = { Text("¿Desea continuar con el proceso?") },
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

@Composable
fun rememberFirestoreUser(uid: String): State<User?> {
    val userState = remember { mutableStateOf<User?>(null) }

    LaunchedEffect(uid) {
        if (uid.isNotBlank()) {
            try {
                val doc = FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(uid)
                    .get()
                    .await()

                userState.value = doc.toObject(User::class.java)
            } catch (e: Exception) {
                Log.e("ProfileScreen", "Error al cargar usuario Firestore", e)
            }
        }
    }

    return userState
}

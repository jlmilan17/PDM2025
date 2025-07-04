package com.mercatto.myapplication.ui.messages


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessagesScreen(navController: NavController) {

    val currentUser = FirebaseAuth.getInstance().currentUser?.uid
    val context = LocalContext.current
    val mainColor = Color(14, 70, 61)
    var message by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mensajes") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { padding ->
        Column (
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(padding).padding(16.dp)
        ) {

            Column (
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .padding(padding).padding(16.dp)
            ) {

                MessageBubble("Hola", "sender")
                MessageBubble("Buenas", "receiver")
                MessageBubble("Detalles y precio", "sender")
            }

        }

    }
}

@Composable
fun MessageBubble(message: String, userType: String) {
    val alignment = if (userType == "receiver") Alignment.CenterEnd else Alignment.CenterStart
    val color = if (userType == "receiver") Color(14, 70, 61) else Color(0xFF2196F3)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        contentAlignment = alignment
    ) {
        Surface(
            shape = RoundedCornerShape(8.dp),
            color = color,
            shadowElevation = 2.dp
        ) {
            Text(
                text = message,
                modifier = Modifier.padding(12.dp),
                color = Color.White
            )
        }
    }
}
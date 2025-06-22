package com.mercatto.myapplication.ui.login

import androidx.compose.foundation.Image
//import androidx.compose.foundation.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.mercatto.myapplication.ui.components.PasswordTextField
import com.mercatto.myapplication.viewmodel.AuthViewModel
import com.mercatto.myapplication.viewmodel.LoginUiState
import com.mercatto.myapplication.R
//import com.mercatto.myapplication.R


@Composable
fun LoginScreen(
    viewModel: AuthViewModel,
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    val loginState by viewModel.loginState.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    val mainColor = Color(14, 70, 61)

    LaunchedEffect(loginState) {
        if (loginState is LoginUiState.Success) {
            onLoginSuccess()
            viewModel.resetLoginState()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            "Iniciar sesión",
            style = MaterialTheme.typography.headlineSmall,
            color = Color(14, 70, 61)
        )

        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = mainColor.copy(alpha = 0.1f),
                unfocusedContainerColor = Color.Transparent,
                focusedLabelColor = mainColor,
                unfocusedLabelColor = mainColor,
                focusedBorderColor = mainColor,
                unfocusedBorderColor = Color.Gray,
                focusedTextColor = mainColor
            )
        )

        Spacer(Modifier.height(16.dp))

        PasswordTextField(
            password = password,
            onPasswordChange = { password = it },
            showPassword = showPassword,
            onToggleShowPassword = { showPassword = !showPassword },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = showPassword, onCheckedChange = { showPassword = it })
            Text("Mostrar contraseña")
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = { viewModel.signIn(email.trim(), password) },
            modifier = Modifier.fillMaxWidth(),
            enabled = loginState != LoginUiState.Loading,
            colors = ButtonDefaults.buttonColors(
                containerColor = mainColor,
                contentColor = Color.White
            )
        ) {
            if (loginState == LoginUiState.Loading) {
                CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
            } else {
                Text("Iniciar sesión")
            }
        }

        Spacer(Modifier.height(16.dp))

        TextButton(onClick = onNavigateToRegister) {
            Text("¿No tienes una cuenta? Regístrate", color = mainColor)
        }

        if (loginState is LoginUiState.Error) {
            Spacer(Modifier.height(12.dp))
            Text(
                text = (loginState as LoginUiState.Error).message,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

package com.mercatto.myapplication.ui.components


import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.*

@Composable
fun PasswordTextField(
    password: String,
    onPasswordChange: (String) -> Unit,
    showPassword: Boolean,
    onToggleShowPassword: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text("Contraseña") },
        singleLine = true,
        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = onToggleShowPassword) {
                Icon(
                    imageVector = if (showPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                    contentDescription = if (showPassword) "Ocultar contraseña" else "Mostrar contraseña"
                )
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        modifier = modifier
    )
}

package com.mercatto.myapplication.ui.components


import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.*

@Composable
fun PasswordTextField(
    password: String,
    onPasswordChange: (String) -> Unit,
    showPassword: Boolean,
    onToggleShowPassword: () -> Unit,
    modifier: Modifier = Modifier
) {

    val mainColor = Color(14, 70, 61)
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = {
            Text(
                text = "Contraseña",
                color = Color.Gray
            )
        },
        placeholder = {
            Text(
                text = "Contraseña",
                color = Color.Gray
            )
        },
        singleLine = true,
        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val icon = if (showPassword)
                Icons.Default.Visibility
            else
                Icons.Default.VisibilityOff

            IconButton(onClick = onToggleShowPassword) {
                Icon(
                    imageVector = icon,
                    contentDescription = if (showPassword) "Ocultar contraseña" else "Mostrar contraseña"
                )
            }
        },
        modifier = modifier,
        colors       = OutlinedTextFieldDefaults.colors(
            focusedContainerColor   = mainColor.copy(alpha = 0.1f),
            unfocusedContainerColor = mainColor.copy(alpha = 0.1f),
            focusedLabelColor       = mainColor,
            unfocusedLabelColor     = Color.Gray,
            focusedBorderColor      = mainColor,
            unfocusedBorderColor    = Color.Gray,
            focusedTextColor        = mainColor,
            unfocusedTextColor      = mainColor
        )
    )
}

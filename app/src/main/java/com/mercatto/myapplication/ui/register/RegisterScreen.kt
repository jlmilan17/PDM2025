package com.mercatto.myapplication.ui.register


import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.mercatto.myapplication.ui.components.PasswordTextField
import com.mercatto.myapplication.ui.components.StoreImageSelector
import com.mercatto.myapplication.viewmodel.AuthViewModel
import com.mercatto.myapplication.viewmodel.RegisterUiState

@Composable
fun RegisterScreen(
    viewModel: AuthViewModel,
    onRegisterSuccess: () -> Unit,
    onNavigateBack: () -> Unit
) {
    val registerState by viewModel.registerState.collectAsState()

    // Local state de los campos
    var fullName      by remember { mutableStateOf("") }
    var email         by remember { mutableStateOf("") }
    var phone         by remember { mutableStateOf("") }
    var password      by remember { mutableStateOf("") }
    var confirmPass   by remember { mutableStateOf("") }
    var isSeller      by remember { mutableStateOf(false) }
    var storeName     by remember { mutableStateOf("") }
    var storeContact  by remember { mutableStateOf("") }
    var storeLocation by remember { mutableStateOf("") }
    var storeImageUri by remember { mutableStateOf<Uri?>(null) }

    // Efecto para navegar al éxito de registro
    LaunchedEffect(registerState) {
        if (registerState is RegisterUiState.Success) {
            onRegisterSuccess()
            viewModel.resetRegisterState()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Registro", style = MaterialTheme.typography.headlineSmall)

        // Nombre completo
        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = { Text("Nombre completo") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        // Correo
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        // Teléfono
        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Teléfono") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth()
        )

        // Contraseña
        PasswordTextField(
            password = password,
            onPasswordChange = { password = it },
            showPassword = false,
            onToggleShowPassword = { /* opcional: manejar mostrar/ocultar */ },
            modifier = Modifier.fillMaxWidth()
        )

        // Confirmar contraseña
        PasswordTextField(
            password = confirmPass,
            onPasswordChange = { confirmPass = it },
            showPassword = false,
            onToggleShowPassword = { /* opcional: manejar mostrar/ocultar */ },
            modifier = Modifier.fillMaxWidth()
        )

        // Switch de vendedor
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Quiero vender productos", modifier = Modifier.weight(1f))
            Switch(
                checked = isSeller,
                onCheckedChange = { isSeller = it }
            )
        }

        // Campos adicionales para vendedores
        if (isSeller) {
            OutlinedTextField(
                value = storeName,
                onValueChange = { storeName = it },
                label = { Text("Nombre de la tienda") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = storeContact,
                onValueChange = { storeContact = it },
                label = { Text("Teléfono de contacto") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = storeLocation,
                onValueChange = { storeLocation = it },
                label = { Text("Ubicación") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Selector de imagen de tienda
            StoreImageSelector(
                storeImageUri = storeImageUri,
                onImageSelected = { storeImageUri = it }
            )
        }

        Spacer(Modifier.weight(1f))

        // Botón de registro
        Button(
            onClick = {
                viewModel.register(
                    fullName      = fullName.trim(),
                    email         = email.trim(),
                    phone         = phone.trim(),
                    password      = password,
                    confirmPassword = confirmPass,
                    isSeller      = isSeller,
                    storeName     = storeName.trim().takeIf { isSeller },
                    storeContact  = storeContact.trim().takeIf { isSeller },
                    storeLocation = storeLocation.trim().takeIf { isSeller },
                    storeImageUri = storeImageUri
                )
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = registerState != RegisterUiState.Loading
        ) {
            if (registerState is RegisterUiState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp
                )
            } else {
                Text("Registrar")
            }
        }

        // Mensaje de error
        if (registerState is RegisterUiState.Error) {
            Spacer(Modifier.height(8.dp))
            Text(
                text = (registerState as RegisterUiState.Error).message,
                color = MaterialTheme.colorScheme.error
            )
        }

        // Botón volver
        TextButton(
            onClick = onNavigateBack,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Volver")
        }
    }
}

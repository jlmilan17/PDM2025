package com.mercatto.myapplication.ui.register


import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
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
import com.mercatto.myapplication.R
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
    val mainColor = Color(14, 70, 61)

    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPass by remember { mutableStateOf("") }
    var isSeller by remember { mutableStateOf(false) }
    var storeName by remember { mutableStateOf("") }
    var storeContact by remember { mutableStateOf("") }
    var storeLocation by remember { mutableStateOf("") }
    var storeImageUri by remember { mutableStateOf<Uri?>(null) }

    LaunchedEffect(registerState) {
        if (registerState is RegisterUiState.Success) {
            onRegisterSuccess()
            viewModel.resetRegisterState()
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(300.dp)
                )
            }
        }

        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Bienvenido a Mercatto!",
                    style = MaterialTheme.typography.headlineMedium
                )
            }
        }

        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Registro de usuario",
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        }


        item { Spacer(modifier = Modifier.height(8.dp)) }

        item {
            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                label = { Text("Nombre completo") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Teléfono") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth()
            )
        }

            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text("Ingrese una contraseña segura", style = MaterialTheme.typography.bodyMedium)
                    PasswordTextField(
                        password = password,
                        onPasswordChange = { password = it },
                        showPassword = true,
                        onToggleShowPassword = { },
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }

            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text("Confirme su contraseña", style = MaterialTheme.typography.bodyMedium)
                    PasswordTextField(
                        password = confirmPass,
                        onPasswordChange = { confirmPass = it },
                        showPassword = true,
                        onToggleShowPassword = { },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

        item {
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
        }

        if (isSeller) {
            item {
                OutlinedTextField(
                    value = storeName,
                    onValueChange = { storeName = it },
                    label = { Text("Nombre de la tienda") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                OutlinedTextField(
                    value = storeContact,
                    onValueChange = { storeContact = it },
                    label = { Text("Teléfono de contacto") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                OutlinedTextField(
                    value = storeLocation,
                    onValueChange = { storeLocation = it },
                    label = { Text("Ubicación") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                StoreImageSelector(
                    storeImageUri = storeImageUri,
                    onImageSelected = { storeImageUri = it }
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Button(
                onClick = {
                    viewModel.register(
                        fullName = fullName.trim(),
                        email = email.trim(),
                        phone = phone.trim(),
                        password = password,
                        confirmPassword = confirmPass,
                        isSeller = isSeller,
                        storeName = storeName.trim().takeIf { isSeller },
                        storeContact = storeContact.trim().takeIf { isSeller },
                        storeLocation = storeLocation.trim().takeIf { isSeller },
                        storeImageUri = storeImageUri
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = registerState != RegisterUiState.Loading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = mainColor,
                    contentColor = Color.White
                )
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
        }

        if (registerState is RegisterUiState.Error) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = (registerState as RegisterUiState.Error).message,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextButton(
                    onClick = onNavigateBack
                ) {
                    Text("Volver")
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }

    }
}

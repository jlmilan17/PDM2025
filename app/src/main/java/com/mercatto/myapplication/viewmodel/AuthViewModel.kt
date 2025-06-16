package com.mercatto.myapplication.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.mercatto.myapplication.data.model.User
import com.mercatto.myapplication.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


sealed class LoginUiState {
    object Idle    : LoginUiState()
    object Loading : LoginUiState()
    object Success : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}

sealed class RegisterUiState {
    object Idle    : RegisterUiState()
    object Loading : RegisterUiState()
    object Success : RegisterUiState()
    data class Error(val message: String) : RegisterUiState()
}

class AuthViewModel(
    private val repo: AuthRepository = AuthRepository()
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val loginState: StateFlow<LoginUiState> = _loginState

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginUiState.Loading
            val result = repo.signIn(email, password)
            if (result.isSuccess) {
                _loginState.value = LoginUiState.Success
            } else {
                _loginState.value = LoginUiState.Error(
                    result.exceptionOrNull()?.localizedMessage
                        ?: "Error desconocido"
                )
            }
        }
    }

    fun resetLoginState() {
        _loginState.value = LoginUiState.Idle
    }

    private val _registerState = MutableStateFlow<RegisterUiState>(RegisterUiState.Idle)
    val registerState: StateFlow<RegisterUiState> = _registerState

    fun register(
        fullName: String,
        email: String,
        phone: String,
        password: String,
        confirmPassword: String,
        isSeller: Boolean,
        storeName: String?,
        storeContact: String?,
        storeLocation: String?,
        storeImageUri: Uri?
    ) {
        if (password != confirmPassword) {
            _registerState.value =
                RegisterUiState.Error("Las contraseñas no coinciden")
            return
        }
        if (fullName.isBlank() || email.isBlank() ||
            phone.isBlank() || password.isBlank()
        ) {
            _registerState.value =
                RegisterUiState.Error("Por favor completa todos los campos obligatorios")
            return
        }

        viewModelScope.launch {
            _registerState.value = RegisterUiState.Loading

            val newUser = User(
                fullName = fullName,
                email = email,
                phone = phone,
                isSeller = isSeller,
                storeName = storeName,
                storeContact = storeContact,
                storeLocation = storeLocation
            )

            val result = repo.signUp(newUser, password, storeImageUri)
            if (result.isSuccess) {
                _registerState.value = RegisterUiState.Success
            } else {
                _registerState.value = RegisterUiState.Error(
                    result.exceptionOrNull()?.localizedMessage
                        ?: "Error al registrar usuario"
                )
            }
        }
    }

    fun resetRegisterState() {
        _registerState.value = RegisterUiState.Idle
    }

    fun getCurrentUser(): FirebaseUser? =
        repo.getCurrentUser()

    fun signOut() {
        repo.signOut()
    }
}

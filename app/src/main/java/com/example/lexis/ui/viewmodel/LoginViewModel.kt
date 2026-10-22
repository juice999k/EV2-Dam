package com.example.lexis.ui.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.example.lexis.ui.login.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onUsernameChanged(nuevoTexto: String) {
        _uiState.update { it.copy(username = nuevoTexto) }
    }

    fun onPasswordChanged(nuevoTexto: String) {
        _uiState.update { it.copy(password = nuevoTexto) }
    }

    fun onRecordarChanged(activo: Boolean) {
        _uiState.update { it.copy(recordarUsuario = activo) }
    }

    fun onIngresarClick() {
        _uiState.update { it.copy(mostrarModalLogin = true) }
    }

    fun onDismissModalLogin() {
        _uiState.update { it.copy(mostrarModalLogin = false) }
    }

    fun onRegistrarClick() {
        val email = _uiState.value.username.trim()
        val pass = _uiState.value.password

        val mensaje = when {
            email.isEmpty() || pass.isEmpty() -> {
                "Por favor, ingresa un correo y una contraseña para registrarte."
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                "El correo ingresado no tiene un formato válido."
            }
            pass.length < 6 -> {
                "La contraseña debe tener al menos 6 caracteres."
            }
            else -> {
                "Usuario registrado exitosamente: $email"
            }
        }

        _uiState.update {
            it.copy(
                mensajeRegistro = mensaje,
                mostrarModalRegistro = true
            )
        }
    }

    fun onDismissModalRegistro() {
        _uiState.update { it.copy(mostrarModalRegistro = false) }
    }

    fun onOlvidastePasswordClick() {
        val email = _uiState.value.username.trim()
        val mensaje = when {
            email.isEmpty() -> {
                "Por favor, escribe tu correo arriba para poder restablecer tu contraseña."
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                "El correo ingresado no tiene un formato válido."
            }
            else -> {
                "Se ha enviado un enlace de recuperación al correo: $email"
            }
        }

        _uiState.update {
            it.copy(
                mensajeOlvidar = mensaje,
                mostrarModalOlvidar = true
            )
        }
    }

    fun onDismissModalOlvidar() {
        _uiState.update { it.copy(mostrarModalOlvidar = false) }
    }
}
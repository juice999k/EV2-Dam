package com.example.lexis.ui.login

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val recordarUsuario: Boolean = false,
    val mostrarModalLogin: Boolean = false,
    val mostrarModalOlvidar: Boolean = false,
    val mensajeOlvidar: String = "",
    val mostrarModalRegistro: Boolean = false,
    val mensajeRegistro: String = ""
)
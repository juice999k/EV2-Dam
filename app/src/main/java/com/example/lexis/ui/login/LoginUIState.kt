package com.example.lexis.ui.login

data class LoginUiState(
    val correo: String = "",
    val clave: String = "",
    val recordarUsuario: Boolean = false,
    val errorCorreo: String? = null,
    val errorClave: String? = null,
    val cargando: Boolean = false,
    val mostrarModalError: Boolean = false,
    val mensajeError: String = "",
    val mostrarModalOlvidar: Boolean = false,
    val mensajeOlvidar: String = "",
    val idUsuarioAutenticado: Int? = null
)

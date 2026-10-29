package com.example.lexis.ui.perfil

data class PerfilUiState(
    val nombre: String = "",
    val correo: String = "",
    val tipoUsuario: String = "",
    val iniciales: String = "",
    val rutaFotoPerfil: String? = null,
    val mensaje: String? = null,
    val mostrarModalCerrarSesion: Boolean = false,
    val sesionCerrada: Boolean = false
)

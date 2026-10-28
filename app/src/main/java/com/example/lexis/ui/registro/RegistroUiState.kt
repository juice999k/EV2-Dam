package com.example.lexis.ui.registro

import com.example.lexis.model.TipoUsuario

data class RegistroUiState(
    val nombre: String = "",
    val correo: String = "",
    val clave: String = "",
    val confirmacionClave: String = "",
    val tipoUsuario: TipoUsuario = TipoUsuario.ESTUDIANTE,
    val errorNombre: String? = null,
    val errorCorreo: String? = null,
    val errorClave: String? = null,
    val errorConfirmacion: String? = null,
    val cargando: Boolean = false,
    val mostrarModalExito: Boolean = false,
    val registroCompletado: Boolean = false
)

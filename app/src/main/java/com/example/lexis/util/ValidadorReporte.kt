package com.example.lexis.util

object ValidadorReporte {
    fun validarDescripcion(descripcion: String): String? = when {
        descripcion.trim().length < 10 -> "Escribe al menos 10 caracteres."
        descripcion.trim().length > 1000 -> "La descripción no puede superar los 1000 caracteres."
        else -> null
    }
}

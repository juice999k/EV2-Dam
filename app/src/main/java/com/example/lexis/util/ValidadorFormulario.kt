package com.example.lexis.util

import android.util.Patterns

object ValidadorFormulario {

    private const val LARGO_MINIMO_NOMBRE = 3
    private const val LARGO_MINIMO_CLAVE = 6

    fun validarNombre(nombre: String): String? = when {
        nombre.isBlank() -> "Ingresa tu nombre"
        nombre.trim().length < LARGO_MINIMO_NOMBRE -> "El nombre debe tener al menos $LARGO_MINIMO_NOMBRE letras"
        else -> null
    }

    fun validarCorreo(correo: String): String? = when {
        correo.isBlank() -> "Ingresa tu correo"
        !Patterns.EMAIL_ADDRESS.matcher(correo.trim()).matches() -> "El correo no tiene un formato válido"
        else -> null
    }

    fun validarClave(clave: String): String? = when {
        clave.isEmpty() -> "Ingresa tu contraseña"
        clave.length < LARGO_MINIMO_CLAVE -> "La contraseña debe tener al menos $LARGO_MINIMO_CLAVE caracteres"
        else -> null
    }

    fun validarConfirmacion(clave: String, confirmacion: String): String? = when {
        confirmacion.isEmpty() -> "Repite tu contraseña"
        clave != confirmacion -> "Las contraseñas no coinciden"
        else -> null
    }
}

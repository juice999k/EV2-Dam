package com.example.lexis.util
 
fun obtenerIniciales(nombreCompleto: String): String =
    nombreCompleto.trim()
        .split(" ")
        .filter { it.isNotBlank() }
        .take(2)
        .joinToString("") { it.first().uppercase() }

package com.example.lexis.util

import java.security.MessageDigest

object ClaveSegura {
    fun hashear(clave: String, sal: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest((sal + clave).toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }
}

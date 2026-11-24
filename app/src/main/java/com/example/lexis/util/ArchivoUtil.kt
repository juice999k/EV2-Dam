package com.example.lexis.util

import android.content.ContentResolver
import android.net.Uri
import java.io.IOException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun guardarTextoEnArchivo(
    resolver: ContentResolver,
    uri: Uri,
    texto: String
) = withContext(Dispatchers.IO) {
    val salida = resolver.openOutputStream(uri, "wt")
        ?: throw IOException("No se pudo abrir el archivo para escribir.")

    salida.bufferedWriter(Charsets.UTF_8).use { escritor ->
        escritor.write(texto)
    }
}
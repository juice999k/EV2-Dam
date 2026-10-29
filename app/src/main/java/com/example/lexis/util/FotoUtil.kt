package com.example.lexis.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface

private const val REDUCCION_TAMANO = 4

fun cargarFotoReducida(rutaArchivo: String): Bitmap? {
    val opciones = BitmapFactory.Options().apply { inSampleSize = REDUCCION_TAMANO }
    val foto = BitmapFactory.decodeFile(rutaArchivo, opciones) ?: return null

    val grados = when (
        ExifInterface(rutaArchivo).getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
    ) {
        ExifInterface.ORIENTATION_ROTATE_90 -> 90f
        ExifInterface.ORIENTATION_ROTATE_180 -> 180f
        ExifInterface.ORIENTATION_ROTATE_270 -> 270f
        else -> 0f
    }
    if (grados == 0f) return foto

    val matriz = Matrix().apply { postRotate(grados) }
    return Bitmap.createBitmap(foto, 0, 0, foto.width, foto.height, matriz, true)
}

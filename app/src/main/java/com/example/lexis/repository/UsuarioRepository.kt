package com.example.lexis.repository

import android.content.Context
import android.graphics.Bitmap
import com.example.lexis.model.TipoUsuario
import com.example.lexis.model.UsuarioDao
import com.example.lexis.model.UsuarioEntity
import com.example.lexis.util.ClaveSegura
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class UsuarioRepository(
    private val usuarioDao: UsuarioDao,
    private val contexto: Context
) {

    suspend fun registrar(
        nombre: String,
        correo: String,
        clave: String,
        tipoUsuario: TipoUsuario
    ): Boolean {
        val correoNormalizado = correo.trim().lowercase()
        if (usuarioDao.buscarPorCorreo(correoNormalizado) != null) return false

        usuarioDao.insertar(
            UsuarioEntity(
                nombre = nombre.trim(),
                correo = correoNormalizado,
                claveHash = ClaveSegura.hashear(clave, correoNormalizado),
                tipoUsuario = tipoUsuario.name
            )
        )
        return true
    }

    suspend fun iniciarSesion(correo: String, clave: String, recordar: Boolean): UsuarioEntity? {
        val correoNormalizado = correo.trim().lowercase()
        val usuario = usuarioDao.buscarPorCorreo(correoNormalizado) ?: return null
        if (usuario.claveHash != ClaveSegura.hashear(clave, correoNormalizado)) return null

        usuarioDao.olvidarTodasLasSesiones()
        if (recordar) usuarioDao.recordarSesion(usuario.id)
        return usuario
    }

    suspend fun obtenerUsuarioRecordado(): UsuarioEntity? = usuarioDao.buscarUsuarioRecordado()

    suspend fun obtenerUsuario(id: Int): UsuarioEntity? = usuarioDao.buscarPorId(id)

    suspend fun cerrarSesion() = usuarioDao.olvidarTodasLasSesiones()

    suspend fun guardarFotoPerfil(idUsuario: Int, foto: Bitmap) = withContext(Dispatchers.IO) {
        val fotoAnterior = usuarioDao.buscarPorId(idUsuario)?.rutaFotoPerfil

        val archivo = File(contexto.filesDir, "perfil_${idUsuario}_${System.currentTimeMillis()}.jpg")
        archivo.outputStream().use { salida ->
            foto.compress(Bitmap.CompressFormat.JPEG, CALIDAD_JPEG, salida)
        }

        usuarioDao.actualizarFotoPerfil(idUsuario, archivo.absolutePath)
        fotoAnterior?.let { File(it).delete() }
    }

    private companion object {
        const val CALIDAD_JPEG = 90
    }
}

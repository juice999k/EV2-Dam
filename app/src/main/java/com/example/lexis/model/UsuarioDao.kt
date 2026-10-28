package com.example.lexis.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UsuarioDao {

    @Insert
    suspend fun insertar(usuario: UsuarioEntity): Long

    @Query("SELECT * FROM usuarios WHERE correo = :correo LIMIT 1")
    suspend fun buscarPorCorreo(correo: String): UsuarioEntity?

    @Query("SELECT * FROM usuarios WHERE id = :id LIMIT 1")
    suspend fun buscarPorId(id: Int): UsuarioEntity?

    @Query("SELECT * FROM usuarios WHERE recordarSesion = 1 LIMIT 1")
    suspend fun buscarUsuarioRecordado(): UsuarioEntity?

    @Query("UPDATE usuarios SET recordarSesion = 0")
    suspend fun olvidarTodasLasSesiones()

    @Query("UPDATE usuarios SET recordarSesion = 1 WHERE id = :id")
    suspend fun recordarSesion(id: Int)

    @Query("UPDATE usuarios SET rutaFotoPerfil = :ruta WHERE id = :id")
    suspend fun actualizarFotoPerfil(id: Int, ruta: String)
}

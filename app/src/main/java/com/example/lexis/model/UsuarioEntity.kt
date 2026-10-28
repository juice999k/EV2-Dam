package com.example.lexis.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "usuarios",
    indices = [Index(value = ["correo"], unique = true)]
)
data class UsuarioEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val correo: String,
    val claveHash: String,
    val tipoUsuario: String = TipoUsuario.ESTUDIANTE.name,
    val rutaFotoPerfil: String? = null,
    val recordarSesion: Boolean = false
)

package com.example.lexis.ui.navigation

object Rutas {
    const val ARGUMENTO_ID_USUARIO = "idUsuario"

    const val LOGIN = "login"
    const val REGISTRO = "registro"
    const val LECTOR = "lector"
    const val INICIO = "inicio/{$ARGUMENTO_ID_USUARIO}"
    const val PERFIL = "perfil/{$ARGUMENTO_ID_USUARIO}"

    fun inicio(idUsuario: Int) = "inicio/$idUsuario"
    fun perfil(idUsuario: Int) = "perfil/$idUsuario"
}

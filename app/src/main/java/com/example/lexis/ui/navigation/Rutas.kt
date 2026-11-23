package com.example.lexis.ui.navigation

object Rutas {
    fun lector(normaId: Int) = "lector/$normaId"
    const val ARGUMENTO_ID_USUARIO = "idUsuario"

    const val LOGIN = "login"
    const val REGISTRO = "registro"
    const val INICIO = "inicio/{$ARGUMENTO_ID_USUARIO}"
    const val PERFIL = "perfil/{$ARGUMENTO_ID_USUARIO}"

    fun inicio(idUsuario: Int) = "inicio/$idUsuario"
    fun perfil(idUsuario: Int) = "perfil/$idUsuario"

    const val ARGUMENTO_ID_CATEGORIA = "categoriaId"
    const val NORMAS = "normas/{$ARGUMENTO_ID_CATEGORIA}"

    fun normas(categoriaId: Int) = "normas/$categoriaId"
    const val ARGUMENTO_ID_NORMA = "normaId"
    const val DETALLE_NORMA = "detalleNorma/{$ARGUMENTO_ID_NORMA}"
    const val LECTOR = "lector/{$ARGUMENTO_ID_NORMA}"

    fun detalleNorma(normaId: Int) = "detalleNorma/$normaId"

    const val GLOSARIO = "glosario"
}

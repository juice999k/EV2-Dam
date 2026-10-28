package com.example.lexis

import android.app.Application
import com.example.lexis.model.LexisDatabase
import com.example.lexis.repository.UsuarioRepository


class LexisApplication : Application() {

    val repositorioUsuarios: UsuarioRepository by lazy {
        UsuarioRepository(
            usuarioDao = LexisDatabase.obtener(this).usuarioDao(),
            contexto = this
        )
    }
}

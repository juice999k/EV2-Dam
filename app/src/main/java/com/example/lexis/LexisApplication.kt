package com.example.lexis

import android.app.Application
import com.example.lexis.model.LexisDatabase
import com.example.lexis.repository.UsuarioRepository
import com.example.lexis.remote.RetrofitInstance
import com.example.lexis.repository.BibliotecaRepository


class LexisApplication : Application() {

    val repositorioUsuarios: UsuarioRepository by lazy {
        UsuarioRepository(
            usuarioDao = LexisDatabase.obtener(this).usuarioDao(),
            contexto = this
        )

    }
    val repositorioBiblioteca: BibliotecaRepository by lazy {
        BibliotecaRepository(RetrofitInstance.api)
    }
}

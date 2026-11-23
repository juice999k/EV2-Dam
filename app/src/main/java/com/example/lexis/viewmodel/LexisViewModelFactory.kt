package com.example.lexis.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.lexis.LexisApplication

object LexisViewModelFactory {

    private fun CreationExtras.repositorio() =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                as LexisApplication).repositorioUsuarios

    private fun CreationExtras.bibliotecaRepository() =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                as LexisApplication).repositorioBiblioteca

    val login = viewModelFactory {
        initializer {
            LoginViewModel(repositorio())
        }
    }

    val registro = viewModelFactory {
        initializer {
            RegistroViewModel(repositorio())
        }
    }

    fun perfil(idUsuario: Int) = viewModelFactory {
        initializer {
            PerfilViewModel(repositorio(), idUsuario)
        }
    }

    fun inicio(idUsuario: Int) = viewModelFactory {
        initializer {
            InicioViewModel(
                repositorio = repositorio(),
                idUsuario = idUsuario,
                bibliotecaRepository = bibliotecaRepository()
            )
        }
    }

    fun normas(categoriaId: Int) = viewModelFactory {
        initializer {
            NormasViewModel(
                repositorio = bibliotecaRepository(),
                categoriaId = categoriaId
            )
        }
    }
}
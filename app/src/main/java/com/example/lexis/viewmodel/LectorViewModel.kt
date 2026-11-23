package com.example.lexis.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lexis.repository.BibliotecaRepository
import com.example.lexis.ui.lector.LectorUiState
import java.io.IOException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LectorViewModel(
    private val repositorio: BibliotecaRepository,
    private val normaId: Int
) : ViewModel() {

    private val _uiState = MutableStateFlow(LectorUiState())
    val uiState: StateFlow<LectorUiState> = _uiState.asStateFlow()

    init {
        cargarArticulos()
    }

    fun cargarArticulos() {
        if (_uiState.value.cargando) return

        _uiState.update {
            it.copy(cargando = true, error = null)
        }

        viewModelScope.launch {
            try {
                val norma = repositorio.obtenerNorma(normaId)
                val articulos = repositorio.obtenerArticulos(normaId)

                _uiState.update {
                    it.copy(
                        tituloLey = norma.titulo,
                        articulos = articulos,
                        cargando = false,
                        error = null
                    )
                }
            } catch (excepcion: IOException) {
                _uiState.update {
                    it.copy(
                        cargando = false,
                        error = "No se pudo conectar. Revisa tu conexión."
                    )
                }
            } catch (excepcion: HttpException) {
                _uiState.update {
                    it.copy(
                        cargando = false,
                        error = "No se pudieron cargar los artículos."
                    )
                }
            }
        }
    }

    fun onAumentarLetra() {
        _uiState.update {
            it.copy(
                tamanoLetra = (it.tamanoLetra + PASO_LETRA)
                    .coerceAtMost(LETRA_MAXIMA)
            )
        }
    }

    fun onDisminuirLetra() {
        _uiState.update {
            it.copy(
                tamanoLetra = (it.tamanoLetra - PASO_LETRA)
                    .coerceAtLeast(LETRA_MINIMA)
            )
        }
    }

    private companion object {
        const val PASO_LETRA = 2
        const val LETRA_MINIMA = 12
        const val LETRA_MAXIMA = 26
    }
}
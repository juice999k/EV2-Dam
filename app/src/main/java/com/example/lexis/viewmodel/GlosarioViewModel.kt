package com.example.lexis.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lexis.repository.BibliotecaRepository
import com.example.lexis.ui.glosario.GlosarioUiState
import java.io.IOException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException

class GlosarioViewModel(
    private val repositorio: BibliotecaRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(GlosarioUiState())
    val uiState = _uiState.asStateFlow()

    init {
        cargarGlosario()
    }

    fun onBusquedaChanged(texto: String) {
        _uiState.update { it.copy(busqueda = texto) }
    }

    fun cargarGlosario() {
        if (_uiState.value.cargando) return
        _uiState.update { it.copy(cargando = true, error = null) }

        viewModelScope.launch {
            try {
                val terminos = repositorio.obtenerGlosario()
                _uiState.update {
                    it.copy(terminos = terminos, cargando = false, error = null)
                }
            } catch (excepcion: IOException) {
                _uiState.update {
                    it.copy(cargando = false, error = "No se pudo conectar. Revisa tu conexión.")
                }
            } catch (excepcion: HttpException) {
                _uiState.update {
                    it.copy(cargando = false, error = "No se pudo cargar el glosario.")
                }
            }
        }
    }
}
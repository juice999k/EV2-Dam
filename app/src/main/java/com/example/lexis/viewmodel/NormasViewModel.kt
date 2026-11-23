package com.example.lexis.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lexis.repository.BibliotecaRepository
import com.example.lexis.ui.normas.NormasUiState
import java.io.IOException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException

class NormasViewModel(
    private val repositorio: BibliotecaRepository,
    private val categoriaId: Int
) : ViewModel() {

    private val _uiState = MutableStateFlow(NormasUiState())
    val uiState: StateFlow<NormasUiState> = _uiState.asStateFlow()

    init {
        cargarNormas()
    }

    fun cargarNormas() {
        if (_uiState.value.cargando) return

        _uiState.update {
            it.copy(
                cargando = true,
                error = null
            )
        }

        viewModelScope.launch {
            try {
                val normas = repositorio.obtenerNormas(categoriaId)

                _uiState.update {
                    it.copy(
                        normas = normas,
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
                        error = "No se pudieron cargar las normas."
                    )
                }
            }
        }
    }
}
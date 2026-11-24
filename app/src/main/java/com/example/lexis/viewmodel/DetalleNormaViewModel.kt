package com.example.lexis.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lexis.repository.BibliotecaRepository
import com.example.lexis.ui.normas.DetalleNormaUiState
import com.example.lexis.util.ValidadorReporte
import java.io.IOException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException

class DetalleNormaViewModel(
    private val repositorio: BibliotecaRepository,
    private val normaId: Int
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetalleNormaUiState())
    val uiState: StateFlow<DetalleNormaUiState> = _uiState.asStateFlow()

    init {
        cargarNorma()
    }

    fun cargarNorma() {
        if (_uiState.value.cargando) return

        _uiState.update {
            it.copy(cargando = true, error = null)
        }

        viewModelScope.launch {
            try {
                val norma = repositorio.obtenerNorma(normaId)

                _uiState.update {
                    it.copy(
                        norma = norma,
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
                        error = if (excepcion.code() == 404) {
                            "La norma solicitada no existe."
                        } else {
                            "No se pudo cargar el detalle de la norma."
                        }
                    )
                }
            }
        }
    }

    fun abrirFormularioReporte() {
        if (_uiState.value.norma == null) return
        _uiState.update {
            it.copy(
                mostrarFormularioReporte = true,
                descripcionReporte = "",
                errorDescripcionReporte = null,
                errorEnvioReporte = null,
                mostrarConfirmacionReporte = false
            )
        }
    }

    fun cerrarFormularioReporte() {
        if (_uiState.value.enviandoReporte) return
        _uiState.update { it.copy(mostrarFormularioReporte = false) }
    }

    fun onDescripcionReporteChanged(texto: String) {
        if (_uiState.value.enviandoReporte) return
        _uiState.update {
            it.copy(
                descripcionReporte = texto,
                errorDescripcionReporte = null,
                errorEnvioReporte = null
            )
        }
    }

    fun cerrarConfirmacionReporte() {
        _uiState.update { it.copy(mostrarConfirmacionReporte = false) }
    }

    fun enviarReporte() {
        val estado = _uiState.value
        if (estado.enviandoReporte || estado.norma == null) return

        val errorDescripcion = ValidadorReporte.validarDescripcion(estado.descripcionReporte)
        if (errorDescripcion != null) {
            _uiState.update { it.copy(errorDescripcionReporte = errorDescripcion) }
            return
        }

        // Activar antes de lanzar evita envíos simultáneos desde el botón.
        _uiState.update {
            it.copy(enviandoReporte = true, errorEnvioReporte = null)
        }

        viewModelScope.launch {
            try {
                repositorio.enviarReporte(normaId, estado.descripcionReporte)
                _uiState.update {
                    it.copy(
                        enviandoReporte = false,
                        mostrarFormularioReporte = false,
                        descripcionReporte = "",
                        errorDescripcionReporte = null,
                        errorEnvioReporte = null,
                        mostrarConfirmacionReporte = true
                    )
                }
            } catch (excepcion: IOException) {
                _uiState.update {
                    it.copy(
                        enviandoReporte = false,
                        errorEnvioReporte = "No se pudo confirmar el envío. Revisa tu conexión."
                    )
                }
            } catch (excepcion: HttpException) {
                _uiState.update {
                    it.copy(
                        enviandoReporte = false,
                        errorEnvioReporte = "El servidor rechazó el reporte. Revisa los datos e inténtalo nuevamente."
                    )
                }
            }
        }
    }
}
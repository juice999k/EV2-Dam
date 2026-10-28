package com.example.lexis.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lexis.repository.UsuarioRepository
import com.example.lexis.ui.inicio.InicioUiState
import com.example.lexis.util.obtenerIniciales
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar

class InicioViewModel(
    private val repositorio: UsuarioRepository,
    private val idUsuario: Int
) : ViewModel() {

    private val _uiState = MutableStateFlow(InicioUiState(saludo = calcularSaludo()))
    val uiState: StateFlow<InicioUiState> = _uiState.asStateFlow()

    init {
        cargarUsuario()
    }

    /** Se vuelve a llamar al regresar desde el perfil para reflejar la foto nueva. */
    fun cargarUsuario() {
        viewModelScope.launch {
            repositorio.obtenerUsuario(idUsuario)?.let { usuario ->
                _uiState.update {
                    it.copy(
                        nombreUsuario = usuario.nombre.substringBefore(" "),
                        iniciales = obtenerIniciales(usuario.nombre),
                        rutaFotoPerfil = usuario.rutaFotoPerfil
                    )
                }
            }
        }
    }

    fun onBusquedaChanged(nuevoTexto: String) {
        _uiState.update { it.copy(textoBusqueda = nuevoTexto) }
    }

    private fun calcularSaludo(): String = when (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) {
        in 0..11 -> "BUENOS DÍAS"
        in 12..19 -> "BUENAS TARDES"
        else -> "BUENAS NOCHES"
    }
}

package com.example.lexis.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lexis.model.TipoUsuario
import com.example.lexis.repository.UsuarioRepository
import com.example.lexis.ui.perfil.PerfilUiState
import com.example.lexis.util.obtenerIniciales
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PerfilViewModel(
    private val repositorio: UsuarioRepository,
    private val idUsuario: Int
) : ViewModel() {

    private val _uiState = MutableStateFlow(PerfilUiState())
    val uiState: StateFlow<PerfilUiState> = _uiState.asStateFlow()

    init {
        cargarUsuario()
    }

    private fun cargarUsuario() {
        viewModelScope.launch {
            repositorio.obtenerUsuario(idUsuario)?.let { usuario ->
                _uiState.update {
                    it.copy(
                        nombre = usuario.nombre,
                        correo = usuario.correo,
                        tipoUsuario = TipoUsuario.valueOf(usuario.tipoUsuario).etiqueta,
                        iniciales = obtenerIniciales(usuario.nombre),
                        rutaFotoPerfil = usuario.rutaFotoPerfil
                    )
                }
            }
        }
    }

    fun onFotoCapturada(foto: Bitmap) {
        viewModelScope.launch {
            repositorio.guardarFotoPerfil(idUsuario, foto)
            val usuarioActualizado = repositorio.obtenerUsuario(idUsuario)
            _uiState.update {
                it.copy(
                    rutaFotoPerfil = usuarioActualizado?.rutaFotoPerfil,
                    mensaje = "Foto de perfil actualizada"
                )
            }
        }
    }

    fun onCamaraNoDisponible() {
        _uiState.update { it.copy(mensaje = "No se encontró una aplicación de cámara en este dispositivo") }
    }

    fun onPermisoCamaraDenegado() {
        _uiState.update { it.copy(mensaje = "Necesitas permitir el acceso a la cámara para tomar tu foto") }
    }

    fun onMensajeMostrado() {
        _uiState.update { it.copy(mensaje = null) }
    }

    fun onCerrarSesionClick() {
        _uiState.update { it.copy(mostrarModalCerrarSesion = true) }
    }

    fun onCancelarCerrarSesion() {
        _uiState.update { it.copy(mostrarModalCerrarSesion = false) }
    }

    fun onConfirmarCerrarSesion() {
        viewModelScope.launch {
            repositorio.cerrarSesion()
            _uiState.update { it.copy(mostrarModalCerrarSesion = false, sesionCerrada = true) }
        }
    }
}

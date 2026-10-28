package com.example.lexis.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lexis.repository.UsuarioRepository
import com.example.lexis.ui.login.LoginUiState
import com.example.lexis.util.ValidadorFormulario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repositorio: UsuarioRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    init {
        entrarSiHayUsuarioRecordado()
    }

    private fun entrarSiHayUsuarioRecordado() {
        viewModelScope.launch {
            repositorio.obtenerUsuarioRecordado()?.let { usuario ->
                _uiState.update { it.copy(idUsuarioAutenticado = usuario.id) }
            }
        }
    }

    fun onCorreoChanged(nuevoTexto: String) {
        _uiState.update { it.copy(correo = nuevoTexto, errorCorreo = null) }
    }

    fun onClaveChanged(nuevoTexto: String) {
        _uiState.update { it.copy(clave = nuevoTexto, errorClave = null) }
    }

    fun onRecordarChanged(activo: Boolean) {
        _uiState.update { it.copy(recordarUsuario = activo) }
    }

    fun onIngresarClick() {
        val estado = _uiState.value
        val errorCorreo = ValidadorFormulario.validarCorreo(estado.correo)
        val errorClave = ValidadorFormulario.validarClave(estado.clave)

        if (errorCorreo != null || errorClave != null) {
            _uiState.update { it.copy(errorCorreo = errorCorreo, errorClave = errorClave) }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(cargando = true) }
            val usuario = repositorio.iniciarSesion(estado.correo, estado.clave, estado.recordarUsuario)
            _uiState.update {
                if (usuario != null) {
                    it.copy(cargando = false, idUsuarioAutenticado = usuario.id)
                } else {
                    it.copy(
                        cargando = false,
                        mostrarModalError = true,
                        mensajeError = "Correo o contraseña incorrectos. Si no tienes cuenta, regístrate."
                    )
                }
            }
        }
    }

    fun onDismissModalError() {
        _uiState.update { it.copy(mostrarModalError = false) }
    }

    fun onOlvidastePasswordClick() {
        val errorCorreo = ValidadorFormulario.validarCorreo(_uiState.value.correo)
        val mensaje = errorCorreo?.let { "Escribe un correo válido arriba para recuperar tu contraseña. ($it)" }
            ?: "Se ha enviado un enlace de recuperación al correo: ${_uiState.value.correo.trim()}"

        _uiState.update { it.copy(mensajeOlvidar = mensaje, mostrarModalOlvidar = true) }
    }

    fun onDismissModalOlvidar() {
        _uiState.update { it.copy(mostrarModalOlvidar = false) }
    }
}

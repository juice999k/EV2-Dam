package com.example.lexis.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lexis.model.TipoUsuario
import com.example.lexis.repository.UsuarioRepository
import com.example.lexis.ui.registro.RegistroUiState
import com.example.lexis.util.ValidadorFormulario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegistroViewModel(
    private val repositorio: UsuarioRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegistroUiState())
    val uiState: StateFlow<RegistroUiState> = _uiState.asStateFlow()

    fun onNombreChanged(nuevoTexto: String) {
        _uiState.update { it.copy(nombre = nuevoTexto, errorNombre = null) }
    }

    fun onCorreoChanged(nuevoTexto: String) {
        _uiState.update { it.copy(correo = nuevoTexto, errorCorreo = null) }
    }

    fun onClaveChanged(nuevoTexto: String) {
        _uiState.update { it.copy(clave = nuevoTexto, errorClave = null) }
    }

    fun onConfirmacionChanged(nuevoTexto: String) {
        _uiState.update { it.copy(confirmacionClave = nuevoTexto, errorConfirmacion = null) }
    }

    fun onTipoUsuarioChanged(nuevoTipo: TipoUsuario) {
        _uiState.update { it.copy(tipoUsuario = nuevoTipo) }
    }

    fun onRegistrarClick() {
        val estado = _uiState.value
        val errorNombre = ValidadorFormulario.validarNombre(estado.nombre)
        val errorCorreo = ValidadorFormulario.validarCorreo(estado.correo)
        val errorClave = ValidadorFormulario.validarClave(estado.clave)
        val errorConfirmacion = ValidadorFormulario.validarConfirmacion(estado.clave, estado.confirmacionClave)

        val hayErrores = listOf(errorNombre, errorCorreo, errorClave, errorConfirmacion).any { it != null }
        if (hayErrores) {
            _uiState.update {
                it.copy(
                    errorNombre = errorNombre,
                    errorCorreo = errorCorreo,
                    errorClave = errorClave,
                    errorConfirmacion = errorConfirmacion
                )
            }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(cargando = true) }
            val registrado = repositorio.registrar(estado.nombre, estado.correo, estado.clave, estado.tipoUsuario)
            _uiState.update {
                if (registrado) {
                    it.copy(cargando = false, mostrarModalExito = true)
                } else {
                    it.copy(cargando = false, errorCorreo = "Este correo ya está registrado")
                }
            }
        }
    }

    fun onModalExitoCerrado() {
        _uiState.update { it.copy(mostrarModalExito = false, registroCompletado = true) }
    }
}

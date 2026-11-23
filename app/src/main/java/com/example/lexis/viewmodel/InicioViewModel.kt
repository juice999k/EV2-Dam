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
import com.example.lexis.repository.BibliotecaRepository
import com.example.lexis.ui.inicio.CategoriaLegal
import java.io.IOException
import retrofit2.HttpException

class InicioViewModel(
    private val repositorio: UsuarioRepository,
    private val idUsuario: Int,
    private val bibliotecaRepository: BibliotecaRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(InicioUiState(saludo = calcularSaludo()))
    val uiState: StateFlow<InicioUiState> = _uiState.asStateFlow()

    init {
        cargarUsuario()
        cargarCategorias()
    }

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

    fun cargarCategorias() {
        if (_uiState.value.cargandoCategorias) return

        _uiState.update {
            it.copy(
                cargandoCategorias = true,
                errorCategorias = null
            )
        }

        viewModelScope.launch {
            try {
                val categorias = bibliotecaRepository.obtenerCategorias()

                _uiState.update { estado ->
                    estado.copy(
                        categorias = categorias.map { categoria ->
                            CategoriaLegal(
                                id = categoria.id,
                                titulo = categoria.nombre,
                                descripcion = categoria.descripcion
                            )
                        },
                        cargandoCategorias = false,
                        errorCategorias = null
                    )
                }
            } catch (excepcion: IOException) {
                _uiState.update {
                    it.copy(
                        cargandoCategorias = false,
                        errorCategorias =
                            "No se pudo conectar. Revisa tu conexión e inténtalo nuevamente."
                    )
                }
            } catch (excepcion: HttpException) {
                _uiState.update {
                    it.copy(
                        cargandoCategorias = false,
                        errorCategorias =
                            "No se pudieron cargar las categorías. Inténtalo nuevamente."
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

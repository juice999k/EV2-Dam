package com.example.lexis.viewmodel

import androidx.lifecycle.ViewModel
import com.example.lexis.model.LeyDemo
import com.example.lexis.ui.lector.LectorUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LectorViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        LectorUiState(tituloLey = LeyDemo.TITULO, articulos = LeyDemo.articulos)
    )
    val uiState: StateFlow<LectorUiState> = _uiState.asStateFlow()

    fun onAumentarLetra() {
        _uiState.update { it.copy(tamanoLetra = (it.tamanoLetra + PASO_LETRA).coerceAtMost(LETRA_MAXIMA)) }
    }

    fun onDisminuirLetra() {
        _uiState.update { it.copy(tamanoLetra = (it.tamanoLetra - PASO_LETRA).coerceAtLeast(LETRA_MINIMA)) }
    }

    private companion object {
        const val PASO_LETRA = 2
        const val LETRA_MINIMA = 12
        const val LETRA_MAXIMA = 26
    }
}

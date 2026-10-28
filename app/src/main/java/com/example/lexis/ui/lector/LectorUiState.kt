package com.example.lexis.ui.lector
 
import com.example.lexis.model.Articulo
 
data class LectorUiState(
    val tituloLey: String = "",
    val articulos: List<Articulo> = emptyList(),
    val tamanoLetra: Int = 16
)

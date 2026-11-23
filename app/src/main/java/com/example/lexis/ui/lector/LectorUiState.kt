package com.example.lexis.ui.lector

import com.example.lexis.model.ArticuloDto

data class LectorUiState(
    val tituloLey: String = "",
    val articulos: List<ArticuloDto> = emptyList(),
    val tamanoLetra: Int = 16,
    val cargando: Boolean = false,
    val error: String? = null
)
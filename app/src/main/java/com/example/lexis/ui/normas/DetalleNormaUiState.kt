package com.example.lexis.ui.normas

import com.example.lexis.model.NormaDto

data class DetalleNormaUiState(
    val norma: NormaDto? = null,
    val cargando: Boolean = false,
    val error: String? = null
)
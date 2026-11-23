package com.example.lexis.ui.normas

import com.example.lexis.model.NormaDto

data class NormasUiState(
    val normas: List<NormaDto> = emptyList(),
    val cargando: Boolean = false,
    val error: String? = null
)
package com.example.lexis.repository

import com.example.lexis.model.CategoriaDto
import com.example.lexis.remote.LexisApiService

class BibliotecaRepository(
    private val api: LexisApiService
) {

    suspend fun obtenerCategorias(): List<CategoriaDto> {
        return api.obtenerCategorias()
    }
}
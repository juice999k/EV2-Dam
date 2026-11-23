package com.example.lexis.repository

import com.example.lexis.model.CategoriaDto
import com.example.lexis.remote.LexisApiService
import com.example.lexis.model.NormaDto

class BibliotecaRepository(
    private val api: LexisApiService
) {

    suspend fun obtenerCategorias(): List<CategoriaDto> {
        return api.obtenerCategorias()
    }
    suspend fun obtenerNormas(categoriaId: Int): List<NormaDto> {
        return api.obtenerNormas(categoriaId)
    }
}
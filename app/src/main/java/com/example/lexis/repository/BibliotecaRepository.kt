package com.example.lexis.repository

import com.example.lexis.model.CategoriaDto
import com.example.lexis.model.TerminoDto
import com.example.lexis.remote.LexisApiService
import com.example.lexis.model.NormaDto
import com.example.lexis.model.ArticuloDto
import com.example.lexis.model.NovedadDto

class BibliotecaRepository(
    private val api: LexisApiService
) {

    suspend fun obtenerCategorias(): List<CategoriaDto> {
        return api.obtenerCategorias()
    }
    suspend fun obtenerNormas(categoriaId: Int): List<NormaDto> {
        return api.obtenerNormas(categoriaId)
    }
    suspend fun obtenerNorma(normaId: Int): NormaDto {
        return api.obtenerNorma(normaId)
    }
    suspend fun obtenerArticulos(normaId: Int): List<ArticuloDto> {
        return api.obtenerArticulos(normaId)
            .sortedBy { it.numero.toIntOrNull() ?: Int.MAX_VALUE }
    }
    suspend fun obtenerNovedades(): List<NovedadDto> {
        return api.obtenerNovedades()
    }

    suspend fun obtenerGlosario(): List<TerminoDto> {
        return api.obtenerGlosario().sortedBy { it.termino.lowercase() }
    }
}
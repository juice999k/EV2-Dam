package com.example.lexis.remote


import com.example.lexis.model.CategoriaDto
import retrofit2.http.GET

interface LexisApiService {

    @GET("categoria")
    suspend fun obtenerCategorias(): List<CategoriaDto>
}
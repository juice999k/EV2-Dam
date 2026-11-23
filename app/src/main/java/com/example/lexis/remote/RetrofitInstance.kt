package com.example.lexis.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL =
        "https://x8ki-letl-twmt.n7.xano.io/api:PRtBSZfd/"

    val api: LexisApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LexisApiService::class.java)
    }
}
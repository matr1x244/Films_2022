package com.geekbrains.december.model.entities.rest.rest_entities.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MovieRepo {

    val api: MovieAPI by lazy {
        val adapter = Retrofit.Builder()
            .baseUrl(ApiUtils.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(ApiUtils.getOkHTTPBuilderWithHeaders())
            .build()

        adapter.create(MovieAPI::class.java)
    }
}
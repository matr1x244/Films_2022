package com.geekbrains.december.model.entities.rest.rest_entities.retrofit

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

//https://api.kinopoisk.dev/movie?search=320&field=id&token=1KK4612-HEMM8RX-P3QSGPJ-VR0AQ82

object ApiUtils {
    private val baseUrlMainPart = "https://api.kinopoisk.dev/movie?"
    //private val baseUrlVersion = "search=&field=id"
    val baseUrl = "$baseUrlMainPart"

    fun getOkHTTPBuilderWithHeaders(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(10, TimeUnit.SECONDS)
        httpClient.readTimeout(10, TimeUnit.SECONDS)
        httpClient.writeTimeout(10, TimeUnit.SECONDS)
        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .header("field","id")
                .header("token", "1KK4612-HEMM8RX-P3QSGPJ-VR0AQ82")
                .method(original.method(), original.body())
                .build()

            chain.proceed(request)
        }

        return httpClient.build()
    }
}
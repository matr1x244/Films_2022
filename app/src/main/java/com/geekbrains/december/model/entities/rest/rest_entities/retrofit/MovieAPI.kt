package com.geekbrains.december.model.entities.rest.rest_entities.retrofit

import com.geekbrains.december.model.entities.rest.rest_entities.MovieDetailsDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface MovieAPI {

    @GET("search") //search=ХХХ&field=id (https://api.kinopoisk.dev/movie?search=320&field=id&token=1KK4612-HEMM8RX-P3QSGPJ-VR0AQ82)
    fun getMovieDetails(
        @Query("id") id: Int
    ) : Call<MovieDetailsDTO>
}
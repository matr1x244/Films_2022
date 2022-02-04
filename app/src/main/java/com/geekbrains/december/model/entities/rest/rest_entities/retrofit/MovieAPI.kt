package com.geekbrains.december.model.entities.rest.rest_entities.retrofit

import com.geekbrains.december.model.entities.rest.rest_entities.MovieDetailsDTO
import com.geekbrains.december.model.entities.rest.rest_entities.MovieLoadDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface MovieAPI {

    @GET("movie") //search=ХХХ&field=id (https://api.kinopoisk.dev/movie?search=320&field=id&token=1KK4612-HEMM8RX-P3QSGPJ-VR0AQ82)

    fun getMovieDetails(
        @Query("search") id: Int?,
        @Query("field") field: String,
        @Query("token") token: String,

        ) : Call <MovieDetailsDTO>

    @GET("movie?sortField=votes.imdb&sortType=-1")

    fun getMovieRecyclerFilms( //https://api.kinopoisk.dev/movie?field=rating.kp&search=${SEARCH_PAGE}&field=year&search=${YEAR_FILMS}&sortField=year&sortType=1&token=${API_KEY}
        @Query("field") fieldRating: String,
        @Query("search") searchRating: String,
        @Query("field") fieldYear: String,
        @Query("search") searchYear: String,
        @Query("field") fieldTypeNimber: String,
        @Query("search") searchTypeNumber: String,
        @Query("token") token: String,

    ) : Call <MovieLoadDTO>

    @GET("movie?sortField=votes.imdb&sortType=-1")

    fun getMovieRecyclerSerials( //https://api.kinopoisk.dev/movie?field=rating.kp&search=${SEARCH_PAGE}&field=year&search=${YEAR_FILMS}&sortField=year&sortType=1&token=${API_KEY}
        @Query("field") fieldRating: String,
        @Query("search") searchRating: String,
        @Query("field") fieldYear: String,
        @Query("search") searchYear: String,
        @Query("field") fieldTypeNimber: String,
        @Query("search") searchTypeNumber: String,
        @Query("token") token: String

    ) : Call <MovieLoadDTO>
}

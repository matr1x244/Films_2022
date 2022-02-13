package com.geekbrains.december.model.entities.rest.rest_entities

import com.google.gson.annotations.SerializedName

data class MovieDetailsDTO(

    @SerializedName("poster") val poster: Poster?,
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("year") val year: Int?,
    @SerializedName("rating") val rating: Rating?,
    @SerializedName("description") val description: String?,
    @SerializedName("slogan") val slogan: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("premiere") val premiere: Premiere?

    //@SerializedName("videos") val videos: Videos?
)

data class Premiere(
    @SerializedName("country") val country: String?
)

/*
data class Videos(
    @SerializedName("trailers") val trailers: Trailers?
)

data class Trailers(
    @SerializedName("url") val url: String?,
    @SerializedName("site") val site: String?
)
*/



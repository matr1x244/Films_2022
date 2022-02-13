package com.geekbrains.december.model.entities.rest.rest_entities

import com.google.gson.annotations.SerializedName


data class MovieLoadDTO (

   @SerializedName("docs") val docs: Array<Docs>
)

data class Docs(

    @SerializedName("poster") val poster: Poster,
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("year") val year: Int?,
    @SerializedName("rating") val rating: Rating?,
    @SerializedName("description") val description: String?,
    @SerializedName("slogan") val slogan: String?,
    @SerializedName("budget") val budget: Budget?,
    @SerializedName("type") val type: String?,
    @SerializedName("enName") val enName: String?,
)

data class Rating(
    @SerializedName("imdb") val imdb: Double?
)

data class Poster(
    @SerializedName ("url") val url: String?,
    @SerializedName ("previewUrl") val previewUrl: String?
)

data class Budget(
    @SerializedName ("value") val value: Number?,
    @SerializedName ("currency") val currency: String?
)







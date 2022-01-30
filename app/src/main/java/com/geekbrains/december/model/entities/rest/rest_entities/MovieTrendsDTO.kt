package com.geekbrains.december.model.entities.rest.rest_entities

import com.google.gson.annotations.SerializedName


data class MovieTrendsDTO (

   @SerializedName("docs") val docs: Array<Docs>
)

data class Docs(

    @SerializedName("poster") val poster: Poster,
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("year") val year: Int?,
    @SerializedName("tmbd") val tmbd: Double?,
    @SerializedName("description") val description: String?,
    @SerializedName("slogan") val slogan: String?

)



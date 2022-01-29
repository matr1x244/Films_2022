package com.geekbrains.december.model.entities.rest_entities


data class MovieTrendsDTO (

    val docs: Array<Docs>
)

data class Docs(

    val id: Int?,
    val name: String?,
    val year: Int?,
    val tmbd: Double?,
    val description: String?,
    val slogan: String?
)



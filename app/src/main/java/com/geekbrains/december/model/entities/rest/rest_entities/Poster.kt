package com.geekbrains.december.model.entities.rest.rest_entities

import com.google.gson.annotations.SerializedName

data class Poster(

    @SerializedName ("url") val url: String,
    @SerializedName("previewUrl") val previewUrl : String
)

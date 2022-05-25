package dev.sergiobelda.foundry.data.api.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GoogleFontApiModel(
    val family: String,
    val category: String
)

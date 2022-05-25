package dev.sergiobelda.foundry.data.api.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GoogleFontsResponse(
    val items: List<GoogleFontApiModel>
)

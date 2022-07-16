package dev.sergiobelda.foundry.data.api.service

import dev.sergiobelda.foundry.data.api.model.GoogleFontsResponse
import retrofit2.Response
import retrofit2.http.GET

interface GoogleFontsApiService {

    @GET("/webfonts/v1/webfonts")
    suspend fun getGoogleFonts(): Response<GoogleFontsResponse>
}

package com.dabellan.yugiproject.Services


import com.dabellan.yugiproject.Constants.Constants
import com.dabellan.yugiproject.Entities.Carta
import retrofit2.Response
import retrofit2.http.GET

interface CartaService {
    @GET(Constants.CARTAS_PATH)
    suspend fun getCartas(): Response<MutableList<Carta>>
}
package com.dabellan.yugiproject.Services


import com.dabellan.yugiproject.Constants.Constants
import com.dabellan.yugiproject.data.model.Carta
import com.dabellan.yugiproject.data.model.CartaItem
import retrofit2.http.GET

interface RetrofitService {
    @GET(Constants.CARTAS_PATH)
    suspend fun getCartas(): List<CartaItem>
}
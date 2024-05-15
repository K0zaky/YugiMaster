package com.dabellan.yugiproject.Services


import com.dabellan.yugiproject.Constants.Constants
import com.dabellan.yugiproject.data.model.CartaItem
import com.dabellan.yugiproject.data.model.UsuarioItem
import retrofit2.http.GET

interface RetrofitService {
    @GET(Constants.CARTAS_PATH)
    suspend fun getCartas(): List<CartaItem>

    @GET(Constants.USERS_PATH)
    suspend fun getUsers(): List<UsuarioItem>
}
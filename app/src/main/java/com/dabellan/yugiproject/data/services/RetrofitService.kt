package com.dabellan.yugiproject.data.services


import com.dabellan.yugiproject.constants.Constants
import com.dabellan.yugiproject.data.model.CartaItem
import com.dabellan.yugiproject.data.model.MagicaItem
import com.dabellan.yugiproject.data.model.MonstruoItem
import com.dabellan.yugiproject.data.model.TrampaItem
import com.dabellan.yugiproject.data.model.UsuarioItem
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RetrofitService {
    @GET(Constants.CARTAS_PATH)
    suspend fun getCartas(): List<CartaItem>

    @GET(Constants.USERS_PATH)
    suspend fun getUsers(): List<UsuarioItem>

    @POST(Constants.USERS_PATH)
    suspend fun crearUsuario(@Body usuario: UsuarioItem): UsuarioItem

    @GET(Constants.MONSTRUOS_PATH)
    suspend fun getMonstruos(): List<MonstruoItem>

    @GET(Constants.MAGICAS_PATH)
    suspend fun getMagicas(): List<MagicaItem>

    @GET(Constants.TRAMPAS_PATH)
    suspend fun getTrampas(): List<TrampaItem>

    @GET(Constants.MONSTRUOID_PATH)
    suspend fun getMonstruo(@Path("id") id: String): MonstruoItem

    @GET(Constants.MAGICAID_PATH)
    suspend fun getMagica(@Path("id") id: String): MagicaItem

    @GET(Constants.TRAMPAID_PATH)
    suspend fun getTrampa(@Path("id") id: String): TrampaItem
}
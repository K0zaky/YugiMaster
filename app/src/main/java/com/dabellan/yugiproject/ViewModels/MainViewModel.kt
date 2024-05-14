package com.dabellan.yugiproject.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dabellan.yugiproject.Services.RetrofitService
import com.dabellan.yugiproject.data.model.CartaItem
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel: ViewModel(){
    //CREO que esto no se usa
    private val _cartas = MutableLiveData<List<CartaItem>>()
    val cartas: LiveData<List<CartaItem>> = _cartas

    private val apiService: RetrofitService by lazy {
        Retrofit.Builder()
            .baseUrl("http://yugi.navelsystems.es")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitService::class.java)
    }

    init {
        viewModelScope.launch {
            fetchCartas()
        }
    }

    private suspend fun fetchCartas() {
        val cartasList = apiService.getCartas()
        _cartas.postValue(cartasList)
    }
}
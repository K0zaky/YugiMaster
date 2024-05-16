package com.dabellan.yugiproject.presentation.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dabellan.yugiproject.data.model.CartaItem
import com.dabellan.yugiproject.data.services.RetrofitService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeViewModel : ViewModel() {

    private val _allCards = MutableLiveData<List<CartaItem>>()
    val allCards: LiveData<List<CartaItem>> = _allCards

    private val apiService: RetrofitService by lazy {
        Retrofit.Builder()
            .baseUrl("http://yugi.navelsystems.es")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitService::class.java)
    }

    fun getAllCards() = viewModelScope.launch {
        val cards = apiService.getCartas()
        _allCards.postValue(cards)
    }

}
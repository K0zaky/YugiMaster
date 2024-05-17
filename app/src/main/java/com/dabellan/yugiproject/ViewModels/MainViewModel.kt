package com.dabellan.yugiproject.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dabellan.yugiproject.data.instances.apiService
import com.dabellan.yugiproject.data.model.CartaItem
import kotlinx.coroutines.launch

class MainViewModel: ViewModel(){

    private val _cartas = MutableLiveData<List<CartaItem>>()
    //val cartas: LiveData<List<CartaItem>> = _cartas

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
package com.dabellan.yugiproject.presentation.deck_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dabellan.yugiproject.data.instances.RetrofitInstance
import com.dabellan.yugiproject.data.model.CartaItem
import kotlinx.coroutines.launch

class AddCartaViewModel : ViewModel() {
    private val _cartaItems = MutableLiveData<List<CartaItem>?>()
    val cartaItems: LiveData<List<CartaItem>?> = _cartaItems

    init {
        getAllCartas()
    }

    fun getAllCartas() {
        viewModelScope.launch {
            try {
                val cartas = RetrofitInstance.api.getCartas()
                _cartaItems.postValue(cartas)
            } catch (e: Exception) {
                _cartaItems.postValue(emptyList())
            }
        }
    }
}
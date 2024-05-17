package com.dabellan.yugiproject.presentation.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dabellan.yugiproject.data.instances.apiService
import com.dabellan.yugiproject.data.model.CartaItem
import kotlinx.coroutines.launch

class DeckViewModel : ViewModel() {

    private val _allCards = MutableLiveData<List<CartaItem>>()
    val allCards: LiveData<List<CartaItem>> = _allCards
    

    fun getAllCards() = viewModelScope.launch {
        val cards = apiService.getCartas()
        _allCards.postValue(cards)
    }

}
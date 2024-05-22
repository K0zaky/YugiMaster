package com.dabellan.yugiproject.presentation.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dabellan.yugiproject.data.instances.apiService
import com.dabellan.yugiproject.data.model.CartaItem
import com.dabellan.yugiproject.data.model.MagicaItem
import com.dabellan.yugiproject.data.model.MonstruoItem
import com.dabellan.yugiproject.data.model.TrampaItem
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _allCards = MutableLiveData<List<CartaItem>>()
    val allCards: LiveData<List<CartaItem>> = _allCards

    private val _allMonsters = MutableLiveData<List<MonstruoItem>>()
    val allMonsters: LiveData<List<MonstruoItem>> = _allMonsters

    private val _allMagicas = MutableLiveData<List<MagicaItem>>()
    val allMagicas: LiveData<List<MagicaItem>> = _allMagicas

    private val _allTrampas = MutableLiveData<List<TrampaItem>>()
    val allTrampas: LiveData<List<TrampaItem>> = _allTrampas

    fun getAllCards() = viewModelScope.launch {
        val cards = apiService.getCartas()
        _allCards.postValue(cards)
    }

    fun getAllMonsters() = viewModelScope.launch {
        val cards = apiService.getMonstruos()
        _allMonsters.postValue(cards)
    }

    fun getAllMagicas() = viewModelScope.launch {
        val cards = apiService.getMagicas()
        _allMagicas.postValue(cards)
    }

    fun getAllTrampas() = viewModelScope.launch {
        val cards = apiService.getTrampas()
        _allTrampas.postValue(cards)
    }

}
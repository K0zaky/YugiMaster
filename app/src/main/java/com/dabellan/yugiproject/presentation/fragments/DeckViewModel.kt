package com.dabellan.yugiproject.presentation.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dabellan.yugiproject.data.instances.apiService
import com.dabellan.yugiproject.data.model.DeckItem
import com.dabellan.yugiproject.data.model.LogedUser
import kotlinx.coroutines.launch

class DeckViewModel : ViewModel() {
    private val _allDecks = MutableLiveData<List<DeckItem>>()
    val allDecks: LiveData<List<DeckItem>> = _allDecks

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun getAllDecks() = viewModelScope.launch {
        try {
            val id = LogedUser.userId.toString()
            val decks = apiService.getUserDecks(id)
            _allDecks.postValue(decks)
            _error.postValue(null)
        } catch (e: Exception) {
            _allDecks.postValue(emptyList())
            _error.postValue("No se pudieron cargar los decks. Error: ${e.message}")
        }
    }

    fun addDeck(deckName: String) = viewModelScope.launch {
        try {
            val idUser = LogedUser.userId
            val newDeck = DeckItem(nombre = deckName, precio = 0.0, id_user = idUser)
            val createdDeck = apiService.newDeck(newDeck)
            getAllDecks()
        } catch (e: Exception) {
            println("No se pudo añadir el deck: ${e.message}")
        }
    }
}
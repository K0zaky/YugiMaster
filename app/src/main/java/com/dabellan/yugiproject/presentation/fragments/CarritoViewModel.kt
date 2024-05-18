package com.dabellan.yugiproject.presentation.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dabellan.yugiproject.data.instances.apiService
import com.dabellan.yugiproject.data.model.DeckItem
import com.dabellan.yugiproject.data.model.LogedUser
import kotlinx.coroutines.launch

class CarritoViewModel : ViewModel() {

    private val _allDecks = MutableLiveData<List<DeckItem>>()
    val allDecks: LiveData<List<DeckItem>> = _allDecks

    fun getAllDecks() = viewModelScope.launch {
        val id = LogedUser.userId.toString()
        val decks = apiService.getUserDecks(id)
        _allDecks.postValue(decks)
    }

}
package com.dabellan.yugiproject.presentation.deck_detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dabellan.yugiproject.data.instances.RetrofitInstance
import com.dabellan.yugiproject.data.model.CartaItem
import com.dabellan.yugiproject.data.services.RetrofitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DeckDetailViewModel() : ViewModel() {

    private val apiService: RetrofitService = RetrofitInstance.api

    private val _cartaItems = MutableLiveData<List<CartaItem>?>()
    val cartaItems: MutableLiveData<List<CartaItem>?> = _cartaItems

    private val _error = MutableLiveData<String?>()


    fun getDeckContentById(deckId: String) {
        viewModelScope.launch {
            try {
                val cartaItems = getDeckContentFromApi(deckId)
                if (cartaItems != null && cartaItems.isNotEmpty()) {
                    _cartaItems.postValue(cartaItems)
                } else {
                    _cartaItems.postValue(emptyList())
                }
            } catch (e: Exception) {
                _error.postValue("Error al cargar el contenido del deck: ${e.message}")
            }
        }
    }

    private suspend fun getDeckContentFromApi(deckId: String): List<CartaItem>? {
        return withContext(Dispatchers.IO) {
            try {
                apiService.getCartasInDeck(deckId)
            } catch (e: Exception) {
                null
            }
        }
    }
}
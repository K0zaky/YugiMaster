package com.dabellan.yugiproject.presentation.deck_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dabellan.yugiproject.data.instances.RetrofitInstance
import com.dabellan.yugiproject.data.model.CartaItem
import com.dabellan.yugiproject.data.services.RetrofitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DeckDetailViewModel : ViewModel() {

    private val apiService: RetrofitService = RetrofitInstance.api

    private val _cartaItems = MutableLiveData<List<CartaItem>?>()
    val cartaItems: LiveData<List<CartaItem>?> = _cartaItems

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    fun getDeckContentById(deckId: Int) {
        viewModelScope.launch {
            try {
                val cartaItems = getDeckContentFromApi(deckId)
                _cartaItems.postValue(cartaItems)
            } catch (e: Exception) {
                _error.postValue("Error al cargar el contenido del deck: ${e.message}")
            }
        }
    }

    private suspend fun getDeckContentFromApi(deckId: Int): List<CartaItem>? {
        return withContext(Dispatchers.IO) {
            try {
                apiService.getCartasInDeck(deckId)
            } catch (e: Exception) {
                null
            }
        }
    }

    fun deleteCartaFromDeck(deckId: Int, cartaId: Int) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    apiService.deleteCartaFromDeck(deckId, cartaId)
                }
                if (response.isSuccessful) {
                    _cartaItems.value = _cartaItems.value?.filterNot { it.id == cartaId }
                } else {
                    _error.postValue("Error al eliminar la carta: ${response.message()}")
                }
            } catch (e: Exception) {
                _error.postValue("Error al eliminar la carta: ${e.message}")
            }
        }
    }

    fun calcularPrecioTotal(cartas: List<CartaItem>?): Double {
        return cartas?.sumByDouble { it.precio.toDouble() } ?: 0.0
    }
}
package com.dabellan.yugiproject.presentation.cart_detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dabellan.yugiproject.data.instances.apiService
import com.dabellan.yugiproject.data.model.MagicaItem
import com.dabellan.yugiproject.data.model.MonstruoItem
import com.dabellan.yugiproject.data.model.TrampaItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartDetailViewModel : ViewModel() {

    private val _monstruoItem = MutableLiveData<MonstruoItem?>()
    val monstruoItem: MutableLiveData<MonstruoItem?> = _monstruoItem

    private val _magicaItem = MutableLiveData<MagicaItem?>()
    val magicaItem: MutableLiveData<MagicaItem?> = _magicaItem

    private val _trampaItem = MutableLiveData<TrampaItem?>()
    val trampaItem: MutableLiveData<TrampaItem?> = _trampaItem


    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun getCartById(cartId: String) {
        uiScope.launch {
            try {
                val monstruoItem = getMonstruoFromApi(cartId)
                if (monstruoItem != null) {
                    _monstruoItem.postValue(monstruoItem)
                } else {
                    val magicaItem = getMagicaFromApi(cartId)
                    if (magicaItem != null) {
                        _magicaItem.postValue(magicaItem)
                    } else {
                        val trampaItem = getTrampaFromApi(cartId)
                        if (trampaItem != null) {
                            _trampaItem.postValue(trampaItem)
                        }
                    }
                }
            } catch (e: Exception) {
                // Manejar errores
            }
        }
    }

    private suspend fun getMonstruoFromApi(cartId: String): MonstruoItem? {
        return withContext(Dispatchers.IO) {
            try {
                apiService.getMonstruo(cartId)
            } catch (e: Exception) {
                null
            }
        }
    }

    private suspend fun getMagicaFromApi(cartId: String): MagicaItem? {
        return withContext(Dispatchers.IO) {
            try {
                apiService.getMagica(cartId)
            } catch (e: Exception) {
                null
            }
        }
    }

    private suspend fun getTrampaFromApi(cartId: String): TrampaItem? {
        return withContext(Dispatchers.IO) {
            try {
                apiService.getTrampa(cartId)
            } catch (e: Exception) {
                null
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
package com.dabellan.yugiproject.presentation.deck_detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dabellan.yugiproject.data.model.CartaItem
import com.dabellan.yugiproject.presentation.composables.CoilImage
import com.dabellan.yugiproject.presentation.composables.normalizarTexto

class DeckDetailActivity: ComponentActivity() {
    private val viewModel: DeckDetailViewModel by viewModels()
    private lateinit var deckId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        deckId = intent.getIntExtra("deckId", -1).toString()
        viewModel.getDeckContentById(deckId)

        viewModel.cartaItems.observe(this) { cartaItems ->
            if (cartaItems != null) {
                setContent {
                    DeckContent(cartaItems)
                }
            }
        }
    }

    @Composable
    fun DeckContent(cartaItems: List<CartaItem>?) {
        if (cartaItems.isNullOrEmpty()) {
            Text(text = "No hay cartas en este deck")
        } else {
            LazyColumn {
                items(cartaItems) { cartaItem ->
                    CartaItemView(cartaItem)
                }
            }
        }
    }

    @Composable
    fun CartaItemView(cartaItem: CartaItem) {
        Column {
            Text(text = normalizarTexto(cartaItem.nombre))
            Text(text = normalizarTexto(cartaItem.codigo))
            CoilImage(url = cartaItem.imagen, modifier = Modifier.size(200.dp))
        }
    }

}
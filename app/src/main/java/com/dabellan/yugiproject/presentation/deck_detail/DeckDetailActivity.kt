package com.dabellan.yugiproject.presentation.deck_detail

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dabellan.yugiproject.data.model.CartaItem
import com.dabellan.yugiproject.presentation.composables.CoilImage
import com.dabellan.yugiproject.presentation.composables.normalizarTexto

class DeckDetailActivity : ComponentActivity() {
    private val viewModel: DeckDetailViewModel by viewModels()
    private var deckId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        deckId = intent.getIntExtra("deckId", -1)
        viewModel.getDeckContentById(deckId)

        viewModel.cartaItems.observe(this) { cartaItems ->
            if (cartaItems != null) {
                setContent {
                    DeckContent(cartaItems, onAddCartaClick = {
                        navigateToAddCarta()
                    })
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getDeckContentById(deckId)
    }

    private fun navigateToAddCarta() {
        val intent = Intent(this, AddCartaActivity::class.java)
        intent.putExtra("deckId", deckId)
        startActivity(intent)
    }

    @Composable
    fun DeckContent(cartaItems: List<CartaItem>?, onAddCartaClick: () -> Unit) {
        Column {
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

        Button(onClick = onAddCartaClick, modifier = Modifier.padding(16.dp)) {
            Text(text = "Añadir Carta")
        }
    }

    @Composable
    fun CartaItemView(cartaItem: CartaItem) {
        Column {
            Text(text = normalizarTexto(cartaItem.nombre))
            Text(text = normalizarTexto(cartaItem.codigo))
            Text(text = cartaItem.precio.toString())
            CoilImage(url = cartaItem.imagen, modifier = Modifier.size(200.dp))
        }
    }
}
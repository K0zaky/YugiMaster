package com.dabellan.yugiproject.presentation.deck_detail

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dabellan.yugiproject.data.model.CartaItem
import com.dabellan.yugiproject.presentation.composables.CardItem
import com.dabellan.yugiproject.ui.theme.YugiprojectTheme

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
                    YugiprojectTheme {
                        DeckContent(cartaItems, onAddCartaClick = {
                            navigateToAddCarta()
                        }, onDeleteCartaClick = { cartaId ->
                            viewModel.deleteCartaFromDeck(deckId, cartaId)
                        })
                    }
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
    fun DeckContent(
        cartaItems: List<CartaItem>?,
        onAddCartaClick: () -> Unit,
        onDeleteCartaClick: (Int) -> Unit
    ) {
        var precioTotal by remember { mutableStateOf(0.0) }

        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
            ) {
                items(cartaItems.orEmpty()) { cartaItem ->
                    CardItem(carta = cartaItem, onClick = {
                        onDeleteCartaClick(cartaItem.id)
                    })
                }
            }

            Spacer(modifier = Modifier.size(16.dp))

            /*Text(
                text = "Precio total: ${precioTotal}€",
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp),
                style = MaterialTheme.typography.bodyMedium
            )*/

            Spacer(modifier = Modifier.size(16.dp))

            Button(
                onClick = onAddCartaClick,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomCenter)
            ) {
                Text(text = "Añadir Carta")
            }
        }

        LaunchedEffect(cartaItems) {
            precioTotal = viewModel.calcularPrecioTotal(cartaItems)
        }
    }
}
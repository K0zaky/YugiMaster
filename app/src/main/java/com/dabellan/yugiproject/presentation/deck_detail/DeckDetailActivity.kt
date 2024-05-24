package com.dabellan.yugiproject.presentation.deck_detail

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
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
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
            ) {
                items(cartaItems.orEmpty()) { cartaItem ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 16.dp)
                            .clickable {
                                //TODO: Implementar el poder borrar la carta
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CoilImage(
                            url = cartaItem.imagen.replace("\\", ""),
                            modifier = Modifier.size(80.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(
                                text = normalizarTexto(cartaItem.nombre),
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(text = "Código: ${cartaItem.codigo}")
                            Text(text = "Precio: ${cartaItem.precio}€")
                        }
                    }
                }
            }

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

        Spacer(modifier = Modifier.size(56.dp))

    }

    @Composable
    fun CartaItemView(cartaItem: CartaItem) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            CoilImage(url = cartaItem.imagen, modifier = Modifier.size(100.dp).padding(end = 16.dp))

            Column {
                Text(text = normalizarTexto(cartaItem.nombre), modifier = Modifier.padding(bottom = 4.dp))
                Text(text = normalizarTexto(cartaItem.codigo), modifier = Modifier.padding(bottom = 4.dp))
                //Text(text = cartaItem.precio.toString())
            }
        }
    }
}
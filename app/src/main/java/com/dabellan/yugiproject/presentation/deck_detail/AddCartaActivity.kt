package com.dabellan.yugiproject.presentation.deck_detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.dabellan.yugiproject.data.instances.RetrofitInstance
import com.dabellan.yugiproject.data.model.CartaItem
import com.dabellan.yugiproject.data.services.CartaDeckBody
import com.dabellan.yugiproject.presentation.composables.normalizarTexto
import com.dabellan.yugiproject.ui.theme.YugiprojectTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AddCartaActivity : ComponentActivity() {
    private val viewModel: AddCartaViewModel by viewModels()
    private var deck_id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        deck_id = intent.getIntExtra("deckId", -1)
        Log.i("deck", deck_id.toString())
        viewModel.getAllCartas()

        viewModel.cartaItems.observe(this) { cartaItems ->
            if (cartaItems != null) {
                setContent {
                    YugiprojectTheme {
                        CartaListContent(cartaItems) { selectedCarta ->
                            addCartaToDeck(deck_id, selectedCarta.id)
                        }
                    }
                }
            }
        }
    }

    private fun addCartaToDeck(deck_id: Int, id_carta: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance.api.anyadirCartaDeck(deck_id, CartaDeckBody(id_carta))
                withContext(Dispatchers.Main) {
                    if (response != null) {
                        Toast.makeText(this@AddCartaActivity, "Carta añadida con éxito", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@AddCartaActivity, "Error al añadir la carta", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@AddCartaActivity, "Error de red: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    fun CartaListContent(cartaItems: List<CartaItem>, onCartaClick: (CartaItem) -> Unit) {
        var searchQuery by remember { mutableStateOf("") }

        val filteredCartaItems = cartaItems.filter {
            it.nombre.contains(searchQuery, ignoreCase = true)
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { },
                    backgroundColor = Color.White,
                    elevation = AppBarDefaults.TopAppBarElevation,
                    navigationIcon = {
                        IconButton(onClick = { onBackPressed() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            }
        ) {
            Column {
                TextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    label = { Text("Buscar carta") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
                LazyColumn {
                    items(filteredCartaItems) { cartaItem ->
                        CartaItemView(cartaItem, onCartaClick)
                    }
                }
            }
        }
    }

    @Composable
    fun CartaItemView(cartaItem: CartaItem, onCartaClick: (CartaItem) -> Unit) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onCartaClick(cartaItem) }
                .padding(vertical = 8.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberImagePainter(cartaItem.imagen),
                contentDescription = null,
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = normalizarTexto(cartaItem.nombre),
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = normalizarTexto(cartaItem.codigo))
                Text(text = "Precio: ${cartaItem.precio}€")
            }
        }
    }
}
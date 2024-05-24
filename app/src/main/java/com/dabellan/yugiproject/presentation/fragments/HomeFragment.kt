package com.dabellan.yugiproject.presentation.fragments

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.asFlow
import com.dabellan.yugiproject.presentation.cart_detail.CartDetailActivity
import com.dabellan.yugiproject.presentation.composables.CardItem

@Composable
fun HomeFragment(homeViewModel: HomeViewModel) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    DisposableEffect(lifecycleOwner) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                homeViewModel.getAllCards()
            }
        }
        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
        }
    }

    var searchText by remember { mutableStateOf("") }

    val allCards = homeViewModel.allCards.asFlow().collectAsState(initial = emptyList())

    val filteredCards = if (searchText.isBlank()) {
        allCards.value
    } else {
        allCards.value.filter { carta ->
            carta.nombre.contains(searchText, ignoreCase = true)
        }
    }

    Column {
        Spacer(modifier = Modifier.size(48.dp))
        OutlinedTextField(
            value = searchText,
            onValueChange = { searchText = it },
            label = { Text("Buscar carta") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp)
        )
        LazyColumn {
            items(filteredCards) { carta ->
                CardItem(carta) { cartaId ->
                    onCardClick(cartaId, context)
                }
            }
        }

        Spacer(modifier = Modifier.size(56.dp))
    }
}

private fun onCardClick(cartaId: Int, context: Context) {
    val intent = Intent(context, CartDetailActivity::class.java)
    intent.putExtra("cartId", cartaId)
    context.startActivity(intent)
}
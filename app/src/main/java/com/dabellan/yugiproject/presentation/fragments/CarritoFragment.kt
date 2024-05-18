package com.dabellan.yugiproject.presentation.fragments

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.asFlow
import com.dabellan.yugiproject.presentation.cart_detail.CartDetailActivity

@Composable
fun CarritoFragment(carritoViewModel: CarritoViewModel) {

    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    DisposableEffect(lifecycleOwner) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                carritoViewModel.getAllDecks()
            }
        }
        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
        }
    }

    val allCards = carritoViewModel.allDecks.asFlow().collectAsState(initial = emptyList())

    Column(Modifier.verticalScroll(rememberScrollState())) {
        Spacer(modifier = Modifier.size(64.dp))
        Text(text = "carrito")
/*
        allCards.value.forEach { carta ->
            CardItem(carta) { cartaId ->
                onCardClick(cartaId, context)
            }
        }*/
        Spacer(modifier = Modifier.size(56.dp))

    }


}

private fun onCardClick(cartaId: Int, context: Context) {
    val intent = Intent(context, CartDetailActivity::class.java)
    intent.putExtra("cartId", cartaId)
    context.startActivity(intent)
}
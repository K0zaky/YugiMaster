package com.dabellan.yugiproject.presentation.fragments

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.navigation.compose.rememberNavController
import com.dabellan.yugiproject.presentation.composables.CardItem


@Composable
fun HomeFragment(homeViewModel: HomeViewModel) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val allCards = homeViewModel.allCards.asFlow().collectAsState(initial = emptyList())
    val navController = rememberNavController()

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

    Column(Modifier.verticalScroll(rememberScrollState())) {
        Spacer(modifier = Modifier.size(64.dp))

        allCards.value.forEach { carta ->
            CardItem(carta) {
                onCardClick(context)
            }
        }
        Spacer(modifier = Modifier.size(56.dp))
    }
}

private fun onCardClick(context: Context) {
    Toast.makeText(context, "Hola", Toast.LENGTH_SHORT).show()
}



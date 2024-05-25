package com.dabellan.yugiproject.presentation.fragments.carrito


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dabellan.yugiproject.presentation.composables.normalizarTexto
import com.dabellan.yugiproject.presentation.fragments.perfil.PerfilViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CarritoFragment(
    carritoViewModel: CarritoViewModel = viewModel(),
    perfilViewModel: PerfilViewModel = viewModel()
) {
    val carritoItems by carritoViewModel.carritoItems.collectAsState()
    val context = LocalContext.current

    DisposableEffect(key1 = carritoViewModel) {
        val carritoItemsFlow = carritoViewModel.carritoItems
        val carritoItemsJob = GlobalScope.launch {
            carritoItemsFlow.collect {
            }
        }

        onDispose {
            carritoItemsJob.cancel()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.size(64.dp))

        if (carritoItems.isEmpty()) {
            Text(
                text = "Carrito vacío",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(16.dp)
            ) {
                items(carritoItems, key = { it }) { itemName ->
                    val dismissState = rememberDismissState(
                        confirmStateChange = {
                            if (it == DismissValue.DismissedToEnd) {
                                carritoViewModel.removeFromCarrito(itemName)
                                true
                            } else {
                                false
                            }
                        }
                    )

                    SwipeToDismiss(
                        state = dismissState,
                        directions = setOf(DismissDirection.EndToStart),
                        background = {
                            val color = when (dismissState.dismissDirection) {
                                DismissDirection.EndToStart -> Color.Red
                                else -> Color.Transparent
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color)
                                    .padding(8.dp),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete Icon",
                                    tint = Color.White
                                )
                            }
                        },
                        dismissContent = {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                elevation = CardDefaults.cardElevation(4.dp)
                            ) {
                                Text(
                                    text = normalizarTexto(itemName),
                                    fontSize = 18.sp,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.size(16.dp))

            Button(
                onClick = {
                    comprar(context, carritoItems, perfilViewModel, carritoViewModel)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(text = "Comprar", fontSize = 20.sp)
            }
        }

        Spacer(modifier = Modifier.size(56.dp))
    }
}

private fun comprar(
    context: android.content.Context,
    carritoItems: List<String>,
    perfilViewModel: PerfilViewModel,
    carritoViewModel: CarritoViewModel
) {
    perfilViewModel.guardarHistorialCompras(carritoItems)
    Toast.makeText(context, "Compra completada", Toast.LENGTH_SHORT).show()
    carritoViewModel.clearCarrito()
}
package com.dabellan.yugiproject.presentation.fragments.carrito


import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dabellan.yugiproject.presentation.composables.normalizarTexto
import com.dabellan.yugiproject.presentation.fragments.perfil.PerfilViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CarritoFragment(
    carritoViewModel: CarritoViewModel = viewModel(),
    perfilViewModel: PerfilViewModel = viewModel()
) {
    var showCarrito by remember { mutableStateOf(true) }

    if (showCarrito) {
        CarritoScreen(
            carritoViewModel = carritoViewModel,
            perfilViewModel = perfilViewModel,
            onRefreshRequested = { showCarrito = false }
        )
    } else {
        LaunchedEffect(Unit) {
            showCarrito = true
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CarritoScreen(
    carritoViewModel: CarritoViewModel,
    perfilViewModel: PerfilViewModel,
    onRefreshRequested: () -> Unit
) {
    val carritoItems by carritoViewModel.carritoItems.collectAsState()
    val context = LocalContext.current

    var showDialog by remember { mutableStateOf(false) }
    var itemToDelete by remember { mutableStateOf<String?>(null) }

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
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .combinedClickable(
                                onClick = {},
                                onLongClick = {
                                    itemToDelete = itemName
                                    showDialog = true
                                }
                            ),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Text(
                            text = normalizarTexto(itemName),
                            fontSize = 18.sp,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.size(16.dp))

            Button(
                onClick = {
                    comprar(context, carritoItems, perfilViewModel, carritoViewModel)
                    onRefreshRequested()
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

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
                itemToDelete = null
            },
            title = { Text("Eliminar del carrito") },
            text = { Text("¿Estás seguro de que deseas eliminar el artículo ${itemToDelete}?") },
            confirmButton = {
                Button(
                    onClick = {
                        carritoViewModel.removeFromCarrito(itemToDelete!!)
                        showDialog = false
                        itemToDelete = null
                        onRefreshRequested()
                    }
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                Button(onClick = {
                    showDialog = false
                    itemToDelete = null
                }) {
                    Text("Cancelar")
                }
            }
        )
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
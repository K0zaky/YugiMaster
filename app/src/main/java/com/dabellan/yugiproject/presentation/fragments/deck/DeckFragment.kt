
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.dabellan.yugiproject.data.model.DeckItem
import com.dabellan.yugiproject.presentation.deck_detail.DeckDetailActivity
import com.dabellan.yugiproject.presentation.fragments.deck.DeckViewModel

@Composable
fun DeckFragment(deckViewModel: DeckViewModel = viewModel()) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    DisposableEffect(lifecycleOwner) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                deckViewModel.getAllDecks()
            }
        }
        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
        }
    }

    val allDecks by deckViewModel.allDecks.observeAsState(emptyList())
    var showDialog by remember { mutableStateOf(false) }
    var deckName by remember { mutableStateOf("") }
    var deckToDelete by remember { mutableStateOf<DeckItem?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(Modifier.verticalScroll(rememberScrollState())) {
            Spacer(modifier = Modifier.size(64.dp))
            Text(
                text = "Decks",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            if (allDecks.isEmpty()) {
                Text(
                    text = "No hay decks",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                        .padding(16.dp)
                )
            } else {
                allDecks.forEach { deck ->
                    DeckItem(deck,
                        onLongClick = {
                            deckToDelete = deck
                            showDialog = true
                        },
                        onClick = { deckId: Int ->
                            onDeckClick(deckId, context)
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.size(56.dp))
        }

        FloatingActionButton(
            onClick = {
                deckToDelete = null
                showDialog = true
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .padding(bottom = 56.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Deck")
        }

        if (showDialog) {
            if (deckToDelete != null) {
                AlertDialog(
                    onDismissRequest = {
                        showDialog = false
                        deckToDelete = null
                    },
                    title = { Text("Eliminar Deck") },
                    text = { Text("¿Estás seguro de que deseas eliminar el deck ${deckToDelete?.nombre}?") },
                    confirmButton = {
                        Button(
                            onClick = {
                                deckViewModel.deleteDeck(deckToDelete!!.id)
                                showDialog = false
                                deckToDelete = null
                            }
                        ) {
                            Text("Eliminar")
                        }
                    },
                    dismissButton = {
                        Button(onClick = {
                            showDialog = false
                            deckToDelete = null
                        }) {
                            Text("Cancelar")
                        }
                    }
                )
            } else {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text("Añadir Deck") },
                    text = {
                        Column {
                            Text("Ingrese el nombre del deck:")
                            Spacer(modifier = Modifier.height(8.dp))
                            TextField(
                                value = deckName,
                                onValueChange = { deckName = it },
                                placeholder = { Text("Nombre del deck") }
                            )
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                if (deckName.isNotBlank()) {
                                    deckViewModel.addDeck(deckName)
                                    showDialog = false
                                }
                            }
                        ) {
                            Text("Añadir")
                        }
                    },
                    dismissButton = {
                        Button(onClick = { showDialog = false }) {
                            Text("Cancelar")
                        }
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DeckItem(deck: DeckItem, onClick: (Int) -> Unit, onLongClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .combinedClickable(
                onClick = { onClick(deck.id) },
                onLongClick = { onLongClick() }
            )
    ) {
        Image(
            painter = rememberImagePainter("https://mcdn.wallpapersafari.com/medium/76/11/dEFLmg.png"),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = deck.nombre,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
    }
}
private fun onDeckClick(deckId: Int, context: Context) {
    val intent = Intent(context, DeckDetailActivity::class.java)
    intent.putExtra("deckId", deckId)
    context.startActivity(intent)
}
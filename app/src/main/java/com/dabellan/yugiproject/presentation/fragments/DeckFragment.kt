
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dabellan.yugiproject.presentation.composables.DeckItem
import com.dabellan.yugiproject.presentation.deck_detail.DeckDetailActivity
import com.dabellan.yugiproject.presentation.fragments.DeckViewModel

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

    Box(modifier = Modifier.fillMaxSize()) {
        Column(Modifier.verticalScroll(rememberScrollState())) {
            Spacer(modifier = Modifier.size(64.dp))
            Text(
                text = "Decks",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,)

            if (allDecks.isEmpty()) {
                Text(
                    text = "No hay decks",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                allDecks.forEach { deck ->
                    DeckItem(deck) { deckId: Int ->
                        onDeckClick(deckId, context)
                    }
                }
            }

            Spacer(modifier = Modifier.size(56.dp))
        }

        FloatingActionButton(
            onClick = { showDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .padding(bottom = 56.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add Deck")
        }

        if (showDialog) {
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

private fun onDeckClick(deckId: Int, context: Context) {
    val intent = Intent(context, DeckDetailActivity::class.java)
    intent.putExtra("deckId", deckId)
    context.startActivity(intent)
}
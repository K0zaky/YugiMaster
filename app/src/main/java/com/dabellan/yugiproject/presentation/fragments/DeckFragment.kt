
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
import androidx.lifecycle.viewmodel.compose.viewModel
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

    val allDecks = deckViewModel.allDecks.asFlow().collectAsState(initial = emptyList())

    Column(Modifier.verticalScroll(rememberScrollState())) {
        Spacer(modifier = Modifier.size(64.dp))
        Text(text = "decks")
        /*
        allDecks.value.forEach { deck ->
            DeckItem(deck) { deckId: Int ->
                onDeckClick(deckId, context)
            }
        }*/
        Spacer(modifier = Modifier.size(56.dp))
    }
}

private fun onDeckClick(deckId: Int, context: Context) {
    val intent = Intent(context, DeckDetailActivity::class.java)
    intent.putExtra("deckId", deckId)
    context.startActivity(intent)
}


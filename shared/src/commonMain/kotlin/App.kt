import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import ui.BirdList
import viewmodel.BirdViewModel

@Composable
fun App() {
    MaterialTheme {
        val birdViewModel = getViewModel(
            key = Unit,
            factory = viewModelFactory { BirdViewModel() })

        val birdState by birdViewModel.birdState.collectAsState()

        BirdList(birdState) { category ->
            birdViewModel.selectCategory(category)
        }
    }
}

expect fun getPlatformName(): String
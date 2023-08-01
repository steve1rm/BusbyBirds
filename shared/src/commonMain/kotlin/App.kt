import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory
import ui.BirdList
import viewmodel.BirdViewModel


@Composable
fun BusbyBirdsTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = MaterialTheme.colors.copy(primary = Color.DarkGray),
        shapes = MaterialTheme.shapes.copy(
            small = AbsoluteCutCornerShape(0.dp),
            medium = AbsoluteCutCornerShape(0.dp),
            large = AbsoluteCutCornerShape(0.dp)
        )
    ) {
        content()
    }
}

@Composable
fun App() {
    BusbyBirdsTheme {
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
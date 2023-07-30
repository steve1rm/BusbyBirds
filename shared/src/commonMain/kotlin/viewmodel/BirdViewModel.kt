package viewmodel

import androidx.compose.runtime.mutableStateOf
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import model.Bird
import state.BirdState

class BirdViewModel : ViewModel() {
    private val _birdState = MutableStateFlow<BirdState>(BirdState())
    val birdState = _birdState.asStateFlow()

    private val httpClient by lazy {
        HttpClient {
            install(ContentNegotiation) {
                json()
            }
        }
    }

    init {
        viewModelScope.launch {
            _birdState.update { birdState ->
                birdState.copy(
                    listOfBird = getListOfBird()
                )
            }
        }
    }

    private suspend fun getListOfBird(): List<Bird> {
        val result = httpClient
            .get("https://sebastianaigner.github.io/demo-image-api/pictures.json")
            .body<List<Bird>>()

        println(result)

        return result
    }

}
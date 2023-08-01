package viewmodel

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
        fetchListOfBirds()
    }

    fun fetchListOfBirds() {
        viewModelScope.launch {
            val listOfBirds = getListOfBird()
            val listOfCategory = listOfBirds.map { category -> category.category }.toSet()
            val defaultSelectedCategory = listOfCategory.firstOrNull().orEmpty()
            val filteredByDefault = listOfBirds.filter { bird -> bird.category  == defaultSelectedCategory}

            _birdState.update { birdState ->
                birdState.copy(
                    listOfBird = listOfBirds,
                    listOfFilteredBird = filteredByDefault,
                    listOfCategory = listOfCategory.toList(),
                    selectedCategory = defaultSelectedCategory
                )
            }
        }
    }

    fun selectCategory(category: String) {
        val filteredBirds = filterSelectedCategory(category)

        _birdState.update { birdState ->
            birdState.copy(listOfFilteredBird = filteredBirds)
        }
    }

    override fun onCleared() {
        httpClient.close()
        super.onCleared()
    }

    private fun filterSelectedCategory(selectedCategory: String): List<Bird> {
        return _birdState.value.listOfBird.filter { bird ->
            selectedCategory == bird.category
        }
    }

    private suspend fun getListOfBird(): List<Bird> {
        return httpClient
            .get("https://sebastianaigner.github.io/demo-image-api/pictures.json")
            .body<List<Bird>>()
    }
}
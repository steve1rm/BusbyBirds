package state

import model.Bird

data class BirdState(
    val listOfBird: List<Bird> = emptyList(),
    val listOfFilteredBird: List<Bird> = emptyList(),
    val listOfCategory: List<String> = emptyList(),
    val selectedCategory: String? = null
)
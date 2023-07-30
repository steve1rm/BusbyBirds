package state

import model.Bird

data class BirdState(
    val listOfBird: List<Bird> = emptyList()
)
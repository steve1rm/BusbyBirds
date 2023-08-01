package ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import model.Bird
import state.BirdState


@Composable
fun BirdList(
    birdState: BirdState,
    modifier: Modifier = Modifier,
    onCategoryClicked: (category: String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            birdState.listOfCategory.toList().forEach { category ->
                Button(
                    modifier = Modifier.weight(1.0F),
                    onClick = {
                       onCategoryClicked(category)
                    }
                ) {
                    Text(text = category)
                }
            }
        }

        AnimatedVisibility(birdState.listOfFilteredBird.isNotEmpty()) {
            LazyVerticalGrid(
                modifier = modifier,
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                contentPadding = PaddingValues(top = 4.dp, start = 2.dp, end = 2.dp)
            ) {
                items(birdState.listOfFilteredBird) { bird ->
                    BirdItem(bird)
                }
            }
        }
    }
}

@Composable
fun BirdItem(bird: Bird) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = bird.author, textAlign = TextAlign.Center, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(2.dp))
        KamelImage(
            modifier = Modifier.fillMaxWidth().aspectRatio(1.0F),
            resource = asyncPainterResource(
                data = "https://sebastianaigner.github.io/demo-image-api/${bird.path}"
            ),
            contentScale = ContentScale.Crop,
            contentDescription = "Image of birds")
    }
}

@Composable
fun PreviewBirdList() {

}
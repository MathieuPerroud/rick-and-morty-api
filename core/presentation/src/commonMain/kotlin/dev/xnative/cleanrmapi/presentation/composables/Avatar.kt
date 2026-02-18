package dev.xnative.cleanrmapi.presentation.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.compose.SubcomposeAsyncImage

@Composable
fun Avatar(
    contentScale: ContentScale = ContentScale.Crop,
    url: String
) {
    SubcomposeAsyncImage(
        modifier = Modifier
            .fillMaxWidth(),
        model = url,
        contentDescription = null,
        contentScale = contentScale
    )

}
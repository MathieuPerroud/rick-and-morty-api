package dev.xnative.cleanrmapi.presentation.composables

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImagePainter
import coil3.compose.LocalPlatformContext
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest

@Composable
fun Avatar(
    contentScale: ContentScale = ContentScale.Crop,
    url: String
) {
    var hasAppeared by remember { mutableStateOf(false) }

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalPlatformContext.current)
            .data(url)
            .build(),
        transform = { state ->
            when (state) {
                is AsyncImagePainter.State.Success -> {
                    hasAppeared = true
                    state
                }
                else -> state
            }
        }
    )
    val alpha by animateFloatAsState(
        targetValue = if (hasAppeared) 1f else 0f,
        animationSpec = tween(300),
        label = ""
    )

    Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier.fillMaxWidth().alpha(alpha),
        contentScale = contentScale
    )
}
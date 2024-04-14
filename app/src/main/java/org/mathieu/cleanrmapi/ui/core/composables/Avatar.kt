package org.mathieu.cleanrmapi.ui.core.composables

import android.content.Context
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import org.mathieu.cleanrmapi.ui.core.extensions.koinInject

@Composable
fun Avatar(
    imageLoader: ImageLoader = koinInject(valueForPreview = ImageLoader(LocalContext.current)),
    contentScale: ContentScale = ContentScale.Crop,
    url: String
) {

    val context: Context = LocalContext.current

    val imageRequest = ImageRequest.Builder(context)
        .data(url)
        .crossfade(true)
        .build()

    SubcomposeAsyncImage(
        modifier = Modifier
            .fillMaxWidth(),
        imageLoader = imageLoader,
        model = imageRequest,
        contentDescription = null,
        contentScale = contentScale
    )

}
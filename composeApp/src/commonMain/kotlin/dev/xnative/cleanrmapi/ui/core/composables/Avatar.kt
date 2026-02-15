package dev.xnative.cleanrmapi.ui.core.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.ImageLoader
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import dev.xnative.cleanrmapi.ui.core.extensions.koinInject

@Composable
fun Avatar(
//    imageLoader: ImageLoader = /*koinInject(valueForPreview =*/ ImageLoader(LocalPlatformContext.current/*)*/),
    contentScale: ContentScale = ContentScale.Crop,
    url: String
) {

//    val context = LocalPlatformContext.current
//
//    val imageRequest = ImageRequest.Builder(context)
//        .data(url)
//        .crossfade(true)
//        .build()

    SubcomposeAsyncImage(
        modifier = Modifier
            .fillMaxWidth(),
//        imageLoader = imageLoader,
        model = url,
        contentDescription = null,
        contentScale = contentScale
    )

}
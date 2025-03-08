package org.mathieu.cleanrmapi.ui

import android.content.Context
import coil.ImageLoader
import coil.decode.ImageDecoderDecoder
import coil.disk.DiskCache
import coil.memory.MemoryCache
import org.koin.dsl.module

val uiModule = module {
    single {

        val context: Context = get()

        ImageLoader.Builder(context)
            .memoryCache {
                MemoryCache.Builder(context)
                    .maxSizePercent(0.25) // maximum use of available memory will be 25%
                    .build()
            }
            .diskCache {
                // When the disk cache reaches its maximum size, Coil will start deleting
                // the oldest or least recently used images from the cache to make room for new ones.
                DiskCache.Builder()
                    .directory(context.cacheDir.resolve("image_cache"))
                    .maxSizeBytes(100L * 1024 * 1024) // Set the maximum size of the disk cache to 100 MB
                    .build()

            }
            .components {
                add(ImageDecoderDecoder.Factory())
            }
            .build()
    }


}
